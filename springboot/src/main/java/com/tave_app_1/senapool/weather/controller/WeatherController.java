package com.tave_app_1.senapool.weather.controller;

import com.tave_app_1.senapool.weather.dto.LocationRequestDto;
import com.tave_app_1.senapool.weather.dto.WeatherDto;
import com.tave_app_1.senapool.weather.service.WeatherService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    /**
     * 날씨 정보
     * [GET] /weather
     * 작성자 : 장동호
     * 작성일 :
     */
    //skyCode : 맑음(0), 구름많음(1), 흐림(2)
    //ptyCode : 없음(0), 비(1), 눈(2)
    @ApiOperation(value = "날씨 정보", notes = "'나의 식물 리스트'에 날씨 정보")
    @PostMapping("/weather")
    public WeatherDto weatherCond(LocationRequestDto locationRequestDto) throws IOException, URISyntaxException {
        return weatherService.getWeather(locationRequestDto);
    }
}
