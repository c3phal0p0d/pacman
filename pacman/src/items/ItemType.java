package src.items;

public enum ItemType {
    Gold,
    Ice,
    Pill;

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
