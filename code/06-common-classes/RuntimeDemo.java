import java.io.IOException;

public class RuntimeDemo{
	public static void main(String[] args) throws IOException{
		Runtime r = Runtime.getRuntime();
		long total = r.totalMemory();
		long free = r.freeMemory();
		System.out.println((total - free) /1024/1024);

	}
}
