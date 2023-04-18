// PacMan.java
// Simple PacMan implementation
package src;

import ch.aplu.jgamegrid.*;
import src.items.ItemManager;
import src.monsters.MonsterManager;
import src.utility.GameCallback;
import src.monsters.Monster;
import src.monsters.MonsterType;

import java.awt.*;
import java.util.ArrayList;
import java.util.Properties;

public class Game extends GameGrid
{
  private final static int nbHorzCells = 20;
  private final static int nbVertCells = 11;
  private PacManGameGrid grid;

  private PacActor pacActor;
  private MonsterManager monsterManager;
  private ItemManager itemManager;

  private GameCallback gameCallback;
  private Properties properties;
  private int seed = 30006;

  // Constructor
  public Game(GameCallback gameCallback, Properties properties)
  {
    //Setup game
    super(nbHorzCells, nbVertCells, 20, false);
    this.gameCallback = gameCallback;
    this.properties = properties;
    setSimulationPeriod(100);
    setTitle("[PacMan in the Multiverse]");

    // Setup Components
    itemManager = new ItemManager(this);
    grid = new PacManGameGrid(this, nbHorzCells, nbVertCells);
    pacActor = new PacActor(this);
    itemManager.setMaxPillsAndItems(itemManager.countPillsAndItems());

    //Setup for auto test
    pacActor.setPropertyMoves(properties.getProperty("PacMan.move"));
    pacActor.setAuto(Boolean.parseBoolean(properties.getProperty("PacMan.isAuto")));
    loadPillAndItemsLocations();

    // Draw grid
    GGBackground bg = getBg();
    grid.drawGrid(bg);
    
    // Setup Components
    monsterManager = new MonsterManager(this, itemManager.getPropertyGoldLocations());

    //Setup Random seeds
    seed = Integer.parseInt(properties.getProperty("seed"));
    pacActor.setSeed(seed);
    monsterManager.setSeed(seed);
    addKeyRepeatListener(pacActor.getPlayerController());
    setKeyRepeatPeriod(150);
    monsterManager.setSlowDown(3);
    pacActor.setSlowDown(3);

    //Run the game
    doRun();
    show();

    // Loop to look for collision in the application thread
    // This makes it improbable that we miss a hit
    boolean hasPacmanBeenHit;
    boolean hasPacmanEatAllPills;
    setupPillAndItemsLocations();
    do {
      hasPacmanBeenHit = monsterManager.hasThereBeenACollision(pacActor);
      hasPacmanEatAllPills = pacActor.getNbPills() >= itemManager.getMaxPillsAndItems();
      delay(10);
    } while(!hasPacmanBeenHit && !hasPacmanEatAllPills);
    delay(120);

    Location loc = pacActor.getLocation();
    monsterManager.stopMonsters();
    pacActor.removeSelf();

    String title = "";
    if (hasPacmanBeenHit) {
      bg.setPaintColor(Color.red);
      title = "GAME OVER";
      addActor(new Actor("sprites/explosion3.gif"), loc);
    } else if (hasPacmanEatAllPills) {
      bg.setPaintColor(Color.yellow);
      title = "YOU WIN";
    }
    setTitle(title);
    gameCallback.endOfGame(title);

    doPause();
  }

  private void loadPillAndItemsLocations() {
    String pillsLocationString = properties.getProperty("Pills.location");
    if (pillsLocationString != null) {
      String[] singlePillLocationStrings = pillsLocationString.split(";");
      for (String singlePillLocationString: singlePillLocationStrings) {
        String[] locationStrings = singlePillLocationString.split(",");
        itemManager.getPropertyPillLocations().add(new Location(Integer.parseInt(locationStrings[0]), Integer.parseInt(locationStrings[1])));
      }
    }

    String goldLocationString = properties.getProperty("Gold.location");
    if (goldLocationString != null) {
      String[] singleGoldLocationStrings = goldLocationString.split(";");
      for (String singleGoldLocationString: singleGoldLocationStrings) {
        String[] locationStrings = singleGoldLocationString.split(",");
        itemManager.getPropertyGoldLocations().add(new Location(Integer.parseInt(locationStrings[0]), Integer.parseInt(locationStrings[1])));
      }
    }
  }

  private void setupPillAndItemsLocations() {
    for (int y = 0; y < nbVertCells; y++)
    {
      for (int x = 0; x < nbHorzCells; x++)
      {
        Location location = new Location(x, y);
        int a = grid.getCell(location);
        if (a == 1 && itemManager.getPropertyPillLocations().size() == 0) {
          itemManager.getPillAndItemLocations().add(location);
        }
        if (a == 3 && itemManager.getPropertyGoldLocations().size() == 0) {
          itemManager.getPillAndItemLocations().add(location);
        }
        if (a == 4) {
          itemManager.getPillAndItemLocations().add(location);
        }
      }
    }

    if (itemManager.getPropertyPillLocations().size() > 0) {
      for (Location location : itemManager.getPropertyPillLocations()) {
        itemManager.getPillAndItemLocations().add(location);
      }
    }
    if (itemManager.getPropertyGoldLocations().size() > 0) {
      for (Location location : itemManager.getPropertyGoldLocations()) {
        itemManager.getPillAndItemLocations().add(location);
      }
    }
  }

  // Getter Methods

  public Properties getProperties() {
    return properties;
  }
  public int getNumHorzCells(){ return this.nbHorzCells; }
  public int getNumVertCells(){ return this.nbVertCells; }
  public GameCallback getGameCallback() { return gameCallback; }
  public PacManGameGrid getGrid() {
    return grid;
  }

  public PacActor getPacActor() {
    return pacActor;
  }
  
  public ItemManager getItemManager(){
    return itemManager;
  }

  public MonsterManager getMonsterManager() {
    return monsterManager;
  }

  public Properties getProperties() {return properties; }
}
