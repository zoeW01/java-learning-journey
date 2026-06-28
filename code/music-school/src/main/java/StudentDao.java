import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

public class StudentDao{
	public static int addStudent(Student s) throws SQLException{
		String sql = "INSERT INTO student (name,age,phone,enrollment_date,instrument) VALUES (?,?,?,?,?);";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int id = 0;

		try{
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);

			pstmt.setString(1,s.getName());
			pstmt.setInt(2,s.getAge());
			pstmt.setString(3,s.getPhone());
			pstmt.setObject(4,s.getEnrollmentDate());
			pstmt.setString(5,s.getInstrument());

			pstmt.executeUpdate();

			rs = pstmt.getGeneratedKeys();
			if(rs.next()){id = rs.getInt(1);}

		}catch(SQLException e){
			throw new SQLException("添加学员失败，未获取到ID",e);
		}finally{
			DBUtil.close(conn,pstmt,rs);
		}
		return id;
	}

	public static int deleteByStudentId(int studentId) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		ResultSet rs = null;

		String sql1 = "SELECT * FROM payment WHERE student_id = ?;";
		String sql2 = "DELETE FROM payment WHERE student_id = ?;";
		String sql3 = "DELETE FROM student WHERE id = ?;";
		int count = 0;

		try{
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			pstmt1 = conn.prepareStatement(sql1);
			pstmt1.setInt(1,studentId);
			rs = pstmt1.executeQuery();

			if(rs.next()){
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setInt(1,studentId);
				count = pstmt2.executeUpdate();
			}

			pstmt3 = conn.prepareStatement(sql3);
			pstmt3.setInt(1,studentId);
			count = pstmt3.executeUpdate();
			conn.commit();

		}catch(SQLException e){
			if(conn != null){
				try{
					conn.rollback();
				}catch(SQLException ex){
					ex.printStackTrace();
				}
			}

			throw new SQLException("删除学员失败",e);
		}finally{
			DBUtil.close(conn,rs,pstmt1,pstmt2,pstmt3);
		}

		return count;
	}

	public static List<Student> getAllStudents() throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Student> students = new ArrayList<>();
		Student s = null;

		String sql = "SELECT * FROM student;";
		try{
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while(rs.next()){
				s = buildStudent(rs);
				students.add(s);
			}
		}catch(SQLException e){
			throw new SQLException("查询所有学员失败", e);
		}finally{
			DBUtil.close(conn,pstmt,rs);
		}
		return students;
	}

	public static List<Student> getStudentByInstrument(String instrument) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Student> students = new ArrayList<>();
		Student s = null;
		String sql = "SELECT * FROM student WHERE instrument = ?;";

		try{
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1,instrument);

			rs = pstmt.executeQuery();

			while(rs.next()){
				s = buildStudent(rs);
				students.add(s);
			}
		}catch(SQLException e){
			throw new SQLException("查询学员失败" ,e);
		}finally{
			DBUtil.close(conn,pstmt,rs);
		}
		return students;
	}

	private static Student buildStudent(ResultSet rs) throws SQLException{
		Student s = new Student(rs.getString("name"),rs.getInt("age"),rs.getString("phone"),rs.getObject("enrollment_date",LocalDate.class),rs.getString("instrument"));
		s.setId(rs.getInt("id"));
		return s;
	}
}
