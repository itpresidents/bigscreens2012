/**
 * Simple Template for the Big Screens Class, Fall 2012
 * <https://github.com/shiffman/Most-Pixels-Ever>
 * 
 * Note this project uses Processing 2.0b3
 */

package controller;

import mpe.client.*;
import processing.core.*;

public class Messenger extends PApplet {

	// --------------------------------------
	AsyncClient client;
	PFont font;

	boolean message = false;

	int bg = -1;
	int piece = 0;
	
	// --------------------------------------
	public void setup() {
		// set up the client
		// For testing locally
		client = new AsyncClient("localhost", 9003);

		// At NYU
		// client = new AsyncClient("128.122.151.64",9003);

		// At IAC
		// client = new AsyncClient("192.168.130.241",9003);

		size(255, 255);

		smooth();
		frameRate(20);
		font = createFont("Arial", 18);
	}

	// --------------------------------------
	public void draw() {
		background(0);

		bg = constrain(bg, -1, 1);
		piece = constrain(piece, 0, 10);

		String msg = bg + "," + piece;
		client.broadcast(msg);

		textFont(font);
		fill(255);
		text("Broadcasting: " + msg, 25, height / 2);
	}

	// Changing background
	public void keyPressed() {
		if (keyCode == UP)
			bg++;
		else if (keyCode == DOWN)
			bg--;
		else if (keyCode == RIGHT)
			piece++;
		else if (keyCode == LEFT)
			piece--;
	}

	// --------------------------------------
	static public void main(String args[]) {
		PApplet.main(new String[] { "controller.Messenger" });

	}
}
