import java.time.LocalDate;
import java.sql.SQLException;
import java.util.List;

public class Main{
	public static void main(String[] args){
		System.out.println("程序启动成功");
		Student s = new Student("张三",13,"13333333333",LocalDate.now(),"钢琴");
		StudentDao sd = new StudentDao();

		int count = 0;
		try{
			count = sd.addStudent(s);
		}catch(SQLException e){
			e.printStackTrace();
		}

		if(count > 0){System.out.println("学员添加成功");}

		List<Student> students = null;

		try{
			students = sd.getAllStudents();

			for(Student student : students){
				System.out.println(student);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}

		try{
			students = sd.getStudentByInstrument("钢琴");

			for(Student student : students){
				System.out.println(student);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
}
