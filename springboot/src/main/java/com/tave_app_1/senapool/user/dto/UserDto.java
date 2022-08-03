package com.tave_app_1.senapool.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import com.tave_app_1.senapool.entity.User;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 50)
    private String userId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 50)
    private String password;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 50)
    private String email;

    private MultipartFile userImage;
}