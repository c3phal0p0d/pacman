package src.monsters;

import ch.aplu.jgamegrid.Location;
import src.utility.GameCallback;

import java.util.ArrayList;

public abstract class ShortestDistanceMonster extends Monster {

    public ShortestDistanceMonster(GameCallback gameCallback, MonsterType type, int numHorzCells, int numVertCells) {
        super(gameCallback, type, numHorzCells, numVertCells);
    }

    private record LocationDistance(Location location, int distance) {}

    protected abstract void walkApproach();

    /*
    Calculates distance between target location and each of its neighbouring locations (8 cells around the monster)
    excluding maze walls. Then, it will move to the neighbouring location that has the shortest distance to target.
    If more than one neighbouring location has the shortest distance, it will randomly select one.
     */
    protected void walkApproach(Location target) {
        ArrayList<LocationDistance> candidates = new ArrayList<LocationDistance>();

        // Calc distance from all surrounding non-mazewall locations
        for (int i = 0; i < 8; i++) {
            Location nextLocation = getNextMoveLocation();
            if (canMove(nextLocation)) { // Location isn't a wall
                int distance = nextLocation.getDistanceTo(target);
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
            int index = randomiser.nextInt(size);

            Location newLocation = candidates.get(index).location();
            setLocation(newLocation);
        }
    }
}
