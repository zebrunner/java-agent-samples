package com.zebrunner.cucumber;

import com.zebrunner.agent.testng.core.testname.TestNameResolver;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import org.apache.commons.lang3.StringUtils;
import org.testng.ITestResult;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Default test name generator. If you need to use your own
 * implementation, specify it in ZebrunnerTestNGCucumberTests constructor. <br>
 * Result:<br>
 * for scenario: {@code Feature Name (Scenario Name)}<br>
 * for scenario outline: {@code Feature Name (Scenario Name)[First Column Value]}
 */
public class ZebrunnerCucumberNameResolver implements TestNameResolver {
    private static final String STR_FORMAT_TEST_NAME = "%s (%s)";
    private static final String FEATURE_NAME_OPTIONAL = "Optional";

    @Override
    @SuppressWarnings({ "unlikely-arg-type" })
    public String resolve(ITestResult result) {
        if (result.getTestContext() == null) {
            throw new IllegalArgumentException("Unable to set test name without testContext!");
        }

        Optional<PickleWrapper> pickleWrapper = Arrays.stream(result.getParameters())
                .filter(PickleWrapper.class::isInstance)
                .findAny()
                .map(p -> (PickleWrapper) p);

        Optional<FeatureWrapper> featureWrapper = Arrays.stream(result.getParameters())
                .filter(FeatureWrapper.class::isInstance)
                .findAny()
                .map(p -> (FeatureWrapper) p);

        if (pickleWrapper.isEmpty() || featureWrapper.isEmpty()) {
            return result.getTestContext().getCurrentXmlTest().getName();
        }

        Optional<List<String>> outlineParams = Arrays.stream(result.getParameters())
                .filter(List.class::isInstance)
                .findAny()
                .map(p -> (List<String>) p);

        if (outlineParams.isEmpty()) {
            return result.getTestContext().getCurrentXmlTest().getName();
        }

        return generateTestName(pickleWrapper.get(), featureWrapper.get(), outlineParams.get());
    }

    static String generateTestName(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper, List<String> scenarioOutlineParams) {
        return ZebrunnerCucumberNameResolver.prepareTestName(STR_FORMAT_TEST_NAME, pickleWrapper, featureWrapper, scenarioOutlineParams);
    }

    private static String prepareTestName(String strFormat, PickleWrapper pickleWrapper, FeatureWrapper featureWrapper,
            List<String> scenarioOutlineParams) {
        String featureName = cleanQuotes(featureWrapper.toString());
        if (featureName.startsWith(FEATURE_NAME_OPTIONAL + "[")) {
            featureName = featureName.replace(FEATURE_NAME_OPTIONAL, "");
        }
        String name = String.format(strFormat, cleanBrackets(featureName), cleanQuotes(pickleWrapper.toString()));
        if (!scenarioOutlineParams.isEmpty()) {
            name = name + "[ " + scenarioOutlineParams.get(0) + " ]";
        }
        return name;
    }

    private static String cleanQuotes(String originalString) {
        return StringUtils.removeEnd(StringUtils.removeStart(originalString, "\""), "\"");
    }

    private static String cleanBrackets(String originalString) {
        return StringUtils.removeEnd(StringUtils.removeStart(originalString, "["), "]");
    }
}
