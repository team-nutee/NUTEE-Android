package kr.nutee.nutee_android.ui.main.fragment.home.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.main.home.ResponseMain

class HomeDetailProfileAdapter(var data: ResponseMain, val context: Context) :
	RecyclerView.Adapter<HomeProfileDetailViewHolder>() {
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeProfileDetailViewHolder {
		val view = LayoutInflater.from(context).inflate(R.layout.main_list_item, parent, false)
		return HomeProfileDetailViewHolder(view)
	}

	override fun getItemCount(): Int {
		return data.size
	}

	override fun onBindViewHolder(holder: HomeProfileDetailViewHolder, position: Int) {
		holder.bind(data[position]!!,position,this)
	}
}