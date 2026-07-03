import java.util.Objects;

public class ObjectsDemo{
	public static void main(String[] args){
		Student s = new Student("zoe",123);
		s.printName(null);
	}
}

class Student{
	private String name;
	private Integer score;

	public Student(String name,Integer score){
		this.name = Objects.requireNonNull(name);
		this.score = Objects.requireNonNull(score);
	}

	public void printName(Student s){
		System.out.println(Objects.toString(s,"未记录"));
	}
}
