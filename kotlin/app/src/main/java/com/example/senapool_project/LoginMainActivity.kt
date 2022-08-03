package com.example.senapool_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.senapool_project.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginMainActivity :AppCompatActivity(){
    lateinit var binding: ActivityLoginBinding
    lateinit var intent1:Intent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*로그인 버튼 누르면 그 때 MainActivity.kt로 이동*/
        binding.loginButton.setOnClickListener{
            intent1=Intent(this,MainActivity::class.java)
            login()
        }


        /*회원가입 버튼 누르면 회원가입으로 이동*/
        binding.signupBtn.setOnClickListener{
            val intent=Intent(this,LoginSignUpActivity::class.java)
            startActivity(intent)
        }

        /*비밀번호찾기 버튼 누르면 이메일 비밀번호 찾기 페이지로 이동*/

        binding.findPWbtn.setOnClickListener{
            val intent=Intent(this,LoginPWActivity::class.java)
            startActivity(intent)
        }
        /*구글 버튼 누르면 api 회원가입&로그인 가능하도록*/
        /*네이버 버튼 누르면 api 회원가입&로그인 가능하도록*/
        /*카카오 버튼 누르면 api 회원가입&로그인 가능하도록*/
    }

    private fun getLogin(): Login{
        val userId: String = binding.loginIdEnrollTv.text.toString()
        val password: String = binding.loginPasswordEnrollTv.text.toString()

        return Login(userId,password)
    }

    private fun login(){
        if (binding.loginIdEnrollTv.text.toString().isEmpty()) {
            Toast.makeText(this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return //함수 끝나도록.
        }

        if (binding.loginPasswordEnrollTv.text.toString().isEmpty()) {
            Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return //함수 끝나도록.
        }

        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.login(getLogin()).enqueue(object : Callback<LoginResponse> {

            //응답이 왔을 때 처리하는 부분
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                //response의 body안에 서버 개발자가 만든게 들어있음
                val resp: LoginResponse = response.body()!!
                Log.d("LOGIN/SUCCESS", resp.toString())
                when(resp.code){
                    2000->{
                        Log.d("LOGIN/SUCCESS", resp.result.toString())

                        intent1.putExtra("token",resp.result.token)
                        intent1.putExtra("userPK",resp.result.userPk)
                        intent1.putExtra("userId",resp.result.userId)
                        intent1.putExtra("userImg",resp.result.userImageUrl)

                        startActivity(intent1)
                    }
                    else->{
                        Toast.makeText(this@LoginMainActivity,resp.message,Toast.LENGTH_SHORT).show()
                        Log.d("LOGIN/FAIL",resp.code.toString())
                        //아이디 중복확인 text 해줘야함.
                    }
                }
            }

            //네트워크 연결자체가 실패했을 때 실행하는 부분
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("LOGIN/FAILURE", t.message.toString())
            }
        })
        //비동기작업이니까 함수가 잘 실행되었는지 확인차 찍어보기
        Log.d("LOGIN", "HELLO")
    }

}