package kr.nutee.nutee_android.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.main_activity.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.fragment.home.HomeFlagement
import kr.nutee.nutee_android.fragment.notice.NoticeFlagment
import kr.nutee.nutee_android.fragment.profile.ProfileFragment
import kr.nutee.nutee_android.fragment.search.SearchFragment

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.main_activity)

        var transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
		transaction.replace(R.id.frame_layout, HomeFlagement()).commitAllowingStateLoss()

        //하단 nav bar 화면 전환
		main_bottom_nav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_home-> {
                    loadFragment(HomeFlagement())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.menu_search-> {
                    loadFragment(SearchFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.menu_add-> {
                    loadFragment(HomeFlagement())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.menu_pin-> {
                    loadFragment(NoticeFlagment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.menu_profile-> {
                    loadFragment(ProfileFragment())
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

