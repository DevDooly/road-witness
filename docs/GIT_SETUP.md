# RoadWitness GitHub 업로드 안내

권장 프로젝트명은 `RoadWitness`, 폴더와 저장소명은 `road-witness`입니다. 현재 프로젝트는 기존 `얌체신고` 폴더에서 작업하며, GitHub 저장소 이름은 로컬 폴더명과 달라도 됩니다.

## 준비된 파일

개발 계획과 구조 문서, 합성 이벤트 예시, `.gitignore`, `.gitattributes`, `.editorconfig`, 이슈·PR 템플릿을 버전 관리 대상으로 준비했습니다. 실제 영상·학습 자료·모델 파일·키와 환경 설정은 제외합니다.

커밋 전 다음 명령으로 상태와 제외 규칙을 확인합니다.

```powershell
git status --short --branch
git diff --check
git ls-files
git check-ignore data/raw/example.mp4 data/exports/report.json .env signing.jks ml/weights/detector.tflite
```

## GitHub 웹에서 생성하고 연결

1. GitHub에서 `road-witness` 저장소를 만듭니다. 초기에는 비공개 저장소를 제안합니다.
2. 로컬 파일을 올릴 것이므로 README, `.gitignore`, 라이선스 자동 생성 옵션은 비워 둡니다.
3. 아래 `$taskRemoteUrl`에 생성된 저장소 주소를 입력하고 프로젝트 폴더에서 실행합니다.

```powershell
$taskRemoteUrl = 'https://github.com/YOUR_ACCOUNT/road-witness.git'
git remote add origin $taskRemoteUrl
git push -u origin main
```

`YOUR_ACCOUNT`는 실제 사용자 또는 조직 이름으로 바꿉니다. 이미 `origin`이 있으면 먼저 `git remote -v`를 확인하고 올바른 저장소인지 확인합니다. 이 문서의 주소는 예시입니다.

## GitHub CLI로 생성하고 업로드

GitHub CLI가 설치되고 로그인되어 있으면 프로젝트 폴더에서 다음 명령을 사용할 수 있습니다.

```powershell
gh auth status
# 로그인이 필요한 경우에 실행
gh auth login
gh repo create road-witness --private --source=. --remote=origin --push
```

계정이나 조직 소유자, 공개 여부는 생성 전에 확인합니다. 원격 업로드는 저장소 대상이 확정된 뒤 수행합니다.

## 후속 커밋

```powershell
git add README.md docs apps ml examples .github .gitignore .gitattributes .editorconfig CONTRIBUTING.md
git diff --cached --stat
git diff --cached --check
git commit -m "docs: update RoadWitness development plan"
git push
```

새 앱 코드가 생기면 해당 경로를 추가합니다. 작업 파일을 한 번 검토한 뒤 필요한 경로를 스테이징합니다.

## 추천 폴더명 적용

원격 저장소는 `road-witness`라는 이름으로 만들 수 있으며 로컬 폴더를 즉시 바꿀 필요는 없습니다. 로컬 이름도 맞추려면 Codex와 Android Studio의 프로젝트 작업을 닫고 상위 폴더에서 직접 이름을 변경한 뒤 새 폴더를 다시 엽니다. `.git`은 함께 이동하며 원격 저장소 주소는 그대로 유지합니다.

## 라이선스

공개 배포 전에 코드 라이선스를 선택합니다. 모델·데이터·폰트 등 외부 자료는 코드와 별도의 이용 조건이 있을 수 있어 함께 확인합니다.
