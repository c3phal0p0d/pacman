package src.actor.items;

public enum ItemType {
    Gold,
    Ice,
    Pill;

    /**
     * EXTRACTS the filepath of the item's sprite.
     * @return the item sprite filepath
     */
    public String getImageName() {
        switch (this) {
            case Gold: return "sprites/gold.png";
            case Ice: return "sprites/ice.png";
            case Pill: return "sprites/pill.png";
            default: {
                assert false;
            }
        }
        return null;
    }
}
