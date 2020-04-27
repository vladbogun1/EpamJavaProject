package main.java.ua.nure.bogun.epammed.service;

import main.java.ua.nure.bogun.epammed.database.DBException;
import main.java.ua.nure.bogun.epammed.entities.*;
import main.java.ua.nure.bogun.epammed.service.dbservice.HospitalCardService;
import main.java.ua.nure.bogun.epammed.service.dbservice.MeetingService;
import main.java.ua.nure.bogun.epammed.service.dbservice.UserService;

import java.util.*;

public class DoneService {
    private Map<User,Integer> doneTable = new LinkedHashMap<>();

    public Map<User, Integer> done() throws DBException {
        List<User> doctors = new UserService().getAllDoctors();
        for (User d: doctors) {
            getUsersFromMeetings(d);
        }
        return doneTable;
    }
    private void getUsersFromMeetings(User doctor) throws DBException {
        List<Meeting> meetings = new MeetingService().getMeetingsByUser(doctor.getId());
        List<Patient> patients = new ArrayList<>();
        for(Meeting meeting: meetings){
            if(!containsUser(patients,meeting.getPatient())) {
                patients.add(meeting.getPatient());
            }
        }
        getHospitalCards(patients, doctor);
    }
    private void getHospitalCards(List<Patient> patients,User doctor) throws DBException {
        for(Patient patient: patients){
            List<HospitalCard> hospitalCards = new HospitalCardService().getCardHistory(patient.getId());
            doneTable.put(doctor,getHospitalCount(hospitalCards));
        }
    }
    private int getHospitalCount(List<HospitalCard> hospitalCards){
        int counter = 0;
        for (HospitalCard card: hospitalCards){
            if (card.getDone()){
                counter++;
            }
        }
        return counter;
    }


    private boolean containsUser(List<Patient> list, Entity m){
        return list.stream().anyMatch(o -> o.getId()==m.getId());
    }

}
