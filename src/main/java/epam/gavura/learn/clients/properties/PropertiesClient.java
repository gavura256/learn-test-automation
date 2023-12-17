package epam.gavura.learn.clients.properties;

import epam.gavura.learn.models.User;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.Optional;
import java.util.ResourceBundle;

@Getter
public final class PropertiesClient {

    @Getter(AccessLevel.NONE)
    private static PropertiesClient uniqueInstance;
    private final String baseUrl;
    private final String groups;
    private final String projectName;
    private final User user;

    private static final String CONFIGURATION_VALUE = "configuration";
    private static final String BASE_URL_VALUE = "baseUrl";
    private static final String GROUPS_VALUE = "groups";
    private static final String PROJECT_NAME_VALUE = "projectName";
    private static final String USER_NAME_VALUE = "userName";
    private static final String PASSWORD_VALUE = "password";
    private static final String USER_TOKEN_VALUE = "userToken";

    private PropertiesClient() {
        user = User.builder()
            .userName(getProperty(USER_NAME_VALUE))
            .password(getProperty(PASSWORD_VALUE))
            .userToken(getProperty(USER_TOKEN_VALUE))
            .build();
        baseUrl = getProperty(BASE_URL_VALUE);
        groups = getProperty(GROUPS_VALUE);
        projectName = getProperty(PROJECT_NAME_VALUE);
    }

    public static PropertiesClient getInstance() {
        synchronized (PropertiesClient.class) {
            if (uniqueInstance == null) {
                uniqueInstance = new PropertiesClient();
            }
        }

        return uniqueInstance;
    }

    private String getProperty(String propertyKey) {
        return Optional.ofNullable(System.getProperty(propertyKey))
            .orElse(ResourceBundle.getBundle(CONFIGURATION_VALUE).getString(propertyKey));
    }
}
