package com.tave_app_1.senapool.user.controller;

import com.tave_app_1.senapool.entity.User;
import com.tave_app_1.senapool.exception.ErrorCode;
import com.tave_app_1.senapool.exception.ErrorResponse;
import com.tave_app_1.senapool.myplant_list.service.MyPlantService;
import com.tave_app_1.senapool.plant_diary.service.PlantDiaryService;
import com.tave_app_1.senapool.user.dto.*;
import com.tave_app_1.senapool.user.service.EmailServiceImpl;
import com.tave_app_1.senapool.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final EmailServiceImpl emailServiceImpl;

    private final PlantDiaryService plantDiaryService;
    private final MyPlantService myPlantService;

    @ApiOperation(
            value = "회원가입",
            notes = "이메일 인증 후 회원가입이 되어야함")
    @ApiResponses(value = {
            @ApiResponse(code = 2000, message = "요청 성공"),
            @ApiResponse(code = 4000, message = "입력값을 확인해주세요"),
            @ApiResponse(code = 4004, message = "이메일로 가입된 아이디가 존재합니다.")
    }
    )
    @PostMapping("/user/signup") // 회원 가입
    public ErrorResponse<?> userSignUp(UserDto userDto) throws Exception {
        Optional<User> findUser = userService.findUserById(userDto.getUserId());
        try {
            if (findUser.isPresent()) {
                return new ErrorResponse<>(ErrorCode.DUPLICATE_EMAIL);
            }
            userService.join(userDto);
            return new ErrorResponse<>(ErrorCode.SUCCESS);
        } catch (ConstraintViolationException e) {
            return new ErrorResponse<>(ErrorCode.INVALID_INPUT_VALUE);
        }
    }

    @ApiOperation(value = "본인 인증 메일 전송", notes = "코드 전송")
    @ApiResponses(value = {
            @ApiResponse(code = 2000, message = "요청 성공"),
            @ApiResponse(code = 4004, message = "이메일로 가입된 아이디가 존재합니다.")
    }
    )
    @PostMapping("/mailConfirm")
    public ErrorResponse<?> emailConfirm(@RequestBody EmailDto emailDto) throws Exception {

        Optional<User> user = userService.findUserId(emailDto.getEmail());

        if (user.isEmpty()) {
            emailServiceImpl.sendSimpleMessage(emailDto.getEmail());
            return new ErrorResponse<>(ErrorCode.SUCCESS);
        } else {
            return new ErrorResponse<>(ErrorCode.DUPLICATE_EMAIL);
        }
    }


    @ApiOperation(
            value = "본인 인증 코드 일치 여부 확인")
    @ApiResponses(value = {
            @ApiResponse(code = 2000, message = "요청 성공"),
            @ApiResponse(code = 4005 , message = "코드가 일치하지 않습니다.")
    }
    )
    @PostMapping("/verifyCode")
    public ErrorResponse<?> verifyCode(@RequestBody VerifyCodeDto verifyCodeDto) {
        if(emailServiceImpl.ePw.equals(verifyCodeDto.getCode())) {
            return new ErrorResponse<>(ErrorCode.SUCCESS);
        }
        else
            return new ErrorResponse<>(ErrorCode.NOT_MATCH_CODE);
    }

    @ApiOperation(value = "로그인", notes = "유저 id로 로그인")
    @ApiResponses(value = {
            @ApiResponse(code = 2000, message = "요청 성공"),
            @ApiResponse(code = 4003, message = "아이디와 비밀번호가 일치하지 않습니다.")
    }
    )
    @PostMapping("/user/login")
    public ErrorResponse<?> login(@RequestBody UserLoginDto userLoginDTO) throws NoSuchElementException {

        try {
            ResponseLoginUserDto user = userService.login(userLoginDTO);
            return new ErrorResponse<>(user);
        } catch (Exception e) {
            return new ErrorResponse<>(ErrorCode.ACCESS_DENIED_LOGIN);
        }
    }

    @ApiOperation(value = "회원 정보 수정", notes = "토큰 헤더에 넣어서 요청")
    @ApiResponses(value = {
            @ApiResponse(code = 2000, message = "요청 성공"),
    }
    )
    @PatchMapping("/user/update")
    public ErrorResponse<?> userUpdate(Authentication authentication, UserUpdateDto userUpdateDto) throws IOException {
        try {
            User user = (User) authentication.getPrincipal();
            userService.userInfoUpdate(user, userUpdateDto);
            return new ErrorResponse<>(ErrorCode.SUCCESS);

        } catch (ConstraintViolationException e) {
            return new ErrorResponse<>(ErrorCode.INVALID_INPUT_VALUE);
        } catch (Exception e) {
            return new ErrorResponse<>(ErrorCode.DUPLICATE_ID);
        }
    }

    @ApiOperation(value = "임시 비밀번호 발급", notes = "이메일에 전송")
    @ApiResponses(value = {
            @ApiResponse(code = 2000, message = "요청 성공"),
            @ApiResponse(code = 4006, message = "이메일로 가입된 아이디가 존재하지 않습니다.")
    }
    )
    @PatchMapping("/user/temPassword")
    public ErrorResponse<?> setTemPW(@RequestBody EmailDto emailDto) throws Exception {
        Optional<User> userId = userService.findUserId(emailDto.getEmail());

        if (userId.orElse(null) == null) {
            return new ErrorResponse<>(ErrorCode.NOT_FOUND_ID);
        }

        emailServiceImpl.sendTempPwMessage(emailDto.getEmail());
        userService.setTemPassword(emailDto.getEmail(), emailServiceImpl.tempPw);

        return new ErrorResponse<>(ErrorCode.SUCCESS);
    }

    @ApiOperation(value = "비밀번호 변경", notes = "토큰과 비밀번호 담아서 요청")
    @ApiResponses(value = {
            @ApiResponse(code = 2000, message = "요청 성공"),
            @ApiResponse(code = 4001, message = "토큰이 없거나, 유효하지 않습니다. 로그인을 해주세요.")
    }
    )
    @PatchMapping("/user/changePw")
    public ErrorResponse<?> changePw(Authentication authentication,@RequestBody PasswordDto passwordDto) {
        try {
            User user = (User) authentication.getPrincipal();
            userService.changePassword(user, passwordDto.getPassword());

            return new ErrorResponse<>(ErrorCode.SUCCESS);
        } catch (Exception e) {
            return new ErrorResponse<>(ErrorCode.INVALID_JWT);
        }
    }

    @ApiOperation(value = "아이디 찾기", notes = "이메일로 가입되어 있는 userId 전송")
    @ApiResponses(value = {
            @ApiResponse(code = 2000, message = "요청 성공"),
            @ApiResponse(code = 4006, message = "이메일로 가입된 아이디가 존재하지 않습니다.")
    }
    )
    @GetMapping("/user/findId")
    public ErrorResponse<?> findUserId(@RequestBody EmailDto emailDto) throws Exception {
        Optional<User> user = userService.findUserId(emailDto.getEmail());
        if (user.isEmpty()) {
            return new ErrorResponse<>(ErrorCode.NOT_FOUND_ID);
        } else {
            emailServiceImpl.sendFindUserMessage(user.get().getUserId(),emailDto.getEmail());
            return new ErrorResponse<>(ErrorCode.SUCCESS);
        }
    }
    @ApiOperation(value = "회원 탈퇴", notes = "'설정 페이지'에서 회원 탈퇴 기능")
    @DeleteMapping("/user/delete")
    public ErrorResponse<?> deleteUser(Authentication authentication, @RequestBody UserPasswordDto passwordDto) throws Exception{
        try {
            User user = (User) authentication.getPrincipal();
//            log.info(plantDiaryService.findUserDiaryAll(user).get().getDiaryImage());
//            log.info(myPlantService.findUserPlantAll(user).get().getPlantImage());
            userService.deleteUser(user, passwordDto);
            return new ErrorResponse<>(ErrorCode.SUCCESS);
        } catch (Exception e) {
            return new ErrorResponse<>(ErrorCode.INVALID_REQUEST);
        }
    }
}