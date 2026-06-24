import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JdbcDemo {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/java_learning";
        String user = "root";
        String password = "123456";
        
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("数据库连接成功！");
            
            stmt = conn.createStatement();
            
            // 1. 查询初始数据
            rs = stmt.executeQuery("SELECT * FROM student");
            System.out.println("--- 初始数据 ---");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " | " + rs.getString("name") + " | " + rs.getInt("age") + " | " + rs.getDouble("score"));
            }
            
            // 2. 新增一条数据
            String insertSql = "INSERT INTO student (name, age, score) VALUES ('Alice', 20, 88.0)";
            int rowsInserted = stmt.executeUpdate(insertSql);
            System.out.println("新增了 " + rowsInserted + " 行");
            
            // 3. 修改数据
            String updateSql = "UPDATE student SET score = 95.0 WHERE name = 'Zoe'";
            int rowsUpdated = stmt.executeUpdate(updateSql);
            System.out.println("修改了 " + rowsUpdated + " 行");
            
            // 4. 删除数据
            String deleteSql = "DELETE FROM student WHERE name = 'Tom'";
            int rowsDeleted = stmt.executeUpdate(deleteSql);
            System.out.println("删除了 " + rowsDeleted + " 行");
            
            // 5. 再次查询所有数据验证变更
            rs2 = stmt.executeQuery("SELECT * FROM student");
            System.out.println("--- 变更后数据 ---");
            while (rs2.next()) {
                System.out.println(rs2.getInt("id") + " | " + rs2.getString("name") + " | " + rs2.getInt("age") + " | " + rs2.getDouble("score"));
            }
            
        } catch (Exception e) {
            System.out.println("数据库操作失败: " + e.getMessage());
        } finally {
            // 在 finally 中统一关闭资源，无论是否异常都会执行
            try {
                if (rs != null) rs.close();
                if (rs2 != null) rs2.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                System.out.println("关闭资源失败: " + e.getMessage());
            }
        }
    }
}
