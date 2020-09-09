package kr.nutee.nutee_android.ui.main.fragment.home.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_show_detail_image_view.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.main.home.Image
import java.util.ArrayList

/*
 * Created by 88yhtserof
 * DESC: 디테일 뷰: 사진 자세히 보기
 */

class ShowDetailImageView : AppCompatActivity() {

    companion object {
		const val TAG = "ShowDetailImageView"
    }

    //데이터 배열 선언
    private var detailViewImageList=ArrayList<Image>()
    private lateinit var detailImageViewAdapter: DetailImageViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_detail_image_view)

        //데이터 배열 준비
		detailViewImageList= intent.getParcelableArrayListExtra("Images") !!

        //어댑터 인스턴스 생성
        detailImageViewAdapter=
			DetailImageViewAdapter(this,
				detailViewImageList
			)

        vp_detail_image.apply {
			//뷰페이저와 어댑터 연결
            adapter=detailImageViewAdapter
			//뷰페이저 방향
            orientation=ViewPager2.ORIENTATION_HORIZONTAL
            //viewPager와 indicator 연결
            indicator_dots.setViewPager2(this)
        }

		//'닫기' 버튼 기능
		tv_top_back_btn.setOnClickListener {
			finish()
		}

		//'이전' 버튼 기능
		tv_bottom_previous_btn.setOnClickListener {
			val current=vp_detail_image.currentItem
			if(current == 0){
				//첫 번째 페이지일 시, 마지막 페이지로 이동
				vp_detail_image.setCurrentItem(detailViewImageList.size-1, true)
			}
			if(current != 0){
				//이전 사진으로 이동
				vp_detail_image.setCurrentItem(current-1, true)
			}
		}

		//'다음' 버튼 기능
		tv_bottom_next_btn.setOnClickListener {
			val current=vp_detail_image.currentItem
			if(current == detailViewImageList.size-1){
				//마지막 페이지일 시, 첫 번째 페이지로 이동
				vp_detail_image.setCurrentItem(0, true)
			}
			if(current != detailViewImageList.size-1){
				//다음 사진으로 이동
				vp_detail_image.setCurrentItem(current+1, true)
			}
		}

    }
}