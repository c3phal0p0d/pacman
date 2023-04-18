package src.monsters;

import ch.aplu.jgamegrid.Location;
import src.Game;

import java.util.ArrayList;
import src.items.Gold;
import java.util.Properties;

public class Orion extends ShortestDistanceMonster {

    final private ArrayList<Gold> goldList;

    private ArrayList<Gold> toVisitList = new ArrayList<Gold>();

    private ArrayList<Gold> claimedList = new ArrayList<Gold>();
    private ArrayList<Gold> unclaimedList = new ArrayList<Gold>();
    private Gold currentTarget = null; // Stores current gold piece that Orion is going towards

    public Orion(MonsterManager monsterManager, ArrayList<Gold> goldList) {
        super(monsterManager, MonsterType.Orion);
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

    public void walkApproach() {
        if (currentTarget != null) { // Walking towards target
            Location targetLocation = currentTarget.getLocation();
            walkApproach(targetLocation);
            if (this.getLocation().equals(currentTarget.getLocation())) { // Arrived at target
                currentTarget = null;
            }
        }
        else { // Find new target
            currentTarget = findClosestGold();
        }
    }

    private Gold findClosestGold() {
        if (toVisitList.isEmpty()) { // All gold pieces visited
            toVisitList.addAll(goldList);
            sortGoldPieces();
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
    private void sortGoldPieces() {
        for (Gold gold: toVisitList) {
            if (gold.isClaimed()) {
                claimedList.add(gold);
            } else {
                unclaimedList.add(gold);
            }
        }
    }
}
