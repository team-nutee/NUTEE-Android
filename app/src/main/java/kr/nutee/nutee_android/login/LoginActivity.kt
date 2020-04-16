package kr.nutee.nutee_android.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.login_activity.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.main.MainActivity

class LoginActivity : AppCompatActivity(), View.OnClickListener {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.login_activity)

		text_forget_id_or_pw_button.setOnClickListener(this)
		btn_login.setOnClickListener(this)
		text_register_button.setOnClickListener(this)
	}


	//클릭 이벤트 구현
	override fun onClick(v: View?) {
		val logTag = "LoginActivityButtonEv"
		//해당 View의 id 값을 가져올 변수 id 생성
		val id = v!!.id

		//각 버튼인 경우 동작 매칭
		when (id) {
			//id&pw 찾기 버튼 클릭시
			R.id.text_forget_id_or_pw_button -> {
				Log.v(logTag, "forget id or pw text button test")
			}

			//로그인 버튼 클릭시
			R.id.btn_login -> {
				Log.v(logTag, "login button click")

				if (et_login_id.text.isNullOrBlank() || et_login_pw.text.isNullOrBlank()) {
					when (true) {
						et_login_id.text.isNullOrBlank() -> Snackbar.make(v, "아이디를 입력하세요", Snackbar.LENGTH_SHORT).show()
						et_login_pw.text.isNullOrBlank() -> Snackbar.make(v, "패스워드를 입력하세요", Snackbar.LENGTH_SHORT).show()
					}
				} else {
					val intent = Intent(this, MainActivity::class.java)
					startActivity(intent)
				}
			}

			//회원가입 버튼 클릭시
			R.id.text_register_button -> {
				Log.v(logTag, "register button test")

				val intent = Intent(this, RegisterActivity::class.java)
				startActivity(intent)
			}

		}
	}


}
