package com.sparta.realestatefeed.repository;

import com.sparta.realestatefeed.entity.ApartLike;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartLikeRepositoryCustom {

    ApartLike findByApartIdAndUserId(Long apartId, Long userId);

}
