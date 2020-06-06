package kr.nutee.nutee_android.ui.main.fragment.add

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.main_fragment_add_photo_item.view.*
import kr.nutee.nutee_android.R

class ImageAdapter(private var datas: ArrayList<Uri>, private val context: Context) : RecyclerView.Adapter<ImageViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.main_fragment_add_photo_item,parent,false)
        return ImageViewHolder(view)
    }

    override fun getItemCount() = datas.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(datas[position])
        holder.itemView.img_btn_image_del.setOnClickListener {
            Log.d("imageDel","del")
            datas.remove(datas[position])
            if (AddActivity().selectedImage.size > 0) {
                AddActivity().selectedImage.remove(datas[position])
            }
            notifyDataSetChanged()
        }
    }

}