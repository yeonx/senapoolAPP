package com.tave_app_1.senapool.weather.dto;

import lombok.Data;

@Data
public class WeatherItemDto {

    private String baseDate;

    private String baseTime;

    private String category;

    private String fcstDate;

    private String fcstTime;

    private String fcstValue;

    private String nx;

    private String ny;
}
