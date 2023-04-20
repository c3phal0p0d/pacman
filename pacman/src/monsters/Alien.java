package src.monsters;

import ch.aplu.jgamegrid.Location;
import src.Game;
import src.utility.GameCallback;

import java.util.ArrayList;

public class Alien extends ShortestDistanceMonster {

   MonsterManager monsterManager;

    public Alien(GameCallback gameCallback, MonsterManager monsterManager, int numHorzCells, int numVertCells) {
        super(gameCallback, MonsterType.Alien, numHorzCells, numVertCells);
        this.monsterManager = monsterManager;
    }

    protected void walkApproach() {
        Location pacManLocation =  monsterManager.getPacActorLocation();
        walkApproach(pacManLocation);
    }
}
