package kr.nutee.nutee_android.ui.extend

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class InfiniteScrollListener(
	val func: () -> Unit,
	val layoutManager: LinearLayoutManager
) : RecyclerView.OnScrollListener() {

	private var previousTotal = 0
	private var loading = true
	private var visibleThreshold = 2
	private var firstVisibleItem = 0
	private var visibleItemCount = 0
	private var totalItemCount = 0

	override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
		super.onScrolled(recyclerView, dx, dy)

		if (dy > 0) {
			visibleItemCount = recyclerView.childCount
			totalItemCount = layoutManager.itemCount
			firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

			if (loading) {
				if (totalItemCount >= previousTotal) {
					loading = false
					previousTotal = totalItemCount
				}
			}
			if (!loading && (totalItemCount - visibleItemCount)
				<= (firstVisibleItem + visibleThreshold)) {
				// 끝에 도달 했을 때
				func() // 매개변수로 넘겨받은 람다식 함수
				loading = true
			}
		}
	}

}