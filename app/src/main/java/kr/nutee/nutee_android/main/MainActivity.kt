package kr.nutee.nutee_android.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.main_activity.*
import kr.nutee.nutee_android.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        init()
    }



    //View pager 전환 함수
    private fun init(){
        with(main_viewpager) {
            adapter = MainPagerAdapter(supportFragmentManager)
            offscreenPageLimit = 5
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
                R.id.menu_add -> main_viewpager.currentItem = 2
                R.id.menu_pin -> main_viewpager.currentItem = 3
                R.id.menu_profile -> main_viewpager.currentItem = 4
            }
            true
        }
    }
}
