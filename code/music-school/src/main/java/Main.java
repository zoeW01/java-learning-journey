import java.time.LocalDate;
import java.sql.SQLException;
import java.util.List;
import java.math.BigDecimal;
import java.util.Scanner;

public class Main{
	public static void main(String[] args){
		System.out.println("程序启动成功");
		Scanner sc = new Scanner(System.in);
		System.out.println("请依次输入学生姓名，年龄，联系方式，乐器");
		Student s = new Student(sc.next(),sc.nextInt(),sc.next(),LocalDate.now(),sc.next());

		int id = 0;
		try{
			id = StudentDao.addStudent(s);
		}catch(SQLException e){
			e.printStackTrace();
		}

		s.setId(id);


		List<Student> students = null;

		try{
			students = StudentDao.getAllStudents();

			for(Student student : students){
				System.out.println(student);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}

		try{
			System.out.print("请输入要查询的乐器：");
			students = StudentDao.getStudentByInstrument(sc.next());

			for(Student student : students){
				System.out.println(student);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}

		System.out.print("请输入缴费金额:");
		Payment payment = new Payment(s.getId(),new BigDecimal(sc.next()),LocalDate.now());
		boolean b = false;
		try{
			b = PaymentDao.addPayment(payment);
		}catch(SQLException e){
			e.printStackTrace();
		}
		if(b){System.out.println("支付记录添加成功");}


		List<Payment> payments = null;
		try{
			payments = PaymentDao.getPaymentsByStudentId(id);
			for(Payment p : payments){
				System.out.println(p);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
}
