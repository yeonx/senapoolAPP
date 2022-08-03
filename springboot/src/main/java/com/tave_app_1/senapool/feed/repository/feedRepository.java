package com.tave_app_1.senapool.feed.repository;

import com.tave_app_1.senapool.entity.PlantDiary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface feedRepository extends JpaRepository<PlantDiary, Long> {
    @Query(value = "SELECT * FROM plant_diary WHERE publish=1 ORDER BY diary_pk DESC", nativeQuery = true)
    Page<PlantDiary> mainFeed(long userPK, Pageable pageable);
}
