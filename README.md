# 🎮 LolTube

> **리그 오브 레전드(LOL) 관련 YouTube 영상을 시청할 수 있는 Android 애플리케이션**
![Android](https://img.shields.io/badge/Platform-Android-green.svg)
![Kotlin](https://img.shields.io/badge/Language-Kotlin-purple.svg)
![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-blue.svg)
![Clean Architecture](https://img.shields.io/badge/Architecture-Clean%20Architecture-orange.svg)

---
## 프로젝트 소개

LolTube는 리그 오브 레전드 팬들을 위한 전용 YouTube 클라이언트 앱입니다. YouTube Data API v3을 활용하여 LOL 관련 영상을 쉽게 시청할 수 있습니다.

### 주요 기능

- **영상 미리보기**: 썸네일과 제목을 클릭하면 YouTube의 해당 영상으로 연결되어 시청 가능
- **Shorts 뷰**: JavaScript 기반 YouTube 임베드 플레이어로 LOL Shorts 영상을 세로 화면으로 시청
- **모던 UI**: Jetpack Compose를 활용한 직관적이고 반응형 UI
- **Clean Architecture**: 유지보수성과 확장성을 고려한 아키텍처 설계
- **고성능**: 코루틴과 StateFlow를 활용한 비동기 처리

---

## 문제 해결 (Troubleshooting)

### WebView 메모리 관리 최적화

**문제**: YouTube Shorts 영상을 연속으로 시청할 때 메모리 사용량이 계속 증가하는 현상

**원인**: WebView에서 JavaScript 히스토리가 계속 쌓이면서 메모리 누수 발생

#### 문제 상황

![Before Optimization](https://github.com/user-attachments/assets/8fb1cf76-da8a-4607-9e50-5073b1832937)

- WebView 히스토리를 삭제하지 않아서 메모리 사용량이 지속적으로 증가
- 장시간 사용 시 앱 성능 저하 및 OutOfMemoryError 발생 가능성

#### 해결 방법

![After Optimization](https://github.com/user-attachments/assets/eb5d3e2a-462f-47f6-8ff9-bf489cf749ca)

- WebView 히스토리를 다음 영상이 실행 될 때, 기존의 영상에 대해서는 주기적으로 삭제하여 메모리 최적화 달성
- 안정적인 메모리 사용 패턴으로 개선

**구현된 해결책**

```kotlin
// WebView 메모리 최적화
webView.clearHistory()
webView.clearCache(true)
```

---
### Clean Architecture를 통한 모듈화 리팩토링

**문제**: 단일 모듈로 구성된 프로젝트로 인한 코드 복잡도 증가 및 유지보수성 저하

**해결 방법**: Multi-Module Clean Architecture 도입

#### 개선 효과

- **코드 재사용성**: 각 레이어별 독립적인 모듈로 분리하여 다른 Feature에서 재활용 가능
- **빌드 성능**: 변경된 모듈만 재빌드되어 전체 빌드 시간 단축
- **테스트 격리**: 각 레이어별 독립적인 테스트 작성 및 실행 가능

```
기존: 단일 app 모듈
개선: app + feature(home, main, shorts) + core(data, domain, network, designsystem)
```

---
### MVI 패턴 도입을 통한 상태 관리 개선

**문제**: 복잡한 UI 상태와 에러 처리로 인한 코드 복잡도 증가

**해결 방법**: MVI(Model-View-Intent) 패턴 도입으로 일관된 상태 관리

#### 개선 효과

- **예측 가능한 상태 흐름**: 단방향 데이터 흐름으로 상태 변화 추적 용이
- **에러 처리 일관성**: ShortsUiState 통한 통합된 에러 처리
- **테스트 용이성**: 상태 기반 테스트로 UI 로직 검증 간소화

```kotlin
// MVI UiState 예시
sealed class ShortsUiState {
    data object Loading : ShortsUiState()
    data class Success(val videos: List<Video>) : ShortsUiState()
    data class Error(val errorType: ErrorType) : ShortsUiState()
}
```

---
### State Hoisting 패턴 적용

**문제**: Stateful Composable로 인한 테스트 및 재사용성 저하

**해결 방법**: State Hoisting 패턴으로 상태 분리

#### 개선 효과

- **테스트 용이성**: 상태와 UI 로직 분리로 독립적 테스트 가능
- **재사용성**: Stateless Composable로 다양한 상황에서 재사용
- **Preview 지원**: 다양한 상태의 Preview 구성 가능

---

## 아키텍처

본 프로젝트는 **Clean Architecture**와 **Multi-Module** 구조를 채택하여 확장 가능하고 테스트 가능한 코드베이스를 구축하였습니다.

### 📂 모듈 구조

```
lolTube
 ├── app                        # Application 모듈
 ├── feature
 │   ├── home                   # 홈 화면 Feature 모듈
 │   └── main                   # 메인 Activity 모듈
 └── core
     ├── data                   # 데이터 레이어 (RepositoryImpl 구현)
     ├── domain                 # 도메인 레이어 (UseCase, Model)
     ├── network                # 네트워크 레이어 (API 통신)
     └── designsystem           # 디자인 시스템
```

---
## 🛠️ 기술 스택

### **Android & Kotlin**

- **Kotlin**: 100% Kotlin으로 개발
- **Min SDK**: 21 (Android 5.0)
- **Target SDK**: 35 (Android 14)

### **UI & UX**

- **Jetpack Compose**: 선언적 UI 프레임워크
- **Material Design 3**: 최신 디자인 가이드라인
- **Coil**: 이미지 로딩 및 캐싱

### **아키텍처**

![Android Clean Architecture](https://raw.githubusercontent.com/bufferapp/android-clean-architecture-boilerplate/master/art/architecture.png)

- **Clean Architecture**: 레이어 분리와 의존성 역전
- **Multi-Module**: 모듈화된 프로젝트 구조
- **MVVM**: Model-View-ViewModel 패턴

### **의존성 주입**

- **Hilt**: Google 권장 DI 프레임워크

### **네트워킹**

- **Retrofit**: RESTful API 통신
- **OkHttp**: HTTP 클라이언트
- **Gson**: JSON 파싱

### **비동기 처리**

- **Coroutines**: 비동기 프로그래밍
- **StateFlow**: 반응형 상태 관리

### **테스팅**

- **JUnit**: 단위 테스트 프레임워크
- **Mockito**: 모킹 프레임워크
- **Coroutines Test**: 코루틴 테스트
- **Jacoco**: 코드 커버리지 분석

---

## 🧪 테스트 커버리지

프로젝트 전체에 **33개의 단위 테스트**가 작성되어 있으며, 높은 코드 커버리지를 달성했습니다.

| 모듈               | 테스트 수   | 커버리지     |
|------------------|---------|----------|
| **core/data**    | 22개     | 95%+     |
| **core/domain**  | 5개      | 73%      |
| **feature/home** | 6개      | 80%+     |
| **전체**           | **33개** | **85%+** |

<img width="1023" height="243" alt="Image" src="https://github.com/user-attachments/assets/e91c92c5-04d6-4587-b8ce-f89dfb890a1b" />
<img width="1023" height="243" alt="Image" src="https://github.com/user-attachments/assets/213d7783-26f9-442c-9a91-ced77c095d3f" />

### 주요 테스트 영역

- **SearchVideosUseCase**: 비즈니스 로직 테스트
- **LolTubeViewModel**: StateFlow와 코루틴 테스트
- **VideoMapper**: 데이터 매핑 로직 테스트
- **Repository**: 데이터 레이어 테스트
