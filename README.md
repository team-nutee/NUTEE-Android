# NUTEE-Android

작성: [박진수(jinsu4755)](https://github.com/jinsu4755)


## 1. 프로젝트 사용 라이브러리

```groovy
    //material 디자인 라이브러리
    implementation 'com.google.android.material:material:1.1.0'
    
    //Recyclerview
    implementation 'androidx.recyclerview:recyclerview:1.1.0'
    
    // 동그란 이미지 커스텀 뷰
    implementation group: 'de.hdodenhof', name: 'circleimageview', version: '3.1.0'
    
    //이미지 로딩 라이브러리 : glide
    // https://mvnrepository.com/artifact/com.github.bumptech.glide/glide
    implementation group: 'com.github.bumptech.glide', name: 'glide', version: '4.9.0'
    kapt "com.github.bumptech.glide:compiler:4.10.0"
    
    //Retrofit 라이브러리 : https://github.com/square/retrofit
    implementation 'com.squareup.retrofit2:retrofit:2.6.2'
    
    //Retrofit 라이브러리 응답으로 가짜 객체를 만들기 위해
    implementation 'com.squareup.retrofit2:retrofit-mock:2.6.2'

    //객체 시리얼라이즈를 위한 Gson 라이브러리 : https://github.com/google/gson
    implementation 'com.google.code.gson:gson:2.8.6'
    
    // Retrofit 에서 Gson 을 사용하기 위한 라이브러리
    implementation 'com.squareup.retrofit2:converter-gson:2.6.2'
```



## 2.프로그램 구조

![img](https://k.kakaocdn.net/dn/NiCrj/btqElX4uI7z/tk8lTg1csPr1ImW7WOCJDK/img.png)

프로그램 구조는 다음과 같이 data, network, ui 3가지로 나누어 패키징 하였다.

##### ui

![img](https://k.kakaocdn.net/dn/b6zHCM/btqEjPGM31k/ohE4NPtkkyqX96OGkkkI11/img.png)

ui 내부에도 역시 큰 틀로 나뉘어 패키징을 진행하였다.

- extend

  - kotlin 확장함수 패키지

- login

  - 로그인, 회원가입 관련 패키지

- main

  메인 화면 관련 로직이 들어있고, 각 fragment별로 정리하였다.

  notice의 경우 내부에 별도로 작동하는 fragment가 존재하여 다시 나누어 정리하였다.





## 3. 핵심 기능 및 구현 방법

### 1. 회원가입 및 로그인

#### 1) 회원가입 애니메이션

down_alpha_translate.xml,down_in.xml,down_out.xml 3가지 애니메이션으로

down_in.xml,down_out.xml 의 경우

changeLayout_down이라는 Kotlin 확장함수를 제작하여 처리하였다.

![img](https://k.kakaocdn.net/dn/drSW0Y/btqElFpvBRi/YgnfyRGdNsVLSYBkN9fekk/img.png)

내부 핸들러로 처리된 부분은 해당 ConstrainLayout이 나타나고 조금의 딜레이를 주어 myAni()함수를 매개 변수로 받아 내부 down_alpha_translate.xml 애니메이션을 처리하였다.

#### 2) kotlin 확장함수

##### customDialog

![img](https://k.kakaocdn.net/dn/bbbibe/btqEjPfHHWM/LmDaCLQWk88fJn6Tp5ONk1/img.png)

customDialog를 띄우고 매개변수로 Dialog에 띄울 message와 okButton를 눌렀을 경우 처리할 함수를 받아 처리하였다.

---

##### textChangedListener

##### ![img](https://k.kakaocdn.net/dn/bI6JoL/btqEjPUmZnd/nRetj2cAh8rIbqnlx5R9V0/img.png)

EditText의 변화를 감지하여 로직을 처리하는 확장함수로 input 이벤트 이후 처리할 함수를 매개변수로 받아 처리하였다.

