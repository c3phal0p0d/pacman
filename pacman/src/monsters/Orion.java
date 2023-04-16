package src.monsters;

import ch.aplu.jgamegrid.Location;
import src.Game;

import java.util.ArrayList;

public class Orion extends Monster {
    private ArrayList<Location> visitedList = new ArrayList<Location>();
    private final int listLength = 10;
    private boolean stopMoving = false;
    private int seed = 0;

    public Orion(Game game) {
        super(game, MonsterType.Orion);
    }

    public void walkApproach() {

    }
}
