public class SystemDemo{
	public static void main(String[] args){
		System.err.println(System.getProperty("user.name")); 
		System.out.println(System.getProperty("os.name")); 
		System.out.println(System.getProperty("os.arch"));
		System.out.println(System.getProperty("java.version"));
		System.out.println(System.getProperty("java.home"));
		System.out.println(System.getProperty("file.separator"));

		int[] arr = {10,20,30,40,50};
		int[] newArr = new int[arr.length];
		System.arraycopy(arr,1,newArr,0,3);
		for(int i : newArr){
			System.out.println(i);
		}

		long start = System.currentTimeMillis();

		for(int i=0;i<1000000;i++){
			int j =0;
		}
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}
}
