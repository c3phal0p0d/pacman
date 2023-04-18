package src.items;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.GGBackground;
import ch.aplu.jgamegrid.Location;
import src.Game;
import src.PacManGameGrid;

import java.awt.*;
import java.util.ArrayList;
import java.util.Properties;

public class ItemManager {
    private ArrayList<Location> pillAndItemLocations = new ArrayList<Location>();
    private ArrayList<Ice> iceCubes = new ArrayList<Ice>();
    private ArrayList<Gold> goldPieces = new ArrayList<Gold>();

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
    public void putPill(GGBackground bg, Location location){
        bg.fillCircle(game.toPoint(location), 5);
    }

    public void putGold(GGBackground bg, Location location){
        bg.setPaintColor(Color.yellow);
        bg.fillCircle(game.toPoint(location), 5);
        Gold gold = new Gold(this, location, "sprites/gold.png", Color.yellow);
        this.goldPieces.add(gold);
        game.addActor(gold, location);
    }

    public void putIce(GGBackground bg, Location location){
        bg.setPaintColor(Color.blue);
        bg.fillCircle(game.toPoint(location), 5);
        Ice ice = new Ice(this, location,"sprites/ice.png", Color.blue);
        this.iceCubes.add(ice);
        game.addActor(ice, location);
    }

    public void removeItem(String type,Location location){
        if(type.equals("gold")){
            for (Gold gold : this.goldPieces){
                if (location.getX() == gold.getLocation().getX() && location.getY() == gold.getLocation().getY()) {
                    gold.hide();
                    if (this.game.getProperties().getProperty("version").equals("multiverse")) {
                        gold.infuriate();
                        gold.claim();
                    }
                }
            }
        } else if(type.equals("ice")){
            for (Ice ice : this.iceCubes){
                if (location.getX() == ice.getLocation().getX() && location.getY() == ice.getLocation().getY()) {
                    ice.hide();
                    if (this.game.getProperties().getProperty("version").equals("multiverse")) {
                        ice.freeze();
                    }
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

}
