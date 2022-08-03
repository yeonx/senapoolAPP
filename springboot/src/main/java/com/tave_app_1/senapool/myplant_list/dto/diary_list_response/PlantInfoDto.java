package com.tave_app_1.senapool.myplant_list.dto.diary_list_response;

import com.tave_app_1.senapool.entity.BaseTime;
import com.tave_app_1.senapool.entity.MyPlant;
import com.tave_app_1.senapool.util.FileUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;

@Slf4j
@Data
public class PlantInfoDto extends BaseTime {

    private Long plantPK;

    private String plantImage;

    private String plantName;

    private String plantType;

    private LocalDate lastWater;

    private Integer waterPeriod;

    // D+000
    private Long period;

    /*
    추후 빌더로 변환
     */
    public PlantInfoDto(MyPlant myPlant) {
        this.plantPK = myPlant.getPlantPK();
        this.plantName = myPlant.getPlantName();
        this.plantType = myPlant.getPlantType();
        this.lastWater = myPlant.getLastWater();
        this.waterPeriod = myPlant.getWaterPeriod();
        this.period = ChronoUnit.DAYS.between(myPlant.getStartDay(), LocalDateTime.now());

        if(myPlant.getPlantImage().isBlank()) this.plantImage = "Default.png";
        else this.plantImage = "http://ec2-3-39-104-218.ap-northeast-2.compute.amazonaws.com:8080/images/plant/" + myPlant.getPlantImage();

    }
}
