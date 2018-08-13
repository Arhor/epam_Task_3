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
		for (int i = 0; i < 5; i++) {
			int station_number = i % n;
			Station current = route.get(station_number);
			
//			System.out.printf("Bus: %d, station: %s%n", busNumber, current.getName());
//			System.out.printf("before: station - %d, bus - %d%n", current.getWaiting(), passengers);
			
			// выход/выход пассажиров
			int leaving = (int)(Math.random() * getPassengers() + 0.5);
			passengers -= leaving;
			int entering = current.exchange(leaving, maxCapacity - passengers, busNumber);
			passengers += entering;

//			System.out.printf(" after: station - %d, bus - %d%n", current.getWaiting(), passengers);
//			System.out.printf("leaved - %d, entered - %d%n%n", leaving, entering);
			
			try {
				int smoking = (int)(Math.random() * 500 + 0.5);
				Thread.sleep(smoking);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
