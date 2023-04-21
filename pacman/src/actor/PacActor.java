// PacActor.java
// Used for PacMan
package src.actor;

import ch.aplu.jgamegrid.*;
import src.Game;
import src.actor.items.Item;
import src.actor.items.ItemManager;
import src.actor.items.ItemType;
import src.utility.GameCallback;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PacActor extends Actor implements LocationVisitedList
{
  private static final int nbSprites = 4;
  private int idSprite = 0;
  private int nbPills = 0;
  private int score = 0;
  private ArrayList<Location> visitedList = new ArrayList<Location>();
  private List<String> propertyMoves = new ArrayList<>();
  private int propertyMoveIndex = 0;
  private int seed;
  private Random randomiser = new Random();
  private PlayerController playerController;
  private String version;
  private MonsterManager monsterManager;
  private ItemManager itemManager;
  private GameCallback gameCallback;
  private int numHorzCells;
  private int numVertCells;



  public PacActor(Game game)
  {
    super(true, "sprites/pacpix.gif", nbSprites);  // Rotatable
    this.version = game.getProperties().getProperty("version");
    this.gameCallback = game.getGameCallback();
    this.numHorzCells = game.getNumHorzCells();
    this.numVertCells = game.getNumVertCells();
    this.monsterManager = game.getMonsterManager();
    this.itemManager = game.getItemManager();
    this.playerController = new PlayerController(this);

    // Setup PacActor
    String[] pacManLocations = game.getProperties().getProperty("PacMan.location").split(",");
    int pacManX = Integer.parseInt(pacManLocations[0]);
    int pacManY = Integer.parseInt(pacManLocations[1]);
    game.addActor(this, new Location(pacManX, pacManY));
  }
  private boolean isAuto = false;

  public void act()
  {
    show(idSprite);
    idSprite++;
    if (idSprite == nbSprites)
      idSprite = 0;

    if (isAuto) {
      moveInAutoMode();
    }
    gameCallback.pacManLocationChanged(getLocation(), score, nbPills);
  }

  private Location closestPillLocation() {
    int currentDistance = 1000;
    Location currentLocation = null;
    List<Location> pillAndItemLocations = itemManager.getPillAndItemLocations();
    for (Location location: pillAndItemLocations) {
      int distanceToPill = location.getDistanceTo(getLocation());
      if (distanceToPill < currentDistance) {
        currentLocation = location;
        currentDistance = distanceToPill;
      }
    }

    return currentLocation;
  }

  private void followPropertyMoves() {
    String currentMove = propertyMoves.get(propertyMoveIndex);
    switch(currentMove) {
      case "R":
        turn(90);
        break;
      case "L":
        turn(-90);
        break;
      case "M":
        Location next = getNextMoveLocation();
        if (canMove(next)) {
          setLocation(next);
          eatPill(next);
        }
        break;
    }
    propertyMoveIndex++;
  }

  private void moveInAutoMode() {
    if (propertyMoves.size() > propertyMoveIndex) {
      followPropertyMoves();
      return;
    }
    Location closestPill = closestPillLocation();
    double oldDirection = getDirection();

    Location.CompassDirection compassDir =
            getLocation().get4CompassDirectionTo(closestPill);
    Location next = getLocation().getNeighbourLocation(compassDir);
    setDirection(compassDir);
    if (!isVisited(next, visitedList) && canMove(next)) {
      setLocation(next);
    } else {
      // normal movement
      int sign = randomiser.nextDouble() < 0.5 ? 1 : -1;
      setDirection(oldDirection);
      turn(sign * 90);  // Try to turn left/right
      next = getNextMoveLocation();
      if (canMove(next)) {
        setLocation(next);
      } else {
        setDirection(oldDirection);
        next = getNextMoveLocation();
        if (canMove(next)) // Try to move forward
        {
          setLocation(next);
        } else {
          setDirection(oldDirection);
          turn(-sign * 90);  // Try to turn right/left
          next = getNextMoveLocation();
          if (canMove(next)) {
            setLocation(next);
          } else {
            setDirection(oldDirection);
            turn(180);  // Turn backward
            next = getNextMoveLocation();
            setLocation(next);
          }
        }
      }
    }
    eatPill(next);
    addVisitedList(next, visitedList);
  }

  protected boolean canMove(Location location)
  {
    // Checks if the player can traverse to the given tile.
    Color c = getBackground().getColor(location);
    if ( c.equals(Color.gray) || location.getX() >= numHorzCells
            || location.getX() < 0 || location.getY() >= numVertCells || location.getY() < 0)
      // Tile is grey or is outside the board
      return false;
    else
      return true;
  }

  protected void eatPill(Location location)
  {
    Item item = itemManager.getItemByLocation(location);
    if(item != null) {
      if(item.isClaimed()) {
        return;
      }

      ItemType type = item.getType();

      if (type.equals(ItemType.Pill))
      {
        System.out.println("eat pill");

        nbPills++;
        score++;
        gameCallback.pacManEatPillsAndItems(location, "pills");
        itemManager.removeItem(ItemType.Pill, location);
      } else if (type.equals(ItemType.Gold)) {

        System.out.println("eat gold");

        nbPills++;
        score+= 5;
        getBackground().fillCell(location, Color.lightGray);
        gameCallback.pacManEatPillsAndItems(location, "gold");
        itemManager.removeItem(ItemType.Gold, location);
        if(version.equals("multiverse")) {
          monsterManager.makeFurious();
        }
      } else if (type.equals(ItemType.Ice)) {

        System.out.println("eat ice");

        getBackground().fillCell(location, Color.lightGray);
        gameCallback.pacManEatPillsAndItems(location, "ice");
        itemManager.removeItem(ItemType.Ice, location);
        if(version.equals("multiverse")) {
          monsterManager.freezeMonsters();
        }
      }
    }

    String title = "[PacMan in the Multiverse] Current score: " + score;
    gameGrid.setTitle(title);
  }

  // Getter and Setter Methods
  public int getNbPills() {
    return nbPills;
  }

  public void setAuto(boolean auto) {
    isAuto = auto;
  }

  public boolean getAuto(){
    return isAuto;
  }

  public void setSeed(int seed) {
    this.seed = seed;
    randomiser.setSeed(seed);
  }

  public void setPropertyMoves(String propertyMoveString) {
    if (propertyMoveString != null) {
      this.propertyMoves = Arrays.asList(propertyMoveString.split(","));
    }
  }

  public PlayerController getPlayerController(){
    return playerController;
  }

}
