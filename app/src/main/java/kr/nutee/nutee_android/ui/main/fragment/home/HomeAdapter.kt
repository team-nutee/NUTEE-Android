package kr.nutee.nutee_android.ui.main.fragment.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.home.HomeData

/*data를 각각 list item View들 과 연결시켜줄 클래스 */
class HomeAdapter(private val context: Context) : RecyclerView.Adapter<HomeViewHolder>() {

	var data = mutableListOf<HomeData>()

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
		val view = LayoutInflater.from(context).inflate(R.layout.main_list_item, parent, false)
		return HomeViewHolder(view)
	}

	override fun getItemCount(): Int {
		return data.size
	}

	//뷰 홀더와 데이터를 매칭
	override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
		holder.bind(data[position])

		holder.btn_favorite.setOnClickListener{
			it.isActivated = !it.isActivated
		}

		holder.itemView.setOnClickListener {
			Toast.makeText(context, "$position", Toast.LENGTH_SHORT).show();
		}
	}

}