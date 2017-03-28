import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JFrame;

public class MyMouseAdapter extends MouseAdapter {
	private Random generator = new Random();
	public void mousePressed(MouseEvent e) {
		switch (e.getButton()) {
		case 1:		//Left mouse button
			Component c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			JFrame myFrame = (JFrame) c;
			MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);
			Insets myInsets = myFrame.getInsets();
			int x1 = myInsets.left;
			int y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			int x = e.getX();
			int y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			myPanel.mouseDownGridX = myPanel.getGridX(x, y);
			myPanel.mouseDownGridY = myPanel.getGridY(x, y);
			myPanel.repaint();
			break;
		case 3:		//Right mouse button
			//			Component C = e.getComponent();
			//			while (!(C instanceof JFrame)) {
			//				C = C.getParent();
			//				if (C == null) {
			//					return;
			//				}
			//			}
			//			JFrame myFrame2 = (JFrame) C;
			//			MyPanel myPanel2 = (MyPanel) myFrame2.getContentPane().getComponent(0);
			//			Insets myInsets2 = myFrame2.getInsets();
			//			int x2 = myInsets2.left;
			//			int y2 = myInsets2.top;
			//			e.translatePoint(-x2, -y2);
			//			int x3 = e.getX();
			//			int y3 = e.getY();
			//			myPanel.x = x3;
			//			myPanel.y = y3;
			//			myPanel.mouseDownGridX = myPanel.getGridX(x3, y3);
			//			myPanel.mouseDownGridY = myPanel.getGridY(x3, y3);
			//			myPanel.repaint();
			break;
		default:    //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
			break;
		}
	}
	public void mouseReleased(MouseEvent e) {
		switch (e.getButton()) {
		case 1:		//Left mouse button
			Component c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			JFrame myFrame = (JFrame)c;
			MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
			if (!myPanel.GameOver) {
				Insets myInsets = myFrame.getInsets();
				int x1 = myInsets.left;
				int y1 = myInsets.top;
				e.translatePoint(-x1, -y1);
				int x = e.getX();
				int y = e.getY();
				myPanel.x = x;
				myPanel.y = y;
				int gridX = myPanel.getGridX(x, y);
				int gridY = myPanel.getGridY(x, y);
				if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {
					//Had pressed outside
					//Do nothing
				} else {
					if ((gridX == -1) || (gridY == -1)) {
						//Is releasing outside
						//Do nothing
					} else {
						if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) {
							//Released the mouse button on a different cell where it was pressed
							//Do nothing
						} else {
							if (myPanel.BombsArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY]) {
								myPanel.GameOver = true;
								myPanel.repaint();
							}
							else {
								surroundings(myPanel.mouseDownGridX, myPanel.mouseDownGridY, myPanel);
								myPanel.repaint();
							}
						}
					}
				}
				myPanel.repaint();
			}
			break;
		case 3:		//Right mouse button
			
		default:    //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
			break;
		}
	}
	private void surroundings(int n, int m, MyPanel myPanel) {
		// TODO Auto-generated method stub
		if (myPanel.counter[n][m] == -1) {
			myPanel.counter[n][m] = 0;
			for (int x = n-1; x <= n+1; x++) {
				if (x < 0 || x >= myPanel.TOTAL_COLUMNS) {
					continue;
				}
				for (int y = m-1; y <= m+1; y++) {
					if (y < 0 || y >= myPanel.TOTAL_ROWS - 1) {
						continue;
					}
					if (x == n && y == m) {
						continue;
					}
					if (myPanel.BombsArray[x][y]) {
						myPanel.counter[n][m]++;
					}
				}
			}
			if (myPanel.counter[n][m] == 0) {
				for (int x = n-1; x <= n+1; x++) {
					if (x < 0 || x >= myPanel.TOTAL_COLUMNS) {
						continue;
					}
					for (int y = m-1; y <= m+1; y++) {
						if (y < 0 || y >= myPanel.TOTAL_ROWS - 1) {
							continue;
						}

						surroundings(x, y, myPanel);

					}
				}
			}
		}

	}
}