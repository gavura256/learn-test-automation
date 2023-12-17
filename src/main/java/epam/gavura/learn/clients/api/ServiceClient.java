package epam.gavura.learn.clients.api;

import com.epam.reportportal.listeners.LogLevel;
import com.epam.reportportal.restassured.ReportPortalRestAssuredLoggingFilter;
import epam.gavura.learn.clients.properties.PropertiesClient;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

@Slf4j
public class ServiceClient {

    private final RequestSpecification requestSpecification;

    static {
        RestAssured.filters(new ReportPortalRestAssuredLoggingFilter(42, LogLevel.INFO));
    }

    public ServiceClient() {
        log.info(">>> REQUEST");
        this.requestSpecification = RestAssured.given()
            .baseUri(PropertiesClient.getInstance().getBaseUrl())
            .contentType(ContentType.JSON)
            .auth().oauth2(PropertiesClient.getInstance().getUser().getUserToken())
            .log().method()
            .log().uri()
            .log().headers()
            .log().body(true);
    }


    protected ResponseHandler defaultGet(String endpoint) {
        return new ResponseHandler(requestSpecification.request(Method.GET, URI.create(endpoint)));
    }

    protected ResponseHandler defaultPut(String endpoint, Object body) {
        return new ResponseHandler(requestSpecification.body(body).request(Method.PUT, endpoint));
    }

    protected ResponseHandler defaultPost(String endpoint, Object body) {
        return new ResponseHandler(requestSpecification.body(body).request(Method.POST, endpoint));
    }

    protected ResponseHandler defaultDelete(String endpoint) {
        return new ResponseHandler(requestSpecification.request(Method.DELETE, endpoint));
    }
}
