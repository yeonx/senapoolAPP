package com.tave_app_1.senapool.myplant_list.controller;

import com.tave_app_1.senapool.entity.User;
import com.tave_app_1.senapool.exception.ErrorCode;
import com.tave_app_1.senapool.exception.ErrorResponse;
import com.tave_app_1.senapool.myplant_list.dto.diary_list_response.DiaryListResponseDto;
import com.tave_app_1.senapool.myplant_list.dto.plant_register_request.PlantRegisterRequestDto;
import com.tave_app_1.senapool.myplant_list.dto.plant_list_response.PlantListResponseDto;
import com.tave_app_1.senapool.myplant_list.dto.plant_update_request.PlantUpdateRequestDto;
import com.tave_app_1.senapool.myplant_list.service.MyPlantService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MyPlantController {

    private final MyPlantService myPlantService;

    /**
     * 유저 식물 리스트
     * [GET] /myplant-list/{userPK}
     * 작성자 : 장동호
     * 수정일 : 2022-07-04
     */
    @ApiOperation(value = "선택한 유저의 '나의 식물 리스트'로 이동", notes = "'임의의 page' -> '나의 식물 리스트'로 이동할 때 유저 및 식물 정보 받아오기")
    @ApiResponses(value = {
            @ApiResponse(code = 2000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 4002, message = "잘못된 요청입니다.")
    })
    @GetMapping(value = "/myplant-list/{userPK}")
    public ErrorResponse<PlantListResponseDto> plantList(@PathVariable("userPK") Long userPK){
        PlantListResponseDto plantListResponseDto = myPlantService.makePlantList(userPK);
        return new ErrorResponse<>(plantListResponseDto);
    }

    /**
     * 내 식물 등록
     * [POST] /myplant-list/{userPK}
     * 작성자 : 장동호
     * 수정일 : 2022-07-04
     */
    @ApiOperation(value = "내 식물 등록", notes = "'나의 식물 리스트'에서 식물 등록(multipart/form-data)")
    @ApiResponses(value = {
            @ApiResponse(code = 2000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 4000, message = "입력값을 확인해주세요."),
            @ApiResponse(code = 4001, message = "토큰이 없거나, 유효하지 않습니다. 로그인을 해주세요."),
            @ApiResponse(code = 4002, message = "잘못된 요청입니다.")
    })
    @PostMapping("/myplant-list/{userPK}")
    public ErrorResponse<?> plantRegister(@PathVariable("userPK") Long userPK,
                                           @Valid PlantRegisterRequestDto plantRegisterRequestDto,
                                           @ApiIgnore Authentication authentication) {

        /*
        Controller 에서 Entity 다루고 있음.
         */
        try {
            // 토큰에서 가져온 user 정보가 존재하지 않을 경우, user.getUserPK()에서 NullPointerException 발생.
            User user = (User) authentication.getPrincipal();

            // 인증 성공
            if (user.getUserPK() == userPK) {
                myPlantService.joinPlant(plantRegisterRequestDto, user);
                return new ErrorResponse<>(ErrorCode.SUCCESS);
            }
            // 인증 실패
            else {
                return new ErrorResponse<>(ErrorCode.INVALID_REQUEST);
            }
        }
        // 유효하지 않은 토큰으로 인한 NullPointerException 처리.
        catch (NullPointerException e) {
            return new ErrorResponse<>(ErrorCode.INVALID_JWT);
        }
    }

    /**
     * 내 식물 수정
     * [PUT] /myplant-list/{userPK}/{plantPK}
     * 작성자 : 장동호
     * 수정일 : 2022-07-04
     */
    @ApiOperation(value = "내 식물 수정", notes = "'나의 식물일기 리스트'에서 식물 수정(multipart/form-data)")
    @ApiResponses(value = {
            @ApiResponse(code = 2000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 4000, message = "입력값을 확인해주세요."),
            @ApiResponse(code = 4001, message = "토큰이 없거나, 유효하지 않습니다. 로그인을 해주세요."),
            @ApiResponse(code = 4002, message = "잘못된 요청입니다.")
    })
    @PutMapping("myplant-list/{userPK}/{plantPK}")
    public ErrorResponse<?> plantUpdate(@PathVariable("userPK") Long userPK,
                                         @PathVariable("plantPK") Long plantPK,
                                         @Valid PlantUpdateRequestDto plantUpdateRequestDto,
                                         @ApiIgnore Authentication authentication){


        try {
            // 토큰에서 가져온 user 정보가 존재하지 않을 경우, user.getUserPK()에서 NullPointerException 발생.
            User user = (User) authentication.getPrincipal();

            // 인증 성공
            if (user.getUserPK() == userPK) {
                myPlantService.updatePlant(plantPK, plantUpdateRequestDto);
                return new ErrorResponse<>(ErrorCode.SUCCESS);
            }
            // 인증 실패
            else {
                return new ErrorResponse<>(ErrorCode.INVALID_REQUEST);
            }
        }
        // 유효하지 않은 토큰으로 인한 NullPointerException 처리.
        catch (Exception e) {
            return new ErrorResponse<>(ErrorCode.INVALID_JWT);
        }
    }

    /**
     * 내 식물 삭제
     * [DELETE] /myplant-list/{userPK}/{plantPK}
     * 작성자 : 장동호
     * 수정일 : 2022-07-04
     */
    @ApiOperation(value = "내 식물 삭제", notes = "'나의 식물일기 리스트'에서 식물 삭제")
    @ApiResponses(value = {
            @ApiResponse(code = 2000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 4000, message = "입력값을 확인해주세요."),
            @ApiResponse(code = 4001, message = "토큰이 없거나, 유효하지 않습니다. 로그인을 해주세요."),
            @ApiResponse(code = 4002, message = "잘못된 요청입니다.")
    })
    @DeleteMapping("myplant-list/{userPK}/{plantPK}")
    public ErrorResponse<?> plantDelete(@PathVariable("userPK") Long userPK,
                                         @PathVariable("plantPK") Long plantPK,
                                         @ApiIgnore Authentication authentication){

        try {
            // 토큰에서 가져온 user 정보가 존재하지 않을 경우, user.getUserPK()에서 NullPointerException 발생.
            User user = (User) authentication.getPrincipal();

            // 인증 성공
            if (user.getUserPK() == userPK) {
                myPlantService.deletePlant(plantPK);
                return new ErrorResponse<>(ErrorCode.SUCCESS);
            }
            // 인증 실패
            else {
                return new ErrorResponse<>(ErrorCode.INVALID_REQUEST);
            }
        }
        // 유효하지 않은 토큰으로 인한 NullPointerException 처리.
        catch (Exception e) {
            return new ErrorResponse<>(ErrorCode.INVALID_JWT);
        }
    }

    /**
     * 선택한 식물의 식물일기 리스트로 이동
     * [GET] /myplant-list/{userPK}/{plantPK}
     * 작성자 : 장동호
     * 수정일 : 2022-07-04
     */
    @ApiOperation(value = "선택한 식물의 '식물일기 리스트'로 이동", notes = "'나의 식물 리스트' -> '나의 식물일기 리스트'로 이동할 때 유저 및 식물 정보")
    @ApiResponses(value = {
            @ApiResponse(code = 2000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 4001, message = "토큰이 없거나, 유효하지 않습니다. 로그인을 해주세요."),
            @ApiResponse(code = 4002, message = "잘못된 요청입니다.")
    })
    @GetMapping("myplant-list/{userPK}/{plantPK}")
    public ErrorResponse<DiaryListResponseDto> diaryList(@PathVariable("userPK") Long userPK,
                                       @PathVariable("plantPK") Long plantPK,
                                      @ApiIgnore Authentication authentication){

        try {
            // 토큰에서 가져온 user 정보가 존재하지 않을 경우, user.getUserPK()에서 NullPointerException 발생.
            User user = (User) authentication.getPrincipal();

            DiaryListResponseDto diaryListResponseDto;
            // 자신의 식물일기 리스트로 이동
            if (user.getUserPK() == userPK) diaryListResponseDto = myPlantService.makeDiaryList(plantPK, true);
                // 다른 사람의 식물일기 리스트로 이동
            else diaryListResponseDto = myPlantService.makeDiaryList(plantPK, false);

            return new ErrorResponse<>(diaryListResponseDto);
        }
        // 유효하지 않은 토큰으로 인한 NullPointerException 처리.
        catch (Exception e) {
            return new ErrorResponse<>(ErrorCode.INVALID_JWT);
        }
    }

    @GetMapping("test")
    public String get(HttpServletRequest request,@RequestParam("name") String name) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("Get messageBody={}", messageBody);
        //log.info("Get url={}", url);
        log.info("Get name={}", name);
        return "ok";
    }

    @PostMapping("test")
    public String post(HttpServletRequest request) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("Post messageBody={}", messageBody);
        //log.info("Get url={}", url);
        //log.info("Post name={}", name);
        return "ok";
    }
}