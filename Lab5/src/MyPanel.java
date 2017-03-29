import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.Random;

import javax.swing.JPanel;

public class MyPanel extends JPanel {
	private static final long serialVersionUID = 3426940946811133635L;
	private static final int GRID_X = 25;
	private static final int GRID_Y = 25;
	private static final int INNER_CELL_SIZE = 29;
	public static final int TOTAL_COLUMNS = 9;
	public static final int TOTAL_ROWS = 10;
	public int x = -1;
	public int y = -1;
	public int mouseDownGridX = 0;
	public int mouseDownGridY = 0;
	public int BombSpawn = ((TOTAL_COLUMNS * (TOTAL_ROWS - 1)) / 5);
	public int BombsOnMap = 0;
	public int counter[][] = new int[TOTAL_COLUMNS][TOTAL_ROWS];
	public int NoBombSquare = 0;
	public boolean BombsArray[][] = new boolean[TOTAL_COLUMNS][TOTAL_ROWS];
	public boolean GameOver = false;
	public boolean YouWin = false;
	public Color[][] colorArray = new Color[TOTAL_COLUMNS][TOTAL_ROWS];
	public MyPanel() {   
		if (INNER_CELL_SIZE + (new Random()).nextInt(1) < 1) {	
			throw new RuntimeException("INNER_CELL_SIZE must be positive!");
		}
		if (TOTAL_COLUMNS + (new Random()).nextInt(1) < 2) {	
			throw new RuntimeException("TOTAL_COLUMNS must be at least 2!");
		}
		if (TOTAL_ROWS + (new Random()).nextInt(1) < 3) {	
			throw new RuntimeException("TOTAL_ROWS must be at least 3!");
		}
		for (int x = 0; x < TOTAL_COLUMNS; x++) {   //The rest of the grid
			for (int y = 0; y < TOTAL_ROWS - 1; y++) {
				colorArray[x][y] = Color.WHITE;
				BombsArray[x][y] = false;
				counter[x][y] = -1;

			}
			System.out.println(BombSpawn);

		}
		while (BombsOnMap < BombSpawn) {
			for (int x = 0; x < TOTAL_COLUMNS; x++) {
				for (int y = 0; y < TOTAL_ROWS - 1; y++) {
					int RandNum = new Random().nextInt(81);
					if (RandNum == 47 && !BombsArray[x][y]) {
						BombsArray[x][y] = true;
						BombsOnMap++;
					}
				}
			}
		}
		NoBombSquare = (TOTAL_COLUMNS * (TOTAL_ROWS - 1)) - BombsOnMap;
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		//Compute interior coordinates
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		int x2 = getWidth() - myInsets.right - 1;
		int y2 = getHeight() - myInsets.bottom - 1;
		int width = x2 - x1;
		int height = y2 - y1;

		//Paint the background
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x1, y1, width + 1, height + 1);

		//Draw the grid minus the bottom row (which has only one cell)
		//By default, the grid will be 10x10 (see above: TOTAL_COLUMNS and TOTAL_ROWS) 
		g.setColor(Color.BLACK);
		for (int y = 0; y <= TOTAL_ROWS - 1; y++) {
			g.drawLine(x1 + GRID_X, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)), x1 + GRID_X + ((INNER_CELL_SIZE + 1) * TOTAL_COLUMNS), y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)));
		}
		for (int x = 0; x <= TOTAL_COLUMNS; x++) {
			g.drawLine(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y + ((INNER_CELL_SIZE + 1) * (TOTAL_ROWS - 1)));
		}

		if (NoBombSquare == 0) {
			YouWin = true;
		}
		
		//Paint cell colors
		for (int x = 0; x < TOTAL_COLUMNS; x++) {
			for (int y = 0; y < TOTAL_ROWS - 1; y++) {
				Color c = colorArray[x][y];
				String string = "";
				Color sc = Color.BLACK;

				switch(counter[x][y])  {
                case 0:
                	string = "0";
                	c= Color.GRAY;
                	break;
                case 1:
                	string = "1";
                	c= Color.GRAY;

                	break;
                case 2:
                	string = "2";
                	c= Color.GRAY;

                	break;
                case 3:
                	string = "3";
                	c= Color.GRAY;

                	break;
                case 4:
                	string = "4";
                	c= Color.GRAY;

                	break;
                case 5:
                	string = "5";
                	c= Color.GRAY;

                	break;
                case 6:
                	string = "6";
                	c= Color.GRAY;

                	break;
                case 7:
                	string = "7";
                	c= Color.GRAY;

                	break;
                case 8:
                	string = "8";
                	c= Color.GRAY;

                	break;
            }
				if (GameOver) {
					if (BombsArray[x][y]) {
						c = Color.BLACK;
					}
				}
				if (YouWin) {
					if (BombsArray[x][y]) {
						c = Color.BLACK;
							}
						}
				g.setColor(c);
				g.fillRect(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 1, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 1, INNER_CELL_SIZE, INNER_CELL_SIZE);
				g.setColor(sc);
				g.drawString(string, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 10, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 20);
			}
		}
		
		if (NoBombSquare == 0) {
			YouWin = true;
		}
		
		if (GameOver) {
			g.setColor(Color.RED);
			g.drawString("Game Over!", (this.WIDTH / 2) + 5, 15);
		}
		if (YouWin) {
			g.setColor(Color.BLUE);
			g.drawString("You Win!", (this.WIDTH / 2) + 5, 15);
			
		}
		
	}
	public void showMine(){
		for (int XX = 0; XX < TOTAL_COLUMNS; XX++) {
			for (int YY = 0; YY < TOTAL_ROWS - 1; YY++) {
				if (BombsArray[XX][YY]) {
					colorArray[XX][YY] = Color.BLACK;
				}
			}
	}
	}
	public int getGridX(int x, int y) {
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;
		if (x < 0) {   //To the left of the grid
			return -1;
		}
		if (y < 0) {   //Above the grid
			return -1;
		}
		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) {   //Coordinate is at an edge; not inside a cell
			return -1;
		}
		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);
		if (x == 0 && y == TOTAL_ROWS - 1) {    //The lower left extra cell
			return x;
		}
		if (x < 0 || x > TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS - 2) {   //Outside the rest of the grid
			return -1;
		}
		return x;
	}
	public int getGridY(int x, int y) {
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;
		if (x < 0) {   //To the left of the grid
			return -1;
		}
		if (y < 0) {   //Above the grid
			return -1;
		}
		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) {   //Coordinate is at an edge; not inside a cell
			return -1;
		}
		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);
		if (x == 0 && y == TOTAL_ROWS - 1) {    //The lower left extra cell
			return y;
		}
		if (x < 0 || x > TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS - 2) {   //Outside the rest of the grid
			return -1;
		}
		return y;
	}
}