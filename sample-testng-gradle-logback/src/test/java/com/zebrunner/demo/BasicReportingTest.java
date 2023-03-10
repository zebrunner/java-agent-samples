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
    public void testGoogleSearch() {
        LOGGER.info("Navigating to url: " + URL);
        driver.get(URL);
        takeScreenshot(driver);
        if (driver.getPageSource().contains(COOKIES_DIALOG_TEXT)) {
            LOGGER.info("Cookies use popup is displayed, necessary to click 'Accept all'");
            driver.findElement(By.xpath("//button[.='Accept all']")).click();
            takeScreenshot(driver);
        }

        LOGGER.info("Performing search with value: " + SEARCH_VALUE);
        WebElement searchInput = driver.findElement(By.xpath("//*[@name='q']"));
        searchInput.sendKeys(SEARCH_VALUE);
        searchInput.sendKeys(Keys.ENTER);
        takeScreenshot(driver);

        LOGGER.info("Verify first search result contains " + SEARCH_VALUE);
        WebElement firstSearchResultLink = driver.findElement(By.xpath("//*[@id='search']//a"));
        assertTrue(firstSearchResultLink.getText().contains(SEARCH_VALUE), "Incorrect first search result");
    }
}
