package com.example.voiceprescription;

public class DatabasePojo {
    private String timeInMillis;
    private String date;
    private String url;

    public DatabasePojo(){}

    public DatabasePojo(String timeInMillis, String date, String url) {
        this.timeInMillis = timeInMillis;
        this.date = date;
        this.url = url;
    }

    public String getTimeInMillis() {
        return timeInMillis;
    }

    public void setTimeInMillis(String timeInMillis) {
        this.timeInMillis = timeInMillis;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
