import java.math.BigInteger;

public class BigIntegerDemo{
	public static void main(String[] args){
		BigInteger bi1 = BigInteger.valueOf(12);
		BigInteger bi2 = BigInteger.valueOf(12);
		System.out.println(bi1 == bi2);

		BigInteger bi3 = new BigInteger("99999999999999999999");
		BigInteger bi4 = new BigInteger("1");
		BigInteger bi5 = bi3.add(bi4);
		System.out.println(bi5);

		int result = bi3.compareTo(bi4);
		System.out.println(result > 0 ? "bi3>bi4" : (result == 0 ? "bi3 == bi4" : "bi3<bi4"));

		BigInteger bi6 = new BigInteger("12345678901234567890");
		BigInteger bi7 = bi6.pow(2);
		BigInteger bi8 = new BigInteger("98765432109876543210");
		BigInteger bi9 = bi7.add(bi8);
		BigInteger[] bi10 = bi9.divideAndRemainder(new BigInteger("100"));
		System.out.println(bi7 + "\n" + bi9 + "\n" + bi10[0] + "\n" + bi10[1]);
	}
}



