package main.java.ua.nure.bogun.epammed.servlets.admin;

import main.java.ua.nure.bogun.epammed.database.DBException;
import main.java.ua.nure.bogun.epammed.database.PatientDBManager;
import main.java.ua.nure.bogun.epammed.entities.Patient;
import main.java.ua.nure.bogun.epammed.entities.User;
import main.java.ua.nure.bogun.epammed.service.dbservice.PatientService;
import main.java.ua.nure.bogun.epammed.service.dbservice.UserService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@WebServlet("/admin")
public class AdminMainServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(PatientDBManager.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            request.setAttribute("disconnected", 1);
            request.getRequestDispatcher("/login").forward(request, response);
        } else {
            try {
                String filter = request.getParameter("search");
                String command = request.getParameter("command");
                String pattern = request.getParameter("pattern");
                List<Patient> patients = new PatientService().getAllPatients();
                List<User> doctors = new UserService().getAllDoctors();
                if (filter != null) {
                    switch (filter) {
                        case "doctor":
                            doctors = filterDoctor(doctors, command, pattern);
                        case "patient":
                            patients = filterPatient(patients, command, pattern);
                    }
                }
                request.setAttribute("patients", patients);
                request.setAttribute("doctors", doctors);
                request.getRequestDispatcher("html/admin/main.jsp").forward(request, response);
            } catch (DBException e) {
                logger.error("Can't get all patients");
                response.sendError(500);
            }
        }
    }

    private List<User> filterDoctor(List<User> users, String filter, String pattern) {
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

    private List<Patient> filterPatient(List<Patient> users, String filter, String pattern) {
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

}
