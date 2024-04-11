package com.zebrunner.demo.steps.hello;

import io.cucumber.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class HelloWorldSteps {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Then("^log Hello World$")
    public void helloWorld() {
        LOGGER.info("Hello World!");
    }

}
