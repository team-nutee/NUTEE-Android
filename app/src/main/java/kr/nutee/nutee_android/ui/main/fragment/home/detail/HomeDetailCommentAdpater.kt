package kr.nutee.nutee_android.ui.main.fragment.home.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.main.home.CommentBody
import kr.nutee.nutee_android.databinding.MainHomeDetailItemBinding

class HomeDetailCommentAdpater(
        val context: Context,
        var datas: Array<CommentBody>,
        val postId: Int?
) : RecyclerView.Adapter<HomeDetailCommentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeDetailCommentViewHolder {
        val binding = MainHomeDetailItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeDetailCommentViewHolder(binding)
        /*val view = LayoutInflater.from(context).inflate(R.layout.main_home_detail_item,parent,false)
        return HomeDetailCommentViewHolder(view)*/
    }

    override fun getItemCount() = datas.size

    override fun onBindViewHolder(holder: HomeDetailCommentViewHolder, position: Int) {
		holder.bind(datas[position],postId,context)
    }

}