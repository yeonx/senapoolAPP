package com.tave_app_1.senapool.myplant_list.dto.diary_list_response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DiaryPrevDto {

    private Long diaryPK;

    private String title;

    private String content;

    private String image;

    private Boolean publish;

    // 아직 test 안해봄
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate createdAt;
}
