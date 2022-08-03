package com.example.senapool_project

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import java.io.File

data class User_Info(
    var userPK: String?="",
    var token: String?=""
)

data class VerifySendEmail(
    @SerializedName(value = "email") var email: String? =""
)

data class VerifyCodeConfirm(
    @SerializedName(value="code") var code: String?=""
)

data class Login(
    @SerializedName(value = "userId") var userId:String?="",
    @SerializedName(value = "password") var password: String?=""
)



data class MyPlantListResult(
    @SerializedName("userPK") var userPK: String?="",
    @SerializedName("userId") var userId: String?="",
    @SerializedName("userImage") var userImage: String?="",
    @SerializedName("plantListDto") var plantListDto: plantDtoList
)

data class plantDtoList(
    @SerializedName("plantDtoList") var plantDtoList: ArrayList<MyPlantList>
)

data class MyPlantList(
    @SerializedName("plantPK") var plantPK: String?="",
    @SerializedName("plantName") var plantName: String?="",
    @SerializedName("plantImage") var plantImage: String?="",
)

data class Password(
    @SerializedName("password") var password: String?=""
)

