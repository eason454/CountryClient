package com.test.countryClient.view;

import com.test.countryClient.data.CountryList;
import com.test.countryClient.rest.RestClientService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("query countries")
@Route(value = "query-countries-sync", layout = MainLayout.class)
public class CountriesView extends Main {
	private final Grid<CountryList> countryListGrid;
	private final RestClientService service;

	public CountriesView(@Autowired RestClientService service) {
		this.service = service;
		countryListGrid = new Grid<>(CountryList.class);
		countryListGrid.removeAllColumns();
		countryListGrid.addColumn(CountryList::getName).setHeader("name");
		countryListGrid.addColumn(CountryList::getCountryCode).setHeader("country code");
		final Button fetchCountries = new Button("Fetch all countries",
				e -> countryListGrid.setItems(service.getCountries()));
		fetchCountries.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		add(fetchCountries, countryListGrid);
	}
}