import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalDemo{
	public static void main(String[] args){
		BigDecimal bd1 = new BigDecimal(0.1);
		BigDecimal bd2 = new BigDecimal("0.1");
		BigDecimal bd3 = BigDecimal.valueOf(0.1);
		System.out.println(bd1 +"\n"+ bd2 +"\n"+ bd3);

		BigDecimal db4 = new BigDecimal("13.99");
		BigDecimal db5 = new BigDecimal("0.01");
		BigDecimal db6 = db4.add(db5);
		System.out.println(db6);

		BigDecimal db7 = new BigDecimal("10");
		BigDecimal db8 = new BigDecimal("3");
		BigDecimal db9 = db7.divide(db8,2,RoundingMode.HALF_UP);
		System.out.println(db9);

		BigDecimal db10 = new BigDecimal("2.0");
		BigDecimal db11 = new BigDecimal("2.00");
		boolean result1 = db10.equals(db11);
		int result2 = db10.compareTo(db11);
		System.out.println(result1 + "-:-" + result2);

		BigDecimal db12 = new BigDecimal("49.99");
		BigDecimal db13 = new BigDecimal("5");
		BigDecimal db14 = new BigDecimal("0.065");
		BigDecimal db15 = db12.multiply(db13);
		BigDecimal db16 = db15.multiply(db14).setScale(2,RoundingMode.HALF_UP);
		BigDecimal db17 = db15.add(db16);
		System.out.println(db15);
		System.out.println(db16);
		System.out.println(db17);

		BigDecimal db18 = new BigDecimal("22");
		BigDecimal db19 = new BigDecimal("7");
		BigDecimal db20 = db18.divide(db19,3,RoundingMode.HALF_UP);
		System.out.println(db20);
		BigDecimal db21 = db18.divide(db19,3,RoundingMode.HALF_DOWN);
		System.out.println(db21);
		BigDecimal db22 = db18.divide(db19,3,RoundingMode.CEILING);
		System.out.println(db22);
		BigDecimal db23 = db18.divide(db19,3,RoundingMode.FLOOR);
		System.out.println(db23);

		BigDecimal db24 = new BigDecimal("5.0");
		BigDecimal db25 = new BigDecimal("5.00");
		System.out.println(db24.equals(db25));
		System.out.println(db24.compareTo(db25) == 0 ? true : false);

		BigDecimal db26 = new BigDecimal("100.5000");
	}
}
