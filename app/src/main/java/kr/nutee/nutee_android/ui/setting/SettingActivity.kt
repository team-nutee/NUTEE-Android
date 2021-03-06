package kr.nutee.nutee_android.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.setting_activity.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.ui.extend.loadFragment
import kr.nutee.nutee_android.ui.setting.fragment.MainSettingFragment
import kr.nutee.nutee_android.ui.setting.fragment.ProfileImageSettingFragment

/*
 * Created by eunseo5355
 * DESC: 설정 Activity
 */

class SettingActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.setting_activity)

		loadFragment(MainSettingFragment(),R.id.frame_layout_setting)

		img_setting_back_btn.setOnClickListener {
			onBackPressed()
		}

	}

	override fun onBackPressed() {
		super.onBackPressed()
	}

}

