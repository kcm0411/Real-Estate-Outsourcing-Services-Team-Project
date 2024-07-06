package com.sparta.realestatefeed.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.realestatefeed.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FollowRepositoryImpl implements FollowRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public Follow findFollowByFollowerAndFollowee(Long followerId, Long followeeId){

        QFollow follow = QFollow.follow;

        return jpaQueryFactory.select(follow)
                .from(follow)
                .where(follow.follower.id.eq(followerId).and(follow.followee.id.eq(followeeId)))
                .fetchOne();

    }
}
