package com.example.senapool_project

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.senapool_project.databinding.FragmentMyPlantBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPlantFragment : Fragment() {

    lateinit var binding: FragmentMyPlantBinding
    private lateinit var myplantRVAdapter: MyPlantRVAdapter
    lateinit var token: String
    lateinit var userPK: String
    var check:Int = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPlantBinding.inflate(inflater,container,false)

        token = arguments?.getString("token").toString()
        userPK = arguments?.getString("userPK").toString()
        Log.d("MYPLANT/PK 입니다",userPK)

        binding.myPlantPlusIb.setOnClickListener {
            val intent = Intent(activity,MyPlantEnrollActivity::class.java)
            intent.putExtra("userPK",userPK) //데이터 넣기
            intent.putExtra("token",token) //데이터 넣기
            startActivity(intent)
            //startActivity(Intent(activity, MyPlantEnrollActivity::class.java))
        }


        return binding.root
    }

    override fun onStart() {
        super.onStart()
        Log.d("MYPLANT","onstart")

        getPlant(userPK)





    }

    private fun initRecyclerView(result: plantDtoList) {
        myplantRVAdapter = MyPlantRVAdapter(requireContext(), result)

        binding.myPlantRv.adapter = myplantRVAdapter
    }



    fun getPlant(userPK:String){
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.MyPlantList(userPK).enqueue(object : Callback<MyPlantListResponse> {

            //응답이 왔을 때 처리하는 부분
            override fun onResponse(call: Call<MyPlantListResponse>, response: Response<MyPlantListResponse>) {
                //response의 body안에 서버 개발자가 만든게 들어있음
                Log.d("MYPlANT/SUCCESS", response.toString())
                val resp: MyPlantListResponse = response.body()!!
                Log.d("MYPlANT/SUCCESS", resp.result.toString())
                when(resp.code){
                    2000->{
                        initRecyclerView(resp.result.plantListDto)
                        Log.d("USERIMAGE/SUCCESS",resp.result.userImage.toString())
                        Glide.with(context!!).load(resp.result.userImage).into(binding.myPlantUserImageIv)
                        binding.myPlantUserNameTv.text=resp.result.userId

                        binding.myPlantRv.adapter = myplantRVAdapter
                        binding.myPlantRv.layoutManager = GridLayoutManager(context, 2)
                        check=2000
                        Log.d("MYPLANT/check",check.toString())
                        if (check==2000) {
                            Log.d("MYPLANT/check","실행된다")
                            myplantRVAdapter.setMyItemClickListener(object : MyPlantRVAdapter.MyItemClickListener {
                                override fun onItemLongClick(plantPK: String?) {
//                                    Log.d("longclick",plantPK.toString())
//                                    val intent = Intent(activity,DialogMyPlantWateringActivity::class.java)
//                                    intent.putExtra("plantPK",plantPK) //데이터 넣기
//                                    intent.putExtra("userPK",userPK) //데이터 넣기
//                                    intent.putExtra("token",token) //데이터 넣기
//                                    startActivity(intent)

                                    // Dialog만들기
                                    val mDialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_my_plant_watering, null)
                                    val mBuilder = AlertDialog.Builder(activity)
                                        .setView(mDialogView)

                                    val  mAlertDialog = mBuilder.show()

                                    val modifyButton = mDialogView.findViewById<TextView>(R.id.my_plant_popup_modify_tv)
                                    modifyButton.setOnClickListener {
                                        mAlertDialog.dismiss()

//                                        val intent = Intent(activity,::class.java)
//                                        intent.putExtra("plantPK",plantPK) //데이터 넣기
//                                        intent.putExtra("userPK",userPK) //데이터 넣기
//                                        intent.putExtra("token",token) //데이터 넣기
//                                        startActivity(intent)

                                    }

                                    val deleteButton = mDialogView.findViewById<TextView>(R.id.my_plant_popup_delete_tv)
                                    deleteButton.setOnClickListener {
                                        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
                                        authService.MyPlantDelete("Bearer "+token,userPK,plantPK!!).enqueue(object : Callback<MyPlantDeleteResponse> {

                                            //응답이 왔을 때 처리하는 부분
                                            override fun onResponse(call: Call<MyPlantDeleteResponse>, response: Response<MyPlantDeleteResponse>) {
                                                //response의 body안에 서버 개발자가 만든게 들어있음
                                                val resp: MyPlantDeleteResponse = response.body()!!
                                                Log.d("DELETE/SUCCESS", resp.toString())
                                                when(resp.code){
                                                    2000->{
                                                        Log.d("DELETE/SUCCESS", resp.message)
                                                        getPlant(userPK)
                                                        mAlertDialog.dismiss()
                                                    }
                                                    else->{

                                                    }
                                                }
                                            }

                                            //네트워크 연결자체가 실패했을 때 실행하는 부분
                                            override fun onFailure(call: Call<MyPlantDeleteResponse>, t: Throwable) {
                                                Log.d("DELETE/FAILURE", t.message.toString())
                                            }
                                        })

                                    }


                                }

                                override fun onItemClick(plantPK: String?) {
                                    var myplantdiarylistfragment = MyPlantDiaryListFragment()
                                    var bundle = Bundle()
                                    bundle.putString("token",token)
                                    bundle.putString("userPK",userPK)
                                    bundle.putString("plantPK",plantPK)
                                    myplantdiarylistfragment.arguments = bundle //fragment의 arguments에 데이터를 담은 bundle을 넘겨줌

                                    //myplantdiarylist프래그먼트로 이동!
                                    (context as MainActivity).supportFragmentManager.beginTransaction()
                                        .replace(R.id.main_frm, myplantdiarylistfragment)
                                        .commitAllowingStateLoss()
                                }




//            override fun onRemoveSong(plantPK: Int) {
//                TODO("Not yet implemented")
//            }
                            })


                        }

                    }
                    else->{
                        //Toast.makeText(this@MyPlantFragment,resp.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            //네트워크 연결자체가 실패했을 때 실행하는 부분
            override fun onFailure(call: Call<MyPlantListResponse>, t: Throwable) {
                Log.d("MYPlANT/FAILURE", t.message.toString())
            }
        })
        //비동기작업이니까 함수가 잘 실행되었는지 확인차 찍어보기
        Log.d("MYPlANT", "HELLO")

    }
}