package src.monsters;

import src.utility.GameCallback;

public class Troll extends RandomWalkMonster{
    public Troll(GameCallback gameCallback, int numHorzCells, int numVertCells) {
        super(gameCallback, MonsterType.Troll, numHorzCells, numVertCells);
    }
}