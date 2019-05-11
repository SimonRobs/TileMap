package com.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Tile {

	private int x, y;
	private Color color;

	/***
	 * Initializes a Tile object
	 * @param x the x position of the Tile
	 * @param y the y position of the Tile
	 */
	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
		color = new Color(51, 51, 51);
	}

	/***
	 * Getter for the Tile's x position
	 * @return The x position
	 */
	public int getX() {
		return x;
	}

	/***
	 * Sets the Tile's x position
	 * @param x the new x position
	 */
	public void setX(int x) {
		this.x = x;
	}


	/***
	 * Getter for the Tile's y position
	 * @return The y position
	 */
	public int getY() {
		return y;
	}

	/***
	 * Sets the Tile's y position
	 * @param y the new y position
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Renders the Tile to the screen
	 * @param g the Graphics context
	 */
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, Main.TILESIZE, Main.TILESIZE);
		g.setColor(Color.black);
		g.drawRect(x, y, Main.TILESIZE, Main.TILESIZE);
	}

	/**
	 * Sets the color of the Tile
	 * @param col the new color
	 */
	public void setColor(Color col) {
		this.color = col;
	}

	/**
	 * Getter for the Tile's bounds
	 * @return The rectangular bounds of the Tile
	 */
	public Rectangle getBounds() {
		return new Rectangle(x, y, Main.TILESIZE, Main.TILESIZE);
	}
}
