package com.pbo.drawingtoolkit;

import java.awt.Color;
import java.awt.Graphics;

public class Rectangles extends DrawingObject{
	public Rectangles() {
		super();
	}
	
	public Rectangles(int x1, int y1, int x2, int y2, Color color) {
		super(x1, y1, x2, y2, color);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.drawRect(getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight());
		
	}
	
}
