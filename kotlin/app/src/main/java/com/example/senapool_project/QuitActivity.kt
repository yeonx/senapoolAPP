package com.example.senapool_project

import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.senapool_project.databinding.ActivityQuitCheckBinding

class QuitActivity:AppCompatActivity() {

    lateinit var binding: ActivityQuitCheckBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityQuitCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*Mainactivity로 가면 안 되고 팝업 뜨고 저 원래 메인 activity로 전환되어야함*/
       /* binding.quitBtnDoublecheck.setOnClickListener{
            val intent=Intent(this,showAlert())
            startActivity()
        }*/


    }

    /*private fun showAlert() {
        AlertDialog.Builder(this)
            .setTitle("앱 종료")
            .setPositiveButton("종료") { dialogInterface: DialogInterface, i: Int -> finish() }
            .setNegativeButton("취소") { dialogInterface: DialogInterface, i: Int -> }
            .show()
    }*/

}

