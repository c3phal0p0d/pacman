package src.monsters;

import ch.aplu.jgamegrid.*;

import src.*;
import java.util.ArrayList;
import java.util.Properties;

public class MonsterManager {
    private ArrayList<Monster> monsters = new ArrayList<Monster>();

    // Creating an instance of monster manager creates all the monsters of the game along with it.

    //    public MonsterManager(ArrayList<Location> locations, ArrayList<Location> goldLocations, PacActor pacActor,
//                          PacManGameGrid grid)

    public MonsterManager(Game game, Properties properties) {

        // Create
        RandomWalkMonster troll = new RandomWalkMonster(game, MonsterType.Troll);
        TX5 tx5 = new TX5(game, MonsterType.TX5);

        // Convert into actor
        String[] trollLocations = properties.getProperty("Troll.location").split(",");
        String[] tx5Locations = properties.getProperty("TX5.location").split(",");
        int trollX = Integer.parseInt(trollLocations[0]);
        int trollY = Integer.parseInt(trollLocations[1]);
        int tx5X = Integer.parseInt(tx5Locations[0]);
        int tx5Y = Integer.parseInt(tx5Locations[1]);
        game.addActor(troll, new Location(trollX, trollY), Location.NORTH);
        game.addActor(tx5, new Location(tx5X, tx5Y), Location.NORTH);

        // TX-5 doesn't move for the first 5 seconds
        tx5.stopMoving(5);

        // Add
        monsters.add(troll);
        monsters.add(tx5);
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

    // Checks for collisions between pacman and all the monsters
    // returns true if yes
    // returns false if no
    public boolean hasThereBeenACollision(PacActor pacActor) {
        for (Monster m: monsters) {
            if (m.getLocation().equals(pacActor.getLocation())) {
                return true;
            }
        }
        return false;
    }
}
