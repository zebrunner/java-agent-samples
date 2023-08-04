# Zebrunner Geb + Spock reporting agent sample

## sample-geb-spock-gradle-logback

Your guide to run the first Geb test with reporting to Zebrunner.

## Preconditions

Before configuring Zebrunner Spock reporting agent, you need to create a free Zebrunner workspace at https://zebrunner.com/.

## Configuration

### _Step 1: Basic project setup_

Clone the Zebrunner's [samples](https://github.com/zebrunner/java-agent-samples) repository and navigate to the code directory as shown below:

```
git clone https://github.com/zebrunner/java-agent-samples.git
cd java-agent-samples/sample-geb-spock-gradle-logback
```

### _Step 2: Configure credentials and reporting_

In Zebrunner:
- Navigate to "Account and profile" section by clicking on the User icon from the top right side;
- Click on "API Tokens" tab;
- Press "Token" button, create a token and copy it before closing the dialog (you won't be able to see the token later).

Define launch configuration (override defaults if needed) with obtained token and copy content below to the `src/test/resources/agent.properties` file.

#### **`agent.properties`**
```properties
reporting.enabled=true
reporting.project-key=DEF
reporting.server.hostname=https://<workspace>.zebrunner.com/
reporting.server.access-token=<token>
reporting.run.display-name=Zebrunner Demo Launch
reporting.run.build=2.41.2.2431-SNAPSHOT
reporting.run.environment=QA
```

### _Step 3: Configure Selenium WebDriver_

In Zebrunner:

- Navigate to "Automation -> Launches" page by selecting the menu from left sidebar;
- Click on `key` icon from the top right side on Launches page. 
- You will see 'Hub Access' popup where you can copy URL of remote Zebrunner Selenium Grid.

Configure desired capabilities and insert snippet below with obtained Zebrunner Selenium Grid information to the `src/test/resources/GebConfig.groovy` file.

#### **`GebConfig.groovy`**
```
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.RemoteWebDriver

def HUB_URL = null // https://user:pass@example.com/wd/hub

driver = {
    ChromeOptions options = new ChromeOptions()
    options.setPlatformName("MAC")
    options.setBrowserVersion("113.0")
    options.setCapability("enableVideo", true)

    new RemoteWebDriver(HUB_URL, options)
}
```

### _Step 4: Execute the tests_

Please run the following command in the terminal:

```
./gradlew test
```

### _Step 5: View test results_

Congratulations! You have just completed a reporting setup for a test project!
Now you can go to the Launches page to see the results.

To learn more about advanced reporting capabilities, supported Selenium Grid providers, test cases mapping and more please go to the [documentation](https://zebrunner.com/documentation/reporting/spock/).
