package by.epam.task3.model;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.task3.service.Route;

public class Bus extends Thread {

    private static final Logger LOG = LogManager.getLogger(Bus.class);
    
    private int busNumber;
    private int passengers;
    private boolean toDepot;
    private Route route;
    
    private Exchanger<Integer> exchanger;
    
    public final int MAX_CAPACITY = 25;
    
    public Bus(int busNumber, Route route) {
        this.busNumber = busNumber;
        this.route = route;
    }

    @Override
    public void run() {
        for (int i = 0; i < route.size(); i++) {
            if (i == route.size() - 1) {
                toDepot = true;
                LOG.info("\nBus #"+ busNumber + " is moving to depot...\n");
            }
            Station current = route.get(i);
            current.connect(this);
            try {
                TimeUnit.MILLISECONDS.sleep((int)(Math.random() * 300 + 300));
            } catch (InterruptedException e) {
                LOG.error("Interrupted exception occured", e);
                Thread.currentThread().interrupt();
            }
        }
    }

    // passengers leave the bus
    public int leaving() {
        int leaving = toDepot ? passengers 
                              : (int)(Math.random() * passengers + 0.5);
        passengers -= leaving;
        return leaving;
    }

    // passengers enter the bus
    public void entering(int entering) {
        passengers += entering;
    }

    public int getFreeSeats() {
        return toDepot ? 0 : MAX_CAPACITY - passengers;
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
    
    public boolean isToDepot() {
        return toDepot;
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
        Bus bus = (Bus) obj;
        if (bus.busNumber != busNumber) { return false; }
        if (bus.passengers != passengers) { return false; }
        if (bus.MAX_CAPACITY != MAX_CAPACITY) { return false; }
        if (route == null) {
            if (!route.equals(bus.route)) {
                return false;
            }
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
