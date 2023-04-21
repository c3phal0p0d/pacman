package src.monsters;

import ch.aplu.jgamegrid.Location;
import src.LocationVisitedList;
import src.utility.GameCallback;

import java.util.ArrayList;

public class TX5 extends RandomWalkMonster implements LocationVisitedList {

    private ArrayList<Location> visitedList = new ArrayList<Location>();

    private MonsterManager monsterManager;

    public TX5 (GameCallback gameCallback, MonsterManager monsterManager, int numHorzCells, int numVertCells) {
        super(gameCallback, MonsterType.TX5, numHorzCells, numVertCells);
        this.monsterManager = monsterManager;
    }

    protected void walkApproach() {
        Location pacLocation = monsterManager.getPacActorLocation();
        double oldDirection = getDirection();
        Location.CompassDirection compassDir = getLocation().get4CompassDirectionTo(pacLocation);
        Location next = getLocation().getNeighbourLocation(compassDir);
        setDirection(compassDir);

        if (!isVisited(next, visitedList) && canMove(next)) {
            setLocation(next);
        } else {
            next = randomWalk(oldDirection);
        }

        gameCallback.monsterLocationChanged(this);
        addVisitedList(next, visitedList);
    }
}
