package main.java.ua.nure.bogun.epammed.database;


import main.java.ua.nure.bogun.epammed.entities.User;
import main.java.ua.nure.bogun.epammed.service.ImagesGenerator;
import main.java.ua.nure.bogun.epammed.service.dbservice.SpecializationService;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDBManager extends DBManager {
    private static final String SELECT_ALL_DOCTORS = "SELECT * FROM " +
            "(user JOIN specialization ON user.specialization_id =specialization.specialization_id)" +
            " JOIN role ON user.role_id = role.role_id WHERE role.role_name = 'doctor'";

    private static final String SELECT_ALL_ADMINS = "SELECT * FROM " +
            "user JOIN role ON user.role_id = role.role_id = 'admin'";

    private static final String SELECT_ALL_NURSES = "SELECT * FROM " +
            "user JOIN role ON user.role_id = role.role_id WHERE role.role_name = 'nurse'";

    private static final String SELECT_USER_BY_LOGIN = "SELECT * FROM" +
            " user  JOIN role ON user.role_id = role.role_id WHERE user.login=?";
    private static final String SELECT_DOCTOR_BY_LOGIN = "SELECT * FROM" +
            " user  JOIN role ON user.role_id = role.role_id WHERE role.role_name='doctor' AND user.login=?";
    private static final String SELECT_NURSE_BY_LOGIN = "SELECT * FROM" +
            " user  JOIN role ON user.role_id = role.role_id WHERE role.role_name='nurse' AND user.login=?";
    private static final String SELECT_ADMIN_BY_LOGIN = "SELECT * FROM" +
            " user  JOIN role ON user.role_id = role.role_id WHERE role.role_name='admin' AND user.login=?";


    private static UserDBManager instance;
    private static final Logger logger = Logger.getLogger(UserDBManager.class);

    public static UserDBManager getInstance() {
        if (instance == null) {
            instance = new UserDBManager();
        }
        return instance;
    }
    private User getUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setLogin(rs.getString("login"));
        user.setPassword(rs.getString("password"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setRoleId(rs.getInt("role_id"));
        user.setCountOfPatients(rs.getInt("count_of_patients"));
        user.setImage(ImagesGenerator.generate());
        SpecializationService sS = new SpecializationService();

        try {
            user.setSpecialization(sS.getSpecialization(rs.getInt("specialization_id")));
        } catch (DBException e) {
            e.printStackTrace();
        }

        return  user;
    }

    public User findUserByLogin(String login) {
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection(CONNECTION_URL);
            pstmt = con.prepareStatement(SELECT_USER_BY_LOGIN);
            pstmt.setString(1, login);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user = getUser(rs);
            }
        } catch (SQLException ex) {
            rollback(con);
            logger.error("Cannot obtain a user by its login", ex);
        } finally {
            close(rs);
            close(pstmt);
            commitAndClose(con);
        }
        return user;
    }


    public User findAdminByLogin(String login) {
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection(CONNECTION_URL);
            pstmt = con.prepareStatement(SELECT_ADMIN_BY_LOGIN);
            pstmt.setString(1, login);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user = getUser(rs);
            }
        } catch (SQLException ex) {
            rollback(con);
            logger.error("Cannot obtain a user by its login", ex);
        } finally {
            close(rs);
            close(pstmt);
            commitAndClose(con);
        }
        return user;
    }

    public User findNurseByLogin(String login) {
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection(CONNECTION_URL);
            pstmt = con.prepareStatement(SELECT_NURSE_BY_LOGIN);
            pstmt.setString(1, login);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user = getUser(rs);
            }
        } catch (SQLException ex) {
            rollback(con);
            logger.error("Cannot obtain a user by its login", ex);
        } finally {
            close(rs);
            close(pstmt);
            commitAndClose(con);
        }
        return user;
    }

    public User findDoctorByLogin(String login) {
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection(CONNECTION_URL);
            pstmt = con.prepareStatement(SELECT_DOCTOR_BY_LOGIN);
            pstmt.setString(1, login);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user = getUser(rs);
            }
        } catch (SQLException ex) {
            rollback(con);
            logger.error("Cannot obtain a user by its login", ex);
        } finally {
            close(rs);
            close(pstmt);
            commitAndClose(con);
        }
        return user;
    }

    public List<User> findAllAdmins() throws DBException {
        Connection connection = null;
        Statement statement = null;
        ResultSet set = null;
        List<User> list = new ArrayList<>();

        try {
            connection = getConnection(CONNECTION_URL);
            statement = connection.createStatement();
            set = statement.executeQuery(SELECT_ALL_ADMINS);

            while (set.next()) {
                User user = getUser(set);
                list.add(user);
            }
        } catch (SQLException e) {
            logger.error("Error in selecting clients", e);
            throw new DBException("Error in selecting clients", e);
        } finally {
            close(connection);
            close(statement);
            close(set);
        }

        return list;
    }
    public List<User> findAllDoctors() throws DBException {
        Connection connection = null;
        Statement statement = null;
        ResultSet set = null;
        List<User> list = new ArrayList<>();

        try {
            connection = getConnection(CONNECTION_URL);
            statement = connection.createStatement();
            set = statement.executeQuery(SELECT_ALL_DOCTORS);

            while (set.next()) {
                User user = getUser(set);
                list.add(user);
            }
        } catch (SQLException e) {
            logger.error("Error in selecting clients", e);
            throw new DBException("Error in selecting clients", e);
        } finally {
            close(connection);
            close(statement);
            close(set);
        }

        return list;
    }
    public List<User> findAllNurses() throws DBException {
        Connection connection = null;
        Statement statement = null;
        ResultSet set = null;
        List<User> list = new ArrayList<>();

        try {
            connection = getConnection(CONNECTION_URL);
            statement = connection.createStatement();
            set = statement.executeQuery(SELECT_ALL_NURSES);

            while (set.next()) {
                User user = getUser(set);
                list.add(user);
            }
        } catch (SQLException e) {
            logger.error("Error in selecting clients", e);
            throw new DBException("Error in selecting clients", e);
        } finally {
            close(connection);
            close(statement);
            close(set);
        }

        return list;
    }

}