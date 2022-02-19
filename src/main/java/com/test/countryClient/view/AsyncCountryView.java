package com.test.countryClient.view;

import com.test.countryClient.api.Country;
import com.test.countryClient.rest.AsyncRestClientService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Asynchronous query country")
@Route(value = "async-in-memory-dto-country", layout = MainLayout.class)
public class AsyncCountryView extends Main {

	private final Grid<Country> countryGrid;
	private final Span statusLabel;
	private final AsyncRestClientService service;

	public AsyncCountryView(@Autowired AsyncRestClientService service) {
		this.service = service;

		countryGrid = new Grid<>(Country.class);

		statusLabel = new Span(" Fetching country, please wait...");
		statusLabel.setVisible(false);

		// Fetch all entities and show
		final Button fetchCountry = new Button("Fetch Country", e -> startFetch());
		fetchCountry.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

		add(fetchCountry, statusLabel, countryGrid);

	}

	private void startFetch() {

		// In this method we ask our service to start fetching the results from REST,
		// and wait for the result.

		// These are run immediately, to give the user feedback that we are doing
		// something
		statusLabel.setVisible(true);
		countryGrid.setEnabled(false);

		// Calling the service to start the op. The callback e provide is called when
		// the results are available.
		service.getAllCountriesAsync(result -> {

			// We now have the results. But, because this call might happen outside normal
			// Vaadin calls, we need to make sure the HTTP Session data of this app isn't
			// violated. For this we use UI#access()
			getUI().get().access(() -> {

				// Finally, we can modify the UI state. These changes are sent to the users
				// browser immediately, because we have enable Websocket Server Push (@Push
				// annotation in MainLayout).
				statusLabel.setVisible(false);
				countryGrid.setEnabled(true);
				countryGrid.setItems(result);
			});
		});
	}
}