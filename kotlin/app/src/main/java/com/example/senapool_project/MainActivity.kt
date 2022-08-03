package com.example.senapool_project

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavArgument
import com.example.senapool_project.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val token = intent.getStringExtra("token")
        val userPK = intent.getStringExtra("userPK")
        val userId = intent.getStringExtra("userId")
        val userImg = intent.getStringExtra("userImg")

        Log.d("MAIN/INTENT",token+' '+userPK)


        initBottomNavigation(userPK.toString(),token.toString(),userId.toString(),userImg.toString())

//        var fragment2 = MyPlantFragment()
//        var bundle = Bundle()
//        //bundle.putString("token",token)
//        bundle.putString("userPK",userPK)
//        fragment2.arguments = bundle //fragment의 arguments에 데이터를 담은 bundle을 넘겨줌
//
//        supportFragmentManager!!.beginTransaction()
//            .replace(R.id.main_frm, fragment2)
//            .commit()
    }

    private fun initBottomNavigation(userPK:String,token:String,userId:String,userImg:String) {

        val plantFragment = MyPlantFragment()
        var bundle = Bundle()
        bundle.putString("token",token)
        bundle.putString("userPK",userPK)
        plantFragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, plantFragment)
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener{ item ->
            when(item.itemId) {
                R.id.page_my_plant -> {
                    var bundle = Bundle()
                    bundle.putString("token",token)
                    bundle.putString("userPK",userPK)

                    plantFragment.arguments = bundle

                    supportFragmentManager.beginTransaction().replace(R.id.main_frm , plantFragment).commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.page_diary_feed -> {
                    val FeedFragment = DiaryFeedFragment()
                    var bundle = Bundle()
                    bundle.putString("token",token)
                    bundle.putString("userPK",userPK)

                    FeedFragment.arguments = bundle

                    supportFragmentManager.beginTransaction().replace(R.id.main_frm, FeedFragment).commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.page_setting -> {
                    val SettingFragment = SettingFragment()
                    var bundle = Bundle()
                    bundle.putString("userId",userId)
                    bundle.putString("userImg",userImg)
                    bundle.putString("token",token)

                    SettingFragment.arguments = bundle

                    supportFragmentManager.beginTransaction().replace(R.id.main_frm, SettingFragment).commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }



}