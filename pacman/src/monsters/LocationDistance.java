package src.monsters;

import ch.aplu.jgamegrid.Location;

/**
 * @param location Helper class that stores location and distance together
 */
public record LocationDistance(Location location, int distance) {}
