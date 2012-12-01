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

public class Breakout extends GraphicsProgram {

/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;
	
/** Y coordination of the paddle */	
	private static final int Y_PADDLE = HEIGHT - PADDLE_HEIGHT - PADDLE_Y_OFFSET;

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;
	
/** Separation between bricks */
	private static final int BRICK_SEP = 4;

/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

/** Number of turns */
	private static final int NTURNS = 3;

/* Method: run() */
/** Runs the Breakout program. */
	public void run() {
		setup();
		play();
	}

	private void setup() {
		setUpBricks();
		setUpPaddle();
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
			brick.setFillColor(color);
			add(brick, x, y);
			x += BRICK_SEP + BRICK_WIDTH;
		}
	}
	
	private void setUpPaddle() {
		paddle = new GRect (PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		xPaddle = (WIDTH - PADDLE_WIDTH) / 2;
		add (paddle, xPaddle, yPaddle);
		addMouseListeners();
	}
	
	private void play() {
		movePaddle();
		label = new GLabel("");
		add (label, 50, 50);
	}

	public void mouseMoved (MouseEvent e) {
		xPaddle = e.getX();
		label.setLabel("Mouse: (" + e.getX() + ", " + e.getY() + ")");
	}
	
	
	private void movePaddle() {
		double dx = paddle.getX() - xPaddle;
		paddle.move(dx, 0);
	}

/* Private instance variables */
	private GRect paddle;
	private int xPaddle;
	private int yPaddle = Y_PADDLE;
	private GLabel label;
	
}
