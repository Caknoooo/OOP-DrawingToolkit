package com.pbo.drawingtoolkit;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Oval extends DrawingObject {
	Point centerPoint;
	
	public Oval() {
		super();
	}
	
	public Oval(int x, int y, int width, int height, Color color) {
		super(x, y, width, height, color);
		//this.centerPoint = point;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.drawOval(getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight());
	}

}
