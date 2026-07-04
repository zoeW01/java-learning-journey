public class Review03{
	public static void main(String[] args){
		int[] arr = {78, 92, 65, 88, 73};

		int max = arr[0];
		int min = arr[0];
		int sum = 0;
		for(int i = 0;i<arr.length;i++){
			if(arr[i] > max) max = arr[i];
			if(arr[i] < min) min = arr[i];
			sum += arr[i];
		}
		double average = (double)sum/arr.length;

		System.out.println("数组最大值为："+max+"，最小值为："+min+"，总和为："+sum+"，平均数为："+average+"。");
	}
}
