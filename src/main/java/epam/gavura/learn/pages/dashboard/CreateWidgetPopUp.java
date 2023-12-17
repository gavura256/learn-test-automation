package epam.gavura.learn.pages.dashboard;

import epam.gavura.learn.pages.base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CreateWidgetPopUp extends BasePage {
    @FindBy(xpath = "//input[@placeholder = 'Search filter by name']")
    private WebElement searchFilterByNameField;
    @FindBy(xpath = "//div[contains(@class, 'scrollWrapper__scrolling-content')]/div[contains(@class, 'filtersItem__filter-item')]")
    private WebElement filterResult;
    @FindBy(xpath = "//input[@placeholder = 'Enter widget name']")
    private WebElement enterWidgetNameField;
    @FindBy(xpath = "//div[contains(@class, 'widgetTypeItem__widget-type-item-name')]")
    private List<WebElement> allWidgetTypes;
    @FindBy(xpath = "//span[contains(@class, 'ghostButton') and text()='Next step']")
    private WebElement nextStepButton;
    @FindBy(xpath = "//button[contains(@class, 'bigButton__big-button--') and text()='Add']")
    private WebElement addWidgetButton;

    public CreateWidgetPopUp selectFirstWidgetType() {
        allWidgetTypes.stream()
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("No widget types found"))
            .click();
        return this;
    }

    public CreateWidgetPopUp clickOnNextStepButton() {
        nextStepButton.click();
        return this;
    }

    public CreateWidgetPopUp searchFilterByName(String filterName) {
        searchFilterByNameField.sendKeys(filterName);
        return this;
    }

    public CreateWidgetPopUp choseFilterResult() {
        filterResult.click();
        return this;
    }

    public CreateWidgetPopUp fillsWidgetName(String widgetName) {
        enterWidgetNameField.sendKeys(widgetName);
        return this;
    }

    public void clickOnAddWidgetButton() {
        addWidgetButton.click();
    }
}
