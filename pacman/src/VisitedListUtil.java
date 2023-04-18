package src;

import ch.aplu.jgamegrid.Location;

import java.util.ArrayList;

public class VisitedListUtil {
    public static boolean isVisited(Location location, ArrayList<Location> visitedList)
    {
        for (Location loc : visitedList)
            if (loc.equals(location))
                return true;
        return false;
    }

    public static void addVisitedList(Location location, ArrayList<Location> visitedList, int limit) {
        visitedList.add(location);
        if (visitedList.size() == limit)
            visitedList.remove(0);
    }
}
