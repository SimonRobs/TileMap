package com.main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class TileMap {

	public static final int COLS = Main.WIDTH / Main.TILESIZE;
	public static final int ROWS = Main.C_HEIGHT / Main.TILESIZE;

	private static ArrayList<Tile> map;
	private int tileSize;

	/***
	 * Initializes the TileMap with the wanted tile size
	 * @param tileSize the size of the Tiles
	 */
	public TileMap(int tileSize) {
		map = new ArrayList<Tile>();
		this.tileSize = tileSize;
	}

	/***
	 * Getter for the map
	 * @return the map
	 */
	public ArrayList<Tile> getMap() {
		return map;
	}

	/***
	 * Getter for the tile size
	 * @return the tile size
	 */
	public int getTileSize() {
		return tileSize;
	}

	/***
	 * Populates the TileMap
	 */
	public void create() {
		for (int y = 0; y < Main.C_HEIGHT; y += tileSize) {
			for (int x = 0; x < Main.WIDTH; x += tileSize) {
				Tile t = new Tile(x, y);
				map.add(t);
			}
		}
	}

	/***
	 * Renders the TileMap to the screen
	 * @param g the Graphics context
	 */
	public void draw(Graphics g) {
		for (Tile t : map) {
			t.draw(g);
		}
	}

	/***
	 * Resets the TileMap to its original state by removing all the text
	 * @param g the Graphics context
	 */
	public void clearText(Graphics g) {
		for (Tile t : map) {
			t.setColor(new Color(51, 51, 51));
			;
		}
	}

}
