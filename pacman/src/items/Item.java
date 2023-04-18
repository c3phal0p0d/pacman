package src.items;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;
import src.PacActor;

import java.awt.*;

public abstract class Item extends Actor {

    private Location location;
    private Color paintColor;

    protected ItemManager itemManager;

    /**
     * Instantiates a new 'Item'
     * @param location the location of the item on the board
     * @param imageName the filename of the item's sprite
     * @param paintColor the color of the item
     */
    public Item(ItemManager itemManager, Location location, String imageName, Color paintColor) {
        super(imageName);
        this.location = location;
        this.paintColor = paintColor;
        this.itemManager = itemManager;
    }

    // Getter and Setter Methods:
    public Location getLocation() { return this.location; }

    public void setLocation(Location newLocation) { this.location = newLocation; }
}
