package com.test.countryClient.api;

public class Country {
    private String name;
    private String countryCode;
    private String capital;
    private Long population;
    private String flagFileUrl;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public Long getPopulation() {
        return population;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }

    public String getFlagFileUrl() {
        return flagFileUrl;
    }

    public void setFlagFileUrl(String flagFileUrl) {
        this.flagFileUrl = flagFileUrl;
    }
}
