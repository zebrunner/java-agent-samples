package com.zebrunner.demo;

import com.zebrunner.agent.core.registrar.Screenshot;
import com.zebrunner.agent.core.webdriver.RemoteWebDriverFactory;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.lang.invoke.MethodHandles;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public abstract class BaseTest {

    protected static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    protected static WebDriver driver;

    private static String ZEBRUNNER_HUB_URL;

    @BeforeClass
    public static void setUp() {
        /** ADD YOUR CAPABILITIES AND ZEBRUNNER_HUB_URL HERE */
        ZEBRUNNER_HUB_URL = null; // "https://user:pass@example.com/wd/hub";

        ChromeOptions options = new ChromeOptions();
        options.setPlatformName("Linux");
        options.setBrowserVersion("108.0");
        options.setCapability("enableVideo", true);
        /** END OF CAPABILITIES */

        driver = initDriver(options);
    }

    @AfterClass
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private static WebDriver initDriver(Capabilities capabilities) {
        URL hubUrl = getSeleniumHubUrl();
        WebDriver driver = hubUrl == null
                ? getLocalDriver(capabilities)
                : getRemoteDriver(hubUrl, capabilities);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        return driver;
    }

    private static WebDriver getRemoteDriver(URL hubUrl, Capabilities capabilities) {
        Capabilities launcherCapabilities = RemoteWebDriverFactory.getCapabilities();
        Capabilities remoteCapabilities = launcherCapabilities.asMap().isEmpty() ? capabilities : launcherCapabilities;
        return new RemoteWebDriver(hubUrl, remoteCapabilities);
    }

    private static WebDriver getLocalDriver(Capabilities capabilities) {
        switch (capabilities.getBrowserName()) {
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
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--remote-allow-origins=*");
                return new ChromeDriver(options);
        }
    }

    private static URL getSeleniumHubUrl() {
        URL launcherHubUrl = RemoteWebDriverFactory.getSeleniumHubUrl();
        if (launcherHubUrl != null) {
            return launcherHubUrl;
        }
        if (ZEBRUNNER_HUB_URL != null) {
            try {
                return new URL(ZEBRUNNER_HUB_URL);
            } catch (MalformedURLException e) {
                throw new RuntimeException("Incorrect Selenium Grid URL", e);
            }
        }
        return null;
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
