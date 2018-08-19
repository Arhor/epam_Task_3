package by.epam.task3.model;

import java.util.ArrayList;
import java.util.List;

public enum Route {
	
	FIRST, SECOND;

	private List<Station> route = new ArrayList<Station>();
	
	public int size() {
		return route.size();
	}
	
	public Station get(int index) {
		return route.get(index);
	}
	
	public List<Station> getRoute() {
		return route;
	}
}
