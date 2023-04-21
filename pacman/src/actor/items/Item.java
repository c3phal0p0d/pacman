package src.actor.items;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

public class Item extends Actor {

    // ATTRIBUTES:
    private Location location;
    private ItemType type;
    private boolean claimed;

    /**
     * INSTANTIATES an instance of 'Item'.
     * @param location      The coordinates of the item on the grid
     * @param type          The enumeration the item is (i.e. Pill, Gold or Ice)
     */
    public Item(Location location, ItemType type) {
        super(type.getImageName());
        this.location = location;
        this.type = type;
    }

    // GETTER & SETTER methods:
    public Location getLocation() { return this.location; }
    protected void claim() { this.claimed = true; };
    public boolean isClaimed() { return this.claimed; }
    public ItemType getType() { return this.type; }
}
