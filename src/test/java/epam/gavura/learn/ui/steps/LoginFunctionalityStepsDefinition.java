package epam.gavura.learn.ui.steps;

import com.google.inject.Inject;
import epam.gavura.learn.clients.properties.PropertiesClient;
import epam.gavura.learn.pages.dashboard.DashboardPage;
import epam.gavura.learn.pages.login.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;

public class LoginFunctionalityStepsDefinition {

    private final LoginPage loginPage;
    private final DashboardPage dashboardPage;

    @Inject
    public LoginFunctionalityStepsDefinition(LoginPage loginPage, DashboardPage dashboardPage) {
        this.loginPage = loginPage;
        this.dashboardPage = dashboardPage;
    }

    @Given("User opens ReportPortal login page")
    public LoginPage userOpensReportPortalLoginPage() {
        return loginPage.open();
    }

    @When("User inputs userName with {string}")
    public void userInputsUserNameWith(String userName) {
        loginPage.inputUserName("validUserName".equals(userName)
            ? PropertiesClient.getInstance().getUser().getUserName()
            : RandomStringUtils.randomAlphabetic(8)
        );
    }

    @When("User inputs password with {string}")
    public void userInputsPasswordWith(String password) {
        loginPage.inputPassword("validPassword".equals(password)
            ? PropertiesClient.getInstance().getUser().getPassword()
            : RandomStringUtils.randomAlphabetic(8)
        );
    }

    @When("User clicks Sign In button")
    public DashboardPage userClicksSignInButton() {
        return loginPage.clickSignIn();
    }

    @Then("User should see alert {string}")
    public void userShouldSeeAlert(String alert) {
        String successMessage = "Signed in successfully";
        String errorMessage = "An error occurred while connecting to server: You do not have enough permissions. Bad credentials";
        if ("successSignIn".equals(alert)) {
            dashboardPage.notificationContainerContainsMessage(successMessage);
        } else {
            dashboardPage.notificationContainerContainsMessage(errorMessage);
        }
    }
}
