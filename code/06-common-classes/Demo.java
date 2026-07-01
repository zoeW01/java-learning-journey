import java.util.Objects;
import java.util.HashSet;
import java.io.IOException;

public class Demo{
	public static void main(String[] args){
		Person p1 = new Person("zhangsan",18);
		Person p2 = new Person("lisi",22);
		Person p3 = new Person("lisi",22);
		HashSet<Person> hs = new HashSet<>();
		hs.add(p1);
		hs.add(p2);
		hs.add(p3);
		for(Person p : hs){
			System.out.println(p);
		}

		System.out.println("====================");

		printLength("hello");
		printLength("123");
		printLength(null);

		System.out.println("====================");

		SystemInfo.printInfo();
		SystemInfo.arrCopy();

		System.out.println("====================");

		try{
			RuntimeInfo.printInfo();
		}catch(IOException e){
			e.printStackTrace();
		}

		System.out.println("====================");

		System.out.println(MathUtil.generateRandomCode(5));
	}

	public static void printLength(String s){
		String str = Objects.toString(s,"长度为零");
		System.out.println("长度：" + str.length());
	}
}


class MathUtil{
	public static int generateRandomCode(int length){
		double min = Math.pow(10,length - 1);
		double max = Math.pow(10,length) - 1;
		double temp = max - min + 1;
		return (int)Math.round(Math.random() * temp + min);
	}
}

class RuntimeInfo{
	public static void printInfo() throws IOException{
		System.out.println(Runtime.getRuntime().maxMemory() / 1024 / 1024);
		System.out.println(Runtime.getRuntime().totalMemory() /1024 / 1024);
		System.out.println(Runtime.getRuntime().freeMemory() /1024 / 1024);
		System.out.println(Runtime.getRuntime().availableProcessors());
		System.out.println(Runtime.getRuntime().version());

		System.out.println(Runtime.getRuntime().exec("ls"));
	}
}
class SystemInfo{
	public static void printInfo(){
		System.out.println(System.getProperty("os.name"));
		System.out.println(System.getProperty("os.arch"));
		System.out.println(System.getProperty("java.version"));
		System.out.println(System.getProperty("java.home"));
		System.out.println(System.getProperty("user.dir"));
		System.out.println(System.getProperty("user.name"));
		System.out.println(System.getenv("JAVA_HOME"));
	}

	public static void arrCopy(){
		long start = System.currentTimeMillis();
		int[] arr = new int[999999];
		int[] newArr = new int[arr.length];
		System.arraycopy(arr,0,newArr,0,arr.length);
		long end = System.currentTimeMillis();

		long time1 = end - start;

		start = System.currentTimeMillis();
		for(int i =0;i<arr.length;i++){
			newArr[i] = arr[i];
		}
		end = System.currentTimeMillis();

		long time2 = end - start;

		System.out.println("第一次耗时："+time1+"，第二次耗时："+time2+".");
	}
}
class Person{
	private String name;
	private int age;

	public Person(String name,int age){
		this.name = name;
		this.age = age;
	}

	public String getName(){return name;}
	public int getAge(){return age;}

	@Override
	public boolean equals(Object obj){
		if(obj == this) return true;
		if(obj == null || this.getClass() != obj.getClass()) return false;

		Person p = (Person)obj;
		return Objects.equals(p.name,name) && p.age == age;
	}

	@Override
	public int hashCode(){
		return Objects.hash(name,age);
	}

	@Override
	public String toString(){
		return "Person{name="+name+",age="+age+"}";
	}
}
