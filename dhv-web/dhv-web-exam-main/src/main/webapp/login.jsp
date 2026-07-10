<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng nhập | Quản lý sinh viên</title>

    <!-- Google Font -->
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">

    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Poppins', sans-serif;
        }

        body {
            height: 100vh;
            display: flex;
            background: linear-gradient(135deg, #4facfe, #00f2fe);
        }

        .container {
            display: flex;
            width: 100%;
        }

        /* LEFT SIDE */
        .left {
            flex: 1;
            color: white;
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
            padding: 40px;
        }

        .left h1 {
            font-size: 40px;
            margin-bottom: 10px;
        }

        .left p {
            opacity: 0.9;
        }

        /* RIGHT SIDE */
        .right {
            flex: 1;
            background: white;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .login-box {
            width: 80%;
            max-width: 400px;
        }

        .login-box h2 {
            margin-bottom: 10px;
        }

        .login-box p {
            margin-bottom: 20px;
            color: gray;
        }

        .form-group {
            margin-bottom: 15px;
        }

        input {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 8px;
        }

        button {
            width: 100%;
            padding: 10px;
            background: #4facfe;
            border: none;
            color: white;
            border-radius: 8px;
            cursor: pointer;
            font-weight: bold;
        }

        button:hover {
            background: #00c6ff;
        }

        .alert {
            padding: 10px;
            margin-bottom: 15px;
            border-radius: 6px;
        }

        .alert-danger {
            background: #ffdddd;
            color: #d8000c;
        }

        .alert-success {
            background: #ddffdd;
            color: #4f8a10;
        }

        .footer {
            margin-top: 15px;
            font-size: 14px;
        }

        .footer a {
            color: #4facfe;
            text-decoration: none;
        }
    </style>
</head>

<body>

<div class="container">

    <!-- LEFT -->
    <div class="left">
        <h1>Welcome 👋</h1>
        <p>Hệ thống quản lý sinh viên</p>
    </div>

    <!-- RIGHT -->
    <div class="right">
        <div class="login-box">

            <h2>Đăng Nhập</h2>
            <p>Vui lòng nhập thông tin của bạn</p>

            <%
                String error = (String) request.getAttribute("error");
                String success = (String) request.getAttribute("success");
                String username = (String) request.getAttribute("username");
                if (username == null) username = "";

                if (error != null) {
            %>
                <div class="alert alert-danger">
                    <%= error %>
                </div>
            <%
                }
                if (success != null) {
            %>
                <div class="alert alert-success">
                    <%= success %>
                </div>
            <%
                }
            %>

            <form action="${pageContext.request.contextPath}/login" method="post">
                <div class="form-group">
                    <input type="text" name="username" placeholder="Tên đăng nhập"
                           value="<%= username %>" required>
                </div>

                <div class="form-group">
                    <input type="password" name="password" placeholder="Mật khẩu" required>
                </div>

                <button type="submit">Đăng Nhập</button>
            </form>

            <div class="footer">
                Chưa có tài khoản?
                <a href="${pageContext.request.contextPath}/register.jsp">Đăng ký</a>
            </div>

        </div>
    </div>

</div>

</body>
</html>