package com.sparta.realestatefeed.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.realestatefeed.entity.ApartLike;
import com.sparta.realestatefeed.entity.QApartLike;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ApartLikeRepositoryImpl implements ApartLikeRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public ApartLike findByApartIdAndUserId(Long apartId, Long userId) {
        QApartLike apartLike = QApartLike.apartLike;

        return jpaQueryFactory.selectFrom(apartLike)
                .where(apartLike.apart.id.eq(apartId).and(apartLike.user.id.eq(userId)))
                .fetchOne();
    }
}