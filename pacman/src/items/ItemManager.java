package src.items;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.GGBackground;
import ch.aplu.jgamegrid.Location;
import src.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.Properties;

public class ItemManager {
    private ArrayList<Location> pillAndItemLocations = new ArrayList<Location>();
    private ArrayList<Item> iceCubes = new ArrayList<Item>();
    private ArrayList<Item> goldPieces = new ArrayList<Item>();

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
        Gold gold = new Gold(location, "sprites/gold.png", Color.yellow);
        this.goldPieces.add(gold);
        game.addActor(gold, location);
    }

    public void putIce(GGBackground bg, Location location){
        bg.setPaintColor(Color.blue);
        bg.fillCircle(game.toPoint(location), 5);
        Ice ice = new Ice(location,"sprites/ice.png", Color.blue);
        this.iceCubes.add(ice);
        game.addActor(ice, location);
    }

    public void removeItem(String type,Location location){
        if(type.equals("gold")){
            for (Actor item : this.goldPieces){
                if (location.getX() == item.getLocation().getX() && location.getY() == item.getLocation().getY()) {
                    item.hide();
                    if (this.game.getProperties().getProperty("version").equals("multiverse")) {
                        ((Gold) item).infuriate(this);
                    }
                }
            }
        } else if(type.equals("ice")){
            for (Item item : this.iceCubes){
                if (location.getX() == item.getLocation().getX() && location.getY() == item.getLocation().getY()) {
                    item.hide();
                    if (this.game.getProperties().getProperty("version").equals("multiverse")) {
                        ((Ice) item).freeze(this);
                    }
                }
            }
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
