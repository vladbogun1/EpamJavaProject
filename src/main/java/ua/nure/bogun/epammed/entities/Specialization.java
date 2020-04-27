package main.java.ua.nure.bogun.epammed.entities;

public class Specialization extends Entity{
    private String specializationName;

    public String getSpecializationName() {
        return specializationName;
    }

    public void setSpecializationName(String specializationName) {
        this.specializationName = specializationName;
    }

    @Override
    public String toString() {
        return "Specialization{" +
                "specializationName='" + specializationName + '\'' +
                '}';
    }
}
