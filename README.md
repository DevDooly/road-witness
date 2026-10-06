# RoadWitness 로드위트니스

주행 영상에서 교통법규 위반 의심 장면을 찾고, 사용자가 검토할 수 있도록 증거 영상과 신고 정보를 정리하는 모바일 애플리케이션입니다.

촬영과 후보 탐지는 주행 중 수행하고, 영상 편집·번호판 확인·신고 내용 검토는 주행이 끝난 뒤 진행합니다. 목표는 사용자가 확인한 근거를 바탕으로 신고를 준비하는 것입니다.

## 프로젝트 이름

| 구분 | 제안 | 의미 |
| --- | --- | --- |
| 추천 프로젝트명 | RoadWitness | 도로 위 상황의 기록과 증거를 정리하는 도우미 |
| 한글 표시명 | 로드위트니스 | 앱의 작업용 표시명 |
| 추천 폴더와 Git 저장소명 | `road-witness` | 영문 소문자로 통일해 경로와 저장소 관리에 사용 |
| 대안 | RoadProof / `road-proof` | 증거 준비 기능을 강조 |
| 대안 | LaneLens / `lane-lens` | 도로 장면 분석 기능을 강조 |

이름은 작업용 제안입니다. 출시 전 상표, 앱스토어 이름, 도메인 사용 가능 여부를 확인합니다. 현재 작업 폴더는 기존 경로를 유지했습니다.

## 개발 방향

첫 개발 플랫폼은 **Android로 확정**했습니다. Kotlin과 Jetpack Compose로 화면을 만들고, CameraX로 촬영·프레임 분석, Media3로 영상 편집, LiteRT로 기기 내 모델 추론, ML Kit 한국어 OCR로 번호판 읽기를 검증합니다. 기술 선택의 근거와 공식 문서는 [기술 구조](docs/ARCHITECTURE.md)에 있습니다.

실시간 촬영과 분석의 가능성을 먼저 실기기에서 확인합니다. 이후 영상 가져오기와 증거 편집 흐름을 완성하고, 실시간 후보 탐지를 연결합니다. iOS 확장은 Android에서 성능과 사용 흐름을 확인한 뒤 별도로 계획합니다.

## 핵심 흐름

1. 출발 전 촬영을 시작하거나 저장된 주행 영상을 가져옵니다.
2. 차량과 장면 변화를 분석해 위반 의심 후보를 생성합니다.
3. 후보 전후 영상과 원본 참조를 보존합니다.
4. 사용자가 영상 구간, 번호판, 촬영 시각, 위치와 의심 유형을 확인합니다.
5. 확인된 정보로 영상·요약문을 내보내고 신고 채널에서 직접 제출합니다.

신호위반·과속 등은 필요한 관측 정보가 다릅니다. 첫 버전의 자동 탐지는 관측 가능한 한 가지 장면 유형으로 범위를 좁히며, 실험을 통해 채택 여부를 결정합니다.

## 문서

- [개발 계획과 단계별 완료 기준](docs/PLAN.md)
- [영상 처리와 AI 기술 구조](docs/ARCHITECTURE.md)
- [영상과 개인정보 관리](docs/DATA_POLICY.md)
- [GitHub 업로드 방법](docs/GIT_SETUP.md)
- [작업과 검증 기준](CONTRIBUTING.md)
- [합성 이벤트 예시](examples/sample-event.json)

## 저장소 구성

```text
road-witness/
├── README.md
├── CONTRIBUTING.md
├── docs/
│   ├── PLAN.md
│   ├── ARCHITECTURE.md
│   ├── DATA_POLICY.md
│   └── GIT_SETUP.md
├── apps/android/README.md
├── ml/README.md
├── examples/sample-event.json
└── .github/
    ├── ISSUE_TEMPLATE/
    └── pull_request_template.md
```

## 현재 준비된 범위

개발 계획, 구조 문서, 이벤트 예시, Git 제외 규칙과 협업 템플릿이 준비되어 있습니다. Android 앱 코드와 학습된 모델은 다음 개발 단계의 산출물이며, 현재 저장소에는 실행 가능한 앱이 없습니다.

최초 개발 작업은 [1주차 검증](docs/PLAN.md)의 촬영·분석 병행 실험입니다. 앱 초기화 위치는 [apps/android](apps/android/README.md), 모델 실험 위치는 [ml](ml/README.md)입니다.

## Git 관리

실제 주행 영상, 번호판·위치 데이터, 모델 가중치와 비밀 키는 `.gitignore`로 제외합니다. 합성 예시와 작은 개발용 코드는 저장소에서 관리합니다. GitHub 업로드 명령은 [Git 안내](docs/GIT_SETUP.md)에 있습니다.

저장소는 [DevDooly/road-witness](https://github.com/DevDooly/road-witness)에서 공개로 관리합니다. 배포 라이선스는 아직 선택하지 않았습니다.
