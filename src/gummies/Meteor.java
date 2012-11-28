package gummies;

import org.jbox2d.collision.shapes.*;
import org.jbox2d.common.*;
import org.jbox2d.dynamics.*;

import pbox2d.*;
import processing.core.*;

public class Meteor {
	PApplet parent;

	// We need to keep track of a Body and a width and height
	Body body;
	float x, y, r;

	// A reference to our box2d world
	PBox2D box2d;

	// Constructor
	Meteor(PApplet p, PBox2D box2d_, float _x, float _y, float _r) {
		parent = p;
		box2d = box2d_;

		x = _x;
		y = _y;

		r = _r;

		// Define a body
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DYNAMIC;
		// Set its position
		bd.position = box2d.coordPixelsToWorld(x, y);
		body = box2d.world.createBody(bd);

		// Make the body's shape a circle
		CircleShape cs = new CircleShape();
		cs.m_radius = box2d.scalarPixelsToWorld(r);

		// Define a fixture
		FixtureDef fd = new FixtureDef();
		fd.shape = cs;
		// Parameters that affect physics
		fd.density = 10000.0f;
		fd.friction = 0.0f;
		fd.restitution = 0.0f;

		body.createFixture(fd);
		
	    body.setLinearVelocity(new Vec2(parent.random(-500, 500), parent.random(-1000, -5000)));
	    body.setAngularVelocity(parent.random(-500, 500));
	}

	// Drawing the circle
	void display() {
		// We look at each body and get its screen position
		Vec2 pos = box2d.getBodyPixelCoord(body);
		float h = Gummies.mHeight - pos.y;
		parent.pushMatrix();
		parent.translate(pos.x, pos.y);
		PApplet.println(pos);
		parent.fill(0);
		parent.noStroke();
		parent.ellipse(0, 0, r * 2, r * 2);
		parent.popMatrix();
	}
}
