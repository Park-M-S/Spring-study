![image](https://github.com/user-attachments/assets/edeae21e-132e-4ab1-af57-2ee9eb1f4649)# Spring Framework에서 MariaDB 연결 설정

## 1. **MariaDB 설치 및 데이터베이스 생성**

1. MariaDB 10.11.11 버전을 설치한다</br>
   [설치 링크](https://mariadb.org/download/?t=mariadb&p=mariadb&r=10.11.11&os=windows&cpu=x86_64&pkg=msi&mirror=blendbyte)
  </br>
2. 설치 후 세팅은 아래와 같이 설정한다</br>
   </br>
   
   | 항목 | 값 |
   |-------|-------|
   | 사용자 | root |
   | 비밀번호 | 1234 |
   | 포트 | 3500 |
  
   </br>
   해당 내용만 보고 적용하기 어려울수 있으니 이미지 자료도 추가.
   </br>

   설치 과정 1 </br>
   ![image](https://github.com/user-attachments/assets/f5979b69-ea46-4b8d-badd-5dc9554f761d)
   </br>
   설치 과정 2 </br>
   ![image](https://github.com/user-attachments/assets/ea92ded2-b560-4ee0-8b0e-7426d7fc86f2)
   </br>

## 2. **Spring 프로젝트 내 에서 구성 파일 작성**

일단 전체 파일 구조는 아래와 같다.</br>

hospital-backend/
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── example/
        │           ├── config/
        │           ├── controller/
        │           ├── dao/
        │           └── model/
        ├── resources/
        │   └── db.properties
        └── webapp/
            └── WEB-INF/
                ├── web.xml
                ├── root-context.xml
                └── servlet-context.xml


   
   

   

