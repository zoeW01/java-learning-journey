public class Review06{
	public static void main(String[] args){
		try{
			System.out.println(divide(10,2));
			System.out.println(divide(10,0));
		}catch(ArithmeticException e){
			System.out.println("程序出错，"+ e.getMessage());
		}finally{
			System.out.println("Finally 块执行了");
		}
	}

	public static int divide(int a,int b) throws ArithmeticException {
		if(b == 0){throw new ArithmeticException("除数不能为零");}
		return a/b;
	}
}
