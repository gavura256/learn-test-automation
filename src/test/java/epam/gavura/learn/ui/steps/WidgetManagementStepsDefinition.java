package epam.gavura.learn.ui.steps;

import com.google.inject.Inject;
import epam.gavura.learn.pages.dashboard.CreateWidgetPopUp;
import epam.gavura.learn.pages.dashboard.DashboardPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class WidgetManagementStepsDefinition {
    private final CreateWidgetPopUp createWidgetPopUp;
    private final DashboardPage dashboardPage;

    @Inject
    public WidgetManagementStepsDefinition(CreateWidgetPopUp createWidgetPopUp, DashboardPage dashboardPage) {
        this.createWidgetPopUp = createWidgetPopUp;
        this.dashboardPage = dashboardPage;
    }

    @Given("User clicks Add new widget button")
    public void userClicksAddNewWidgetButton() {
        dashboardPage.clickOnAddNewWidget();
    }

    @Given("User chose first widget type")
    public void userChoseFirstWidgetType() {
        createWidgetPopUp.selectFirstWidgetType()
            .clickOnNextStepButton();
    }

    @Given("User fills in widget name with random value")
    public void userFillsInWidgetNameWithRandomValue() {
        createWidgetPopUp.fillsWidgetName(RandomStringUtils.randomAlphabetic(10))
            .clickOnAddWidgetButton();
    }

    @Given("New widget should be created")
    public void newWidgetShouldBeCreated() {
        assertThat(dashboardPage.getAllWidgets())
            .isNotEmpty();
    }

    @When("User resize widget")
    public void userResizeWidget() {
        dashboardPage.resizeWidget(400, 400);
    }

    @Then("the widget should be resized")
    public void theWidgetShouldBeResized() {
        assertThat(dashboardPage.getWidgetSize().height)
            .as("Widget height is not correct")
            .isEqualTo(400);
        assertThat(dashboardPage.getWidgetSize().width)
            .as("Widget width is not correct")
            .isEqualTo(400);
    }

    @Given("User chose {string}")
    public void userChose(String filterName) {
        createWidgetPopUp.searchFilterByName(filterName)
            .choseFilterResult()
            .clickOnNextStepButton();
    }
}
