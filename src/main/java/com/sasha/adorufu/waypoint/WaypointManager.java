package com.sasha.adorufu.waypoint;

import com.sasha.adorufu.AdorufuMod;

import java.io.IOException;
import java.util.ArrayList;

public class WaypointManager {

    private ArrayList<Waypoint> waypoints = new ArrayList<>();

    public WaypointManager() {
        try {
            waypoints = AdorufuMod.DATA_MANAGER.loadWaypoints();
        } catch (IOException e) {
            e.printStackTrace();
            AdorufuMod.logErr(true, "Failed to load waypoints from files.");
        }
    }
    public ArrayList<Waypoint> getWaypoints() {
        return waypoints;
    }
    public void addWaypoint(Waypoint waypoint, boolean save) {
        waypoints.add(waypoint);
        if (!save) return;
        try {
            AdorufuMod.DATA_MANAGER.saveWaypoint(waypoint, false);
        } catch (IOException e) {
            e.printStackTrace();
            AdorufuMod.logErr(false, "Couldn't save waypoint to file!");
        }
    }
    public void delWaypoint(String waypoint) {
        Waypoint toDelete = null;
        for (Waypoint waypoint1 : waypoints) {
            if (waypoint1.getName().equalsIgnoreCase(waypoint)) {
                toDelete = waypoint1;
                break;
            }
        }
        if (toDelete == null) return;
        waypoints.remove(toDelete);
        try {
            AdorufuMod.DATA_MANAGER.saveWaypoint(toDelete, true);
        } catch (IOException e) {
            e.printStackTrace();
            AdorufuMod.logErr(false, "Couldn't delete waypoint file on disk!");
        }
    }
    public boolean sameName(String name) {
        for (Waypoint waypoint : waypoints) {
            if (waypoint.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
