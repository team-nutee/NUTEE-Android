package kr.nutee.nutee_android.ui.main.fragment.home.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.main.home.Comment
import kr.nutee.nutee_android.data.main.home.CommentBody

class HomeDetailCommentAdpater (var datas: List<CommentBody> ,val context: Context) : RecyclerView.Adapter<HomeDetailCommentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeDetailCommentViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.main_home_detail_item,parent,false)
        return HomeDetailCommentViewHolder(view)
    }

    override fun getItemCount() = datas.size

    override fun onBindViewHolder(holder: HomeDetailCommentViewHolder, position: Int) {
		holder.bind(datas[position])
    }

}