# NUTEE-Android



## 확장 함수 정리

#### LoadFragment.kt

**확장함수:** loadFragment

**작성자**: [박진수(jinsu4755)](https://github.com/jinsu4755)

![img](https://blog.kakaocdn.net/dn/bgxHWs/btqGvY9Mudr/JZP9OaIeFC8kIKspPQIW00/img.png) 

해당 프로젝트에서 Fragment를 띄우는 중복된 부분이 많아 이를 줄이기 위해서 제작하였음.

transaction을 생성하고 인자로 받은 view를 또 다른 인자 fragment로 변경한다.



**확장함수:** loadFragment

**작성자**: [박진수(jinsu4755)](https://github.com/jinsu4755)

![img](https://blog.kakaocdn.net/dn/caBHa7/btqGvYIICCD/rMcb1npW8hlKMMu6xGwZD1/img.png) 

사용된 이유는 위와 같다. 그저 view를 받아온 fragment로 변경만 하면 1->2->3 으로 불러오고 뒤로가야하는경우

이전 fragment를 재사용할 수 없을 듯하여 재사용하기 위해서 이전 fragment를 back stack에 담아두기 위해서 만들었다.



---

## 구현 방법 메모

### RegisterActivity와 Fragment들

작성: [박진수(jinsu4755)](https://github.com/jinsu4755)

> 기존의 Register Activity는 그저 하나의 Activity에서 여러 뷰를 에니메이션으로 전환하는 방식으로 사용하였다.
>
> 하지만 이 방법을 사용하면 추후 유지보수가 너무 힘들 것이라 판단하여 다음과 같이 고치기로 하였다.





