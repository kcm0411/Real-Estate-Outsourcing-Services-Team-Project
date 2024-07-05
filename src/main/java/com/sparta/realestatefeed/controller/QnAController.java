package com.sparta.realestatefeed.controller;

import com.sparta.realestatefeed.dto.CommonDto;
import com.sparta.realestatefeed.dto.OneQnALikeResponseDto;
import com.sparta.realestatefeed.dto.QnARequestDto;
import com.sparta.realestatefeed.dto.QnAResponseDto;
import com.sparta.realestatefeed.security.UserDetailsImpl;
import com.sparta.realestatefeed.service.QnAService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class QnAController {

    private QnAService qnAService;

    public QnAController(QnAService qnAService) {
        this.qnAService = qnAService;
    }

    @PostMapping("/aparts/{apartId}/qna")
    public ResponseEntity<CommonDto<QnAResponseDto>> createQnA(@PathVariable Long apartId, @RequestBody QnARequestDto qnARequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        CommonDto<QnAResponseDto> responseDto = qnAService.create(apartId, qnARequestDto, userDetails.getUser());

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);

    }

    @GetMapping("aparts/{apartId}/qna/{qnaId}")
    public ResponseEntity<CommonDto<OneQnALikeResponseDto>> getQnA(@PathVariable Long qnaId) {

        CommonDto<OneQnALikeResponseDto> responseDto = qnAService.select(qnaId);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);

    }

    @GetMapping("aparts/{apartId}/qna")
    public ResponseEntity<CommonDto<List<QnAResponseDto>>> getAllQnAForApartId(@PathVariable Long apartId) {


        CommonDto<List<QnAResponseDto>> responseDto = qnAService.selectByApartId(apartId);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);

    }

    @PutMapping("aparts/{apartId}/qna/{qnaId}")
    public ResponseEntity<CommonDto<QnAResponseDto>> updateQnA(@PathVariable Long qnaId, @RequestBody QnARequestDto qnARequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        CommonDto<QnAResponseDto> responseDto = qnAService.updateQnA(qnaId, qnARequestDto, userDetails.getUser());

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);

    }

    @DeleteMapping("aparts/{apartId}/qna/{qnaId}")
    public ResponseEntity<?> deleteQnA(@PathVariable Long qnaId, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        qnAService.deleteQnA(qnaId, userDetails.getUser());

        return ResponseEntity.status(HttpStatus.OK).body("문의 삭제에 성공하셨습니다.");

    }

    /**
     * 댓글 좋아요 기능 ( 인가 필요 )
     * @param apartId : 좋아요 할 게시글의 id (RESTful 용)
     * @param qnaId : 좋아요 할 게시글의 id
     * @return : 좋아요 추가/취소 확인
     */
    @PostMapping("aparts/{apartId}/qna/{qnaId}/like")
    public ResponseEntity<CommonDto<String>> likeApart(@PathVariable Long qnaId, @AuthenticationPrincipal UserDetailsImpl userDetails){

        CommonDto<String> responseDto = qnAService.likeQnA(qnaId, userDetails.getUser());

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);

    }
}