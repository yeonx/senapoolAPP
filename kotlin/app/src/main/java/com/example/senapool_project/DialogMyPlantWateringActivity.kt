package com.example.senapool_project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.example.senapool_project.databinding.DialogMyPlantWateringBinding

class DialogMyPlantWateringActivity: AppCompatActivity(){

    lateinit var binding: DialogMyPlantWateringBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogMyPlantWateringBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userPK = intent.getStringExtra("userPK")
        val plantPK = intent.getStringExtra("plantPK")
        val token = intent.getStringExtra("token")
    }

    //팝업하다 망한 것~
}