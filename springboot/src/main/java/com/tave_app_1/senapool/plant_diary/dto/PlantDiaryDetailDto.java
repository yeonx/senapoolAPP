package com.tave_app_1.senapool.plant_diary.dto;

import com.tave_app_1.senapool.entity.PlantDiary;

import lombok.Data;

@Data
public class PlantDiaryDetailDto {

    private PlantDiaryInfoDto plantDiaryInfoDto;

    public PlantDiaryDetailDto(Long userPK, PlantDiary plantDiary) {
        plantDiaryInfoDto = new PlantDiaryInfoDto(userPK,plantDiary);
    }


}

