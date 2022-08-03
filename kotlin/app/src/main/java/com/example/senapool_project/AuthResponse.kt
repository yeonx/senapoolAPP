package com.example.senapool_project

import com.google.gson.annotations.SerializedName
import org.json.JSONObject

data class AuthResponse(
    @SerializedName(value = "isSuccess") val isSuccess:Boolean,
    @SerializedName(value = "code") val code:Int,
    @SerializedName(value = "message") val message:String
    )

data class VerifySendResponse(
    @SerializedName(value = "isSuccess") val isSuccess:Boolean,
    @SerializedName(value = "code") val code:Int,
    @SerializedName(value = "message") val message:String
)

data class VerifyConfirmResponse(
    @SerializedName(value = "isSuccess") val isSuccess:Boolean,
    @SerializedName(value = "code") val code:Int,
    @SerializedName(value = "message") val message:String
)

data class LoginResponse(
    @SerializedName(value = "isSuccess") val isSuccess:Boolean,
    @SerializedName(value = "code") val code:Int,
    @SerializedName(value = "message") val message:String,
    @SerializedName(value = "result") val result: Result
)
data class Result(
    @SerializedName("token") var token: String?="",
    @SerializedName("userPk") var userPk: String?="",
    @SerializedName("userId") var userId: String?="",
    @SerializedName("email") var email: String?="",
    @SerializedName("userImageUrl") var userImageUrl: String?="",
)

data class MyPlantListResponse(
    @SerializedName(value = "isSuccess") val isSuccess:Boolean,
    @SerializedName(value = "code") val code:Int,
    @SerializedName(value = "message") val message:String,
    @SerializedName(value = "result") val result: MyPlantListResult
)

data class MyPlantEnrollResponse(
    @SerializedName(value = "isSuccess") val isSuccess:Boolean,
    @SerializedName(value = "code") val code:Int,
    @SerializedName(value = "message") val message:String
)

data class MyPlantDiaryListResponse(
    @SerializedName(value = "isSuccess") val isSuccess:Boolean,
    @SerializedName(value = "code") val code:Int,
    @SerializedName(value = "message") val message:String,
    @SerializedName(value = "result") val result: MyPlantDiaryList
)

data class MyPlantDiaryList(
    @SerializedName("plantInfoDto") val plantInfoDto: plantInfoDto,
    @SerializedName("diaryPrevListDto") val diaryPrevListDto: diaryPrevListDto,
)

data class plantInfoDto(
    @SerializedName("plantPK") var plantPK: String?="",
    @SerializedName("plantImage") var plantImage: String?="",
    @SerializedName("plantName") var plantName: String?="",
    @SerializedName("plantType") var plantType: String?="",
    @SerializedName("waterPeriod") var waterPeriod: Int?=null,
    @SerializedName("period") var period: Int?=null,
    @SerializedName("lastWater") var lastWater: String=""
)

data class diaryPrevListDto(
    @SerializedName("diaryPrevDtoList") var diaryPrevDtoList: ArrayList<DiaryList>
)

data class DiaryList(
    @SerializedName("content") var content:String="",
    @SerializedName("title") var title:String="",
    @SerializedName("publish") var publish:Boolean,
    @SerializedName("image") var image:String="",
    @SerializedName("diaryPK") var diaryPK:String="",
    @SerializedName("createdAt") var createdAt:String="",
)


data class MyPlantDiaryDetailResponse(
    @SerializedName(value = "isSuccess") val isSuccess:Boolean,
    @SerializedName(value = "code") val code:Int,
    @SerializedName(value = "message") val message:String,
    @SerializedName(value = "result") val result: plantDiaryInfoDto
)

data class plantDiaryInfoDto(
    @SerializedName("plantDiaryInfoDto") var plantDiaryInfoDto:DiaryDetail
)

data class DiaryDetail(
    @SerializedName("content") var content:String="",
    @SerializedName("title") var title:String="",
    @SerializedName("publish") var publish:Boolean,
    @SerializedName("diaryImage") var image:String="",
    @SerializedName("diaryPK") var diaryPK:String="",
    @SerializedName("createDate") var createdAt:String="",
    @SerializedName("likesCount") var likesCount:Int?=null,
    @SerializedName("likesState") var likesState:Boolean=false
)

data class DiaryFeedResponse(
    @SerializedName(value = "isSuccess") val isSuccess:Boolean,
    @SerializedName(value = "code") val code:Int,
    @SerializedName(value = "message") val message:String,
    @SerializedName(value = "result") val result: Content
)

data class Content(
    @SerializedName("content") var content: ArrayList<Feed>,
)

data class Feed(
    @SerializedName("createDate") var createdAt:String="",
    @SerializedName("plantDiaryPK") var plantDiaryPK:String="",
    @SerializedName("title") var title:String="",
    @SerializedName("content") var content:String="",
    @SerializedName("diaryImage") var diaryImage:String="",
    @SerializedName("publish") var publish:Boolean,
    @SerializedName("likesCount") var likesCount:Int?=null,
    @SerializedName("likesState") var likesState:Boolean,
    @SerializedName("diaryImageUrl") var diaryImageUrl:String="",
    @SerializedName("userID") var userID:String="",
    @SerializedName("userImage") var userImage:String=""
)

data class MyPlantDeleteResponse(
    @SerializedName(value = "isSuccess") val isSuccess:Boolean,
    @SerializedName(value = "code") val code:Int,
    @SerializedName(value = "message") val message:String,
)

data class LikeResponse(
    @SerializedName(value = "isSuccess") val isSuccess:Boolean,
    @SerializedName(value = "code") val code:Int,
    @SerializedName(value = "message") val message:String,
    @SerializedName(value = "result") val result:String,
)

data class QuitResponse(
    @SerializedName(value = "isSuccess") val isSuccess:Boolean,
    @SerializedName(value = "code") val code:Int,
    @SerializedName(value = "message") val message:String
)


