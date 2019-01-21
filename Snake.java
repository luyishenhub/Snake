//javaswing, Jframe and toolkit

package snake;

import java.awt.Dimension; // what is java awt?
import java.awt.Point;
import java.awt.Toolkit; // what is toolkit indeed?
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Snake implements ActionListener,KeyListener {
	public JFrame jframe;
	public RenderPanel renderpanel;
	public Timer timer= new Timer(20,this); // the timer and actionevent actionlistener actionperformed stuff ?
	
	public static Snake snake; // static so it can be used by other variables and methods; instance means variables which are static
	
	public ArrayList<Point> snakeParts = new ArrayList<Point>();
	public Point head, cherry;
	public static final int UP=0,DOWN=1,LEFT=2,RIGHT=3, SCALE =10;
	public int ticks = 0, direction = DOWN, score, tailLength =10;
	
	public Random random;
	
	public Dimension dim;
	
	public Boolean over = false, paused = false;
	
	public Snake() {
		dim = Toolkit.getDefaultToolkit().getScreenSize();//get the size of my monitor
		jframe = new JFrame("Snake");
		jframe.setVisible(true);
		jframe.setSize(800,690);
		jframe.setResizable(false);//so the user cannot resize the frame
		jframe.setLocation(dim.width/2 - jframe.getWidth()/2, dim.height/2 - jframe.getHeight()/2); // set the left top locatio of the window
		jframe.add(renderpanel = new RenderPanel()); // add(component); initially renderpanel=null, so need to make a new one
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit the program when the window is closed
		jframe.addKeyListener(this); // add this keylistener instance to the snake
		startGame();
		
		
	}
	
	public void startGame() {
		over = false;
		direction = DOWN;
		score =0;
		tailLength = 3;
		ticks=0;
		
		head = new Point (0,-1);
		random = new Random();
		
		snakeParts.clear(); //resize the snake to the beginning after game restarts
		
		cherry = new Point (random.nextInt(65),random.nextInt(79));
		
		//set the initial size of the snake
		for(int i =0; i<tailLength;i++) {
			snakeParts.add(new Point (head.x,head.y)); // grow more from the top?
		}
		timer.start(); // snake class is when you just make the object, so its like the beginning
	}
	
	public static void main (String [] args) {
		snake = new Snake(); // since snake is static, so i can use it here
	}

	@Override
	public void actionPerformed(ActionEvent arg0) { // updates every frame?
		renderpanel.repaint(); // to call the method continuously, like an update() //repaint= javaswing stuff? 
		ticks++; //?
		//ticks% n sets the speed
		if(ticks%2==0 && head != null && over==false && !paused) { //in every 10 ticks
			//add an element in the direction, and then remove the last index after that to reach the effect that the sanek is moving forward
			snakeParts.add(new Point (head.x, head.y)); //so its not a pointer, head.x is a int value, wont change as head changes//can i just change it by change the pointer?
			if(direction == UP) {
				if(head.y-1 >= 0)
					head = new Point (new Point (head.x,head.y-1)); //point (Coordinate): //why going up y-1 ?
				else
					over=true; // once the snake go outside of the screen, then stop the snake moving action
			}
			if(direction == DOWN) {
				if(head.y+1 <=66) //dim.height/SCALE?
					head = new Point(new Point (head.x,head.y+1));
				else
					over=true;
			}
			if(direction == LEFT) {
				if(head.x-1 >= 0)
					head = new Point(new Point (head.x-1,head.y));
				else
					over=true;
			}
			if(direction == RIGHT) {
				if(head.x+1 < 80)
					head = new Point(new Point (head.x+1,head.y));
				else
					over=true;
			}
			if(snakeParts.size()>tailLength)
				snakeParts.remove(0); // once the length is more than it should be, delete the first element
			//snakeParts.remove(0); //remove the last element
			if(cherry!=null) {
				if(head.equals(cherry)) {//determines if the x and y values of the two points are equal
					score+=10;
					tailLength++;
					cherry.setLocation(random.nextInt(60),random.nextInt(75)); //make value 1 smaller cuz the cherry might be on the edge so we wont see it
				}
				
			}
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) { //update every frame?
		int i = e.getKeyCode();//get keyevent e 's code, the code will transfer to actual letter on the keyboard
		if(i==KeyEvent.VK_A && direction != RIGHT)//VK_A is a constant for the A key
			direction = LEFT;
		if(i==KeyEvent.VK_D && direction != LEFT)
			direction = RIGHT;
		if(i==KeyEvent.VK_W && direction != DOWN)
			direction = UP;
		if(i==KeyEvent.VK_S && direction != UP)
			direction = DOWN;
		if(i==KeyEvent.VK_SPACE) {
			if (over)
				startGame();
			else
				//at the frame, the program wont go to actionperform method,but the values stayed the same; so once the game goes to that method again, it continues from where it stops
				paused = !paused; //paused = its opposite value
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}



//more details about ending the game when the snake hits itself
//video:
//https://www.youtube.com/watch?v=S_n3lryyGZM
// time: 1:12:56
	
