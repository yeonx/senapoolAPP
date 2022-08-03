package com.tave_app_1.senapool.likes.service;

import com.tave_app_1.senapool.entity.User;
import com.tave_app_1.senapool.likes.repository.LikesRepository;
import com.tave_app_1.senapool.plant_diary.repository.PlantDiaryRepository;
import com.tave_app_1.senapool.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikesService {
    private final LikesRepository likesRepository;
    private final UserRepository userRepository;

    private final PlantDiaryRepository plantDiaryRepository;

    @Transactional
    public Integer likes(long diaryPK, Long userPK){
        User user = userRepository.findByUserPK(userPK);
        likesRepository.likes(diaryPK, user.getUserPK());
        return likesRepository.findAllByDiary(plantDiaryRepository.findByPlantDiaryPK(diaryPK)).size();
    }

    @Transactional
    public Integer unLikes(long diaryPK, Long userPK){
        User user = userRepository.findByUserPK(userPK);
        likesRepository.unLikes(diaryPK, user.getUserPK());
        return likesRepository.findAllByDiary(plantDiaryRepository.findByPlantDiaryPK(diaryPK)).size();
    }
}
