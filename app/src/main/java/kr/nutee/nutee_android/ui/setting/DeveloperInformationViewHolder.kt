package kr.nutee.nutee_android.ui.setting

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.main.home.DeveloperInformationItem
import kr.nutee.nutee_android.databinding.DeveloperInformationItemBinding

/*
 * Created by eunseo5355
 * DESC: 개발자 정보 ViewHolder
 * Created by 88yhtserof
 * 누티 2.0 수정
 */

class DeveloperInformationViewHolder(private val binding: DeveloperInformationItemBinding): RecyclerView.ViewHolder(binding.root) {

	//private var tv_developer_role_1: TextView = itemView.findViewById(R.id.tv_developer_information_role_1)
	//private val tv_developer_role_2: TextView = itemView.findViewById(R.id.tv_developer_information_role_2)

	fun bind(developerData: DeveloperInformationItem){
		binding.tvDeveloperInformationName.text = developerData.name
		binding.tvDeveloperInformationRole1.text = developerData.role1
		binding.tvDeveloperInformationRole2.text = developerData.role2
		binding.tvDeveloperInformationDate.text = developerData.date

		if(developerData.role2 == null){
			binding.tvDeveloperInformationRole2.visibility = View.INVISIBLE
		}

		// 역할 텍스트의 background 색상 변경
		with(binding){
			when(developerData.role1){
				"PM" -> tvDeveloperInformationRole1.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.PM)
				"Android" -> tvDeveloperInformationRole1.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.Android)
				"iOS" -> tvDeveloperInformationRole1.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.iOS)
				"Design" -> tvDeveloperInformationRole1.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.Design)
				"Web" -> tvDeveloperInformationRole1.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.Web)
				"BackEnd" -> tvDeveloperInformationRole1.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.Server)
				"QA" -> tvDeveloperInformationRole1.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.QA)
			}
		}
		binding.tvDeveloperInformationRole2.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.Design)
	}
}
