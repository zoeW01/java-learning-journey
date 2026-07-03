import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.border.*;

public class GUIDemo{
	public static void main(String[] args){
		APP app = new APP();
	}
}

class APP{
	GameJFrame gameJFrame = new GameJFrame();
//	LoginJFrame loginJFrame = new LoginJFrame();
//	RegisterJFrame registerJFrame = new RegisterJFrame();
}

class GameJFrame extends JFrame{
	private final double scale = 1.0;
	//C:\Users\LENOVO\Pictures\Screenshots\素材\animal\animal3
	private String[] path = {"/mnt/c/Users/LENOVO/Pictures/Screenshots/素材/animal/animal3/","","","",""};
	private int choice = 0;

	private int[][] data = new int[4][4];

	private int x = 0;
	private int y = 0;

	public GameJFrame(){
		initJFrame();

		initJMenuBar();

		initData();

		initImageIcon();

		this.setVisible(true);
	}

	private void initJFrame(){
		this.setSize((int)(653*scale),(int)(730*scale));
		this.setTitle("Puzzle Game 1.0");
		this.setAlwaysOnTop(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e){
				int code = e.getKeyCode();
				if(code == 65){
					GameJFrame.this.getContentPane().removeAll();

					JLabel all = new JLabel(new ImageIcon(path[choice] +"all.jpg"));
					all.setBounds((int)(scale * 89),(int)(scale * 134),(int)(scale * 420),(int)(scale * 420));
					GameJFrame.this.getContentPane().add(all);

					JLabel backgroundLabel = new JLabel(new ImageIcon("/mnt/c/Users/LENOVO/Pictures/Screenshots/background.png"));
					backgroundLabel.setBounds((int)(scale * 40),(int)(scale * 40),(int)(scale * 508),(int)(scale * 560));
					GameJFrame.this.getContentPane().add(backgroundLabel);

					GameJFrame.this.getContentPane().repaint();
				}
			}

			public void keyReleased(KeyEvent e){
				int code = e.getKeyCode();
				if(code == 39){
					System.out.println("RIGHT");

					if(y == 0) return;

					data[x][y] = data[x][y-1];
					data[x][y-1] = 0;
					y--;

					initImageIcon();
				}else if(code == 40){
					System.out.println("DOWN");

					if(x == 0) return;

					data[x][y] = data[x-1][y];
					data[x-1][y] = 0;
					x--;

					initImageIcon();
				}else if(code == 37){
					System.out.println("LEFT");

					if(y == 3) return;

					data[x][y] = data[x][y+1];
					data[x][y+1] = 0;
					y++;

					initImageIcon();
				}else if(code == 38){
					System.out.println("UP");

					if(x == 3) return;

					data[x][y] = data[x+1][y];
					data[x+1][y] = 0;
					x++;

					initImageIcon();
				}else if(code == 65){
					initImageIcon();
				}
			}

			public void keyTyped(KeyEvent e){}
		});

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
		int[] arr = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
		Random r = new Random();

		for(int i = 0;i<arr.length;i++){
			int index = r.nextInt(arr.length);

			int temp = arr[i];
			arr[i] = arr[index];
			arr[index] = temp;
		}

		for(int i=0;i<arr.length;i++){
			if(arr[i] == 0){
				x = i/4;
				y = i%4;
			}

			data[i/4][i%4] = arr[i];
		}
	}

	private void initImageIcon(){
		this.getContentPane().removeAll();

		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				JLabel jLabel = new JLabel(new ImageIcon(path[choice] + data[i][j]+".jpg"));
				jLabel.setBounds((int)(scale * (105*j+83)),(int)(scale * (105*i+134)),(int)(scale * 105),(int)(scale * 105));
				jLabel.setBorder(new BevelBorder(0));
				this.getContentPane().add(jLabel);
			}
		}

		JLabel backgroundLabel = new JLabel(new ImageIcon("/mnt/c/Users/LENOVO/Pictures/Screenshots/background.png"));
		backgroundLabel.setBounds((int)(scale * 40),(int)(scale * 40),(int)(scale * 508),(int)(scale * 560));
		this.getContentPane().add(backgroundLabel);

		this.getContentPane().repaint();
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
