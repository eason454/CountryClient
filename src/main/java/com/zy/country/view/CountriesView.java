package com.zy.country.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.zy.country.data.CountryList;
import com.zy.country.rest.RestClientService;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@PageTitle("query countries")
@Route(value = "query-countries-sync", layout = MainLayout.class)
public class CountriesView extends Main {
	private final Grid<CountryList> countryListGrid;
	private final RestClientService service;
	private final Span amountLabel;

	public CountriesView(@Autowired RestClientService service) {
		this.service = service;
		amountLabel = new Span();
		amountLabel.setVisible(false);
		countryListGrid = new Grid<>(CountryList.class);
		countryListGrid.removeAllColumns();
		countryListGrid.addColumn(CountryList::getName).setHeader("name");
		countryListGrid.addColumn(CountryList::getCountryCode).setHeader("country code");
		final Button fetchCountries = new Button("Fetch all countries",
				e -> {
					amountLabel.setVisible(false);
					List<CountryList> countries = service.getCountries();
					amountLabel.setText(String.format("Got %s countries", countries.size()));
					amountLabel.setVisible(true);
					countryListGrid.setItems(countries);
				});
		fetchCountries.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		add(fetchCountries, amountLabel, countryListGrid);
	}
}