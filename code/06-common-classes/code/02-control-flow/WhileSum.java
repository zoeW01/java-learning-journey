public class WhileSum{
	public static void main(String[] args){
		int i = 1;
		int sum1 = 0;
		while(i <= 100){
			sum1 += i;
			i ++;
		}
		System.out.println(sum1);

		int j = 1;
		int sum2 = 0;
		do{
		sum2 += j;
		j ++;
		}while(j <= 100);
		System.out.println(sum2);
	}
}
