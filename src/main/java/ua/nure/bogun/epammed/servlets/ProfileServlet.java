package main.java.ua.nure.bogun.epammed.servlets;

import main.java.ua.nure.bogun.epammed.database.DBException;
import main.java.ua.nure.bogun.epammed.entities.Meeting;
import main.java.ua.nure.bogun.epammed.entities.Patient;
import main.java.ua.nure.bogun.epammed.entities.Role;
import main.java.ua.nure.bogun.epammed.entities.User;
import main.java.ua.nure.bogun.epammed.service.UrlGetter;
import main.java.ua.nure.bogun.epammed.service.dbservice.MeetingService;
import main.java.ua.nure.bogun.epammed.service.dbservice.PatientService;
import main.java.ua.nure.bogun.epammed.service.dbservice.UserService;
import main.java.ua.nure.bogun.epammed.servlets.doctor.DoctorMainServlet;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ProfileServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null){
            String role = request.getParameter("role");
            String id = request.getParameter("id");
            String first_name = request.getParameter("first_name");
            String last_name = request.getParameter("last_name");
            int status;
            switch (role){
                case "worker":
                   status = updateUser(id, first_name, last_name);
                    break;
                case "patient":
                    status = updatePatient(id, first_name , last_name);
                    break;
                default:
                    status = 500;
            }
            response.setStatus(status);

        }
    }

    private int updatePatient(String id, String first_name, String last_name) {
        if(id==null||first_name==null||last_name==null){
            return 400;
        }
        Patient patient = new PatientService().getPatientById(Integer.parseInt(id));
        patient.setFirstName(first_name);
        patient.setLastName(last_name);
        try {
            new PatientService().updatePatient(patient);
            return 200;
        } catch (DBException ignored) {}
        return 500;
    }

    private int updateUser(String id, String first_name, String last_name) {
        if(id==null||first_name==null||last_name==null){
            return 400;
        }
        User user = new UserService().getUserById(Integer.parseInt(id));
        user.setFirstName(first_name);
        user.setLastName(last_name);
        try {
            new UserService().updateUser(user);
            return 200;
        } catch (DBException ignored) {}
        return 500;
    }

    private boolean canEdit(User user){
        switch (Role.getUserRole(user)){
            case ADMIN:
                return true;
            case DOCTOR:
                return false;
            case NURSE:
                return false;
            default:
                return false;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            request.setAttribute("disconnected", 1);
            request.getRequestDispatcher("/login").forward(request, response);
        } else {
            String userLogin = request.getParameter("user");
            String patientId = request.getParameter("patient");
            User loginner = (User)session.getAttribute("user");
            logger.info("User #"+loginner.getId()+" entered " + UrlGetter.get(request));
            request.setAttribute("editable", canEdit(loginner));
            if (userLogin != null && patientId == null) {
                User user = new UserService().getUserByLogin(userLogin);
                List<Meeting> meetings = new ArrayList<>();
                try {
                    meetings = new MeetingService().getMeetingsByUser(user.getId());
                } catch (DBException e) {
                    e.printStackTrace();
                }
                request.setAttribute("meetings", meetings);
                request.setAttribute("profiler", user);
            } else if (userLogin == null && patientId != null) {
                Patient patient = new PatientService().getPatientById(Integer.parseInt(patientId));
                List<Meeting> meetings = new ArrayList<>();
                try {
                    meetings = new MeetingService().getMeetingsByPatient(patient.getId());
                } catch (DBException e) {
                    e.printStackTrace();
                }
                request.setAttribute("meetings", meetings);
                request.setAttribute("profiler", patient);
            } else {
                request.getRequestDispatcher("/404-error.jsp").forward(request, response);
            }

            request.getRequestDispatcher("/html/profile.jsp").forward(request, response);

        }

    }
}
