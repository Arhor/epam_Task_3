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

public class Station {

    private static final Logger LOG = LogManager.getLogger(Station.class);

    private String name;
    private int passengers;
    private Semaphore semaphore = new Semaphore(2, true);
    private Exchanger<Integer> ex = new Exchanger<Integer>();
    private Lock locking = new ReentrantLock();
    private Condition condition = locking.newCondition();

    public Station(String name) {
        setName(name);
        setPassengers((int)(Math.random() * 100 + 1));
    }
    
    public void connect(Bus bus) {
        try {
            semaphore.acquire();
            bus.setExchanger(ex);
            int leaving = bus.exit();
            if (bus.getPassengers() > 0) {
                int swap = -1;
                try {
                    swap = ex.exchange(bus.getPassengers(), 
                            1000,
                            TimeUnit.MILLISECONDS);
                    LOG.info("Before: " + bus.getPassengers() 
                            + " in the bus # " + bus.getBusNumber()
                            + " at station [" + name + "]\n");
                } catch (InterruptedException e) {
                    LOG.error("Interrupted exception occured", e);
                    Thread.currentThread().interrupt();
                } catch (TimeoutException e) {
                    LOG.debug("There is not bus to swap passengers");
                } finally {
                    if (swap > -1) {
                        bus.setPassengers(swap);
                        LOG.info(" After: " + bus.getPassengers()
                                + " in the bus # " + bus.getBusNumber()
                                + " at station [" + name + "]\n");
                    }	
                }
            }
            int freeSeats = bus.getFreeSeats();
            int entering = getEntering(freeSeats);
            bus.enter(entering);
            passengers += leaving;
        }catch(InterruptedException e) {
            LOG.error("Interrupted exception occured", e);;
        } finally {
            semaphore.release();
        }
    }

    public int getEntering(int freeSeats) {
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
    	Station station = (Station)obj;
    	if (station.getPassengers() != passengers) { return false; }
    	if (name == null) {
    		return name == station.getName();
    	} else if (!name.equals(station.getName())) {
    		return false;
    	}
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
                + "name: " + getName()
                + ", passengers: " + getPassengers();
    }
}