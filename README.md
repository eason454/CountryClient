# CountryClient

This is a web application to query countries, and in addition, provides more detailed information per country for using REST services with Spring Boot and Vaadin.

You can  
1) Display country detail by name from a traditional REST API  
2) Display all countries from a traditional REST API
3) Display country detail by name from a reactive REST API  
4) Display all countries from a reactive REST API

The backend REST service package is provided from [CountryManagement](https://github.com/eason454/CountryManagement). 
You have to run backend application first in order to use this web application.

## How to run

* Clone the repository
* JDK 1.8 and Maven 3.x in your environment
* Build the project by running

  ``` mvn clean package ```

* Once build successfully, run the application by:
```
     mvn spring-boot:run -Pproduction
```

You should see log message below if you run it successfully:
```
    2022-02-23 20:52:17.104  INFO 28588 --- [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8111 (http) with context path '/country'
    2022-02-23 20:52:17.122  INFO 28588 --- [  restartedMain] com.zy.country.CountryClientApplication  : Started CountryClientApplication in 6.084 seconds (JVM running for 7.75)
    
```

Then open a browser, copy the link below and open:

```
    http://localhost:8111/country
```

## About pages
You can navigate to different pages via left sidebar. Here are pages:
1) Query country detail
   
   Query with standard way of calling REST services, given country name input, and query country detail synchronously 
   from backend, country name cannot be empty(validation on page) before click "Fetch country" button.

2) Query all countries
   
   Same standard way of calling REST services, click "Fetch all countries" to get all countries, and amount of result is shown after query

3) Query country detail asynchronously
   
   Query with reactive way of calling REST services, server will push an event containing data to client, if event contains error reason, 
   page will show error, like "Not Found" if no matched country  

4) Query all countries asynchronously
   
   Reactive way, you will see "Fetching countries, please wait..." label before server push all events to client, then all countries will 
   be displayed in the grid, you will also see amount of countries.