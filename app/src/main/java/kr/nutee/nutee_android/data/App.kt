package kr.nutee.nutee_android.data

import android.app.Application
import kr.nutee.nutee_android.data.main.search.NuteeSearchSharedPreference

class App:Application() {

	// onCreate이전에 prefs를 먼저 초기화 한다.
	companion object{
		lateinit var prefs : NuteeSharedPreferences
		lateinit var prefsSearch:NuteeSearchSharedPreference
	}

	override fun onCreate() {
		prefs = NuteeSharedPreferences(applicationContext)
		prefsSearch=NuteeSearchSharedPreference(applicationContext)
		super.onCreate()
	}
}