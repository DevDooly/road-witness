# RoadWitness Android 앱

Kotlin과 Jetpack Compose로 만든 초기 Android 앱입니다. 홈·주행·사건·설정 화면과 탭 이동, 뒤로 가기로 홈 복귀, 선택한 탭 복원을 제공합니다.

촬영, 차량·번호판 분석, 사건 저장, 영상 편집과 신고 자료 내보내기는 다음 단계에서 연결합니다. 촬영 버튼은 비활성 상태이며 실제 영상이나 개인정보를 수집하지 않습니다.

## 개발 환경

| 항목 | 버전 |
| --- | --- |
| Gradle Wrapper | 8.14, 공식 배포 SHA256 고정 |
| Android Gradle Plugin | 8.13.2 |
| Kotlin과 Compose Compiler | 2.3.10 |
| Compose BOM | 2024.10.00 |
| 최소 Android | 8.0 / API 26 |
| Compile SDK와 Target SDK | API 36 |
| Java 소스 대상 | 17 |
| 첫 검증의 빌드 JDK | 21 |

AGP·Kotlin·Gradle은 공식 호환 범위에 맞춰 고정했습니다. [AGP 호환 안내](https://developer.android.com/build/releases/agp-8-13-0-release-notes), [Kotlin 호환 안내](https://kotlinlang.org/docs/gradle-configure-project.html)

앱 패키지는 GitHub 소유자에 맞춘 `io.github.devdooly.roadwitness`입니다. 개발 APK는 `.debug` 접미사를 사용합니다. 배포용 패키지와 서명은 출시 전에 확정합니다.

## Android Studio에서 열기

저장소의 `apps/android` 폴더를 프로젝트로 열고 Gradle 동기화를 진행합니다. SDK 위치는 Android Studio에서 설정하며 `local.properties`와 서명 파일은 Git에서 제외합니다.

## 명령줄 빌드

Android SDK 위치를 자신의 설치 경로에 맞춰 설정합니다. 아래는 Windows 기본 설치 위치의 예시이며 이 폴더에서 실행합니다.

```powershell
$taskSdkRoot = Join-Path $env:USERPROFILE 'AppData\Local\Android\Sdk'
Set-Content -LiteralPath local.properties -Value ('sdk.dir=' + $taskSdkRoot.Replace('\', '/').Replace(':', '\:')) -Encoding ASCII
.\gradlew.bat :app:assembleDebug :app:lintDebug --console=plain
```

출력 APK는 `app/build/outputs/apk/debug/app-debug.apk`입니다. Linux와 macOS에서는 SDK를 설정한 뒤 `./gradlew :app:assembleDebug :app:lintDebug`를 사용합니다.

OneDrive 등 동기화 경로에서 중간 파일 잠금이 발생하면 앱 생성 파일을 로컬 임시 폴더로 분리할 수 있습니다.

```powershell
$taskBuildDir = Join-Path $env:TEMP 'roadwitness-android-build'
.\gradlew.bat :app:assembleDebug :app:lintDebug "-Proadwitness.buildDir=$taskBuildDir" --no-daemon --console=plain
```

이때 APK는 `$taskBuildDir/outputs/apk/debug/app-debug.apk`, lint 보고서는 `$taskBuildDir/reports/`에 생성됩니다. 이 옵션은 소스 위치와 앱의 기능을 변경하지 않습니다.

## 연결된 휴대폰에서 실행

USB 디버깅을 승인한 휴대폰 한 대가 연결된 상태에서 실행합니다. 여러 기기를 사용할 때는 `-d` 대신 `-s`와 대상 기기 ID를 지정합니다.

```powershell
$taskAdb = Join-Path $taskSdkRoot 'platform-tools\adb.exe'
& $taskAdb -d install -r app/build/outputs/apk/debug/app-debug.apk
& $taskAdb -d shell am start -W -n io.github.devdooly.roadwitness.debug/io.github.devdooly.roadwitness.MainActivity
```

## 파일 구조

```text
apps/android/
├── app/
│   ├── build.gradle.kts
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/io/github/devdooly/roadwitness/
│       │   ├── MainActivity.kt
│       │   └── ui/RoadWitnessApp.kt
│       └── res/
├── gradle/
│   ├── libs.versions.toml
│   └── wrapper/
├── gradlew
├── gradlew.bat
├── build.gradle.kts
└── settings.gradle.kts
```

다음 구현 작업은 [1주차 계획](../../docs/PLAN.md)의 CameraX 촬영·저장 실험입니다.

2026년 10월 7일 Samsung SM-S918N / Android 16에서 설치·실행과 화면 이동을 확인했습니다. [실기기 확인 결과](../../docs/ANDROID_VERIFICATION.md)에 빌드와 검증 범위를 기록했습니다.
