package com.sparta.realestatefeed.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.realestatefeed.entity.QQnALike;
import com.sparta.realestatefeed.entity.QnALike;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QnALikeRepositoryImpl implements QnALikeRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public QnALike findByQnAIdAndUserId(Long qnAId, Long userId) {

        QQnALike qnALike = QQnALike.qnALike;

        return jpaQueryFactory.selectFrom(qnALike)
                .where(qnALike.qnA.qnaId.eq(qnAId).and(qnALike.user.id.eq(userId)))
                .fetchOne();
    }

}
