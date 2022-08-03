package com.tave_app_1.senapool.weather.dto;

import lombok.Data;

@Data
public class WeatherResponseDto {

    private WeatherHeaderDto header;

    private WeatherBodyDto   body;
}
