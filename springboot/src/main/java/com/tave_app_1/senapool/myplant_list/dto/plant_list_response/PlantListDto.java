package com.tave_app_1.senapool.myplant_list.dto.plant_list_response;

import com.tave_app_1.senapool.entity.MyPlant;
import com.tave_app_1.senapool.util.FileUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PlantListDto {

    private List<PlantDto> plantDtoList;

    public PlantListDto(List<MyPlant> plantList){
        plantDtoList = new ArrayList<>(plantList.size());

        for(MyPlant m : plantList){
            String plantImage = "http://ec2-3-39-104-218.ap-northeast-2.compute.amazonaws.com:8080/images/plant/" + m.getPlantImage();

            plantDtoList.add(new PlantDto(m.getPlantPK(), m.getPlantName(), m.getPlantType(), m.getWaterPeriod(), m.getStartDay(), plantImage));
        }
    }
}
