package kr.nutee.nutee_android.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.developer_information_activity.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.main.home.DeveloperInformationItem
import kr.nutee.nutee_android.databinding.DeveloperInformationActivityBinding

/*
 * Created by eunseo5355
 * DESC: 개발자 정보  Activity
 */

class DeveloperInformationActivity : AppCompatActivity() {

	lateinit var developerInformationAdapter: DeveloperInformationAdapter
	private val developerDatas = mutableListOf<DeveloperInformationItem>()
	private val binding by lazy { DeveloperInformationActivityBinding.inflate(layoutInflater) }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)

		//RecyclerView와 Adapter 연결
		developerInformationAdapter = DeveloperInformationAdapter(this)
		binding.rvDeveloperInformation.adapter = developerInformationAdapter

		binding.imgDeveloperInformationBackBtn.setOnClickListener {
			onBackPressed()
		}
		/*img_developer_information_back_btn.setOnClickListener {
			onBackPressed()
		}*/

		loadDatas()

	}

	override fun onBackPressed() {
		super.onBackPressed()
	}

	private fun loadDatas(){
		developerDatas.apply {
			add(
					DeveloperInformationItem(
							name = "이문혁",
							role1 = "BackEnd",
							role2 = null,
							date = "2019 ~",
							href = "https://github.com/MoonHKLee"
					)
			)
			add(
					DeveloperInformationItem(
							name = "윤석노",
							role1 = "BackEnd",
							role2 = null,
							date = "2021 ~",
							href = "https://github.com/sny1032"
					)
			)
			add(
					DeveloperInformationItem(
							name = "오준현",
							role1 = "PM",
							role2 = "Design",
							date = "2019 ~",
							href = "https://github.com/5anniversary"
					)
			)
			add(
					DeveloperInformationItem(
							name = "김희재",
							role1 = "iOS",
							role2 = null,
							date = "2019 ~",
							href = "https://github.com/iowa329"
					)
			)
			add(
					DeveloperInformationItem(
							name = "김은우",
							role1 = "iOS",
							role2 = null,
							date = "2019 ~",
							href = "https://github.com/suuum12"
					)
			)
			add(
					DeveloperInformationItem(
							name = "임윤휘",
							role1 = "Android",
							role2 = null,
							date = "2020 ~",
							href = "https://github.com/88yhtserof"
					)
			)
			add(
				DeveloperInformationItem(
					name = "김지원",
					role1 = "Web",
					role2 = null,
					date = "2020 ~",
					href = "https://github.com/gwonkim"
				)
			)
			add(
					DeveloperInformationItem(
							name = "김산호",
							role1 = "Web",
							role2 = null,
							date = "2020 ~",
							href = "https://github.com/san9901"
					)
			)
			add(
					DeveloperInformationItem(
							name = "박세연",
							role1 = "QA",
							role2 = null,
							date = "2020 ~",
							href = "https://github.com/SEYEON-PARK"
					)
			)
			add(
					DeveloperInformationItem(
							name = "임우찬",
							role1 = "BackEnd",
							role2 = null,
							date = "2019 ~ 2020",
							href = "https://github.com/dladncks1217"
					)
			)
			add(
					DeveloperInformationItem(
							name = "박진수",
							role1 = "Android",
							role2 = null,
							date = "2020 ~ 2021",
							href = "https://github.com/jinsu4755"
					)
			)
			add(
					DeveloperInformationItem(
							name = "배은서",
							role1 = "Android",
							role2 = null,
							date = "2020 ~ 2021",
							href = "https://github.com/eunseo5355"
					)
			)
		}
		developerInformationAdapter.developerDatas = developerDatas
		developerInformationAdapter.notifyDataSetChanged()
	}
}
