package by.epam.task3.runner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.task3.model.Bus;
import by.epam.task3.model.Route;
import by.epam.task3.service.Router;

public class Runner {
	
	private static final Logger LOG = LogManager.getLogger(Runner.class);

	public static void main(String[] args) {
		
		Route one = Route.FIRST;
		Route two = Route.SECOND;
		
		try {
			Router.initialize();
			Router.setRoute(one);
			Router.setRoute(two);
		} catch (IOException e) {
			LOG.error("I/O exception occured: ", e);
			return;
		}
		
		LOG.info("Total before: " + Router.countPassengers() + "\n");
		
		List<Bus> busDepot = new ArrayList<>();
		for (int i = 0; i < 5; i ++) {
			Route toAdd = Math.random() > 0.5 ? one : two;
			busDepot.add(new Bus(i + 1, toAdd));
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
		
		int inBus = 0;
		for (Bus bus : busDepot) {
			inBus += bus.getPassengers();
		}
		
		LOG.info("Total after: " + (Router.countPassengers() + inBus) + "\n");
	}
}
