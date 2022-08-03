package com.tave_app_1.senapool.likes.repository;

import com.tave_app_1.senapool.entity.Likes;
import com.tave_app_1.senapool.entity.PlantDiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    @Modifying
    @Query(value = "INSERT INTO likes(diary_pk, user_pk) VALUES(:diaryPK, :userPK)", nativeQuery = true)
    void likes(long diaryPK, long userPK);

    @Modifying
    @Query(value = "DELETE FROM likes WHERE diary_pk = :diaryPK AND user_pk = :userPK", nativeQuery = true)
    void unLikes(long diaryPK, long userPK);

    List<Likes> findAllByDiary(PlantDiary diary);
}
