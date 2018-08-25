package by.epam.task3.model;

import java.util.ArrayList;
import java.util.List;

public class Route {

	private int id;
    private List<Station> route = new ArrayList<Station>();
    
    public Route(int id) {
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