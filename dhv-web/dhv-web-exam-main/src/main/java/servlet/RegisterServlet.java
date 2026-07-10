package servlet;

import dao.UserDAO;
import model.User;
import util.HashUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/register.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmpassword");
        String fullname = request.getParameter("fullname");


        if (username == null || username.trim().isEmpty() ||
            password == null || password.isEmpty() ||
            fullname == null || fullname.trim().isEmpty()) {
            
            request.setAttribute("error", "Vui lòng điền đầy đủ tất cả các trường dữ liệu!");
            keepFormData(request, username, fullname);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }


        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Mật khẩu xác nhận không khớp!");
            keepFormData(request, username, fullname);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }


        if (userDAO.checkUsernameExists(username.trim())) {
            request.setAttribute("error", "Tên tài khoản này đã được sử dụng!");
            keepFormData(request, username, fullname);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }


        String hashedPass = HashUtil.hashSHA256(password);
        User user = new User(0, username.trim(), hashedPass, fullname.trim(), "student");

        if (userDAO.register(user)) {
            request.setAttribute("success", "Đăng ký thành công! Hãy đăng nhập bằng tài khoản mới.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Hệ thống bận. Đăng ký không thành công!");
            keepFormData(request, username, fullname);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }

    private void keepFormData(HttpServletRequest request, String username, String fullname) {
        request.setAttribute("username", username);
        request.setAttribute("fullname", fullname);
    }
}
