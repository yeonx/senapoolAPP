package com.tave_app_1.senapool.plant_diary.dto;

import com.tave_app_1.senapool.entity.MyPlant;
import com.tave_app_1.senapool.entity.PlantDiary;
import com.tave_app_1.senapool.entity.User;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PlantDiaryUpdateDto {

    private MultipartFile file;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private Boolean publish;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createDate;

    public PlantDiary toEntity(String diaryImage, User user,MyPlant myPlant){
        return new PlantDiary(user, myPlant, title, content, publish, createDate, diaryImage);
    }

}
