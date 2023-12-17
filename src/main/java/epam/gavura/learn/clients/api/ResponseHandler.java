package epam.gavura.learn.clients.api;

import io.restassured.response.Response;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResponseHandler {
    private final Response httpResponse;
    @Getter
    private final int statusCode;

    public ResponseHandler(Response httpResponse) {
        log.info("<<< RESPONSE");
        httpResponse.then()
            .log().headers()
            .log().status()
            .log().body(true);
        this.httpResponse = httpResponse;
        this.statusCode = this.httpResponse.getStatusCode();
    }

    public ResponseHandler checkStatusCode(int httpStatus) {
        if (statusCode != httpStatus) {
            throw new IllegalArgumentException(
                String.format(
                    "The status code in the response is not expected, should be %s but was %s",
                    httpStatus,
                    statusCode));
        }

        return this;
    }

    public <T> T getBodyAsObject(Class<T> clazz) {
        return httpResponse.as(clazz);
    }
}
