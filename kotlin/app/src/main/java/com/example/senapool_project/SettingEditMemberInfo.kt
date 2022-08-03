package com.example.senapool_project

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.senapool_project.databinding.ActivityEditMemberinfoBinding


class SettingEditMemberInfo :AppCompatActivity(){

    lateinit var binding: ActivityEditMemberinfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_memberinfo)

        binding= ActivityEditMemberinfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editCompletedBtn.setOnClickListener{
            val intent=Intent(this,SettingFragment::class.java)
            startActivity(intent)
        }


    }
}
