package com.zebrunner.demo;

import com.zebrunner.cucumber.ZebrunnerTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/features/",
        glue = "com.zebrunner.demo.steps",
        plugin = { "pretty",
                "html:target/cucumber-core-test-report",
                "pretty:target/cucumber-core-test-report.txt",
                "json:target/cucumber-core-test-report.json",
                "junit:target/cucumber-core-test-report.xml" }
)
public class CucumberTest extends ZebrunnerTestNGCucumberTests {
    //do nothing here as everything is declared in "?.feature" and steps
}
