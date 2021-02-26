package kr.nutee.nutee_android.ui.main.fragment.add

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.add_activity.view.*
import kotlinx.android.synthetic.main.main_fragment_add_photo_item.view.*
import kr.nutee.nutee_android.R
import okhttp3.internal.notify

class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
}