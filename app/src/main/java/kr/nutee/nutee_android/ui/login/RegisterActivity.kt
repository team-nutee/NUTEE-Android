package kr.nutee.nutee_android.ui.login

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.register_activity.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.ui.extend.changeLayout_down
import kr.nutee.nutee_android.ui.extend.customDialog
import kr.nutee.nutee_android.ui.extend.textChangedListener


class RegisterActivity : AppCompatActivity(), View.OnClickListener {

	var page : Int = 1
	private var user_id: String = ""
	private var user_pw: String = ""

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
		/*page == 1*/
		et_register_email.textChangedListener {
			text_register_email_btn.isEnabled = !it.isNullOrBlank()
		}
		text_register_email_btn.setOnClickListener(this)
		et_auth_num.textChangedListener {
			text_auth_num_btn.isEnabled = !it.isNullOrBlank()
		}
		text_auth_num_btn.setOnClickListener(this)

		/*page==2*/
		et_register_id.textChangedListener {
			text_register_id_btn.isEnabled = !it.isNullOrBlank()
		}
		text_register_id_btn.setOnClickListener(this)

		/*page==3*/
		et_register_nick.textChangedListener {
			text_register_nick_btn.isEnabled = !it.isNullOrBlank()
		}
		text_register_nick_btn.setOnClickListener(this)

		/*page==4*/
		et_register_password.textChangedListener {
			text_register_password_btn.isEnabled = !it.isNullOrBlank()
		}
		et_register_password_check.textChangedListener {
			text_register_password_check_btn.isEnabled = !it.isNullOrBlank()
			text_register_ok_btn.isEnabled = !it.isNullOrBlank()
		}
		text_register_password_btn.setOnClickListener(this)
		text_register_password_check_btn.setOnClickListener(this)

		text_register_ok_btn.setOnClickListener(this)
	}

	private fun updateProgress(current_status: Int, next_status: Int) {
		//프로그래스 바 업데이트
		val progressAnimator =
			ObjectAnimator.ofInt(pb_register_progress_bar, "progress", current_status, next_status)
		progressAnimator.duration = 700 //지속 시간
		progressAnimator.interpolator = LinearInterpolator() //궤적 생성 방법
		progressAnimator.start()
	}

	override fun onClick(v: View?) {
		val intent = Intent()

		when (v!!.id) {
			//page 1
			R.id.text_register_email_btn -> {
				showEditTextAlphaTranslate(ll_register_auth_num)
			}
			R.id.text_auth_num_btn -> {
				pb_register_progress_bar.progress = ++page
				changeLayout_down(cl_register_email, cl_register_id) {
					showEditTextAlphaTranslate(ll_register_id)
				}
			}
			//page 2
			R.id.text_register_id_btn -> {
				user_id = et_register_id.text.toString()
				pb_register_progress_bar.progress = ++page
				ll_register_id.visibility = View.INVISIBLE
				changeLayout_down(cl_register_id,cl_register_nick){
					showEditTextAlphaTranslate(ll_register_nick)
				}
			}
			//page 3
			R.id.text_register_nick_btn ->{
				pb_register_progress_bar.progress = ++page
				ll_register_nick.visibility = View.INVISIBLE
				changeLayout_down(cl_register_nick,cl_register_password){
					showEditTextAlphaTranslate(ll_register_password)
					showEditTextAlphaTranslate(ll_register_password_check)
				}
			}
			//page 4
			R.id.text_register_ok_btn ->{
				user_pw = et_register_password.text.toString()
				intent.putExtra("id", user_id)
				intent.putExtra("pw", user_pw)
				setResult(Activity.RESULT_OK, intent)
				finish()
			}
		}
	}

	override fun onBackPressed() {
		when (page) {
			1 -> {
				customDialog(
					"회원가입을 종료하실껀가요?\uD83D\uDE25"
				) {super.onBackPressed()}
			}
			2 ->{
				pb_register_progress_bar.progress = --page
				changeLayout_down(cl_register_id, cl_register_email){}
			}
			3 -> {
				pb_register_progress_bar.progress = --page
				changeLayout_down(cl_register_nick,cl_register_id){}
			}
			else -> {
				pb_register_progress_bar.progress = --page
				changeLayout_down(cl_register_password, cl_register_nick){}
			}
		}
	}
	/* 애니메이션 함수 */

	// 나타나면서 내려오기 애니메이션 적용 함수
	private fun showEditTextAlphaTranslate(mylayout: LinearLayout) {
		val animation: Animation =
			AnimationUtils.loadAnimation(applicationContext, R.anim.down_alpha_translate)
		mylayout.startAnimation(animation)
		mylayout.visibility
	}

}
