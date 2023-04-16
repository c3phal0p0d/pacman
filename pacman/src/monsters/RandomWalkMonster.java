package src.monsters;

import ch.aplu.jgamegrid.Location;
import src.Game;

import java.util.Random;

public class RandomWalkMonster extends Monster {

    public RandomWalkMonster(Game game, MonsterType type) {
        super(game, type);
    }

    protected Location randomWalk(double oldDirection) {
        // Random walk
        int sign = randomiser.nextDouble() < 0.5 ? 1 : -1;
        System.out.println(sign);
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
        game.getGameCallback().monsterLocationChanged(this);
    }
}
