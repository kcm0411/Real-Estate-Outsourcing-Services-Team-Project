package com.sparta.realestatefeed.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.realestatefeed.entity.Apart;
import com.sparta.realestatefeed.entity.QApart;
import com.sparta.realestatefeed.entity.QApartLike;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ApartRepositoryImpl implements ApartRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Apart> findByFollweeIdList(List<Long> followeeIdList, long offset, int pageSize){

        QApart apart = QApart.apart;

        OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(Order.DESC, apart.createdAt);

        return jpaQueryFactory.selectFrom(apart)
                .where(apart.user.id.in(followeeIdList))
                .offset(offset)
                .limit(pageSize)
                .orderBy(orderSpecifier)
                .fetch();

    }

}
