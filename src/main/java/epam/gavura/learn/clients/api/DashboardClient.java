package epam.gavura.learn.clients.api;

import epam.gavura.learn.clients.properties.PropertiesClient;
import epam.gavura.learn.models.dashboard.AddWidgetRQ;
import epam.gavura.learn.models.dashboard.CreateDashboardRQ;
import epam.gavura.learn.models.dashboard.UpdateDashboardRQ;

public class DashboardClient extends ServiceClient {

    private static final String DASHBOARD_ENDPOINT = "/api/v1/%s/dashboard/";
    private static final String ADD = "/add";

    private final String currentProjectName = PropertiesClient.getInstance().getProjectName();

    public ResponseHandler getAllDashboards() {
        return super.defaultGet(String.format(DASHBOARD_ENDPOINT, currentProjectName));
    }

    public ResponseHandler createNewDashBoard(CreateDashboardRQ body) {
        return super.defaultPost(String.format(DASHBOARD_ENDPOINT, currentProjectName), body);
    }

    public ResponseHandler getDashboardById(long id) {
        return super.defaultGet(String.format(DASHBOARD_ENDPOINT, currentProjectName).concat(String.valueOf(id)));
    }

    public ResponseHandler updateDashboard(long id, UpdateDashboardRQ body) {
        return super.defaultPut(String.format(DASHBOARD_ENDPOINT, currentProjectName).concat(String.valueOf(id)), body);
    }

    public ResponseHandler deleteDashboardById(long id) {
        return super.defaultDelete(String.format(DASHBOARD_ENDPOINT, currentProjectName).concat(String.valueOf(id)));
    }

    public ResponseHandler addWidgetToDashboard(long id, AddWidgetRQ body) {
        return super.defaultPut(String.format(DASHBOARD_ENDPOINT, currentProjectName).concat(String.valueOf(id).concat(ADD)), body);
    }
}
