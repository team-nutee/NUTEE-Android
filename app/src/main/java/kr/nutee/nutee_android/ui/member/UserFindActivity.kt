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
import kr.nutee.nutee_android.data.member.find.RequestFindPw
import kr.nutee.nutee_android.data.member.register.RequestEmail
import kr.nutee.nutee_android.databinding.UserFindActivityBinding
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.extend.textChangedListener

class UserFindActivity : AppCompatActivity(), View.OnClickListener {

	private val binding by lazy { UserFindActivityBinding.inflate(layoutInflater) }
	val dataValid = DataValid()
	val requestToServer = RequestToServer

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)
		init()
	}

	private fun init() {
		btnEvent()
		editTextEvent()
		editTextChangeEvent()
		registerButtonEventMapping()
	}

	private fun btnEvent() {
		binding.textFindIdEmailBtn.setOnClickListener(this)
		binding.textFindPwBtn.setOnClickListener(this)
	}

	private fun editTextEvent() {

		binding.etFindPwId.setOnFocusChangeListener { _, hasFocus ->
			if(hasFocus && (binding.clFindId.visibility != View.GONE)){
				hindFindId()
			}
		}
		binding.etFindPwEmail.setOnFocusChangeListener { _, hasFocus ->
			if(hasFocus && (binding.clFindId.visibility != View.GONE)){
				hindFindId()
			}
		}

	}

	private fun hindFindId() {
		val hindAnimation = AnimationUtils.loadAnimation(this, R.anim.up_out)
		hindAnimation.setAnimationListener(object : Animation.AnimationListener {
			override fun onAnimationRepeat(animation: Animation?) = Unit
			override fun onAnimationEnd(animation: Animation?) {
				binding.clFindId.visibility = View.GONE
			}

			override fun onAnimationStart(animation: Animation?) = Unit
		})
		binding.clFindId.startAnimation(hindAnimation)
	}

	private fun showFindId() {
		val showAnimation = AnimationUtils.loadAnimation(this, R.anim.down_in)
		showAnimation.setAnimationListener(object : Animation.AnimationListener {
			override fun onAnimationRepeat(animation: Animation?) = Unit
			override fun onAnimationEnd(animation: Animation?) {
				binding.clFindId.visibility = View.VISIBLE
			}

			override fun onAnimationStart(animation: Animation?) = Unit
		})
		binding.clFindId.visibility = View.VISIBLE
		binding.clFindId.startAnimation(showAnimation)
	}

	private fun editTextChangeEvent() {
		binding.etFindIdEmail.textChangedListener {
			binding.textFindIdEmailBtn.isEnabled =
					(!it.isNullOrBlank()) && dataValid.isValidEmail(binding.etFindIdEmail.text.toString())
		}
		binding.etFindPwEmail.textChangedListener {
			binding.textFindPwBtn.isEnabled =
				((!it.isNullOrBlank())) && dataValid.isValidEmail(binding.etFindPwEmail.text.toString()) && (!binding.etFindPwId.text.isNullOrBlank())
		}
	}

	override fun onBackPressed() {
		if (binding.clFindId.visibility == View.GONE) {
			showFindId()
		} else {
			super.onBackPressed()
		}
	}

	override fun onClick(v: View?) {
		when (v!!.id) {
			binding.textFindIdEmailBtn.id -> findUserId()
			binding.textFindPwBtn.id -> findUserPw()
			binding.clUserFind.id -> if (binding.clFindId.visibility == View.GONE) showFindId()

			/*R.id.text_find_id_email_btn -> findUserId()
			R.id.text_find_pw_btn -> findUserPw()
			R.id.cl_user_find -> if (binding.clFindId.visibility == View.GONE) showFindId()*/
		}
	}

	private fun findUserId() {
		requestToServer.authService.requestFindId(
				RequestEmail(
						binding.etFindIdEmail.text.toString()
				)).customEnqueue(
				onSuccess = {
					settingResultText(
						binding.textFindIdResult,
						"입력하신 이메일로 아이디가 발송되었습니다.",
						getColor(R.color.nuteeBase)
					)
				},
				onError = {
					settingResultText(
						binding.textFindIdResult,
						"존재하지 않는 이메일입니다.",
						getColor(R.color.colorRed)
					)
				}
			)
	}

	private fun findUserPw() {
		requestToServer.authService.requestFindPw(
				RequestFindPw(
						binding.etFindPwEmail.text.toString(),
						binding.etFindPwId.text.toString()
				)).customEnqueue(
				onSuccess = {
					settingResultText(
						binding.textFindPwResult,
						it.body()!!.body,
						getColor(R.color.nuteeBase)
					)
				},
				onError = {
					settingResultText(
					binding.textFindPwResult,
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

	private fun registerButtonEventMapping() {
		binding.textUserFindBack.setOnClickListener {
			super.onBackPressed()
		}
	}
}
