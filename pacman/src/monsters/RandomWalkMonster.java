package src.monsters;

import ch.aplu.jgamegrid.Location;
import src.Game;

import java.util.Random;

public abstract class RandomWalkMonster extends Monster {

    public RandomWalkMonster(MonsterManager monsterManager, MonsterType type) {
        super(monsterManager, type);
    }

    protected Location randomWalk(double oldDirection) {
        // Random walk
        int sign = randomiser.nextDouble() < 0.5 ? 1 : -1;
        setDirection(oldDirection);
        turn(sign * 90);  // Try to turn left/right
        Location next = getNextMoveLocation();
        if (canMove(next))
        {
            setLocation(next);
        }
        else
        {
            setDirection(oldDirection);
            next = getNextMoveLocation();
            if (canMove(next)) // Try to move forward
            {
                setLocation(next);
            }
            else
            {
                setDirection(oldDirection);
                turn(-sign * 90);  // Try to turn right/left
                next = getNextMoveLocation();
                if (canMove(next))
                {
                    setLocation(next);
                }
                else
                {
                    setDirection(oldDirection);
                    turn(180);  // Turn backward
                    next = getNextMoveLocation();
                    setLocation(next);
                }
            }
        }
        return next;
    }

    protected void walkApproach()
    {
        double oldDirection = getDirection();
        randomWalk(oldDirection);
        monsterManager.game.getGameCallback().monsterLocationChanged(this);
    }
}
