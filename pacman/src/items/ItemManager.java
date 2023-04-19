package src.items;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.GGBackground;
import ch.aplu.jgamegrid.Location;
import src.Game;
import src.PacManGameGrid;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Properties;

public class ItemManager {
    private ArrayList<Location> pillAndItemLocations = new ArrayList<Location>();

    private ArrayList<Ice> iceCubes = new ArrayList<Ice>();

    private ArrayList<Gold> goldPieces = new ArrayList<Gold>();

    private ArrayList<Pill> pills = new ArrayList<Pill>();

    private ArrayList<Item> itemList = new ArrayList<Item>();

    private ArrayList<Location> propertyPillLocations = new ArrayList<>();

    private ArrayList<Location> propertyGoldLocations = new ArrayList<>();

    private Game game;

    int maxPillsAndItems;

    public ItemManager(Game game) {
        this.game = game;
    }

    public int countPillsAndItems() {
        int pillsAndItemsCount = 0;
        for (int y = 0; y < game.getNumVertCells(); y++)
        {
            for (int x = 0; x < game.getNbHorzCells(); x++)
            {
                Location location = new Location(x, y);
                int a = game.getGrid().getCell(location);
                if (a == 1 && propertyPillLocations.size() == 0) { // Pill
                    pillsAndItemsCount++;
                } else if (a == 3 && propertyGoldLocations.size() == 0) { // Gold
                    pillsAndItemsCount++;
                }
            }
        }
        if (propertyPillLocations.size() != 0) {
            pillsAndItemsCount += propertyPillLocations.size();
        }

        if (propertyGoldLocations.size() != 0) {
            pillsAndItemsCount += propertyGoldLocations.size();
        }

        return pillsAndItemsCount;
    }

    // Item Methods
    public void putPill(Location location){
        Pill pill = new Pill(this, location, ItemType.Pill);
        pills.add(pill);
        itemList.add(pill);
        game.addActor(pill, location);
    }

    public void putGold(Location location){
        Gold gold = new Gold(this, location, ItemType.Gold);
        goldPieces.add(gold);
        itemList.add(gold);
        game.addActor(gold, location);
    }

    public void putIce(Location location){
        Ice ice = new Ice(this, location,ItemType.Ice);
        iceCubes.add(ice);
        itemList.add(ice);
        game.addActor(ice, location);
    }

    public void removeItem(ItemType type, Location location){
        if(type.equals(ItemType.Gold)){
            for (Gold gold : this.goldPieces){
                if (location.getX() == gold.getLocation().getX() && location.getY() == gold.getLocation().getY()) {
                    gold.hide();
                    if (this.game.getProperties().getProperty("version").equals("multiverse")) {
                        gold.infuriate();
                        gold.claim();
                    }
                }
            }
        } else if(type.equals(ItemType.Ice)){
            for (Ice ice : this.iceCubes){
                if (location.getX() == ice.getLocation().getX() && location.getY() == ice.getLocation().getY()) {
                    ice.hide();
                    if (this.game.getProperties().getProperty("version").equals("multiverse")) {
                        ice.freeze();
                    }
                }
            }
        } else if(type.equals(ItemType.Pill)){
            for (Pill pill : this.pills){
                if (location.getX() == pill.getLocation().getX() && location.getY() == pill.getLocation().getY()) {
                    pill.hide();
                }
            }
        }
    }

    public void loadPillAndItemsLocations() {
        String pillsLocationString = game.getProperties().getProperty("Pills.location");
        if (pillsLocationString != null) {
            String[] singlePillLocationStrings = pillsLocationString.split(";");
            for (String singlePillLocationString: singlePillLocationStrings) {
                String[] locationStrings = singlePillLocationString.split(",");
                propertyPillLocations.add(new Location(Integer.parseInt(locationStrings[0]),
                                                                        Integer.parseInt(locationStrings[1])));
            }
        }

        String goldLocationString = game.getProperties().getProperty("Gold.location");
        if (goldLocationString != null) {
            String[] singleGoldLocationStrings = goldLocationString.split(";");
            for (String singleGoldLocationString: singleGoldLocationStrings) {
                String[] locationStrings = singleGoldLocationString.split(",");
                propertyGoldLocations.add(new Location(Integer.parseInt(locationStrings[0]),
                                                                        Integer.parseInt(locationStrings[1])));
            }
        }
    }

    public void setupPillAndItemsLocations() {
        PacManGameGrid grid = game.getGrid();
        for (int y = 0; y < game.getNumVertCells(); y++)
        {
            for (int x = 0; x < game.getNumHorzCells(); x++)
            {
                Location location = new Location(x, y);
                int a = grid.getCell(location);
                if (a == 1 && propertyPillLocations.size() == 0) {
                    pillAndItemLocations.add(location);
                }
                if (a == 3 && propertyGoldLocations.size() == 0) {
                    pillAndItemLocations.add(location);
                }
                if (a == 4) {
                    pillAndItemLocations.add(location);
                }
            }
        }

        if (propertyPillLocations.size() > 0) {
            pillAndItemLocations.addAll(propertyPillLocations);
        }
        if (propertyGoldLocations.size() > 0) {
            pillAndItemLocations.addAll(propertyGoldLocations);
        }
    }

    // Getter & setter methods
    public ArrayList<Location> getPillAndItemLocations() {
        return pillAndItemLocations;
    }

    public int getMaxPillsAndItems() {
        return maxPillsAndItems;
    }

    public Game getGame() {
        return game;
    }

    public ArrayList<Gold> getGoldPieces() { return this.goldPieces; }

    public void setMaxPillsAndItems(int maxPillsAndItems){
        this.maxPillsAndItems = maxPillsAndItems;
    }

    public ArrayList<Location> getPropertyPillLocations() {
        return propertyPillLocations;
    }

    public ArrayList<Location> getPropertyGoldLocations() {
        return propertyGoldLocations;
    }

    public ItemType getItemByLocation(Location location) {
        for (Item item: itemList) {
            if (item.getLocation().equals(location)) {
                return item.getType();
            }
        }
        return null;
    }
}
