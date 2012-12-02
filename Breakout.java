/*
 * File: Breakout.java
 * -------------------
 * Name:Pedro
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.JButton;

public class Breakout extends GraphicsProgram {

/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600 + 40;

/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 70;
	
/** Y coordination of the paddle */	
	private static final int Y_PADDLE = HEIGHT - PADDLE_HEIGHT - PADDLE_Y_OFFSET;

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;
	
/** Total number of bricks */
	private static final int TOTAL_BRICKS = NBRICKS_PER_ROW * NBRICK_ROWS;
	
/** Separation between bricks */
	private static final int BRICK_SEP = 4;

/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW + 1) * BRICK_SEP) / NBRICKS_PER_ROW;

/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 7;

/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

/** Number of turns */
	private static final int NTURNS = 3;
	
/** Size of the final Board */
	private static final int BOARD_WIDTH = 200;
	private static final int BOARD_HEIGHT = BOARD_WIDTH / 2;	
	
/** Animation cycle delay */
	private static final int DELAY = 7;	

/* Method: run() */
/** Runs the Breakout program. */
	public void run() {
		setup();
		play();
		showScore();
	}

	private void setup() {
		setUpBricks();
		setUpPaddle();
	}
	
	public void init() {
		add(new JButton ("New Ball"), SOUTH);
		addMouseListeners();
		addActionListeners();
	}
	
	private void setUpBricks() {
		int y = BRICK_Y_OFFSET;
		for (int i=0; i<NBRICK_ROWS; i++) {
			Color rowColor = Color.WHITE;
			switch(i) {
			case 0 : rowColor = Color.RED; break;
			case 1 : rowColor = Color.RED; break;
			case 2 : rowColor = Color.ORANGE; break;
			case 3 : rowColor = Color.ORANGE; break;
			case 4 : rowColor = Color.YELLOW; break;
			case 5 : rowColor = Color.YELLOW; break;
			case 6 : rowColor = Color.GREEN; break;
			case 7 : rowColor = Color.GREEN; break;
			case 8 : rowColor = Color.CYAN; break;
			case 9 : rowColor = Color.CYAN; break;
			}
			setBrickRow (y, rowColor);
			y += BRICK_SEP + BRICK_HEIGHT;
		}
	}
	
	private void setBrickRow (int y, Color color) {
		int x = (WIDTH - (NBRICKS_PER_ROW * BRICK_WIDTH + (NBRICKS_PER_ROW - 1) * BRICK_SEP)) / 2;
		for (int i=0; i <NBRICKS_PER_ROW; i++) {
			GRect brick = new GRect (x, y, BRICK_WIDTH, BRICK_HEIGHT);
			brick.setFilled(true);
			brick.setColor(color);
			add(brick, x, y);
			x += BRICK_SEP + BRICK_WIDTH;
		}
	}
	
	private void setUpPaddle() {
		paddle = new GRect (PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		xPaddle = (WIDTH - PADDLE_WIDTH) / 2;
		add (paddle, xPaddle, yPaddle);
	}
	
	public void mouseMoved (MouseEvent e) {
		if (e.getX() >= PADDLE_WIDTH / 2 && e.getX() <= WIDTH - PADDLE_WIDTH / 2) {
			xPaddle = e.getX() - PADDLE_WIDTH / 2;
			double dx = xPaddle - paddle.getX();
			paddle.move(dx, 0);
		} else if (e.getX() < PADDLE_WIDTH / 2) {
			xPaddle = 0;
			double dx = xPaddle - paddle.getX();
			paddle.move(dx, 0);
		} else {
			xPaddle = WIDTH - PADDLE_WIDTH;
			double dx = xPaddle - paddle.getX();
			paddle.move(dx, 0);
		}
	}
	
	public void actionPerformed (ActionEvent e) {
		if (ball == null && turn < NTURNS) {
			ball = new GOval (2*BALL_RADIUS, 2*BALL_RADIUS);
			ball.setFilled(true);
			add (ball, getWidth() / 2 - BALL_RADIUS, getHeight() / 2 - BALL_RADIUS);
			ball.sendToBack();
			vx = rgen.nextDouble(1.0, 3.0);
			if (rgen.nextBoolean(0.5)) vx = -vx;
			vy = 3;
		}
	}
	
	private void play() {
		while (!gameOver()) {
			moveBall();
			checkForCollision();
			pause(DELAY);
		}
		
	}

	private boolean gameOver() {
		return (win) || (loose);
	}
	
	private void moveBall() {
		if (ball!=null) {
			ball.move(vx, vy);
			if (ball.getX() <=0) vx=-vx;
			if (ball.getX() >= WIDTH - 2*BALL_RADIUS) vx = -vx;
			if (ball.getY() <=0) vy=-vy;
			if (ball.getY() >= HEIGHT - 2*BALL_RADIUS) loose = true;
			ball1 = ball.getLocation();
			ball2 = new GPoint(ball.getX()+ 2 * BALL_RADIUS, ball.getY());
			ball3 = new GPoint(ball.getX(), ball.getY() + 2 * BALL_RADIUS);
			ball4 = new GPoint(ball.getX(), ball.getY() + 2 * BALL_RADIUS);
		}
	}
	
	private void checkForCollision() {
		if (ball != null) {
			if( paddle.contains(ball1) || 
					paddle.contains(ball2) || 
						paddle.contains(ball3) ||
							paddle.contains(ball4)) {
				vy = -vy;
			}
			if (getElementAt(ball1) != ball && getElementAt(ball1) != null && getElementAt(ball1) != paddle) { 
				destroyBrick(getElementAt(ball1));
			} else if (getElementAt(ball2) != ball && getElementAt(ball1) != null && getElementAt(ball1) != paddle) {
				destroyBrick (getElementAt(ball2));
			} else if (getElementAt(ball3) != ball && getElementAt(ball1) != null && getElementAt(ball1) != paddle) {
				destroyBrick (getElementAt(ball3));
			} else if (getElementAt(ball4) != ball && getElementAt(ball1) != null && getElementAt(ball1) != paddle) {
				destroyBrick (getElementAt(ball4));
			}
		}
		if (points == 100) win = true;
	}
	
	private void destroyBrick(GObject obj) {
		remove(obj);
		vy = -vy;
		points++;
	}
	
	private void showScore() {
		
		if (win) {
			showBoard("Congratulations. You won!");
			boardColor = Color.GREEN;
		}else if (loose) {
			showBoard("Sorry, you lost. Try again.");
			boardColor = Color.RED;
		}
	}
		
	private void showBoard(String str) {
		int x = (getWidth() - BOARD_WIDTH) / 2;
		int y = (getHeight() - BOARD_HEIGHT) / 2;
		GRect board = new GRect(x,y,BOARD_WIDTH, BOARD_HEIGHT);
		board.setFilled(true);
		board.setFillColor(boardColor);
		GLabel label = new GLabel (str);
		double xLabel = (getWidth() - label.getWidth()) / 2;
		double yLabel = (getHeight() + label.getAscent()) / 2;
		label.setFont("Helvetica, Font.BOLD, 18");
		add (board);
		add (label, xLabel, yLabel);
	}


/* Private instance variables */
	private GRect paddle;
	private int xPaddle;
	private int yPaddle = Y_PADDLE;
	private int points = 0; //number of bricks destroyed
	private int turn;
	private GOval ball;
	private boolean win; //true when all the bricks are destroyed
	private boolean loose; //true when the player looses the 3 lives
	private double vx, vy;
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private GPoint ball1;
	private GPoint ball2;
	private GPoint ball3;
	private GPoint ball4;
	private Color boardColor;
}
