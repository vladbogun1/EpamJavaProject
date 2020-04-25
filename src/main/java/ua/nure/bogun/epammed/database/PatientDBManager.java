package main.java.ua.nure.bogun.epammed.database;

import main.java.ua.nure.bogun.epammed.entities.Patient;
import main.java.ua.nure.bogun.epammed.entities.User;
import main.java.ua.nure.bogun.epammed.service.ImagesGenerator;
import main.java.ua.nure.bogun.epammed.service.dbservice.HospitalCardService;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class PatientDBManager extends DBManager{
    private static final String SELECT_ALL_PATIENTS = "SELECT * FROM " +
            "patient";

    private static final String SELECT_PATIENT_BY_ID = "SELECT * FROM " +
            "patient WHERE patient_id=?";

    private static PatientDBManager instance;
    private static final Logger logger = Logger.getLogger(PatientDBManager.class);

    public static PatientDBManager getInstance() {
        if (instance == null) {
            instance = new PatientDBManager();
        }
        return instance;
    }
    private Patient getPatient(ResultSet rs) throws SQLException {
        Patient patient = new Patient();
        patient.setId(rs.getInt("patient_id"));
        patient.setFirstName(rs.getString("first_name"));
        patient.setLastName(rs.getString("last_name"));
        patient.setBirthday(rs.getDate("birthday"));
        patient.setImage(ImagesGenerator.generate());
        HospitalCardService sS = new HospitalCardService();

        try {
            patient.setCardHistory(sS.getCardHistory(patient.getId()));
        } catch (DBException e) {
            e.printStackTrace();
        }

        return  patient;
    }

    public Patient findPatientById(int id) {
        Patient patient = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection(CONNECTION_URL);
            pstmt = con.prepareStatement(SELECT_PATIENT_BY_ID);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                patient = getPatient(rs);
            }
        } catch (SQLException ex) {
            rollback(con);
            logger.error("Cannot obtain a user by its login", ex);
        } finally {
            close(rs);
            close(pstmt);
            commitAndClose(con);
        }
        return patient;
    }

    public List<Patient> findAllPatients() throws DBException {
        Connection connection = null;
        Statement statement = null;
        ResultSet set = null;
        List<Patient> list = new ArrayList<>();

        try {
            connection = getConnection(CONNECTION_URL);
            statement = connection.createStatement();
            set = statement.executeQuery(SELECT_ALL_PATIENTS);

            while (set.next()) {
                Patient client = getPatient(set);
                list.add(client);
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
