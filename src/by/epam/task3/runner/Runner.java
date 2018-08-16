package by.epam.task3.runner;

import java.util.ArrayList;
import java.util.List;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.Future;
//import java.util.concurrent.Callable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.task3.model.Bus;
import by.epam.task3.model.Route;
import by.epam.task3.model.Station;

public class Runner {
	
	private static final Logger LOG = LogManager.getLogger(Runner.class);

	public static void main(String[] args) {
		
		Route route = new Route(6);
		
		route.getRoute().add(new Station("Yanki Luchiny"));
		route.getRoute().add(new Station("School N40"));
		route.getRoute().add(new Station("vulica Hashkevicha"));
		route.getRoute().add(new Station("zavulak Chyrzeuskih"));
		route.getRoute().add(new Station("Igumenski trakt"));
		
		List<Bus> busDepot = new ArrayList<>();
		for (int i = 0; i < 5; i ++) {
			busDepot.add(new Bus(i + 1, route));
		}
		
		int before = 0;
		for (Station st : route.getRoute()) {
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
		for (Station st : route.getRoute()) {
			total += st.getPassengers();
		}
		for (Bus bus : busDepot) {
			total += bus.getPassengers();
		}
		LOG.info("Total after: " + total);
	}
}
