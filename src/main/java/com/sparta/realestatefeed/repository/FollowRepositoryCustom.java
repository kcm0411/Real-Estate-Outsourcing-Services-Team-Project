package com.sparta.realestatefeed.repository;

import com.sparta.realestatefeed.entity.Follow;
import com.sparta.realestatefeed.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepositoryCustom {

    Follow findFollowByFollowerAndFollowee(Long followerId, Long followeeId);

    List<Long> findFolloweeIdList(Long followerId);

}
