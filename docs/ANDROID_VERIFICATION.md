# RoadWitness 기본 앱 실행 확인

확인일: 2026년 10월 7일

Kotlin·Compose 기본 앱을 빌드해 연결된 삼성 휴대폰에 설치했습니다. 홈·주행·사건·설정 화면과 탐색 동작을 확인했으며, 검증 종료 후 홈 화면에서 앱이 실행 중인 것을 확인했습니다.

## 검증 환경

| 항목 | 값 |
| --- | --- |
| 기기 | Samsung SM-S918N |
| OS | Android 16 / API 36 |
| 개발용 패키지 | `io.github.devdooly.roadwitness.debug` |
| 앱 버전 | `0.1.0-dev`, versionCode 1 |
| 최소와 대상 SDK | API 26 / API 36 |
| 빌드 | Gradle 8.14, AGP 8.13.2, Kotlin 2.3.10, JDK 21 |

## 결과

| 검증 | 결과 |
| --- | --- |
| `:app:assembleDebug` | 성공 |
| `:app:lintDebug` | 성공, 오류 0건, 경고 9건 |
| ADB APK 설치 | 성공 |
| 최초 실행 | `Status: ok`, `LaunchState: COLD` |
| 홈 화면 | 화면 문구와 실행 중인 프로세스 확인 |
| 홈의 주행 화면 열기 | 주행 화면으로 이동 |
| 촬영 시작 | 준비 중 표시와 비활성 상태 확인 |
| Android 뒤로 가기 | 주행 화면에서 홈 복귀 |
| 사건 탭 | 빈 사건 목록 표시 |
| 설정 탭 | 앱 상태와 보관 방식 안내 표시 |
| 주행과 홈 탭 | 각 화면 이동과 마지막 홈 복귀 확인 |

화면 요소의 실제 위치를 UI Automator로 읽고 ADB 입력으로 눌러 검증했습니다. 실행 화면 캡처와 XML은 Git에서 제외되는 앱의 `.gradle/device-verification`에 보관합니다.

남은 lint 경고는 고정한 의존성의 새 버전 안내 7건, 낮은 Android 버전의 백업 설정 안내 1건, 비어 있는 이전 아이콘 폴더의 버전 표기 안내 1건입니다. 현재 앱은 영상과 사건 데이터를 수집하지 않습니다.

## 재현 방법

[Android 앱 안내](../apps/android/README.md)의 빌드·설치 명령을 사용합니다. 이번 검증에서는 프로젝트 경로의 중간 파일 잠금을 피해 `roadwitness.buildDir` 옵션으로 임시 출력 폴더에서 빌드했습니다. 검증한 APK는 `apps/android/app/build/outputs/apk/debug/app-debug.apk`에도 복사했습니다.

촬영·분석·영상 편집의 성능 검증은 [1주차 개발 계획](PLAN.md)의 다음 작업입니다. 이번 확인은 기본 앱의 빌드와 화면 실행 범위입니다.
