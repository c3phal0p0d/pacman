package src.monsters;

import ch.aplu.jgamegrid.Location;
import src.Game;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class TX5 extends RandomWalkMonster {

    public TX5 (MonsterManager monsterManager, MonsterType type) {
        super(monsterManager, type);
    }

    public void walkApproach() {

        Location pacLocation = monsterManager.game.pacActor.getLocation();
        double oldDirection = getDirection();
        Location.CompassDirection compassDir = getLocation().get4CompassDirectionTo(pacLocation);
        Location next = getLocation().getNeighbourLocation(compassDir);
        setDirection(compassDir);

        if (!isVisited(next) && canMove(next)) {
            setLocation(next);
        } else {
            next = randomWalk(oldDirection);
        }

        monsterManager.game.getGameCallback().monsterLocationChanged(this);
        addVisitedList(next);
    }

    public void stopMoving(int seconds) {
        this.stopMoving = true;
        Timer timer = new Timer(); // Instantiate Timer Object
        int SECOND_TO_MILLISECONDS = 1000;
        final Monster monster = this;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                monster.stopMoving = false;
            }
        }, seconds * SECOND_TO_MILLISECONDS);
    }
}
