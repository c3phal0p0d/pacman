package src.monsters;

import ch.aplu.jgamegrid.*;

import src.*;
import src.Game;
import src.items.Item;
import src.items.ItemManager;
import src.utility.GameCallback;

import java.util.ArrayList;
import java.util.Properties;

public class MonsterManager {

    private Properties properties;

    private ArrayList<Monster> monsters = new ArrayList<Monster>();

    private ItemManager itemManager;

    private PacActor pacActor;

    // Creating an instance of monster manager creates all the monsters of the game along with it.
    public MonsterManager(Game game, ItemManager itemManager) {

        this.properties = game.getProperties();
        this.itemManager = itemManager;

        createPacActor(game);

        // Simple version
        createTroll(game);
        createTX5(game);

        // Multiverse exclusive monsters
        String version = properties.getProperty("version");
        if (version.equals("multiverse")) {
            createWizard(game);
            createOrion(game);
            createAlien(game);
        }
    }

    private void createPacActor(Game game) {
        pacActor = new PacActor(game);

        //Setup for auto test
        pacActor.setPropertyMoves(properties.getProperty("PacMan.move"));
        pacActor.setAuto(Boolean.parseBoolean(properties.getProperty("PacMan.isAuto")));
        game.addKeyRepeatListener(pacActor.getPlayerController());
        pacActor.setSlowDown(3);
    }

    private void createTroll(Game game) {
        // Create Troll
        Troll troll = new Troll(game.getGameCallback(), game.getNumHorzCells(), game.getNumVertCells());
        String[] trollLocations = properties.getProperty("Troll.location").split(",");
        int trollX = Integer.parseInt(trollLocations[0]);
        int trollY = Integer.parseInt(trollLocations[1]);
        game.addActor(troll, new Location(trollX, trollY), Location.NORTH);
        monsters.add(troll);
    }

    private void createTX5(Game game) {
        // Create TX5
        TX5 tx5 = new TX5(game.getGameCallback(), this, game.getNumHorzCells(), game.getNumVertCells());
        String[] tx5Locations = properties.getProperty("TX5.location").split(",");
        int tx5X = Integer.parseInt(tx5Locations[0]);
        int tx5Y = Integer.parseInt(tx5Locations[1]);
        game.addActor(tx5, new Location(tx5X, tx5Y), Location.NORTH);
        tx5.stopMoving(5); // TX-5 doesn't move for the first 5 seconds
        monsters.add(tx5);
    }

    private void createWizard(Game game) {
        // Create Wizard
        Wizard wizard = new Wizard(game.getGameCallback(), game.getNumHorzCells(), game.getNumVertCells());
        String[] wizardLocations = properties.getProperty("Wizard.location").split(",");
        int wizardX = Integer.parseInt(wizardLocations[0]);
        int wizardY = Integer.parseInt(wizardLocations[1]);
        game.addActor(wizard, new Location(wizardX, wizardY), Location.NORTH);
        monsters.add(wizard);
    }

    private void createOrion(Game game) {
        // Create Orion
        ArrayList<Item> goldPieces = itemManager.getGoldPieces();
        Orion orion = new Orion(game.getGameCallback(), game.getNumHorzCells(), game.getNumVertCells(), goldPieces);
        String[] orionLocations = properties.getProperty("Orion.location").split(",");
        int orionX = Integer.parseInt(orionLocations[0]);
        int orionY = Integer.parseInt(orionLocations[1]);
        game.addActor(orion, new Location(orionX, orionY), Location.NORTH);
        monsters.add(orion);
    }

    private void createAlien(Game game) {
        // Create Alien
        Alien alien = new Alien(game.getGameCallback(), this, game.getNumHorzCells(),
                                game.getNumVertCells());
        String[] alienLocations = properties.getProperty("Alien.location").split(",");
        int alienX = Integer.parseInt(alienLocations[0]);
        int alienY = Integer.parseInt(alienLocations[1]);
        game.addActor(alien, new Location(alienX, alienY), Location.NORTH);
        monsters.add(alien);
    }

    // Sets the random number seed for all entities
    public void setSeed(int seed) {
        for (Monster m: monsters) {
            m.setSeed(seed);
        }
        pacActor.setSeed(seed);
    }

    // Sets the interval the game will wait before the first call of the act() method
    public void setSlowDown(int interval) {
        for (Monster m: monsters) {
            m.setSlowDown(interval);
        }
    }

    // Freeze all monsters for a period of time
    public void freezeMonsters() {
        int FREEZE_TIME_INTERVAL = 3;
        for (Monster m: monsters) {
            m.stopMoving(FREEZE_TIME_INTERVAL);
        }
    }

    // Make all monsters furious for a period of time
    public void makeFurious() {
        int FURIOUS_TIME_INTERVAL = 3;
        for (Monster m: monsters) {
            m.makeFurious(FURIOUS_TIME_INTERVAL);
        }
    }

    // Stops all monsters in place, only called when game ends or player dies
    public void stopMonsters() {
        for (Monster m: monsters) {
            m.setStopMoving(true);
        }
    }

    /*
        Checks for collisions between pacman and all the monsters
        returns true if yes
        returns false if no
     */
    public boolean hasThereBeenACollision() {
        for (Monster m: monsters) {
            if (m.getLocation().equals(pacActor.getLocation())) {
                return true;
            }
        }
        return false;
    }

    public boolean hasEatenAllPills() {
        System.out.println(pacActor.getNbPills() + " " + itemManager.getMaxPillsAndItems());
        return pacActor.getNbPills() >= itemManager.getMaxPillsAndItems();
    }

    public Location getPacActorLocation() {
        return pacActor.getLocation();
    }

    public void removePacActor() {
        pacActor.removeSelf();
    }

    public ItemManager getItemManager() {
        return itemManager;
    }
}
