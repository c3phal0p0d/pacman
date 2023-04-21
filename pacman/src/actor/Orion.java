package src.actor;

import ch.aplu.jgamegrid.Location;

import java.util.ArrayList;
import src.actor.items.Item;
import src.utility.GameCallback;

/**
 * Type: New file
 * Team Name: Thursday 11:00am Team 1
 * Team Members:
 *      - Jiachen Si (1085839)
 *      - Natasha Chiorsac (1145264)
 *      - Jude Thaddeau Data (1085613)
 */

public class Orion extends ShortestDistanceMonster {

    // ATTRIBUTES:
    final private ArrayList<Item> goldList;
    private ArrayList<Item> toVisitList = new ArrayList<Item>();
    private ArrayList<Item> claimedList = new ArrayList<Item>();
    private ArrayList<Item> unclaimedList = new ArrayList<Item>();
    private Item currentTarget = null; // Stores current gold piece that Orion is going towards

    /**
     * INSTANTIATES an instance of 'Orion'
     * @param gameCallback  Used to display behaviour of the game
     * @param numHorzCells  The number of HORIZONTAL cells on the board
     * @param numVertCells  The number of VERTICAL cells on the board
     * @param goldList      An array of locations of ALL the gold items
     */
    public Orion(GameCallback gameCallback, int numHorzCells, int numVertCells, ArrayList<Item> goldList) {
        super(gameCallback, MonsterType.Orion, numHorzCells, numVertCells);
        this.goldList = goldList;
    }

    /**
     * HANDLES the specific the walk of 'Orion'.
     *
     *     Orion walks through every gold location by:
     *         1. Randomly selecting one gold piece and walking to it
     *         2. Repeat step 1 until all gold pieces have been visited
     *         2. Once all gold pieces have been visited, start over again by randomly selecting any gold locations.
     */
    protected void walkApproach() {

        // STEP 1: Walk towards a GOLD item
        if (currentTarget != null) {
            Location targetLocation = currentTarget.getLocation();
            walkApproach(targetLocation);

            // STEP 2: Reset to indicate that the NEXT gold item needs to be approached
            if (this.getLocation().equals(currentTarget.getLocation())) { // Arrived at target
                currentTarget = null;
            }
        // STEP 3: Find a new gold piece
        } else { // Find new target
            currentTarget = findClosestItem();
        }
        gameCallback.monsterLocationChanged(this);
    }

    /**
     * CALCULATES the closest GOLD piece.
     * @return  The closest gold piece.
     */
    private Item findClosestItem() {

        // STEP 1: Reset if all gold pieces have been visited
        if (toVisitList.isEmpty()) { // All gold pieces visited
            toVisitList.addAll(goldList);
            sortItemPieces();
        }
        // CASE 2A: Prioritise UNCLAIMED gold pieces first
        int index;
        if (!claimedList.isEmpty()) {
            index = randomiser.nextInt(claimedList.size());
            currentTarget = claimedList.get(index);
            claimedList.remove(currentTarget);

        // CASE 2B: Remove the CLAIMED gold pieces
        } else {
            index = randomiser.nextInt(unclaimedList.size());
            currentTarget = unclaimedList.get(index);
            unclaimedList.remove(currentTarget);
        }
        // STEP 3: Remove from pool of unvisited gold pieces
        toVisitList.remove(currentTarget); // Remove from pool of unvisited gold pieces
        return currentTarget;
    }

    /**
     * HELPER function that sorts gold pieces into a claimed by 'PacActor' & unclaimed list. Used whenever Orion
     * reaches a targeted gold piece.
     */
    private void sortItemPieces() {

        // STEP 1: Reset the claimed & unclaimed lists
        claimedList.clear();
        unclaimedList.clear();

        // STEP 2: Iterate through all visited GOLD pieces & add them to their respective list
        for (Item gold: toVisitList) {
            if (gold.isClaimed()) {
                claimedList.add(gold);
            } else {
                unclaimedList.add(gold);
            }
        }
    }
}