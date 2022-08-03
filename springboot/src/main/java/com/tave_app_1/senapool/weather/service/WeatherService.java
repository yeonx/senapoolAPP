package com.tave_app_1.senapool.weather.service;

import com.tave_app_1.senapool.weather.dto.LocationRequestDto;
import com.tave_app_1.senapool.weather.dto.WeatherApiResponseDto;
import com.tave_app_1.senapool.weather.dto.WeatherDto;
import com.tave_app_1.senapool.weather.dto.WeatherItemDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
public class WeatherService {

    @Transactional
    public WeatherDto getWeather(LocationRequestDto locationRequestDto) throws UnsupportedEncodingException, URISyntaxException {

        String baseDate = getBaseDate();
        String baseTime = getBaseTime();

        ResponseEntity<WeatherApiResponseDto> response = requestWeatherApi(locationRequestDto, baseDate, baseTime); // 데이터가 하나도 없는 경우 새로 생성
        List<WeatherItemDto> weatherItemList = response.getBody()
                .getResponse()
                .getBody()
                .getItems()
                .getItem();

        WeatherDto weatherDto = new WeatherDto();
        log.info("응답 정보 파싱 시작");
        parseResponse(weatherItemList, weatherDto, baseTime);
        log.info("응답 정보 파싱 끝");

        /*
        location 정보 Dto에 넣는 로직 추가
         */

        return weatherDto;
    }

    private WeatherDto parseResponse(List<WeatherItemDto> weatherItemList, WeatherDto weatherDto, String baseTime) {
        for ( WeatherItemDto item : weatherItemList) {
            if(item.getFcstTime().equals(getFcstTime(baseTime))){

                String category = item.getCategory();

                if(category.equals("PTY")){
                    switch (Integer.parseInt(item.getFcstValue())) {
                        case 0:
                            weatherDto.setPtyCode(0);
                            break;
                        case 1:
                        case 2:
                        case 5:
                        case 6:
                            weatherDto.setPtyCode(1);
                            break;
                        case 3:
                        case 7:
                            weatherDto.setPtyCode(2);
                            break;
                    }
                } else if (category.equals("SKY")) {
                    switch (Integer.parseInt(item.getFcstValue())) {
                        case 1:
                            weatherDto.setSkyCode(0);
                            break;
                        case 3:
                            weatherDto.setSkyCode(1);
                            break;
                        case 4:
                            weatherDto.setSkyCode(2);
                            break;
                    }
                } else if(category.equals("T1H")) weatherDto.setTemp(Double.parseDouble(item.getFcstValue()));
            }
        }

        return weatherDto;
    }

    public ResponseEntity<WeatherApiResponseDto> requestWeatherApi(LocationRequestDto locationRequestDto, String baseDate, String baseTime) throws UnsupportedEncodingException, URISyntaxException
    {
        String url = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst";
        String serviceKey = "vBqsw3r95baYVfRNf4/bdTs38YjNrGZgm5oCjbdXzcMprg3uvsRV6Qf20mDT8sRDMSF20TNrQY82plTcXylaog==";
        String encodedServiceKey = URLEncoder.encode(serviceKey, "UTF-8");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "JSON", Charset.forName("UTF-8")));

        StringBuilder builder = new StringBuilder(url);
        builder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + encodedServiceKey);
        builder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
        builder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8"));
        builder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8"));
        builder.append("&" + URLEncoder.encode("base_date", "UTF-8") + "=" + URLEncoder.encode(getBaseDate(), "UTF-8"));
        builder.append("&" + URLEncoder.encode("base_time", "UTF-8") + "=" + URLEncoder.encode(getBaseTime(), "UTF-8"));
        builder.append("&" + URLEncoder.encode("nx", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(locationRequestDto.getNx()), "UTF-8"));
        builder.append("&" + URLEncoder.encode("ny", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(locationRequestDto.getNy()), "UTF-8"));
        URI uri = new URI(builder.toString());

        log.info(getBaseDate() + "/" + getBaseTime());
        log.info("api 요청 시작");
        ResponseEntity<WeatherApiResponseDto> response = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<String>(headers), WeatherApiResponseDto.class);
        log.info("api 응답 받음");
        return response;
    }

    public String getBaseDate() {
        // 현재 날짜 구하기
        LocalDate now = LocalDate.now();
        // 포맷 정의 후 적용
        return now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    public String getBaseTime() {
        // 현재 날짜 구하기
        LocalTime now = LocalTime.now();
        // 포맷 정의하기
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
        // 포맷 적용하기
        String formatedNow = now.format(formatter);
        // 1시간 이전 시간 구하기
        int prev = Integer.parseInt(formatedNow);
        if(prev % 100 >= 30) return now.format(DateTimeFormatter.ofPattern("HH30"));
        else {
            prev = prev / 100 * 100 - 100 + 30;
            if (prev < 0) prev = 2330;
            return String.valueOf(prev);
        }
    }

    public String getFcstTime(String baseTime) {
        return String.valueOf(Integer.parseInt(baseTime) + 70);
    }
}
