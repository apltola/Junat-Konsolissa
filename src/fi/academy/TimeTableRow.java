package fi.academy;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeTableRow {
@JsonFormat(pattern = "yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", locale="UTC")
    private Date scheduledTime;
    private String stationShortCode;
    private String type;

    public String getType() {
        return type;
    }

    public Date getScheduledTime() {
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
