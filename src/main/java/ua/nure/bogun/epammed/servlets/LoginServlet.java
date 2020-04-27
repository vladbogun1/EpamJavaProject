package main.java.ua.nure.bogun.epammed.servlets;

import main.java.ua.nure.bogun.epammed.database.DBException;
import main.java.ua.nure.bogun.epammed.entities.Role;
import main.java.ua.nure.bogun.epammed.service.ClientLoginService;
import main.java.ua.nure.bogun.epammed.service.Hashing;
import main.java.ua.nure.bogun.epammed.service.Login;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logUser")
public class LoginServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(LoginServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            response.sendRedirect("/");
        } else {
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            String role = request.getParameter("role");
            ClientLoginService service = new ClientLoginService();
            try {
                Login.STATUS status = service.logining(login, password, Role.getRole(Integer.parseInt(role)));
                switch (status) {
                    case FALSE:
                        response.setStatus(204);
                        break;
                    case TRUE:
                        session.setAttribute("user", service.getCash());
                        logger.info("User #" + service.getCash().getId() + " log in successful.");
                        response.setStatus(200);
                        break;
                    default:
                        response.setStatus(400);
                }
            } catch (DBException e) {
                logger.error("Error in authentication", e);
                response.sendError(500);
            }

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
