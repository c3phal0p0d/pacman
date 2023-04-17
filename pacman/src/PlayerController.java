package src;

import ch.aplu.jgamegrid.GGKeyRepeatListener;
import ch.aplu.jgamegrid.Location;

import java.awt.event.KeyEvent;

public class PlayerController implements GGKeyRepeatListener {
    private PacActor pacActor;

    public PlayerController(PacActor pacActor){
        this.pacActor = pacActor;
    }

    /*
    Control pacman character through keyboard input
     */
    public void keyRepeated(int keyCode)
    {
        // Handles Player Input
        if (pacActor.getAuto()) {
            return;
        }
        if (pacActor.isRemoved())  // Already removed
            return;
        Location next = null;
        switch (keyCode)
        {
            case KeyEvent.VK_LEFT:
                next = pacActor.getLocation().getNeighbourLocation(Location.WEST);
                pacActor.setDirection(Location.WEST);
                break;
            case KeyEvent.VK_UP:
                next = pacActor.getLocation().getNeighbourLocation(Location.NORTH);
                pacActor.setDirection(Location.NORTH);
                break;
            case KeyEvent.VK_RIGHT:
                next = pacActor.getLocation().getNeighbourLocation(Location.EAST);
                pacActor.setDirection(Location.EAST);
                break;
            case KeyEvent.VK_DOWN:
                next = pacActor.getLocation().getNeighbourLocation(Location.SOUTH);
                pacActor.setDirection(Location.SOUTH);
                break;
        }
        if (next != null && pacActor.canMove(next))
        {
            pacActor.setLocation(next);
            pacActor.eatPill(next);
        }
    }
}
