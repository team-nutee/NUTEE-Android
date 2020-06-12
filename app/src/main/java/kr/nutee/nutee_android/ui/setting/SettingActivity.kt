package kr.nutee.nutee_android.ui.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.setting_activity.*
import kotlinx.android.synthetic.main.user_find_activity.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.extend.textChangedListener
import kr.nutee.nutee_android.ui.member.DataValid
import kr.nutee.nutee_android.ui.member.LoginActivity

class SettingActivity : AppCompatActivity(), View.OnClickListener {

	private val dataValid = DataValid()
	private val requestToServer = RequestToServer

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.setting_activity)

		init()
	}

	private fun init() {
		btnEvent()
		editTextChangeEvent()
	}

	private fun btnEvent() {
		text_logout_button.setOnClickListener(this)
		text_termofuse_btn.setOnClickListener(this)
		text_change_pw_button.setOnClickListener(this)
		text_pw_change_btn.setOnClickListener(this)
	}

	private fun editTextChangeEvent() {
		et_do_change_pw.textChangedListener {
			text_change_pw_button.isEnabled = (!it.isNullOrBlank())
		}
		et_pw_change_pw.textChangedListener {
			if (dataValid.isValidPassword(et_pw_change_pw.text.toString())) {
				settingResultText(
					text_pw_change_result,
					"사용 가능한 비밀번호 입니다.",
					getColor(R.color.nuteeBase)
				)
			} else {
				settingResultText(
					text_pw_change_result,
					"8자 이상의 영어 대문자, 소문자, 숫자가 포함된 비밀번호를 입력해주세요.",
					getColor(R.color.colorRed)
				)
			}
		}
		et_pw_change_check.textChangedListener {
			if (et_pw_change_pw.text.toString() == et_pw_change_check.text.toString()) {
				settingResultText(
					text_pw_change_check_result,
					"",
					getColor(R.color.nuteeBase)
				)
				text_pw_change_btn.isEnabled = true
			} else {
				settingResultText(
					text_pw_change_check_result,
					"비밀번호가 일치하지 않습니다.",
					getColor(R.color.colorRed)
				)
			}
		}
	}

	override fun onClick(v: View?) {
		when (v!!.id) {
			R.id.text_logout_button -> {
				requestLogout()
			}
			R.id.text_termofuse_btn -> {
				val intent = Intent(this, TermOfUseActivity::class.java)
				startActivity(intent)
			}
			R.id.text_change_pw_button -> requestPasswordCheck()
			R.id.text_pw_change_btn -> requestChagePassword()
		}
	}

	private fun requestPasswordCheck() {
		requestToServer.service.requestPasswordCheck(
			App.prefs.local_login_token,
			et_do_change_pw.text.toString()
		).customEnqueue {
			if (it.isSuccessful) {
				settingResultText(
					text_change_pw_result,
					"비밀번호가 확인이 완료되었습니다.",
					getColor(R.color.nuteeBase)
				)
				inAnim(cl_pw_change)
			} else {
				settingResultText(
					text_change_pw_result,
					"비밀번호가 일치하지 않습니다.",
					getColor(R.color.colorRed)
				)
			}
		}
	}

	private fun requestChagePassword() {
		requestToServer.service.requestChagePassword(
			App.prefs.local_login_token,
			et_pw_change_pw.text.toString()
		).customEnqueue {
			if (it.isSuccessful) {
				Toast.makeText(this,"비밀번호가 변경되었습니다. \n 다시한번 로그인 해주세요!",Toast.LENGTH_SHORT).show()
				val intent = Intent(this, LoginActivity::class.java)
				startActivity(intent)
				clearUserData()
				finishAffinity()
			} else {
				settingResultText(
					text_pw_change_check_result,
					"비밀번호 변경에 실패하였습니다.",
					getColor(R.color.nuteeBase)
				)
			}
		}
	}

	private fun requestLogout() {
		requestToServer.service.reqeustLogout(
			App.prefs.PREFS_TOKEN
		).customEnqueue {
			if (it.isSuccessful) {
				Log.d("Logout",it.message())
				clearUserData()
				val intent = Intent(this, LoginActivity::class.java)
				startActivity(intent)
				finishAffinity()
			}
		}
	}

	private fun clearUserData() {
		App.prefs.local_login_id = ""
		App.prefs.local_login_pw = ""
		App.prefs.local_login_token = ""
	}

	// 텍스트뷰 나타나면서 흔들리는 에니메이션 적용
	private fun settingResultText(view: TextView, string: String, color:Int) {
		view.text = string
		view.setTextColor(color)
		val animation: Animation =
			AnimationUtils.loadAnimation(applicationContext, R.anim.shake)
		view.startAnimation(animation)
	}

	private fun inAnim(cl:ConstraintLayout) {
		val showAnimation = AnimationUtils.loadAnimation(this, R.anim.down_in)
		showAnimation.setAnimationListener(object : Animation.AnimationListener {
			override fun onAnimationRepeat(animation: Animation?) = Unit
			override fun onAnimationEnd(animation: Animation?) {
				cl.visibility = View.VISIBLE
			}
			override fun onAnimationStart(animation: Animation?) = Unit
		})
		cl.startAnimation(showAnimation)
	}
}
