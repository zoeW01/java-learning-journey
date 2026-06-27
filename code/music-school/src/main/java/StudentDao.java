import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

public class StudentDao{
	public int addStudent(Student s) throws SQLException{
		String sql = "INSERT INTO student (name,age,phone,enrollment_date,instrument) VALUES (?,?,?,?,?);";
		Connection conn = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try{
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1,s.getName());
			pstmt.setInt(2,s.getAge());
			pstmt.setString(3,s.getPhone());
			pstmt.setObject(4,s.getEnrollmentDate());

			pstmt.setString(5,s.getInstrument());
			count = pstmt.executeUpdate();
		}catch(SQLException e){
			throw new SQLException("添加学员失败",e);
		}finally{
			if(pstmt != null){
				try{
					pstmt.close();
				}catch(SQLException e){
					e.printStackTrace();
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
		return count;
	}

	public List<Student> getAllStudents() throws SQLException {
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
			if(rs != null){
				try{
					rs.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}

			if(pstmt != null){
				try{
					pstmt.close();
				}catch(SQLException e){
					e.printStackTrace();
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
		return students;
	}

	public List<Student> getStudentByInstrument(String instrument) throws SQLException {
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
			if(rs != null){
				try{
					rs.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}

			if(pstmt != null){
				try{
					pstmt.close();
				}catch(SQLException e){
					e.printStackTrace();
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
		return students;
	}

	private Student buildStudent(ResultSet rs) throws SQLException{
		Student s = new Student(rs.getString("name"),rs.getInt("age"),rs.getString("phone"),rs.getObject("enrollment_date",LocalDate.class),rs.getString("instrument"));
		s.setId(rs.getInt("id"));
		return s;
	}
}
