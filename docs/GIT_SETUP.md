# RoadWitness GitHub 업로드 안내

권장 프로젝트명은 `RoadWitness`, 폴더와 저장소명은 `road-witness`입니다. 현재 프로젝트는 기존 `얌체신고` 폴더에서 작업하며, GitHub 저장소 이름은 로컬 폴더명과 달라도 됩니다.

GitHub 저장소는 [DevDooly/road-witness](https://github.com/DevDooly/road-witness)이며 사용자 결정에 따라 공개로 관리합니다. 기본 브랜치는 `main`, 원격 이름은 `origin`입니다.

## 준비된 파일

개발 계획과 구조 문서, 합성 이벤트 예시, `.gitignore`, `.gitattributes`, `.editorconfig`, 이슈·PR 템플릿을 버전 관리 대상으로 준비했습니다. 실제 영상·학습 자료·모델 파일·키와 환경 설정은 제외합니다.

커밋 전 다음 명령으로 상태와 제외 규칙을 확인합니다.

```powershell
git status --short --branch
git diff --check
git ls-files
git check-ignore data/raw/example.mp4 data/exports/report.json .env signing.jks ml/weights/detector.tflite
```

## 원격 저장소 연결

원격 연결이 없는 로컬 저장소에서 다음 명령을 사용합니다. 이미 `origin`이 있으면 먼저 `git remote -v`로 주소를 확인합니다.

```powershell
git remote add origin https://github.com/DevDooly/road-witness.git
git push -u origin main
```

## 다른 개발 환경에서 복제

프로젝트를 둘 상위 폴더에서 다음 명령을 실행합니다.

```powershell
git clone https://github.com/DevDooly/road-witness.git
Set-Location road-witness
```

push에는 해당 저장소의 쓰기 권한과 GitHub 인증이 필요합니다.

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
