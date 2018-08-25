/*
 * class: Route
 */

package by.epam.task3.service;

import java.util.ArrayList;
import java.util.List;

import by.epam.task3.model.Station;

/**
 * 
 * 
 * @version 2.0 25 Aug 2018
 * @author Maxim Burishinets
 */
public class Route {

    private int id;
    private List<Station> route = new ArrayList<Station>();
    
    Route(int id) {
        this.id = id;
    }
    
    public int size() {
        return route.size();
    }
    
    public Station get(int index) {
        return route.get(index);
    }
    
    public void addStation(Station station) {
        route.add(station);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "@"
                + "id: " + id
                + "stations: " + route;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this) { return true; }
        if (obj == null) { return false; }
        if (obj.getClass() != getClass()) { return false; }
        Route rt = (Route) obj;
        if (rt.id != id) { return false; }
        if (route == null) {
            if (!route.equals(rt.route)) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        return id * 31 + route.hashCode();
    }
}