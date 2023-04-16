// Monster.java
// Used for PacMan
package src.monsters;

import ch.aplu.jgamegrid.*;
import java.awt.Color;
import java.io.Console;
import java.util.*;
import src.Game;

public abstract class Monster extends Actor
{
  protected Game game;
  protected MonsterType type;
  private ArrayList<Location> visitedList = new ArrayList<Location>();
  private final int listLength = 10;
  protected boolean stopMoving = false;
  protected Random randomiser = new Random(0);

  public Monster(Game game, MonsterType type)
  {
    super("sprites/" + type.getImageName());
    this.game = game;
    this.type = type;
  }

  // This method is called upon every cycle of the jgamegrid simulation loop
  public void act()
  {
    if (stopMoving) {
      return;
    }
    walkApproach();
    setHorzMirror(!(getDirection() > 150) || !(getDirection() < 210));
  }

  protected abstract void walkApproach();

  protected void addVisitedList(Location location)
  {
    visitedList.add(location);
    if (visitedList.size() == listLength)
      visitedList.remove(0);
  }

  protected boolean isVisited(Location location)
  {
    for (Location loc : visitedList)
      if (loc.equals(location))
        return true;
    return false;
  }

  /*
  Checks if the monster is able to move to a specified location. Returns true if yes, false if no.
   */
  protected boolean canMove(Location location)
  {
    Color c = getBackground().getColor(location);
    return !c.equals(Color.gray) && location.getX() < game.getNumHorzCells()
            && location.getX() >= 0 && location.getY() < game.getNumVertCells() && location.getY() >= 0;
  }

  // Getter and Setter Methods
  public MonsterType getType() {
    return type;
  }

  public void setSeed(int seed) {
    randomiser.setSeed(seed);
  }

  public void setStopMoving(boolean stopMoving) {
    this.stopMoving = stopMoving;
  }
}


