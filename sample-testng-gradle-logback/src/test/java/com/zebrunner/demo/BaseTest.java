package com.zebrunner.demo;

import com.zebrunner.agent.core.registrar.Screenshot;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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

    protected RemoteWebDriver driver;

    @BeforeClass
    public void setUp() {
        /** ADD GENERATED CAPABILITIES HERE */
        ChromeOptions browserOptions = new ChromeOptions();
        browserOptions.setCapability("enableVideo", true);
        browserOptions.setBrowserVersion("108.0");
        browserOptions.setPlatformName("Linux");
        /** END OF CAPABILITIES */
        if (getSeleniumHubUrl() == null || browserOptions == null) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else {
            driver = new RemoteWebDriver(getSeleniumHubUrl(), browserOptions);
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(DEFAULT_IMPLICIT_WAIT));
        driver.manage().window().maximize();
    }

    public URL getSeleniumHubUrl() {
        String hubUrl = System.getenv("ZEBRUNNER_HUB_URL");
        try {
            return hubUrl != null ? new URL(hubUrl) : null;
        } catch (MalformedURLException e) {
            throw new RuntimeException("Unrecognized or unspecified ZEBRUNNER_HUB_URL environment variable", e);
        }
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
