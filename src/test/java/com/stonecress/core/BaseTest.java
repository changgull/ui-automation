package com.stonecress.core;


import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest extends Base {
    @Parameters({"env", "verbose", "browserOptions"})
    @BeforeSuite(alwaysRun = true)
    public void beforeSuite(
            @Optional("environment") String env,
            @Optional("false") Boolean verbose,
            @Optional("headless") String browserOptions) {
        loadGlobalProperties("default.properties");
        loadGlobalProperties(env + ".properties");
        getProperties().put("browserOptions", browserOptions);
        if (verbose) {
            RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        } else {
            RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        }
    }
}
