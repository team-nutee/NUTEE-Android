package kr.nutee.nutee_android.ui.setting

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.Info
import kr.nutee.nutee_android.databinding.DeveloperInformationItemBinding
import kr.nutee.nutee_android.databinding.InfoItemBinding

class InfoAdpater (private val context: Context) : RecyclerView.Adapter<InfoViewHolder>() {

    var datas = mutableListOf<Info>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
        val binding = InfoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InfoViewHolder(binding)
        //val view = LayoutInflater.from(context).inflate(R.layout.info_item,parent,false)
        //return InfoViewHolder(view)

    }

    override fun getItemCount() = datas.size

    override fun onBindViewHolder(holder: InfoViewHolder, position: Int) {
        holder.bind(datas[position])
    }

}