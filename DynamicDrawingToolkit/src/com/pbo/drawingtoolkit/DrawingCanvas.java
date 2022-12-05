package com.pbo.drawingtoolkit;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicTabbedPaneUI.MouseHandler;

@SuppressWarnings({ "serial", "unused" })
public class DrawingCanvas extends JPanel{
	private ArrayList<DrawingObject> myShapes;
	private int currentShapeType;
	private DrawingObject currentShapeObject;
	private DrawingObject selectShapeObject;
	private Color currentShapeColor;
	private Point centerPoint;
	private Shape circle;
	
	private JLabel statusLabel;
	
	public DrawingCanvas(JLabel statusLabel) {
		this.statusLabel = statusLabel;
		
		myShapes = new ArrayList<DrawingObject>();
		currentShapeType = 0;
		currentShapeObject = null;
		currentShapeColor = Color.BLACK;
		selectShapeObject = null;
		
		this.setLayout(null);
		this.setBackground(Color.WHITE);
		add(statusLabel, BorderLayout.SOUTH);
		
		MouseHandler handler = new MouseHandler();
		addMouseListener(handler);
		addMouseMotionListener(handler);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for(int i = myShapes.size() - 1; i >= 0; i--) {
			myShapes.get(i).draw(g);
		}
		
		if(currentShapeObject != null) {
			currentShapeObject.draw(g);
		}
		
		if(selectShapeObject != null) {
			g.setColor(Color.red);
			g.drawRect(selectShapeObject.getUpperLeftX() - 1, selectShapeObject.getUpperLeftY() - 1, 
					selectShapeObject.getWidth() + 2, selectShapeObject.getHeight() + 2);
		}
		
	}
	
	public void setCurrentShapeType(int type) {
		this.currentShapeType = type;
		currentShapeObject = null;
		selectShapeObject = null;
		
		Cursor cursor;
		if(currentShapeType == 0) cursor = new Cursor(Cursor.HAND_CURSOR);
		else cursor = new Cursor(Cursor.DEFAULT_CURSOR);
		
		this.setCursor(cursor);
		repaint();
	}
	
	public void clearDrawing() {
		myShapes.clear();
		currentShapeType = 0;
		selectShapeObject = null;
		currentShapeObject = null;
		repaint();
	}
	
	private class MouseHandler extends MouseAdapter{
		public void mousePressed(MouseEvent e) {
			Cursor cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
			switch(currentShapeType) {
//				case 0:
				case 1:
					currentShapeObject = new Line( e.getX(), e.getY(), e.getX(), e.getY(),currentShapeColor);
					setCursor(cursor);
					break;
				case 2:
					currentShapeObject = new Circle( e.getX(), e.getY(), e.getX(), e.getY(), currentShapeColor);
					setCursor(cursor);
					break;
				case 3:
					currentShapeObject = new Rectangles(e.getX(), e.getY(), e.getX(), e.getY(), currentShapeColor);
					setCursor(cursor);
					break;
				case 4:
					currentShapeObject= new Oval( e.getX(), e.getY(), e.getX(), e.getY(), currentShapeColor);
					setCursor(cursor);
					break;
			}
		}
		
		public void mouseClicked(MouseEvent e) {
			selectShapeObject = null;
			if(currentShapeType == 0) {
				for(DrawingObject obj : myShapes) {
					if(obj.bound.getBounds().contains(e.getX(), e.getY())) {
						selectShapeObject = obj;
						break;
					}
				}
			}
			
			repaint();
		}
		
		public void mouseReleased(MouseEvent e) {
			if(currentShapeObject != null) {
				currentShapeObject.setX2(e.getX());
				currentShapeObject.setY2(e.getY());
				currentShapeObject.bound = new Rectangle(currentShapeObject.getUpperLeftX(), currentShapeObject.getUpperLeftY()
						, currentShapeObject.getWidth(), currentShapeObject.getHeight());
				
				myShapes.add(currentShapeObject);
				System.out.println(myShapes.toArray());
				
				currentShapeObject = null;
				
				Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR);
				setCursor(cursor);
			}
			repaint();
		}
		
		public void mouseMoved(MouseEvent e) {
			statusLabel.setText(String.format("Mouse Coordinates X: %d Y: %d", e.getX(), e.getY()));
		}
		
		public void mouseDragged(MouseEvent e) {
			if(currentShapeObject != null) {
				currentShapeObject.setX2(e.getX());
				currentShapeObject.setY2(e.getY());
				currentShapeObject.bound = new Rectangle(currentShapeObject.getUpperLeftX(), currentShapeObject.getUpperLeftY()
						, currentShapeObject.getWidth(), currentShapeObject.getHeight());
				statusLabel.setText(String.format("Mouse Coordinates X: %d Y: %d", e.getX(), e.getY()));
			}
			else if(selectShapeObject != null) {
				int xDiff = (selectShapeObject.getWidth() / 2);
				int yDiff = (selectShapeObject.getHeight() / 2);
				
				selectShapeObject.setX1(e.getX() - xDiff);
				selectShapeObject.setY1(e.getY() - yDiff);
				selectShapeObject.setX2(e.getX() + xDiff);
				selectShapeObject.setY2(e.getY() + yDiff);
				
				selectShapeObject.bound = new Rectangle(selectShapeObject.getUpperLeftX(), selectShapeObject.getUpperLeftY(), 
						selectShapeObject.getWidth(), selectShapeObject.getHeight());
			}
			repaint();
		}
	}
}
