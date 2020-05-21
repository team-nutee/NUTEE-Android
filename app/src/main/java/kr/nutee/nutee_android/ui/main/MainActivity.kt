package kr.nutee.nutee_android.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.main_activity.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.ui.main.fragment.add.AddActivity
import kr.nutee.nutee_android.ui.main.fragment.home.HomeFlagement
import kr.nutee.nutee_android.ui.main.fragment.notice.NoticeFlagment
import kr.nutee.nutee_android.ui.main.fragment.profile.ProfileFragment
import kr.nutee.nutee_android.ui.main.fragment.search.SearchFragment
import kr.nutee.nutee_android.ui.setting.SettingActivity

class MainActivity : AppCompatActivity() {

	private var pressTime:Long = 0 //onBackPressedEvent 처리 변수

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.main_activity)
		init()
	}

	private fun init() {
		//초기 fragment 설정
		supportFragmentManager.beginTransaction().replace(R.id.frame_layout, HomeFlagement()).commitAllowingStateLoss()

		text_setting.setOnClickListener {
			val intent = Intent(this, SettingActivity::class.java)
			startActivity(intent)
		}

		//navigationBottomView 등록
		mainNavigationBottomView(main_bottom_nav)
	}

    override fun onBackPressed() {
        if (System.currentTimeMillis()- pressTime < 2000) {
            super.onBackPressed()
            // 어떤 엑티비티에서도 종료
            finishAffinity()
        }
        Toast.makeText(this,"한 번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show()

        pressTime = System.currentTimeMillis()
    }

    // NavigationBottomView 화면전환
    private fun mainNavigationBottomView(bottomNavigationView: BottomNavigationView){
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_home-> {
                    main_title.text = resources.getText(R.string.fragment_home)
                    loadFragment(HomeFlagement())
                    text_setting.visibility = View.INVISIBLE
                }

                R.id.menu_search-> {
                    main_title.text = resources.getString(R.string.fragment_search)
                    loadFragment(SearchFragment())
                    text_setting.visibility = View.INVISIBLE
                }

                R.id.menu_add-> {
                    val intent = Intent(this, AddActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                R.id.menu_pin-> {
                    main_title.text = resources.getString(R.string.fragment_notice)
                    loadFragment(NoticeFlagment())
                    text_setting.visibility = View.INVISIBLE
                }

                R.id.menu_profile-> {
                    main_title.text = resources.getString(R.string.fragment_profile)
                    loadFragment(ProfileFragment())
                    text_setting.visibility = View.VISIBLE
                }

            }
            false
        }
    }

    //fragment load 함수
    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout, fragment)
        transaction.commit()
    }

}


