package com.example.senapool_project

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.senapool_project.databinding.ActivityDiaryFeedDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiaryFeedDetailActivity : AppCompatActivity(){

    lateinit var binding: ActivityDiaryFeedDetailBinding
    lateinit var diaryPK:String
    lateinit var userPK:String
    lateinit var token:String
    var likes: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiaryFeedDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPK = intent.getStringExtra("userPK").toString()
        diaryPK = intent.getStringExtra("plantDiaryPK").toString()
        token = intent.getStringExtra("token").toString()
        Log.d("DIARYDETIAL/create",diaryPK)

        binding.diaryFeedDetailContentTv.movementMethod = ScrollingMovementMethod.getInstance()

        binding.diaryFeedDetailArrowIv.setOnClickListener { finish() }

        binding.diaryFeedDetailHeartIv.setOnClickListener {
            if (likes==false) {
                binding.diaryFeedDetailHeartIv.setImageResource(R.drawable.heart)
                getLike()

            } else {
                binding.diaryFeedDetailHeartIv.setImageResource(R.drawable.empty_heart)
                getUnlike()
            }
            likes = !likes
        }

    }

    override fun onStart() {
        super.onStart()
        getDiary(diaryPK)

    }

    fun getDiary(diaryPK:String){
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.MyPlantDiaryDetail("Bearer "+token,userPK,diaryPK).enqueue(object :
            Callback<MyPlantDiaryDetailResponse> {

            //응답이 왔을 때 처리하는 부분
            override fun onResponse(call: Call<MyPlantDiaryDetailResponse>, response: Response<MyPlantDiaryDetailResponse>) {
                //response의 body안에 서버 개발자가 만든게 들어있음
                Log.d("DIARYDETAIL/SUCCESS", response.toString())
                val resp: MyPlantDiaryDetailResponse = response.body()!!
                Log.d("DIARYDETAIL/SUCCESS", resp.toString())
                when(resp.code){
                    2000->{
                        Glide.with(this@DiaryFeedDetailActivity).load(resp.result.plantDiaryInfoDto.image).into(binding.diaryFeedDetailImgIv)
                        binding.diaryFeedDetailTitleTv.text=resp.result.plantDiaryInfoDto.title
                        binding.diaryFeedDetailContentTv.text=resp.result.plantDiaryInfoDto.content
                        binding.diaryFeedDetailDateTv.text=resp.result.plantDiaryInfoDto.createdAt
                        binding.diaryFeedDetailHeartCountTv.text=resp.result.plantDiaryInfoDto.likesCount.toString()
                        //공개여부 수정하기

                        if (resp.result.plantDiaryInfoDto.likesState==true){
                            binding.diaryFeedDetailHeartIv.setImageResource(R.drawable.heart)
                            likes=true
                        }else{
                            binding.diaryFeedDetailHeartIv.setImageResource(R.drawable.empty_heart)
                            likes=true
                        }


                    }


                    else->{
                        //Toast.makeText(this@MyPlantFragment,resp.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            //네트워크 연결자체가 실패했을 때 실행하는 부분
            override fun onFailure(call: Call<MyPlantDiaryDetailResponse>, t: Throwable) {
                Log.d("DIARYDETAIL/FAILURE", t.message.toString())
            }
        })
        //비동기작업이니까 함수가 잘 실행되었는지 확인차 찍어보기
        Log.d("DIARYDETAIL", "HELLO")

    }

    fun getLike(){
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.Like("Bearer "+token,diaryPK).enqueue(object :
            Callback<LikeResponse> {

            //응답이 왔을 때 처리하는 부분
            override fun onResponse(call: Call<LikeResponse>, response: Response<LikeResponse>) {
                //response의 body안에 서버 개발자가 만든게 들어있음
                Log.d("Like/SUCCESS", response.toString())
                val resp: LikeResponse = response.body()!!
                Log.d("Like/SUCCESS", resp.toString())
                when(resp.code){
                    2000->{
                        binding.diaryFeedDetailHeartCountTv.text = resp.result
                    }


                    else->{
                        //Toast.makeText(this@MyPlantFragment,resp.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            //네트워크 연결자체가 실패했을 때 실행하는 부분
            override fun onFailure(call: Call<LikeResponse>, t: Throwable) {
                Log.d("Like/FAILURE", t.message.toString())
            }
        })
        //비동기작업이니까 함수가 잘 실행되었는지 확인차 찍어보기
        Log.d("Like", "HELLO")
    }

    fun getUnlike(){
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.Unlike("Bearer "+token,diaryPK).enqueue(object :
            Callback<LikeResponse> {

            //응답이 왔을 때 처리하는 부분
            override fun onResponse(call: Call<LikeResponse>, response: Response<LikeResponse>) {
                //response의 body안에 서버 개발자가 만든게 들어있음
                Log.d("UNLike/SUCCESS", response.toString())
                val resp: LikeResponse = response.body()!!
                Log.d("UNLike/SUCCESS", resp.toString())
                when(resp.code){
                    2000->{
                        binding.diaryFeedDetailHeartCountTv.text = resp.result
                    }


                    else->{
                        //Toast.makeText(this@MyPlantFragment,resp.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            //네트워크 연결자체가 실패했을 때 실행하는 부분
            override fun onFailure(call: Call<LikeResponse>, t: Throwable) {
                Log.d("UNLike/FAILURE", t.message.toString())
            }
        })
        //비동기작업이니까 함수가 잘 실행되었는지 확인차 찍어보기
        Log.d("UNLike", "HELLO")
    }

}