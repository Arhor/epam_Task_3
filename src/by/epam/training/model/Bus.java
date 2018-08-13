package by.epam.training.model;

import java.util.ArrayList;
import java.util.List;

public class Bus extends Thread {
	
	private int busNumber;
	private int passengers;
	private int maxCapacity = 25;
	private List<Station> route = new ArrayList<>();

	@Override
	public void run() {
		int n = route.size();
		for (int i = 0; i < 25; i++) {
			int station_number = i % n;
			Station current = route.get(station_number);
			
			int leaving = (int)(Math.random() * getPassengers() + 0.5);
			passengers -= leaving;
			int entering = current.exchange(leaving, maxCapacity - passengers, this);
			passengers += entering;
		}
	}

	public void setBusNumber(int busNumber) {
		this.busNumber = busNumber;
	}
	
	public int getBusNumber() {
		return busNumber;
	}
	
	public void setPassengers(int passengers) {
		this.passengers = passengers;
	}
	
	public int getPassengers() {
		return passengers;
	}
	
	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}
	
	public int getMaxCapacity() {
		return maxCapacity;
	}

	public void setRoute(List<Station> route) {
		this.route = route;
	}
	
	public List<Station> getRoute() {
		return route;
	}
}
