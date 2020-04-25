package main.java.ua.nure.bogun.epammed.service.dbservice;

import main.java.ua.nure.bogun.epammed.database.DBException;
import main.java.ua.nure.bogun.epammed.database.HospitalCardDBManager;
import main.java.ua.nure.bogun.epammed.entities.HospitalCard;

import java.util.List;

public class HospitalCardService {
    public List<HospitalCard> getCardHistory(int id) throws DBException {
        List<HospitalCard> cards = HospitalCardDBManager.getInstance().findHospitalCardsByPatientId(id);
        return cards;
    }
}
