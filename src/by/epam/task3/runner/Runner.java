package by.epam.task3.runner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.task3.model.Bus;
import by.epam.task3.model.Route;
import by.epam.task3.model.Station;
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
		
		List<Bus> busDepot = new ArrayList<>();
		for (int i = 0; i < 5; i ++) {
			Route toAdd = Math.random() > 0.5 ? one : two;
			busDepot.add(new Bus(i + 1, toAdd));
		}
		
		int before = 0;
		for (Station st : one.getRoute()) {
			before += st.getPassengers();
		}
		LOG.info("Total before: " + before + "\n");
		
		for (Bus bus : busDepot) {
			bus.start();
		}
		try {
			for (Bus bus : busDepot) {
				bus.join();
			}
		} catch (InterruptedException e) {
			LOG.error("Interrupted exception occured", e);
		}
		
		int total = 0;
		for (Station st : one.getRoute()) {
			total += st.getPassengers();
		}
		for (Bus bus : busDepot) {
			total += bus.getPassengers();
		}
		LOG.info("Total after: " + total + "\n");
	}
}
