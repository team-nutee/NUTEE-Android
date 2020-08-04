package kr.nutee.nutee_android.data.main.home.detail

import android.media.Image
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import kotlinx.android.synthetic.main.activity_show_detail_image_view.view.*
import kotlinx.android.synthetic.main.layout_detail_image_view_item.view.*

class DetailImageViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

	private val itemImage: ImageView =itemView.iv_layout_detail_image_view

	fun bindWithView(imageItem:ImageItem){
		itemImage.setImageResource(imageItem.imageSrc)
	}
}