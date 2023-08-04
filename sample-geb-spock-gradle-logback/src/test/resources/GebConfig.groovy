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
