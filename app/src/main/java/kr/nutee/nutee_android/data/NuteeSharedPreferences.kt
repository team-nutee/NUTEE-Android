package kr.nutee.nutee_android.data

import android.content.Context
import android.content.SharedPreferences
import com.securepreferences.SecurePreferences


/*
* NUTEE 사용자 정보를 로컬에 저장하는 class
*/
class NuteeSharedPreferences(context: Context){
	val PREFS_FILENAME = "prefs"
	//회원 정보
	val PREFS_LOCAL_LOGIN_ID = "local_login_id"
	val PREFS_LOCAL_LOGIN_PW = "local_login_pw"
	val PREFS_TOKEN = "local_login_token"
	val PREFS_LOCAL_USER_ID = "user_id"
	val PREFS_LOCAL_LOGIN_OTO="local_login_oto"

	//SharedPreferences를 암호화하여 저장한다.
	val prefs : SharedPreferences = SecurePreferences(context, "userpassword", PREFS_FILENAME)
	val editor = prefs.edit()

	var local_login_id : String
		get() = prefs.getString(PREFS_LOCAL_LOGIN_ID, "")!!
		set(value) = editor.putString(PREFS_LOCAL_LOGIN_ID, value).apply()

	var local_login_pw : String
		get() = prefs.getString(PREFS_LOCAL_LOGIN_PW, "")!!
		set(value) = editor.putString(PREFS_LOCAL_LOGIN_PW, value).apply()

	var local_login_token : String
		get() = prefs.getString(PREFS_TOKEN, "")!!
		set(value) = editor.putString(PREFS_TOKEN, value).apply()

	var local_user_id: String
		get() = prefs.getString(PREFS_LOCAL_USER_ID, "")!!
		set(value) = editor.putString(PREFS_LOCAL_USER_ID,value).apply()

	//자동 로그인 설정
	var local_login_oto:Boolean
		get() = prefs.getBoolean(PREFS_LOCAL_LOGIN_OTO,false)
		set(value) = editor.putBoolean(PREFS_LOCAL_LOGIN_OTO,value).apply()
}