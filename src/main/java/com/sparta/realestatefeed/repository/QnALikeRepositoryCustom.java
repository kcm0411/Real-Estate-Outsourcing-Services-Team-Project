package com.sparta.realestatefeed.repository;

import com.sparta.realestatefeed.entity.QnALike;
import org.springframework.stereotype.Repository;

@Repository
public interface QnALikeRepositoryCustom {

    QnALike findByQnAIdAndUserId(Long qnaId, Long id);

    Long findCountLikeByQnAId(Long qnaId);

}
