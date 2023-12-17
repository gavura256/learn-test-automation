package epam.gavura.learn.api;

import epam.gavura.learn.clients.api.DashboardClient;
import epam.gavura.learn.clients.properties.PropertiesClient;
import epam.gavura.learn.models.dashboard.DashboardResources;
import epam.gavura.learn.models.dashboard.ErrorObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static epam.gavura.learn.constants.Groups.API_SMOKE;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class GetDashboardTest {

    @Test
    @Tag(API_SMOKE)
    void checkGetAllDashboardsPositiveTest() {
        var dashboard = new DashboardClient()
            .getAllDashboards()
            .checkStatusCode(SC_OK)
            .getBodyAsObject(DashboardResources.class)
            .getDashboardResourceList()
            .stream()
            .filter(dashboardResource -> dashboardResource.getName().contains("DEMO DASHBOARD"))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("No context is present"));

        assertThat(dashboard.getOwner())
            .isEqualTo(PropertiesClient.getInstance().getUser().getUserName());
    }

    @Test
    @Tag(API_SMOKE)
    void checkGetDashboardByIdNegativeTest() {
        long id = Long.parseLong(RandomStringUtils.randomNumeric(10));
        ErrorObject actualError = new DashboardClient()
            .getDashboardById(id)
            .checkStatusCode(SC_NOT_FOUND)
            .getBodyAsObject(ErrorObject.class);

        assertThat(actualError)
            .as("There should be no dashboard with this id")
            .isEqualTo(ErrorObject.builder()
                .errorCode(40422)
                .message(String.format("Dashboard with ID '%s' not found on project '%s'. Did you use correct Dashboard ID?",
                    id,
                    PropertiesClient.getInstance().getProjectName()))
                .build());
    }
}
