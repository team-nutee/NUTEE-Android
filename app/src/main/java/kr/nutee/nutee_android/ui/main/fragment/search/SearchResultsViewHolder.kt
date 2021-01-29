package kr.nutee.nutee_android.ui.main.fragment.search

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.main_list_item.view.*
import kr.nutee.nutee_android.data.DateParser
import kr.nutee.nutee_android.ui.extend.imageSetting.setImageURLSetting
/*
 * Created by 88yhtesrof
 * DESC: 검색 결과 창 : 검색 결과가 있는 경우 RecyclerView Holder
 *
 * 삭제예정
 */

//class SearchResultsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)  {
//
//	val itemImgeProfile:ImageView=itemView.img_list_profile
//	val itemUserName:TextView=itemView.text_main_username
//	val itemUpdateAt:TextView=itemView.text_main_updateat
//	val itemContent:TextView=itemView.text_main_content
//	val itemCountImage:TextView=itemView.ext_main_count_imaget
//	val itemCountComment:TextView=itemView.text_main_count_comment
//	val itemCountLike:TextView=itemView.text_main_count_like
//
//	fun bindWithView(item: ResponseSearch){
//		val user=item.User
//
//		val userImageLoad =
//			setImageURLSetting(user?.Image?.src)
//		Glide.with(itemView).load(userImageLoad).into(itemImgeProfile)
//		itemUserName.text= user?.nickname
//		itemUpdateAt.text=item.updatedAt?.let { DateParser(it).calculateDiffDate() }
//		itemContent.text=item.content
//		itemCountImage.text= item.Images.size.toString()
//		itemCountComment.text= item.Comments.size.toString()
//		itemCountLike.text= item.Likers.size.toString()
//	}
//}