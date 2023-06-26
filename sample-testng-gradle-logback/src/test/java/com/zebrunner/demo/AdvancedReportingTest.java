package com.zebrunner.demo;

import com.zebrunner.agent.core.annotation.Maintainer;
import com.zebrunner.agent.core.annotation.TestLabel;
import com.zebrunner.agent.core.registrar.Artifact;
import com.zebrunner.agent.core.registrar.CurrentTestRun;
import com.zebrunner.agent.core.registrar.Label;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.nio.file.Paths;

/**
 * Example shows ADVANCED reporting to Zebrunner.
 * <p>
 * Following details can be added to a specific test or a whole test run:
 * - artifacts and references
 * - labels
 * - maintainer
 * <p>
 * Possibility to set on runtime:
 * - build
 * - locale
 * - platform
 */
@TestLabel(name = "feature", value = "advanced_reporting")
public class AdvancedReportingTest extends BaseTest {

    @BeforeSuite(description = "Example of overriding configuration parameters at runtime")
    public void initSuite() {
        LOGGER.info("Set a build for a test run");
        CurrentTestRun.setBuild("1.0-SNAPSHOT");

        LOGGER.info("Set a locale for a test run");
        CurrentTestRun.setLocale("EN_en");

        LOGGER.info("Set a platform for a test run");
        CurrentTestRun.setPlatform("API");
    }

    @Test
    @Maintainer("asukhodolova")
    public void testWithSpecificMaintainer() {
        LOGGER.info("Example shows how to attach a maintainer for a specific test");
        LOGGER.info("NOTE: The maintainer username should be a valid Zebrunner username, otherwise it will be set to anonymous");
    }

    @Test
    public void testWithDefaultMaintainer() {
        LOGGER.info("Example shows default test maintainer");
    }

    @Test
    @TestLabel(name = "feature", value = "annotation")
    @TestLabel(name = "app", value = {"reporting-service:v1.0", "reporting-service:v1.1"})
    public void testWithLabelsAddedUsingAnnotation() {
        LOGGER.info("Example shows how to add labels using annotation for a specific test");
    }

    @Test
    public void testWithLabelsAddedUsingJavaAPI() {
        LOGGER.info("Example shows how to add labels using Java API for a specific test and a whole test run");
        Label.attachToTest("feature", "java_api");
        Label.attachToTestRun("browser", "chrome");
    }

    @Test
    public void testWithArtifacts() {
        LOGGER.info("Example shows how to attach artifact for a specific test and a whole test run");
        Artifact.attachToTest("simple.txt", Paths.get("src/test/resources/artifacts/simple.txt"));
        Artifact.attachToTestRun("welcome.png", Paths.get("src/test/resources/artifacts/welcome.png"));
    }

    @Test
    public void testWithArtifactReferences() {
        LOGGER.info("Example shows how to attach reference for a specific test and a whole test run");
        Artifact.attachReferenceToTest("Zebrunner website", "https://zebrunner.com");
        Artifact.attachReferenceToTestRun("Zebrunner documentation", "https://zebrunner.com/documentation/");
    }
}
