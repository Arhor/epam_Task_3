package by.epam.task3.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import by.epam.task3.model.Route;
import by.epam.task3.model.Station;

public abstract class Router {

	private static Map<Integer,Station> stations;
	
	public static void initialize() throws IOException {
		Properties prop = PropertiesHandler.readProperties("resources/stations.properties");
		stations = new HashMap<Integer,Station>();
		Iterator<Object> iterator = prop.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			Integer id = Integer.valueOf(key.replace("station.", ""));
			String name = prop.getProperty(key);
			stations.put(id, new Station(name));
		}
	}
	
	public static void setRoute(Route route) throws IOException {
		if (route != null && stations != null) {
			Properties prop = PropertiesHandler.readProperties("resources/routes.properties");
			String[] numbers = prop.getProperty(route.toString()).split(",");
			for (String num : numbers) {
				Station station = stations.get(Integer.valueOf(num));
				if (station != null) {
					route.getRoute().add(station);
				}
			}
		}
	}
}
