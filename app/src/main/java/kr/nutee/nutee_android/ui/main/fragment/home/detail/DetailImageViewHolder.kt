package kr.nutee.nutee_android.ui.main.fragment.home.detail

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_detail_image_view_item.view.*
import kr.nutee.nutee_android.ui.main.fragment.home.detail.ImageItem

class DetailImageViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

	private val itemImage: ImageView =itemView.iv_layout_detail_image_view

	fun bindWithView(imageItem: ImageItem){
		itemImage.setImageResource(imageItem.imageSrc)
	}
}