package kr.nutee.nutee_android.ui.main.fragment.add

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewParent
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.add_activity.view.*
import kotlinx.android.synthetic.main.main_fragment_add_photo_item.view.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.databinding.AddActivityBinding
import kr.nutee.nutee_android.databinding.MainFragmentAddPhotoItemBinding
import okhttp3.internal.notify

class ImageViewHolder(private val binding: MainFragmentAddPhotoItemBinding) : RecyclerView.ViewHolder(binding.root){
	fun bind(customData: Uri,context:Context){
		Glide.with(context).load(customData).into(binding.imgUploadImage)
		binding.imgBtnImageDel.setOnClickListener{
			Log.d("imageDel","del")
			AddActivity().selectedImage.remove(customData)

			val itemViewBinding: AddActivityBinding?= null
			itemViewBinding!!.rvImageList.adapter!!.notifyDataSetChanged()

			//itemView.rv_image_list.adapter!!.notifyDataSetChanged()
		}
	}
}

/*class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val img_upload_image = itemView.findViewById<ImageView>(R.id.img_upload_image)
	val img_btn_image_del = itemView.findViewById<ImageView>(R.id.img_btn_image_del)
    fun bind(customData: Uri,context:Context) {
		Glide.with(context).load(customData).into(img_upload_image)
		img_btn_image_del.setOnClickListener {
			Log.d("imageDel","del")
			AddActivity().selectedImage.remove(customData)
			itemView.rv_image_list.adapter!!.notifyDataSetChanged()
		}
    }
}*/
