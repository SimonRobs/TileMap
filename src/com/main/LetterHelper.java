package com.main;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class LetterHelper {

	private static ArrayList<Character> letters;
	private static ArrayList<int[][]> letterDefines;
	private static Map<Character, int[][]> alphabet;

	private int height, width;
	private static int nBlocks;

	/**
	 * Default constructor. Defines the alphabet
	 */
	public LetterHelper() {
		try {
			defineMap();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Defines the usable alphabet
	 *
	 * @throws FileNotFoundException
	 *             If the letters.txt file is not found
	 * @throws IOException
	 *             If there was a problem while reading the letters file
	 * @throws org.json.simple.parser.ParseException
	 *             If there was a problem while reading the letterDefines file
	 */
	private void defineMap() throws FileNotFoundException, IOException, ParseException {
		alphabet = new HashMap<Character, int[][]>();
		initList();
		initDefines();
		
		int i = 0;
		for(char letter : letters)
			alphabet.put(letter, letterDefines.get(i++));
	}

	/**
	 * Initializes the letter list
	 * 
	 * @throws FileNotFoundException
	 *             If the letters.txt file is not found
	 * @throws IOException
	 *             If there was a problem while reading the file
	 */
	private void initList() throws FileNotFoundException, IOException {
		height = 5;
		width = 3;
		nBlocks = (TileMap.COLS / width) * (TileMap.ROWS / height);
		letters = new ArrayList<Character>();

		FileReader fr = new FileReader("letters.txt");

		int i;
		while ((i = fr.read()) != -1) {
			char c = (char) i;
			if (c != '¶')
				letters.add(c);
		}
		fr.close();
	}

	/**
	 * Initializes the letter defines read from the JSON
	 * 
	 * @throws FileNotFoundException
	 *             If the letters.txt file is not found
	 * @throws IOException
	 *             If there was a problem while reading the letters file
	 * @throws org.json.simple.parser.ParseException
	 *             If there was a problem while reading the letterDefines file
	 */
	private void initDefines() throws FileNotFoundException, IOException, ParseException {
		letterDefines = new ArrayList<int[][]>();

		JSONParser parser = new JSONParser();
		Object parsed = parser.parse(new FileReader("letterDefines.json"));
		JSONObject json = (JSONObject) parsed;

		for (char c : letters) {
			JSONArray jsonArr = (JSONArray) json.get(Character.toString(c));
			int[][] arr = getLetterDefine(jsonArr);
			letterDefines.add(arr);
		}
	}

	/**
	 * Parses the JSON Array to return the tile definition
	 * 
	 * @param arr
	 *            The JSON Array to parse
	 * @return The tile definition in the JSON as a 2D array
	 */
	private int[][] getLetterDefine(JSONArray arr) {
		int[][] letterDefine = new int[height][width];
		int i = 0, j = 0;
		for (Object rowObj : arr) {
			JSONArray row = (JSONArray) rowObj;
			int[] defineRow = new int[width];
			j = 0;
			for (Object itemObj : row) {
				long itemL = (long) itemObj;
				defineRow[j++] = (int) itemL;
			}
			letterDefine[i++] = defineRow;
		}
		return letterDefine;
	}

	/**
	 * Draws a letter from the alphabet in the defined bounds
	 * 
	 * @param letter
	 *            the letter to draw
	 * @param map
	 *            the TileMap on which to draw the letter
	 * @param r
	 *            the row to which to draw the letter
	 * @param c
	 *            the column to which to draw the letter
	 */
	public void drawLetter(char letter, TileMap map, int r, int c) {
		int[][] letterMap = alphabet.get(letter);
		Tile currentTile;
		for (int col = 0; col < width; col++) {
			for (int row = 0; row < height; row++) {
				currentTile = map.getMap().get((r + row) * TileMap.COLS + (c + col));
				if (letterMap[row][col] == 1)
					currentTile.setColor(new Color(150, 150, 150));
				else
					currentTile.setColor(new Color(51, 51, 51));
			}
		}
	}

	/**
	 * Draws a string to the TileMap
	 * 
	 * @param s
	 *            the String to draw
	 * @param map
	 *            the TileMap on which to draw the String
	 * @param r
	 *            the row to which to draw the String
	 * @param c
	 *            the column to which to draw the String
	 */
	public void drawString(String s, TileMap map, int r, int c) {
		char[] sArr = s.toUpperCase().toCharArray();
		int initC = c;
		for (int i = 0; i < sArr.length; i++) {
			if (c + width + 1 >= TileMap.COLS) {
				r += height + 1;
				if (r >= TileMap.ROWS) {
					System.err.println("String cannot excede canvas height...");
					return;
				}
				c = initC;
				if (sArr[i] == ' ' && i != sArr.length - 1)
					i++;
			}
			char letter = sArr[i];
			try {
				drawLetter(letter, map, r, c);
			} catch (NullPointerException nullE) {
				System.out.println("Character not defined in the alphabet!");
			}
			c += width + 1;
		}
		char[] rest = new char[nBlocks - sArr.length];
		for (int i = 0; i < rest.length; i++) {
			rest[i] = ' ';
		}
		for (int i = 0; i < rest.length; i++) {
			if (c + width + 1 >= TileMap.COLS) {
				r += height + 1;
				if (r >= TileMap.ROWS) {
					return;
				}
				c = initC;
			}
			char space = rest[i];
			drawLetter(space, map, r, c);
			c += width + 1;
		}
	}
}
