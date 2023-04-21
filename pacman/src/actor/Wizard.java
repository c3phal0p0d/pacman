package src.actor;

import ch.aplu.jgamegrid.Location;
import src.utility.GameCallback;

import java.awt.*;

/**
 * Type: New file
 * Team Name: Thursday 11:00am Team 1
 * Team Members:
 *      - Jiachen Si (1085839)
 *      - Natasha Chiorsac (1145264)
 *      - Jude Thaddeau Data (1085613)
 */

public class Wizard extends RandomWalkMonster {

    /**
     * INSTANTIATES a new instance of 'Wizard'.
     * @param gameCallback Used to display behaviour of the game
     * @param numHorzCells The number of HORIZONTAL cells on the board
     * @param numVertCells The number of VERTICAL cells on the board
     */
    public Wizard(GameCallback gameCallback, int numHorzCells, int numVertCells) {
        super(gameCallback, MonsterType.Wizard, numHorzCells, numVertCells);
    }

    /**
     * 'Wizard' walks randomly with addition of being able to phase through walls.
     */
    protected void walkApproach() {

        // STEP 1: Randomly choose starting direction to search in
        int upperbound = 7;
        int dirMultiple = randomiser.nextInt(upperbound);
        int newDirection = dirMultiple * 45;
        turn(newDirection);

        // STEP 2: Check all directions
        for (int i = 0; i < 8; i++) {

            // STEP 3: Check adjacent tiles
            Location nextLocation = getNextMoveLocation();
            if (wizardCanMove(nextLocation)) {
                break;
            }
            // STEP 4: Move hasn't been found, check next direction
            turn(45);
        }
        gameCallback.monsterLocationChanged(this);
    }

    /**
     * Handles the LOGIC of when 'PacActor' consumes a GOLD item.
     */
    @Override
    protected void furiousWalkApproach() {

        // STEP 1: Move once
        walkApproach();

        // CASE 2A: Try to travel in the same direction again
        Location next = getNextMoveLocation();
        if (wizardCanMove(next)) {
            setLocation(next);
            gameCallback.monsterLocationChanged(this);

        // CASE 2B: Travel elsewhere
        } else {
            walkApproach();
        }
    }

    /**
     * CHECKS if the wizard can move or phase to a location.
     * @param   nextLocation     The location to move towards
     * @return  'true' if move is legal, 'false' otherwise
     */
    private boolean wizardCanMove(Location nextLocation) {

        // CASE 1A: Location is NOT a maze WALL
        if (canMove(nextLocation)) {
            setLocation(nextLocation);
            return true;

        // CASE 1B: Location is a maze wall
        } else {

            // STEP 2: Calculate the adjacent wall location to phase into
            Location.CompassDirection compassDir = getLocation().get4CompassDirectionTo(nextLocation);
            Location adjLocation = calcAdjacentLocation(nextLocation, compassDir);
            Color c = getBackground().getColor(adjLocation);

            // STEP 3: Check if the new location is within the board
            if (insideBorder(adjLocation) && !c.equals(Color.gray)) {
                setLocation(adjLocation);
                return true;
            }
        }
        return false;
    }

    /**
     * CHECKS if a location is within the board
     * @param   location  The location to check if it is inside the board
     * @return  'true' if within the board, 'false' otherwise
     */
    private boolean insideBorder(Location location) {
        return location.getX() < numHorzCells && location.getX() >= 0 &&
                location.getY() < numVertCells && location.getY() >= 0;
    }

    /**
     * CALCULATES the adjacent cell of a wall that the wizard can phase into.
     * @param   next        The location of the wall
     * @param   compassDir  The compass direction of the adjacent cell
     * @return
     */
    private Location calcAdjacentLocation(Location next, Location.CompassDirection compassDir) {
        int addX = 0;
        int addY = 0;
        int nextX = next.getX();
        int nextY = next.getY();
        switch (compassDir) {
            case NORTH -> addY = 1;
            case NORTHEAST -> {
                addX = 1;
                addY = 1;
            }
            case EAST -> addX = 1;
            case SOUTHEAST -> {
                addX = 1;
                addY = -1;
            }
            case SOUTH -> addY = -1;
            case SOUTHWEST -> {
                addX = -1;
                addY = -1;
            }
            case WEST -> addX = -1;
            case NORTHWEST -> {
                addX = -1;
                addY = 1;
            }
        }
        return new Location(nextX + addX, nextY + addY);
    }
}
