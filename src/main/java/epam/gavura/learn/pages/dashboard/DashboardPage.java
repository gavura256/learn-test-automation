package epam.gavura.learn.pages.dashboard;

import epam.gavura.learn.clients.properties.PropertiesClient;
import epam.gavura.learn.clients.ui.WebDriverSingleton;
import epam.gavura.learn.pages.base.BasePage;
import lombok.Getter;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;


public class DashboardPage extends BasePage {

    private static final String DASHBOARD_PAGE_URI = String.format("/ui/#%s/dashboard", PropertiesClient.getInstance().getProjectName());

    @FindBy(xpath = "//input[@placeholder='Enter dashboard name']")
    private WebElement enterDashboardNameField;

    @FindBy(xpath = "//span[contains(text(), 'Add New Dashboard')]")
    private WebElement addNewDashboardButton;

    @FindBy(xpath = "//span[contains(@class, 'ghostButton') and text()='Edit']")
    private WebElement editDashboardButton;

    @FindBy(xpath = "//button[contains(@class, 'bigButton__big-button--') and text()='Add']")
    private WebElement confirmAddingButton;

    @FindBy(xpath = "//span[contains(@class, 'ghostButton') and text()='Delete']")
    private WebElement deleteDashboardButton;

    @FindBy(xpath = "//button[contains(@class, 'bigButton__big-button--') and text()='Delete']")
    private WebElement confirmDeletingButton;

    @FindBy(xpath = "//button[contains(@class, 'bigButton__big-button--') and text()='Update']")
    private WebElement confirmUpdatingButton;

    @FindBy(xpath = "//ul[contains(@class, 'pageBreadcrumbs')]/*[2]/*")
    private WebElement dashboardName;

    @FindBy(xpath = "//span[contains(@class, 'ghostButton') and text()='Add new widget']")
    private WebElement addNewWidgetButton;

    @Getter
    @FindBy(xpath = "//div[@class='react-grid-layout']/child::*")
    private List<WebElement> allWidgets;


    public void open() {
        WebDriverSingleton.getWebDriver().get(BASE_URL + DASHBOARD_PAGE_URI);
    }

    public void clickOnAddNewDashboard() {
        addNewDashboardButton.click();
    }

    public void fullsInDashboardNameAs(String name) {
        enterDashboardNameField.sendKeys(name);
    }

    public void confirmAdding() {
        confirmAddingButton.click();
    }

    public void confirmDeleting() {
        confirmDeletingButton.click();
    }

    public void clicksOnEditButton() {
        editDashboardButton.click();
    }

    public void putNewDashboardNameAs(String string) {
        enterDashboardNameField.sendKeys(string);
    }

    public void confirmUpdating() {
        confirmUpdatingButton.click();
    }

    public String getDashboardName() {
        return dashboardName.getText();
    }

    public void clickOnDeleteButton() {
        deleteDashboardButton.click();
    }

    public void clickOnAddNewWidget() {
        addNewWidgetButton.click();
    }

    public void resizeWidget(int width, int height) {
        String script = String.format("arguments[0].style.width='%spx'; arguments[0].style.height='%spx';", width, height);
        ((JavascriptExecutor) WebDriverSingleton.getWebDriver()).executeScript(script, allWidgets.get(0));
    }

    public Dimension getWidgetSize() {
        return allWidgets.get(0).getSize();
    }
}
