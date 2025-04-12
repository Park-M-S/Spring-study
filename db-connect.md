# Spring Framework에서 MariaDB 연결 설정

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
설명은 이렇게 길지만 간단히 설명하자면 데이터베이스와 상호작용을 해주고 CRUD작업도 담당하며 Model 객체로 데이터를 반환해서 Service와 Controller에서 사용할 수 있도록 돕는 기능이라 보면 된다

</br>

## 3. **커넥션 풀이 뭔가?**

1. 커넥션 풀의 기능</br>
   커넥션 풀은 데이터베이스 연결을 효율적으로 관리하여 성능을 향상시키고, 자원을 절약하는 중요한 기술입니다.</br> 커넥션 풀을 사용하면 매번 연결을 만들고 끊는 비용을 줄일 수 있고, 데이터베이스와의 연결을 효율적으로 관리하여 애플리케이션의 성능을 극대화할 수 있습니다.
   </br>

   설명은 이렇게 길지만 우리가 쓰는 목적으론 속도 향상 때문에 쓰는거다.</br>
2. 커넥션 풀을 쓰는 이유는 뭐냐?</br>
   기존 방법</br>
   ![image](https://github.com/user-attachments/assets/c5aa88bf-4f0f-4d29-9e81-11c19c1f5bbd)
   커넥션 풀 사용시</br>
   ![image](https://github.com/user-attachments/assets/bb5f8596-8298-465f-ab23-3edee5abeef2)

   위 사진과 같이 과정이 확 줄어드는걸 확인할 수 있다.
   </br>
3. 코드 비교</br>

   커넥션 풀 미사용시 코드
   ```
   <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
    <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
    <property name="url" value="jdbc:mysql://localhost:3306/your_database"/>
    <property name="username" value="root"/>
    <property name="password" value="password"/>
   </bean>
   ```
   커넥션 풀 사용시 코드
   ```
   <bean id="dataSource" class="com.zaxxer.hikari.HikariDataSource">
    <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
    <property name="jdbcUrl" value="jdbc:mysql://localhost:3306/your_database"/>
    <property name="username" value="root"/>
    <property name="password" value="password"/>
    <property name="maximumPoolSize" value="10"/>  <!-- 최대 커넥션 풀 크기 설정 -->
    <property name="minimumIdle" value="5"/>      <!-- 최소 유휴 커넥션 개수 설정 -->
    <property name="connectionTimeout" value="30000"/> <!-- 커넥션 타임아웃 설정 -->
   </bean>
   ```
   보기엔 큰 차이가 없으나 사용시는 최소, 최대 지정이 가능하다 그리고 연결된 커넥션을 자동으로 관리해서 불필요한 연결을 제외할 수 있다.

4. 마무리 정리</br>
   1대1 비교는 아래와 같다.</br>

   | 특성               | 커넥션 풀 없이 `DriverManagerDataSource` 사용 | `HikariCP` 커넥션 풀 사용 |
|------------------|--------------------------------------------|--------------------------|
| **성능**          | 낮음 (매번 새로운 연결을 생성하고 종료하므로 성능 저하 발생) | 높음 (커넥션을 풀에서 재사용하여 성능 향상) |
| **자원 관리**      | 비효율적 (매번 새로운 커넥션을 생성하고 종료) | 효율적 (커넥션 풀을 사용하여 자원을 재사용) |
| **설정의 복잡성**   | 간단 (설정이 최소화됨)                     | 복잡 (커넥션 풀 크기, 타임아웃, 유휴 커넥션 설정 등 추가 설정 필요) |
| **자원 낭비 및 누수** | 높은 자원 낭비 가능, 커넥션 누수 위험 존재    | 자원 관리가 자동으로 이루어져 누수 위험 감소 |
| **트래픽 처리**    | 높은 트래픽 처리에 적합하지 않음            | 높은 트래픽 처리에 적합 (커넥션 풀로 빠르게 커넥션 할당 가능) |
| **유연성**         | 낮음 (커넥션 수가 제한적)                   | 높음 (최대/최소 커넥션 수, 커넥션 타임아웃 등 다양한 설정 가능) |
| **에러 발생 시 처리** | 예외가 발생할 확률이 높음                  | 커넥션 풀을 통해 안정적인 연결 유지 |
| **초기화 시간**    | 커넥션을 매번 새로 열고 닫기 때문에 시간이 더 걸림 | 초기화 시 커넥션 풀을 준비하지만, 후속 요청에선 더 빠름 |
| **커넥션 관리**    | 수동으로 관리해야 하며, 커넥션 누수가 발생할 위험이 있음 | 자동으로 관리되며, 커넥션 누수 방지 기능이 있음 |
   
   
   

   

