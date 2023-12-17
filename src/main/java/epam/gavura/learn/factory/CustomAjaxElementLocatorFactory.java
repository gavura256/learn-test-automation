package epam.gavura.learn.factory;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.AjaxElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import java.lang.reflect.Field;

@Slf4j
public class CustomAjaxElementLocatorFactory implements ElementLocatorFactory {

    private final WebDriver driver;
    private final int timeOutInSeconds;

    public CustomAjaxElementLocatorFactory(WebDriver driver, int timeOutInSeconds) {
        this.driver = driver;
        this.timeOutInSeconds = timeOutInSeconds;
    }

    @Override
    public ElementLocator createLocator(Field field) {
        return new AjaxElementLocator(driver, field, timeOutInSeconds) {
            @Override
            protected boolean isElementUsable(WebElement element) {
                boolean isUsable = false;
                try {
                    isUsable = element.isEnabled() && element.isDisplayed();
                } catch (StaleElementReferenceException e) {
                    log.info("StaleElementReferenceException occurred");
                } catch (ElementNotInteractableException e) {
                    log.info("ElementNotInteractableException occurred");
                }
                return isUsable;
            }
        };
    }
}
