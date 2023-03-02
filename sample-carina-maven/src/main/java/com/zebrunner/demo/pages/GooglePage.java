package com.zebrunner.demo.pages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractPage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;


public class GooglePage extends AbstractPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String URL = "https://www.google.com/";

    @FindBy(xpath = "//input[@name='q']")
    private ExtendedWebElement searchField;

    @FindBy(xpath = "//*[@id='search']//a")
    private ExtendedWebElement firstSearchResultLink;

    public GooglePage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(searchField);
        setPageAbsoluteURL(URL);
    }

    public Boolean isResultContainsSearchValue(String searchValue){
        return firstSearchResultLink.getText().contains(searchValue);
    }

    public void search(String searchValue){
        LOGGER.info("Performing search with value: " + searchValue);
        searchField.type(searchValue);
        searchField.sendKeys(Keys.ENTER);
    }
}
