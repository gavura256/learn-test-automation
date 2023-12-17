package epam.gavura.learn.api;

import epam.gavura.learn.clients.api.DashboardClient;
import epam.gavura.learn.clients.properties.PropertiesClient;
import epam.gavura.learn.models.dashboard.ErrorObject;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static epam.gavura.learn.constants.Groups.API_SMOKE;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;

class DeleteDashboardWithNonExitingIdNegativeTest {
    @Test
    @Tag(API_SMOKE)
    void checkDeleteDashboardWithNonExitingIdTest() {
        long id = Long.parseLong(RandomStringUtils.randomNumeric(10));
        ErrorObject expectedError = ErrorObject.builder()
            .errorCode(40422)
            .message(String.format("Dashboard with ID '%s' not found on project '%s'. Did you use correct Dashboard ID?",
                id,
                PropertiesClient.getInstance().getProjectName()))
            .build();

        ErrorObject actualError = new DashboardClient()
            .deleteDashboardById(id)
            .checkStatusCode(SC_NOT_FOUND)
            .getBodyAsObject(ErrorObject.class);

        assertThat(actualError)
            .as("Error is not expected")
            .isEqualTo(expectedError);
    }
}
