package com.tave_app_1.senapool.user.dto;

import com.tave_app_1.senapool.entity.User;
import lombok.Data;

@Data
public class ResponseLoginUserDto {

    private String token;
    private Long userPk;
    private String userId;
    private String email;
    private String userImageUrl;

    public ResponseLoginUserDto(String token, Long userPk, String userId, String email, String userImageUrl) {
        this.token = token;
        this.userPk = userPk;
        this.userId = userId;
        this.email = email;
        this.userImageUrl = userImageUrl;
    }
}
