import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil{
        private static final String URL = "jdbc:mysql://localhost:3306/java_learning";
        private static final String USER = "root";
        private static final String PASSWORD = "123456";

        private DBUtil(){}

        public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL,USER,PASSWORD);
	}

	public static void close(Connection conn,PreparedStatement pstmt,ResultSet rs){
		close(conn,rs,pstmt);
	}

	public static void close(Connection conn,ResultSet rs,PreparedStatement ... pstmt){
		if(rs != null){
			try{
				rs.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}

		for(PreparedStatement p : pstmt){
			if(p != null){
				try{
					p.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
		}

		if(conn != null){
			try{
				conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
}
