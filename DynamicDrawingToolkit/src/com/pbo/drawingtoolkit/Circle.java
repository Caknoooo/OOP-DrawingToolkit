package com.pbo.drawingtoolkit;

import java.awt.Color;
import java.awt.Graphics;

public class Circle extends DrawingObject{
	private int circleWidth, circleHeight;
	
	public Circle() {
		super();
	}
	
	public Circle(int x1, int y1, int x2, int y2, Color color) {
		super(x1, y1, x2, y2, color);
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		circleWidth = getWidth();
		circleHeight = getHeight();
		circleWidth = circleHeight;
		
		g.drawOval(getUpperLeftX(), getUpperLeftY(), circleWidth, circleHeight);
	}

}