package com.sparta.realestatefeed.repository;

import com.sparta.realestatefeed.entity.QnA;
import com.sparta.realestatefeed.entity.QnALike;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QnALikeRepositoryCustom {

    QnALike findByQnAIdAndUserId(Long qnaId, Long id);

    Long findCountLikeByQnAId(Long qnaId);

    List<QnA> findByUserId(Long id, long offset, int pageSize);

    Long findCountLikeByUserId(Long id);
}
