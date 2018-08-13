package by.epam.training.runner;

import java.util.ArrayList;
import java.util.List;

import by.epam.training.model.Bus;
import by.epam.training.model.Station;

public class Runner {

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
		
		int before = 0;
		
		for (Station st : route) {
			before += st.getWaiting();
		}
		
		System.out.println("Total before: " + before);
		
		bus_1.setRoute(route);
		bus_2.setRoute(route);
		bus_3.setRoute(route);
		
		bus_1.setBusNumber(1);
		bus_2.setBusNumber(2);
		bus_3.setBusNumber(3);
		
		bus_1.start();
		bus_2.start();
		bus_3.start();
		try {
			bus_1.join();
			bus_2.join();
			bus_3.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int total = 0;
		for (Station st : route) {
			total += st.getWaiting();
		}
		total += bus_1.getPassengers();
		total += bus_2.getPassengers();
		total += bus_3.getPassengers();
		System.out.println("Total after: " + total);
	}
}
