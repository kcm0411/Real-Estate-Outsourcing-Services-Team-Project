package com.sparta.realestatefeed.service;

import com.sparta.realestatefeed.dto.CommonDto;
import com.sparta.realestatefeed.dto.OneQnALikeResponseDto;
import com.sparta.realestatefeed.dto.QnARequestDto;
import com.sparta.realestatefeed.dto.QnAResponseDto;
import com.sparta.realestatefeed.entity.*;
import com.sparta.realestatefeed.repository.ApartRepository;
import com.sparta.realestatefeed.repository.QnALikeRepository;
import com.sparta.realestatefeed.repository.QnARepository;
import com.sparta.realestatefeed.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class QnAService {

    private QnARepository qnARepository;
    private UserRepository userRepository;
    private ApartRepository apartRepository;
    private QnALikeRepository qnALikeRepository;

    public QnAService(QnARepository qnARepository, UserRepository userRepository, ApartRepository apartRepository, QnALikeRepository qnALikeRepository) {
        this.qnARepository = qnARepository;
        this.userRepository = userRepository;
        this.apartRepository = apartRepository;
        this.qnALikeRepository = qnALikeRepository;
    }

    @Transactional
    public CommonDto<QnAResponseDto> create(Long apartId, QnARequestDto qnARequestDto, User user) {

        Apart apart = apartRepository.findById(apartId)
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 아파트를 찾을 수 없습니다."));

        if (apart.getIsSaled() == ApartStatusEnum.SOLD) {
            throw new NoSuchElementException("매매중인 아파트가 존재하지 않습니다.");
        }

        QnA saveQnA = new QnA(qnARequestDto.getContent(),user, apart);

        qnARepository.save(saveQnA);

        QnAResponseDto responseDto = new QnAResponseDto(saveQnA);

        CommonDto<QnAResponseDto> commonDto = new CommonDto<QnAResponseDto>(HttpStatus.OK.value(), "문의 등록에 성공하셨습니다.", responseDto);

        return commonDto;

    }

    public CommonDto<OneQnALikeResponseDto> select(Long qnaId) {

        QnA qna = qnARepository.findById(qnaId)
                .orElseThrow(() -> new NoSuchElementException("요청하신 댓글이 존재하지 않습니다."));

        // 댓글 id로 좋아요 수 조회
        Long countLike = qnALikeRepository.findCountLikeByQnAId(qnaId);

        OneQnALikeResponseDto responseDto = new OneQnALikeResponseDto(qna, countLike);

        CommonDto<OneQnALikeResponseDto> commonDto = new CommonDto<OneQnALikeResponseDto>(HttpStatus.OK.value(), "문의 조회에 성공하셨습니다.", responseDto);

        return commonDto;

    }

    @Transactional
    public CommonDto<QnAResponseDto> updateQnA(Long qnaId, QnARequestDto qnARequestDto, User user) {

        QnA qna = qnARepository.findById(qnaId)
                .orElseThrow(() -> new NoSuchElementException("요청하신 댓글이 존재하지 않습니다."));

        if (!user.getRole().equals(UserRoleEnum.ADMIN)) {
            if (!user.getId().equals(qna.getUser().getId())) {
                throw new AccessDeniedException("직접 작성한 댓글만 수정할 수 있습니다.");
            }
        }

        qna.changeContent(qnARequestDto.getContent());

        QnAResponseDto responseDto = new QnAResponseDto(qna);

        CommonDto<QnAResponseDto> commonDto = new CommonDto<QnAResponseDto>(HttpStatus.OK.value(), "문의 수정에 성공하셨습니다.", responseDto);

        return commonDto;

    }

    public void deleteQnA(Long qnaId, User user) {

        QnA qna = qnARepository.findById(qnaId)
                .orElseThrow(() -> new NoSuchElementException("요청하신 댓글이 존재하지 않습니다."));

        if (!user.getRole().equals(UserRoleEnum.ADMIN)) {
            if (!user.getId().equals(qna.getUser().getId())) {
                throw new AccessDeniedException("직접 작성한 댓글만 삭제할 수 있습니다.");
            }
        }

        qnARepository.deleteById(qna.getQnaId());

    }

    public CommonDto<List<QnAResponseDto>> selectByApartId(Long apartId) {

        List<QnA> qnas = qnARepository.findByApartId(apartId);

        List<QnAResponseDto> responseDtos = new ArrayList<>();

        for (QnA l : qnas) {
            QnAResponseDto qnaResponseDto = new QnAResponseDto(l);
            responseDtos.add(qnaResponseDto);

        }

        CommonDto<List<QnAResponseDto>> commonDto = new CommonDto<>(HttpStatus.OK.value(), "아파트에 대한 문의 조회에 성공하셨습니다.", responseDtos);

        return commonDto;
    }

    public CommonDto<String> likeQnA(Long qnaId, User user) {

        // 댓글 있는지 체크
        QnA qnA = qnARepository.findById(qnaId)
                .orElseThrow(() -> new NoSuchElementException("요청하신 댓글이 존재하지 않습니다."));

        // 댓글 작성자가 본인인지 체크
        if (qnA.getUser().getId().equals(user.getId())){
            throw new IllegalArgumentException("자신의 댓글에는 좋아요를 추가할 수 없습니다.");
        }


        // 댓글이 있다면, 댓글좋아요 테이블에서 좋아요를 이미 누른상태인지, 아닌지 체크
        if (Objects.isNull(qnALikeRepository.findByQnAIdAndUserId(qnaId, user.getId()))) {

            // NULL 값 반환 = DB에 없음 = 좋아요 등록 = Create
            QnALike qnALike = new QnALike(qnA, user);
            qnALikeRepository.save(qnALike);

            return new CommonDto<>(HttpStatus.OK.value(), "좋아요를 눌렀습니다.", null);

        } else {

            // DB에 있음 = 좋아요 취소 = Delete
            QnALike qnALike = qnALikeRepository.findByQnAIdAndUserId(qnaId, user.getId());
            qnALikeRepository.delete(qnALike);

            return new CommonDto<>(HttpStatus.OK.value(), "좋아요를 취소했습니다.", null);

        }
    }

}
