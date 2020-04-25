package kr.nutee.nutee_android.ui.main.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.main_fragment_home.*

import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.home.HomeData

class HomeFlagement : Fragment() {

	private lateinit var homeAdapter: HomeAdapter

	private val datas = mutableListOf<HomeData>()

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.main_fragment_home,container,false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		homeAdapter =
			HomeAdapter(view.context)
		rv_home.run {
			adapter = homeAdapter
			layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
		}
		loadDatas()
	}

	private fun loadDatas() {
		datas.apply {
			add(
				HomeData(
					name = "jinsu",
					content = "test1번 화면 체크",
					profile_img = "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fk.kakaocdn.net%2Fdn%2Fd44x6A%2Fbtqzu7dKbZ6%2FlOoGM6j0rjWD3p8kKfB8ck%2Fimg.jpg"
				)
			)
			add(
				HomeData(
					name = "jinsu4755",
					content = "긴글 테스트 최대 몇줄까지의 제한은 아직 설정하지 않았음 어디까지 가나 한번 봅시다 우리 이걸보고 이게 몇줄까지 표시될지 정하는겁니다 ㅇㅈ? 일단 쥰네이 길게 가자구 최근, ‘닌텐도 스위치(스위치)'와 ‘모여봐요 동물의숲(동물의숲)’ 붐이 일어나면서 콘솔 플랫폼이 재평가받고 있고, 이에 따라 한국형 콘솔 게임 소식들이 연이어 지고 있다.\n" +
							" \n" +
							"사실 국내 게임사들의 콘솔 게임 신작 소식이 전해진 것은 하루 이틀이 아니다. 국내 콘솔 시장의 본격적인 전성기를 이끈 ‘플레이스테이션2(PS2)’ 시절부터 국내 누적 판매량 300만대 이상을 달성한 ‘닌텐도DS(NDS)’, 지난 세대 콘솔 ‘플레이스테이션3(PS3)’ 및 ‘Xbox 360’, 현세대기 ‘플레이스테이션4(PS4)’ 및 ‘Xbox One’, 스위치(현재 국내 누적 판매량 80만대 추정) 때까지 콘솔 게임기가 인기를 끌 때마다 국내 게임사는 항상 신작을 출시하거나 개발 중이라고 언급하고 있다.\n" +
							" \n" +
							"그러나 시장에 진출한 한국산 콘솔 게임 90% 이상은 흥행에 실패했고, 개발 중이라 발표했던 게임들은 실제 시장에 내놓지도 못하고 프로젝트가 중단하는 일이 대다수이다. 또 새로운 플랫폼이 나올 때마다 이런 일이 계속 되풀이된다.\n" +
							" \n" +
							"한국산 콘솔 게임이 꾸준히 콘솔 시장에 진출하고 있지만 눈에 띄는 성적을 올리지 못하는 이유는 무엇일까?\n" +
							" \n" +
							"살펴보면 게임을 완성도나 작품성보단 매출 베이스 게임만 만드는 기형적인 개발환경 탓이 크다. 현재 국내 시장은 주요 플랫폼인 모바일을 이끄는 오픈마켓 애플앱스토어와 구글플레이 매출 순위로 게임성 및 인기의 척도를 따지는 구조이기 때문에 당장 투자대비 수익성이 낮은 콘솔 게임 개발은 매출이 중심인 기업 입장에선 꺼려한다.\n" +
							" \n" +
							"이런 환경 탓에 최근 몇 년간 기자가 다녀본 기자간담회나 인터뷰를 다녀보면 실제 게임을 개발하고 주도해야 하는 기획자나 개발자보단 매출을 버는 BM(비즈니스 모델)을 만드는 사업담당자가 입김이 센 광경을 자주 목격하게 된다. 예를 모 대형 게임사들의 경우 사업담당자가 시장을 분석 후 BM을 만들어 주면 기획자나 개발자는 그 BM에 맞춰 게임을 만들거나 기존에 정하던 방향성을 바꿔야 한다.\n" +
							" \n" +
							"이렇다 보니 관련한 주요 행사가 열리면 사업담당자는 썰 풀이 많고, 개발자는 자연스레 꿀 먹은 벙어리처럼 가만히 있는 광경이 자주 목격된다. 즉, 패키지 하나로 완성도 및 작품성을 높여야 하는 콘솔 게임에 매출 베이스의 국내 게임사의 개발 환경은 득보단 독이 되는 방해요소들이 많다. ",
					profile_img = "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fk.kakaocdn.net%2Fdn%2Fd44x6A%2Fbtqzu7dKbZ6%2FlOoGM6j0rjWD3p8kKfB8ck%2Fimg.jpg"

				)
			)
			add(
				HomeData(
					name = "Antilog",
					content = "글글글",
					profile_img = "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fk.kakaocdn.net%2Fdn%2Fd44x6A%2Fbtqzu7dKbZ6%2FlOoGM6j0rjWD3p8kKfB8ck%2Fimg.jpg"
				)
			)
			add(
				HomeData(
					name = "jinsu",
					content = "이것도 글글글",
					profile_img = "https://scontent-ssn1-1.cdninstagram.com/v/t51.2885-19/s150x150/75586256_1458557390966189_2675692289568800768_n.jpg?_nc_ht=scontent-ssn1-1.cdninstagram.com&_nc_ohc=DPhy1itcp2sAX_lASKp&oh=8cf0aa96223e6936464141d055179036&oe=5EC248BE"
				)
			)
		}
		homeAdapter.data = datas
		homeAdapter.notifyDataSetChanged()
	}

}
