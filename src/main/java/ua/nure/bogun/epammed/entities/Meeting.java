package main.java.ua.nure.bogun.epammed.entities;

import java.sql.Date;

public class Meeting extends Entity{

    private Date date;
    private Patient patient;
    private User user;


    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }


    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "date=" + date +
                ", patient=" + patient +
                ", user=" + user +
                '}';
    }
}
