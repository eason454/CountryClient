package com.test.countryClient.rest;

import com.test.countryClient.data.Country;
import com.test.countryClient.data.CountryList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.Serializable;
import java.util.List;

@Service
public class RestClientService implements Serializable {

    private static final long serialVersionUID = 5425508918530251853L;

    @Value("${server.port}")
    private String serverPort;

    @Value("${country.service.url}")
    private String countryServiceUrl;

    public Country getCountryByName(String name) {
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri(countryServiceUrl + "/name/" + name)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        return spec.retrieve().toEntity(Country.class).block().getBody();
    }

    public List<CountryList> getCountries() {
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri(countryServiceUrl)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        return spec.retrieve().toEntityList(CountryList.class).block().getBody();
    }
}