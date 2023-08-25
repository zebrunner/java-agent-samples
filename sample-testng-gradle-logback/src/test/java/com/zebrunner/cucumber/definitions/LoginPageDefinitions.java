package com.zebrunner.cucumber.definitions;


import com.zebrunner.agent.core.registrar.Screenshot;
import com.zebrunner.agent.core.webdriver.RemoteWebDriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.lang.invoke.MethodHandles;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class LoginPageDefinitions {

    protected static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    protected WebDriver driver;


    private String ZEBRUNNER_HUB_URL;

    @Before
    public void setUp() {
        ZEBRUNNER_HUB_URL = "https://team:da4ZbYTi24Pff3VX46fAe8ndRcKo5cjWOxhTHgvvz0dBWmGz86Ae3jublZjxfew0@engine.zebrunner.com/wd/hub";

        ChromeOptions options = new ChromeOptions();

        driver = initDriver(options);
    }

    @Given("User is on HRMLogin page {string}")
    public void loginTest(String url) {
        driver.get(url);
        takeScreenshot(driver);
    }

    @When("User enters username as {string} and password as {string}")
    public void goToHomePage(String userName, String passWord) {
        LOGGER.info("User enters username as {} and password as {}", userName, passWord);
        // login to application
        driver.findElement(By.name("username")).sendKeys(userName);
        driver.findElement(By.name("password")).sendKeys(passWord);
        driver.findElement(By.xpath("//*[@id='app']/div[1]/div/div[1]/div/div[2]/div[2]/form/div[3]/button")).submit();
        takeScreenshot(driver);
        // go the next page

    }

    @Then("User should be able to login successfully and new page open")
    public void verifyLogin() {
        LOGGER.info("User should be able to login successfully and new page open");
        String homePageHeading = driver.findElement(By.xpath("//*[@id='app']/div[1]/div[2]/div[2]/div/div[1]/div[1]/div[1]/h5")).getText();
        takeScreenshot(driver);
        //Verify new page - HomePage
        Assert.assertEquals(homePageHeading,"Employee Information");

    }

    @Then("User should be able to see error message {string}")
    public void verifyErrorMessage(String expectedErrorMessage) {
        LOGGER.info("User should be able to see error message {}", expectedErrorMessage);
        String actualErrorMessage = driver.findElement(By.xpath("//*[@id='app']/div[1]/div/div[1]/div/div[2]/div[2]/div/div[1]/div[1]/p")).getText();
        takeScreenshot(driver);
        // Verify Error Message
        Assert.assertEquals(actualErrorMessage,expectedErrorMessage);

    }

    @After
    public void teardown() {
        driver.quit();
    }


    private WebDriver initDriver(Capabilities capabilities) {
        URL hubUrl = getSeleniumHubUrl();
        WebDriver driver = hubUrl == null
                ? getLocalDriver(capabilities)
                : getRemoteDriver(hubUrl, capabilities);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        return driver;
    }

    private WebDriver getRemoteDriver(URL hubUrl, Capabilities capabilities) {
        Capabilities launcherCapabilities = RemoteWebDriverFactory.getCapabilities();
        Capabilities remoteCapabilities = launcherCapabilities.asMap().isEmpty() ? capabilities : launcherCapabilities;
        return new RemoteWebDriver(hubUrl, remoteCapabilities);
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

    private URL getSeleniumHubUrl() {
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
