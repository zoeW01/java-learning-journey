import java.io.IOException;

public class RuntimeDemo{
	public static void main(String[] args) throws IOException{
		Runtime.getRuntime().exec("sudo bash -c shutdown -h now");
	}
}
