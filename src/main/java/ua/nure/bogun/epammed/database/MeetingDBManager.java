package main.java.ua.nure.bogun.epammed.database;

import main.java.ua.nure.bogun.epammed.entities.HospitalCard;
import main.java.ua.nure.bogun.epammed.entities.Meeting;
import main.java.ua.nure.bogun.epammed.service.dbservice.SpecializationService;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        meeting.setPatientId(rs.getInt("patient_id"));
        meeting.setUserId(rs.getInt("user_id"));
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

}
