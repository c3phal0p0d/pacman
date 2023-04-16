// PacGrid.java
package src;

import ch.aplu.jgamegrid.*;

import java.awt.*;

public class PacManGameGrid
{
  /*
   * Class that represents the grid the game is to be played on
   * x
   */

  private Game game;

  private int nbHorzCells;
  private int nbVertCells;
  private int[][] mazeArray;

  public PacManGameGrid(Game game, int nbHorzCells, int nbVertCells)
  {
    this.game = game;
    this.nbHorzCells = nbHorzCells;
    this.nbVertCells = nbVertCells;
    mazeArray = new int[nbVertCells][nbHorzCells];
    String maze =
      "xxxxxxxxxxxxxxxxxxxx" + // 0
      "x....x....g...x....x" + // 1
      "xgxx.x.xxxxxx.x.xx.x" + // 2
      "x.x.......i.g....x.x" + // 3
      "x.x.xx.xx  xx.xx.x.x" + // 4
      "x......x    x......x" + // 5
      "x.x.xx.xxxxxx.xx.x.x" + // 6
      "x.x......gi......x.x" + // 7
      "xixx.x.xxxxxx.x.xx.x" + // 8
      "x...gx....g...x....x" + // 9
      "xxxxxxxxxxxxxxxxxxxx";// 10


    // Copy maze structure into integer array
    for (int i = 0; i < nbVertCells; i++)
    {
      for (int k = 0; k < nbHorzCells; k++) {
        int value = toInt(maze.charAt(nbHorzCells * i + k));
        mazeArray[i][k] = value;
      }
    }
  }

  public int getCell(Location location)
  {
    return mazeArray[location.y][location.x];
  }
  private int toInt(char c)
  {
    if (c == 'x') // Maze Wall
      return 0;
    if (c == '.') // Pill
      return 1;
    if (c == ' ') // Empty
      return 2;
    if (c == 'g') // Gold
      return 3;
    if (c == 'i') // Ice
      return 4;
    return -1;    // Not Recognised
  }

  public void drawGrid(GGBackground bg)
  {
    bg.clear(Color.gray);
    bg.setPaintColor(Color.white);
    for (int y = 0; y < nbVertCells; y++)
    {
      for (int x = 0; x < nbHorzCells; x++)
      {
        bg.setPaintColor(Color.white);
        Location location = new Location(x, y);
        int a = getCell(location);
        if (a > 0)
          bg.fillCell(location, Color.lightGray);
        if (a == 1 && game.getItemManager().getPropertyPillLocations().size() == 0) { // Pill
          game.getItemManager().putPill(bg, location);
        } else if (a == 3 && game.getItemManager().getPropertyGoldLocations().size() == 0) { // Gold
          game.getItemManager().putGold(bg, location);
        } else if (a == 4) {
          game.getItemManager().putIce(bg, location);
        }
      }
    }

    for (Location location : game.getItemManager().getPropertyPillLocations()) {
      game.getItemManager().putPill(bg, location);
    }

    for (Location location : game.getItemManager().getPropertyGoldLocations()) {
      game.getItemManager().putGold(bg, location);
    }
  }
}
