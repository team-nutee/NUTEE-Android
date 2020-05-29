package kr.nutee.nutee_android.ui.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.setting_activity.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.TermOfUseActivity
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.ui.member.LoginActivity

class SettingActivity : AppCompatActivity(), View.OnClickListener {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.setting_activity)

		init()
	}

	private fun init() {
		text_logout_button.setOnClickListener(this)
		text_termofuse_btn.setOnClickListener(this)
	}

	override fun onClick(v: View?) {
		when (v!!.id) {
			R.id.text_logout_button -> {
				App.prefs.local_login_id = null
				App.prefs.local_login_pw = null
				val intent = Intent(this, LoginActivity::class.java)
				startActivity(intent)
				finishAffinity()
			}
			R.id.text_termofuse_btn ->{
				val intent = Intent(this, TermOfUseActivity::class.java)
				startActivity(intent)
			}
		}
	}
}
