package kr.nutee.nutee_android.ui.main.fragment.home.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_show_detail_image_view.*
import kr.nutee.nutee_android.R

/*
 * Created by 88yhtsero
 * DESC: 디테일 뷰: 사진 자세히 보기 viewPager
 */

class ShowDetailImageView : AppCompatActivity() {

    companion object {
    const val TAG = "ShowDetailImageView"
    }

    //데이터 배열 선언
    private var imageItemList=ArrayList<ImageItem>()
    private lateinit var detailImageViewAdapter: DetailImageViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_detail_image_view)

		//'닫기' 버튼 기능
		tv_top_back_btn.setOnClickListener {
			finish()
		}


        //데이터 배열 준비
        imageItemList.add(
			ImageItem(R.drawable.image_test)
		)
        imageItemList.add(
			ImageItem(R.drawable.image_test)
		)
        imageItemList.add(
			ImageItem(R.drawable.image_test)
		)
        imageItemList.add(
			ImageItem(R.drawable.image_test)
		)

        //어댑터 인스턴스 생성
        detailImageViewAdapter=
			DetailImageViewAdapter(
				imageItemList
			)

        vp_detail_image.apply {
            adapter=detailImageViewAdapter
            orientation=ViewPager2.ORIENTATION_HORIZONTAL
            //viewPager와 indicator 연결
            indicator_dots.setViewPager2(this)
        }
    }
}