package src.monsters;

import ch.aplu.jgamegrid.Location;
import src.Game;

import java.util.ArrayList;

public class Alien extends ShortestDistanceMonster {

    public Alien(MonsterManager monsterManager) {
        super(monsterManager, MonsterType.Alien);
    }

    protected void walkApproach() {
        Location pacManLocation = monsterManager.game.pacActor.getLocation();
        walkApproach(pacManLocation);
    }
}
