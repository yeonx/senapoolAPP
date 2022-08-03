package com.example.senapool_project

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.senapool_project.databinding.ItemMyPlantBinding

//어댑터가 아이템뷰 객체들에게 바인딩해주려면 전에 만든 데이터 리스트를 매개변수로 받아와야함.
class MyPlantRVAdapter(val context: Context, val result : plantDtoList): RecyclerView.Adapter<MyPlantRVAdapter.ViewHolder>(){

    //사용하고자 하는 아이템뷰 객체를 만들어야한다. 그리고 나서 만들어진 아이템뷰 객체를 재활용할 수 있도록 ViewHolder에게 던져줘야 한다.(-> return ViewHolder(binding))
    //얘는 재활용하기 때문에 처음 몇번만 호출되고 말음.
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyPlantRVAdapter.ViewHolder {
        val binding: ItemMyPlantBinding = ItemMyPlantBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    //viewHolder에 데이터를 바인딩해줘야 할 때마다 호출해줘야하는 함수. 사용자가 화면을 위아래로 스크롤할 때 마다 엄청 많이 호출됨.
    //position : 인덱스 아이디
    //클릭 이벤트는 여기서 작성해주는 것이 좋다.
    override fun onBindViewHolder(holder: MyPlantRVAdapter.ViewHolder, position: Int) {

        if(result.plantDtoList[position].plantImage == "" || result.plantDtoList[position].plantImage == null){

        } else {
            Log.d("image",result.plantDtoList[position].plantImage.toString() )
            Glide.with(context).load(result.plantDtoList[position].plantImage).into(holder.plantImage) //적용시키는 방법
        }
        holder.plantName.text = result.plantDtoList[position].plantName // 제목 적용

        holder.itemView.setOnClickListener{
            mItemClickListener.onItemClick(result.plantDtoList[position].plantPK)
        }

        holder.itemView.setOnLongClickListener {
            mItemClickListener.onItemLongClick(result.plantDtoList[position].plantPK)
            Log.d("longclick", true.toString())
            return@setOnLongClickListener true
        }
    }

    //데이터셋의 크기를 알려주는 함수

    override fun getItemCount(): Int = result.plantDtoList.size

    //ViewHolder 클래스는 아이템뷰 객체들이 날라가지 않도록 담고 있는 그릇.
    inner class ViewHolder(val binding: ItemMyPlantBinding): RecyclerView.ViewHolder(binding.root){

        val plantImage : ImageView = binding.itemMyPlantImgIv
        val plantName : TextView = binding.itemMyPlantPlantNameTv

    }

    interface MyItemClickListener{
        //fun onRemoveSong(plantPK: Int)
        fun onItemClick(plantPK: String?)
        fun onItemLongClick(plantPK: String?)
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }



}



