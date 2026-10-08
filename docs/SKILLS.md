# 개발 단계별 스킬 추천

확인일: 2026-10-08. 기본 앱의 빌드·설치·실기기 실행은 완료했으며, 다음 작업은 CameraX 촬영과 프레임 분석의 병행 검증입니다. 아래 Android 스킬은 추천 목록이며 아직 설치하지 않았습니다.

## 우선순위

| 적용 시점 | 스킬과 실제 지침 | 추천 이유 | 필요한 준비 |
| --- | --- | --- | --- |
| 다음 촬영 구현 | [camerax](https://github.com/android/skills/blob/main/camera/camerax/SKILL.md) | 녹화 시작·종료 이벤트, 카메라·음성 권한, UI 스레드 처리, 분석·ML Kit 연결과 발열 관련 지침을 활용할 수 있습니다. | 선택한 CameraX 버전과 기기 지원 조합을 확인하고, 프리뷰·녹화부터 적용합니다. 음성 녹음은 제품 범위를 정한 뒤 연결합니다. |
| 반복 실기기 검증 | [android-cli](https://github.com/android/skills/blob/main/devtools/android-cli/SKILL.md) | 앱 실행, 화면·UI 구조 확인, SDK·에뮬레이터 관리와 공식 문서 검색에 사용할 수 있습니다. | 별도의 `android` CLI 도구가 필요합니다. 현재 Gradle·ADB 검증은 이미 동작하므로 반복 검증을 확장할 때 도입을 검토합니다. |
| 촬영·추론 성능 측정 | [android-profiler](https://github.com/android/skills/blob/main/profilers/android-profiler/SKILL.md) | 시스템 trace, 메모리와 호출 스택 분석을 통해 촬영·추론 병목과 누수를 조사할 수 있습니다. | 촬영과 추론이 연결된 뒤 측정 목표를 정합니다. 스킬의 환경 안내와 선택한 분석 도구의 Windows 지원·의존성을 먼저 확인합니다. |

세 스킬의 출처는 Google LLC가 작성하는 Android 조직의 [android/skills](https://github.com/android/skills)입니다. 실제 `SKILL.md`를 읽고 현재 작업과 맞는 범위로 선정했습니다. 공통 저장소의 GitHub stars는 확인 당시 약 7.7K이며 개별 스킬의 평가가 아닙니다.

skills.sh에서 확인한 설치수는 [camerax 약 900](https://www.skills.sh/android/skills/camerax), [android-cli 2.4K](https://www.skills.sh/android/skills/android-cli), [android-profiler 657](https://www.skills.sh/android/skills/android-profiler)입니다. 수치는 조회 시점에 따라 달라집니다. 설치수 1,000 미만인 스킬은 공식 출처와 실제 구현 지침의 적합성을 근거로 추천했습니다. `android-cli` 페이지에는 Snyk `Warn` 표시가 있어 설치 검토 시 상세 사유도 확인해야 합니다. 현재 조사에서는 그 상세 사유를 확인하지 않았습니다.

## 설치 요청 시 사용할 명령

아래 명령은 skills.sh에 제시된 방식이며 이번 작업에서는 실행하지 않았습니다. Windows PowerShell에서는 `npx.cmd`를 사용합니다. 필요한 스킬을 하나씩 선택해 설치하고, 설치 결과에서 Codex가 사용하는 경로를 확인합니다.

```powershell
npx.cmd skills add https://github.com/android/skills --skill camerax
npx.cmd skills add https://github.com/android/skills --skill android-cli
npx.cmd skills add https://github.com/android/skills --skill android-profiler
```

스킬 설치와 스킬이 사용하는 외부 실행 도구 설치는 별도입니다. 현재 SDK나 라이브러리 버전을 스킬의 예제에 맞춰 일괄 변경하지 않고, 프로젝트의 버전과 실기기 지원 여부를 확인합니다.

## 현재 사용할 수 있는 스킬

| 스킬 | 사용 시점 |
| --- | --- |
| `find-skills` | 새 개발 단계에서 필요한 스킬을 검색하고 실제 출처·내용·호환성을 확인합니다. 이번 추천 조사에 사용했습니다. |
| `openai-docs` | Codex의 `AGENTS.md`와 스킬 설정을 공식 문서로 확인합니다. 이번 작업 지침 작성에 사용했습니다. |
| `skill-creator` | 빌드 → 설치 → 실행 → 화면 확인 → 결과 기록이 반복되면 RoadWitness 전용 실기기 검증 스킬을 만드는 데 사용할 수 있습니다. 아직 별도 스킬은 만들지 않았습니다. |

자동 commit·push는 [AGENTS.md](../AGENTS.md)에 지속 지침으로 저장했습니다. Codex의 프로젝트 지침 로딩은 [공식 AGENTS.md 안내](https://learn.chatgpt.com/docs/agent-configuration/agents-md), 스킬 구조와 설치는 [공식 스킬 안내](https://learn.chatgpt.com/docs/build-skills)를 참고했습니다.

LiteRT 추론, 번호판 OCR, Media3 구간 편집 단계에 들어가면 해당 작업에 맞는 스킬을 추가 조사합니다. 기존 Compose UI 작업에는 현재 버전에서 동작하는 공식 문서를 사용합니다.
