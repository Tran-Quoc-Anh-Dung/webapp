package dao;

import context.DBContext;
import model.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT id, student_code, fullname, class_name, email, note FROM students ORDER BY id DESC";

        try (
            Connection conn = new DBContext().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                list.add(new Student(
                    rs.getInt("id"),
                    rs.getString("student_code"),
                    rs.getString("fullname"),
                    rs.getString("class_name"),
                    rs.getString("email"),
                    rs.getString("note")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Student getStudentById(int id) {
        String sql = "SELECT id, student_code, fullname, class_name, email, note FROM students WHERE id = ?";
        try (
            Connection conn = new DBContext().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Student(
                        rs.getInt("id"),
                        rs.getString("student_code"),
                        rs.getString("fullname"),
                        rs.getString("class_name"),
                        rs.getString("email"),
                        rs.getString("note")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addStudent(Student student) {
        String sql = "INSERT INTO students (student_code, fullname, class_name, email, note) VALUES (?, ?, ?, ?, ?)";
        try (
            Connection conn = new DBContext().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, student.getStudentCode());
            ps.setString(2, student.getFullname());
            ps.setString(3, student.getClassName());
            ps.setString(4, student.getEmail());
            ps.setString(5, student.getNote());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateStudent(Student student) {
        String sql = "UPDATE students SET student_code = ?, fullname = ?, class_name = ?, email = ?, note = ? WHERE id = ?";
        try (
            Connection conn = new DBContext().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, student.getStudentCode());
            ps.setString(2, student.getFullname());
            ps.setString(3, student.getClassName());
            ps.setString(4, student.getEmail());
            ps.setString(5, student.getNote());
            ps.setInt(6, student.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE id = ?";
        try (
            Connection conn = new DBContext().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkStudentCodeExists(String studentCode, int excludeId) {
        String sql = "SELECT 1 FROM students WHERE student_code = ? AND id != ?";
        try (
            Connection conn = new DBContext().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, studentCode);
            ps.setInt(2, excludeId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
