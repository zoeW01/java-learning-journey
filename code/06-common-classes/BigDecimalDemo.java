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
	}
}
