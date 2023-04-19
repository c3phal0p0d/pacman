package src.items;

import ch.aplu.jgamegrid.Location;

public class Ice extends Item {

    /**
     * Instantiates a new 'Ice'.
     * @param location the location of the ice on the board
     * @param imageName the filename of the ice's sprite
     */
    public Ice(ItemManager itemManager, Location location, ItemType type) {
        super(itemManager, location, type);
    }

    public void freeze() {
        final int ICE_FREEZE_TIME = 3;
        itemManager.getGame().getMonsterManager().freezeMonsters(ICE_FREEZE_TIME);
    }

}
