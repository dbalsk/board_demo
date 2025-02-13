# 게시판 프로젝트
## ✔ 개발환경
1. IDE: IntelliJ IDEA Community
2. Spring Boot 2.6.13
3. JDK 11
4. mysql
5. Spring Data JPA
6. Thymeleaf

## ✔ 게시판 주요기능
1. 글쓰기(/board/save)
2. 글목록(/board/)
3. 글조회(/board/{id})
4. 글수정(/board/update/{id})
    - 상세화면에서 수정 버튼 클릭
    - 서버에서 해당 게시글의 정보를 가지고 수정 화면 출력
    - 제목, 내용 수정 입력 받아서 서버로 요청
    - 수정 처리
5. 글삭제(/board/delete/{id})
6. 페이징처리(/board/paging?page=1)
7. 파일(이미지)첨부하기
   - 단일 파일 첨부
   - 다중 파일 첨부
8. 댓글작성
   
## ✔ MySQL 데이터베이스 및 계정 설정 (DOCKER)
```
docker run --name my-mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=db_board -e MYSQL_USER=user_board -e MYSQL_PASSWORD=1234 -p 3306:3306 -d mysql:8
```
