package kr.nutee.nutee_android.ui.member.register

import android.animation.ObjectAnimator
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.register_activity.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.member.register.*
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.changeLayoutDown
import kr.nutee.nutee_android.ui.extend.dialog.customDialog
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.extend.textChangedListener


class RegisterActivity : AppCompatActivity(), View.OnClickListener {


	val requestToServer = RequestToServer

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.register_activity)

	}

	override fun onClick(v: View?) {
		TODO("Not yet implemented")
	}
}

