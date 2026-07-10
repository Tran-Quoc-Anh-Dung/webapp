# Hệ thống Quản lý Sinh viên

Java Servlet/JSP + MySQL — CRUD sinh viên, đăng nhập, mật khẩu băm SHA-256.

## Yêu cầu máy

- JDK 17+
- Maven
- MySQL 8.x (root, mật khẩu mặc định rỗng — nếu có mật khẩu, sửa `src/main/java/context/DBContext.java`)

## Cách chạy (clone → dùng)

```bash
# 1. Clone
git clone https://github.com/maitamdev/dhv-web-exam.git
cd dhv-web-course

# 2. Tạo CSDL (MySQL Workbench / phpMyAdmin / terminal)
#    Chạy toàn bộ file schema.sql

# 3. Chạy app
mvn clean tomcat7:run
```

Mở trình duyệt: **http://localhost:8080/**

### Tài khoản mẫu (sau khi chạy schema.sql)
Hoặc bạn có thể tự tạo theo ý thích 

Hoặc đăng ký mới: http://localhost:8080/register.jsp

## Chức năng

- Đăng nhập / đăng ký (mật khẩu băm SHA-256)
- CRUD sinh viên: MSSV, họ tên, lớp, email, ghi chú

## Cấu trúc

```
schema.sql          → tạo DB appdb + bảng + user admin
pom.xml
src/main/java/
  context/          → kết nối MySQL
  dao/              → UserDAO, StudentDAO
  model/            → User, Student
  servlet/          → Login, Register, Student, Logout, AuthFilter
  util/             → HashUtil
src/main/webapp/    → JSP + CSS
```
