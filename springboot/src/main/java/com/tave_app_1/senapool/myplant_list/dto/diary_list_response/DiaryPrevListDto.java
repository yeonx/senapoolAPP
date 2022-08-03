package com.tave_app_1.senapool.myplant_list.dto.diary_list_response;

import com.tave_app_1.senapool.entity.PlantDiary;
import com.tave_app_1.senapool.util.FileUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
public class DiaryPrevListDto {

    private List<DiaryPrevDto> diaryPrevDtoList;

    public DiaryPrevListDto(List<PlantDiary> plantDiaryList, Boolean myPage) {
        diaryPrevDtoList = new ArrayList<>(plantDiaryList.size());

        for (PlantDiary m : plantDiaryList) {
            // 다른 사람의 일기 목록을 볼 때
            if(myPage == false) {
                // 일기장의 publish 속성이 true 되어 있으면 가져온다.
                if (m.getPublish() == true) {
                    String diaryImage;
                    if (m.getDiaryImage().isBlank()) diaryImage = "Default.png";
                    else diaryImage = "http://ec2-3-39-104-218.ap-northeast-2.compute.amazonaws.com:8080/images/diary/" + m.getDiaryImage();

                    diaryPrevDtoList.add(new DiaryPrevDto(m.getPlantDiaryPK(), m.getTitle(),m.getContent(), diaryImage, m.getPublish(), m.getCreateDate()));
                }
            }
            // 내 일기 목록을 볼 때는 목록 전부를 가져온다.
            else {
                String diaryImage;
                if (m.getDiaryImage().isBlank()) diaryImage = "Default.png";
                else diaryImage = "http://ec2-3-39-104-218.ap-northeast-2.compute.amazonaws.com:8080/images/diary/" + m.getDiaryImage();

                diaryPrevDtoList.add(new DiaryPrevDto(m.getPlantDiaryPK(), m.getTitle(), m.getContent(),diaryImage, m.getPublish(), m.getCreateDate()));
            }
        }
    }
}
