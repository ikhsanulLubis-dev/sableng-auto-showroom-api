CREATE DATABASE sableng_auto_db;

//aplication.properties pada java
spring.application.name=showroomapi

spring.datasource.url=jdbc:postgresql://localhost:5432/sableng_auto_db
spring.datasource.username=postgres
spring.datasource.password=PASSWORD_KAMU

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true