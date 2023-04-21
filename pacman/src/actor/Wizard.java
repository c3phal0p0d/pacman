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
        //setDirection(0);
        setDirection(newDirection);

        // STEP 2: Check all directions
        for (int i = 0; i < 8; i++) {

            // STEP 3: Check adjacent tiles
            Location nextLocation = getNextMoveLocation();

            // CASE 1A: Location is NOT a maze wall
            if (canMove(nextLocation)) {
                setLocation(nextLocation);
                break;
            }
            // CASE 1B: Location is a maze wall
            else {
                // STEP 2: Calculate the adjacent wall location to phase into
                Location adjLocation = getAdjLocation(nextLocation);

                // STEP 3: Check if the new location is within the board
                if (adjLocation != null) {
                    setLocation(adjLocation);
                    break;
                }
            }

            // STEP 4: Move hasn't been found, check next direction
            turn(45);
        }
        gameCallback.monsterLocationChanged(this);
    }

    private Location getAdjLocation(Location nextLocation) {
        // STEP 1: Calculate the adjacent location to teleport to
        Location.CompassDirection compassDir = getLocation().getCompassDirectionTo(nextLocation);
        Location adjLocation = calcAdjacentLocation(nextLocation, compassDir);
        Color c = getBackground().getColor(adjLocation);

        // STEP 2: Check if the new location is within the board
        if (insideBorder(adjLocation) && !c.equals(Color.gray)) {
            return adjLocation;
        } else {
            return null;
        }
    }

    /**
     * Handles the LOGIC of when 'PacActor' consumes a GOLD item.
     */
    @Override
    protected void furiousWalkApproach() {

        // STEP 1: Move once
        walkApproach();

        // STEP 2: Try to travel in the same direction again
        Location next = getNextMoveLocation();

        // CASE 2A: Location is NOT a maze wall
        if (canMove(next)) {
            setLocation(next);
            gameCallback.monsterLocationChanged(this);
        }
        // CASE 2B: Location is NOT a maze wall
        else {
            Location adj = getAdjLocation(next);
            // CASE 2B-A: Adjacent location is legal
            if (getAdjLocation(next) != null) {
                setLocation(adj);
                gameCallback.monsterLocationChanged(this);
            // CASE 2B-B: Adjacent location is legal
            } else {
                walkApproach();
            }
        }
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

        if(compassDir.equals(Location.CompassDirection.SOUTHEAST)) {
            System.out.println("pog");
        }

        switch (compassDir) {
            case NORTH -> addY = -1;
            case NORTHEAST -> {
                addX = 1;
                addY = -1;
            }
            case EAST -> addX = 1;
            case SOUTHEAST -> {
                addX = 1;
                addY = 1;
            }
            case SOUTH -> addY = 1;
            case SOUTHWEST -> {
                addX = -1;
                addY = 1;
            }
            case WEST -> addX = -1;
            case NORTHWEST -> {
                addX = -1;
                addY = -1;
            }
        }
        return new Location(nextX + addX, nextY + addY);
    }
}
