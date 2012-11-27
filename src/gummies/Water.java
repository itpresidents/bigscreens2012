package gummies;

import java.util.*;

import pbox2d.*;
import processing.core.*;

class Water {
	
	int xspacing = 10; // how far apart horizontally.
	int w; // width of water surface
	float r = xspacing/2;
	
	float yoff = 0.0f; // perlin noise 2nd dimension
	float[] yvalues; // store height values in array

	// The water surface is made up of an ArrayList of Box2d circles
	ArrayList <Circle> surface;

	PApplet parent;
	PBox2D box2d;
	float counter = 0.0f;
	
	Water(PApplet p, PBox2D box2d_) {
		parent = p;
		box2d = box2d_;
		
		// make a chain of water surface
		
		surface = new ArrayList<Circle>();
		
	    // This is what box2d uses to put the surface in its world
		
		w = Gummies.mWidth+16;
		yvalues = new float[w/xspacing];
		
		for(int i = 0; i < yvalues.length; i ++){
			surface.add(new Circle(parent,box2d,i*xspacing,Gummies.mHeight-100,(xspacing/2)));
		}
	  }
	    
	void display() {
	    for (Circle v: surface) {
//	    	v.body.setTransform(new Vec2(v.locx,v.locy-100), 0);
	    	v.display();
	    }
//	    counter-=0.1f;
	}
}
