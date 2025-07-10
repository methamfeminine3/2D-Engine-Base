package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {

	// SCREEN SETTINGS
	
	final int originalTileSize = 16; // each tile is 16x16
	
	final int Scale    = 3;
	final int TileSize = originalTileSize * Scale;
	
	final int MaxScreenCol = 16;
	final int MaxScreenRow = 12;
	final int ScreenWidth  = TileSize * MaxScreenCol; 
	final int ScreenHeight = TileSize * MaxScreenRow;
		// 768 x 576 screen size
	
	//FPS 
	
	double FPS = 60;
	
	
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	
	// set default position
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(ScreenWidth, ScreenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		
		
	}

	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
		
	}
	@Override
	public void run() {
		while (gameThread != null) {
			Double drawInterval = 1000000000 / FPS;
			Double nextDrawTime = System.nanoTime() + drawInterval;
			
//			System.out.println("Game loop is running");
		//	1 UPDATE: update information such as character position
			update();
		//	2 DRAW: draw screen with update
			repaint();
			
			
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime / 1000000;
				
				if (remainingTime < 0) {
					remainingTime = 0;
				}
				Thread.sleep((long) remainingTime);
				
				nextDrawTime += drawInterval;
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void update() {
		if (keyH.upPressed == true) {
			playerY -= playerSpeed;
		}
		else if (keyH.downPressed == true) {
			playerY += playerSpeed; 
		}
		else if (keyH.leftPressed == true) {
			playerX -= playerSpeed;
		}
		else if (keyH.rightPressed == true) {
			playerX += playerSpeed;		
		}
	}
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.white);
		g2.fillRect(playerX, playerY, TileSize, TileSize);
		g2.dispose();
		
		
	}
}
