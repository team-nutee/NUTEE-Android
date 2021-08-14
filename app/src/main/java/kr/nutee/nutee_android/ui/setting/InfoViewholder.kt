package kr.nutee.nutee_android.ui.setting

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kr.nutee.nutee_android.data.Info
import kr.nutee.nutee_android.databinding.InfoItemBinding

/*class InfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(customData: Info) {

    }
}*/
class InfoViewHolder(private val binding: InfoItemBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(customData: Info){

    }
}