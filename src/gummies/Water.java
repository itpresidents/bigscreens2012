package gummies;

import java.util.*;

import org.jbox2d.collision.shapes.*;
import org.jbox2d.common.*;
import org.jbox2d.dynamics.*;

import pbox2d.*;
import processing.core.*;

class Water {

	// The water surface is made up of an ArrayList of points
	ArrayList <Vec2> surface;

	PApplet parent;
	PBox2D box2d;
	
	float counter;

	Water(PApplet p, PBox2D box2d_) {
		parent = p;
		box2d = box2d_;
		counter = (float) 0.0;
		
		// make a chain of water surface
		
		surface = new ArrayList<Vec2>();
		
	    // This is what box2d uses to put the surface in its world
	    ChainShape chain = new ChainShape();
		
		float theta = 0;
		
	    // This has to go backwards so that the objects  bounce off the top of the surface
	    // This "edgechain" will only work in one direction!
	    for (float x = Gummies.mWidth +10; x > -10; x -= 5) {

	      // Doing some stuff with perlin noise to calculate a surface that points down on one side
	      // and up on the other
	      float y = parent.map(parent.cos(theta),-1,1,Gummies.mHeight-100,Gummies.mHeight);
	      theta += 0.15;

	      // Store the vertex in screen coordinates
	      surface.add(new Vec2(x,y));

	    }
	    
	    // Build an array of vertices in Box2D coordinates
	    // from the ArrayList we made
	    Vec2[] vertices = new Vec2[surface.size()];
	    for (int i = 0; i < vertices.length; i++) {
	      Vec2 edge = box2d.coordPixelsToWorld(surface.get(i));
	      vertices[i] = edge;
	    }
	    
	    // Create the chain!
	    chain.createChain(vertices,vertices.length);

	    // The edge chain is now attached to a body via a fixture
	    BodyDef bd = new BodyDef();
	    bd.position.set(0.0f,0.0f);
	    Body body = box2d.createBody(bd);
	    // Shortcut, we could define a fixture if we
	    // want to specify frictions, restitution, etc.
	    body.createFixture(chain,1);

	}
	void display() {
	    parent.strokeWeight(2);
	    parent.stroke(0);
	    parent.noFill();
	    parent.beginShape();
	    for (Vec2 v: surface) {
	    	parent.vertex(v.x,v.y);
	    }
	    parent.endShape();
	}
	
	void update() {
		
	    float theta = counter;
	    
	    for(int i = 0; i < surface.size(); i++){
		    float y = parent.map(parent.cos(theta),-1,1,Gummies.mHeight-100,Gummies.mHeight);
		    theta += 0.15;
	    	surface.get(i).y = y;
	    }
	    
	    counter += 0.01;

	}
}
