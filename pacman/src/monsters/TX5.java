package src.monsters;

import ch.aplu.jgamegrid.Location;
import src.Game;
import src.VisitedListUtil;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class TX5 extends RandomWalkMonster {

    private final int listLength = 10;

    public ArrayList<Location> visitedList = new ArrayList<Location>();

    public TX5 (MonsterManager monsterManager) {
        super(monsterManager, MonsterType.TX5);
    }

    public void walkApproach() {

        Location pacLocation = monsterManager.game.pacActor.getLocation();
        double oldDirection = getDirection();
        Location.CompassDirection compassDir = getLocation().get4CompassDirectionTo(pacLocation);
        Location next = getLocation().getNeighbourLocation(compassDir);
        setDirection(compassDir);

        if (!VisitedListUtil.isVisited(next, visitedList) && canMove(next)) {
            setLocation(next);
        } else {
            next = randomWalk(oldDirection);
        }

        monsterManager.game.getGameCallback().monsterLocationChanged(this);
        VisitedListUtil.addVisitedList(next, visitedList, listLength);
    }
}
