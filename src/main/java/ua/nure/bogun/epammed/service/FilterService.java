package main.java.ua.nure.bogun.epammed.service;

import main.java.ua.nure.bogun.epammed.database.DBException;
import main.java.ua.nure.bogun.epammed.entities.Patient;
import main.java.ua.nure.bogun.epammed.entities.User;
import main.java.ua.nure.bogun.epammed.service.dbservice.PatientService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FilterService {
    public List<User> filterDoctor(List<User> users, String filter, String pattern) {
        Predicate<User> predicate = null;
        switch (filter) {
            case "firstName":
                predicate = user -> user.getFirstName()
                        .toLowerCase().indexOf(pattern.toLowerCase()) == 0;
                break;
            case "lastName":
                predicate = user -> user.getLastName()
                        .toLowerCase().indexOf(pattern.toLowerCase()) == 0;
                break;
            case "patients":
                predicate = user ->
                        user.getCountOfPatients() > Integer.parseInt(pattern);
                break;
            case "category":
                predicate = user -> user.getSpecialization().getSpecializationName()
                        .toLowerCase().indexOf(pattern.toLowerCase()) == 0;
                break;

        }
        if (predicate != null) {
            return users.stream().filter(predicate).collect(Collectors.toList());
        }
        return users;
    }

    public List<Patient> filterPatient(List<Patient> users, String filter, String pattern) {
        Predicate<Patient> predicate = null;
        switch (filter) {
            case "firstName":
                predicate = user -> user.getFirstName()
                        .toLowerCase().indexOf(pattern.toLowerCase()) == 0;
                break;
            case "lastName":
                predicate = user -> user.getLastName()
                        .toLowerCase().indexOf(pattern.toLowerCase()) == 0;
                break;
            case "birthday":
                predicate = user ->
                        user.getBirthday().toString()
                                .indexOf(pattern.toLowerCase()) == 0;
                break;
        }
        if (predicate != null) {
            return users.stream().filter(predicate).collect(Collectors.toList());
        }
        return users;
    }


    public static void filter(HttpServletRequest request, HttpSession session, Logger logger) throws DBException {
        User loginner = (User)session.getAttribute("user");
        logger.info("User #"+loginner.getId()+" entered " + UrlGetter.get(request));
        String filter = request.getParameter("search");
        String command = request.getParameter("command");
        String pattern = request.getParameter("pattern");
        List<Patient> patients = new PatientService().getAllPatients();
        if (filter != null) {
            switch (filter) {
                case "patient":
                    patients = new FilterService().filterPatient(patients, command, pattern);
                    break;
            }
        }
        request.setAttribute("patients", patients);
    }
}
