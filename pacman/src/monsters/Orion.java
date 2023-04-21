package src.monsters;

import ch.aplu.jgamegrid.Location;
import src.Game;

import java.util.ArrayList;
import src.items.Item;
import src.utility.GameCallback;

public class Orion extends ShortestDistanceMonster {

    final private ArrayList<Item> goldList;

    private ArrayList<Item> toVisitList = new ArrayList<Item>();

    private ArrayList<Item> claimedList = new ArrayList<Item>();
    private ArrayList<Item> unclaimedList = new ArrayList<Item>();
    private Item currentTarget = null; // Stores current gold piece that Orion is going towards

    public Orion(GameCallback gameCallback, int numHorzCells, int numVertCells, ArrayList<Item> goldList) {
        super(gameCallback, MonsterType.Orion, numHorzCells, numVertCells);
        this.goldList = goldList;
    }

    /*
    Orion walks through every gold location by
        1. Randomly selecting one gold piece and walking to it
        2. Repeat step 1 until all gold pieces have been visited
        2. Once all gold pieces have been visited, start over again by randomly selecting any gold locations.

    Even though the gold pieces are eaten by PacMan, Orion still will visit those locations. However, Orion will
    choose to go to locations with gold pieces before other locations where the gold pieces have been eaten by PacMan.

    Once a gold location has been selected, Orion will visit it, even if the gold piece in that location is eaten while
    Orion is on the way there.
     */

    protected void walkApproach() {
        if (currentTarget != null) { // Walking towards target
            Location targetLocation = currentTarget.getLocation();
            walkApproach(targetLocation);
            if (this.getLocation().equals(currentTarget.getLocation())) { // Arrived at target
                currentTarget = null;
            }
        }
        else { // Find new target
            currentTarget = findClosestItem();
        }
        gameCallback.monsterLocationChanged(this);
    }

    private Item findClosestItem() {
        if (toVisitList.isEmpty()) { // All gold pieces visited
            toVisitList.addAll(goldList);
            sortItemPieces();
        }

        int index;
        if (!claimedList.isEmpty()) { // Prioritize unclaimed gold pieces first
            index = randomiser.nextInt(claimedList.size());
            currentTarget = claimedList.get(index);
            claimedList.remove(currentTarget);
        }
        else {
            index = randomiser.nextInt(unclaimedList.size());
            currentTarget = unclaimedList.get(index);
            unclaimedList.remove(currentTarget);
        }
        toVisitList.remove(currentTarget); // Remove from pool of unvisited gold pieces
        return currentTarget;
    }

    /*
    Called once Orion has reached its target location

    Sorts the gold pieces into two lists:
        1. Claimed by pacActor
        2. Unclaimed
     */
    private void sortItemPieces() {

        claimedList.clear();
        unclaimedList.clear();

        for (Item gold: toVisitList) {
            if (gold.isClaimed()) {
                claimedList.add(gold);
            } else {
                unclaimedList.add(gold);
            }
        }
    }
}
