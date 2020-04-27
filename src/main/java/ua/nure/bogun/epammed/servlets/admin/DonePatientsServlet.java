package main.java.ua.nure.bogun.epammed.servlets.admin;

import main.java.ua.nure.bogun.epammed.database.DBException;
import main.java.ua.nure.bogun.epammed.entities.HospitalCard;
import main.java.ua.nure.bogun.epammed.entities.Meeting;
import main.java.ua.nure.bogun.epammed.entities.Role;
import main.java.ua.nure.bogun.epammed.entities.User;
import main.java.ua.nure.bogun.epammed.service.DoneService;
import main.java.ua.nure.bogun.epammed.service.dbservice.HospitalCardService;
import main.java.ua.nure.bogun.epammed.service.dbservice.MeetingService;
import main.java.ua.nure.bogun.epammed.service.dbservice.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@WebServlet("/done")
public class DonePatientsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            request.setAttribute("disconnected", 1);
            request.getRequestDispatcher("/login").forward(request, response);
        } else {
            User doctor = (User) session.getAttribute("user");
            if (Role.getUserRole(doctor)!=Role.ADMIN) {
                request.getRequestDispatcher("/404-error.jsp").forward(request, response);
                return;
            }
            try {
                Map<User, Integer> doneTable = new DoneService().done();
                request.setAttribute("doneTable", doneTable);
                request.getRequestDispatcher("/html/admin/done.jsp").forward(request, response);
            } catch (DBException e) {
                request.getRequestDispatcher("/404-error.jsp").forward(request, response);
                e.printStackTrace();
            }
        }
    }
}
