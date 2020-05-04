package kr.nutee.nutee_android.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
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

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.main_activity)
	}

    //엑티비티가 사용자와 상호작용하기전에 설정
    override fun onResume() {
        super.onResume()

        //초기 fragment 설정
        supportFragmentManager.beginTransaction().replace(R.id.frame_layout, HomeFlagement()).commitAllowingStateLoss()

        text_setting.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }

        //navigationBottomView 등록
        mainNavigationBottomView(main_bottom_nav)
    }

    // NavigationBottomView 화면전환
    private fun mainNavigationBottomView(bottomNavigationView: BottomNavigationView){
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_home-> {
                    main_title.text = resources.getText(R.string.fragment_home)
                    loadFragment(HomeFlagement())
                    text_setting.visibility = View.INVISIBLE
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.menu_search-> {
                    main_title.text = resources.getString(R.string.fragment_search)
                    loadFragment(SearchFragment())
                    text_setting.visibility = View.INVISIBLE
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.menu_add-> {
                    val intent = Intent(this, AddActivity::class.java)
                    startActivity(intent)
                    finish()
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.menu_pin-> {
                    main_title.text = resources.getString(R.string.fragment_notice)
                    loadFragment(NoticeFlagment())
                    text_setting.visibility = View.INVISIBLE
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.menu_profile-> {
                    main_title.text = resources.getString(R.string.fragment_profile)
                    loadFragment(ProfileFragment())
                    text_setting.visibility = View.VISIBLE
                    return@setOnNavigationItemSelectedListener true
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


	//View pager 전환 함수
	/*private fun init(){
		with(main_viewpager) {
			adapter = MainPagerAdapter(supportFragmentManager)
			offscreenPageLimit = 4
			addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
				override fun onPageScrollStateChanged(state: Int) = kotlin.Unit

				override fun onPageScrolled(
					position: Int,
					positionOffset: Float,
					positionOffsetPixels: Int
				) = kotlin.Unit
				//패이지가 선택되면 해당 페이지 isChecked 를 true 로
				override fun onPageSelected(position: Int) {
					main_bottom_nav.menu.getItem(position).isChecked = true
				}
			})
		}


		main_bottom_nav.setOnNavigationItemSelectedListener {
			when (it.itemId) {
				R.id.menu_home -> main_viewpager.currentItem = 0
				R.id.menu_search -> main_viewpager.currentItem = 1
				*//*R.id.menu_add -> main_viewpager.currentItem = 2*//*
                R.id.menu_pin -> main_viewpager.currentItem = 2
                R.id.menu_profile -> main_viewpager.currentItem = 3
            }
            true
        }
    }*/

}



private fun BottomNavigationView.setOnNavigationItemSelectedListener() {

}

