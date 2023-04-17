package src.items;

import ch.aplu.jgamegrid.Location;
import src.monsters.Monster;
import src.monsters.MonsterManager;
import src.monsters.MonsterType;

import java.awt.*;

public class Gold extends Item {

    /**
     * Instantiates a new 'Pill'.
     * @param location the location of the pill on the board
     * @param imageName the filename of the item's sprite
     * @param paintColor the color of the item
     */
    public Gold(Location location, String imageName, Color paintColor) {
        super(location, imageName, paintColor);
    }

    /**
     * Sets the monsters into a 'furious' state.
     * @param itemManager An instance of the 'MonsterManager'
     */
    public void infurate(ItemManager itemManager) {
        for (Monster monster: itemManager.getGame().getMonsterManager().getMonsters()) {
            monster.setFurious(true);
        }
    }

    /**
     * Calms the monster down by resetting it's 'furious' state.
     * @param itemManager An instance of the 'MonsterManager'
     */
    public void soothe(ItemManager itemManager) {
        for (Monster monster: itemManager.getGame().getMonsterManager().getMonsters()) {
            monster.setFurious(false);
        }
    }
}
