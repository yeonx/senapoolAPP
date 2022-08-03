package com.tave_app_1.senapool.plant_diary.service;


import com.tave_app_1.senapool.entity.MyPlant;
import com.tave_app_1.senapool.entity.PlantDiary;
import com.tave_app_1.senapool.entity.User;
import com.tave_app_1.senapool.exception.CustomException;
import com.tave_app_1.senapool.myplant_list.repository.MyPlantRepository;
import com.tave_app_1.senapool.plant_diary.dto.PlantDiaryDetailDto;
import com.tave_app_1.senapool.plant_diary.dto.PlantDiaryUpdateDto;
import com.tave_app_1.senapool.plant_diary.dto.PlantDiaryUploadDto;
import com.tave_app_1.senapool.plant_diary.repository.PlantDiaryRepository;
import com.tave_app_1.senapool.user.repository.UserRepository;
import com.tave_app_1.senapool.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlantDiaryService {

    private final UserRepository userRepository;
    private final PlantDiaryRepository plantDiaryRepository;
    private final MyPlantRepository myPlantRepository;

    private final FileUtil fileUtil;


    @Transactional
    public void uploadDiary(PlantDiaryUploadDto plantDiaryUploadDto,User user,MyPlant myPlant){
        PlantDiary plantDiary;
        plantDiary = makePlantDiaryEntity(plantDiaryUploadDto,user,myPlant);
        plantDiaryRepository.save(plantDiary);
    }


    @Transactional
    public void updateDiary(long plantDiaryPK, PlantDiaryUpdateDto plantDiaryUpdateDto) throws IOException {
        PlantDiary plantDiary = plantDiaryRepository.findByPlantDiaryPK(plantDiaryPK);
        String uniqueImageName = fileUtil.imageChange(plantDiaryUpdateDto.getFile(),plantDiary.getDiaryImage());
        plantDiary.update(plantDiaryUpdateDto.getTitle(),plantDiaryUpdateDto.getContent(),uniqueImageName,plantDiaryUpdateDto.getPublish(),plantDiaryUpdateDto.getCreateDate());
    }


    @Transactional
    public void delete(Long plantPK) {
        // 일기 삭제
        plantDiaryRepository.deleteById(plantPK);
    }


    @Transactional(readOnly = true)
    public PlantDiaryDetailDto makeDiaryDetail(Long userPK, Long diaryPK) {

        PlantDiary plantDiary = plantDiaryRepository.findByPlantDiaryPK(diaryPK);

        if(plantDiary==null) throw new CustomException("데이터베이스에서 해당 식물 일기를 발견하지 못했습니다.");

        return new PlantDiaryDetailDto(userPK,plantDiary);
    }



    //Entity 만들기
    private PlantDiary makePlantDiaryEntity(PlantDiaryUploadDto plantDiaryUploadDto, User user, MyPlant myPlant) {

        PlantDiary plantDiary;

        if(plantDiaryUploadDto.getFile().isEmpty()){
            plantDiary = plantDiaryUploadDto.toEntity("", user,myPlant);
        }else{
            String uniqueImageName = fileUtil.saveDiaryImage(plantDiaryUploadDto.getFile());
            plantDiary = plantDiaryUploadDto.toEntity(uniqueImageName, user,myPlant);
        }

        return plantDiary;
    }


    @Transactional
    public void deleteMyPlantDiaryAll(MyPlant myPlant){
        plantDiaryRepository.deleteByMyPlant(myPlant);
    }


    @Transactional
    public void deleteUserDiaryAll(User user){
        plantDiaryRepository.deleteByUser(user);
    }


}
