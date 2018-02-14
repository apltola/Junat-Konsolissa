package fi.academy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeTableRow {

    private String scheduledTime;
    private String stationShortCode;
    private String type;

    public String getType() {
        return type;
    }

    public String getScheduledTime() {
        return scheduledTime;
    }

    public String getStationShortCode() {
        return stationShortCode;
    }

    @Override
    public String toString() {
        return scheduledTime + "\n";
    }
}
