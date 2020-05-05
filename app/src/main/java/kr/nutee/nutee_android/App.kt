package kr.nutee.nutee_android

import android.app.Application
import kr.nutee.nutee_android.data.NuteeSharedPreferences

class App:Application() {

	// onCreate이전에 prefs를 먼저 초기화 한다.
	companion object{
		lateinit var prefs : NuteeSharedPreferences
	}

	override fun onCreate() {
		prefs = NuteeSharedPreferences(applicationContext)
		super.onCreate()
	}
}