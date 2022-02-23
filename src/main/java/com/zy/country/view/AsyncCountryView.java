package com.zy.country.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.zy.country.data.Country;
import com.zy.country.data.CountryEvent;
import com.zy.country.data.ErrorMessage;
import com.zy.country.rest.AsyncRestClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@PageTitle("Query country asynchronously")
@Route(value = "query-country-async", layout = MainLayout.class)
public class AsyncCountryView extends Main {

	private final Grid<Country> countryGrid;
	private final Span statusLabel;
	private final Span errorLabel;
	private final AsyncRestClientService service;
	private Binder<Country> binder = new Binder<>();
	private static final String ERROR_INVALID_COUNTRY_NAME = "Invalid country name";

	public AsyncCountryView(@Autowired AsyncRestClientService service) {
		this.service = service;
		TextField name = new TextField();
		name.setLabel("Country name");
		name.setRequired(true);
		name.setRequiredIndicatorVisible(true);
		name.setErrorMessage(ERROR_INVALID_COUNTRY_NAME);
		binder.forField(name).withValidator(new StringLengthValidator(ERROR_INVALID_COUNTRY_NAME, 1, 100)).bind(Country::getName, Country::setName);

		countryGrid = new Grid<>(Country.class);
		countryGrid.removeAllColumns();
		countryGrid.addColumn(Country::getName).setHeader("name");
		countryGrid.addColumn(Country::getCountryCode).setHeader("country code");
		countryGrid.addColumn(Country::getCapital).setHeader("capital");
		countryGrid.addColumn(Country::getPopulation).setHeader("population");
		countryGrid.addColumn(Country::getFlagFileUrl).setHeader("flag file url");

		statusLabel = new Span(" Fetching country, please wait...");
		statusLabel.setVisible(false);

		errorLabel = new Span();
		errorLabel.setVisible(false);

		final Button fetchCountry = new Button("Fetch Country", e -> startFetch(name.getValue()));
		fetchCountry.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.add(name);
		add(horizontalLayout, fetchCountry, errorLabel, statusLabel, countryGrid);

	}

	private void startFetch(String name) {
		binder.validate();
		if (!binder.isValid()) {
			return;
		}
		statusLabel.setVisible(true);
		countryGrid.setEnabled(false);
		errorLabel.setVisible(false);

		service.getCountryAsync(result -> getUI().get().access(() -> {

			// We now have the results. But, because this call might happen outside normal
			// Vaadin calls, we need to make sure the HTTP Session data of this app isn't
			// violated. For this we use UI#access()
			statusLabel.setVisible(false);
			countryGrid.setEnabled(true);
			List<Country> countries = result.stream().map(CountryEvent::getCountry).filter(Objects::nonNull).collect(Collectors.toList());
			if (CollectionUtils.isEmpty(countries)) {
				List<String> errors = result.stream().map(CountryEvent::getError).map(ErrorMessage::getMessage).collect(Collectors.toList());
				errorLabel.setText(String.join(",", errors));
				errorLabel.setVisible(true);
			}
			countryGrid.setItems(countries);
		}), name);
	}
}