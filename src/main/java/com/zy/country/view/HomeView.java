package com.zy.country.view;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Section;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@PageTitle("Country Portal")
@Route(value = "", layout = MainLayout.class)
public class HomeView extends Main {

	public HomeView() {
		add(new Section(new Paragraph(
				"This example app demonstrates country service.")));
		add(new Section(
				new H2(new RouterLink("Query country detail", CountryView.class)),
				new Paragraph("Query country detail with standard way of calling REST services")));

		add(new Section(new H2(new RouterLink("Query all countries", CountriesView.class)),
				new Paragraph("Query all countries with standard way of calling REST services")));

		add(new Section(
				new H2(new RouterLink("Query country detail asynchronously", AsyncCountryView.class)),
				new Paragraph("Query country detail with reactive way of calling REST services")));

		add(new Section(new H2(new RouterLink("Query all countries asynchronously", AsyncCountriesView.class)),
				new Paragraph("Query all countries with reactive way of calling REST services")));
	}
}