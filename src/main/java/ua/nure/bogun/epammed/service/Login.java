package main.java.ua.nure.bogun.epammed.service;

import main.java.ua.nure.bogun.epammed.database.DBException;
import main.java.ua.nure.bogun.epammed.entities.Role;

public interface Login {

    STATUS logining(String login, String password, Role role) throws DBException;

    enum STATUS{
        TRUE, FALSE, UNDEFINED
    }
}