package com.sparta.realestatefeed.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.realestatefeed.entity.Apart;
import com.sparta.realestatefeed.entity.ApartLike;
import com.sparta.realestatefeed.entity.QApart;
import com.sparta.realestatefeed.entity.QApartLike;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ApartLikeRepositoryImpl implements ApartLikeRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public ApartLike findLikeByApartIdAndUserId(Long apartId, Long userId) {
        QApartLike apartLike = QApartLike.apartLike;

        return jpaQueryFactory.selectFrom(apartLike)
                .where(apartLike.apart.id.eq(apartId).and(apartLike.user.id.eq(userId)))
                .fetchOne();
    }

    public Long findCountLikeByApartId(Long apartId){

        QApartLike apartLike = QApartLike.apartLike;

        return jpaQueryFactory.select(apartLike.count())
                .from(apartLike)
                .where(apartLike.apart.id.eq(apartId))
                .fetchOne();

    }

    public List<Apart> findByUserId(Long id, long offset, int pageSize){

        QApartLike apartLike = QApartLike.apartLike;
        QApart apart = QApart.apart;

        OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(Order.DESC, apart.createdAt);

        return jpaQueryFactory.select(apart)
                .from(apartLike)
                .join(apartLike.apart,apart)
                .where(apartLike.user.id.eq(id))
                .offset(offset)
                .limit(pageSize)
                .orderBy(orderSpecifier)
                .fetch();

    }

    public Long findCountLikeByUserId(Long id){

        QApartLike apartLike = QApartLike.apartLike;

        return jpaQueryFactory.select(apartLike.count())
                .from(apartLike)
                .where(apartLike.user.id.eq(id))
                .fetchOne();
    }

}