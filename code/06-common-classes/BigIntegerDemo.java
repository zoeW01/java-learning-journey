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
	}
}



