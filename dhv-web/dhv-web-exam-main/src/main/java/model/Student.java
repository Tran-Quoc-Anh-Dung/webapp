package model;

public class Student {
    private int id;
    private String studentCode;
    private String fullname;
    private String className;
    private String email;
    private String note;

    public Student() {
    }

    public Student(int id, String studentCode, String fullname, String className, String email, String note) {
        this.id = id;
        this.studentCode = studentCode;
        this.fullname = fullname;
        this.className = className;
        this.email = email;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
