package com.tave_app_1.senapool.weather.dto;

import lombok.Data;

@Data
public class WeatherDto {

    /*
    // 발표 일자
    private String baseDate;

    // 발표 시각
    private String baseTime;

    // 자료구분코드
    private String category;

    // 예보지점 X좌표
    private String nx;

    // 예보지점 Y좌표
    private String ny;

    // 실황 값
    private Double obsrValue;
    */

    // 하늘상태 - 맑음(0), 구름많음(1), 흐림(2)
    private int skyCode;

    // 강수형태 - 없음(0), 비(1), 눈(2)
    private int ptyCode;

    // 기온
    private Double temp;

    private String location;
}
