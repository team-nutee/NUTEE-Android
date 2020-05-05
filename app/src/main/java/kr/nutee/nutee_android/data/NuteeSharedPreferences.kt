package kr.nutee.nutee_android.data

import android.content.Context
import android.content.SharedPreferences

/*
* NUTEE 사용자 정보를 로컬에 저장하는 class
*/
class NuteeSharedPreferences(context: Context){
	val PREFS_FILENAME = "prefs"
	//회원 정보
	val PREFS_LOCAL_LOGIN_ID = "local_login_id"
	val PREFS_LOCAL_LOGIN_PW = "local_login_pw"

	val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME,0)
	val editor = prefs.edit()

	var local_login_id : String?
		get() = prefs.getString(PREFS_LOCAL_LOGIN_ID, null)
		set(value) = editor.putString(PREFS_LOCAL_LOGIN_ID, value).apply()

	var local_login_pw : String?
		get() = prefs.getString(PREFS_LOCAL_LOGIN_PW, null)
		set(value) = editor.putString(PREFS_LOCAL_LOGIN_PW, value).apply()

}