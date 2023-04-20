// PacMan.java
// Simple PacMan implementation
package src;

import ch.aplu.jgamegrid.*;
import src.items.ItemManager;
import src.monsters.MonsterManager;
import src.utility.GameCallback;

import java.awt.*;
import java.util.Properties;

public class Game extends GameGrid
{
  private final static int nbHorzCells = 20;
  private final static int nbVertCells = 11;
  private PacManGameGrid grid;

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
    itemManager = new ItemManager();
    grid = new PacManGameGrid(nbHorzCells, nbVertCells);
    itemManager.setMaxPillsAndItems(itemManager.countPillsAndItems(this));

    //Setup for auto test
    itemManager.loadPillAndItemsLocations(properties);

    // Draw grid
    GGBackground bg = getBg();
    grid.drawGrid(this, bg);
    
    // Setup Components
    monsterManager = new MonsterManager(this, itemManager);

    //Setup Random seeds
    seed = Integer.parseInt(properties.getProperty("seed"));
    monsterManager.setSeed(seed);
    setKeyRepeatPeriod(150);
    monsterManager.setSlowDown(3);

    //Run the game
    doRun();
    show();

    // Loop to look for collision in the application thread
    // This makes it improbable that we miss a hit
    boolean hasPacmanBeenHit;
    boolean hasPacmanEatAllPills;

    itemManager.setupPillAndItemsLocations(this);
    do {


      hasPacmanBeenHit = monsterManager.hasThereBeenACollision();
      hasPacmanEatAllPills = monsterManager.hasEatenAllPills();
      delay(10);
    } while(!hasPacmanBeenHit && !hasPacmanEatAllPills);
    delay(120);

    Location loc = monsterManager.getPacActorLocation();
    monsterManager.stopMonsters();
    monsterManager.removePacActor();

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
  
  public ItemManager getItemManager(){
    return itemManager;
  }
}
