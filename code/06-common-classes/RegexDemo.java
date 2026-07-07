import java.util.Scanner;
import java.util.regex.*;

public class RegexDemo{
	private static Scanner sc = new Scanner(System.in);
	public static void main(String[] args){
//		while(true){
			System.out.println("输入文本");
			extractEmails(sc.nextLine());
/*			System.out.println("输入手机号校验"); 
			System.out.println(phone(sc.next())); 
			System.out.println("输入邮政编码校验"); 
			System.out.println(zipCode(sc.next())); 
			System.out.println("输入身份证号校验");
			System.out.println(id(sc.next()));
			System.out.println("输入邮箱校验");
			System.out.println(email(sc.next()));
			System.out.println("输入日期格式校验");
			System.out.println(date(sc.next()));
*/
//		}
	}

	public static void extractEmails(String text){
		Pattern p = Pattern.compile("(\\w+)@([0-9a-zA-Z]+)\\.([a-zA-Z]{2,6})");
		Matcher m = p.matcher(text);

		while(m.find()){
			System.out.println("用户名："+m.group(1)+"域名："+m.group(2)+"顶级域："+m.group(3));
		}
	}

	public static boolean phone(String phone){
	//以 1 开头
	//第二位为 3、4、5、6、7、8、9 之一
	//后跟 9 位数字
	//总共 11 位
		return phone.matches("[1][3-9]\\d{9}");
	}

	public static boolean zipCode(String zipCode){
		return zipCode.matches("[1-9]\\d{5}");
	}

	public static boolean id(String id){
		return id.matches("\\d{17}[\\dxX]");
	}

	public static boolean id(String id,boolean b){
		if(b) return id(id);
		return id.matches("[1-9]\\d{5}(20|1[89])\\d{2}(0[1-9]|1[0-2])(0[1-9]|[1-2]\\d|3[01])\\d{3}(\\d|(?i)x)");
	}

	public static boolean email(String email){
		return email.matches("(\\w+)@([0-9a-zA-Z]+)\\.([a-zA-Z]{2,6})");
	}

	public static boolean date(String date){
		return date.matches("\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])");
	}

	public static boolean userName(String username){
		return username.matches("\\w{4,16}");
	}
}
