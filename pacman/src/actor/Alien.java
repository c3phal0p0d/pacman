package src.actor;

import ch.aplu.jgamegrid.Location;
import src.utility.GameCallback;

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
