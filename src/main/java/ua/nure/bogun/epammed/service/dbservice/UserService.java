package main.java.ua.nure.bogun.epammed.service.dbservice;

import main.java.ua.nure.bogun.epammed.database.DBException;
import main.java.ua.nure.bogun.epammed.database.PatientDBManager;
import main.java.ua.nure.bogun.epammed.database.UserDBManager;
import main.java.ua.nure.bogun.epammed.entities.Patient;
import main.java.ua.nure.bogun.epammed.entities.User;

import java.util.List;

public class UserService {
    public User getUserByLogin(String login) {
        return UserDBManager.getInstance().findUserByLogin(login);
    }
    public User getAdminByLogin(String login) {
        return UserDBManager.getInstance().findAdminByLogin(login);
    }
    public User getDoctorByLogin(String login) {
        return UserDBManager.getInstance().findDoctorByLogin(login);
    }
    public User getNurseByLogin(String login) {
        return UserDBManager.getInstance().findNurseByLogin(login);
    }

    public List<User> getAllDoctors() throws DBException {
        return UserDBManager.getInstance().findAllDoctors();
    }
}
