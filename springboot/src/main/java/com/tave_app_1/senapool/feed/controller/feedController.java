package com.tave_app_1.senapool.feed.controller;

import com.tave_app_1.senapool.entity.PlantDiary;
import com.tave_app_1.senapool.entity.User;
import com.tave_app_1.senapool.exception.ErrorCode;
import com.tave_app_1.senapool.exception.ErrorResponse;
import com.tave_app_1.senapool.feed.service.feedService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class feedController {
    private final feedService feedService;

    @ApiOperation(value = "식물 일기 불러오기", notes = "'식물일기 피드'에 들어갈 식물 일기들을 불러오는 api")
    @GetMapping("/plant-diary")
    public ErrorResponse<Page<PlantDiary>> mainFeed(Authentication authentication, @PageableDefault(size=200)Pageable pageable){
        try {
            User user  = (User) authentication.getPrincipal();
            return new ErrorResponse<>(feedService.getDiaries(user.getUserPK(), pageable));
        } catch (Exception e) {
            return new ErrorResponse<>(ErrorCode.INVALID_REQUEST);
        }
    }
}
