package by.epam.task3.model;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Bus extends Thread {

    private static final Logger LOG = LogManager.getLogger(Bus.class);
    
    public final int MAX_CAPACITY = 25;
    private int busNumber;
    private int passengers;
    private Route route;
    private Exchanger<Integer> exchanger;
    
    public Bus(int busNumber, Route route) {
        setBusNumber(busNumber);
        setRoute(route);
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
                Thread.currentThread().interrupt();
            }
        }
    }

    // passengers leave the bus
    public int leaving() {
        int leaving = (int)(Math.random() * passengers + 0.5);
        passengers -= leaving;
        return leaving;
    }

    // passengers enter the bus
    public void entering(int entering) {
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

    public void setRoute(Route route) {
        this.route = route;
    }

    public Route getRoute() {
        return route;
    }

    public void setExchanger(Exchanger<Integer> exchanger) {
        this.exchanger = exchanger;
    }
    
    public Exchanger<Integer> getExchanger() {
        return exchanger;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) { return true; }
        if (obj == null) { return false; }
        if (obj.getClass() != getClass()) { return false; }
        Bus bus = (Bus)obj;
        if (bus.busNumber != busNumber) { return false; }
        if (bus.passengers != passengers) { return false; }
        if (bus.MAX_CAPACITY != MAX_CAPACITY) { return false; }
        if (route == null) {
            return route == bus.route;
        } else if (!route.equals(bus.route)) {
            return false;
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        return (passengers + busNumber + MAX_CAPACITY) * 31 
                + (route == null ? 0 : route.hashCode());
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "@"
                + "number: " + busNumber
                + ", passengers: " + passengers
                + ", max capacity: " + MAX_CAPACITY;
    }
}
