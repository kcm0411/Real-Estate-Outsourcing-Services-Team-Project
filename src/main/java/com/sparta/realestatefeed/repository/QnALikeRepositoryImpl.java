package com.sparta.realestatefeed.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.realestatefeed.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public Long findCountLikeByQnAId(Long qnAId){

        QQnALike qnALike = QQnALike.qnALike;

        return jpaQueryFactory.select(qnALike.count())
                .from(qnALike)
                .where(qnALike.qnA.qnaId.eq(qnAId))
                .fetchOne();

    }

    public List<QnA> findByUserId(Long id, long offset, int pageSize){

        QQnALike qnALike = QQnALike.qnALike;
        QQnA qnA = QQnA.qnA;

        OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(Order.DESC, qnA.createdAt);

        return jpaQueryFactory.select(qnA)
                .from(qnALike)
                .join(qnALike.qnA,qnA)
                .where(qnALike.user.id.eq(id))
                .offset(offset)
                .limit(pageSize)
                .orderBy(orderSpecifier)
                .fetch();

    }

    public Long findCountLikeByUserId(Long id){

        QQnALike qnALike = QQnALike.qnALike;

        return jpaQueryFactory.select(qnALike.count())
                .from(qnALike)
                .where(qnALike.user.id.eq(id))
                .fetchOne();
    }
}
