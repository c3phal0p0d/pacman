package src.actor;

import ch.aplu.jgamegrid.Location;
import src.utility.GameCallback;

import java.util.ArrayList;

/**
 * Type: New file
 * Team Name: Thursday 11:00am Team 1
 * Team Members:
 *      - Jiachen Si (1085839)
 *      - Natasha Chiorsac (1145264)
 *      - Jude Thaddeau Data (1085613)
 */

public abstract class ShortestDistanceMonster extends Monster {

    // ATTRIBUTES:
    private record LocationDistance(Location location, int distance) {}

    /**
     * INSTANTIATES a new instance of 'ShortestDistanceMonster'.
     * @param gameCallback  Used to display behaviour of the game
     * @param type          The TYPE of the monster
     * @param numHorzCells  The number of HORIZONTAL cells on the board
     * @param numVertCells  The number of VERTICAL cells on the board
     */
    public ShortestDistanceMonster(GameCallback gameCallback, MonsterType type, int numHorzCells, int numVertCells) {
        super(gameCallback, type, numHorzCells, numVertCells);
    }

    /**
     * The walk approach for 'ShortestDistanceMonster'.
     */
    protected abstract void walkApproach();

    /**
     * CALCULATES the distance between target location and each of its neighbouring locations (8 cells around the
     * monster) excluding maze walls.
     * @param target    The target location to move towards
     */
    protected void walkApproach(Location target) {

        // STEP 1: Initialise a record for candidate location distance
        ArrayList<LocationDistance> candidates = new ArrayList<LocationDistance>();

        // STEP 2: Calculate the distance from all surrounding non-mazewall locations
        for (int i = 0; i < 8; i++) {
            Location nextLocation = getNextMoveLocation();

            // STEP 3: Found a location that is not a wall
            if (canMove(nextLocation)) {
                int distance = nextLocation.getDistanceTo(target);
                LocationDistance candidate = new LocationDistance(nextLocation, distance);

                // CASE 4A: No shortest distance yet, add the new candidate location distance
                if(candidates.isEmpty()) {
                    candidates.add(candidate);

                // CASE 4B: Check if the current candidate distance is more desirable
                } else {
                    int minDistance = candidates.get(0).distance();

                    // STEP 5 Add candidate distances that are SHORTER or the same
                    if(distance < minDistance) {
                        candidates.clear();
                        candidates.add(candidate);
                    } else if (distance == minDistance) {
                        candidates.add(candidate);
                    }
                }
            }
            // STEP 6: Check the next square
            turn(45); // Check next square
        }
        // STEP 7: Choose move based on candidates
        int size = candidates.size();

        // CASE 8A: Only 1 candidate
        if (size == 1) { // Only 1 candidate
            setLocation(candidates.get(0).location());

        // CASE 8B: Randomly choose a candidate
        } else if (size > 1) { // Randomly choose one
            int index = randomiser.nextInt(size);
            Location newLocation = candidates.get(index).location();
            setLocation(newLocation);
        }
        gameCallback.monsterLocationChanged(this);
    }
}