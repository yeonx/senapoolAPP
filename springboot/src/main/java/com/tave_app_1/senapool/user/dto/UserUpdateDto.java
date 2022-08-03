package com.tave_app_1.senapool.user.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Id;

@Data
public class UserUpdateDto {

    private String userId;

    private MultipartFile userImage;


}
