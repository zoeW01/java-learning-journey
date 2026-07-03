import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PaymentDao{
	public static boolean addPayment(Payment payment) throws SQLException{
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean b = false;
		String sql = "INSERT INTO payment (student_id,amount,payment_date) VALUES (?,?,?);";

		try{
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1,payment.getStudentId());
			pstmt.setObject(2,payment.getAmount());
			pstmt.setObject(3,payment.getPaymentDate());

			if(pstmt.executeUpdate() > 0){b = true;}
		}catch(SQLException e){
			throw new SQLException("添加支付记录失败",e);
		}finally{
			DBUtil.close(conn,pstmt,null);
		}
		return b;
	}

	public static List<Payment> getPaymentsByStudentId(int studentId) throws SQLException{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Payment> payments = new ArrayList<>();
		Payment payment = null;
		String sql = "SELECT * FROM payment WHERE student_id = ?;";

		try{
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1,studentId);

			rs = pstmt.executeQuery();

			while(rs.next()){
				payment = buildPayment(rs);
				payments.add(payment);
			}
		}catch(SQLException e){
			throw new SQLException("查询支付信息失败",e);
		}finally{
			DBUtil.close(conn,pstmt,rs);
		}

		return payments;
	}

	private static Payment buildPayment(ResultSet rs) throws SQLException{
		Payment payment = new Payment(rs.getInt("student_id"),rs.getObject("amount",BigDecimal.class),rs.getObject("payment_date",LocalDate.class));
		payment.setId(rs.getInt("id"));
		payment.setCreateAt(rs.getObject("created_at",LocalDateTime.class));
		return payment;
	}
}
