package kr.nutee.nutee_android.ui.extend.animation

import android.content.Context
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.constraintlayout.widget.ConstraintLayout
import kr.nutee.nutee_android.R

/*
* Created by jinsu4755
* DESC: constaintLayout과 관련된 에니메이션 처리
*/

fun Context.constraintDownInAnimation(layout: ConstraintLayout){
	val inAnimation = AnimationUtils.loadAnimation(this,R.anim.down_in)
	layout.startAnimation(inAnimation)
	layout.visibility = View.VISIBLE
}

/*
이전 레이아웃과 다음 레이아웃을 받아
이전 레이아웃이 아래로 사라지고 다음 레이아웃이 위에서 아래로 나타나게 한다.
 */
fun Context.changeLayoutDown(prevLayout: ConstraintLayout, nextLayout: ConstraintLayout, myAni: ()->Unit) {


	val inAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.down_in)
	val outAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.down_out)
	inAnimation.setAnimationListener(object: Animation.AnimationListener {
		override fun onAnimationRepeat(animation: Animation?)=Unit

		override fun onAnimationEnd(animation: Animation?) {
			//out된것은 Gone처리, in되는것은 보이도록 설정
			nextLayout.visibility = View.VISIBLE
			// Handler를 사용하여 딜레이 후 실행 Runnale를 실행
			val mDelayHandler = Handler()
			val mRunnable : Runnable = Runnable { myAni() }
			mDelayHandler.postDelayed(mRunnable,200)
		}

		override fun onAnimationStart(animation: Animation?){
			prevLayout.visibility = View.GONE
		}

	})
	prevLayout.startAnimation(outAnimation)
	nextLayout.startAnimation(inAnimation)
}