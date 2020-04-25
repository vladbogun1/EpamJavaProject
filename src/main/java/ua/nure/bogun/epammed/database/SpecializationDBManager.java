package main.java.ua.nure.bogun.epammed.database;

import main.java.ua.nure.bogun.epammed.entities.Specialization;
import main.java.ua.nure.bogun.epammed.entities.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.sql.DriverManager.getConnection;

public class SpecializationDBManager extends DBManager{
    private static final String SELECT_SPECIALIZATION_BY_ID = "SELECT * FROM " +
            "specialization WHERE specialization_id=?";
    private static SpecializationDBManager instance;
    private static final Logger logger = Logger.getLogger(SpecializationDBManager.class);

    public static SpecializationDBManager getInstance() {
        if (instance == null) {
            instance = new SpecializationDBManager();
        }
        return instance;
    }
    private Specialization getSpecialization(ResultSet rs) throws SQLException {
        Specialization specialization = new Specialization();
        specialization.setId(rs.getInt("specialization_id"));
        specialization.setSpecializationName(rs.getString("specialization_name"));
        return  specialization;
    }
    public Specialization findSpecializationById(int id) {
        Specialization specialization = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection(CONNECTION_URL);
            pstmt = con.prepareStatement(SELECT_SPECIALIZATION_BY_ID);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                specialization = getSpecialization(rs);
            }
        } catch (SQLException ex) {
            rollback(con);
            logger.error("Cannot obtain a Specialization by its id", ex);
        } finally {
            close(rs);
            close(pstmt);
            commitAndClose(con);
        }
        return specialization;
    }
}
