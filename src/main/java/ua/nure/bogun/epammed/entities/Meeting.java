package main.java.ua.nure.bogun.epammed.entities;

import java.sql.Date;

public class Meeting extends Entity{
    private int patientId;
    private int userId;
    private Date date;

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "patientId=" + patientId +
                ", userId=" + userId +
                ", date=" + date +
                '}';
    }
}
