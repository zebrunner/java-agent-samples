package com.zebrunner.demo;

import com.zebrunner.agent.core.registrar.Screenshot;
import com.zebrunner.agent.core.webdriver.RemoteWebDriverFactory;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
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

    private String ZEBRUNNER_HUB_URL;

    @BeforeClass
    public void setUp() {
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
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private WebDriver initDriver(AbstractDriverOptions options) {
        WebDriver driver = System.getProperty("ZEBRUNNER_HUB_URL", ZEBRUNNER_HUB_URL) == null
                ? getLocalDriver(options)
                : getRemoteDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        return driver;
    }

    private WebDriver getRemoteDriver(Capabilities capabilities) {
        Capabilities launcherCapabilities = RemoteWebDriverFactory.getCapabilities();
        Capabilities remoteCapabilities = launcherCapabilities.asMap().isEmpty() ? capabilities : launcherCapabilities;
        try {
            return new RemoteWebDriver(new URL(ZEBRUNNER_HUB_URL), remoteCapabilities);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error while creating a WebDriver session", e);
        }
    }

    private WebDriver getLocalDriver(Capabilities capabilities) {
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

    protected void takeScreenshot(WebDriver driver) {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Screenshot.upload(new FileInputStream(screenshot).readAllBytes(), System.currentTimeMillis());
        } catch (Exception e) {
            throw new RuntimeException("Error while taking a screenshot", e);
        }
    }
}
