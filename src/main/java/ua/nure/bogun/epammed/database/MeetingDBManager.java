package main.java.ua.nure.bogun.epammed.database;

import main.java.ua.nure.bogun.epammed.entities.HospitalCard;
import main.java.ua.nure.bogun.epammed.entities.Meeting;
import main.java.ua.nure.bogun.epammed.entities.Patient;
import main.java.ua.nure.bogun.epammed.entities.User;
import main.java.ua.nure.bogun.epammed.service.dbservice.PatientService;
import main.java.ua.nure.bogun.epammed.service.dbservice.SpecializationService;
import main.java.ua.nure.bogun.epammed.service.dbservice.UserService;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class MeetingDBManager extends DBManager{
    private static final String SELECT_MEETINGS_BY_PATIENT_ID = "SELECT * FROM " +
            "meeting WHERE patient_id=?";
    private static final String SELECT_MEETINGS_BY_USER_ID = "SELECT * FROM " +
            "meeting WHERE user_id=?";
    private static final String SELECT_ALL_MEETINGS = "SELECT * FROM " +
            "meeting";
    private static final String INSERT = "INSERT INTO meeting" +
            "(user_id, patient_id, meeting_date) " +
            "VALUES(?, ?, ?)";

    private static MeetingDBManager instance;
    private static final Logger logger = Logger.getLogger(MeetingDBManager.class);

    public static MeetingDBManager getInstance() {
        if (instance == null) {
            instance = new MeetingDBManager();
        }
        return instance;
    }

    private Meeting getMeeting(ResultSet rs) throws SQLException {
        Meeting meeting = new Meeting();
        meeting.setId(rs.getInt("meeting_id"));
        PatientService ps = new PatientService();
        meeting.setPatient(ps.getPatientById(rs.getInt("patient_id")));
        UserService us = new UserService();
        meeting.setUser(us.getUserById(rs.getInt("user_id")));
        meeting.setDate(rs.getDate("meeting_date"));
        return  meeting;
    }

    public List<Meeting> findMeetingsByPatientId(int patientId) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Meeting> list = new ArrayList<>();

        try {
            con = getConnection(CONNECTION_URL);
            pstmt = con.prepareStatement(SELECT_MEETINGS_BY_PATIENT_ID);
            pstmt.setInt(1, patientId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Meeting meeting = getMeeting(rs);
                list.add(meeting);
            }
        } catch (SQLException e) {
            logger.error("Error in selecting meetings", e);
            throw new DBException("Error in selecting meetings", e);
        } finally {
            close(con);
            close(pstmt);
            close(rs);
        }

        return list;
    }
    public List<Meeting> findMeetingsByUserId(int patientId) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Meeting> list = new ArrayList<>();

        try {
            con = getConnection(CONNECTION_URL);
            pstmt = con.prepareStatement(SELECT_MEETINGS_BY_USER_ID);
            pstmt.setInt(1, patientId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Meeting meeting = getMeeting(rs);
                list.add(meeting);
            }
        } catch (SQLException e) {
            logger.error("Error in selecting meetings", e);
            throw new DBException("Error in selecting meetings", e);
        } finally {
            close(con);
            close(pstmt);
            close(rs);
        }

        return list;
    }

    public boolean insert(User user, Patient patient) throws DBException {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean status = true;
        try {
            connection = getConnection(CONNECTION_URL);

            statement = connection.prepareStatement(INSERT);
            statement.setInt(1, user.getId());
            statement.setInt(2, patient.getId());
            statement.setDate(3, new Date(System.currentTimeMillis()));
            statement.execute();

        } catch (SQLException e) {
            status = false;
            logger.error("Error in creating meeting", e);
            throw new DBException("Error in creating meeting", e);

        } finally {
            close(connection);
            close(statement);
        }
        return status;
    }
}
