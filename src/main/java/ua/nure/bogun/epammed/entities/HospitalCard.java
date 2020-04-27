package main.java.ua.nure.bogun.epammed.entities;

import java.sql.Date;

public class HospitalCard extends Entity{
    private int patientId;
    private Date date;
    private String diagnose;
    private String medicine;
    private boolean done;

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDiagnose(String diagnose) {
        this.diagnose = diagnose;
    }

    public String getDiagnose() {
        return diagnose;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean getDone() {
        return done;
    }

    @Override
    public String toString() {
        return "HospitalCard{" +
                "patientId=" + patientId +
                ", date=" + date +
                ", diagnose='" + diagnose + '\'' +
                ", medicine=" + medicine +
                ", done=" + done +
                '}';
    }
}
