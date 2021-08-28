package kr.nutee.nutee_android.ui.extend

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuInflater
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.internal.NavigationMenuItemView
import kotlinx.android.synthetic.main.custom_loading_dialog.*
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.main_activity.view.*
import kotlinx.android.synthetic.main.term_of_use_activity.view.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.databinding.CustomSelectDialogBinding
import kr.nutee.nutee_android.databinding.MainActivityBinding
import kr.nutee.nutee_android.databinding.SearchActivityBinding
import kr.nutee.nutee_android.databinding.SettingActivityBinding
import kr.nutee.nutee_android.ui.extend.dialog.customSelectDialog
import kr.nutee.nutee_android.ui.main.MainActivity


/*
* Created by jinsu4755
* DESC: 중복되는 Fragment 호출 부분을 함수로 처리하기 위해서 제작한 확장함수들
*/

//fragment load 함수
fun Context.loadMainPageFragment(title: CharSequence, fragment: Fragment, searchView: Int, settingView: Int) {
	val transaction =
			(this as FragmentActivity).supportFragmentManager.beginTransaction()

	/*val binding by lazy {MainActivityBinding.inflate(layoutInflater)}
	binding.mainTitle.text = title
	transaction.replace(binding.frameLayout.id, fragment)
	transaction.commit()
	binding.imgMainTopSearch.visibility = searchView
	binding.imgMainTopSetting.visibility = settingView*/

	main_title.text= title
	transaction.replace(R.id.frame_layout, fragment)
	transaction.commit()
	img_main_top_search.visibility = searchView
	img_main_top_setting.visibility= settingView
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
		view: Int,
		backStackName: String?
) {
	val transaction =
			(this as FragmentActivity).supportFragmentManager.beginTransaction()
	transaction.replace(view, fragment)
	transaction.addToBackStack(backStackName)
	transaction.commit()
}