/*
 * class: Router
 */

package by.epam.task3.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.task3.model.Station;

/**
 * Class Router initializes lists of stations and routes and gets routes by
 * their id, implements thread-safety Singltone
 * 
 * @version 2.0 25 Aug 2018
 * @author Maxim Burishinets
 */
public enum Router {

    ROUTER;

    public static final String STATIONS = "resources/stations.properties";
    public static final String ROUTES = "resources/routes.properties";

    private final Logger LOG = LogManager.getLogger(Router.class);

    private HashMap<Integer, Station> stations;
    private HashMap<Integer, Route> routes;
    
    Router() {
        stations = new HashMap<Integer, Station>();
        routes = new HashMap<Integer, Route>();
        initializeStations();
        initializeRoutes();
    }
    
    /**
     * Initializes map of stations and their IDs according to properties file
     */
    private void initializeStations() {
        Properties prop = PropertiesHandler.readProperties(STATIONS);
        Iterator<Object> iterator = prop.keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            int id = Integer.parseInt(key.replace("station.", ""));
            String name = prop.getProperty(key);
            Station station = new Station(name);
            station.setPassengers((int)(Math.random() * 100 + 1));
            stations.put(id, station);
            LOG.info("Initialized: " + station);
        }
    }
    
    /**
     * Initializes map of routes according to properties file
     */
    private void initializeRoutes() {
        Properties prop = PropertiesHandler.readProperties(ROUTES);
        Iterator<Object> iterator = prop.keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            int routeNumber = Integer.parseInt(key.replace("route.", ""));
            Route route = new Route(routeNumber);
            String[] stationNumbers = prop.getProperty(key).split(",");
            for (String num : stationNumbers) {
                Integer stationId = Integer.valueOf(num);
                route.addStation(stations.get(stationId));
            }
            routes.put(routeNumber, route);
       }
    }
    
    public Route getRoute(int id) {
        return routes.get(id);
    }
    
    /**
     * @return array of available routes's IDs
     */
    public int[] getAvailableRoutes() {
        int[] output = new int[routes.keySet().size()];
        Iterator<Integer> iterator = routes.keySet().iterator();
        for (int i = 0; i < output.length; i++) {
            output[i] = iterator.next();
        }
        return output;
    }
    
    /**
     * @return total amount of passengers on available stations
     */
    public int countPassengers() {
        int sum = -1;
        if (stations != null) {
            sum = 0;
            for (Integer key : stations.keySet()) {
                Station station = stations.get(key);
                sum += station.getPassengers();
            }
            LOG.info("\nTotal passengers: " + sum + "\n\n");
        }
        return sum;
    }
}
