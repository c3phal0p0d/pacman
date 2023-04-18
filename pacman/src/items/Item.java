package src.items;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;
import src.PacActor;

import java.awt.*;

public abstract class Item extends Actor {

    private Location location;
    private Color paintColor;

    /**
     * Instantiates a new 'Item'
     * @param location the location of the item on the board
     * @param imageName the filename of the item's sprite
     * @param paintColor the color of the item
     */
    public Item(Location location, String imageName, Color paintColor) {
        super(imageName);
        this.location = location;
        this.paintColor = paintColor;
    }

    // Getter Methods:
    public Location getLocation() { return this.location; }
    public Color getPaintColor() { return this.paintColor; }

    // Setter Methods:
    public void setLocation(Location newLocation) { this.location = newLocation; }
    public void setPaintColor(Color newPaintColor) { this.paintColor = newPaintColor; }
}
