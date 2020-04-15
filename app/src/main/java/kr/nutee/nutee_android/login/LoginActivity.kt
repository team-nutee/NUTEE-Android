package kr.nutee.nutee_android.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.main.MainActivity

class LoginActivity : AppCompatActivity(), View.OnClickListener {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_login)

		btn_login.setOnClickListener(this)
	}


	//클릭 이벤트 구현
	override fun onClick(v: View?) {
		//해당 View의 id 값을 가져올 변수 id 생성
		val id = v!!.id

		//각 버튼인 경우 동작 매칭
		when (id) {
			//로그인 버튼 클릭시
			R.id.btn_login -> {
				Log.d("LoginButton:", "login button click")

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

		}
	}


}
