import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.util.Random;
import java.util.Objects;



public class GUIDemo{
	public static void main(String[] args){
		APP app = new APP();
	}
}

class APP{
//	GameJFrame gameJFrame = new GameJFrame();
	LoginJFrame loginJFrame = new LoginJFrame();
//	RegisterJFrame registerJFrame = new RegisterJFrame();
}

class GameJFrame extends JFrame implements ActionListener{
	private final double scale = 1.0;
	//C:\Users\LENOVO\Pictures\Screenshots\素材\animal\animal3
	private String[] paths = {"/mnt/c/Users/LENOVO/Pictures/Screenshots/素材/animal/animal","/mnt/c/Users/LENOVO/Pictures/Screenshots/素材/girl/girl","/mnt/c/Users/LENOVO/Pictures/Screenshots/素材/sport/sport"};
	private int choice = 0;
	private int num = 1;
	private String path = paths[choice] + num +"/";

	private int step = 0;

	private int[][] data = new int[4][4];

	private int[][] win = new int[][]{
						{1,2,3,4},
						{5,6,7,8},
						{9,10,11,12},
						{13,14,15,0}
					};

	private int x = 0;
	private int y = 0;


		JMenuItem replayItem = new JMenuItem("Replay Game");
		JMenuItem reloginItem = new JMenuItem("Re Log In");
		JMenuItem closeItem = new JMenuItem("Close Game");
		JMenuItem aboutItem = new JMenuItem("Account Number");

		JMenuItem animalItem = new JMenuItem("animal");
		JMenuItem girlItem = new JMenuItem("girl");
		JMenuItem sportItem = new JMenuItem("sport");

	public GameJFrame(){
		initJFrame();

		initJMenuBar();

		initData();

		initImageIcon();

		this.setVisible(true);
	}

	private void initJFrame(){
 		this.setSize((int)(663*scale),(int)(740*scale));
		this.setTitle("Puzzle Game 1.0");
		this.setAlwaysOnTop(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e){
				int code = e.getKeyCode();
				if(code == 65){
					GameJFrame.this.getContentPane().removeAll();

					JLabel all = new JLabel(new ImageIcon(path +"all.jpg"));
					all.setBounds((int)(scale * 89),(int)(scale * 134),(int)(scale * 420),(int)(scale * 420));
					GameJFrame.this.getContentPane().add(all);

					JLabel backgroundLabel = new JLabel(new ImageIcon("/mnt/c/Users/LENOVO/Pictures/Screenshots/background.png"));
					backgroundLabel.setBounds((int)(scale * 40),(int)(scale * 40),(int)(scale * 508),(int)(scale * 560));
					GameJFrame.this.getContentPane().add(backgroundLabel);

					GameJFrame.this.getContentPane().repaint();
  				}
			}

			public void keyReleased(KeyEvent e){

				if(GameJFrame.this.compareDataAndWin()) return;

				int code = e.getKeyCode();
				System.out.println(code);
				if(code == 39){
					System.out.println("RIGHT");

					if(y == 0) return;

					data[x][y] = data[x][y-1];
					data[x][y-1] = 0;
					y--;

					step++;

					initImageIcon();
				}else if(code == 40){
					System.out.println("DOWN");

					if(x == 0) return;

					data[x][y] = data[x-1][y];
					data[x-1][y] = 0;
					x--;

					step++;

					initImageIcon();
				}else if(code == 37){
					System.out.println("LEFT");

					if(y == 3) return;

					data[x][y] = data[x][y+1];
					data[x][y+1] = 0;
					y++;

					step++;

					initImageIcon();
				}else if(code == 38){
					System.out.println("UP");

					if(x == 3) return;

					data[x][y] = data[x+1][y];
					data[x+1][y] = 0;
					x++;

					step++;

					initImageIcon();
				}else if(code == 65){
					initImageIcon();
				}else if(code == 87){
					data = new int[][]{
							{1,2,3,4},
							{5,6,7,8},
							{9,10,11,12},
							{13,14,15,0}
						};

					initImageIcon();
				}else if(code == 27){
					System.exit(0);
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

		JMenu changeImage = new JMenu("Change Image");
/*
		JMenuItem replayItem = new JMenuItem("Replay Game");
		JMenuItem reloginItem = new JMenuItem("Re Log In");
		JMenuItem closeItem = new JMenuItem("Close Game");
		JMenuItem aboutItem = new JMenuItem("Account Number")

		JMenuItem animalItem = new JMenuItem("animal");
		JMenuItem girlItem = new JMenuItem("girl");
		JMenuItem sportItem = new JMenuItem("sport");
*/

		replayItem.addActionListener(this);
		reloginItem.addActionListener(this);
		closeItem.addActionListener(this);
		aboutItem.addActionListener(this);

		animalItem.addActionListener(this);
		girlItem.addActionListener(this);
		sportItem.addActionListener(this);

		changeImage.add(animalItem);
		changeImage.add(girlItem);
		changeImage.add(sportItem);

		functionMenu.add(changeImage);
		functionMenu.add(replayItem);
		functionMenu.add(reloginItem);
		functionMenu.add(closeItem);
		aboutMenu.add(aboutItem);

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

		if(compareDataAndWin()){
			JLabel winJLabel = new JLabel(new ImageIcon("/mnt/c/Users/LENOVO/Pictures/Screenshots/素材/win.png"));
			winJLabel.setBounds((int)(scale * 203),(int)(scale * 283),(int)(scale * 197),(int)(scale * 73));
			this.getContentPane().add(winJLabel);
		}

		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				JLabel jLabel = new JLabel(new ImageIcon(path + data[i][j] +".jpg"));
				jLabel.setBounds((int)(scale * (105*j+83)),(int)(scale * (105*i+134)),(int)(scale * 105),(int)(scale * 105));
				jLabel.setBorder(new BevelBorder(0));
				this.getContentPane().add(jLabel);
			}
		}

		initStepCountJLabel();

		JLabel backgroundLabel = new JLabel(new ImageIcon("/mnt/c/Users/LENOVO/Pictures/Screenshots/background.png"));
		backgroundLabel.setBounds((int)(scale * 40),(int)(scale * 40),(int)(scale * 508),(int)(scale * 560));
		this.getContentPane().add(backgroundLabel);

		this.getContentPane().repaint();
	}

	private void initStepCountJLabel(){
		JLabel stepCountJLabel = new JLabel("Step Count :" + step);
		stepCountJLabel.setBounds((int)(scale * 50),(int)(scale * 30),(int)(scale * 120),(int)(scale * 20));
		this.getContentPane().add(stepCountJLabel);
	}

	private boolean compareDataAndWin(){
		for(int i=0;i<win.length;i++){
			for(int j=0;j<win[i].length;j++){
				if(win[i][j] != data[i][j]){
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public void actionPerformed(ActionEvent e){
		Object obj = e.getSource();

		if(obj == replayItem){
			System.out.println("重新游戏");

			step = 0;

			initData();

			initImageIcon();
		}else if(obj == reloginItem){
			System.out.println("重新登陆");

			this.setVisible(false);

			new LoginJFrame();
		}else if(obj == closeItem){
			System.out.println("关闭游戏");

			System.exit(0);
		}else if(obj == aboutItem){
			System.out.println("公众号");

			initAccount();
		}else if(obj == animalItem){
			choice = 0;

			step = 0;

			initNumAndPath();

			initData();

			initImageIcon();
		}else if(obj == girlItem){
			choice = 1;

			step = 0;

			initNumAndPath();

			initData();

			initImageIcon();
		}else if(obj == sportItem){
			choice = 2;

			step = 0;

			initNumAndPath();

			initData();

			initImageIcon();
		}
	}

	private void initAccount(){
		JDialog jDialog = new JDialog();

		jDialog.getContentPane().setLayout(null);
		jDialog.setSize((int)(scale * 344),(int)(scale * 344));
		jDialog.setAlwaysOnTop(true);
		jDialog.setLocationRelativeTo(null);
		jDialog.setModal(true);

		JLabel jLabel = new JLabel(new ImageIcon("/mnt/c/Users/LENOVO/Pictures/Screenshots/素材/about.png"));

		jLabel.setBounds((int)(scale * 0),(int)(scale * 0),(int)(scale * 258),(int)(scale * 258));

		jDialog.getContentPane().add(jLabel);

		jDialog.setVisible(true);
	}

	private void initNumAndPath(){
		Random r = new Random();

		if(choice == 0){
			num = r.nextInt(8) + 1;
		}else if(choice == 1){
			num = r.nextInt(13) + 1;
		}else if(choice == 2){
			num = r.nextInt(10) + 1;
		}

		path = paths[choice] + num + "/";
	}
}

class LoginJFrame extends JFrame implements MouseListener{
	private String username1 = "张三";
	private String password1 = "123465789";

	private String username2 = "李四";
	private String password2 = "444444444";

	private String rightCode = "";

	private JTextField usernameText = new JTextField();
	private	JPasswordField passwordText = new JPasswordField();
	private	JTextField codeText = new JTextField();
	private	JLabel code = new JLabel(getCode());
	private JButton login  = new JButton();
	private JButton register = new JButton();


	public LoginJFrame(){
		initLoginJFrame();

		initView();

		this.setVisible(true);
	}

	private void initLoginJFrame(){
		this.setSize(548,490);
		this.setTitle("Puzzle Login"); 
		this.setAlwaysOnTop(true); 
		this.setLocationRelativeTo(null); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		this.setLayout(null);
	}

	private void initView(){
		this.getContentPane().removeAll();

		JLabel usernameImage = new JLabel(new ImageIcon("/mnt/c/Users/LENOVO/Pictures/Screenshots/素材/login/用户名.png"));
		usernameImage.setBounds(60,140,80,30);
//		JTextField usernameText = new JTextField();
		usernameText.setBounds(160,140,200,30);

		JLabel passwordImage = new JLabel(new ImageIcon("/mnt/c/Users/LENOVO/Pictures/Screenshots/素材/login/密码.png"));
		passwordImage.setBounds(60,190,80,30);
//		JPasswordField passwordText = new JPasswordField();
		passwordText.setBounds(160,190,200,30);

		JLabel codeImage = new JLabel(new ImageIcon("/mnt/c/Users/LENOVO/Pictures/Screenshots/素材/login/验证码.png"));
		codeImage.setBounds(60,240,80,30);
//		JTextField codeText = new JTextField();
		codeText.setBounds(165,240,160,30);

//		JLabel code = new JLabel(getCode());
		code.setBounds(350,240,80,30);

		code.addMouseListener(this);

//		JButton login  = new JButton();
		login.setIcon(new ImageIcon("/mnt/c/Users/LENOVO/Pictures/Screenshots/素材/login/登录按钮.png"));
		login.setBounds(80,300,128,47);
		login.setBorderPainted(false);
		login.setContentAreaFilled(false);

		login.addMouseListener(this);

//		JButton register = new JButton();
		register.setBounds(288,300,128,47);
		register.setIcon(new ImageIcon("/mnt/c/Users/LENOVO/Pictures/Screenshots/素材/login/注册按钮.png"));
		register.setBorderPainted(false);
		register.setContentAreaFilled(false);

		register.addMouseListener(this);

		JLabel background = new JLabel(new ImageIcon("/mnt/c/Users/LENOVO/Pictures/Screenshots/素材/login/background.png"));
		background.setBounds(0,0,470,390);

		this.getContentPane().add(usernameImage);
		this.getContentPane().add(usernameText);
		this.getContentPane().add(passwordImage);
		this.getContentPane().add(passwordText);
		this.getContentPane().add(codeImage);
		this.getContentPane().add(codeText);
		this.getContentPane().add(code);
		this.getContentPane().add(login);
		this.getContentPane().add(register);
		this.getContentPane().add(background);

		this.getContentPane().repaint();
	}

	private void initDialog(String s){
		JDialog d = new JDialog();

		d.setSize(300,250);
		d.setAlwaysOnTop(true);
		d.setLocationRelativeTo(null);
		d.setModal(true);

		JLabel warning = new JLabel(s);
		warning.setBounds(0,0,300,250);
		d.getContentPane().add(warning);

		d.setVisible(true);

	}

	private String getCode(){
		Random r= new Random();
		String code = "";
		for(int i=0;i<4;i++){
			code += r.nextInt(10);
		}
		rightCode = code;

		return code;
	}

	public void mouseClicked(MouseEvent e){
		Object obj = e.getSource();
		if(obj == code){
			code.setText(getCode());
		}
	}

	public void mousePressed(MouseEvent e){
		Object obj = e.getSource();
		if(obj == login){
			login.setIcon(new ImageIcon("/mnt/c/Users/LENOVO/Pictures/Screenshots/素材/login/登录按下.png"));
		}else if(obj == register){
			register.setIcon(new ImageIcon("/mnt/c/Users/LENOVO/Pictures/Screenshots/素材/login/注册按下.png"));
		}
	}

	public void mouseReleased(MouseEvent e){
		Object obj = e.getSource();
		if(obj == login){
			login.setIcon(new ImageIcon("/mnt/c/Users/LENOVO/Pictures/Screenshots/素材/login/登录按钮.png"));

			if(!rightCode.equals(codeText.getText())){
				initDialog("验证码错误，请重新输入");

				codeText.setText("");
				code.setText(getCode());
			}else if(!username1.equals(usernameText.getText()) && !username2.equals(usernameText.getText())){
				initDialog("用户名错误，请重新输入");

				usernameText.setText("");
			}else if((!(password1.equals(passwordText.getText()))) && (!(password2.equals(passwordText.getText())))){
				initDialog("密码错误，请重新输入");

				passwordText.setText("");
			}else {
				this.setVisible(false);

				new GameJFrame();
			}
		}else if(obj == register){
			register.setIcon(new ImageIcon("/mnt/c/Users/LENOVO/Pictures/Screenshots/素材/login/注册按钮.png"));
		}
	}

	public void mouseEntered(MouseEvent e){

	}

	public void mouseExited(MouseEvent e){

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
