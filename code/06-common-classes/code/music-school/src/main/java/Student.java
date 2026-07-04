import java.time.LocalDate;
public class Student{
	private int id;
	private String name;
	private int age;
	private String phone;
	private LocalDate enrollmentDate;
	private String instrument;

	public Student(){}

	public Student(String name,int age,String phone,LocalDate enrollmentDate,String instrument){
		this.name = name;
		this.age = age;
		this.phone = phone;
		this.enrollmentDate = enrollmentDate;
		this.instrument = instrument;
	}

	public int getId(){return id;}
	public void setId(int id){this.id = id;}
	public String getName(){return name;}
	public int getAge(){return age;}
	public String getPhone(){return phone;}
	public LocalDate getEnrollmentDate(){return enrollmentDate;}
	public String getInstrument(){return instrument;}

	@Override
	public String toString(){
		return "Student{id = "+id+",name = "+name+",age = "+age+",phone = "+phone+",enrollmentDate = "+enrollmentDate+",instrument = "+instrument+".}";
	}
}
