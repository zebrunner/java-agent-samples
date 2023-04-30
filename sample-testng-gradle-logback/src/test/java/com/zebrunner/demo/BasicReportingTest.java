package com.zebrunner.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Example shows BASIC reporting to Zebrunner:
 * - test run structure
 * - logs and screenshots
 */
public class BasicReportingTest extends BaseTest {

    private static final String URL = "https://www.google.com/";
    private static final String COOKIES_DIALOG_TEXT = "Before you continue to Google Search";
    private static final String SEARCH_VALUE = "Zebrunner";

    @Test
    public void testGoogleSearchPass() {
        openUrlAndAcceptCookies(URL);
        performSearch(SEARCH_VALUE);

        LOGGER.info("Verify first search result contains " + SEARCH_VALUE);
        WebElement firstSearchResultLink = driver.findElement(By.xpath("//*[@id='search']//a"));
        assertTrue(firstSearchResultLink.getText().contains(SEARCH_VALUE), "Incorrect first search result");
    }

    @Test
    public void testGoogleSearchFailByAuto() {
        openUrlAndAcceptCookies(URL);
        performSearch(SEARCH_VALUE);

        LOGGER.info("Verify first search result equals " + SEARCH_VALUE);
        WebElement firstSearchResultLink = driver.findElement(By.xpath("//*[@id='result']//a"));
        assertTrue(firstSearchResultLink.getText().contains(SEARCH_VALUE), "Incorrect first search result");
    }

    @Test
    public void testGoogleSearchFailByBusiness() {
        openUrlAndAcceptCookies(URL);
        performSearch(SEARCH_VALUE);

        LOGGER.info("Verify first search result equals " + SEARCH_VALUE);
        WebElement firstSearchResultLink = driver.findElement(By.xpath("//*[@id='search']//a"));
        assertTrue(firstSearchResultLink.getText().equals(SEARCH_VALUE), "Incorrect first search result");
    }

    @Test(dependsOnMethods = "testGoogleSearchFailByBusiness")
    public void testGoogleSearchSkip() {
        LOGGER.info("Empty test that will be skipped");
    }

    private void openUrlAndAcceptCookies(String url) {
        LOGGER.info("Navigating to url: " + url);
        driver.get(url);
        takeScreenshot(driver);
        if (driver.getPageSource().contains(COOKIES_DIALOG_TEXT)) {
            LOGGER.info("Cookies use popup is displayed, necessary to click 'Accept all'");
            driver.findElement(By.xpath("//button[.='Accept all']")).click();
            takeScreenshot(driver);
        }
    }

    private void performSearch(String value) {
        LOGGER.info("Performing search with value: " + value);
        WebElement searchInput = driver.findElement(By.xpath("//*[@name='q']"));
        searchInput.sendKeys(value);
        searchInput.sendKeys(Keys.ENTER);
        takeScreenshot(driver);
    }
}
