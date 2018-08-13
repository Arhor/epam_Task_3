package by.epam.training.model;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Station {
	
	private String name;
	private int waiting;
	private Lock locking = new ReentrantLock();
	private Condition isFree = locking.newCondition();
	
	//TODO: реализовать способ принимать сразу несколько потоков (ограниченное кол-во)
	
	public int exchange(int leaving, int freeSeats, int busNumber) {
		try {
			locking.lock();
			System.out.printf("Station %s is locked by bus N %d%n", name, busNumber);
			
			int passengers = 25 - freeSeats + leaving;
			
			System.out.printf("\tbefore: station - %d, bus - %d%n", waiting, passengers);
			
			int entering = (int)(Math.random() * waiting + 0.5);
			entering = entering > freeSeats ? freeSeats : entering;
			waiting += leaving - entering;
			
			System.out.printf("\t after: station - %d, bus - %d%n", waiting, passengers - leaving + entering); // try
			System.out.printf("\tleaved - %d, entered - %d%n", leaving, entering);
			
			System.out.printf("Station %s is unlocked by bus N %d%n%n", name, busNumber);
			return entering;
		} finally {
			isFree.signal();
			locking.unlock();
		}
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
