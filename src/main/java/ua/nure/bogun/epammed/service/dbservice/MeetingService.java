package main.java.ua.nure.bogun.epammed.service.dbservice;

import main.java.ua.nure.bogun.epammed.database.DBException;
import main.java.ua.nure.bogun.epammed.database.MeetingDBManager;
import main.java.ua.nure.bogun.epammed.entities.Meeting;
import main.java.ua.nure.bogun.epammed.entities.Patient;
import main.java.ua.nure.bogun.epammed.entities.User;

import java.util.List;

public class MeetingService {
    public List<Meeting> getMeetingsByPatient(int id) throws DBException {
        return MeetingDBManager.getInstance().findMeetingsByPatientId(id);
    }
    public List<Meeting> getMeetingsByUser(int id) throws DBException {
        return MeetingDBManager.getInstance().findMeetingsByUserId(id);
    }

    public boolean createMeeting(User user, Patient patient) throws DBException{
        return MeetingDBManager.getInstance().insert(user,patient);
    }
//    public List<Meeting> getAllMeetings(){
//        return MeetingDBManager.getInstance().findAllMeetings();
//    }
}
