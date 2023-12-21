package epam.gavura.learn.hooks;

import com.epam.reportportal.listeners.LogLevel;
import com.epam.reportportal.service.ReportPortal;
import epam.gavura.learn.clients.ui.WebDriverSingleton;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import static epam.gavura.learn.constants.Groups.UI_SMOKE;

@Slf4j
@UtilityClass
public class ScenarioHooks {
    @Before(UI_SMOKE)
    public static void setupMethod() {
        WebDriverSingleton.getWebDriver().manage().window().maximize();
    }

    @After(UI_SMOKE)
    public static void tearDownMethod(Scenario scenario) {
        if (scenario.isFailed()) {
            log.info("Scenario failed, taking screenshot");
            File screenshot = ((TakesScreenshot) WebDriverSingleton.getWebDriver()).getScreenshotAs(OutputType.FILE);
            String name = scenario.getName();
            saveScreenshot(name);
            sendScreenShotToReportPortal(name, screenshot);
        }
        WebDriverSingleton.quit();
    }

    private static void saveScreenshot(String scenarioName) {
        try {
            File screenshotFile = ((TakesScreenshot) WebDriverSingleton.getWebDriver()).getScreenshotAs(OutputType.FILE);
            Path folderPath = Paths.get("target", "screenshots");
            Files.createDirectories(folderPath);
            String filePath = folderPath.resolve(scenarioName + "_" + RandomStringUtils.randomNumeric(5) + ".png").toString();
            Files.move(screenshotFile.toPath(), Paths.get(filePath));
            log.info("Screenshot saved: " + getClickablePath(filePath));
        } catch (IOException e) {
            log.info("Error while saving screenshot: " + e.getMessage());
        }
    }

    private static void sendScreenShotToReportPortal(String name, File screenshot) {
        ReportPortal.emitLog(name, LogLevel.INFO.name(), new Date(), screenshot);
    }

    private static String getClickablePath(String filePath) {
        // ANSI escape code for hyperlinks
        String ansiHyperlink = "\u001B]8;;%s\u0007%s\u001B]8;;\u0007";

        // Format the hyperlink with the file path
        return String.format(ansiHyperlink, "file://" + filePath, filePath);
    }
}
