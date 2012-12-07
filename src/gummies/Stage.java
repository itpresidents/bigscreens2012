package gummies;

import java.awt.Color;
import java.util.ArrayList;

import org.jbox2d.common.Vec2;

import pbox2d.*;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.core.PShape;
import processing.core.PFont;

public class Stage {
	PApplet parent;

	// Settings file
	Settings settings;

	// Background
	PImage skyline;
	int skyWidth;
	int skyHeight;

	// Credits
	Sign centerSign;

	// /////////////////////////////////////////////////////
	// ////////////////////////WIND/////////////////////////
	// /////////////////////////////////////////////////////

	ArrayList<Spring> springs = new ArrayList<Spring>();
	ArrayList<Bear> bears = new ArrayList<Bear>();

	// Tchochkes
	PImage[] gummyImgs = new PImage[3];
	PImage gummyMask;

	// Define altitude and light source for shadows
	public static float alt = Gummies.mHeight * 2;
	public static PVector source = new PVector(Gummies.mWidth / 2, -alt);

	// Used for generating noise across a number of wind classes
	public static int t;

	// /////////////////////////////////////////////////////
	// ///////////////////////BOX2D/////////////////////////
	// /////////////////////////////////////////////////////

	// A reference to our box2d world
	PBox2D box2d;

	// Create water line
	Water water;

	Stage(PApplet parent_) {
		parent = parent_;
		t = PApplet.parseInt(parent.random(1000));

		// Create skyline
		initSkyline(-1);

		// Create wind
		gummyMask = parent.loadImage("data/gummy_mask.jpg");

		for (int i = 0; i < gummyImgs.length; i++) {
			gummyImgs[i] = parent.loadImage("data/gummy_" + i + ".jpg");
			gummyImgs[i].mask(gummyMask);
		}

		// Load settings file
		// Create signs
		init(0);
	}

	void run() {
		parent.background(255);
		// Draw the skyline
		parent.image(skyline, 0, 0, skyWidth, skyHeight);
		
		
		// Blow the wind
		launchGummies();

		// Constantly change size, rotation, opacity and strength of springs for
		// each bear
		for (int i = 0; i < bears.size(); i++) {
			Bear thisBear = bears.get(i);
			thisBear.run();
			// If bear reach right side of window, kill it.
			if (thisBear.die())
				bears.remove(thisBear);
		}

		// We must always step through time!
		box2d.step();

		// Display credits
		centerSign.display();

		// Display water
		water.display();
		water.update();
	}

	void launchGummies() {

		// Create new springs and bears at a controlled rate
		float toss = PApplet.parseInt(parent.random(100));
		if (toss < settings.launchRate) {
			// if(bears.size() == 0) {
			// When launching new bears...
			// Choose a color gummy at random
			bears.add(new Bear(parent, gummyImgs[PApplet.parseInt(parent
					.random(0, gummyImgs.length))], (parent.noise(t
					+ parent.random(100)) * 100), box2d));

			t += parent.random(-1, 5);
		}
	}
	
	void init(int whichPiece) {
		String[] data = parent.loadStrings("data/settings.txt");

		String[] thisPiece = new String [8];
		int start = whichPiece*9;
		for(int i = 0; i < thisPiece.length; i++) {
			thisPiece[i] = data[start];
			start++;
		}
		
		// Create Settings
		settings = new Settings(parent, thisPiece);
		
		// Initialize box2d physics and create the world
		box2d = new PBox2D(parent);
		box2d.createWorld();
		
		// Create Sign
		Vec2 centerSignPos = new Vec2(Gummies.mWidth / 2, Gummies.mHeight / 2 - 200);
		float tilt = PApplet.PI / 72;
		float titleYOffset = 10;
		float namesYOffset = 180;
		float titleTextSize = 256;
		float namesTextSize = 128;
		PFont font = parent.createFont("data/comicSansBold.ttf", 540);
		float res = 50;
		float margin = 100;
		float signHeight = 500;
		int color = parent.color(255,100,50); 
		centerSign = new Sign(parent, box2d, settings, centerSignPos, tilt,
				titleYOffset, namesYOffset, titleTextSize, namesTextSize,
				font, res, margin, signHeight, color);
		
		// Create the water
		water = new Water(parent, box2d, settings);

		// Clear out all the bears
		bears.clear();
	}
	
	void initSkyline(int whichSkyline) {
		skyWidth = PApplet.parseInt(Gummies.mWidth);
		skyHeight = PApplet.parseInt(Gummies.mHeight);

		if (whichSkyline == -1)
			skyline = parent.loadImage("data/sky.jpg");
		else if (whichSkyline == 0)
			skyline = parent.loadImage("data/skyline-masked.jpg");
		else if (whichSkyline == 1) {
			skyline = parent.loadImage("data/skyline_futz.jpg");
			skyWidth = PApplet.parseInt(Gummies.mWidth);
			skyHeight = PApplet.parseInt(Gummies.mHeight);
		}
	}
}
