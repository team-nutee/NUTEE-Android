package kr.nutee.nutee_android.ui.main.fragment.home.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_show_detail_image_view.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.data.main.home.Image
import kr.nutee.nutee_android.databinding.ActivityShowDetailImageViewBinding
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue

/*
 * Created by 88yhtserof
 * DESC: 디테일 뷰: 사진 자세히 보기
 */

class ShowDetailImageView : AppCompatActivity() {

	private val binding by lazy { ActivityShowDetailImageViewBinding.inflate(layoutInflater) }

    companion object {
		const val TAG = "ShowDetailImageView"
    }

	private lateinit var detailViewImageList:Array<Image>
    private var postId=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
		//setContentView(R.layout.activity_show_detail_image_view)

		postId=intent.getIntExtra("postId",0)
		requestImage()

		//'닫기' 버튼 기능
		binding.tvTopBackBtn.setOnClickListener {
			finish()
		}

		//'이전' 버튼 기능
		binding.tvBottomPreviousBtn.setOnClickListener {
			val current=binding.vpDetailImage.currentItem
			if(current == 0){
				//첫 번째 페이지일 시, 마지막 페이지로 이동
				binding.vpDetailImage.setCurrentItem(detailViewImageList.size-1, true)
			}
			if(current != 0){
				//이전 사진으로 이동
				binding.vpDetailImage.setCurrentItem(current-1, true)
			}
		}

		//'다음' 버튼 기능
		binding.tvBottomNextBtn.setOnClickListener {
			val current=binding.vpDetailImage.currentItem
			if(current == detailViewImageList.size-1){
				//마지막 페이지일 시, 첫 번째 페이지로 이동
				binding.vpDetailImage.setCurrentItem(0, true)
			}
			if(current != detailViewImageList.size-1){
				//다음 사진으로 이동
				binding.vpDetailImage.setCurrentItem(current+1, true)
			}
		}

    }

	private fun requestImage(){
		RequestToServer.snsService.requestDetail(
			"Bearer "+ App.prefs.local_login_token,
			this.postId
		).customEnqueue(
			onSuccess = {
				detailViewImageList=it.body()?.body?.images!!
				binding.vpDetailImage.apply {
					adapter=DetailImageViewAdapter(context,detailViewImageList)
					orientation=ViewPager2.ORIENTATION_HORIZONTAL
					//viewPager와 indicator 연결
					binding.indicatorDots.setViewPager2(this)
				}
			}
		)
	}
}