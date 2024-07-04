package com.sparta.realestatefeed.repository;

import com.sparta.realestatefeed.entity.ApartLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartLikeRepository extends JpaRepository<ApartLike, Long>, ApartLikeRepositoryCustom {
}
