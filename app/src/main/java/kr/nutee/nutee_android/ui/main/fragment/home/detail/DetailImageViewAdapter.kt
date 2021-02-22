package kr.nutee.nutee_android.ui.main.fragment.home.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.main.home.Image
import kr.nutee.nutee_android.ui.extend.animation.glideProgressDrawable
import kr.nutee.nutee_android.ui.extend.imageSetting.setImageURLSetting

/*
 * Created by 88yhtsero
 * DESC: 디테일 뷰: 사진 자세히 보기 viewPager adapter
 *
 */

class DetailImageViewAdapter(private val context: Context, private var detailViewImageList: Array<Image>)
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
		Glide.with(context)
			.load(setImageURLSetting(detailViewImageList[position].src))
			.placeholder(context.glideProgressDrawable())
			.into(holder.itemImage)
	}
}