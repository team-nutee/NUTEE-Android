package kr.nutee.nutee_android.ui.main.fragment.home.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.nutee.nutee_android.R

/*
 * Created by 88yhtsero
 * DESC: 디테일 뷰: 사진 자세히 보기
 *
 */

class DetailImageViewAdapter(private var detailViewImageList:ArrayList<Int>)
	:RecyclerView.Adapter<DetailImageViewHolder>(){
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailImageViewHolder {
		return DetailImageViewHolder(
			LayoutInflater.from(parent.context).inflate(
				R.layout.layout_detail_image_view_item, parent, false
			)
		)
	}

	override fun getItemCount(): Int {
		return detailViewImageList.size
	}

	override fun onBindViewHolder(holder: DetailImageViewHolder, position: Int) {
		//데이터와 뷰를 묶기
		holder.bindWithView(detailViewImageList[position])
	}
}