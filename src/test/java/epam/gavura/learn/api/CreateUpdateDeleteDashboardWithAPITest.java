package epam.gavura.learn.api;

import epam.gavura.learn.clients.api.DashboardClient;
import epam.gavura.learn.clients.properties.PropertiesClient;
import epam.gavura.learn.models.dashboard.CreateDashboardRQ;
import epam.gavura.learn.models.dashboard.DashboardResource;
import epam.gavura.learn.models.dashboard.DashboardResources;
import epam.gavura.learn.models.dashboard.EntryCreatedRS;
import epam.gavura.learn.models.dashboard.OperationCompletionRS;
import epam.gavura.learn.models.dashboard.UpdateDashboardRQ;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static epam.gavura.learn.constants.Groups.API_SMOKE;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CreateUpdateDeleteDashboardWithAPITest {
    private static final String RANDOM_DASHBOARD_NAME = RandomStringUtils.randomAlphabetic(10);
    private static long myFancyDashboardId;

    @BeforeAll
    static void beforeCreateUpdateDeleteDashboardWithAPITest() {
        List<DashboardResource> list = new DashboardClient()
            .getAllDashboards()
            .checkStatusCode(SC_OK)
            .getBodyAsObject(DashboardResources.class)
            .getDashboardResourceList()
            .stream()
            .filter(dashboardResource -> RANDOM_DASHBOARD_NAME.equals(dashboardResource.getName()))
            .toList();

        assertThat(list)
            .as("There should be no dashboard with this name")
            .isEmpty();
    }

    @Test
    @Tag(API_SMOKE)
    @Order(0)
    void checkCreateNewDashboardTest() {
        CreateDashboardRQ expectedDashboard = CreateDashboardRQ.builder()
            .name(RANDOM_DASHBOARD_NAME)
            .build();
        myFancyDashboardId = new DashboardClient()
            .createNewDashBoard(expectedDashboard)
            .checkStatusCode(SC_CREATED)
            .getBodyAsObject(EntryCreatedRS.class)
            .getId();
        DashboardResource myFancyDashboard = new DashboardClient()
            .getDashboardById(myFancyDashboardId)
            .checkStatusCode(SC_OK)
            .getBodyAsObject(DashboardResource.class);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(myFancyDashboard.getName())
                .as("Unexpected name of the dashboard")
                .isEqualTo(expectedDashboard.getName());
            softly.assertThat(myFancyDashboard.getDescription())
                .as("Unexpected description of the dashboard")
                .isEqualTo(expectedDashboard.getDescription());
            softly.assertThat(myFancyDashboard.getOwner())
                .as("Unexpected owner of the dashboard")
                .isEqualTo(PropertiesClient.getInstance().getUser().getUserName());
        });
    }

    @Test
    @Tag(API_SMOKE)
    @Order(1)
    void checkUpdateDashboardTest() {
        String expectedNewDashboardName = RandomStringUtils.randomAlphabetic(10);
        UpdateDashboardRQ bodyToUpdate = UpdateDashboardRQ.builder()
            .name(expectedNewDashboardName)
            .build();
        String resultMessage = new DashboardClient()
            .updateDashboard(myFancyDashboardId, bodyToUpdate)
            .checkStatusCode(SC_OK)
            .getBodyAsObject(OperationCompletionRS.class)
            .getMessage();

        assertThat(resultMessage)
            .as("Should be not empty success message")
            .isEqualTo(String.format("Dashboard with ID = '%s' successfully updated", myFancyDashboardId));

        String actualDashboardName = new DashboardClient()
            .getDashboardById(myFancyDashboardId)
            .checkStatusCode(SC_OK)
            .getBodyAsObject(DashboardResource.class)
            .getName();

        assertThat(actualDashboardName)
            .as("Dashboard name hadn't been updated")
            .isEqualTo(expectedNewDashboardName);
    }

    @Test
    @Tag(API_SMOKE)
    @Order(2)
    void checkDeleteDashboardTest() {
        String resultMessage = new DashboardClient()
            .deleteDashboardById(myFancyDashboardId)
            .checkStatusCode(SC_OK)
            .getBodyAsObject(OperationCompletionRS.class)
            .getMessage();
        assertThat(resultMessage)
            .as("Delete message is not expected")
            .isEqualTo(String.format("Dashboard with ID = '%s' successfully deleted.", myFancyDashboardId));

        List<DashboardResource> list = new DashboardClient()
            .getAllDashboards()
            .checkStatusCode(SC_OK)
            .getBodyAsObject(DashboardResources.class)
            .getDashboardResourceList()
            .stream()
            .filter(dashboardResource -> dashboardResource.getId() == myFancyDashboardId)
            .toList();

        assertThat(list)
            .as("There should be no dashboard with this id")
            .isEmpty();
    }
}
