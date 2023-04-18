package src.monsters;

import ch.aplu.jgamegrid.Location;
import src.Game;

import java.util.ArrayList;

public class Alien extends ShortestDistanceMonster {

    private final Location pacManLocation;
    public Alien(MonsterManager monsterManager) {
        super(monsterManager, MonsterType.Alien);
        pacManLocation = monsterManager.game.pacActor.getLocation();

    }

    protected void walkApproach() {
        walkApproach(pacManLocation);
    }
}
