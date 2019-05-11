package com.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

public class Main extends JPanel {
	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 1024, C_HEIGHT = 640, TILESIZE = 8;
	public static final int T_HEIGHT = 30;
	public static JFrame frame;
	private static JTextArea textArea;

	private static boolean running;

	private TileMap map;
	private LetterHelper lh;

	public static void main(String[] args) {
		Main app = new Main();
		app.init();

		while (running) {
			textArea.requestFocus();
			app.repaint();
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void init() {
		frame = new JFrame("Tile Trial");
		/////////// CANVAS//////////////////////
		this.setSize(new Dimension(WIDTH, C_HEIGHT));
		this.setBackground(new Color(0, 0, 0));
		map = new TileMap(TILESIZE);
		map.create();
		lh = new LetterHelper();
		frame.getContentPane().add(this);
		///////////////////////////////////////
		/////////// TEXT////////////////////////
		textArea = new JTextArea("Write what you want");
		textArea.getInputMap().put(KeyStroke.getKeyStroke("ENTER"),""); //Disable Carriage Return
		frame.getContentPane().add(textArea, BorderLayout.SOUTH);
		//////////////////////////////////////
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, C_HEIGHT + T_HEIGHT);
		frame.setResizable(false);
		running = true;
		frame.setVisible(true);
	}

	public void paintComponent(Graphics g) {
		map.draw(g);
		updateText();
	}

	private void updateText() {
		String text = textArea.getText();
		lh.drawString(text, map, 2, 2);
	}

	@SuppressWarnings("unused")
	private void penMode() {
		Point initMouseLoc = frame.getMousePosition();
		if (initMouseLoc != null) {
			Point mouseLoc = new Point(initMouseLoc.x, initMouseLoc.y - 25);
			for (Tile t : map.getMap()) {
				if (t.getBounds().contains(mouseLoc)) {
					t.setColor(new Color(200, 200, 200));
				}
			}
		}
	}
}
