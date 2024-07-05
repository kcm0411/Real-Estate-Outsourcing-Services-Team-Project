package com.sparta.realestatefeed.dto;

import com.sparta.realestatefeed.entity.Apart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OneApartLikeResponseDto {

    private Long id;
    private String apartName;
    private String address;
    private String area;
    private Long salePrice;
    private String isSaled;
    private LocalDateTime modifiedAt;
    private String nickName;
    private Long countLike;

    public OneApartLikeResponseDto(Apart apart, Long countLike) {
        this.id = apart.getId();
        this.apartName = apart.getApartName();
        this.address = apart.getAddress();
        this.area = apart.getArea();
        this.salePrice = apart.getSalePrice();
        this.isSaled = apart.getIsSaled().getDescription();
        this.modifiedAt = apart.getModifiedAt();
        this.nickName = apart.getUser().getNickName();
        this.countLike = countLike;
    }

}
