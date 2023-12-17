package epam.gavura.learn.api;

import epam.gavura.learn.clients.api.DashboardClient;
import epam.gavura.learn.models.dashboard.CreateDashboardRQ;
import epam.gavura.learn.models.dashboard.ErrorObject;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static epam.gavura.learn.constants.Groups.API_SMOKE;
import static org.assertj.core.api.Assertions.assertThat;

class CreateNewDashBoardNegativeTest {
    @Test
    @Tag(API_SMOKE)
    void checkCreateNewDashBoardWithEmptyBodyNegativeTest() {
        ErrorObject actualError = new DashboardClient().createNewDashBoard(CreateDashboardRQ.builder().build())
            .checkStatusCode(400)
            .getBodyAsObject(ErrorObject.class);

        assertThat(actualError)
            .as("User cannot create new dashboard with empty body")
            .isEqualTo(ErrorObject.builder()
                .errorCode(4001)
                .message("Incorrect Request. [Field 'name' should not be null.] ")
                .build());
    }
}
