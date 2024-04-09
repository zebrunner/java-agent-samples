package com.zebrunner.cucumber;

import com.zebrunner.agent.testng.core.testname.TestNameResolverRegistry;
import io.cucumber.testng.CucumberPropertiesProvider;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.Pickle;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * The class that should be used instead of {@link io.cucumber.testng.AbstractTestNGCucumberTests}
 */
public abstract class ZebrunnerTestNGCucumberTests {
    private static final Map<URI, List<String>> FEATURE_LINES = new ConcurrentHashMap<>();
    private TestNGCucumberRunner testNGCucumberRunner;

    public ZebrunnerTestNGCucumberTests() {
        // Provide your implementation if necessary...
        TestNameResolverRegistry.set(new ZebrunnerCucumberNameResolver());
    }

    @BeforeClass(
            alwaysRun = true
    )
    public void setUpClass(ITestContext context) {
        XmlTest currentXmlTest = context.getCurrentXmlTest();
        Objects.requireNonNull(currentXmlTest);
        CucumberPropertiesProvider properties = currentXmlTest::getParameter;
        this.testNGCucumberRunner = new TestNGCucumberRunner(this.getClass(), properties);
    }

    @Test(
            groups = { "cucumber" },
            description = "Runs Cucumber Feature",
            dataProvider = "scenarios"
    )
    public void feature(PickleWrapper pickleWrapper,
            @SuppressWarnings("unused") FeatureWrapper featureWrapper,
            @SuppressWarnings("unused") String uniqueId,
            @SuppressWarnings("unused") List<String> scenarioOutlineParams) {
        this.testNGCucumberRunner.runScenario(pickleWrapper.getPickle());
    }

    @DataProvider
    public Object[][] scenarios(ITestContext context) {
        Object[][] parameters = this.testNGCucumberRunner.provideScenarios();
        Object[][] newParams = new Object[parameters.length][1];

        for (int i = 0; i < parameters.length; ++i) {
            newParams[i] = new Object[4];
            newParams[i][0] = parameters[i][0];
            newParams[i][1] = parameters[i][1];
            PickleWrapper pickleWrapper = (PickleWrapper) parameters[i][0];
            Pickle pickle = pickleWrapper.getPickle();

            String line = FEATURE_LINES.computeIfAbsent(pickle.getUri(), uri -> {
                try {
                    return FileUtils.readLines(new File(pickle.getUri()), Charset.defaultCharset());
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }).get(pickle.getLine() - 1);

            newParams[i][2] = StringUtils.deleteWhitespace(String.format("%s.%s.%s",
                    pickle.getUri(), pickle.getName(), line
            ));
            newParams[i][3] = StringUtils.contains(line, "|") ?
                    Arrays.stream(StringUtils.split(StringUtils.removeStart(StringUtils.trim(line), "|"), "|"))
                            .map(StringUtils::trim)
                            .collect(Collectors.toList()) :
                    List.of();
        }
        return newParams;
    }

    @AfterClass(
            alwaysRun = true
    )
    public void tearDownClass() {
        if (this.testNGCucumberRunner != null) {
            this.testNGCucumberRunner.finish();
        }
    }
}

