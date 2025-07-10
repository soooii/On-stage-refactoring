# 🎵 On-Stage

> **링크를 콘텐츠로, 데이터를 가치로**  
> 아티스트와 유저를 연결하는 링크 기반 소셜 플랫폼

<img width="2558" height="1018" alt="Image" src="https://github.com/user-attachments/assets/f5a25d8f-737c-4dae-b7d4-6bff37133abb" />

**On-stage**는 아티스트, 크리에이터, 일반 사용자가 자신만의 **콘텐츠 링크 페이지를 만들고 공유**하며, **실시간 통계 분석 기능**을 통해 **콘텐츠 노출**과 **반응 데이터를 정량화**할 수 있도록 지원하는 **웹 플랫폼**입니다.


## Key Features

### 🔗 링크 기반 콘텐츠 허브
On-stage는 사용자당 고유한 퍼블릭 페이지를 제공하며 다양한 외부 콘텐츠를 통합할 수 있습니다.

- 사용자 고유 페이지 생성: `on-stage.link/page/{닉네임}`
- YouTube, Instagram, Spotify 등 외부 미디어 콘텐츠 임베딩 지원
- **자유도 높은 레이아웃 커스터마이징** (링크 그룹, 구간 여백 조정 등)

### 📊 실시간 유저 행위 및 위치 기반 통계

유저의 페이지 조회, 링크 클릭 등 행동 데이터를 자동 수집 및 시각화하며 **IP 기반 위치 통계까지 제공**하는 트래킹 시스템이 내장되어 있습니다.

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
| <img src="https://github.com/user-attachments/assets/af229237-4f25-4e52-87db-ed1d03ce388f" height="150"> | <img src="https://github.com/user-attachments/assets/58865660-a166-4adc-8428-855a0e822583" width="200" height="150"> | <img src="https://github.com/user-attachments/assets/4acc5e17-2932-4c10-98ac-0e4c09921880" height="150"> |

#### 2) 메인 화면

| <div align="center">프로필 설정</div>                                                                         | <div align="center">링크 생성</div>                                                                          | <div align="center">테마 설정</div>                                                                          |
|----------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------|
| <img src="https://github.com/user-attachments/assets/d525e8d2-3906-41d0-9cf6-120eb05ce55f" height="150"> | <img src="https://github.com/user-attachments/assets/958b6bac-fb6e-4538-a907-deedf5514cbc" height="150"> | <img src="https://github.com/user-attachments/assets/bb7dee13-5a74-4732-ad77-82cba72b5232" height="150"> |

#### 3) 아티스트 정보

| <div align="center">아티스트 소식</div>                                                                     | 
|-------------------------------------------------------------------------------------------------------|
| <img src="https://github.com/user-attachments/assets/5682f492-1a98-4790-beae-61f56a9c821c" height="150"> |

#### 4) 분석 화면

| <div align="center">링크 분석 화면</div>                                                                       |
|----------------------------------------------------------------------------------------------------------|
| <img src="https://github.com/user-attachments/assets/6cb13b15-0d83-4843-bf37-6a814d58b7c7" height="150"> |

#### 5) 공유 링크 화면 :  *당신에게 관심있는 사람들은 이 페이지를 통해 당신에 대해 알 수 있어요.*

| <div align="center">공유 링크 메인 화면</div>                                                                    | <div align="center">링크 공유 / 아티스트 정보 창</div>                                                                                                    |
|----------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://github.com/user-attachments/assets/9fcc0d7d-a12b-490a-9f37-e65c429932c6" height="150"> | <img src="https://github.com/user-attachments/assets/54438648-76d3-47e9-8577-d50be00490a4" height="150" style="display: block; margin: auto;"> |

##  Project Architecture
<img src="https://github.com/user-attachments/assets/2aff9d24-10be-4e20-a2f2-dd8f23ec05d1" height="400"><br>

| Layer        | Stack / Tool                                                                 |
|--------------|------------------------------------------------------------------------------|
| **Frontend** | React, HTML5, JavaScript, CSS3                               |
| **Backend**  | Java 17, Spring Boot, JWT, Spring Security, Spring Data JPA, QueryDSL, OpenAI            |
| **Database** | MySQL, Redis                                      |

