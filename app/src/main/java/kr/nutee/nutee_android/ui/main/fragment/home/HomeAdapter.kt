package kr.nutee.nutee_android.ui.main.fragment.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.data.main.home.ResponseMain
import kr.nutee.nutee_android.data.main.home.ResponseMainItem
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue


/*data를 각각 list item View들 과 연결시켜줄 클래스 */
class HomeAdapter(var data: ResponseMain, val context: Context) : RecyclerView.Adapter<HomeViewHolder>() {

	val requestToServer = RequestToServer

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
		val view = LayoutInflater.from(context).inflate(R.layout.main_list_item, parent, false)
		return HomeViewHolder(view)
	}

	override fun getItemCount(): Int {
		return data.size
	}

	//뷰 홀더와 데이터를 매칭
	override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
		holder.bind(data[position],position, this)
		Log.d("testCall", "바이딩")

	}

}