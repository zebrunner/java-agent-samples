package com.zebrunner.demo.steps.web;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.lang.invoke.MethodHandles;

public class DocumentationStepDefinition {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Given("^user is on Documentation page$")
    public void user_already_on_doc_page() throws InterruptedException {
        LOGGER.info("Navigating to Documentation Page...");
        Hooks.getDriver().get("https://zebrunner.com/documentation/");
        Thread.sleep(1000);
        LOGGER.info("User is on Documentation Page");
    }

    @When("^select Reporting concepts$")
    public void select_reporting_concepts() throws InterruptedException {
        LOGGER.info("Selecting Reporting concepts...");

        WebElement el = Hooks.getDriver().findElement(By.xpath("//*[contains(text(),'Reporting concepts')]"));
        el.click();
        Thread.sleep(1000);
        JavascriptExecutor js = (JavascriptExecutor) Hooks.getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        Thread.sleep(1000);
    }

    @When("^select Java$")
    public void select_java() throws InterruptedException {
        LOGGER.info("Selecting Java...");
        WebElement el = Hooks.getDriver().findElement(By.xpath("//*[contains(text(),'Java')]"));
        el.click();
        Thread.sleep(1000);
    }

    @When("^select TestNG$")
    public void select_testng() throws InterruptedException {
        LOGGER.info("Navigating to TestNG agent documentation...");
        WebElement el = Hooks.getDriver().findElement(By.xpath("//*[contains(text(),'TestNG')]"));
        el.click();
        Thread.sleep(1000);
        JavascriptExecutor js = (JavascriptExecutor) Hooks.getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        Thread.sleep(1000);
    }

    @Then("verify page title")
    public void verify_page_title() throws InterruptedException {
        LOGGER.info("Verifying page title...");
        String title = Hooks.getDriver().findElement(By.xpath("//h1")).getText();
        Thread.sleep(1000);
        Assert.assertEquals(title, "TestNG reporting agent", "Page title is not as expected");
    }

    @When("^select Core concepts$")
    public void select_core_concepts() throws InterruptedException {
        LOGGER.info("Selecting Core concepts...");
        WebElement el = Hooks.getDriver().findElement(By.xpath("//*[contains(text(),'Core concepts')]"));
        el.click();
        Thread.sleep(1000);
        JavascriptExecutor js = (JavascriptExecutor) Hooks.getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        Thread.sleep(1000);
    }

    @When("^select Projects$")
    public void select_projects() throws InterruptedException {
        LOGGER.info("Selecting Projects...");
        WebElement el = Hooks.getDriver().findElement(By.xpath("//*[contains(text(),'Projects')]"));
        el.click();
        Thread.sleep(1000);
    }

    @When("^select Test repository$")
    public void select_test_repo() throws InterruptedException {
        LOGGER.info("Navigating to Test repository...");
        WebElement el = Hooks.getDriver().findElement(By.xpath("//*[contains(text(),'Test repository')]"));
        el.click();
        Thread.sleep(1000);
        JavascriptExecutor js = (JavascriptExecutor) Hooks.getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        Thread.sleep(1000);
    }

    @When("^select Automation launches$")
    public void select_automation_launches() throws InterruptedException {
        LOGGER.info("Navigating to Automation launches...");
        WebElement el = Hooks.getDriver().findElement(By.xpath("//*[contains(text(),'Automation launches')]"));
        el.click();
        Thread.sleep(1000);
        JavascriptExecutor js = (JavascriptExecutor) Hooks.getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        Thread.sleep(1000);
    }

    @Then("^verify Automation launches page title$")
    public void verify_automation_launches_page_title() throws InterruptedException {
        LOGGER.info("Verifying page title...");
        String title = Hooks.getDriver().findElement(By.xpath("//h1")).getText();
        Thread.sleep(1000);
        Assert.assertEquals(title, "Automation launches", "Page title is not as expected");
    }

    @When("^select Test Case management$")
    public void select_tcm() throws InterruptedException {
        LOGGER.info("Selecting Test Case management...");
        WebElement el = Hooks.getDriver().findElement(By.xpath("//*[contains(text(),'Test Case management')]"));
        el.click();
        Thread.sleep(1000);
        JavascriptExecutor js = (JavascriptExecutor) Hooks.getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        Thread.sleep(1000);
    }

    @When("^select TestRail$")
    public void select_testrail() throws InterruptedException {
        LOGGER.info("Selecting TestRail...");
        WebElement el = Hooks.getDriver().findElement(By.xpath("//*[contains(text(),'TestRail')]"));
        el.click();
        Thread.sleep(1000);
    }

    @When("^select Xray$")
    public void select_xray() throws InterruptedException {
        LOGGER.info("Navigating to Xray...");
        WebElement el = Hooks.getDriver().findElement(By.xpath("//*[contains(text(),'Xray')]"));
        el.click();
        Thread.sleep(1000);
        JavascriptExecutor js = (JavascriptExecutor) Hooks.getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        Thread.sleep(1000);
    }

    @When("^select Testing platforms$")
    public void select_tp() throws InterruptedException {
        LOGGER.info("Selecting Testing platforms...");
        WebElement el = Hooks.getDriver().findElement(By.xpath("//*[contains(text(),'Testing platforms')]"));
        el.click();
        Thread.sleep(1000);
        JavascriptExecutor js = (JavascriptExecutor) Hooks.getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        Thread.sleep(1000);
    }

    @When("^select Zebrunner Selenium Grid$")
    public void select_zebrunner_grid() throws InterruptedException {
        LOGGER.info("Selecting Zebrunner Selenium Grid...");
        WebElement el = Hooks.getDriver().findElement(By.xpath("//*[contains(text(),'Zebrunner Selenium Grid')]"));
        el.click();
        Thread.sleep(1000);
    }

    @When("^select Administration$")
    public void select_administration() throws InterruptedException {
        LOGGER.info("Selecting Administration...");
        WebElement el = Hooks.getDriver().findElement(By.xpath("//*[contains(text(),'Administration')]"));
        el.click();
        Thread.sleep(1000);
        JavascriptExecutor js = (JavascriptExecutor) Hooks.getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        Thread.sleep(1000);
    }

    @When("^select Onboarding$")
    public void select_onboarding() throws InterruptedException {
        LOGGER.info("Navigating to Onboarding...");
        WebElement el = Hooks.getDriver().findElement(By.xpath("//*[contains(text(),'Onboarding')]"));
        el.click();
        Thread.sleep(1000);
    }
}
