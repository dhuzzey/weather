package com.huzzey.weather2.datatype;


public class Weather {
    private String dateTime;
    private String description;
    private String icon;
    private boolean header;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isHeader() {
        return header;
    }

    public void setHeader(boolean header) {
        this.header = header;
    }

    public String getTime(){
        //Log.w("Weather", "getTime");
        return dateTime != null && dateTime.length() > 11? dateTime.substring(10):"";
    }

    public String getDate() {
        //Log.w("Weather", "getDate " + dateTime + " 1 " + description);
        return dateTime != null && dateTime.length() > 11? dateTime.substring(0, 10):"";
    }
}
