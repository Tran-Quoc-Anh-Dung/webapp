<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ page import="model.User" %>
<%@ page import="model.Student" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Bieu mau sinh vien</title>

    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">

    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Inter', sans-serif;
        }

        body {
            background: #f4f6f9;
        }

        /* HEADER */
        .header {
            background: #1e293b;
            color: white;
            padding: 15px 30px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .logo {
            font-weight: bold;
            font-size: 18px;
        }

        .user-box {
            display: flex;
            align-items: center;
            gap: 15px;
        }

        .logout {
            background: #ef4444;
            padding: 6px 12px;
            border-radius: 6px;
            color: white;
            text-decoration: none;
        }

        /* MAIN */
        .container {
            padding: 40px;
            display: flex;
            justify-content: center;
        }

        .card {
            background: white;
            padding: 30px;
            border-radius: 12px;
            width: 100%;
            max-width: 600px;
            box-shadow: 0 5px 20px rgba(0,0,0,0.1);
        }

        h2 {
            margin-bottom: 20px;
        }

        .form-group {
            margin-bottom: 15px;
        }

        label {
            display: block;
            margin-bottom: 5px;
            font-weight: 500;
        }

        input, textarea {
            width: 100%;
            padding: 10px;
            border-radius: 8px;
            border: 1px solid #ccc;
        }

        textarea {
            resize: none;
            height: 80px;
        }

        .actions {
            margin-top: 20px;
            display: flex;
            gap: 10px;
        }

        button {
            padding: 10px 15px;
            border: none;
            border-radius: 8px;
            background: #3b82f6;
            color: white;
            cursor: pointer;
        }

        .cancel {
            background: #6b7280;
            text-decoration: none;
            color: white;
            padding: 10px 15px;
            border-radius: 8px;
        }

        .alert {
            padding: 10px;
            margin-bottom: 15px;
            border-radius: 6px;
        }

        .alert-danger {
            background: #ffe5e5;
            color: #b91c1c;
        }
    </style>
</head>

<body>

<%
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");

    User currentUser = (User) session.getAttribute("user");
    if (currentUser == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    Student s = (Student) request.getAttribute("student");
    boolean isEdit = (s != null);
    String formAction = isEdit ? "update" : "insert";
    String pageTitle = isEdit ? "Cập Nhật Sinh Viên" : "Thêm Sinh Viên Mới";
    String roleLabel = "admin".equals(currentUser.getRole())
        ? "Quản trị viên" : "Sinh viên";
%>

<!-- HEADER -->
<div class="header">
    <div class="logo">DHV Student</div>
    <div class="user-box">
        <div>
            <div><%= currentUser.getFullname() %></div>
            <small><%= roleLabel %></small>
        </div>
        <a href="${pageContext.request.contextPath}/logout" class="logout">Đăng xuất</a>
    </div>
</div>

<!-- MAIN -->
<div class="container">
    <div class="card">

        <h2><%= pageTitle %></h2>

        <%
            String error = (String) request.getAttribute("error");
            if (error != null) {
        %>
            <div class="alert alert-danger"><%= error %></div>
        <%
            }
        %>

        <form action="${pageContext.request.contextPath}/student?action=<%= formAction %>" method="post">

            <% if (isEdit) { %>
                <input type="hidden" name="id" value="<%= s.getId() %>">
            <% } %>

            <div class="form-group">
                <label>MSSV</label>
                <input type="text" name="studentCode"
                       value="<%= isEdit ? s.getStudentCode() : "" %>" required>
            </div>

            <div class="form-group">
                <label>Họ tên</label>
                <input type="text" name="fullname"
                       value="<%= isEdit ? s.getFullname() : "" %>" required>
            </div>

            <div class="form-group">
                <label>Lớp</label>
                <input type="text" name="className"
                       value="<%= isEdit ? s.getClassName() : "" %>" required>
            </div>

            <div class="form-group">
                <label>Email</label>
                <input type="email" name="email"
                       value="<%= isEdit ? s.getEmail() : "" %>" required>
            </div>

            <div class="form-group">
                <label>Ghi chú</label>
                <textarea name="note"><%= isEdit ? s.getNote() : "" %></textarea>
            </div>

            <div class="actions">
                <button type="submit">Lưu</button>
                <a href="${pageContext.request.contextPath}/student?action=list" class="cancel">Hủy</a>
            </div>

        </form>

    </div>
</div>

</body>
</html>