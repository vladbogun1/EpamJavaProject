package main.java.ua.nure.bogun.epammed.entities;


public enum Role {

    ADMIN, DOCTOR, NURSE;

    public static Role getUserRole(User user) {
        int roleId = user.getRoleId();
        return Role.values()[roleId];
    }
    public static Role getRole(int id){
        return Role.values()[id];
    }
    public String getName() {
        return name().toLowerCase();
    }


}
