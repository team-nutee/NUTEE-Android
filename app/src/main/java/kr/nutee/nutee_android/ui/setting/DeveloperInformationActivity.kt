package kr.nutee.nutee_android.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.developer_information_activity.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.main.home.DeveloperInformationItem

/*
 * Created by eunseo5355
 * DESC: 개발자 정보  Activity
 */

class DeveloperInformationActivity : AppCompatActivity() {

	lateinit var developerInformationAdapter: DeveloperInformationAdapter
	val developerDatas = mutableListOf<DeveloperInformationItem>()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.developer_information_activity)

		//RecyclerView와 Adapter 연결
		developerInformationAdapter = DeveloperInformationAdapter(this)
		rv_developer_information.adapter = developerInformationAdapter

		img_developer_information_back_btn.setOnClickListener {
			onBackPressed()
		}

		loadDatas()

	}

	override fun onBackPressed() {
		super.onBackPressed()
	}

	private fun loadDatas(){
		developerDatas.apply {
			add(
				DeveloperInformationItem(
					name = "박진수",
					role1 = "Android",
					role2 = "PL",
					role3 = "TI",
					href = "https://github.com/jinsu4755"
				)
			)
			add(
				DeveloperInformationItem(
					name = "배은서",
					role1 = "Android",
					role2 = null,
					role3 = null,
					href = "https://github.com/eunseo5355"
				)
			)
			add(
				DeveloperInformationItem(
					name = "임윤휘",
					role1 = "Android",
					role2 = "Design",
					role3 = null,
					href = "https://github.com/88yhtserof"
				)
			)
			add(
				DeveloperInformationItem(
					name = "오준현",
					role1 = "iOS",
					role2 = "Design",
					role3 = "PM",
					href = "https://github.com/5anniversary"
				)
			)
			add(
				DeveloperInformationItem(
					name = "김희재",
					role1 = "iOS",
					role2 = null,
					role3 = null,
					href = "https://github.com/iowa329"
				)
			)
			add(
				DeveloperInformationItem(
					name = "이문혁",
					role1 = "Server",
					role2 = "Web",
					role3 = null,
					href = "https://github.com/MoonHKLee"
				)
			)
			add(
				DeveloperInformationItem(
					name = "김은우",
					role1 = "Server",
					role2 = "PL",
					role3 = null,
					href = "https://github.com/suuum12"
				)
			)
			add(
				DeveloperInformationItem(
					name = "차희주",
					role1 = "Server",
					role2 = null,
					role3 = null,
					href = "https://github.com/JOOZOO20"
				)
			)
			add(
				DeveloperInformationItem(
					name = "김지원",
					role1 = "Web",
					role2 = "PL",
					role3 = null,
					href = "https://github.com/gwonkim"
				)
			)
			add(
				DeveloperInformationItem(
					name = "고병우",
					role1 = "QA",
					role2 = null,
					role3 = null,
					href = "https://github.com/kohbwoo"
				)
			)
			add(
				DeveloperInformationItem(
					name = "박세연",
					role1 = "QA",
					role2 = null,
					role3 = null,
					href = "https://github.com/SEYEON-PARK"
				)
			)
		}
		developerInformationAdapter.developerDatas = developerDatas
		developerInformationAdapter.notifyDataSetChanged()
	}
}
