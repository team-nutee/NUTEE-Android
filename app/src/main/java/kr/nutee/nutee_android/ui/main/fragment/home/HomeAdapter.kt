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
import kr.nutee.nutee_android.ui.extend.loadFragment
import kr.nutee.nutee_android.ui.main.MainActivity
import kr.nutee.nutee_android.ui.main.fragment.home.detail.HomeDetailFragment


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
		holder.bind(data[position])

		holder.btn_favorite.setOnClickListener {
			likeClickEvent(it, data[position],position)
		}
	}

	private fun likeClickEvent(it: View, customData: ResponseMainItem, position: Int) {
		if (it.isActivated) {
			//좋아요 버튼 활성상태
			requestToServer.service.requestDelLike(App.prefs.local_login_token, customData.id)
				.customEnqueue { res->
					if (res.isSuccessful) {
						notifyItemChanged(position)
						it.isActivated = !it.isActivated
					}
				}

		} else {
			//비활성 상태
			requestToServer.service.requestLike(App.prefs.local_login_token, customData.id)
				.customEnqueue { res->
					if (res.isSuccessful) {
						notifyItemChanged(position)
						it.isActivated = !it.isActivated
					}
				}
		}
	}

}