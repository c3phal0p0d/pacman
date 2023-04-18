package src.items;

import ch.aplu.jgamegrid.Location;
import src.monsters.Monster;
import src.monsters.MonsterManager;

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

    /**
     * Freezes all monster entities, makes them stop moving
     * @param itemManager An instance of the 'ItemManager'
     */
    public void freeze(ItemManager itemManager) {
        itemManager.getGame().getMonsterManager().stopMonsters();
    }
}
