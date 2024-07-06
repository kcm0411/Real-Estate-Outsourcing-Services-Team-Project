package com.sparta.realestatefeed.service;

import com.sparta.realestatefeed.dto.CommonDto;
import com.sparta.realestatefeed.entity.Follow;
import com.sparta.realestatefeed.entity.User;
import com.sparta.realestatefeed.repository.FollowRepository;
import com.sparta.realestatefeed.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public FollowService(FollowRepository followRepository, UserRepository userRepository) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
    }

    public CommonDto<String> addFollow(User followerUser, Long userId) {

        // 아이디로 이미 팔로우했는지 체크
        Follow follow = followRepository.findFollowByFollowerAndFollowee(followerUser.getId(), userId);

        if (followerUser.getId().equals(userId)){
            throw new IllegalArgumentException("나 자신을 팔로우 할 수 없습니다.");
        }

        User followeeUser = userRepository.findById(userId).orElseThrow(
                () -> new NoSuchElementException("팔로우 할 대상을 찾지 못했습니다.")
        );

        // 팔로우 한 상태면 언팔, 안한 상태면 팔로우
        if (Objects.isNull(follow)) {

            // 팔로우 저장, return
            Follow addFollow = new Follow(followeeUser, followerUser);

            followRepository.save(addFollow);

            return new CommonDto<>(HttpStatus.OK.value(), followeeUser.getNickName() + " 님을 팔로우했습니다.", null);

        } else {

            // 언팔로우
            followRepository.delete(follow);

            return new CommonDto<>(HttpStatus.OK.value(), followeeUser.getNickName() + " 님을 언팔로우했습니다.", null);

        }

    }

}
