package src.actor;

import ch.aplu.jgamegrid.Location;
import src.utility.GameCallback;

public class Alien extends ShortestDistanceMonster {

   EntityManager entityManager;

    public Alien(GameCallback gameCallback, EntityManager entityManager, int numHorzCells, int numVertCells) {
        super(gameCallback, MonsterType.Alien, numHorzCells, numVertCells);
        this.entityManager = entityManager;
    }

    protected void walkApproach() {
        Location pacManLocation =  entityManager.getPacActorLocation();
        walkApproach(pacManLocation);
    }
}
