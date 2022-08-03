package com.example.senapool_project

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.senapool_project.databinding.FragmentMyPlantDiaryListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate


class MyPlantDiaryListFragment : Fragment() {

    lateinit var binding: FragmentMyPlantDiaryListBinding
    lateinit var diaryRVAdapter: DiaryRVAdapter

    lateinit var token: String
    lateinit var userPK: String
    lateinit var plantPK: String

    lateinit var plantName: String
    @RequiresApi(Build.VERSION_CODES.O)
    val today: LocalDate = LocalDate.now()
    lateinit var lastWaterday: String
    var cycle:Int = 0

    private val CHANNEL_ID = "testChannel01"   // Channel for notification
    private var notificationManager: NotificationManager? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPlantDiaryListBinding.inflate(inflater,container,false)

        token = arguments?.getString("token").toString()
        userPK = arguments?.getString("userPK").toString()
        plantPK = arguments?.getString("plantPK").toString()
        Log.d("DIARYLIST",token+' '+userPK+' '+plantPK)



        binding.myPlantDiaryListNewDiaryWriteTv.setOnClickListener {
            val intent = Intent(activity,MyPlantDiaryWriteActivity::class.java)
            intent.putExtra("userPK",userPK) //데이터 넣기
            intent.putExtra("plantPK",plantPK) //데이터 넣기
            intent.putExtra("token",token) //데이터 넣기
            startActivity(intent)

        }

        createNotificationChannel(CHANNEL_ID, "testChannel", "this is a test Channel")


        return binding.root
    }

    override fun onStart() {
        super.onStart()

        getDiary(userPK)

    }

    private fun initRecyclerView(result: diaryPrevListDto) {
        diaryRVAdapter = DiaryRVAdapter(requireContext(), result)

        binding.myPlantDiaryListRv.adapter = diaryRVAdapter
    }

    fun getDiary(userPK:String){
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.MyPlantDiaryList("Bearer "+token,userPK,plantPK).enqueue(object : Callback<MyPlantDiaryListResponse> {

            //응답이 왔을 때 처리하는 부분
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(call: Call<MyPlantDiaryListResponse>, response: Response<MyPlantDiaryListResponse>) {
                //response의 body안에 서버 개발자가 만든게 들어있음
                Log.d("DIARYLIST/SUCCESS", response.toString())
                val resp: MyPlantDiaryListResponse = response.body()!!
                Log.d("DIARYLIST/SUCCESS", resp.toString())
                when(resp.code){
                    2000->{
                        initRecyclerView(resp.result.diaryPrevListDto)
                        Glide.with(context!!).load(resp.result.plantInfoDto.plantImage).into(binding.myPlantDiaryListPlantIv)
                        binding.myPlantDiaryListPlantNameTv.text=resp.result.plantInfoDto.plantName
                        binding.myPlantDiaryListPlantTypeTv.text=resp.result.plantInfoDto.plantType
                        binding.myPlantDiaryListHowLongDateTv.text= "D+"+resp.result.plantInfoDto.period.toString()
                        binding.myPlantDiaryListWateringBtn.text="\""+resp.result.plantInfoDto.waterPeriod.toString()+"일에 한번\n물을 줘야해요\""

                        plantName=resp.result.plantInfoDto.plantName.toString()
                        cycle= resp.result.plantInfoDto.period!!
                        lastWaterday=resp.result.plantInfoDto.lastWater

                        Calculation_date(today,lastWaterday,cycle)

                        binding.myPlantDiaryListRv.adapter = diaryRVAdapter
                        binding.myPlantDiaryListRv.layoutManager=LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

                        Log.d("MYPLANT/check","실행된다")
                        diaryRVAdapter.setMyItemClickListener(object : DiaryRVAdapter.MyItemClickListener {
                                override fun onItemClick(diaryPK: String?) {
                                        val intent = Intent(activity,MyPlantDiaryDetailActivity::class.java)
                                        intent.putExtra("userPK",userPK)
                                        intent.putExtra("diaryPK",diaryPK) //데이터 넣기
                                        intent.putExtra("token",token) //데이터 넣기
                                        startActivity(intent)


//            override fun onRemoveSong(plantPK: Int) {
//                TODO("Not yet implemented")
//            }
                            }
                        })

                        }


                    else->{
                        //Toast.makeText(this@MyPlantFragment,resp.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            //네트워크 연결자체가 실패했을 때 실행하는 부분
            override fun onFailure(call: Call<MyPlantDiaryListResponse>, t: Throwable) {
                Log.d("DIARYLIST/FAILURE", t.message.toString())
            }
        })
        //비동기작업이니까 함수가 잘 실행되었는지 확인차 찍어보기
        Log.d("DIARYLIST", "HELLO")

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun displayNotification() {
        val notificationId = 45

        val notification = Notification.Builder(applicationContext@activity, CHANNEL_ID)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle("세나풀")
            .setContentText("오늘은 "+plantName+" 물 주는 날입니다!")
            .build()

        notificationManager?.notify(notificationId, notification)
    }

    private fun createNotificationChannel(channelId: String, name: String, channelDescription: String) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT // set importance
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = channelDescription
            }
            // Register the channel with the system
            notificationManager = requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager?.createNotificationChannel(channel)
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun Calculation_date(today:LocalDate, lastWaterday:String,cycle:Int){
        val Y2:Int = today.year
        val M2:Int = today.monthValue
        val D2:Int = today.dayOfMonth
        val Y1:Int = lastWaterday.substring(0,4).toInt()
        val M1:Int = lastWaterday.substring(5,7).toInt()
        val D1:Int = lastWaterday.substring(8,10).toInt()

        var result_date:Int

        var month= mutableListOf<Int>(0,31,28,31,30,31,30,31,31,30,31,30,31)
        if (Y1%4==0 && Y1%100!=0 || Y1%400==0){
            month[2]=29
        }
        if (Y1==Y2){
            if(M1==M2){
                result_date = D2-D1
            }else{
                Log.d("1",(month[M1]-D1).toString())
                Log.d("2",(month.subList(M1+1,M2).sum()).toString())
                result_date = month[M1]-D1 + month.subList(M1+1,M2).sum() + D2
            }
        }else{
            result_date = month[M1]-D1 + month.subList(M1+1,-1).sum() + (Y2-Y1-1)*month.sum() + month.subList(1,M2).sum() + D2
        }

        Log.d("result_date",result_date.toString())
        if (result_date%cycle==0){
            displayNotification()
        }

    }

}