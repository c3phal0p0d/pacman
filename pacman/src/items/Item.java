package src.items;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;
import src.PacActor;

import java.awt.*;

public abstract class Item extends Actor {

    private Location location;

    private ItemType type;

    protected ItemManager itemManager;

    protected boolean claimed;

    /**
     * Instantiates a new 'Item'
     * @param location the location of the item on the board
     * @param imageName the filename of the item's sprite
     */
    public Item(ItemManager itemManager, Location location, ItemType type) {
        super(type.getImageName());
        this.location = location;
        this.itemManager = itemManager;
        this.type = type;
    }

    // Getter and Setter Methods:
    public Location getLocation() { return this.location; }

    public void setLocation(Location newLocation) { this.location = newLocation; }
    // Getter and Setter Method
    protected void claim() { this.claimed = true; };

    public boolean isClaimed() { return this.claimed; }

    public ItemType getType() { return this.type; }
}
