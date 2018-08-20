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
    public void delWaypoint(Waypoint waypoint) {
        waypoints.add(waypoint);
        try {
            AdorufuMod.DATA_MANAGER.saveWaypoint(waypoint, true);
        } catch (IOException e) {
            e.printStackTrace();
            AdorufuMod.logErr(false, "Couldn't delete waypoint file on disk!");
        }
    }
}
