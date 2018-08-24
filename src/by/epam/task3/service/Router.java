package by.epam.task3.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.task3.model.Route;
import by.epam.task3.model.Station;

public abstract class Router {
    
    private static final Logger LOG = LogManager.getLogger(Router.class);
    
    public static final String STATIONS = "resources/stations.properties";
    public static final String ROUTES = "resources/routes.properties";

    private static Map<Integer,Station> stations;
    
    public static void initialize() {
        Properties prop = PropertiesHandler.readProperties(STATIONS);
        stations = new HashMap<Integer,Station>();
        Iterator<Object> iterator = prop.keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            Integer id = Integer.valueOf(key.replace("station.", ""));
            String name = prop.getProperty(key);
            Station station = new Station(name);
            station.setPassengers((int)(Math.random() * 100 + 1));
            stations.put(id, station);
            LOG.info("Initialized: " + stations.get(id) + "\n");
        }
    }
    
    public static void setRoute(Route route) {
        if (route != null && stations != null) {
            Properties prop = PropertiesHandler.readProperties(ROUTES);
            String[] numbers = prop.getProperty(route.toString()).split(",");
            for (String num : numbers) {
                Station station = stations.get(Integer.valueOf(num));
                if (station != null) {
                    route.getRoute().add(station);
                }
            }
        }
    }
    
    public static int countPassengers() {
    	int sum = -1;
        if (stations != null) {
        	sum = 0;
            for (Integer key : stations.keySet()) {
                Station station = stations.get(key);
                sum += station.getPassengers();
            }
        }
        return sum;
    }
}
