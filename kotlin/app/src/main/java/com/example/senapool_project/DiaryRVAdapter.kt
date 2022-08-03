package com.example.senapool_project

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.senapool_project.databinding.FragmentSettingBinding
import com.example.senapool_project.databinding.ItemMyPlantBinding
import com.example.senapool_project.databinding.ItemMyPlantDiaryBinding

//어댑터가 아이템뷰 객체들에게 바인딩해주려면 전에 만든 데이터 리스트를 매개변수로 받아와야함.
class DiaryRVAdapter(val context: Context, val result : diaryPrevListDto): RecyclerView.Adapter<DiaryRVAdapter.ViewHolder>(){

    //사용하고자 하는 아이템뷰 객체를 만들어야한다. 그리고 나서 만들어진 아이템뷰 객체를 재활용할 수 있도록 ViewHolder에게 던져줘야 한다.(-> return ViewHolder(binding))
    //얘는 재활용하기 때문에 처음 몇번만 호출되고 말음.
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): DiaryRVAdapter.ViewHolder {
        val binding: ItemMyPlantDiaryBinding = ItemMyPlantDiaryBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    //viewHolder에 데이터를 바인딩해줘야 할 때마다 호출해줘야하는 함수. 사용자가 화면을 위아래로 스크롤할 때 마다 엄청 많이 호출됨.
    //position : 인덱스 아이디
    //클릭 이벤트는 여기서 작성해주는 것이 좋다.
    override fun onBindViewHolder(holder: DiaryRVAdapter.ViewHolder, position: Int) {

        if(result.diaryPrevDtoList[position].image == "" || result.diaryPrevDtoList[position].image == null){

        } else {
            Log.d("image",result.diaryPrevDtoList[position].image )
            Glide.with(context).load(result.diaryPrevDtoList[position].image).into(holder.image) //적용시키는 방법
        }
        holder.title.text = result.diaryPrevDtoList[position].title // 제목 적용
        holder.date.text = result.diaryPrevDtoList[position].createdAt
        holder.content.text = result.diaryPrevDtoList[position].content

        holder.itemView.setOnClickListener{
            mItemClickListener.onItemClick(result.diaryPrevDtoList[position].diaryPK)
        }
    }

    //데이터셋의 크기를 알려주는 함수

    override fun getItemCount(): Int = result.diaryPrevDtoList.size

    //ViewHolder 클래스는 아이템뷰 객체들이 날라가지 않도록 담고 있는 그릇.
    inner class ViewHolder(val binding: ItemMyPlantDiaryBinding): RecyclerView.ViewHolder(binding.root){

        val date: TextView = binding.itemMyPlantDiaryDateTv
        val title : TextView = binding.itemMyPlantDiaryTitleTv
        val content : TextView = binding.itemMyPlantDiaryContentTv
        val image: ImageView = binding.itemMyPlantDiaryImgIv

    }

    interface MyItemClickListener{
        //fun onRemoveSong(plantPK: Int)
        fun onItemClick(diaryPK: String?)
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }
}