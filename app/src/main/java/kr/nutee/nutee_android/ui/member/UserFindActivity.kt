package kr.nutee.nutee_android.ui.member

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.user_find_activity.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.extend.textChangedListener

class UserFindActivity : AppCompatActivity(), View.OnClickListener {

	val dataValid = DataValid()
	val requestToServer = RequestToServer

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.user_find_activity)
		init()
	}

	private fun init() {
		btnEvent()
		editTextEvent()
		editTextChangeEvent()
	}

	private fun btnEvent() {
		text_find_id_email_btn.setOnClickListener(this)
		text_find_pw_btn.setOnClickListener(this)
	}

	private fun editTextEvent() {

		et_find_pw_id.setOnFocusChangeListener { _, hasFocus ->
			if (hasFocus && (cl_find_id.visibility != View.GONE)) {
				hindFindId()
			}
		}
		et_find_pw_email.setOnFocusChangeListener { _, hasFocus ->
			if (hasFocus && (cl_find_id.visibility != View.GONE)) {
				hindFindId()
			}
		}

	}

	private fun hindFindId() {
		val hindAnimation = AnimationUtils.loadAnimation(this, R.anim.up_out)
		hindAnimation.setAnimationListener(object : Animation.AnimationListener {
			override fun onAnimationRepeat(animation: Animation?) = Unit
			override fun onAnimationEnd(animation: Animation?) {
				cl_find_id.visibility = View.GONE
			}

			override fun onAnimationStart(animation: Animation?) = Unit
		})
		cl_find_id.startAnimation(hindAnimation)
	}

	private fun showFindId() {
		val showAnimation = AnimationUtils.loadAnimation(this, R.anim.down_in)
		showAnimation.setAnimationListener(object : Animation.AnimationListener {
			override fun onAnimationRepeat(animation: Animation?) = Unit
			override fun onAnimationEnd(animation: Animation?) {
				cl_find_id.visibility = View.VISIBLE
			}

			override fun onAnimationStart(animation: Animation?) = Unit
		})
		cl_find_id.visibility = View.VISIBLE
		cl_find_id.startAnimation(showAnimation)
	}

	private fun editTextChangeEvent() {
		et_find_id_email.textChangedListener {
			text_find_id_email_btn.isEnabled =
				(!it.isNullOrBlank()) && dataValid.isValidEmail(et_find_id_email.text.toString())
		}
		et_find_pw_email.textChangedListener {
			text_find_pw_btn.isEnabled =
				((!it.isNullOrBlank())) && dataValid.isValidEmail(et_find_pw_email.text.toString()) && (!et_find_pw_id.text.isNullOrBlank())
		}
	}

	override fun onBackPressed() {
		if (cl_find_id.visibility == View.GONE) {
			showFindId()
		} else {
			super.onBackPressed()
		}
	}

	override fun onClick(v: View?) {
		when (v!!.id) {
			R.id.text_find_id_email_btn -> findUserId()
			R.id.text_find_pw_btn -> findUserPw()
			R.id.cl_user_find -> if (cl_find_id.visibility == View.GONE) showFindId()
		}
	}

	private fun findUserId() {
		requestToServer.authService.requestFindId(et_find_id_email.text.toString())
			.customEnqueue(
				onSuccess = {
					settingResultText(
						text_find_id_result,
						"입력하신 이메일로 아이디가 발송되었습니다.",
						getColor(R.color.nuteeBase)
					)
				},
				onError = {
					settingResultText(
						text_find_id_result,
						"존재하지 않는 이메일입니다.",
						getColor(R.color.colorRed)
					)
				}
			)
	}

	private fun findUserPw() {
		requestToServer.authService.requestFindPw(
			et_find_pw_id.text.toString(),
			et_find_pw_email.text.toString())
			.customEnqueue(
				onSuccess = {
					settingResultText(
						text_find_pw_result,
						"이메일 발송이 완료되었습니다.",
						getColor(R.color.nuteeBase)
					)
				},
				onError = {
					settingResultText(
					text_find_pw_result,
					"아이디/이메일이 일치하지 않습니다.",
					getColor(R.color.colorRed)
				)
				}
			)
	}

	// 텍스트뷰 나타나면서 흔들리는 에니메이션 적용
	private fun settingResultText(view:TextView, string: String, color:Int) {
		view.text = string
		view.setTextColor(color)
		val animation: Animation =
			AnimationUtils.loadAnimation(applicationContext, R.anim.shake)
		view.startAnimation(animation)
	}
}
