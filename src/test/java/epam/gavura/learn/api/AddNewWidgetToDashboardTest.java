package epam.gavura.learn.api;

import epam.gavura.learn.clients.api.DashboardClient;
import epam.gavura.learn.clients.properties.PropertiesClient;
import epam.gavura.learn.models.dashboard.AddWidgetRQ;
import epam.gavura.learn.models.dashboard.CreateDashboardRQ;
import epam.gavura.learn.models.dashboard.EntryCreatedRS;
import epam.gavura.learn.models.dashboard.ErrorObject;
import epam.gavura.learn.models.dashboard.Widget;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static epam.gavura.learn.constants.Groups.API_SMOKE;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

class AddNewWidgetToDashboardTest {
    private static long dashboardId;

    @BeforeAll
    static void beforeAddNewWidgetToDashboardTest() {
        dashboardId = new DashboardClient()
            .createNewDashBoard(CreateDashboardRQ.builder()
                .name(RandomStringUtils.randomAlphabetic(10))
                .build())
            .checkStatusCode(SC_CREATED)
            .getBodyAsObject(EntryCreatedRS.class)
            .getId();
    }

    @Test
    @Tag(API_SMOKE)
    void checkAddNewWidgetToDashboardWithEmptyBodyTest() {
        ErrorObject actualError = new DashboardClient()
            .addWidgetToDashboard(dashboardId, AddWidgetRQ.builder().build())
            .checkStatusCode(400)
            .getBodyAsObject(ErrorObject.class);
        assertThat(actualError)
            .as("When adding an empty widget, the response is not as expected")
            .isEqualTo(ErrorObject.builder()
                .errorCode(4001)
                .message("Incorrect Request. [Field 'addWidget' should not be null.] ")
                .build());
    }

    @Test
    @Tag(API_SMOKE)
    void checkAddNewWidgetToDashboardWithNonExistingIdTest() {
        new DashboardClient().getDashboardById(dashboardId);
        ErrorObject actualError = new DashboardClient()
            .addWidgetToDashboard(dashboardId,
                AddWidgetRQ.builder()
                    .addWidget(
                        Widget.builder()
                            .widgetId(0)
                            .build())
                    .build())
            .checkStatusCode(404)
            .getBodyAsObject(ErrorObject.class);
        assertThat(actualError)
            .as("When adding an empty widget, the response is not as expected")
            .isEqualTo(ErrorObject.builder()
                .errorCode(40420)
                .message(String.format("Widget with ID '0' not found on project '%s'. Did you use correct Widget ID?",
                    PropertiesClient.getInstance().getProjectName()))
                .build());
    }


    @AfterAll
    static void afterAddNewWidgetToDashboardTest() {
        new DashboardClient()
            .deleteDashboardById(dashboardId)
            .checkStatusCode(SC_OK);
    }
}
