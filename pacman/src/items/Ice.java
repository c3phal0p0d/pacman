package src.items;

import ch.aplu.jgamegrid.Location;

import java.awt.*;

public class Ice extends Item {

    /**
     * Instantiates a new 'Ice'.
     * @param location the location of the ice on the board
     * @param imageName the filename of the ice's sprite
     * @param paintColor the color of the ice
     */
    public Ice(Location location, String imageName, Color paintColor) {
        super(location, imageName, paintColor);
    }
}
