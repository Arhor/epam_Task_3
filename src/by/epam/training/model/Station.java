package by.epam.training.model;

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
    private int waiting;
    private final Semaphore semaphore = new Semaphore(2, true);
    private final Exchanger<Integer> ex = new Exchanger<Integer>();
    private final Lock locking = new ReentrantLock();
    private final Condition condition = locking.newCondition();

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
                } catch (TimeoutException e) {
                    LOG.debug("There is not bus to swap passengers", e);
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
            waiting += leaving;

        }catch(InterruptedException e) {
            LOG.error("Interrupted exception occured", e);;
        } finally {
            semaphore.release();
        }
    }

    public Station(String name) {
        setName(name);
        setWaiting((int)(Math.random() * 100 + 1));
    }

    public int getEntering(int freeSeats) {
        try {
            locking.lock();
            int entering = (int)(Math.random() * waiting + 0.5);
            entering = entering <= freeSeats ? entering : freeSeats;
            waiting -= entering;
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
