package kr.nutee.nutee_android.data.main.search

import android.content.Context
import android.content.SharedPreferences

/*
88yhtserof
* NUTEE 검색 기록을 저장하는 class
*/

class NuteeSearchSharedPreference(context: Context) {
	//이전 검색 기록을 저장할 SharedPreference의 name
	val PREFS_FILENAME = "search"
	val DEFAULT = ""

	private val prefs: SharedPreferences =
		context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
	private val editor = prefs.edit()

	//SharedPreference 에 저장된 값 가져오기
	fun getString(key: Int): String {
		return prefs.getString("$key", DEFAULT).toString()
	}

	//SharedPreference에 값 저장하기
	fun setString(key: Int, value: String) {
		editor.putString("$key", value).apply()
	}

	fun contains(key: Int): Boolean {
		return prefs.contains("$key")
	}

	fun remove(key: Int) {
		editor.remove("$key").
		apply()
	}

	fun AllDelete() {
		editor.clear().
		apply()
	}

	val KeyList: List<String>
		get() = prefs.all.map { it.key }

	val All
		get() =prefs.getAll()
}


