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
    public List<Apart> findByFollweeIdList(List<Long> followeeIdList, String orderByOption, long offset, int pageSize){

        QApart apart = QApart.apart;

        OrderSpecifier<?>[] orderSpecifiers;

        switch(orderByOption){

            case "user":
                orderSpecifiers = new OrderSpecifier<?>[]{
                        new OrderSpecifier<>(Order.ASC, apart.user.id),
                        new OrderSpecifier<>(Order.DESC, apart.createdAt)
                };

                break;

            default:
                orderSpecifiers = new OrderSpecifier<?>[]{
                        new OrderSpecifier<>(Order.DESC, apart.createdAt)
                };
                break;
        }

        return jpaQueryFactory.selectFrom(apart)
                .where(apart.user.id.in(followeeIdList))
                .offset(offset)
                .limit(pageSize)
                .orderBy(orderSpecifiers)
                .fetch();

    }

}
