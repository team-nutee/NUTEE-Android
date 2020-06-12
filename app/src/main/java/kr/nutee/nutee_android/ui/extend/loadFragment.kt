package kr.nutee.nutee_android.ui.extend

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.ui.main.MainActivity

//fragment load 함수
fun Context.loadFragment(fragment: Fragment) {
	val transaction = (this as FragmentActivity).supportFragmentManager.beginTransaction()
	transaction.replace(R.id.frame_layout, fragment)
	transaction.commit()
}

