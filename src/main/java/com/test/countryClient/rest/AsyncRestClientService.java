package com.test.countryClient.rest;

import java.io.Serializable;
import java.util.List;

import com.test.countryClient.api.Country;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
@Service
public class AsyncRestClientService implements Serializable {

    /**
     * Generic callback interface for asynchronous operations.
     *
     * @param <T> the result type
     */
    public static interface AsyncRestCallback<T> {
        void operationFinished(T results);
    }

    /**
     * Returns parsed {@link Country} objects from the REST service,
     * asynchronously.
     */
    public void getAllCountriesAsync(AsyncRestCallback<List<Country>> callback) {

        // Configure fetch as normal
        RequestHeadersSpec<?> spec = WebClient.create().get().uri("https://localhost:8080/countries/all");

        // But instead of 'block', do 'subscribe'. This means the fetch will run on a
        // separate thread and notify us when it's ready by calling our lambda
        // operation.
        spec.retrieve().toEntityList(Country.class).subscribe(result -> {

            // This code block is run whenever the results are back

            // get results as usual
            final List<Country> countries = result.getBody();

            System.out.println(String.format("...received %d items.", countries.size()));

            // call the ui with the data
            callback.operationFinished(countries);
        });

    }

}