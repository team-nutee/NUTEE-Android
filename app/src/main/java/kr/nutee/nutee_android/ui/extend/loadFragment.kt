package kr.nutee.nutee_android.ui.extend

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.main_activity.*
import kr.nutee.nutee_android.R

/*
* Created by jinsu4755
* DESC: 중복되는 Fragment 호출 부분을 함수로 처리하기 위해서 제작한 확장함수들
*/

//fragment load 함수
fun Context.loadMainPageFragment(title: CharSequence, fragment: Fragment, visibility: Int) {
	val transaction =
		(this as FragmentActivity).supportFragmentManager.beginTransaction()
	main_title.text = title
	transaction.replace(R.id.frame_layout, fragment)
	transaction.commit()
	text_setting.visibility = visibility
}

fun Context.loadFragment(
	fragment: Fragment,
	view: Int
){
	val transaction
			= (this as FragmentActivity).supportFragmentManager.beginTransaction()
	transaction.replace(view, fragment)
	transaction.commit()
}

fun Context.loadFragmentAddtoBackStack(
	fragment: Fragment,
	view:Int,
	backStackName: String?
) {
	val transaction =
		(this as FragmentActivity).supportFragmentManager.beginTransaction()
	transaction.replace(view, fragment)
	transaction.addToBackStack(backStackName)
	transaction.commit()
}

