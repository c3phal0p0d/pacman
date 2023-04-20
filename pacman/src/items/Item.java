package src.items;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;
import src.PacActor;

import java.awt.*;

public class Item extends Actor {

    // ATTRIBUTES:
    private Location location;
    private ItemType type;
    protected ItemManager itemManager;
    private boolean claimed;

    /**
     * INSTANTIATES an instance of 'Item'.
     * @param itemManager   The manager responsible for item creation, placement & removal
     * @param location      The coordinates of the item on the grid
     * @param type          The enumeration the item is (i.e. Pill, Gold or Ice)
     */
    public Item(ItemManager itemManager, Location location, ItemType type) {
        super(type.getImageName());
        this.location = location;
        this.itemManager = itemManager;
        this.type = type;
    }

    // GETTER & SETTER methods:
    public Location getLocation() { return this.location; }
    protected void claim() { this.claimed = true; };
    public boolean isClaimed() { return this.claimed; }\
    public ItemType getType() { return this.type; }
}
