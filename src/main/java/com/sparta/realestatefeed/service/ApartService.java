package com.sparta.realestatefeed.service;

import com.sparta.realestatefeed.dto.ApartRequestDto;
import com.sparta.realestatefeed.dto.ApartResponseDto;
import com.sparta.realestatefeed.dto.CommonDto;
import com.sparta.realestatefeed.dto.OneApartLikeResponseDto;
import com.sparta.realestatefeed.entity.Apart;
import com.sparta.realestatefeed.entity.ApartLike;
import com.sparta.realestatefeed.entity.User;
import com.sparta.realestatefeed.entity.UserRoleEnum;
import com.sparta.realestatefeed.repository.ApartLikeRepository;
import com.sparta.realestatefeed.repository.ApartRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class ApartService {

    private final ApartRepository apartRepository;
    private final ApartLikeRepository apartLikeRepository;

    public ApartService(ApartRepository apartRepository, ApartLikeRepository apartLikeRepository) {
        this.apartRepository = apartRepository;
        this.apartLikeRepository = apartLikeRepository;
    }

    @Transactional
    public CommonDto<ApartResponseDto> createApart(ApartRequestDto requestDto, User user) {

        Apart apart = new Apart(requestDto, user);
        Apart savedApart = apartRepository.save(apart);
        ApartResponseDto responseDto = new ApartResponseDto(savedApart);
        return new CommonDto<>(HttpStatus.OK.value(), "아파트 생성에 성공하였습니다.", responseDto);
    }

    public CommonDto<OneApartLikeResponseDto> getApart(Long id) {

        Apart apart = apartRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("유효하지 않은 아파트 ID입니다."));

        // 아파트 id로 좋아요 수 조회
        Long countLike = apartLikeRepository.findCountLikeByApartId(id);

        OneApartLikeResponseDto responseDto = new OneApartLikeResponseDto(apart,countLike);
        return new CommonDto<>(HttpStatus.OK.value(), "아파트 조회에 성공하였습니다.", responseDto);
    }

    public CommonDto<List<ApartResponseDto>> getAparts(String area, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Apart> apartsPage;
        if (area == null || area.isEmpty()) {
            apartsPage = apartRepository.findAllByOrderByModifiedAtDesc(pageable);
        } else {
            apartsPage = apartRepository.findByArea(area, pageable);
        }

        if (apartsPage.isEmpty()) {
            return new CommonDto<>(HttpStatus.NOT_FOUND.value(), "해당 지역의 아파트가 없습니다.", new ArrayList<>());
        }

        List<ApartResponseDto> responseDtos = apartsPage.stream()
                .map(ApartResponseDto::new)
                .collect(Collectors.toList());
        return new CommonDto<>(HttpStatus.OK.value(), (area == null || area.isEmpty() ? "모든" : area + " 지역별") + " 아파트 조회에 성공하였습니다.", responseDtos);
    }

    @Transactional
    public CommonDto<ApartResponseDto> updateApart(Long id, ApartRequestDto requestDto, User user) {

        Apart apart = apartRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("유효하지 않은 아파트 ID입니다."));

        if (!user.getRole().equals(UserRoleEnum.ADMIN)) {
            if (!apart.getUser().getId().equals(user.getId())) {
                throw new IllegalArgumentException("권한이 없는 사용자입니다.");
            }
        }

        apart.update(requestDto);
        Apart updatedApart = apartRepository.save(apart);
        ApartResponseDto responseDto = new ApartResponseDto(updatedApart);
        return new CommonDto<>(HttpStatus.OK.value(), "아파트 수정에 성공하였습니다.", responseDto);
    }

    public CommonDto<String> deleteApart(Long id, User user) {

        Apart apart = apartRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("유효하지 않은 아파트 ID입니다."));

        if (!user.getRole().equals(UserRoleEnum.ADMIN)) {
            if (!apart.getUser().getId().equals(user.getId())) {
                throw new IllegalArgumentException("권한이 없는 사용자입니다.");
            }
        }

        apartRepository.delete(apart);
        return new CommonDto<>(HttpStatus.OK.value(), "아파트 삭제에 성공하였습니다.", null);
    }

    public CommonDto<List<ApartResponseDto>> getApartsByArea(String area, int page, int size, String sortBy, String order) {

        Pageable pageable = PageRequest.of(page, size, order.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending());
        Page<Apart> apartsPage = apartRepository.findByArea(area, pageable);
        List<ApartResponseDto> responseDtos = apartsPage.stream()
                .map(ApartResponseDto::new)
                .collect(Collectors.toList());
        return new CommonDto<>(HttpStatus.OK.value(), area + " 지역별 아파트 조회에 성공하였습니다.", responseDtos);
    }

    public CommonDto<String> likeApart(Long id, User user) {

        // 아파트 있는지 체크
        Apart apart = apartRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("유효하지 않은 아파트 ID입니다."));

        // 아파트 작성자가 본인인지 체크
        if (apart.getUser().getId().equals(user.getId())){
            throw new IllegalArgumentException("자신의 게시글에는 좋아요를 추가할 수 없습니다.");
        }

        // 아파트 있다면, 게시글좋아요 테이블에서 좋아요를 이미 누른상태인지, 아닌지 체크
        if (Objects.isNull(apartLikeRepository.findLikeByApartIdAndUserId(id, user.getId()))){

            // NULL 값 반환 = DB에 없음 = 좋아요 등록 = Create
            ApartLike apartLike = new ApartLike(apart, user);
            apartLikeRepository.save(apartLike);

            return new CommonDto<>(HttpStatus.OK.value(), "좋아요를 눌렀습니다.", null);

        } else {

            // DB에 있음 = 좋아요 취소 = Delete
            ApartLike apartLike = apartLikeRepository.findLikeByApartIdAndUserId(id, user.getId());
            apartLikeRepository.delete(apartLike);

            return new CommonDto<>(HttpStatus.OK.value(), "좋아요를 취소했습니다.", null);

        }

    }

}
