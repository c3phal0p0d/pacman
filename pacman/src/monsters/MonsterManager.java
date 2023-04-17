package src.monsters;

import ch.aplu.jgamegrid.*;

import src.*;
import java.util.ArrayList;
import java.util.Properties;

public class MonsterManager {

    private Game game;
    public Properties properties;
    private ArrayList<Monster> monsters = new ArrayList<Monster>();

    // Creating an instance of monster manager creates all the monsters of the game along with it.
    public MonsterManager(Game game, Properties properties, ArrayList<Location> goldLocations) {

        this.game = game;
        this.properties = properties;

        createSimpleMonsters();

        // Multiverse exclusive monsters
        String version = properties.getProperty("version");
        if (version.equals("multiverse")) {
            createMultiverseMonsters(goldLocations);
        }
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    private void createSimpleMonsters() {

        // Create Troll
        Troll troll = new Troll(this, MonsterType.Troll);
        String[] trollLocations = properties.getProperty("Troll.location").split(",");
        int trollX = Integer.parseInt(trollLocations[0]);
        int trollY = Integer.parseInt(trollLocations[1]);
        game.addActor(troll, new Location(trollX, trollY), Location.NORTH);
        monsters.add(troll);

        // Create TX5
        TX5 tx5 = new TX5(this, MonsterType.TX5);
        String[] tx5Locations = properties.getProperty("TX5.location").split(",");
        int tx5X = Integer.parseInt(tx5Locations[0]);
        int tx5Y = Integer.parseInt(tx5Locations[1]);
        game.addActor(tx5, new Location(tx5X, tx5Y), Location.NORTH);
        tx5.stopMoving(5); // TX-5 doesn't move for the first 5 seconds
        monsters.add(tx5);
    }

    private void createMultiverseMonsters(ArrayList<Location> goldLocations) {
        // Create Wizard
        Wizard wizard = new Wizard(this, MonsterType.Wizard);
        String[] wizardLocations = properties.getProperty("Wizard.location").split(",");
        int wizardX = Integer.parseInt(wizardLocations[0]);
        int wizardY = Integer.parseInt(wizardLocations[1]);
        game.addActor(wizard, new Location(wizardX, wizardY), Location.NORTH);
        monsters.add(wizard);

        // Create Orion
//        Orion orion = new Orion(this, MonsterType.Orion, goldLocations);
//        String[] orionLocations = properties.getProperty("Orion.location").split(",");
//        int orionX = Integer.parseInt(orionLocations[0]);
//        int orionY = Integer.parseInt(orionLocations[1]);
//        game.addActor(orion, new Location(orionX, orionY), Location.NORTH);
//        monsters.add(orion);

        // Create Alien
        Alien alien = new Alien(this, MonsterType.Alien);
        String[] alienLocations = properties.getProperty("Alien.location").split(",");
        int alienX = Integer.parseInt(alienLocations[0]);
        int alienY = Integer.parseInt(alienLocations[1]);
        game.addActor(alien, new Location(alienX, alienY), Location.NORTH);
        monsters.add(alien);
    }

    // Sets the random number seed for all monsters
    public void setSeed(int seed) {
        for (Monster m: monsters) {
            m.setSeed(seed);
        }
    }

    // Sets the interval the game will wait before the first call of the act() method
    public void setSlowDown(int interval) {
        for (Monster m: monsters) {
            m.setSlowDown(interval);
        }
    }

    // Stops all monsters in place
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
    public boolean hasThereBeenACollision(PacActor pacActor) {
        for (Monster m: monsters) {
            if (m.getLocation().equals(pacActor.getLocation())) {
                return true;
            }
        }
        return false;
    }

    public void setFuriousState(boolean state) {
        for (Monster m: monsters) {
            m.setFurious(state);
        }
    }

    public Game getGame() {
        return game;
    }

}
