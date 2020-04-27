package main.java.ua.nure.bogun.epammed.database;

import main.java.ua.nure.bogun.epammed.entities.HospitalCard;
import main.java.ua.nure.bogun.epammed.entities.Patient;
import main.java.ua.nure.bogun.epammed.entities.User;
import main.java.ua.nure.bogun.epammed.service.dbservice.SpecializationService;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class HospitalCardDBManager extends DBManager{
    private static final String SELECT_ALL_HOSPITAL_CARDS_BY_PATIENT_ID = "SELECT * FROM " +
            "hospital_card WHERE patient_id=?";

    private static HospitalCardDBManager instance;
    private static final Logger logger = Logger.getLogger(HospitalCardDBManager.class);

    public static HospitalCardDBManager getInstance() {
        if (instance == null) {
            instance = new HospitalCardDBManager();
        }
        return instance;
    }
    private HospitalCard getHospitalCard(ResultSet rs) throws SQLException {
        HospitalCard card = new HospitalCard();
        card.setId(rs.getInt("hospital_card_id"));
        card.setPatientId(rs.getInt("patient_id"));
        card.setDate(rs.getDate("hospital_card_date"));
        card.setDiagnose(rs.getString("diagnosis"));
        card.setMedicine(rs.getInt("name_of_medication"));
        card.setDone(rs.getBoolean("done"));
        return  card;
    }

    public List<HospitalCard> findHospitalCardsByPatientId(int patientId) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<HospitalCard> list = new ArrayList<>();

        try {
            con = getConnection(CONNECTION_URL);
            pstmt = con.prepareStatement(SELECT_ALL_HOSPITAL_CARDS_BY_PATIENT_ID);
            pstmt.setInt(1, patientId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                HospitalCard card = getHospitalCard(rs);
                list.add(card);
            }
        } catch (SQLException e) {
            logger.error("Error in selecting clients", e);
            throw new DBException("Error in selecting clients", e);
        } finally {
            close(con);
            close(pstmt);
            close(rs);
        }

        return list;
    }

}
