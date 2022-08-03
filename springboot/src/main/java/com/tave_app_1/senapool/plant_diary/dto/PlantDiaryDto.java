package com.tave_app_1.senapool.plant_diary.dto;


import com.tave_app_1.senapool.entity.PlantDiary;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.PrePersist;
import java.time.LocalDate;
import java.time.LocalDateTime;


//@Slf4j
@Data
@AllArgsConstructor
public class PlantDiaryDto {

    private long plantDiaryPK;

    private String title;

    private String content;

    private String diaryImage;

    private Boolean publish;

    private LocalDate createDate;

//
//    public PlantDiaryDto(PlantDiary plantDiary) {
//        this.plantDiaryPK = plantDiary.getPlantDiaryPK();
//        this.title = plantDiary.getTitle();
//        this.content = plantDiary.getContent();
//        this.publish = plantDiary.getPublish();
//        this.createDate = plantDiary.getCreateDate();
//
//        if(plantDiary.getDiaryImage().isBlank()) this.diaryImage = "Default.png";
//        else this.diaryImage = "http://ec2-3-39-104-218.ap-northeast-2.compute.amazonaws.com:8080/images/diary/" +  plantDiary.getDiaryImage();
//    }

}
