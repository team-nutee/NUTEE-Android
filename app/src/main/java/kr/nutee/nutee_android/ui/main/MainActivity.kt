package kr.nutee.nutee_android.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.main_activity.view.*
import kotlinx.android.synthetic.main.setting_activity.view.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.databinding.MainActivityBinding
import kr.nutee.nutee_android.ui.extend.loadMainPageFragment
import kr.nutee.nutee_android.ui.main.fragment.add.AddActivity
import kr.nutee.nutee_android.ui.main.fragment.home.HomeFragement
import kr.nutee.nutee_android.ui.main.fragment.notice.NoticeFragment
import kr.nutee.nutee_android.ui.main.fragment.profile.ProfileFragment
import kr.nutee.nutee_android.ui.main.fragment.search.SearchView
import kr.nutee.nutee_android.ui.setting.SettingActivity

class MainActivity : AppCompatActivity() {

	private val binding by lazy{MainActivityBinding.inflate(layoutInflater)}
	private var pressTime:Long = 0 //onBackPressedEvent 처리 변수
	private lateinit var icSearch:ImageButton
	private lateinit var icSetting: ImageButton

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)
		init()
		icSearchEvent()
		icSettingEvent()
	}

	private fun init() {
		//초기 fragment 설정
		supportFragmentManager.beginTransaction().replace(binding.frameLayout.id, HomeFragement()).commitAllowingStateLoss()

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
		//검색창 강제 선택시 키보드가 자동으로 보이게 함
		val inputMethodManager : InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_home-> {
					loadMainPageFragment(
						resources.getText(R.string.fragment_home),
						HomeFragement(),
						View.VISIBLE,
							View.GONE
					)
					inputMethodManager.hideSoftInputFromWindow(
						currentFocus?.windowToken,
						InputMethodManager.HIDE_NOT_ALWAYS
					)
					return@setOnNavigationItemSelectedListener true
                }


                R.id.menu_add-> {
                    val intent = Intent(this, AddActivity::class.java)
					startActivity(intent)
                }

                R.id.menu_notice-> {
					loadMainPageFragment(
							resources.getString(R.string.fragment_notice),
							NoticeFragment(),
							View.INVISIBLE,
							View.INVISIBLE
					)
					inputMethodManager.hideSoftInputFromWindow(
						currentFocus?.windowToken,
						InputMethodManager.HIDE_NOT_ALWAYS
					)
					return@setOnNavigationItemSelectedListener true
                }

                R.id.menu_profile-> {
					loadMainPageFragment(
							resources.getString(R.string.fragment_profile),
							ProfileFragment(),
							View.GONE,
							View.VISIBLE
					)
					inputMethodManager.hideSoftInputFromWindow(
						currentFocus?.windowToken,
						InputMethodManager.HIDE_NOT_ALWAYS
					)

					return@setOnNavigationItemSelectedListener true
                }

            }
            false
        }
    }

	//상단바 검색 위젯 이벤트
	private fun icSearchEvent(){
		icSearch=binding.imgMainTopSearch
		icSearch.setOnClickListener {
			val intent=Intent(this,SearchView::class.java)
			startActivity(intent)
		}
	}

	//상단바 설정 위젯 이벤트
	private fun icSettingEvent(){
		icSetting=binding.imgMainTopSetting
		icSetting.setOnClickListener {
			val intent=Intent(this, SettingActivity::class.java)
			startActivity(intent)
		}
	}
}


