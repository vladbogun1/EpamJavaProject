package main.java.ua.nure.bogun.epammed.service.dbservice;

import main.java.ua.nure.bogun.epammed.database.DBException;
import main.java.ua.nure.bogun.epammed.database.MeetingDBManager;
import main.java.ua.nure.bogun.epammed.database.PatientDBManager;
import main.java.ua.nure.bogun.epammed.entities.Meeting;
import main.java.ua.nure.bogun.epammed.entities.Patient;

import java.util.List;

public class PatientService {
    public List<Patient> getAllPatients() throws DBException {
        return PatientDBManager.getInstance().findAllPatients();
    }
    public Patient getPatientById(int patientId) {
        return PatientDBManager.getInstance().findPatientById(patientId);
    }

    public boolean updatePatient(Patient patient) throws DBException {
        return PatientDBManager.getInstance().update(patient);
    }
}
