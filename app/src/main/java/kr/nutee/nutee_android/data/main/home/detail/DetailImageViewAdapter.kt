package kr.nutee.nutee_android.data.main.home.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_detail_image_view_item.view.*
import kr.nutee.nutee_android.R

class DetailImageViewAdapter(private var imageList:ArrayList<ImageItem>)
	:RecyclerView.Adapter<DetailImageViewHolder>(){
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailImageViewHolder {
		return DetailImageViewHolder(LayoutInflater.from(parent.context).inflate(
										R.layout.layout_detail_image_view_item,parent,false))
	}

	override fun getItemCount(): Int {
		return imageList.size
	}

	override fun onBindViewHolder(holder: DetailImageViewHolder, position: Int) {
		//데이터와 뷰를 묶기
		holder.bindWithView(imageList[position])
	}
}