package com.zy.country.view;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Nav;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.RouterLink;

/**
 * Example application that demonstrates how to Use Spring WebFlux to fetch data from a
 * REST service and show it in a Vaadin Grid.
 */
@CssImport("./styles/shared-styles.css")
@Push
public class MainLayout extends AppLayout implements AfterNavigationObserver {

	private final H1 pageTitle;
	private final RouterLink home;
	private final RouterLink singleCountry;
	private final RouterLink allCountries;
	private final RouterLink singleCountryAsync;
	private final RouterLink allCountriesAsync;

	public MainLayout() {
		// Navigation
		home = new RouterLink("Home", HomeView.class);
		singleCountry = new RouterLink("Query country detail", CountryView.class);
		allCountries = new RouterLink("Query all countries", CountriesView.class);
		singleCountryAsync = new RouterLink("Query country detail asynchronously", AsyncCountryView.class);
		allCountriesAsync = new RouterLink("Query all countries asynchronously", AsyncCountriesView.class);

		final UnorderedList list = new UnorderedList(new ListItem(home), new ListItem(singleCountry),
				new ListItem(allCountries), new ListItem(singleCountryAsync),
				new ListItem(allCountriesAsync));
		final Nav navigation = new Nav(list);
		addToDrawer(navigation);
		setPrimarySection(Section.DRAWER);
		setDrawerOpened(false);

		// Header
		pageTitle = new H1("Home");
		final Header header = new Header(new DrawerToggle(), pageTitle);
		addToNavbar(header);
	}

	private RouterLink[] getRouterLinks() {
		return new RouterLink[] { home, singleCountry, allCountries, singleCountryAsync, allCountriesAsync};
	}

	@Override
	public void afterNavigation(AfterNavigationEvent event) {
		for (final RouterLink routerLink : getRouterLinks()) {
			if (routerLink.getHighlightCondition().shouldHighlight(routerLink, event)) {
				pageTitle.setText(routerLink.getText());
			}
		}
	}
}