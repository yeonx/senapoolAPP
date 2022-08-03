package com.example.senapool_project

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.senapool_project.databinding.FragmentSettingBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingFragment : Fragment() {

    lateinit var binding: FragmentSettingBinding
    lateinit var userId: String
    lateinit var userImg: String
    lateinit var token: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(inflater,container,false)

        userId = arguments?.getString("userId").toString()
        userImg = arguments?.getString("userImg").toString()
        token = arguments?.getString("token").toString()

        Glide.with(requireContext()).load(userImg).into(binding.settingMageIv)
        binding.settingMyPlantNameTv.text=userId

        /*회원정보 수정 버튼 클릭*/
        binding.editMemInfoBtn.setOnClickListener {
            startActivity(Intent(activity, SettingEditMemberInfo::class.java))
        }

        /*로그아웃 버튼 클릭*/
        binding.logoutBtn.setOnClickListener {
            startActivity(Intent(activity, LoginMainActivity::class.java))
        }

        /*회원탈퇴 버튼 클릭  ->  수정 필요*/
        binding.quitBtn.setOnClickListener {
            quit()
        }


        return binding.root
    }

    fun quit(){
        // Dialog만들기
        val mDialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_withdrawal, null)
        val mBuilder = AlertDialog.Builder(activity)
            .setView(mDialogView)

        val  mAlertDialog = mBuilder.show()



        val noButton = mDialogView.findViewById<TextView>(R.id.withdrawal_no_tv)
        noButton.setOnClickListener {
            mAlertDialog.dismiss()

        }

        fun getPassword(): Password{
            val password: String=mDialogView.findViewById<EditText>(R.id.withdrawal_password_et).text.toString()
            Log.d("Password",password+token)
            return Password(password)
        }

        val yesButton = mDialogView.findViewById<TextView>(R.id.withdrawal_yes_tv)
        yesButton.setOnClickListener {
            val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
            authService.Quit("Bearer "+token,
                Password(mDialogView.findViewById<EditText>(R.id.withdrawal_password_et).text.toString())
            ).enqueue(object :
                Callback<QuitResponse> {

                //응답이 왔을 때 처리하는 부분
                override fun onResponse(call: Call<QuitResponse>, response: Response<QuitResponse>) {
                    //response의 body안에 서버 개발자가 만든게 들어있음
                    val resp: QuitResponse = response.body()!!
                    Log.d("QUIT/SUCCESS", resp.toString())
                    when(resp.code){
                        2000->{
                            Log.d("QUIT/SUCCESS", resp.message)

                            // Dialog만들기
                            val mDialogView2 = LayoutInflater.from(activity).inflate(R.layout.dialog_withdrawal_confirm, null)
                            val mBuilder2 = AlertDialog.Builder(activity)
                                .setView(mDialogView2)

                            val  mAlertDialog2 = mBuilder2.show()

                            Handler().postDelayed(Runnable {
                                mAlertDialog2.dismiss()
                                mAlertDialog.dismiss()
                                startActivity(Intent(activity, LoginMainActivity::class.java))
                            }, 2000) // 0.6초 정도 딜레이를 준 후 시작


                        }
                        else->{

                        }
                    }
                }

                //네트워크 연결자체가 실패했을 때 실행하는 부분
                override fun onFailure(call: Call<QuitResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })

        }
    }
}
