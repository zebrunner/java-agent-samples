package com.zebrunner.demo;

import com.zebrunner.agent.core.registrar.Screenshot;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.FileInputStream;
import java.lang.invoke.MethodHandles;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public abstract class BaseTest {

    protected static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final Integer DEFAULT_IMPLICIT_WAIT = 10;

    protected WebDriver driver;

    @BeforeClass
    public void setUp() {
        /** ADD GENERATED CAPABILITIES HERE */
        AbstractDriverOptions browserOptions = null;
        String ZEBRUNNER_GRID_URL = null;
        /** */
        if (ZEBRUNNER_GRID_URL == null || browserOptions == null) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else {
            try {
                driver = new RemoteWebDriver(new URL(ZEBRUNNER_GRID_URL), browserOptions);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                throw new RuntimeException("Error while creating a session", e);
            }
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(DEFAULT_IMPLICIT_WAIT));
        driver.manage().window().maximize();
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected void takeScreenshot(WebDriver driver) {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Screenshot.upload(new FileInputStream(screenshot).readAllBytes(), System.currentTimeMillis());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
