package main.java.ua.nure.bogun.epammed.servlets;

import main.java.ua.nure.bogun.epammed.database.DBException;
import main.java.ua.nure.bogun.epammed.entities.Patient;
import main.java.ua.nure.bogun.epammed.entities.User;
import main.java.ua.nure.bogun.epammed.service.UrlGetter;
import main.java.ua.nure.bogun.epammed.service.dbservice.MeetingService;
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
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/service")
public class ServicesServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ServicesServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null){
            String command = request.getParameter("command");
            String doctor_id = request.getParameter("doctor_id");
            String patient_id = request.getParameter("patient_id");
            String first_name = request.getParameter("first_name");
            String last_name = request.getParameter("last_name");
            String date = request.getParameter("date");
            int status;
            switch (command){
                case "addMeeting":
                    status = addMeeting(doctor_id, patient_id);
                    break;
                case "deleteMeeting":
                    status = deleteMeeting(doctor_id, patient_id);
                    break;
                case "newPatient":
                    status = newPatient(first_name, last_name, date);
                    break;
                default:
                    status = 500;
            }
            response.setStatus(status);
        }
    }

    private int newPatient(String first_name, String last_name, String date) {
        if(first_name==null||last_name==null||date==null){
            return 400;
        }
        Patient patient = new Patient();
        patient.setFirstName(first_name);
        patient.setLastName(last_name);
        patient.setBirthday(Date.valueOf(date));
        try{
            if(new PatientService().createPatient(patient)){
                return 200;
            }
        } catch (DBException ignored){}

        return 500;
    }

    private int deleteMeeting(String doctor_id, String patient_id) {
        return 500;
    }

    private int addMeeting(String doctor_id, String patient_id) {
        if(doctor_id==null||patient_id==null){
            return 400;
        }
        User user = new UserService().getUserById(Integer.parseInt(doctor_id));
        Patient patient = new PatientService().getPatientById(Integer.parseInt(patient_id));
        try {
            if(new MeetingService().createMeeting(user, patient)) {
                return 200;
            }
        } catch (DBException ignored){}
        return 500;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            request.setAttribute("disconnected", 1);
            request.getRequestDispatcher("/login").forward(request, response);
        } else {
            User loginner = (User)session.getAttribute("user");
            logger.info("User #"+loginner.getId()+" entered " + UrlGetter.get(request));
            List<User> doctors = new ArrayList<>();
            List<Patient>  patients= new ArrayList<>();
            try {
                doctors = new UserService().getAllDoctors();
                patients = new PatientService().getAllPatients();
            } catch (DBException e) {
                e.printStackTrace();
            }
            request.setAttribute("doctors", doctors);
            request.setAttribute("patients", patients);
            request.getRequestDispatcher("/html/service.jsp").forward(request, response);
        }
    }
}
