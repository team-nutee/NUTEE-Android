package kr.nutee.nutee_android.ui.main.fragment.home.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.main.home.Image
import kr.nutee.nutee_android.databinding.LayoutDetailImageViewItemBinding
import kr.nutee.nutee_android.ui.extend.animation.glideProgressDrawable

/*
 * Created by 88yhtsero
 * DESC: 디테일 뷰: 사진 자세히 보기 viewPager adapter
 *
 */

class DetailImageViewAdapter(private val context: Context, private var detailViewImageList: Array<Image>)
	:RecyclerView.Adapter<DetailImageViewHolder>(){
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailImageViewHolder {
		val binding = LayoutDetailImageViewItemBinding.inflate(
				LayoutInflater.from(parent.context),
				parent,
				false
		)
		return DetailImageViewHolder(binding)
	}

	override fun getItemCount(): Int {
		return detailViewImageList.size
	}

	override fun onBindViewHolder(holder: DetailImageViewHolder, position: Int) {
		//데이터와 뷰를 묶기
		Glide.with(context)
			.load(detailViewImageList[position].src)
			.placeholder(context.glideProgressDrawable())
			.into(holder.itemImage)
	}
}