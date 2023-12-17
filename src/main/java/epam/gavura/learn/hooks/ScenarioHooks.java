package epam.gavura.learn.hooks;

import com.epam.reportportal.listeners.LogLevel;
import com.epam.reportportal.service.ReportPortal;
import epam.gavura.learn.clients.ui.WebDriverSingleton;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.util.Date;

import static epam.gavura.learn.constants.Groups.UI_SMOKE;

@Slf4j
public class ScenarioHooks {
    @Before(UI_SMOKE)
    public static void setupMethod() {
        WebDriverSingleton.getWebDriver()
            .manage()
            .window()
            .maximize();
    }

    @After(UI_SMOKE)
    public void tearDownMethod(Scenario scenario) {
        if (scenario.isFailed()) {
            ReportPortal.emitLog(
                scenario.getName(),
                LogLevel.INFO.name(),
                new Date(),
                ((TakesScreenshot) WebDriverSingleton.getWebDriver()).getScreenshotAs(OutputType.FILE));
        }

        WebDriverSingleton.quit();
    }
}
