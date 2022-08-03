package com.example.senapool_project

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.senapool_project.databinding.FragmentDiaryFeedBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiaryFeedFragment : Fragment() {

    lateinit var binding: FragmentDiaryFeedBinding
    lateinit var feedRVAdapter: FeedRVAdapter

    lateinit var token:String
    lateinit var userPK:String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiaryFeedBinding.inflate(inflater,container,false)

        token = arguments?.getString("token").toString()
        userPK = arguments?.getString("userPK").toString()

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        getFeed()

        binding.diaryFeedListRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition() // 화면에 보이는 마지막 아이템의 position
                val itemTotalCount = recyclerView.adapter!!.itemCount - 1 // 어댑터에 등록된 아이템의 총 개수 -1
                Log.d("Scroll",lastVisibleItemPosition.toString())
                // 스크롤이 끝에 도달했는지 확인
                if (lastVisibleItemPosition == itemTotalCount) {

                }
            }
        })
    }

    private fun initRecyclerView(result: ArrayList<Feed>) {
        feedRVAdapter = FeedRVAdapter(requireContext(), result)

        binding.diaryFeedListRv.adapter = feedRVAdapter
    }

    fun getFeed(){
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.DiaryFeed("Bearer "+token).enqueue(object :
            Callback<DiaryFeedResponse> {

            //응답이 왔을 때 처리하는 부분
            override fun onResponse(call: Call<DiaryFeedResponse>, response: Response<DiaryFeedResponse>) {
                //response의 body안에 서버 개발자가 만든게 들어있음
                Log.d("DIARYLIST/SUCCESS", response.toString())
                val resp: DiaryFeedResponse = response.body()!!
                Log.d("DIARYLIST/SUCCESS", resp.toString())
                when(resp.code){
                    2000->{
                        initRecyclerView(resp.result.content)

                        binding.diaryFeedListRv.adapter=feedRVAdapter
                        binding.diaryFeedListRv.layoutManager=
                            LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

                        feedRVAdapter.setMyItemClickListener(object : FeedRVAdapter.MyItemClickListener {
                            override fun onItemClick(plantDiaryPK: String?) {
                                val intent = Intent(activity,DiaryFeedDetailActivity::class.java)
                                intent.putExtra("plantDiaryPK",plantDiaryPK) //데이터 넣기
                                intent.putExtra("userPK",userPK) //데이터 넣기
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
            override fun onFailure(call: Call<DiaryFeedResponse>, t: Throwable) {
                Log.d("DIARYLIST/FAILURE", t.message.toString())
            }
        })
        //비동기작업이니까 함수가 잘 실행되었는지 확인차 찍어보기
        Log.d("DIARYLIST", "HELLO")

    }
}