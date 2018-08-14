package by.epam.training.runner;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.training.model.Bus;
import by.epam.training.model.Station;

public class Runner {
	
	private static final Logger LOG = LogManager.getLogger(Runner.class);

	public static void main(String[] args) {
		//only for test purpose
		
		List<Station> route = new ArrayList<>();
		
		route.add(new Station("Yanki Luchiny"));
		route.add(new Station("School N40"));
		route.add(new Station("vulica Hashkevicha"));
		route.add(new Station("zavulak Chyrzeuskih"));
		route.add(new Station("Igumenski trakt"));
		
		Bus bus_1 = new Bus();
		Bus bus_2 = new Bus();
		Bus bus_3 = new Bus();
		Bus bus_4 = new Bus();
		Bus bus_5 = new Bus();
		Bus bus_6 = new Bus();
		Bus bus_7 = new Bus();
		
		int before = 0;
		
		for (Station st : route) {
			before += st.getWaiting();
		}
		
		LOG.info("Total before: " + before + "\n");
		
		bus_1.setRoute(route);
		bus_2.setRoute(route);
		bus_3.setRoute(route);
		bus_4.setRoute(route);
		bus_5.setRoute(route);
		bus_6.setRoute(route);
		bus_7.setRoute(route);
		
		bus_1.setBusNumber(1);
		bus_2.setBusNumber(2);
		bus_3.setBusNumber(3);
		bus_4.setBusNumber(4);
		bus_5.setBusNumber(5);
		bus_6.setBusNumber(6);
		bus_7.setBusNumber(7);
		
		bus_1.start();
		bus_2.start();
		bus_3.start();
		bus_4.start();
		bus_5.start();
		bus_6.start();
		bus_7.start();
		
		try {
			bus_1.join();
			bus_2.join();
			bus_3.join();
			bus_4.join();
			bus_5.join();
			bus_6.join();
			bus_7.join();
		} catch (InterruptedException e) {
			LOG.error("Interrupted exception occured", e);
		}
		int total = 0;
		for (Station st : route) {
			total += st.getWaiting();
		}
		total += bus_1.getPassengers();
		total += bus_2.getPassengers();
		total += bus_3.getPassengers();
		total += bus_4.getPassengers();
		total += bus_5.getPassengers();
		total += bus_6.getPassengers();
		total += bus_7.getPassengers();
		LOG.info("Total after: " + total);
	}
}
