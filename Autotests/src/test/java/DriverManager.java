import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class DriverManager {

    private String seleniumAddress = "http://148.251.234.76:4441/wd/hub";
    private String browserName = "chrome";

    private WebDriver driver;

    public WebDriver getDriver() throws MalformedURLException {

        if (driver == null) {
            if ("chrome".equalsIgnoreCase(browserName)) {
                System.setProperty("webdriver.chrome.driver", "./src/drivers/chromedriver.exe");
                driver = new ChromeDriver();
            }
            if ("firefox".equalsIgnoreCase(browserName)) {
                System.setProperty("webdriver.gecko.driver", "./src/drivers/geckodriver_64.exe");
                driver =  new FirefoxDriver();
            }
            if ("ie".equalsIgnoreCase(browserName)) {
                System.setProperty("webdriver.ie.driver", "./src/drivers/IEDriverServer_32.exe");
                driver =  new InternetExplorerDriver();
            }
            if ("edge".equalsIgnoreCase(browserName)){
                System.setProperty("webdriver.edge.driver", "./src/drivers/MicrosoftWebDriver.exe");
                driver =  new EdgeDriver();
            }
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().deleteAllCookies();

        return driver;
    }

    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    public String getDriverInfo() {
        return ((EventFiringWebDriver) driver).getWrappedDriver().toString();
    }
}
