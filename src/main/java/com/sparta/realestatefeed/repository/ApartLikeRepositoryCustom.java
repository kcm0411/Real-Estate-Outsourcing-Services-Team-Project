package com.sparta.realestatefeed.repository;

import com.sparta.realestatefeed.entity.Apart;
import com.sparta.realestatefeed.entity.ApartLike;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApartLikeRepositoryCustom {

    ApartLike findLikeByApartIdAndUserId(Long apartId, Long userId);

    Long findCountLikeByApartId(Long id);

    List<Apart> findByUserId(Long id, long offset, int pageSize);

    Long findCountLikeByUserId(Long id);
}
