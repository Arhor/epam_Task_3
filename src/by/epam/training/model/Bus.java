package by.epam.training.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Bus extends Thread {

    private static final Logger LOG = LogManager.getLogger(Bus.class);

    private int busNumber;
    private int passengers;
    private final int MAX_CAPACITY = 25;
    private List<Station> route = new ArrayList<>();
    private Exchanger<Integer> exchanger;
    
    public Bus(int busNumber) {
    	setBusNumber(busNumber);
    }

    @Override
    public void run() {
        int n = route.size();
        for (int i = 0; i < 5; i++) {
            int stationNumber = i % n;
            Station current = route.get(stationNumber);
            current.connect(this);
            try {
                TimeUnit.MILLISECONDS.sleep((int)(Math.random() * 300 + 300)); // on the way...
            } catch (InterruptedException e) {
                LOG.error("Interrupted exception occured", e);
            }
        }
    }

    // passengers leave the bus
    public int exit() {
        int leaving = (int)(Math.random() * passengers + 0.5);
        passengers -= leaving;
        return leaving;
    }

    // passengers enter the bus
    public void enter(int entering) {
        passengers += entering;
    }

    public int getFreeSeats() {
        return MAX_CAPACITY - passengers;
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

    public void setRoute(List<Station> route) {
        this.route = route;
    }

    public List<Station> getRoute() {
        return route;
    }

    public Exchanger<Integer> getExchanger() {
        return exchanger;
    }

    public void setExchanger(Exchanger<Integer> exchanger) {
        this.exchanger = exchanger;
    }
    
    @Override
    public String toString() {
    	return getClass().getSimpleName()
    			+ "@"
    			+ "number: " + getBusNumber()
    			+ ", passengers: " + getPassengers()
    			+ ", max capacity: " + MAX_CAPACITY;
    }
    
    @Override
    public int hashCode() {
    	return (passengers + busNumber + MAX_CAPACITY) * 31 
    			+ (route == null ? 0 : route.hashCode());
    }
}
