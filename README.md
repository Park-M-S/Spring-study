# Spring Framework 기반 병원 가이드 웹 페이지 학습 로드맵

## 🎯 프로젝트 개요
- **대상**: 고령층 사용자
- **기능 요약**:
  1. 사용자가 진료 과목을 선택
  2. Geolocation으로 사용자 현재 위치 획득
  3. 선택한 과목과 위치 기준으로 가까운 병원 목록 표시

---

## 1단계: 개발 환경 설정
- [x] JDK 21 설치
- [x] Maven 프로젝트 생성 (WAR)
- [x] Eclipse IDE 2025-03 버전 설치
- [x] Spring Framework 6 설치
- [x] Tomcat 10.1 설정
- [x] MariaDB (10.11.11) 서버 설치 및 DB 생성

---

## 2단계: 기본 프로젝트 구조 구성
- [ ] 패키지 구조 설정 (Controller, Service, DAO, Model, Config)
- [x] Framework 구성 xml (web.xml, root-context.xml, servlet-context.xml 등) 작성

---

## 3단계: 데이터베이스 연동
- [x] MariaDB 테이블 sql문으로 삽입 (`hospital_main`, `hospital_detail` 등)
- [x] HikariCP로 커넥션 풀 설정
- [x] JdbcTemplate 설정 및 DAO 구현

---

## 4단계: 병원 API 만들기
- [ ] `/api/hospitals` GET API 만들기
- [ ] 진료과, 위치(위도/경도) 기준으로 검색 가능하도록 구현
- [ ] 거리 계산 로직 적용 (Haversine 공식을 활용한 정렬)

---

## 5단계: 프론트엔드 연동 (Vue.js)
- [ ] Vue CLI 설치 및 프로젝트 생성
- [ ] 사용자의 현재 위치 Geolocation으로 받아오기
- [ ] 카카오맵 API 연동
- [ ] 병원 목록을 지도에 마커로 표시
- [ ] 반경 필터 및 진료과 필터 구현

---

## 6단계: 사용자 친화적 UI 구현
- [ ] 고령층을 위한 큰 글씨, 명확한 버튼
- [ ] 직관적인 선택 UI (예: 진료과 버튼형 선택)
- [ ] 로딩 애니메이션, 오류 메시지 처리

---

## 7단계: 테스트 및 배포
- [ ] 기능 테스트 (진료과 선택 → 위치 기반 병원 리스트 출력)
- [ ] 크롬, 사파리 등 브라우저 호환성 확인
- [ ] Tomcat 서버에 배포

---

## 📝 부록
- [x] Geolocation API 예제
- [x] 카카오 지도 마커 예제
