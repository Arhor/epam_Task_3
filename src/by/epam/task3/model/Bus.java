/*
 * class: Bus
 */

package by.epam.task3.model;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.task3.service.Route;

/**
 * Class Bus represents thread-object "bus", it's number, amount of passengers,
 * route and methods of going through this route
 * 
 * @version 1.0 25 Aug 2018
 * @author Maxim Burishinets
 */
public class Bus extends Thread {

    public final int MAX_CAPACITY = 25;

    private static final Logger LOG = LogManager.getLogger(Bus.class);
    
    private int busNumber;
    private int passengers;
    private boolean toDepot;
    private Route route;
    
    private Exchanger<Integer> exchanger;
    
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

    /**
     * Returns randomly generated number of passengers leaving the bus
     * 
     * @return number of passengers leaving the bus
     */
    public int leaving() {
        int leaving = toDepot ? passengers 
                              : (int)(Math.random() * passengers + 0.5);
        passengers -= leaving;
        return leaving;
    }

    /**
     * Accepts number of passengers to add to the bus
     * 
     * @param entering - number of passengers entering the bus
     */
    public void entering(int entering) {
        passengers += entering;
    }

    /**
     * Returns the number of available seats in the bus according to whether
     * the bus goes to the depot or not
     * 
     * @return number of free seats in the bus
     */
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
