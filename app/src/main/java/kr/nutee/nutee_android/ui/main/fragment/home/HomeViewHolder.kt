package kr.nutee.nutee_android.ui.main.fragment.home

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.data.DateParser
import kr.nutee.nutee_android.data.QueryValue
import kr.nutee.nutee_android.data.TestToken
import kr.nutee.nutee_android.data.main.RequestReport
import kr.nutee.nutee_android.data.main.home.Body
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.contentMoreEvent
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.extend.dialog.cumstomReportDialog
import kr.nutee.nutee_android.ui.extend.dialog.customDialogSingleButton
import kr.nutee.nutee_android.ui.main.fragment.add.AddActivity
import kr.nutee.nutee_android.ui.main.fragment.home.detail.HomeDetailActivity
import kr.nutee.nutee_android.ui.main.fragment.search.SearchResultsView


/*home fragment RecyclerView 내부 하나의 뷰의 정보를 지정하는 클래스 */
class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

	val requestToServer = RequestToServer

	val category = itemView.findViewById<TextView>(R.id.text_main_home_recyclerview_item_category)
	val title = itemView.findViewById<TextView>(R.id.text_main_home_recyclerview_item_title)
	val content = itemView.findViewById<TextView>(R.id.text_main_home_recyclerview_item_content)
	val text_main_home_count_image = itemView.findViewById<TextView>(R.id.text_main_home_recyclerview_item_img)
	val text_main_home_count_comment = itemView.findViewById<TextView>(R.id.text_main_home_recyclerview_item_comment)
	val text_main_home_count_like = itemView.findViewById<TextView>(R.id.text_main_home_recyclerview_item_likes)
	val text_main_home_updateat = itemView.findViewById<TextView>(R.id.text_main_home_recyclerview_item_date)
	val text_main_home_hits = itemView.findViewById<TextView>(R.id.text_main_home_recyclerview_item_hits)
	val img_main_home_more=itemView.findViewById<ImageView>(R.id.img_main_home_recyclerview_item_more)
	private val textRewrite = itemView.findViewById<TextView>(R.id.text_main_item_rewrite)

	fun bind(
		customData: Body
	) {
		category.text = customData.category
		text_main_home_updateat.text = customData.updatedAt?.let { DateParser(it).calculateDiffDate() }
		title.text=customData.title
		content.text = customData.content
		text_main_home_count_image.text = customData.images?.size.toString()
		text_main_home_count_comment.text = customData.commentNum.toString()
		text_main_home_count_like.text = customData.likers?.size.toString()
		text_main_home_hits.text=customData.hits.toString()
		//수정 여부 표시
		if(customData.updatedAt !=customData.createdAt)
			textRewrite.visibility=View.VISIBLE

		checkNullInItem(customData)

		itemView.setOnClickListener{
			Log.d("DetailClick",customData.id.toString())
			//FIXME 아이디가 없는경우 존재하지 않는 글입니다 띄워주기! 그리고 자체적으로 리스트에서 삭제하고 적용하기.
			val gotoDetailPageIntent = Intent(itemView.context, HomeDetailActivity::class.java)
			gotoDetailPageIntent.putExtra("Detail_id", customData.id!!)
			itemView.context.startActivity(gotoDetailPageIntent)
		}
		img_main_home_more.setOnClickListener{
			moreEvent(it,customData)
		}
		category.setOnClickListener {
			categoryClickEvent(it,customData)
		}
	}

	private fun moreEvent(it:View, customData: Body) {
		itemView.context.contentMoreEvent(
			customData.user,
			View.GONE,{},
			{ //게시글 수정
				if(customData.images.isNullOrEmpty()) rewritePost(customData)
				else
					itemView.context.customDialogSingleButton(itemView.context.getString(R.string.UnableRewritePost))
			},
			{ //게시글 삭제
				requestToServer.backService.requestDelete(
					"Bearer "+ TestToken.testToken,
					customData.id)
					.customEnqueue(
						onSuccess = {
							Log.d("Network", "누름")
						},
						onError = {
							Toast.makeText(itemView.context,"네트워크 오류",Toast.LENGTH_SHORT)
								.show()}
					)},
			{ //게시글 신고
				it.context.cumstomReportDialog("이 게시글을 신고하시겠습니까?"){
					requestToServer.backService.requestReport(
						RequestReport(it), customData.id)
						.customEnqueue(
							onSuccess = {
								Toast.makeText(itemView.context,"신고가 성공적으로 접수되었습니다.",Toast.LENGTH_SHORT)
									.show()
							}
						)
				}}
		)
	}

	private fun rewritePost(customData: Body) {
		val intent = Intent(itemView.context, AddActivity::class.java)
		intent.putExtra("title",customData.title)
		intent.putExtra("content",customData.content)
		intent.putExtra("category",customData.category)
		intent.putExtra("postId",customData.id)
		if (customData.images?.isNullOrEmpty() == false){
			val imageArrayList = customData.images.toCollection(ArrayList())
			intent.putParcelableArrayListExtra("rewriteImage", imageArrayList)
			itemView.context.startActivity(intent)
		}
//		val imageArrayList = arrayListOf<String>()
//		customData.images?.forEach{
//			imageArrayList.add(it.src)
//		}
//		intent.putParcelableArrayListExtra("rewriteImage", imageArrayList)
		itemView.context.startActivity(intent)
	}

	private fun categoryClickEvent(view:View, customData: Body){
		val intent = Intent(itemView.context, SearchResultsView::class.java)
		intent.putExtra("categorySearch",customData.category) //검색어
		itemView.context.startActivity(intent)
	}

	private fun checkNullInItem(customData: Body){
		if(customData.likers?.size.toString()=="null"){
			text_main_home_count_like.text="0"
		}
		if(customData.images?.size.toString()=="null")
			text_main_home_count_image.text="0"
	}
}

