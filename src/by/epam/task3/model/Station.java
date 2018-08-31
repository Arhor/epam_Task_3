/*
 * class: Station
 */

package by.epam.task3.model;

import java.util.concurrent.Exchanger;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class Station represents resource-object "station", it's name, amount of
 * passengers and methods which help to exchange passengers between arrived
 * buses (also just leave and enter the bus)
 * 
 * @version 1.0 25 Aug 2018
 * @author Maxim Burishinets
 */
public class Station {

    private static final Logger LOG = LogManager.getLogger(Station.class);

    private String name;
    private int passengers;
    
    private Semaphore semaphore = new Semaphore(2, true);
    private Exchanger<Integer> ex = new Exchanger<Integer>();
    private Lock locking = new ReentrantLock();
    private Condition condition = locking.newCondition();

    public Station(String name) {
        this.name = name;
    }
    
    /**
     * Method connect invokes by arrived bus, includes "bus-leaving",
     * "passengers exchange" and "bus-entering" stages
     * 
     * @param bus - arrived bus
     */
    public void connect(Bus bus) {
        try {
            semaphore.acquire();
            LOG.info(String.format("Bus #%d arrived to '%s' station",
                    bus.getBusNumber(), name));
            bus.setExchanger(ex);
            int leaving = bus.leaving();
            int swap = -1;
            if (bus.getPassengers() > 0) {
                try {
                    swap = ex.exchange(bus.getPassengers(), 
                            1000,
                            TimeUnit.MILLISECONDS);
                    LOG.info("Before: " + bus.getPassengers() 
                            + " in the bus #" + bus.getBusNumber()
                            + " at station [" + name + "]");
                } catch (InterruptedException e) {
                    LOG.error("Interrupted exception occured", e);
                    Thread.currentThread().interrupt();
                } catch (TimeoutException e) {
                    LOG.debug("There is not bus to swap passengers");
                } finally {
                    if (swap > -1) {
                        bus.setPassengers(swap);
                        LOG.info(" After: " + bus.getPassengers()
                                + " in the bus #" + bus.getBusNumber()
                                + " at station [" + name + "]");
                    }    
                }
            }
            int entering = getEntering(bus);
            bus.entering(entering);
            passengers += leaving;
            LOG.info(String.format("Bus #%d departed from '%s' station:"
                    + " %d left, %d entered, %d swapped",
                    bus.getBusNumber(), name, leaving, entering,
                    (swap == -1 ? 0 : swap)));
        }catch(InterruptedException e) {
            LOG.error("Interrupted exception occured", e);
            Thread.currentThread().interrupt();
        } finally {
            semaphore.release();
        }
    }

    /**
     * Gets amount of passengers which are going to enter the bus according
     * to number of free seats in the bus
     * 
     * @param bus - bus for passengers to enter to
     * @return number of passengers 
     */
    public int getEntering(Bus bus) {
        int freeSeats = bus.getFreeSeats();
        try {
            locking.lock();
            int entering = (int)(Math.random() * passengers + 0.5);
            entering = entering <= freeSeats ? entering : freeSeats;
            passengers -= entering;
            return entering;
        } finally {
            condition.signal();
            locking.unlock();
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public int getPassengers() {
        return passengers;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) { return true; }
        if (obj == null) { return false; }
        if (obj.getClass() != getClass()) { return false; }
        Station station = (Station) obj;
        if (station.passengers != passengers) { return false; }
        if (name == null) {
        	if (station.name != null) {
        		return false;
        	}
        } else if (!name.equals(station.name)) { 
        	return false; }
        return true;
    }
    
    @Override
    public int hashCode() {
        return passengers * 31 + (name == null ? 0 : name.hashCode());
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "@"
                + "name: " + name
                + ", passengers: " + passengers;
    }
}
