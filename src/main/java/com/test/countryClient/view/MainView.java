package com.test.countryClient.view;

import com.test.countryClient.api.Country;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import static java.util.Arrays.asList;

@PageTitle("Country Portal")
@Route(value = "", layout = MainLayout.class)
public class MainView extends VerticalLayout {

	final Grid<Country> grid;

	public MainView() {
		this.grid = new Grid<>(Country.class);
		add(grid);
		listCountries();
	}

	private void listCountries() {
		Country country1 = new Country();
		country1.setName("Finland");
		country1.setCountryCode("FI");
		country1.setPopulation(5500000L);
		country1.setCapital("Helsinki");
		grid.setItems(asList(country1));//queryrepo.findAll()
	}
}

