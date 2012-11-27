package gummies;

import org.jbox2d.collision.shapes.*;
import org.jbox2d.common.*;
import org.jbox2d.dynamics.*;

import pbox2d.*;
import processing.core.*;

public class Circle {
	PApplet parent;

	// We need to keep track of a Body and a width and height
	Body body;
	float r, locx, locy;

	// A reference to our box2d world
	PBox2D box2d;
	
	// Constructor
	Circle(PApplet p, PBox2D box2d_, float x, float y, float _r) {
		parent = p;
		box2d = box2d_;
		
		locx = x;
		locy = y;

		r = _r;

		// Define a body
	    BodyDef bd = new BodyDef();
	    bd.type = BodyType.KINEMATIC;
	    // Set its position
	    bd.position = box2d.coordPixelsToWorld(x,y);
	    body = box2d.world.createBody(bd);
	    
	    // Make the body's shape a circle
	    CircleShape cs = new CircleShape();
	    cs.m_radius = box2d.scalarPixelsToWorld(r);
	    
	    body.createFixture(cs,1);
	}

	// Drawing the circle
	void display() {
	    // We look at each body and get its screen position
	    Vec2 pos = box2d.getBodyPixelCoord(body);
	    // Get its angle of rotation
	    float a = body.getAngle();
	    parent.pushMatrix();
	    parent.translate(pos.x,pos.y);
	    parent.rotate(a);
	    parent.fill(0);
	    parent.stroke(0);
	    parent.strokeWeight(1);
	    parent.ellipse(0,0,r*2,r*2);
	    parent.popMatrix();
	    }
}

