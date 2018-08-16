package by.epam.task3.model;

import java.util.ArrayList;
import java.util.List;

public class Route {

	private int id;
	private List<Station> route = new ArrayList<Station>();
	
	public Route(int id) {
		setId(id);
	}
	
	public int size() {
		return route.size();
	}
	
	public Station get(int index) {
		return route.get(index);
	}
	
	public List<Station> getRoute() {
		return route;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) { return true; }
		if (obj == null) { return false; }
		if (obj.getClass() != getClass()) { return false; }
		Route rt = (Route)obj;
		if (rt.id != id) { return false; }
		if (route == null) {
			return route == rt.route;
		} else if (route.equals(rt.route)) {
			return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		return (int)(31 * id + (route == null ? 0 : route.hashCode()));
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName()
				+ "@"
				+ "id: " + id;
	}
}
