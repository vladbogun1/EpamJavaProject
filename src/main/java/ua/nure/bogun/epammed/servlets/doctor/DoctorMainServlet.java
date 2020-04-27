package main.java.ua.nure.bogun.epammed.servlets.doctor;

import main.java.ua.nure.bogun.epammed.database.DBException;
import main.java.ua.nure.bogun.epammed.entities.Patient;
import main.java.ua.nure.bogun.epammed.entities.User;
import main.java.ua.nure.bogun.epammed.service.FilterService;
import main.java.ua.nure.bogun.epammed.service.UrlGetter;
import main.java.ua.nure.bogun.epammed.service.dbservice.PatientService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/doctor")
public class DoctorMainServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(DoctorMainServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            request.setAttribute("disconnected", 1);
            request.getRequestDispatcher("/login").forward(request, response);
        } else {
            try {
                FilterService.filter(request, session, logger);
                request.getRequestDispatcher("html/doctor/main.jsp").forward(request, response);
            } catch (DBException e) {
                logger.error("Can't get all patients");
                response.sendError(500);
            }
        }
    }


}
