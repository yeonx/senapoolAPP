package com.tave_app_1.senapool.likes.controller;

import com.tave_app_1.senapool.entity.User;
import com.tave_app_1.senapool.exception.ErrorCode;
import com.tave_app_1.senapool.exception.ErrorResponse;
import com.tave_app_1.senapool.likes.service.LikesService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LikesController {
    private final LikesService likesService;

    @ApiOperation(value = "좋아요 등록", notes = "'식물일기 피드'와 '식물 일기 피드 상세' 페이지에서 사용하게 될 좋아요")
    @PutMapping("/plant-diary/{diaryPK}/like")
    public ErrorResponse<?> likes(@PathVariable("diaryPK") long diaryPK, Authentication authentication){
        try {
            User user = (User) authentication.getPrincipal();
            return new ErrorResponse<>(likesService.likes(diaryPK, user.getUserPK()));
        } catch (Exception e) {
            return new ErrorResponse<>(ErrorCode.INVALID_REQUEST);
        }
    }

    @ApiOperation(value = "좋아요 취소", notes = "'식물일기 피드'와 '식물 일기 피드 상세' 페이지에서 사용하게 될 좋아요")
    @DeleteMapping("/plant-diary/{diaryPK}/unlike")
    public ErrorResponse<?> unLikes(@PathVariable("diaryPK") long diaryPK, Authentication authentication){
        try {
            User user = (User) authentication.getPrincipal();
            return new ErrorResponse<>(likesService.unLikes(diaryPK, user.getUserPK()));
        } catch (Exception e) {
            return new ErrorResponse<>(ErrorCode.INVALID_REQUEST);
        }
    }
}
