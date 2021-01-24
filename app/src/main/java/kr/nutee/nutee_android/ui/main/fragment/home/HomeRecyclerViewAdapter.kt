package kr.nutee.nutee_android.ui.main.fragment.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.main.home.Body

//메인뷰버그해결 코드 따로 저장해놓음-88yhtserof
/*
 * Created by 88yhtserof
 * DESC: 메인뷰 홈 게시글 RecyclerViewAdapter
 */

class HomeRecyclerViewAdapter(private var homeDataList: ArrayList<Body>)
	: RecyclerView.Adapter<HomeViewHolder>() {
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
		val view=LayoutInflater.from(parent.context).inflate(R.layout.main_home_recyclerview_item,parent,false)

		return HomeViewHolder(view)
	}

	override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
		holder.bind(homeDataList[position])
	}

	override fun getItemCount(): Int {
		return homeDataList.size
	}
}