# MusinsaAssignment
무신사 모바일개발그룹 과제전형 <br />
by 서혁범 <br />

## Development Environment
- Android Studio Electric Eel | 2022.1.1 Patch 1
- Android SDK Platform-Tools: 33.0.2 (Compile Sdk Version)
- Gradle Plugin Version: 7.3.1
- Gradle Version 7.5
- Language: Kotlin

## Application Version
- minSdkVersion : 26
- targetSdkVersion : 33

## Introduction
1. 앱 실행시 상품 정보 API 호출해 Scrollable Layout 으로 표현 (MainScreen.kt)
2. MVVM Pattern 적용 및 Clean architecture 형태로 프로젝트를 구성하였으나, 개발 편의상 모듈 분리는 하지 않음

## APIs
### Dagger, Hilt
Hilt 는 안드로이드에서 DI 를 위한 Jetpack 의 권장 라이브러리이며 Dagger 에서 제공하는 여러가지 annotation 을 활용하여 Clean Architectrue 형태의 프로젝트 구성을 편리하게함 <br />

### Coil 
여러 이미지 로딩 라이브러리가 존재하지만 Kotlin Base(Kotlin Coroutines)로 만들어져 kotlin 친화적일 것이라 생각해 사용하게 되었으며 성능상 다른 라이브러리와 드라마틱한 차이가 나지 않는 점과 Jetpack Compose 도 쉽게 사용이 가능한 점 때문에 해당 프로젝트에도 적용 되었음 <br />
(출처: https://coil-kt.github.io/coil/README-ko/) <br />

### Retrofit
Http 모듈을 직접 개발하는것은 많은 시간과 노력이 필요함. 빠른 서비스 개발을 위한 필수적인 라이브러리로서 사용하게되었음. <br />

## Preview
![device-2023-06-15-161544](https://github.com/hyeok713/MusinsaAssignment/assets/72484451/1c819c37-e308-4bcc-b98b-5cbf8d9f7529)
