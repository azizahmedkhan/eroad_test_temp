package com.eroad_test.datetime;

/**
 * Created by azizahmed.khan on 28/02/18.
 * 2013-07-10 02:52:49,-44.490947,171.220966,Pacific/Auckland,2013-07-10T14:52:49
 */
public class LocalizedDataTime {

    private String uTCDateTime;
    private String longitude;
    private String latitude;
    private String timeZone;
    private String localDateTime;

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getUTCDateTime() {
        return uTCDateTime;
    }

    public void setUTCDateTime(String uTCDateTime) {
        this.uTCDateTime = uTCDateTime;
    }

    public String getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(String localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String toStringBeforeLocalTime() {
        return uTCDateTime+","+latitude+","+longitude;
    }

     //"2013-07-10 02:52:49,-44.490947,171.220966,Pacific/Auckland,2013-07-10T14:52:49"
    @Override
    public String toString() {
        return toStringBeforeLocalTime()+","+timeZone+","+localDateTime+"\n";
    }


}
