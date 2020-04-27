package main.java.ua.nure.bogun.epammed.entities;

import java.sql.Date;
import java.util.List;

public class Patient extends Entity{
    private String firstName;
    private String lastName;
    private Date birthday;
    private List<HospitalCard> cardHistory;
    private String image;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setCardHistory(List<HospitalCard> cardHistory) {
        this.cardHistory = cardHistory;
    }

    public List<HospitalCard> getCardHistory() {
        return cardHistory;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthday=" + birthday +
                ", cardHistory=" + cardHistory +
                ", image='" + image + '\'' +
                '}';
    }
}
