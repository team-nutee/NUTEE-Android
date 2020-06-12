package kr.nutee.nutee_android.ui.member

import android.util.Log

class DataValid {
	/* 이메일 정규식 확인 */
	fun isValidEmail(email: String): Boolean {
		val reg = Regex("^[a-zA-Z0-9._%+-]+@office.skhu.ac.kr")
		return if (email.matches(reg)) {
			Log.d("RegisterValid", email.matches(reg).toString())
			email.matches(reg)
		} else email.matches(Regex("nutee.skhu.2020@gmail.com"))
	}
	/*아이디 정규식 확인*/
	fun isValidId(id: String): Boolean {
		val reg = Regex("^[a-zA-Z0-9]*\$")
		Log.d("IdValid", id.matches(reg).toString())
		return id.matches(reg)
	}
	/*비밀번호 정규식 확인*/
	fun isValidPassword(pw: String): Boolean {
		val reg = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_+-]).{7,15}.\$")
		Log.d("IdValid", pw.matches(reg).toString())
		return pw.matches(reg)
	}
}