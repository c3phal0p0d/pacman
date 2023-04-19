package src.items;

import ch.aplu.jgamegrid.Location;

public class Gold extends Item {

    /**
     * Instantiates a new 'Pill'.
     * @param location the location of the pill on the board
     * @param imageName the filename of the item's sprite
     */
    public Gold(ItemManager itemManager, Location location, ItemType type) {
        super(itemManager, location, type);
    }

    public void infuriate() {
        final int FURIOUS_TIME = 3;
        itemManager.getGame().getMonsterManager().makeFurious(FURIOUS_TIME);
    }

    /**
     * Calms the monster down by resetting it's 'furious' state.
     * @param itemManager An instance of the 'MonsterManager'
     */
    public void soothe(ItemManager itemManager) {
        itemManager.getGame().getMonsterManager().setFuriousState(false);
    }
}
