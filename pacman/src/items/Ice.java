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
    public Ice(ItemManager itemManager, Location location, String imageName, Color paintColor) {
        super(itemManager, location, imageName, paintColor);
    }

    public void freeze() {
        final int ICE_FREEZE_TIME = 3;
        itemManager.getGame().getMonsterManager().freezeMonsters(ICE_FREEZE_TIME);
    }

}
