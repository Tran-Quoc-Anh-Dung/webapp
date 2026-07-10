<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ page import="model.User" %>
<%@ page import="model.Student" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>QLSV</title>

    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">

    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Inter', sans-serif;
        }

        body {
            background: #f1f5f9;
        }

        /* HEADER */
        .header {
            background: #0f172a;
            color: white;
            padding: 15px 30px;
            display: flex;
            justify-content: space-between;
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
            padding: 30px;
        }

        .top-bar {
            display: flex;
            justify-content: space-between;
            margin-bottom: 20px;
        }

        .btn {
            padding: 8px 14px;
            border-radius: 6px;
            text-decoration: none;
            color: white;
        }

        .btn-primary { background: #3b82f6; }
        .btn-edit { background: #10b981; }
        .btn-delete { background: #ef4444; }

        table {
            width: 100%;
            border-collapse: collapse;
            background: white;
            border-radius: 10px;
            overflow: hidden;
        }

        th, td {
            padding: 12px;
            text-align: left;
        }

        th {
            background: #1e293b;
            color: white;
        }

        tr:nth-child(even) {
            background: #f8fafc;
        }

        tr:hover {
            background: #e2e8f0;
        }

        .empty {
            text-align: center;
            padding: 40px;
            background: white;
            border-radius: 10px;
        }
    </style>
</head>

<body>

<%
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");

    User currentUser = (User) session.getAttribute("user");
    if (currentUser == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    String roleLabel = "admin".equals(currentUser.getRole())
        ? "Quản trị viên" : "Sinh viên";
%>

<!-- HEADER -->
<div class="header">
    <div>
        <strong>DHV Student</strong>
        (<%= currentUser.getFullname() %> - <%= roleLabel %>)
    </div>
    <a href="${pageContext.request.contextPath}/logout" class="logout">Đăng xuất</a>
</div>

<!-- MAIN -->
<div class="container">

    <div class="top-bar">
        <h2>Danh sách Sinh viên</h2>
        <a href="${pageContext.request.contextPath}/student?action=new" class="btn btn-primary">
            + Thêm Sinh viên
        </a>
    </div>

    <%
        List<Student> students = (List<Student>) request.getAttribute("studentList");
        if (students != null && !students.isEmpty()) {
    %>

    <table>
        <tr>
            <th>MSSV</th>
            <th>Họ tên</th>
            <th>Lớp</th>
            <th>Email</th>
            <th>Ghi chú</th>
            <th>Hành động</th>
        </tr>

        <%
            for (Student s : students) {
        %>
        <tr>
            <td><%= s.getStudentCode() %></td>
            <td><%= s.getFullname() %></td>
            <td><%= s.getClassName() %></td>
            <td><%= s.getEmail() %></td>
            <td><%= s.getNote() != null ? s.getNote() : "" %></td>
            <td>
                <a href="${pageContext.request.contextPath}/student?action=edit&id=<%= s.getId() %>"
                   class="btn btn-edit">Sửa</a>

                <a href="${pageContext.request.contextPath}/student?action=delete&id=<%= s.getId() %>"
                   class="btn btn-delete"
                   onclick="return confirm('Xóa sinh viên này?');">
                   Xóa
                </a>
            </td>
        </tr>
        <%
            }
        %>
    </table>

    <%
        } else {
    %>

    <div class="empty">
        <h3>Chưa có sinh viên nào</h3>
        <p>Hãy thêm sinh viên để bắt đầu</p>
        <br>
        <a href="${pageContext.request.contextPath}/student?action=new" class="btn btn-primary">
            Thêm sinh viên
        </a>
    </div>

    <%
        }
    %>

</div>

</body>
</html>