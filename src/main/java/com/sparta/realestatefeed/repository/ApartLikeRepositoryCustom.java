package com.sparta.realestatefeed.repository;

import com.sparta.realestatefeed.entity.ApartLike;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartLikeRepositoryCustom {

    ApartLike findLikeByApartIdAndUserId(Long apartId, Long userId);

    Long findCountLikeByApartId(Long id);
}
