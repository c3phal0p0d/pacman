package src.items;

import ch.aplu.jgamegrid.Location;
import src.Game;
import src.PacManGameGrid;

import java.util.ArrayList;
import java.util.Properties;

public class ItemManager {

    // ATTRIBUTES:
    private ArrayList<Location> pillAndItemLocations = new ArrayList<Location>();
    private ArrayList<Item> iceCubes = new ArrayList<Item>();
    private ArrayList<Item> goldPieces = new ArrayList<Item>();
    private ArrayList<Item> pills = new ArrayList<Item>();
    private ArrayList<Location> propertyPillLocations = new ArrayList<>();
    private ArrayList<Location> propertyGoldLocations = new ArrayList<>();
    int maxPillsAndItems;

    /**
     * INSTANTIATES an instance of 'ItemManager'.
     */
    public ItemManager() { }

    /**
     * CALCULATES the number of pill & gold items on the boards.
     * @param game  Contains the details about the board
     * @return      The number of consumable items for 'PacActor'
     */
    public int countPillsAndItems(Game game) {

        // STEP 1: Loop through each of the cells on the board
        int pillsAndItemsCount = 0;
        for (int y = 0; y < game.getNumVertCells(); y++)
        {
            for (int x = 0; x < game.getNbHorzCells(); x++)
            {
                // STEP 2: Read the current cell
                Location location = new Location(x, y);
                int a = game.getGrid().getCell(location);

                // CASE 3A: Found a pill
                if (a == 1 && propertyPillLocations.size() == 0) { // Pill
                    pillsAndItemsCount++;

                // CASE 3B: Found gold
                } else if (a == 3 && propertyGoldLocations.size() == 0) { // Gold
                    pillsAndItemsCount++;
                }
            }
        }
        // CASE 4A: Add the number of pills in the count
        if (propertyPillLocations.size() != 0) {
            pillsAndItemsCount += propertyPillLocations.size();
        }
        // CASE 4B: Add the number of gold items in the count
        if (propertyGoldLocations.size() != 0) {
            pillsAndItemsCount += propertyGoldLocations.size();
        }
        // STEP 5: Return the number of consumable items
        return pillsAndItemsCount;
    }

    /**
     * PLACES an instance of a 'Pill' item on the board.
     * @param location  The coordinates of where to place the pill
     * @param game      The instance of the game to add the pill onto
     */
    public void putPill(Location location, Game game){
        Item pill = new Item(this, location, ItemType.Pill);
        pills.add(pill);
        game.addActor(pill, location);
    }

    /**
     * PLACES an instance of a 'Gold' item on the board.
     * @param location  The coordinates of where to place the gold item
     * @param game      The instance of the game to add the gold item onto
     */
    public void putGold(Location location, Game game){
        Item gold = new Item(this, location, ItemType.Gold);
        goldPieces.add(gold);
        game.addActor(gold, location);
    }

    /**
     * PLACES an instance of an 'Ice' item on the board.
     * @param location  The coordinates of where to place the ice item
     * @param game      The instance of the game to add the ice item onto
     */
    public void putIce(Location location, Game game){
        Item ice = new Item(this, location,ItemType.Ice);
        iceCubes.add(ice);
        game.addActor(ice, location);
    }

    /**
     * REMOVES an item from the board after it has been consumed by an instance of 'PacActor'
     * @param type      The 'ItemType' enumeration of the item to be removed
     * @param location  The location of the item to be removed
     */
    public void removeItem(ItemType type, Location location){

        // CASE A: Removing a GOLD item
        if (type.equals(ItemType.Gold)){
            for (Item gold : this.goldPieces){
                if (location.getX() == gold.getLocation().getX() && location.getY() == gold.getLocation().getY()) {
                    gold.hide();
                    gold.claim();
                    return;
                }
            }
        // CASE B: Removing an ICE item
        } else if(type.equals(ItemType.Ice)){
            for (Item ice : this.iceCubes){
                if (location.getX() == ice.getLocation().getX() && location.getY() == ice.getLocation().getY()) {
                    ice.hide();
                    ice.claim();
                    return;
                }
            }
        // CASE C: Removing a PILL
        } else if(type.equals(ItemType.Pill)){
            for (Item pill : this.pills){
                if (location.getX() == pill.getLocation().getX() && location.getY() == pill.getLocation().getY()) {
                    pill.hide();
                    pill.claim();
                    return;
                }
            }
        }
    }

    /**
     * READS the pill & gold locations given to the 'Game' class.
     * @param properties  The properties to be read from
     */
    public void loadPillAndItemsLocations(Properties properties) {

        // STEP 1: Read the pills into 'propertyPillLocations'
        String pillsLocationString = properties.getProperty("Pills.location");
        if (pillsLocationString != null) {
            String[] singlePillLocationStrings = pillsLocationString.split(";");
            for (String singlePillLocationString: singlePillLocationStrings) {
                String[] locationStrings = singlePillLocationString.split(",");
                propertyPillLocations.add(new Location(Integer.parseInt(locationStrings[0]),
                                                                        Integer.parseInt(locationStrings[1])));
            }
        }
        // STEP 2: Read the pills into 'propertyGoldLocations'
        String goldLocationString = properties.getProperty("Gold.location");
        if (goldLocationString != null) {
            String[] singleGoldLocationStrings = goldLocationString.split(";");
            for (String singleGoldLocationString: singleGoldLocationStrings) {
                String[] locationStrings = singleGoldLocationString.split(",");
                propertyGoldLocations.add(new Location(Integer.parseInt(locationStrings[0]),
                                                                        Integer.parseInt(locationStrings[1])));
            }
        }
    }

    /**
     * ADDS the pill, gold & ice locations.
     * @param game  Used to get 'grid' of the game
     */
    public void setupPillAndItemsLocations(Game game) {

        // STEP : Loop through every location
        PacManGameGrid grid = game.getGrid();
        for (int y = 0; y < game.getNumVertCells(); y++)
        {
            for (int x = 0; x < game.getNumHorzCells(); x++)
            {
                // STEP 2: Extract the current location
                Location location = new Location(x, y);
                int a = grid.getCell(location);

                // CASE 3A: Found a PILL
                if (a == 1 && propertyPillLocations.size() == 0) {
                    pillAndItemLocations.add(location);
                }
                // CASE 3B: Found a GOLD item
                if (a == 3 && propertyGoldLocations.size() == 0) {
                    pillAndItemLocations.add(location);
                }
                // CASE 3C: Found an ICE item
                if (a == 4) {
                    pillAndItemLocations.add(location);
                }
            }
        }
        // STEP 4: Add all the PILL locations
        if (propertyPillLocations.size() > 0) {
            pillAndItemLocations.addAll(propertyPillLocations);
        }
        // STEP 5: Add all the GOLD locations
        if (propertyGoldLocations.size() > 0) {
            pillAndItemLocations.addAll(propertyGoldLocations);
        }
    }

    // GETTER & SETTER methods:
    public ArrayList<Location> getPillAndItemLocations() { return pillAndItemLocations; }
    public int getMaxPillsAndItems() { return maxPillsAndItems; }
    public ArrayList<Item> getGoldPieces() { return this.goldPieces; }
    public void setMaxPillsAndItems(int maxPillsAndItems){ this.maxPillsAndItems = maxPillsAndItems; }
    public ArrayList<Location> getPropertyPillLocations() { return propertyPillLocations; }
    public ArrayList<Location> getPropertyGoldLocations() { return propertyGoldLocations; }

    public Item getItemByLocation(Location location) {

        // STEP 1: Check through all items lists to find a match
        ArrayList<Item> items = new ArrayList<Item>();
        items.addAll(iceCubes);
        items.addAll(goldPieces);
        items.addAll(pills);

        // STEP 2: Linear search the item
        for (Item item: items) {

            // CASE 3A: The item was found
            if (item.getLocation().equals(location)) {
                return item;
            }
        }
        // CASE 3B: No item found
        return null;
    }
}