package com.tave_app_1.senapool.weather.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LocationRequestDto {

    @NotBlank
    private int nx;

    @NotBlank
    private int ny;
}
