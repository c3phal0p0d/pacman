package src.monsters;

import ch.aplu.jgamegrid.Location;
import src.Game;
import src.utility.GameCallback;

import java.awt.*;
import java.util.ArrayList;

public class Wizard extends RandomWalkMonster {

    public Wizard(GameCallback gameCallback, int numHorzCells, int numVertCells) {
        super(gameCallback, MonsterType.Wizard, numHorzCells, numVertCells);
    }

    /*
    Wizard randomly select one of its neighbour locations (8 cells around the Wizard).
        - If location is not a maze wall, move to that location.
        - If location is a maze wall, check if adjacent location in same direction as selected location
          is a wall or not:
            - If wall, choose another location
            - If not wall, walk through wall to adjacent location.
     */
    protected void walkApproach() {

        // Randomly choose starting direction to search in
        int upperbound = 7;
        int dirMultiple = randomiser.nextInt(upperbound);
        int newDirection = dirMultiple * 45;
        turn(newDirection);

        // Check all directions
        for (int i = 0; i < 8; i++) {

            // Check adjacent tiles
            Location nextLocation = getNextMoveLocation();
            if (wizardCanMove(nextLocation)) {
                break;
            }

            // Move hasn't been found, check next direction
            turn(45);
        }
    }

    @Override
    protected void furiousWalkApproach() {

        // Move once
        walkApproach();

        // Try to travel in the same direction again
        Location next = getNextMoveLocation();
        if (wizardCanMove(next)) {
            setLocation(next);
        }
        else {
            walkApproach();
        }
    }

    private boolean wizardCanMove(Location nextLocation) {
        if (canMove(nextLocation)) { // Location is not a maze wall
            setLocation(nextLocation);
            return true;
        } else { // Location is a maze wall
            Location.CompassDirection compassDir = getLocation().get4CompassDirectionTo(nextLocation);
            Location adjLocation = calcAdjacentLocation(nextLocation, compassDir);
            Color c = getBackground().getColor(adjLocation);
            if (insideBorder(adjLocation) && !c.equals(Color.gray)) { // Inside border and not maze wall
                setLocation(adjLocation);
                return true;
            }
        }
        return false;
    }

    private boolean insideBorder(Location location) {
        // Location is outside the grid border
        return location.getX() < numHorzCells && location.getX() >= 0 &&
                location.getY() < numVertCells && location.getY() >= 0;
    }

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
