package epam.gavura.learn.clients.ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.HashMap;

@UtilityClass
public final class WebDriverSingleton {
    private static final ThreadLocal<WebDriver> DRIVERS = new ThreadLocal<>();

    public static WebDriver getWebDriver() {
        if (DRIVERS.get() == null) {
            DRIVERS.set(new DriverFactory().getDriver());
        }

        return DRIVERS.get();
    }

    public static void quit() {
        DRIVERS.get().quit();
        DRIVERS.remove();
    }

    private static final class DriverFactory {
        @SneakyThrows
        public WebDriver getDriver() {
            WebDriver webdriver;
            if (System.getProperty("remoteUrl") != null) {
                ChromeOptions chromeOptions = new ChromeOptions();
                HashMap<String, Object> ltOptions = new HashMap<>();
                ltOptions.put("project", "Learn Test automation");
                ltOptions.put("w3c", true);
                ltOptions.put("plugin", "java-junit");
                ltOptions.put("build", "Learn Test automation");
                chromeOptions.setCapability("LT:Options", ltOptions);

                webdriver = new RemoteWebDriver(
                    new URL(System.getProperty("remoteUrl")),
                    chromeOptions);
            } else {
                WebDriverManager.chromedriver().setup();

                webdriver = new ChromeDriver();
            }
            return webdriver;
        }
    }
}
