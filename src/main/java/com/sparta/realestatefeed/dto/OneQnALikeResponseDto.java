package com.sparta.realestatefeed.dto;

import com.sparta.realestatefeed.entity.QnA;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class OneQnALikeResponseDto {

    private Long qnaId;
    private String content;
    private Long countLike;

    public OneQnALikeResponseDto(QnA qna, Long countLike) {
        this.qnaId = qna.getQnaId();
        this.content = qna.getContent();
        this.countLike = countLike;
    }
}
