# 게시판 프로젝트
## :mega: 소개
>CRUD 구현 및 페이징처리, AJAX 댓글기능, 회원기능 학습을 위한 **게시판 프로젝트**입니다  
>구현 기간은 2024.1.30 ~ 2025.2.18 입니다.
## ✔ 개발환경
1. IDE: IntelliJ IDEA Community
2. Spring Boot 2.6.13
3. JDK 11
4. mysql
5. Spring Data JPA
6. Thymeleaf

## ✔ 게시판 주요기능
1. 글쓰기
2. 글 목록
3. 글 조회
4. 글 수정
5. 글삭제
6. 페이징처리
7. 파일(이미지)첨부하기
   - 단일 파일 첨부
   - 다중 파일 첨부
8. 댓글작성
9. 회원가입
10. 로그인
11. 회원 목록 조회
12. 회원 상세 조회
13. 회원정보 수정 및 삭제
14. 로그아웃
    
## ✔ MySQL 데이터베이스 및 계정 설정 (DOCKER)
```
docker run --name board_demo -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=db_board -e MYSQL_USER=user_board -e MYSQL_PASSWORD=1234 -p 3306:3306 -d mysql:8
```
