package kr.nutee.nutee_android.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.main_activity.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.ui.extend.loadMainPageFragment
import kr.nutee.nutee_android.ui.main.fragment.add.AddActivity
import kr.nutee.nutee_android.ui.main.fragment.home.HomeFragement
import kr.nutee.nutee_android.ui.main.fragment.notice.NoticeFragment
import kr.nutee.nutee_android.ui.main.fragment.profile.ProfileFragment
import kr.nutee.nutee_android.ui.main.fragment.search.SearchFragment
import kr.nutee.nutee_android.ui.setting.SettingActivity

class MainActivity : AppCompatActivity() {

	private var pressTime:Long = 0 //onBackPressedEvent 처리 변수

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.main_activity)
		init()
		Log.d("appToken",FirebaseInstanceId.getInstance().getToken().toString())
	}

	private fun init() {
		//초기 fragment 설정
		supportFragmentManager.beginTransaction().replace(R.id.frame_layout, HomeFragement()).commitAllowingStateLoss()

		text_setting.setOnClickListener {
			val intent = Intent(this, SettingActivity::class.java)
			startActivity(intent)
		}

		//navigationBottomView 등록
		mainNavigationBottomView(main_bottom_nav)
	}

    override fun onBackPressed() {
		if (supportFragmentManager.backStackEntryCount > 0) {
			supportFragmentManager.popBackStack()
		} else{
			if (System.currentTimeMillis()- pressTime < 2000) {
				super.onBackPressed()
				// 어떤 엑티비티에서도 종료
				finishAffinity()
			}
			Toast.makeText(this,"한 번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show()

			pressTime = System.currentTimeMillis()
		}
    }

    // NavigationBottomView 화면전환
    private fun mainNavigationBottomView(bottomNavigationView: BottomNavigationView){
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_home-> {
					loadMainPageFragment(
						resources.getText(R.string.fragment_home),
						HomeFragement(),
						View.INVISIBLE
					)
					return@setOnNavigationItemSelectedListener true
                }

                R.id.menu_search-> {
					loadMainPageFragment(
						resources.getText(R.string.fragment_search),
						SearchFragment(),
						View.INVISIBLE
					)
					return@setOnNavigationItemSelectedListener true
                }

                R.id.menu_add-> {
                    val intent = Intent(this, AddActivity::class.java)
					startActivity(intent)
                }

                R.id.menu_pin-> {
					loadMainPageFragment(
						resources.getString(R.string.fragment_notice),
						NoticeFragment(),
						View.INVISIBLE
					)
					return@setOnNavigationItemSelectedListener true
                }

                R.id.menu_profile-> {
					loadMainPageFragment(
						resources.getString(R.string.fragment_profile),
						ProfileFragment(),
						View.VISIBLE
					)
					return@setOnNavigationItemSelectedListener true
                }

            }
            false
        }
    }

}


