package kr.nutee.nutee_android.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.setting_activity.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.databinding.SettingActivityBinding
import kr.nutee.nutee_android.ui.extend.loadFragment
import kr.nutee.nutee_android.ui.setting.fragment.MainSettingFragment
import kr.nutee.nutee_android.ui.setting.fragment.ProfileImageSettingFragment

/*
 * Created by eunseo5355
 * DESC: 설정 Activity
 */

class SettingActivity : AppCompatActivity() {

	private val binding by lazy {SettingActivityBinding.inflate(layoutInflater) }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)

		loadFragment(MainSettingFragment(), binding.frameLayoutSetting.id)

		binding.imgSettingBackBtn.setOnClickListener {
			onBackPressed()
		}
	}

	override fun onBackPressed() {
		super.onBackPressed()
	}

}

