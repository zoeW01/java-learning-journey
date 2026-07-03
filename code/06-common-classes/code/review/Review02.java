public class  Review02{
	public static void main(String[] args){
		for(int i = 1; i <= 100; i ++){
			if(i % 3 == 0 && i % 5 != 0){
				System.out.println(i);
			}
		}
		
		int j = 1;
		int sum = 0;
		while(j <= 50){
			sum += j;
			j ++;
		}
		System.out.println(sum);
	}
}
