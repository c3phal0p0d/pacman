package src.items;

import ch.aplu.jgamegrid.Location;

import java.awt.*;

public class Pill extends Item {

    /**
     * Instantiates a new 'Pill'.
     * @param location the location of the pill on the board
     * @param imageName the filename of the item's sprite
     * @param paintColor the color of the item
     */
    public Pill(ItemManager itemManager, Location location, String imageName, Color paintColor) {
        super(itemManager, location, imageName, paintColor);
    }
}
