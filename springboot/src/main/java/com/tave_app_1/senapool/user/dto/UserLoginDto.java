package com.tave_app_1.senapool.user.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
public class UserLoginDto {

    @NotNull
    @Size(min = 3, max = 50)
    private String userId;

    @NotNull
    @Size(min = 3, max = 50)
    private String password;

}
