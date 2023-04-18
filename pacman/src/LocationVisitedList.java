package src;

import ch.aplu.jgamegrid.Location;

import java.util.ArrayList;

public interface LocationVisitedList {

    int limit = 10;
    default boolean isVisited(Location location, ArrayList<Location> visitedList)
    {
        for (Location loc : visitedList)
            if (loc.equals(location))
                return true;
        return false;
    }

    default void addVisitedList(Location location, ArrayList<Location> visitedList) {
        visitedList.add(location);
        if (visitedList.size() == limit)
            visitedList.remove(0);
    }
}
