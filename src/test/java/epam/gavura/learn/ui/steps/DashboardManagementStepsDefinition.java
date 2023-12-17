package epam.gavura.learn.ui.steps;

import com.google.inject.Inject;
import epam.gavura.learn.clients.properties.PropertiesClient;
import epam.gavura.learn.models.User;
import epam.gavura.learn.pages.dashboard.DashboardPage;
import epam.gavura.learn.pages.login.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

public class DashboardManagementStepsDefinition {

    private final LoginPage loginPage;
    private final DashboardPage dashboardPage;
    private final String randomDashboardName = RandomStringUtils.randomAlphabetic(10);
    private final String newRandomDashboardName = RandomStringUtils.randomAlphabetic(10);

    @Inject
    public DashboardManagementStepsDefinition(LoginPage loginPage, DashboardPage dashboardPage) {
        this.loginPage = loginPage;
        this.dashboardPage = dashboardPage;
    }

    @Given("User has been login in")
    public void userHasBeenLoginIn() {
        User user = PropertiesClient.getInstance().getUser();
        loginPage.open()
            .inputUserName(user.getUserName())
            .inputPassword(user.getPassword())
            .clickSignIn();
    }

    @Given("User opens Dashboard page")
    public void userOpensDashboardPage() {
        dashboardPage.open();
    }

    @When("User clicks Add New Dashboard button")
    public void userClicksAddNewDashboardButton() {
        dashboardPage.clickOnAddNewDashboard();
    }

    @When("User fills in dashboard name with random value")
    public void userFillsInDashboardNameWithRandomValue() {
        dashboardPage.fullsInDashboardNameAs(randomDashboardName);
    }

    @When("User clicks Add button")
    public void userClicksAddButton() {
        dashboardPage.confirmAdding();
    }

    @Then("User should see message {string}")
    public void userShouldSeeMessage(String notificationMessage) {
        await().atMost(30, TimeUnit.SECONDS)
            .pollDelay(1, TimeUnit.SECONDS)
            .untilAsserted(() -> assertThat(dashboardPage.notificationContainerContainsMessage(notificationMessage)).isTrue());
    }

    @Then("Dashboard name should be changed")
    public void dashboardNameShouldBeChanged() {
        assertThat(dashboardPage.getDashboardName()).isNotEqualTo(randomDashboardName);
    }

    @When("User clicks edit to update the dashboard")
    public void userClicksEditToUpdateTheDashboard() {
        dashboardPage.clicksOnEditButton();
    }

    @When("User updates dashboard name with random value")
    public void userUpdatesDashboardNameWithRandomValue() {
        dashboardPage.putNewDashboardNameAs(newRandomDashboardName);
    }

    @When("User clicks the Update button")
    public void userClicksTheUpdateButton() {
        dashboardPage.confirmUpdating();
    }

    @When("User clicks Delete button")
    public void userClicksDeleteButton() {
        dashboardPage.clickOnDeleteButton();
    }

    @When("User confirms deletion")
    public void userConfirmsDeletion() {
        dashboardPage.confirmDeleting();
    }
}
