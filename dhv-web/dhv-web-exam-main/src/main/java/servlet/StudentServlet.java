package servlet;

import dao.StudentDAO;
import model.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/student")
public class StudentServlet extends HttpServlet {
    private final StudentDAO studentDAO = new StudentDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteStudent(request, response);
                    break;
                case "list":
                default:
                    listStudents(request, response);
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException("Loi doGet StudentServlet", ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "insert":
                    insertStudent(request, response);
                    break;
                case "update":
                    updateStudent(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/student?action=list");
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException("Loi doPost StudentServlet", ex);
        }
    }

    private void listStudents(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Student> list = studentDAO.getAllStudents();
        request.setAttribute("studentList", list);
        request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/student-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null) {
            try {
                int id = Integer.parseInt(idStr);
                Student existing = studentDAO.getStudentById(id);
                request.setAttribute("student", existing);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "ID sinh viên không hợp lệ!");
            }
        }
        request.getRequestDispatcher("/student-form.jsp").forward(request, response);
    }

    private void insertStudent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String studentCode = request.getParameter("studentCode");
        String fullname = request.getParameter("fullname");
        String className = request.getParameter("className");
        String email = request.getParameter("email");
        String note = request.getParameter("note");
        if (note == null) {
            note = "";
        }

        if (studentCode == null || studentCode.trim().isEmpty() ||
            fullname == null || fullname.trim().isEmpty() ||
            className == null || className.trim().isEmpty() ||
            email == null || email.trim().isEmpty()) {

            request.setAttribute("error", "Tất cả các trường (trừ ghi chú) đều bắt buộc!");
            request.getRequestDispatcher("/student-form.jsp").forward(request, response);
            return;
        }

        if (!email.contains("@")) {
            request.setAttribute("error", "Email không hợp lệ!");
            request.getRequestDispatcher("/student-form.jsp").forward(request, response);
            return;
        }

        if (studentDAO.checkStudentCodeExists(studentCode.trim(), 0)) {
            request.setAttribute("error", "Mã sinh viên này đã tồn tại!");
            request.getRequestDispatcher("/student-form.jsp").forward(request, response);
            return;
        }

        Student newStudent = new Student(0, studentCode.trim(), fullname.trim(),
                className.trim(), email.trim(), note.trim());
        if (studentDAO.addStudent(newStudent)) {
            response.sendRedirect(request.getContextPath() + "/student?action=list");
        } else {
            request.setAttribute("error", "Lỗi CSDL khi thêm sinh viên!");
            request.getRequestDispatcher("/student-form.jsp").forward(request, response);
        }
    }

    private void updateStudent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String studentCode = request.getParameter("studentCode");
        String fullname = request.getParameter("fullname");
        String className = request.getParameter("className");
        String email = request.getParameter("email");
        String note = request.getParameter("note");
        if (note == null) {
            note = "";
        }

        Student student = new Student(id, studentCode, fullname, className, email, note);

        if (studentCode == null || studentCode.trim().isEmpty() ||
            fullname == null || fullname.trim().isEmpty() ||
            className == null || className.trim().isEmpty() ||
            email == null || email.trim().isEmpty()) {

            request.setAttribute("error", "Tất cả các trường (trừ ghi chú) đều bắt buộc!");
            request.setAttribute("student", student);
            request.getRequestDispatcher("/student-form.jsp").forward(request, response);
            return;
        }

        if (!email.contains("@")) {
            request.setAttribute("error", "Email không hợp lệ!");
            request.setAttribute("student", student);
            request.getRequestDispatcher("/student-form.jsp").forward(request, response);
            return;
        }

        if (studentDAO.checkStudentCodeExists(studentCode.trim(), id)) {
            request.setAttribute("error", "Mã sinh viên đã bị trùng!");
            request.setAttribute("student", student);
            request.getRequestDispatcher("/student-form.jsp").forward(request, response);
            return;
        }

        student.setStudentCode(studentCode.trim());
        student.setFullname(fullname.trim());
        student.setClassName(className.trim());
        student.setEmail(email.trim());
        student.setNote(note.trim());

        if (studentDAO.updateStudent(student)) {
            response.sendRedirect(request.getContextPath() + "/student?action=list");
        } else {
            request.setAttribute("error", "Lỗi CSDL khi cập nhật sinh viên!");
            request.setAttribute("student", student);
            request.getRequestDispatcher("/student-form.jsp").forward(request, response);
        }
    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String idStr = request.getParameter("id");
        if (idStr != null) {
            try {
                int id = Integer.parseInt(idStr);
                studentDAO.deleteStudent(id);
            } catch (NumberFormatException e) {
                // ignore
            }
        }
        response.sendRedirect(request.getContextPath() + "/student?action=list");
    }
}