package com.zebrunner.demo;

import com.zebrunner.agent.core.registrar.Screenshot;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
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

    protected WebDriver driver;

    /** REPLACE WITH YOUR CAPABILITIES HERE */
    String ZEBRUNNER_HUB_URL = null; // "https://user:pass@example.com/wd/hub";

    public AbstractDriverOptions getDriverOptions() {
        ChromeOptions options = new ChromeOptions();
        options.setPlatformName("Linux");
        options.setBrowserVersion("108.0");
        options.setCapability("enableVideo", true);
        return options;
    }
    /** END OF CAPABILITIES */

    @BeforeClass
    public void setUp() {
        driver = initDriver(getDriverOptions());
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private WebDriver initDriver(AbstractDriverOptions options) {
        WebDriver driver = ZEBRUNNER_HUB_URL == null
                ? getLocalDriver(options)
                : getRemoteDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        return driver;
    }

    private WebDriver getRemoteDriver(AbstractDriverOptions options) {
        try {
            return new RemoteWebDriver(new URL(ZEBRUNNER_HUB_URL), options);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error while creating a WebDriver session", e);
        }
    }

    private WebDriver getLocalDriver(AbstractDriverOptions options) {
        switch (options.getBrowserName()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver();
            case "edge":
                WebDriverManager.edgedriver().setup();
                return new EdgeDriver();
            case "safari":
                WebDriverManager.safaridriver().setup();
                return new SafariDriver();
            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();
        }
    }

    protected void takeScreenshot(WebDriver driver) {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Screenshot.upload(new FileInputStream(screenshot).readAllBytes(), System.currentTimeMillis());
        } catch (Exception e) {
            throw new RuntimeException("Error while taking a screenshot", e);
        }
    }
}
