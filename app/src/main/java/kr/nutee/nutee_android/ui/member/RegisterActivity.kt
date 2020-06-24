package kr.nutee.nutee_android.ui.member

import android.animation.ObjectAnimator
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.register_activity.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.member.register.*
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.changeLayoutDown
import kr.nutee.nutee_android.ui.extend.dialog.customDialog
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.extend.textChangedListener


class RegisterActivity : AppCompatActivity(), View.OnClickListener {

	var page: Int = 1
	private var user_id: String = ""
	private var user_pw: String = ""
	private var user_email : String = ""
	private var user_nick : String = ""

	val requestToServer = RequestToServer

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.register_activity)

		init()
	}

	/*초기 설정 메소드*/
	private fun init() {
		//프로그래스 바 설정
		updateProgress(0, 1)
		cl_register_email.visibility = View.VISIBLE

		/*각 페이지마다 입력칸 이벤트 설정, 버튼 이벤트 지정*/
		textChangedEventMappging()
		buttonEventMappging()
	}

	private fun buttonEventMappging() {
		/*page == 1*/
		text_register_email_btn.setOnClickListener(this)
		text_auth_num_btn.setOnClickListener(this)
		text_register_next_btn1.setOnClickListener(this)
		/*page==2*/
		text_register_id_btn.setOnClickListener(this)
		text_register_next_btn2.setOnClickListener(this)
		/*page==3*/
		text_register_nick_btn.setOnClickListener(this)
		text_register_next_btn3.setOnClickListener(this)
		/*page==4*/
		text_register_password_btn.setOnClickListener(this)
		text_register_password_check_btn.setOnClickListener(this)
		text_register_ok_btn.setOnClickListener(this)
	}
	private fun textChangedEventMappging() {
		/*page == 1*/
		et_register_email.textChangedListener {
			text_register_email_btn.isEnabled =
				(!it.isNullOrBlank()) && isValidEmail(et_register_email.text.toString())
		}
		et_auth_num.textChangedListener {
			text_auth_num_btn.isEnabled = !it.isNullOrBlank()
		}
		/*page==2*/
		et_register_id.textChangedListener {
			text_register_id_btn.isEnabled = (!it.isNullOrBlank())&& isValidId(et_register_id.text.toString())
			if (!isValidId(et_register_id.text.toString()))
				showTextShake(text_register_id_text,"영문 혹은 숫자만 이용가능합니다.",getColor(R.color.colorRed))
		}
		/*page==3*/
		et_register_nick.textChangedListener {
			text_register_nick_btn.isEnabled = !it.isNullOrBlank()
		}
		/*page==4*/
		et_register_password.textChangedListener {
			text_register_password_btn.isEnabled = (!it.isNullOrBlank())&& isValidPassword(et_register_password.text.toString())
			if (isValidPassword(et_register_password.text.toString())) {
				showTextShake(
					text_register_pw_text,
					"",
					getColor(R.color.nuteeBase)
				)
			} else {
				showTextShake(
					text_register_pw_text,
					"8자 이상의 영어 대문자, 소문자, 숫자가 포함된 비밀번호를 입력해주세요.",
					getColor(R.color.colorRed)
				)
			}
		}
		et_register_password_check.textChangedListener {
			text_register_password_check_btn.isEnabled = !it.isNullOrBlank()
		}
	}

	/*프로그래스 바 업데이트 함수*/
	private fun updateProgress(current_status: Int, next_status: Int) {
		val progressAnimator =
			ObjectAnimator.ofInt(pb_register_progress_bar, "progress", current_status, next_status)
		progressAnimator.duration = 700 //지속 시간
		progressAnimator.interpolator = LinearInterpolator() //궤적 생성 방법
		progressAnimator.start()
	}

	override fun onClick(v: View?) {
		when (v!!.id) {
			//page 1
			R.id.text_register_email_btn -> requestToEmailOTP()
			R.id.text_auth_num_btn -> requestToOTP(text_register_next_btn1)
			R.id.text_register_next_btn1->{
				pb_register_progress_bar.progress = ++page
				changeLayoutDown(cl_register_email, cl_register_id) {
					showEditTextAlphaTranslate(ll_register_id)
				}
			}
			//page 2
			R.id.text_register_id_btn -> requestIdCheck(text_register_next_btn2)
			R.id.text_register_next_btn2->{
				pb_register_progress_bar.progress = ++page
				changeLayoutDown(cl_register_id, cl_register_nick) {
					showEditTextAlphaTranslate(ll_register_nick)
				}
			}
			//page 3
			R.id.text_register_nick_btn -> requestNicknameCheck(text_register_next_btn3)
			R.id.text_register_next_btn3->{
				pb_register_progress_bar.progress = ++page
				changeLayoutDown(cl_register_nick, cl_register_password) {
					showEditTextAlphaTranslate(ll_register_password)
				}
			}
			//page 4
			R.id.text_register_password_btn ->{
				user_pw = et_register_password.text.toString()
				showEditTextAlphaTranslate(ll_register_password_check)
			}
			R.id.text_register_password_check_btn ->{
				if (passwordCheck()) {
					showTextShake(text_register_pw_check_text,"비밀번호 확인 완료.",getColor(R.color.nuteeBase))
					text_register_ok_btn.isEnabled = true
				} else {
					showTextShake(text_register_pw_check_text,"비밀번호가 다릅니다",getColor(R.color.colorRed))
				}
			}
			R.id.text_register_ok_btn -> {
				requestRegister()
			}
		}
	}

	override fun onBackPressed() {
		when (page) {
			1 -> {
				customDialog(
					"회원가입을 종료하실껀가요?\uD83D\uDE25"
				) { super.onBackPressed() }
			}
			2 -> {
				pb_register_progress_bar.progress = --page
				changeLayoutDown(cl_register_id, cl_register_email) {}
			}
			3 -> {
				pb_register_progress_bar.progress = --page
				changeLayoutDown(cl_register_nick, cl_register_id) {}
			}
			else -> {
				pb_register_progress_bar.progress = --page
				changeLayoutDown(cl_register_password, cl_register_nick) {}
			}
		}
	}

	/* 서버 연결 관련 */
	private fun requestToEmailOTP() {
		requestToServer.service.requestEmailOTP(
			RequestEmailOTP(
				schoolEmail = et_register_email.text.toString()
			)
		).customEnqueue(
			onSuccess = {
				Log.d("Send OTP Status", it.code().toString())
				if (it.code()==200) {
					showTextShake(text_register_email_text, "입력하신 이메일로 OTP 인증번호가 발송되었습니다.", getColor(R.color.nuteeBase))
					showEditTextAlphaTranslate(ll_register_auth_num)
				}
				else if (it.code() == 409) {
					showTextShake(text_register_email_text, "이미 가입된 이메일입니다.", getColor(R.color.colorRed))
				}
			}
		)
	}

	private fun requestToOTP(next_btn:TextView) {
		requestToServer.service.requestOTPCheck(
			RequestOTPCheck(
				otpcheck = et_auth_num.text.toString()
			)
		).customEnqueue(
			onSuccess = {
				if (it.code() == 200) {
					showTextShake(text_auth_num_text, "OTP 인증에 성공했습니다.", getColor(R.color.nuteeBase))
					next_btn.isEnabled = true
					user_email = et_register_email.text.toString()
				} else {
					Log.d("code",it.code().toString())
					showTextShake(text_auth_num_text, "잘못된 인증번호입니다.", getColor(R.color.colorRed))
				}
			}
		)
	}

	private fun requestIdCheck(next_btn:TextView) {
		requestToServer.service.requestIdCheck(
			RequestIdCheck(
				userId = et_register_id.text.toString()
			)
		).customEnqueue {
			when (it.code()) {
				200 -> {
					showTextShake(text_register_id_text,"아이디 중복체크 성공.", getColor(R.color.nuteeBase))
					next_btn.isEnabled = true
					user_id = et_register_id.text.toString()
				}
				409 -> showTextShake(text_register_id_text,"현재 로그인 중입니다.", getColor(R.color.colorRed))
				else -> showTextShake(text_register_id_text,"이미 사용중인 아이디입니다.", getColor(R.color.colorRed))
			}
		}
	}

	private fun requestNicknameCheck(next_btn:TextView) {
		requestToServer.service.requestNickCheck(
			RequestNickCheck(
				userId = user_id
			)
		).customEnqueue {
			when (it.code()) {
				200 ->{
					showTextShake(text_register_nick_text,"사용 가능한 닉네임 입니다",getColor(R.color.nuteeBase))
					next_btn.isEnabled = true
					user_nick = et_register_nick.text.toString()
				}
				401 -> showTextShake(text_register_nick_text,"현재 로그인 중입니다.",getColor(R.color.colorRed))
				409 -> showTextShake(text_register_nick_text,"이미 사용중인 닉네임 입니다",getColor(R.color.colorRed))
			}
		}
	}

	private fun requestRegister() {
		requestToServer.service.requestRegister(
			RequestRegister(
				userId = user_id,
				password = user_pw,
				nickname = user_nick,
				schoolEmail = user_email
			)
		).customEnqueue {
			when (it.code()) {
				200 -> {
					intent.putExtra("id", user_id)
					intent.putExtra("pw", user_pw)
					setResult(Activity.RESULT_OK, intent)
					finish()
				}
			}
		}
	}

	/* 이메일 정규식 확인 */
	private fun isValidEmail(email: String): Boolean {
		val reg = Regex("^[a-zA-Z0-9._%+-]+@office.skhu.ac.kr")
		return if (email.matches(reg)) {
			Log.d("RegisterValid", email.matches(reg).toString())
			email.matches(reg)
		} else email.matches(Regex("nutee.skhu.2020@gmail.com"))
	}
	/*아이디 정규식 확인*/
	private fun isValidId(id: String): Boolean {
		val reg = Regex("^[a-zA-Z0-9]*\$")
		Log.d("IdValid", id.matches(reg).toString())
		return id.matches(reg)
	}
	/*비밀번호 정규식 확인*/
	private fun isValidPassword(pw: String): Boolean {
		val reg = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_+-]).{7,15}.\$")
		Log.d("IdValid", pw.matches(reg).toString())
		return pw.matches(reg)
	}

	/*비밀번호 확인*/
	private fun passwordCheck(): Boolean {
		return (user_pw == et_register_password_check.text.toString())
	}

	/* 애니메이션 함수 */

	// 리니어 레이아웃이 나타나면서 내려오기 애니메이션 적용 함수
	private fun showEditTextAlphaTranslate(mylayout: LinearLayout) {
		val animation: Animation =
			AnimationUtils.loadAnimation(applicationContext, R.anim.down_alpha_translate)
		mylayout.startAnimation(animation)
		mylayout.visibility = View.VISIBLE
	}

	// 텍스트뷰 나타나면서 흔들리는 에니메이션 적용
	private fun showTextShake(myTextView: TextView, msg: String, color: Int) {
		myTextView.text = msg
		myTextView.setTextColor(color)
		val animation: Animation =
			AnimationUtils.loadAnimation(applicationContext, R.anim.shake)
		myTextView.startAnimation(animation)
		myTextView.visibility = View.VISIBLE
	}

}
