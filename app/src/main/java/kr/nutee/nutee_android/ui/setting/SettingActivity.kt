package kr.nutee.nutee_android.ui.setting

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.setting_activity.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.ui.extend.loadFragment
import kr.nutee.nutee_android.ui.setting.fragment.MainSettingFragment

class SettingActivity : AppCompatActivity() {

	val manager = supportFragmentManager

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.setting_activity)

		loadFragment(MainSettingFragment(),R.id.frame_layout_setting)

		img_setting_back_btn.setOnClickListener {
			loadFragmentAddtoBackStack(MainSettingFragment()
				,R.id.frame_layout_setting
				,null)
		}

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
		backStackName:String?
	) {
		val transaction =
			(this as FragmentActivity).supportFragmentManager.beginTransaction()
		transaction.replace(view, fragment)
		transaction.addToBackStack(backStackName)
		transaction.commit()
	}


}

