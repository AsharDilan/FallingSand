import java.awt.*;
import java.util.*;

public class SandLab {
  public static void main(String[] args)
  {
    SandLab lab = new SandLab(120, 80);
    lab.run();
  }


  public static final int EMPTY = 0;
  public static final int METAL = 1;
  public static final int SAND = 2;
  public static final int WATER = 3;


  private int[][] grid;
  private SandDisplay display;

  public SandLab(int numRows, int numCols)
  {
    String[] names;
    names = new String[4];
    names[EMPTY] = "Empty";
    names[METAL] = "Metal";
    names[SAND] = "Sand";
    names[WATER] = "Water";
    display = new SandDisplay("Falling Sand", numRows, numCols, names);
    grid = new int[numRows][numCols];
  }


  private void locationClicked(int row, int col, int tool)
  {
    grid[row][col] = tool;
  }

  public void updateDisplay() {
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[0].length; j++) {
        if (grid[i][j] == EMPTY) {
          display.setColor(i, j, Color.BLACK);
        } else if (grid[i][j] == METAL) {
          display.setColor(i, j, Color.gray);
        } else if (grid[i][j] == SAND) {
          display.setColor(i, j, Color.yellow);
        } else if (grid[i][j] == WATER) {
          display.setColor(i, j, Color.cyan);
        }
      }
    }
  }

  public void step () {
    Random rand = new Random();
    int x = rand.nextInt(grid.length - 1);
    int y = rand.nextInt(grid[0].length);

    if (grid[x][y] == SAND) {
      if (grid[x + 1][y] == EMPTY) {
        grid[x][y] = EMPTY;
        grid[x + 1][y] = SAND;
      }
    }
    else if(grid[x][y] == WATER) {
      int num = rand.nextInt(3);
      System.out.println("" + num);
      if (grid[x][y] == WATER) {
        if ((grid[x+1][y] == EMPTY) && (grid[x][y+1] == EMPTY) && (grid[x][y-1] == EMPTY)) {
          grid[x][y] = EMPTY;

          if ((num == 0) && (grid[x][y+1] == EMPTY)) {
            grid[x][y+1] = WATER;
          }
          else if ((num == 1) && (grid[x][y-1] == EMPTY)) {
            grid[x][y-1] = WATER;
          }
          else if ((num == 2) && (grid[x+1][y] == EMPTY)) {
            grid[x+1][y] = WATER;

          }
        }
      }
    }
  }


  public void run() {
    while (true) {
      for (int i = 0; i < display.getSpeed(); i++)
        step();
      updateDisplay();
      display.repaint();
      display.pause(1);
      int[] mouseLoc = display.getMouseLocation();
      if (mouseLoc != null)
        locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
    }
  }
}
