package com.zy.country.view;

import com.zy.country.data.Country;
import com.zy.country.rest.RestClientService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;

import static java.util.Collections.emptyList;

@PageTitle("query country detail")
@Route(value = "query-country-sync", layout = MainLayout.class)
public class CountryView extends Main {

    private static final String ERROR_INVALID_COUNTRY_NAME = "Invalid country name";
    private final RestClientService service;

    private Binder<Country> binder = new Binder<>();

    public CountryView(@Autowired RestClientService service) {
        this.service = service;
        TextField name = new TextField();
        name.setLabel("Country name");
        name.setRequired(true);
        name.setRequiredIndicatorVisible(true);
        name.setErrorMessage(ERROR_INVALID_COUNTRY_NAME);
        binder.forField(name).withValidator(new StringLengthValidator(ERROR_INVALID_COUNTRY_NAME, 1, 100)).bind(Country::getName, Country::setName);

        Grid<Country> countryGrid = new Grid<>(Country.class);
        countryGrid.removeAllColumns();
        countryGrid.addColumn(Country::getName).setHeader("name");
        countryGrid.addColumn(Country::getCountryCode).setHeader("country code");
        countryGrid.addColumn(Country::getCapital).setHeader("capital");
        countryGrid.addColumn(Country::getPopulation).setHeader("population");
        countryGrid.addColumn(Country::getFlagFileUrl).setHeader("flag file url");
        final Button fetchCountry = new Button("Fetch country",
                e -> {
                    binder.validate();
                    if(!binder.isValid()){
                        return;
                    }
                    countryGrid.setItems(emptyList());
                    countryGrid.setItems(service.getCountryByName(name.getValue()));
                });
        fetchCountry.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(name);
        add(horizontalLayout, fetchCountry, countryGrid);

    }
}