package epam.gavura.learn.models.dashboard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class WidgetOptions {
    private boolean zoom;
    private String timeline;
    private String viewMode;
    private boolean latest;
    private boolean includeMethods;
    private String launchNameFilter;
}
