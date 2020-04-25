package main.java.ua.nure.bogun.epammed.service.dbservice;

import main.java.ua.nure.bogun.epammed.database.DBException;
import main.java.ua.nure.bogun.epammed.database.SpecializationDBManager;
import main.java.ua.nure.bogun.epammed.entities.Specialization;

public class SpecializationService {
    public Specialization getSpecialization(int id) throws DBException{
        if(id<0){
            return null;
        }
        return SpecializationDBManager.getInstance().findSpecializationById(id);
    }
}
