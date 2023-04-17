package src.monsters;

import ch.aplu.jgamegrid.Location;
import src.Game;

import java.util.ArrayList;

public class Alien extends Monster {

    public Alien(MonsterManager monsterManager, MonsterType type) {
        super(monsterManager, type);
    }

    /*
    Alien calculates the distance between PacMan and each of its neighbouring locations (8 cells around the Alien)
    excluding maze walls. Then, it will move to the neighbouring location that has the shortest distance to PacMan.
    If more than one neighbouring location has the shortest distance, it will randomly select one.
     */
    protected void walkApproach() {
        ArrayList<LocationDistance> candidates = new ArrayList<LocationDistance>();
        Location pacManLocation = monsterManager.game.pacActor.getLocation();

        // Calc distance from all surrounding non-mazewall locations
        for (int i = 0; i < 8; i++) {
            //System.out.println("fuck u");
            Location nextLocation = getNextMoveLocation();
            if (canMove(nextLocation)) { // Location isn't a wall
                int distance = nextLocation.getDistanceTo(pacManLocation);
                LocationDistance candidate = new LocationDistance(nextLocation, distance);
                if(candidates.isEmpty()) { // Array is empty
                    candidates.add(candidate);
                } else {
                    int minDistance = candidates.get(0).distance();
                    if(distance < minDistance) { // shorter distance found
                        candidates.clear();
                        candidates.add(candidate);
                    } else if (distance == minDistance) { // same distance
                        candidates.add(candidate);
                    }
                }
            }
            turn(45); // Check next square
        }

        // Choose move based on candidates
        int size = candidates.size();
        if (size == 1) { // Only 1 candidate
            setLocation(candidates.get(0).location());
        } else if (size > 1) { // Randomly choose one
            System.out.println(size);
            int index = randomiser.nextInt(size - 1);

            Location newLocation = candidates.get(index).location();
            setLocation(newLocation);
        }
    }
}
