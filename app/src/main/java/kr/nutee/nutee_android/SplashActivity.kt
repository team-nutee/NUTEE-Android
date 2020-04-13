package kr.nutee.nutee_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kr.nutee.nutee_android.login.LoginActivity

/*
* Splash 화면 Activity
*/
class SplashActivity : AppCompatActivity() {


    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 1000 //1 seconds

    private val mRunnable: Runnable = Runnable {
        // 아직 스레드가 끝나지 않은 경우
        if (!isFinishing) {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish() //종료
        }
    }

    //해당 Activity 생명주기 생성
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //핸들러 사용
        mDelayHandler = Handler()

        //딜레이 시간 후 실행되는
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)

    }

    //생명주기가 끝났을 경우
    public override fun onDestroy() {
        //my DelayHandler is not null
        if (mDelayHandler != null) {
            //스레드 실행을 취소한다.
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }
}
