package kr.nutee.nutee_android.ui.setting

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.main.home.DeveloperInformationItem

/*
 * Created by eunseo5355
 * DESC: 개발자 정보 ViewHolder
 */

class DeveloperInformationViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

	private val tv_developer_name: TextView = itemView.findViewById(R.id.tv_developer_information_name)
	private var tv_developer_role_1: TextView = itemView.findViewById(R.id.tv_developer_information_role_1)
	private val tv_developer_role_2: TextView = itemView.findViewById(R.id.tv_developer_information_role_2)
	private val tv_developer_role_3: TextView = itemView.findViewById(R.id.tv_developer_information_role_3)

	fun bind(developerData: DeveloperInformationItem){
		tv_developer_name.text = developerData.name
		tv_developer_role_1.text = developerData.role1
		tv_developer_role_2.text = developerData.role2
		tv_developer_role_3.text = developerData.role3

		if(developerData.role2 == null){
			tv_developer_role_2.visibility = View.INVISIBLE
		}

		if(developerData.role3 == null){
			tv_developer_role_3.visibility = View.INVISIBLE
		}

		// 역할 텍스트의 background 색상 변경
		when(developerData.role1){
			"PM" -> tv_developer_role_1.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.PM)
			"PL" -> tv_developer_role_1.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.PL)
			"Android" -> tv_developer_role_1.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.Android)
			"iOS" -> tv_developer_role_1.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.iOS)
			"Design" -> tv_developer_role_1.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.Design)
			"Web" -> tv_developer_role_1.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.Web)
			"Server" -> tv_developer_role_1.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.Server)
			"QA" -> tv_developer_role_1.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.QA)
			"TI" -> tv_developer_role_1.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.TI)
		}

		when(developerData.role2){
			"PM" -> tv_developer_role_2.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.PM)
			"PL" -> tv_developer_role_2.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.PL)
			"Android" -> tv_developer_role_2.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.Android)
			"iOS" -> tv_developer_role_2.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.iOS)
			"Design" -> tv_developer_role_2.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.Design)
			"Web" -> tv_developer_role_2.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.Web)
			"Server" -> tv_developer_role_2.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.Server)
			"QA" -> tv_developer_role_2.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.QA)
			"TI" -> tv_developer_role_2.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.TI)
		}

		when(developerData.role3){
			"PM" -> tv_developer_role_3.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.PM)
			"PL" -> tv_developer_role_3.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.PL)
			"Android" -> tv_developer_role_3.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.Android)
			"iOS" -> tv_developer_role_3.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.iOS)
			"Design" -> tv_developer_role_3.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.Design)
			"Web" -> tv_developer_role_3.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.Web)
			"Server" -> tv_developer_role_3.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.Server)
			"QA" -> tv_developer_role_3.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.QA)
			"TI" -> tv_developer_role_3.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.TI)
		}
	}
}
