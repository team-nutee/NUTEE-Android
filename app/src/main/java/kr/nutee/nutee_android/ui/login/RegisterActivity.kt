package kr.nutee.nutee_android.ui.login

import android.animation.ObjectAnimator
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.register_activity.*
import kr.nutee.nutee_android.R


class RegisterActivity : AppCompatActivity(), View.OnClickListener {

	var page : Int = 1

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.register_activity)

		init()
	}

	private fun init() {
		/*초기 설정 메소드*/
		et_register_email.addTextChangedListener(emailWatcher)
		text_register_email_btn.setOnClickListener(this)

		et_auth_num.addTextChangedListener(authNumWatcher)
		text_auth_num_btn.setOnClickListener(this)

		updateProgress(0, 1)
	}

	private fun updateProgress(current_status: Int, next_status: Int) {
		//프로그래스 바 업데이트
		val progressAnimator =
			ObjectAnimator.ofInt(pb_register_progress_bar, "progress", current_status, next_status)
		progressAnimator.duration = 700
		progressAnimator.interpolator = LinearInterpolator()
		progressAnimator.start()
	}

	private fun nextPage() {
		when (page) {
			1->{}

		}
	}

	//페이지 전환 이벤트 메소드
	private fun nextAnim(prevLayout:ConstraintLayout, nextLayout: ConstraintLayout){
		nextLayout.visibility = View.VISIBLE


	}

	//페이지 전환 이벤트 메소드
	private fun next_to_id(prevLayout:ConstraintLayout, nextLayout: ConstraintLayout ){
		/*cl_register_email 을 사라지게한다.
		* cl_register_password 를 나타나게 한다.
		* 값 검사 이후에 페이지를 넘기며 email 관련 정보를 변수에 담아서 처리한다.*/
		page = 2

		val inAnimation = AnimationUtils.loadAnimation(applicationContext,R.anim.up_in)
		val outAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.up_out)
		outAnimation.setAnimationListener(object:Animation.AnimationListener{
			override fun onAnimationRepeat(animation: Animation?)=Unit

			override fun onAnimationEnd(animation: Animation?) {
				//out된것은 Gone처리, in되는것은 보이도록 설정
				prevLayout.visibility = View.GONE
			}

			override fun onAnimationStart(animation: Animation?) = Unit

		})
		prevLayout.startAnimation(outAnimation)
		nextLayout.startAnimation(inAnimation)

	}

	private fun backPressDialog(){
		//뒤로가기 버튼을 클릭한경우 custom dialog를 띄우고 이벤트 처리
		val dialog = Dialog(this)
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
		dialog.setCancelable(false)
		dialog.setContentView(R.layout.custom_dialog)

		val body = dialog.findViewById<TextView>(R.id.dialog_text)
		body.text = "회원가입을 종료하실껀가요?\uD83D\uDE25"
		val okButton = dialog.findViewById<Button>(R.id.okButton)
		val cancelButton = dialog.findViewById<Button>(R.id.cancelButton)

		dialog.show()

		okButton.setOnClickListener {
			dialog.dismiss()
			onBackPressed()
		}
		cancelButton.setOnClickListener { dialog.dismiss() }
	}

	override fun onBackPressed() {
		when (page) {
			1 -> {
				backPressDialog()
				super.onBackPressed()
			}
		}
	}

	override fun onClick(v: View?) {
		when (v!!.id) {
			//page 1
			R.id.text_register_email_btn -> {
				val animation: Animation =
					AnimationUtils.loadAnimation(applicationContext, R.anim.down_alpha_translate)
				ll_register_auth_num.startAnimation(animation)
				ll_register_auth_num.visibility
			}
			R.id.text_auth_num_btn -> {
				pb_register_progress_bar.progress = 2
			}
		}
	}

	private val emailWatcher = object : TextWatcher {
		override fun afterTextChanged(s: Editable?) {
			text_register_email_btn.isEnabled = !s.isNullOrBlank()
		}
		override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
		override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) =Unit
	}
	private val authNumWatcher = object : TextWatcher {
		override fun afterTextChanged(s: Editable?) {
			text_auth_num_btn.isEnabled = !s.isNullOrBlank()
		}
		override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
		override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

	}

}
