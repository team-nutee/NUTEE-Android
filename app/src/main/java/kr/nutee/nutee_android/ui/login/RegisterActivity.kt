package kr.nutee.nutee_android.ui.login

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.register_activity.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.ui.extend.changeLayout_down
import kr.nutee.nutee_android.ui.extend.customDialog
import kr.nutee.nutee_android.ui.extend.textChangedListener


class RegisterActivity : AppCompatActivity(), View.OnClickListener {

	var page : Int = 1

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

		/*page == 1*/
		//이메일 인증칸 조건 만족시 버튼 보이게 하고 해당 버튼 클릭시 이벤트 적용
		et_register_email.textChangedListener {
			text_register_email_btn.isEnabled = !it.isNullOrBlank()
		}
		text_register_email_btn.setOnClickListener(this)

		//인증 번호칸 조건 만족시 버튼 보이게 하고 해당 버튼 클릭시 이벤트 적용
		et_auth_num.textChangedListener {
			text_auth_num_btn.isEnabled = !it.isNullOrBlank()
		}
		text_auth_num_btn.setOnClickListener(this)
		/*page==2*/
		et_register_id.textChangedListener {
			text_register_id_btn.isEnabled = !it.isNullOrBlank()
		}

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
				pb_register_progress_bar.progress = ++page
				changeLayout_down(cl_register_id,cl_register_nick){
					showEditTextAlphaTranslate(ll_register_nick)
				}
			}
			//page 3
			R.id.text_register_nick_btn ->{
				pb_register_progress_bar.progress = ++page
				changeLayout_down(cl_register_nick,cl_register_password){
					showEditTextAlphaTranslate(ll_register_password)
					showEditTextAlphaTranslate(ll_register_password_check)
				}
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
				page--
				changeLayout_down(cl_register_id, cl_register_email){}
			}
			3 -> {
				page--
			}
			else -> {
				page--
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
