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
public class Widget {
    private String widgetName;
    private int widgetId;
    private String widgetType;
    private WidgetSize widgetSize;
    private WidgetPosition widgetPosition;
    private WidgetOptions widgetOptions;
}
