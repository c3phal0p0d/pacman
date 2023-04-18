package src.items;

import ch.aplu.jgamegrid.Location;
import src.monsters.Monster;
import src.monsters.MonsterManager;
import src.monsters.MonsterType;

import java.awt.*;

public class Gold extends Item {

    protected boolean claimed;

    /**
     * Instantiates a new 'Pill'.
     * @param location the location of the pill on the board
     * @param imageName the filename of the item's sprite
     * @param paintColor the color of the item
     */
    public Gold(ItemManager itemManager, Location location, String imageName, Color paintColor) {
        super(itemManager, location, imageName, paintColor);
    }

    public void infuriate() {
        final int FURIOUS_TIME = 3;
        itemManager.getGame().getMonsterManager().makeFurious(FURIOUS_TIME);
    }

    // Getter and Setter Method
    protected void claim() { this.claimed = true; };

    public boolean isClaimed() { return this.claimed; }
}
