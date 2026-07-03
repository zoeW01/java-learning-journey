import java.util.Scanner;

public class MathDemo{
	private static Scanner sc = new Scanner(System.in);
	public static void main(String[] args){
		for(int i=0;i<10;i++){
			System.out.println(MathUtil.randomInt(10,20));
		}
	}

	//判断一个数是否为质数
	public static boolean isPrime(int number){
		if(number <= 1) return false;
		for(int i=2;i<=Math.sqrt(number);i++){
			if(number % i == 0){
				return false;
			}
		}
		return true;
	}
	public static boolean isNarcissistic(int number){
		if(number <= 0) return false;

		long sum = 0;
		int digits = 0;
		int temp = number;

		while(temp != 0){
			temp/=10;
			digits ++;
		}

		temp = number;
		while(temp != 0){
			sum += Math.round(Math.pow(temp%10,digits));
			temp /= 10;
		}
		return sum == number;
	}
}


class MathUtil{
	public static int randomInt(int min,int max){
		return (int)(Math.random() * (max - min + 1) + min);
	}
}
