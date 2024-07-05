package com.sparta.realestatefeed.dto;

import com.sparta.realestatefeed.entity.User;
import lombok.Getter;

@Getter
public class ProfileAndCountLikeResponseDto {

    private String userName;
    private String nickName;
    private String email;
    private String info;
    private Long apartCountLike;
    private Long qnACountLike;

    public ProfileAndCountLikeResponseDto(User user, Long apartCountLike, Long qnACountLike) {
        this.userName = user.getUserName();
        this.nickName = user.getNickName();
        this.email = user.getEmail();
        this.info = user.getInfo();
        this.apartCountLike = apartCountLike;
        this.qnACountLike = qnACountLike;
    }

}
