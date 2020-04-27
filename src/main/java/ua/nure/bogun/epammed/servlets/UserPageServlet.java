package main.java.ua.nure.bogun.epammed.servlets;

import main.java.ua.nure.bogun.epammed.entities.Role;
import main.java.ua.nure.bogun.epammed.entities.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("")
public class UserPageServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UserPageServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            response.sendRedirect("/login");
            return;
        }
        User user = (User)session.getAttribute("user");
        String path = getPath(Role.getUserRole(user));
        logger.info("User #"+user.getId()+" move to page "+ path);
        response.sendRedirect(path);
    }
    public String getPath(Role role){
        switch (role){
            case ADMIN:
                return "/admin";
            case DOCTOR:
                return "/doctor";
            case NURSE:
                return "/nurse";
            default:
                return "/404-error.jsp";
        }
    }
}
