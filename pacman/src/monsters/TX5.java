package src.monsters;

import ch.aplu.jgamegrid.Location;
import src.LocationVisitedList;

import java.util.ArrayList;

public class TX5 extends RandomWalkMonster implements LocationVisitedList {

    public ArrayList<Location> visitedList = new ArrayList<Location>();

    public TX5 (MonsterManager monsterManager) {
        super(monsterManager, MonsterType.TX5);
    }

    public void walkApproach() {

        Location pacLocation = monsterManager.getGame().getPacActor().getLocation();
        double oldDirection = getDirection();
        Location.CompassDirection compassDir = getLocation().get4CompassDirectionTo(pacLocation);
        Location next = getLocation().getNeighbourLocation(compassDir);
        setDirection(compassDir);

        if (!isVisited(next, visitedList) && canMove(next)) {
            setLocation(next);
        } else {
            next = randomWalk(oldDirection);
        }

        monsterManager.game.getGameCallback().monsterLocationChanged(this);
        addVisitedList(next, visitedList);
    }
}
