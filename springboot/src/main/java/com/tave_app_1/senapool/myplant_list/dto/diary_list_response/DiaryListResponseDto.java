package com.tave_app_1.senapool.myplant_list.dto.diary_list_response;

import com.tave_app_1.senapool.entity.MyPlant;
import lombok.Data;

@Data
public class DiaryListResponseDto {

    private PlantInfoDto plantInfoDto;

    private DiaryPrevListDto diaryPrevListDto;

    public DiaryListResponseDto(MyPlant myPlant, Boolean myPage) {
        plantInfoDto = new PlantInfoDto(myPlant);
        diaryPrevListDto = new DiaryPrevListDto(myPlant.getPlantDiaryList(), myPage);
    }
}
