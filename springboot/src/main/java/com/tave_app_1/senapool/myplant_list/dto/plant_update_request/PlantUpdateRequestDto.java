package com.tave_app_1.senapool.myplant_list.dto.plant_update_request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tave_app_1.senapool.entity.MyPlant;
import com.tave_app_1.senapool.entity.User;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
public class PlantUpdateRequestDto {

    private MultipartFile file;

    @NotBlank
    private String plantName;

    @NotBlank
    private String plantType;

    @NotNull
    @Min(value = 1)
    private Integer waterPeriod;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate lastWater;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDay;

    /*
     추가 정보 넣어야함
     */
    public MyPlant toEntity(String plantImageName, User user){
        return new MyPlant(user, plantName, plantType, waterPeriod, lastWater, startDay, plantImageName);
    }

}
