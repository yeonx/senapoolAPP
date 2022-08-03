package com.example.senapool_project

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.senapool_project.databinding.ItemDiaryFeedBinding
import com.example.senapool_project.databinding.ItemMyPlantDiaryBinding
import org.w3c.dom.Text

class FeedRVAdapter(val context: Context, val result : ArrayList<Feed>): RecyclerView.Adapter<FeedRVAdapter.ViewHolder>() {

    //사용하고자 하는 아이템뷰 객체를 만들어야한다. 그리고 나서 만들어진 아이템뷰 객체를 재활용할 수 있도록 ViewHolder에게 던져줘야 한다.(-> return ViewHolder(binding))
    //얘는 재활용하기 때문에 처음 몇번만 호출되고 말음.
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FeedRVAdapter.ViewHolder {
        val binding: ItemDiaryFeedBinding = ItemDiaryFeedBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    //viewHolder에 데이터를 바인딩해줘야 할 때마다 호출해줘야하는 함수. 사용자가 화면을 위아래로 스크롤할 때 마다 엄청 많이 호출됨.
    //position : 인덱스 아이디
    //클릭 이벤트는 여기서 작성해주는 것이 좋다.
    override fun onBindViewHolder(holder: FeedRVAdapter.ViewHolder, position: Int) {

        if(result[position].diaryImageUrl == "" || result[position].diaryImageUrl == null){

        } else {
            Log.d("image",result[position].diaryImageUrl )
            Glide.with(context).load(result[position].diaryImageUrl).into(holder.diaryImage) //적용시키는 방법
            Glide.with(context).load(result[position].userImage).into(holder.userImage)
        }
        holder.title.text = result[position].title // 제목 적용
        holder.date.text = result[position].createdAt
        holder.content.text = result[position].content
        holder.likes.text = result[position].likesCount.toString()
        holder.userName.text = result[position].userID

        holder.itemView.setOnClickListener{
            mItemClickListener.onItemClick(result[position].plantDiaryPK)
        }
        if (result[position].likesState==true){
            holder.likeImg.setImageResource(R.drawable.heart)
        }else{
            holder.likeImg.setImageResource(R.drawable.empty_heart)
        }
    }


    //데이터셋의 크기를 알려주는 함수

    override fun getItemCount(): Int = result.size

    //ViewHolder 클래스는 아이템뷰 객체들이 날라가지 않도록 담고 있는 그릇.
    inner class ViewHolder(val binding: ItemDiaryFeedBinding): RecyclerView.ViewHolder(binding.root){

        val userName: TextView = binding.itemDiaryFeedUserTv
        val date: TextView = binding.itemDiaryFeedDateTv
        val title : TextView = binding.itemDiaryFeedTitleTv
        val content : TextView = binding.itemDiaryFeedContentTv
        val diaryImage: ImageView = binding.itemDiaryFeedImgIv
        val likes: TextView = binding.itemDiaryFeedHeartCountTv
        val userImage: ImageView = binding.itemDiaryFeedUserIv
        val likeImg: ImageView = binding.itemDiaryFeedHeartIv

    }

    interface MyItemClickListener{
        //fun onRemoveSong(plantDiaryPK: Int)
        fun onItemClick(plantDiaryPK: String?)
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }



}