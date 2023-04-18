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
  protected MonsterManager monsterManager;
  protected MonsterType type;
  protected boolean stopMoving = false;
  protected Random randomiser = new Random(0);
  private boolean isFurious = false;

  public Monster(MonsterManager monsterManager, MonsterType type)
  {
    super("sprites/" + type.getImageName());
    this.monsterManager = monsterManager;
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

  /*
  Checks if the monster is able to move to a specified location. Returns true if yes, false if no.
   */
  protected boolean canMove(Location location)
  {
    Color c = getBackground().getColor(location);
    return !c.equals(Color.gray) && location.getX() < monsterManager.game.getNumHorzCells()
            && location.getX() >= 0 && location.getY() < monsterManager.game.getNumVertCells() && location.getY() >= 0;
  }

  public void stopMoving(int seconds) {
    this.stopMoving = true;
    Timer timer = new Timer(); // Instantiate Timer Object
    int SECOND_TO_MILLISECONDS = 1000;
    final Monster monster = this;
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        monster.stopMoving = false;
      }
    }, seconds * SECOND_TO_MILLISECONDS);
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

  public void setFurious(boolean state) {
    this.isFurious = state;
  }
}


