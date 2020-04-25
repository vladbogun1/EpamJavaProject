package main.java.ua.nure.bogun.epammed.service.dbservice;

import main.java.ua.nure.bogun.epammed.database.DBException;
import main.java.ua.nure.bogun.epammed.database.MeetingDBManager;
import main.java.ua.nure.bogun.epammed.entities.Meeting;

import java.util.List;

public class MeetingService {
    public List<Meeting> getMeetingsByPatient(int id) throws DBException {
        return MeetingDBManager.getInstance().findMeetingsByPatientId(id);
    }
    public List<Meeting> getMeetingsByUser(int id) throws DBException {
        return MeetingDBManager.getInstance().findMeetingsByUserId(id);
    }
//    public List<Meeting> getAllMeetings(){
//        return MeetingDBManager.getInstance().findAllMeetings();
//    }
}
