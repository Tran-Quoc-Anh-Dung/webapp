package context;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBContext {

    private final String SERVER = "127.0.0.1";
    private final String PORT = "3307";
    private final String DATABASE = "appdb";
    private final String USER = "root";
    private final String PASSWORD = "";

    public Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://" + SERVER + ":" + PORT + "/" + DATABASE
                    + "?useSSL=false"
                    + "&serverTimezone=Asia/Ho_Chi_Minh"
                    + "&allowPublicKeyRetrieval=true"
                    + "&useUnicode=true"
                    + "&characterEncoding=utf8"
                    + "&connectionCollation=utf8mb4_unicode_ci";

            conn = DriverManager.getConnection(url, USER, PASSWORD);
            if (conn != null) {
                try (var st = conn.createStatement()) {
                    st.execute("SET NAMES utf8mb4 COLLATE utf8mb4_unicode_ci");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("[LỖI] Kết nối CSDL thất bại: " + e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }

    public static void main(String[] args) {
        DBContext db = new DBContext();
        if (db.getConnection() != null) {
            System.out.println("Kết nối MySQL thành công!");
        } else {
            System.out.println("Kết nối MySQL thất bại!");
        }
    }
}
