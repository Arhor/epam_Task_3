package by.epam.training.model;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Station {
	
	private String name;
	private int waiting;
	
	private final Semaphore semaphore = new Semaphore(2, true);
	
	public int exchange(int leaving, int freeSeats, Bus bus) {
		int entering = 0;
		try {
			semaphore.acquire();
			
			int passengers = 25 - freeSeats + leaving;
			
			System.out.printf("Bus N %d arrived to station '%s' (bus: %d, station: %d)%n%n", bus.getBusNumber(), name, passengers, waiting);
			
			entering = (int)(Math.random() * waiting + 0.5);
			entering = entering > freeSeats ? freeSeats : entering;
			waiting += leaving - entering;
			
			TimeUnit.MILLISECONDS.sleep((int)(Math.random() * 1500 + 0.5));
			
			System.out.printf("Bus N %d departed from station '%s' (bus: %d, station %d, leaved %d, enetered %d)%n%n", bus.getBusNumber(), name, passengers - leaving + entering, waiting, leaving, entering);
			
		}catch(InterruptedException e) {
			e.printStackTrace();
		} finally {
			semaphore.release();
		}
		return entering;
	}
	
	public Station(String name) {
		setName(name);
		setWaiting((int)(Math.random() * 100 + 1));
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setWaiting(int waiting) {
		this.waiting = waiting;
	}
	
	public int getWaiting() {
		return waiting;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName()
				+ "@"
				+ getName() + ", "
				+ getWaiting();
	}
}
