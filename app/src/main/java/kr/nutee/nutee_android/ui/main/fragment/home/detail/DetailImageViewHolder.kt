package kr.nutee.nutee_android.ui.main.fragment.home.detail

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_detail_image_view_item.view.*

/*
 * Created by 88yhtsero
 * DESC: 디테일 뷰: 사진 자세히 보기 viewPager holder
 */

class DetailImageViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

	//layout_detail_image_view_item 의 image view
	val itemImage: ImageView =itemView.iv_layout_detail_image_view
}