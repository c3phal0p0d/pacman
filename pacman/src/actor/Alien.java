package src.actor;

import ch.aplu.jgamegrid.Location;
import src.utility.GameCallback;

/**
 * Type: New file
 * Team Name: Thursday 11:00am Team 1
 * Team Members:
 *      - Jiachen Si (1085839)
 *      - Natasha Chiorsac (1145264)
 *      - Jude Thaddeau Data (1085613)
 */

public class Alien extends ShortestDistanceMonster {

    // ATTRIBUTES:
    EntityManager entityManager;

    /**
     * INSTANTIATES an instance of 'Alien'.
     * @param gameCallback      Used to display behaviour of the game
     * @param entityManager     An instance of the manager responsible for monster creation
     * @param numHorzCells      The number of HORIZONTAL cells on the board
     * @param numVertCells      The number of VERTICAL cells on the board
     */
    public Alien(GameCallback gameCallback, EntityManager entityManager, int numHorzCells, int numVertCells) {
        super(gameCallback, MonsterType.Alien, numHorzCells, numVertCells);
        this.entityManager = entityManager;
    }

    /**
     * HANDLES the varying ways different monsters navigate the board
     */
    protected void walkApproach() {
        Location pacManLocation =  entityManager.getPacActorLocation();
        walkApproach(pacManLocation);
    }
}
