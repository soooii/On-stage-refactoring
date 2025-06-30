# 🎵 On-Stage

> **링크를 콘텐츠로, 데이터를 가치로**  
> 아티스트와 유저를 연결하는 링크 기반 소셜 플랫폼

![main](https://github.com/user-attachments/assets/3d6505f1-1fba-4196-9b4d-68d0aa784e9d)

**On-stage**는 아티스트, 크리에이터, 일반 사용자가 자신만의 **콘텐츠 링크 페이지를 만들고 공유**하며, **실시간 통계 분석 기능**을 통해 **콘텐츠 노출**과 **반응 데이터를 정량화**할 수 있도록 지원하는 **웹 플랫폼**입니다.


## Key Features

### 🔗 링크 기반 콘텐츠 허브
On-stage는 사용자당 고유한 퍼블릭 페이지를 제공하며, 다양한 외부 콘텐츠를 통합할 수 있습니다.

- 사용자 고유 페이지 생성: `on-stage.link/page/{닉네임}`
- YouTube, Instagram, Spotify 등 외부 미디어 콘텐츠 임베딩 지원
- **자유도 높은 레이아웃 커스터마이징** (링크 그룹, 구간 여백 조정 등)

### 📊 실시간 유저 행위 및 위치 기반 통계

유저의 페이지 조회, 링크 클릭 등 행동 데이터를 자동 수집 및 시각화하며, **IP 기반 위치 통계까지 제공**하는 트래킹 시스템이 내장되어 있습니다.

- 페이지 조회수, 링크 클릭 수, 소셜 링크 클릭 실시간 기록
- 사용자 위치는 `ipapi`를 통한 자동 위치 수집

### 🤖 AI 기반 아티스트 뉴스 제공

On-stage는 외부 뉴스 크롤링과 AI를 결합하여 아티스트 관련 콘텐츠를 자동으로 정제/요약하는 **AI 콘텐츠 큐레이션 시스템**을 제공합니다.

- `OpenAI` 기반 **요약 자동화 파이프라인** 구축
- 기사 수집 → 요약 → 저장까지 완전 자동 처리

## 서비스 화면 예시
#### 1) 홈

| <div align="center">홈 화면</div>                                                                           | <div align="center">로그인</div>                                                                                     | <div align="center">로그아웃</div>                                                                           |
|----------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------|
| <img src="https://github.com/user-attachments/assets/7df7d7f3-d66e-4b5e-9fc0-58110d5e74d3" height="150"> | <img src="https://github.com/user-attachments/assets/41d1725f-23b6-4bfd-899b-ac037d1f7178" width="200" height="150"> | <img src="https://github.com/user-attachments/assets/f1092e11-1824-49c4-ab9c-5e3b2058fd87" height="150"> |

#### 2) 메인 화면

| <div align="center">프로필 설정</div>                                                                         | <div align="center">링크 생성</div>                                                                          | <div align="center">테마 설정</div>                                                                          |
|----------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------|
| <img src="https://github.com/user-attachments/assets/4902345f-cb00-4841-b677-b393206554de" height="150"> | <img src="https://github.com/user-attachments/assets/7a115ff1-e2a6-44a2-8dff-d5333e5f1e75" height="150"> | <img src="https://github.com/user-attachments/assets/05fa9362-469d-4acd-aaed-77c1f0b1c655" height="150"> |

#### 3) 아티스트 정보

| <div align="center">아티스트 소식</div>                                                                     | 
|-------------------------------------------------------------------------------------------------------|
| <img src="https://github.com/user-attachments/assets/0de009e8-81db-4d08-9650-135b2af05366" height="150"> |

#### 4) 분석 화면

| <div align="center">링크 분석 화면</div>                                                                       |
|----------------------------------------------------------------------------------------------------------|
| <img src="https://github.com/user-attachments/assets/3c431f26-f6a6-4bbb-bc08-1877f1b7774a" height="150"> |

#### 5) 공유 링크 화면 :  *당신에게 관심있는 사람들은 이 페이지를 통해 당신에 대해 알 수 있어요.*

| <div align="center">공유 링크 메인 화면</div>                                                                    | <div align="center">링크 공유 / 아티스트 정보 창</div>                                                                                                    |
|----------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://github.com/user-attachments/assets/11a7b5ef-fc19-449a-b3ce-ab84d12de4f8" height="150"> | <img src="https://github.com/user-attachments/assets/b401215c-6b5b-44c0-93a5-bcd80c3c0b12" height="150" style="display: block; margin: auto;"> |
---
##  Project Architecture
<img src="https://github.com/user-attachments/assets/66596ac5-a391-4718-8b04-b0efabd6ae72" height="400"><br>

| Layer        | Stack / Tool                                                                 |
|--------------|------------------------------------------------------------------------------|
| **Frontend** | React, HTML5, JavaScript, CSS3                               |
| **Backend**  | Java 17, Spring Boot, JWT, Spring Security, Spring Data JPA, QueryDSL, OpenAI            |
| **Database** | MySQL, Redis                                      |

