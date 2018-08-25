package by.epam.task3.service;

import java.util.ArrayList;
import java.util.List;

import by.epam.task3.model.Station;

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
}