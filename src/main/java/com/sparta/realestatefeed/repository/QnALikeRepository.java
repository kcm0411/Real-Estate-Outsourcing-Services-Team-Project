package com.sparta.realestatefeed.repository;

import com.sparta.realestatefeed.entity.QnALike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QnALikeRepository extends JpaRepository<QnALike, Long>, QnALikeRepositoryCustom {
}
