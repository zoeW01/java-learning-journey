import java.time.LocalDate;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.util.Scanner;

public class Main{
	private static Scanner sc = new Scanner(System.in);
	List<Student> students = new ArrayList<>();
	List<Payment> payments = new ArrayList<>();
	Student student = null;
	Payment payment = null;

	public static void main(String[] args){
		System.out.println("程序启动成功");
		char choice;
		while(true){
			System.out.println("---琴行操作系统---");
			System.out.println("1.添加学生");
			System.out.println("2.查询所有学生");
			System.out.println("3.根据ID查询学生");
			System.out.println("4.删除学生");
			System.out.println("5.添加支付记录");
			System.out.println("6.根据ID查询支付记录");
			System.out.println("7.退出系统");
			System.out.println("---琴行操作系统---");
			System.out.print("请输入你的选择：");

			choice = sc.nextLine().charAt(0);

			if(choice == 48){
				System.out.println("正在执行添加学生操作~");
				addStudent();
			}else if(choice == 49){
				System.out.println("正在执行查询所有学生操作~");
				getAllStudents();
			}else if(choice == 50){
				System.out.println("正在执行根据ID查询学生操作~");
				getStudentByInstrument();
			}else if(choice == 51){
				System.out.println("正在执行删除学生操作~");
				deleteByStudentId();
			}else if(choice == 52){
				System.out.println("正在执行添加支付记录操作~");
				addPayment()
			}else if(choice == 53){
				System.out.println("正在执行根据ID查询支付记录操作~");
				getPaymentsByStudentId();
			}else if(choice == 54){
				System.out.println("正在执行退出操作~");
				exit();
				return;
			}else{
				System.out.println("输入有误，请重新输入~");
			}
		}
	}

	public static void addStudent(){
		System.out.println("请依次输入学生姓名，年龄，联系方式，乐器");
		student = new Student(sc.next(),sc.nextInt(),sc.next(),LocalDate.now(),sc.next());

		int id = 0;

		try{
			id = StudentDao.addStudent(student);
		}catch(SQLException e){
			e.printStackTrace();
		}

		student.setId(id);
	}

	public static void getAllStudents(){
		try{
			students = StudentDao.getAllStudents();

			for(Student s : students){
				System.out.println(s);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	public static void getStudentByInstrument(){
		try{
			System.out.print("请输入要查询的乐器：");
			students = StudentDao.getStudentByInstrument(sc.next());

			for(Student s : students){
				System.out.println(s);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	public static void deleteByStudentId(){
		System.out.print("请输入要删除学员的ID：");
		int id = sc.nextInt();

		try{
			StudentDao.deleteByStudentId(id);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	public static void addPayment(){
		System.out.print("请输入缴费学员ID：");
		int id = sc.nextInt();
		sc.nextLine();
		System.out.println();
		System.out.print("请输入缴费金额:");
		String amount = sc.nextLine();

		payment = new Payment(id,new BigDecimal(amount),LocalDate.now());
		boolean b = false;

		try{
			b = PaymentDao.addPayment(payment);
		}catch(SQLException e){
			e.printStackTrace();
		}

		if(b){System.out.println("支付记录添加成功");}
	}

	public static void getPaymentsByStudentId(){
		try{
			System.out.print("请输入要查询的学员ID：");
			int id = sc.nextInt();
			payments = PaymentDao.getPaymentsByStudentId(id);
			for(Payment p : payments){
				System.out.println(p);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	public static void exit(){
		System.out.println("再见！");
	}
}
