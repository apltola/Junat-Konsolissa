package fi.academy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.awt.*;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Juna {
    private boolean cancelled;
    private String commuterLineID;
    //LocalDate departureDate;  // Jackson ei oikein pidä Java 8 päivistä oletuksena
    private Date departureDate;
    private String operatorShortCode;
    private int operatorUICCode;
    private boolean runningCurrently;
    private List<TimeTableRow> timeTableRows;
    private Date timetableAcceptanceDate;
    private String timetableType;
    private String trainCategory;
    private int trainNumber;
    private String trainType;
    private long version;
    private String station;
    private String nextStation;



    public String getNextStation() {
        return nextStation;
    }

    public String getStation() {
        return station;
    }

    @Override
    public String toString() {
        return "Juna{" + "\ncancelled=" + cancelled + ", \ncommuterLineID='" + commuterLineID + '\'' + /*", departureDate=" + departureDate +*/ ", \noperatorShortCode='" + operatorShortCode + '\'' + ", \noperatorUICCode=" + operatorUICCode + ", \nrunningCurrently=" + runningCurrently + "\ntimeTableRows=\n" + timeTableRows + ", \ntimetableAcceptanceDate=" + timetableAcceptanceDate + ", \ntimetableType='" + timetableType + '\'' + ", \ntrainCategory='" + trainCategory + '\'' + ", \ntrainNumber=" + trainNumber + ", \ntrainType='" + trainType + '\'' + ", \nversion=" + version + '}';
    }



    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public String getCommuterLineID() {
        return commuterLineID;
    }

    public void setCommuterLineID(String commuterLineID) {
        this.commuterLineID = commuterLineID;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public String getOperatorShortCode() {
        return operatorShortCode;
    }

    public void setOperatorShortCode(String operatorShortCode) {
        this.operatorShortCode = operatorShortCode;
    }

    public int getOperatorUICCode() {
        return operatorUICCode;
    }

    public void setOperatorUICCode(int operatorUICCode) {
        this.operatorUICCode = operatorUICCode;
    }

    public boolean isRunningCurrently() {
        return runningCurrently;
    }

    public void setRunningCurrently(boolean runningCurrently) {
        this.runningCurrently = runningCurrently;
    }

    public List<TimeTableRow> getTimeTableRows() {
        return timeTableRows;
    }

    public void setTimeTableRows(List<TimeTableRow> timeTableRows) {
        this.timeTableRows = timeTableRows;
    }

    public Date getTimetableAcceptanceDate() {
        return timetableAcceptanceDate;
    }

    public void setTimetableAcceptanceDate(Date timetableAcceptanceDate) {
        this.timetableAcceptanceDate = timetableAcceptanceDate;
    }

    public String getTimetableType() {
        return timetableType;
    }

    public void setTimetableType(String timetableType) {
        this.timetableType = timetableType;
    }

    public String getTrainCategory() {
        return trainCategory;
    }

    public void setTrainCategory(String trainCategory) {
        this.trainCategory = trainCategory;
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(int trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getTrainType() {
        return trainType;
    }

    public void setTrainType(String trainType) {
        this.trainType = trainType;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
