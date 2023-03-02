package com.zebrunner.demo;

import com.qaprosoft.carina.core.foundation.IAbstractTest;
import com.zebrunner.demo.pages.GooglePage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.invoke.MethodHandles;

public class BasicReportingTest implements IAbstractTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String URL = "https://www.google.com/";
    private static final String SEARCH_VALUE = "Zebrunner";

    @Test
    public void testGoogleSearch() {
        LOGGER.info("Navigating to url: " + URL);
        GooglePage googlePage = new GooglePage(getDriver());
        googlePage.open();
        googlePage.search(SEARCH_VALUE);

        LOGGER.info("Verify first search result contains " + SEARCH_VALUE);
        Assert.assertTrue(googlePage.isResultContainsSearchValue(SEARCH_VALUE),
                "Result should contain " + SEARCH_VALUE);
    }
}
