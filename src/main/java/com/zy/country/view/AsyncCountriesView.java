package com.zy.country.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.zy.country.data.CountryList;
import com.zy.country.data.CountryListEvent;
import com.zy.country.rest.AsyncRestClientService;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@PageTitle("Query countries asynchronously")
@Route(value = "query-countries-async", layout = MainLayout.class)
public class AsyncCountriesView extends Main {

    private final Grid<CountryList> countryListGrid;
    private final Span statusLabel;
    private final Span amountLabel;
    private final AsyncRestClientService service;

    public AsyncCountriesView(@Autowired AsyncRestClientService service) {
        this.service = service;

        countryListGrid = new Grid<>(CountryList.class);
        countryListGrid.removeAllColumns();
        countryListGrid.addColumn(CountryList::getName).setHeader("name");
        countryListGrid.addColumn(CountryList::getCountryCode).setHeader("country code");


        statusLabel = new Span(" Fetching countries, please wait...");
        statusLabel.setVisible(false);

        amountLabel = new Span();
        amountLabel.setVisible(false);

        final Button fetchCountries = new Button("Fetch all countries", e -> startFetch());
        fetchCountries.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(fetchCountries, statusLabel, amountLabel, countryListGrid);

    }

    private void startFetch() {
        statusLabel.setVisible(true);
        countryListGrid.setEnabled(false);
        amountLabel.setVisible(false);

        service.getAllCountriesAsync(result -> getUI().get().access(() -> {
			statusLabel.setVisible(false);
			countryListGrid.setEnabled(true);
			List<CountryList> countries = result.stream().map(CountryListEvent::getCountry).collect(Collectors.toList());
			amountLabel.setText(String.format("Got %s countries", countries.size()));
			amountLabel.setVisible(true);
			countryListGrid.setItems(countries);
		}));
    }
}