package snakeProject;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayDeque;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Snake extends JPanel {
	
	private final int WIDTH = 50;
	private ArrayDeque<SnakePart> snake = new ArrayDeque<>();
	private Point apple = new Point(0,0);
	private Random rand = new Random();
	private int Offset = 0;
	private int newDir = 39;
	private boolean isGrowing = false;
	private boolean gameover = false;
	

	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Snake"); // creation de fenetre
		Snake panel = new Snake();
		frame.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
			panel.onKeyPressed(e.getKeyCode());
				
			}
		});
	
		frame.setContentPane(panel); // permet de mettre les contenus
		frame.setSize(13*50, 13*50); // dimensions fenetre 
		frame.setResizable(false); // fixe les dimensions de la fenetre
		frame.setLocationRelativeTo(null); // pour centre la fenetre 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // fermeture fenetre = fermeture prog
		frame.setVisible(true); // pour afficher la fenetre 

	}

	
	public Snake(){
		createApple();
		snake.add(new SnakePart(0, 0, 39));
		//setBackground(Color.WHITE);
		new Thread(new Runnable() {

			@Override
			public void run() {
				
				while(true) {
					repaint();
					try {
						Thread.sleep(1000/60);
					} catch (InterruptedException e) {
						
						e.printStackTrace();
					}
				}//boucle infinie (excution en permanance 60/sec)
				
			}
			
		}).start(); // multi-threading (multi-execution des taches)
}
	
	public void createApple() {
		boolean posAvailable;
		do{
			apple.x = rand.nextInt(12);
			apple.y = rand.nextInt(12);
			posAvailable = true;
			for(SnakePart p : snake) {
				if(p.x == apple.x && p.y == apple.y) {
					posAvailable = false;
					break;
				}
			}
		}while(!posAvailable);
	}
	@Override
	protected void paintComponent(Graphics g) {
		if(gameover) {
			g.setColor(Color.RED);
			g.setFont(new Font("Arial", 100, 100));
			g.drawString("Gameover", 13*50/2-g.getFontMetrics().stringWidth("Gameover")/2, 13*50/2);
			return;
		}
		
		Offset +=5;
		SnakePart head = null;
		
		if(Offset == WIDTH) { 
			Offset =0;
			
			try {
				head = (SnakePart) snake.getFirst().clone();
				head.move();
				head.dir = newDir;
				snake.addFirst(head);
				if(head.x == apple.x && head.y == apple.y) {
					isGrowing = true;
					createApple();
				}
				
				if(!isGrowing) {
					snake.pollLast();
				}else { 
					isGrowing = false;} 
				
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		
		super.paintComponent(g);
		
		g.setColor(Color.red);
		g.fillOval(apple.x*WIDTH + WIDTH/4, apple.y*WIDTH + WIDTH/4, WIDTH/2, WIDTH/2);
		g.setColor(Color.DARK_GRAY);
		
		for(SnakePart p : snake) {
			
			if(Offset == 0) {
				if(p != head) {
					if(p.x == head.x && p.y == head.y) {
						gameover = true;
					}
				}
			}
			
			if(p.dir == 37 || p.dir == 39) { 
				
				g.fillRect(p.x*WIDTH+((p.dir==37)? -Offset:Offset), p.y*WIDTH, WIDTH, WIDTH);
				
				}else{
					g.fillRect(p.x*WIDTH, p.y*WIDTH+((p.dir==38)? -Offset:Offset), WIDTH, WIDTH);
			}
			
		}
		
		g.setColor(Color.BLUE);
		g.drawString("Score :"+ (snake.size()-1), 10, 20);
	}

	public void onKeyPressed(int keyCode){
		if(keyCode >= 37 && keyCode <= 40) { 
			if(Math.abs(keyCode - newDir) != 2) {
				newDir = keyCode;
			}
			
		}
	}
	
	class SnakePart{
		public int x, y, dir;
		
		public SnakePart(int x, int y, int dir) {
			this.x = x;
			this.y = y;
			this.dir = dir;
		}
		
		public void move() {
			if(dir == 37 || dir == 39) {
				x+= (dir == 37)? -1 : 1;
				
				if(x > 13) 
					x= -1;
				else if(x < -1)
					x=13;
			}else {
				y+= (dir == 38)? -1 : 1;
				
				if(y > 13) 
					y= -1;
				else if(y < -1)
					y=13;
			}
		}
		@Override
		protected Object clone() throws CloneNotSupportedException {
			
			return new SnakePart(x, y, dir);
		}
	}
	
}

