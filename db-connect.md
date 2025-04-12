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
```
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
```
</br>

이제 db.properties 부터 작성

```
db세팅 내용

jdbc.driverClassName=org.mariadb.jdbc.Driver
jdbc.url=jdbc:mariadb://localhost:3500/hospital_db
jdbc.username=root
jdbc.password=1234
```

<br>

web.xml

```
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="https://jakarta.ee/xml/ns/jakartaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee
                             https://jakarta.ee/xml/ns/jakartaee/web-app_5_0.xsd"
         version="5.0">

    <!-- Root Context 설정 -->
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>/WEB-INF/root-context.xml</param-value>
    </context-param>

    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>

    <!-- DispatcherServlet 설정 -->
    <servlet>
        <servlet-name>appServlet</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>/WEB-INF/servlet-context.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>

    <servlet-mapping>
        <servlet-name>appServlet</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>

</web-app>
```

root-context.xml

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="https://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="https://www.springframework.org/schema/context"
       xsi:schemaLocation="https://www.springframework.org/schema/beans
                           https://www.springframework.org/schema/beans/spring-beans.xsd
                           https://www.springframework.org/schema/context
                           https://www.springframework.org/schema/context/spring-context.xsd">

    <!-- DB 프로퍼티 로딩 -->
    <context:property-placeholder location="classpath:db.properties"/>

    <!-- 데이터소스 설정 -->
    <!-- 얜 db.properties에 작성한 내용들을 각각 url, username등으로 가져오는 것이다. class 안에 작성된 HikariCP는 Spring에서 채택한 커넥션 풀이다-->
    <!-- 커넥션 풀에 관한 자세한 내용은 하위에 기술 -->
    <bean id="dataSource" class="com.zaxxer.hikari.HikariDataSource">
        <property name="driverClassName" value="${jdbc.driverClassName}" />
        <property name="jdbcUrl" value="${jdbc.url}" />
        <property name="username" value="${jdbc.username}" />
        <property name="password" value="${jdbc.password}" />
    </bean>

    <!-- JdbcTemplate -->
    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
        <property name="dataSource" ref="dataSource" />
    </bean>

    <!-- DAO 등록 -->
    <bean id="hospitalDao" class="com.example.dao.HospitalDao">
        <property name="jdbcTemplate" ref="jdbcTemplate" />
    </bean>

</beans>
```

servlet-context.xml

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="https://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="https://www.springframework.org/schema/context"
       xmlns:mvc="https://www.springframework.org/schema/mvc"
       xsi:schemaLocation="https://www.springframework.org/schema/beans
                           https://www.springframework.org/schema/beans/spring-beans.xsd
                           https://www.springframework.org/schema/context
                           https://www.springframework.org/schema/context/spring-context.xsd
                           https://www.springframework.org/schema/mvc
                           https://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!-- 컴포넌트 스캔 -->
    <context:component-scan base-package="com.example.controller" />

    <!-- MVC 기본 설정 -->
    <mvc:annotation-driven/>

    <!-- CORS (Vue 요청 대응용) -->
    <bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping">
        <property name="corsConfigurations">
            <map>
                <entry key="/**">
                    <bean class="org.springframework.web.cors.CorsConfiguration">
                        <property name="allowedOrigins" value="*" />
                        <property name="allowedMethods">
                            <list>
                                <value>GET</value>
                                <value>POST</value>
                                <value>PUT</value>
                                <value>DELETE</value>
                                <value>OPTIONS</value>
                            </list>
                        </property>
                        <property name="allowedHeaders" value="*" />
                    </bean>
                </entry>
            </map>
        </property>
    </bean>

</beans>
```

이상 db에 직접적으로 관여하는 파일들을 작성하였다.</br>
물론 세부적으로 db에서 getter, setter 설정은 DTO(Spring Framework에서는 Model로 사용)에서 담당하고</br>
DAO에서는 아래와 같은 기능을 수행한다.</br>

```
DAO의 주요 역할
데이터베이스와의 연결 관리

데이터베이스에 연결하고, 쿼리를 실행하며, 데이터를 가져오는 역할을 합니다.

일반적으로 JDBC, JPA, MyBatis 등의 라이브러리를 사용하여 데이터베이스와의 연결을 처리합니다.

CRUD 작업 구현

CRUD (Create, Read, Update, Delete) 작업을 데이터베이스에 수행합니다.

각 데이터베이스 작업을 메서드로 구현하여, 필요한 데이터를 저장하거나 조회하고 수정 및 삭제할 수 있게 해줍니다.

SQL 쿼리 실행

데이터베이스에서 필요한 데이터를 SQL 쿼리로 조회하거나, 삽입, 수정, 삭제하는 작업을 담당합니다.

결과 반환

조회 결과를 DTO 객체나 List 형태로 반환하여, 그 결과를 Service나 Controller에 전달합니다.

트랜잭션 관리 (선택적)

데이터베이스 트랜잭션을 관리하여, 여러 쿼리가 성공적으로 실행되거나 롤백되도록 보장합니다.

Spring의 경우 @Transactional 어노테이션을 사용해 트랜잭션을 관리할 수 있습니다.

```

   
   

   

