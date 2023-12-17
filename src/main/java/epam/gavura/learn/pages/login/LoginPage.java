package epam.gavura.learn.pages.login;

import epam.gavura.learn.clients.ui.WebDriverSingleton;
import epam.gavura.learn.pages.base.BasePage;
import epam.gavura.learn.pages.dashboard.DashboardPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {
    @FindBy(xpath = "//input[@type = 'text']")
    private WebElement loginField;
    @FindBy(xpath = "//input[@type = 'password']")
    private WebElement passwordField;
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement submitButton;
    private static final String LOGIN_PAGE_URI = "/ui/#login";

    public LoginPage open() {
        WebDriverSingleton.getWebDriver().get(BASE_URL + LOGIN_PAGE_URI);
        return this;
    }

    public LoginPage inputUserName(String userName) {
        loginField.sendKeys(userName);

        return this;
    }

    public LoginPage inputPassword(String password) {
        passwordField.sendKeys(password);

        return this;
    }

    public DashboardPage clickSignIn() {
        submitButton.click();

        return new DashboardPage();
    }
}
