package epam.gavura.learn.pages.base;

import epam.gavura.learn.clients.properties.PropertiesClient;
import epam.gavura.learn.clients.ui.WebDriverSingleton;
import epam.gavura.learn.factory.CustomAjaxElementLocatorFactory;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Slf4j
public class BasePage {
    private static final int TIME_OUT_IN_SECONDS = 30;

    protected static final String BASE_URL = PropertiesClient.getInstance().getBaseUrl();

    @FindBy(xpath = "//*[contains(@class, 'notificationItem__message-container')]/p")
    private List<WebElement> notifications;

    public BasePage() {
        PageFactory.initElements(
            new CustomAjaxElementLocatorFactory(WebDriverSingleton.getWebDriver(), TIME_OUT_IN_SECONDS),
            this);
    }

    public boolean notificationContainerContainsMessage(String message) {
        return notifications.stream()
            .anyMatch(notification -> notification.getText().contains(message));
    }
}
