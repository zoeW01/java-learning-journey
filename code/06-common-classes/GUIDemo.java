import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GUIDemo{
	public static void main(String[] args){
		APP app = new APP();
	}
}

class APP{
	GameJFrame gameJFrame = new GameJFrame();
	LoginJFrame loginJFrame = new LoginJFrame();
	RegisterJFrame registerJFrame = new RegisterJFrame();
}

class GameJFrame extends JFrame{
	private int[][] data = new int[4][4];

	public GameJFrame(){
		initJFrame();

		initJMenuBar();

		initData();

		initImageIcon();

		this.setVisible(true);
	}

	private void initJFrame(){
		this.setSize(603,680);
		this.setTitle("Puzzle Game 1.0");
		this.setAlwaysOnTop(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
	}

	private void initJMenuBar(){
		JMenuBar jmb = new JMenuBar();

		JMenu functionMenu = new JMenu("Function");
		JMenu aboutMenu = new JMenu("About Us");

		JMenuItem replayItem = new JMenuItem("Replay Game");
		JMenuItem reloginItem = new JMenuItem("Re Log In");
		JMenuItem closeItem = new JMenuItem("Close Game");
		JMenuItem accountItem = new JMenuItem("Account Number");

		functionMenu.add(replayItem);
		functionMenu.add(reloginItem);
		functionMenu.add(closeItem);
		aboutMenu.add(accountItem);

		jmb.add(functionMenu);
		jmb.add(aboutMenu);

		this.setJMenuBar(jmb);
	}

	private void initData(){
		int[] arr = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
		Random r = new Random();

		for(int i = 0;i<arr.length;i++){
			int index = r.nextInt(arr.length);

			int temp = arr[i];
			arr[i] = arr[index];
			arr[index] = temp;
		}

		for(int i=0;i<arr.length;i++){
			data[i/4][i%4] = arr[i];
		}
	}

	private void initImageIcon(){
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				JLabel jLabel = new JLabel(new ImageIcon("/mnt/c/Users/LENOVO/Pictures/Screenshots/"+data[i][j]+".png"));
				jLabel.setBounds(105*j,105*i,105,105);
				this.getContentPane().add(jLabel);
			}
		}
	}
}

class LoginJFrame extends JFrame{
	public LoginJFrame(){
		this.setSize(488,430);
		this.setTitle("Puzzle Login");
		this.setAlwaysOnTop(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}

class RegisterJFrame extends JFrame{
	public RegisterJFrame(){
		this.setSize(488,500);
		this.setTitle("Puzzle Register");
		this.setAlwaysOnTop(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}
