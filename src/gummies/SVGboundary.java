package gummies;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.joints.*;

import pbox2d.PBox2D;
import processing.core.PApplet;
import processing.core.PShape;

public class SVGboundary {
	PApplet parent;
	
	// We need to keep track of a Body and a width and height
	Body body;
	float w, h;

	// A reference to our box2d world
	PBox2D box2d;
	
	// our svg
	PShape s;
	

	// Constructor
	SVGboundary(PApplet p, PBox2D box2d_, PShape _s) {
		parent = p;
		box2d = box2d_;
		
		s = _s;
		w = 1437;
		h = 76;

		
	    // Define the polygon
	    PolygonShape sd = new PolygonShape();
	    // Figure out the box2d coordinates
	    float box2dW = box2d.scalarPixelsToWorld(w/2);
	    float box2dH = box2d.scalarPixelsToWorld(h/2);
	    // We're just a box
	    sd.setAsBox(box2dW, box2dH);
	    
	    // Create the body
	    BodyDef bd = new BodyDef();
	    bd.type = BodyType.STATIC;
	    bd.position.set(box2d.coordPixelsToWorld(s.getParam(0),s.getParam(1)));
	    body = box2d.createBody(bd);
	    
	    // Attached the shape to the body using a Fixture
	    body.createFixture(sd,1);
	    
	}

	// Drawing the boundary
	void display() {
		    parent.fill(0);
		    parent.stroke(0);
		    parent.rectMode(parent.CENTER);
		    parent.rect(s.getParam(0)+718,s.getParam(1),w,h);
		  }
}
