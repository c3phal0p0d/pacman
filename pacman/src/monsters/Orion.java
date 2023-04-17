package src.monsters;

import ch.aplu.jgamegrid.Location;
import src.Game;

import java.util.ArrayList;
import java.util.Properties;

public class Orion extends Monster {

    final private ArrayList<Location> goldLocations;
    public Orion(MonsterManager monsterManager, MonsterType type, ArrayList<Location> goldLocations) {
        super(monsterManager, type);
        this.goldLocations = goldLocations;
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

    }
}
