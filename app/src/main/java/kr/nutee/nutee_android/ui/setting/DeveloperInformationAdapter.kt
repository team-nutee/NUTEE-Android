package kr.nutee.nutee_android.ui.setting

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.developer_information_item.view.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.main.home.DeveloperInformationItem
import kr.nutee.nutee_android.data.member.register.CategoryDomain
import kr.nutee.nutee_android.databinding.DeveloperInformationItemBinding
import kr.nutee.nutee_android.databinding.ItemCategorySelectedBinding
import kr.nutee.nutee_android.databinding.ItemModelListBinding
import kr.nutee.nutee_android.ui.member.register.fragment.SelectedCategoryAdapter

/*
 * Created by eunseo5355
 * DESC: 개발자 정보 Adapter
 */

class DeveloperInformationAdapter(val context: Context)
    : RecyclerView.Adapter<DeveloperInformationViewHolder>() {

	var developerDatas = mutableListOf<DeveloperInformationItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeveloperInformationViewHolder {
        val binding = DeveloperInformationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return DeveloperInformationViewHolder(binding)
    }

    override fun getItemCount(): Int = developerDatas.size

    override fun onBindViewHolder(holder: DeveloperInformationViewHolder, position: Int) {
        holder.bind(developerDatas[position])

        holder.binding.imgDeveloperGithubBtn.setOnClickListener {
            //안드로이드 웹브라우저앱을 이용하여 링크 열기
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(developerDatas[position].href)
            ContextCompat.startActivity(context, openURL, null)
        }

		/*holder.itemView.img_developer_github_btn.setOnClickListener {
            //안드로이드 웹브라우저앱을 이용하여 링크 열기
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(developerDatas[position].href)
            ContextCompat.startActivity(context, openURL, null)
        }*/
    }
}