package com.zebrunner.cucumber.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(tags = "", features = {"src/test/resources/features/"}, glue = {"com.zebrunner.cucumber.definitions"},
        plugin = {})
public class CucumberRunnerTests extends AbstractTestNGCucumberTests {
}
