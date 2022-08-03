package com.tave_app_1.senapool.weather.dto;

import lombok.Data;

@Data
public class WeatherBodyDto {

    private String dataType;

    private WeatherItemsDto items;

    private int numOfRows;

    private int pageNo;

    private int totalCount;
}
