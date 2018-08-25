package by.epam.task3.runner;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.task3.model.Bus;
import by.epam.task3.service.Router;

public class Runner {

    public static final int DEPOT_SIZE = 5;
    private static final Logger LOG = LogManager.getLogger(Runner.class);

    public static void main(String[] args) {
        
        Router router = Router.ROUTER;
        
        LOG.info("\nTotal passengers before: " + router.countPassengers() + "\n\n");
        
        List<Bus> busDepot = new ArrayList<>();
        int[] availible = router.getAvailibleRoutes();
        for (int i = 1; i <= DEPOT_SIZE; i ++) {
            int routeId = availible[(int)(Math.random()*availible.length)];
            busDepot.add(new Bus(i, router.getRoute(routeId)));
            LOG.info("Bus #" + i + " goes on the route #" + routeId + "\n");
        }
        
        for (Bus bus : busDepot) {
            bus.start();
        }
        try {
            for (Bus bus : busDepot) {
                bus.join();
            }
        } catch (InterruptedException e) {
            LOG.error("Interrupted exception occured", e);
            Thread.currentThread().interrupt();
        }
        
        LOG.info("\nTotal passengers after: " + router.countPassengers() + "\n");
    }
}
