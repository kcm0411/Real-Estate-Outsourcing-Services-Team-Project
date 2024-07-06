package com.sparta.realestatefeed.repository;

import com.sparta.realestatefeed.entity.Apart;

import java.util.List;

public interface ApartRepositoryCustom {
    List<Apart> findByFollweeIdList(List<Long> followeeIdList, long offset, int pageSize);
}
