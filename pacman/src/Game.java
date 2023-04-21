// PacMan.java
// Simple PacMan implementation
package src;

import ch.aplu.jgamegrid.*;
import src.actor.items.ItemManager;
import src.actor.EntityManager;
import src.utility.GameCallback;

import java.awt.*;
import java.util.Properties;

public class Game extends GameGrid
{
  private final static int nbHorzCells = 20;
  private final static int nbVertCells = 11;
  private PacManGameGrid grid;

  private EntityManager entityManager;
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

    itemManager = new ItemManager();
    grid = new PacManGameGrid(nbHorzCells, nbVertCells);
    itemManager.setupPillAndItemsLocations(this);
    itemManager.loadPillAndItemsLocations(properties);
    itemManager.setMaxPillsAndItems(this);

    // Draw grid
    GGBackground bg = getBg();
    grid.drawGrid(this, bg);

    createEntityManager(seed);

    //Setup Random seeds
    seed = Integer.parseInt(properties.getProperty("seed"));
    setKeyRepeatPeriod(150);

    //Run the game
    doRun();
    show();

    // Loop to look for collision in the application thread
    // This makes it improbable that we miss a hit
    boolean hasPacmanBeenHit;
    boolean hasPacmanEatAllPills;

    do {
      hasPacmanBeenHit = entityManager.hasThereBeenACollision();
      hasPacmanEatAllPills = entityManager.hasEatenAllPills();
      delay(10);
    } while(!hasPacmanBeenHit && !hasPacmanEatAllPills);
    delay(120);

    Location loc = entityManager.getPacActorLocation();
    entityManager.stopMonsters();
    entityManager.removePacActor();

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

  private void createEntityManager(int seed) {
    entityManager = new EntityManager(this, itemManager);
    entityManager.createPacActor(this);
    entityManager.setSeed(seed);
    entityManager.setSlowDown(3);
  }

  // Getter Methods
  public PacManGameGrid getGrid() {
    return grid;
  }

  public Properties getProperties() {
    return properties;
  }

  public int getNumHorzCells(){ return this.nbHorzCells; }

  public int getNumVertCells(){ return this.nbVertCells; }

  public GameCallback getGameCallback() { return gameCallback; }
  
  public ItemManager getItemManager(){
    return itemManager;
  }

  public EntityManager getEntityManager() {
    return entityManager;
  }
}
