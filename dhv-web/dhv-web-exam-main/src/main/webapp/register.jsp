<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng ký | Quản lý sinh viên</title>

    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">

    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Inter', sans-serif;
        }

        body {
            height: 100vh;
            background: linear-gradient(135deg, #667eea, #764ba2);
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .card {
            background: white;
            padding: 35px;
            width: 100%;
            max-width: 420px;
            border-radius: 12px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.2);
        }

        h2 {
            text-align: center;
            margin-bottom: 5px;
        }

        p {
            text-align: center;
            color: gray;
            margin-bottom: 20px;
        }

        .form-group {
            margin-bottom: 15px;
        }

        input {
            width: 100%;
            padding: 10px;
            border-radius: 8px;
            border: 1px solid #ccc;
        }

        input:focus {
            border-color: #667eea;
            outline: none;
        }

        button {
            width: 100%;
            padding: 10px;
            border: none;
            background: #667eea;
            color: white;
            border-radius: 8px;
            font-weight: bold;
            cursor: pointer;
        }

        button:hover {
            background: #5a67d8;
        }

        .alert {
            padding: 10px;
            border-radius: 6px;
            margin-bottom: 15px;
        }

        .alert-danger {
            background: #ffe5e5;
            color: #c53030;
        }

        .footer {
            margin-top: 15px;
            text-align: center;
            font-size: 14px;
        }

        .footer a {
            color: #667eea;
            text-decoration: none;
        }
    </style>
</head>

<body>

<div class="card">

    <h2>Đăng Ký</h2>
    <p>Tạo tài khoản mới cho sinh viên</p>

    <%
        String error = (String) request.getAttribute("error");
        String username = (String) request.getAttribute("username");
        String fullname = (String) request.getAttribute("fullname");
        if (username == null) username = "";
        if (fullname == null) fullname = "";

        if (error != null) {
    %>
        <div class="alert alert-danger">
            <%= error %>
        </div>
    <%
        }
    %>

    <form id="registerForm" action="${pageContext.request.contextPath}/register" method="post">

        <div class="form-group">
            <input type="text" name="fullname" placeholder="Họ và tên"
                   value="<%= fullname %>" required>
        </div>

        <div class="form-group">
            <input type="text" name="username" placeholder="Tên đăng nhập"
                   value="<%= username %>" required>
        </div>

        <div class="form-group">
            <input type="password" id="password" name="password" placeholder="Mật khẩu" required>
        </div>

        <div class="form-group">
            <input type="password" id="confirmpassword" name="confirmpassword" placeholder="Xác nhận mật khẩu" required>
        </div>

        <button type="submit">Đăng Ký</button>

    </form>

    <div class="footer">
        Đã có tài khoản?
        <a href="${pageContext.request.contextPath}/login.jsp">Đăng nhập</a>
    </div>

</div>

<script>
    const form = document.getElementById('registerForm');
    const pass = document.getElementById('password');
    const confirmPass = document.getElementById('confirmpassword');

    form.addEventListener('submit', function (e) {
        if (pass.value !== confirmPass.value) {
            e.preventDefault();
            alert('Mật khẩu xác nhận không khớp!');
        }
    });
</script>

</body>
</html>