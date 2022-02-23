package com.zy.country.rest;

import com.zy.country.data.CountryEvent;
import com.zy.country.data.CountryListEvent;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;

import java.io.Serializable;
import java.util.List;

@Service
public class AsyncRestClientService implements Serializable {
    @Value("${server.port}")
    private String serverPort;

    @Value("${country.service.url}")
    private String countryServiceUrl;


    /**
     * Generic callback interface for asynchronous operations.
     *
     * @param <T> the result type
     */
    public interface AsyncRestCallback<T> {
        void operationFinished(T results);
    }

    /**
     * Returns parsed {@link CountryListEvent} objects from the REST service, asynchronously.
     */
    public void getAllCountriesAsync(AsyncRestCallback<List<CountryListEvent>> callback) {

        RequestHeadersSpec<?> spec = WebClient.create().get().uri(countryServiceUrl).header(HttpHeaders.ACCEPT, MediaType.TEXT_EVENT_STREAM_VALUE);

        // Instead of 'block', do 'subscribe'. This means the fetch will run on a
        // separate thread and notify us when it's ready by calling our lambda operation.
        spec.retrieve().toEntityList(CountryListEvent.class).subscribe(result -> {

            // This code block is run whenever the results are back
            final List<CountryListEvent> countries = result.getBody();
            callback.operationFinished(countries);
        });

    }

    public void getCountryAsync(AsyncRestCallback<List<CountryEvent>> callback, String name) {

        RequestHeadersSpec<?> spec = WebClient.create().get().uri(countryServiceUrl + "/name/" + name).header(HttpHeaders.ACCEPT, MediaType.TEXT_EVENT_STREAM_VALUE);
        spec.retrieve().toEntityList(CountryEvent.class).subscribe(result -> {
            List<CountryEvent> country = result.getBody();
            callback.operationFinished(country);
        });

    }

}