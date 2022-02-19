package com.test.countryClient.view;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Section;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@PageTitle("Country Portal")
@Route(value = "", layout = MainLayout.class)
public class HomeView extends Main {

	public HomeView() {
		add(new Section(new Paragraph(
				"This example app demonstrates country services.")));

		add(new Section(
				new H2(new RouterLink("Query country information by name", AsyncCountryView.class))));

		add(new Section(new H2(new RouterLink("Query all countries", AsyncCountriesView.class))));
	}
}