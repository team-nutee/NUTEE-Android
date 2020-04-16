package kr.nutee.nutee_android.fragment.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.nutee.nutee_android.R

class HomeAdapter(private val context: Context) : RecyclerView.Adapter<HomeViewHolder>() {

	var data = mutableListOf<HomeData>()

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
		val view = LayoutInflater.from(context).inflate(R.layout.main_list_item, parent, false)
		return HomeViewHolder(view)
	}

	override fun getItemCount(): Int {
		return data.size
	}

	override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
		holder.bind(data[position])
	}

}