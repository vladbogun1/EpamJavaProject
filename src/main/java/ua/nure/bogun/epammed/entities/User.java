package main.java.ua.nure.bogun.epammed.entities;

public class User extends Entity {
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private int roleId;
    private Specialization specialization;
    private int countOfPatients;
    private String image;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public int getCountOfPatients() {
        return countOfPatients;
    }

    public void setCountOfPatients(int countOfPatients) {
        this.countOfPatients = countOfPatients;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", roleId=" + roleId +
                ", specialization=" + specialization +
                ", countOfPatients=" + countOfPatients +
                ", image='" + image + '\'' +
                '}';
    }
}
