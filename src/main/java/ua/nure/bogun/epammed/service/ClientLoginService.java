package main.java.ua.nure.bogun.epammed.service;

import main.java.ua.nure.bogun.epammed.database.DBException;
import main.java.ua.nure.bogun.epammed.database.UserDBManager;
import main.java.ua.nure.bogun.epammed.entities.Role;
import main.java.ua.nure.bogun.epammed.entities.User;
import main.java.ua.nure.bogun.epammed.service.dbservice.UserService;

public class ClientLoginService implements Login{
    private User cash;

    @Override
    public STATUS logining(String login, String password, Role role) throws DBException {

        User client = getUser(login,role);
        if(client != null){
            if(Hashing.getHashedString(password).equals(client.getPassword())){
                cash = client;
                return STATUS.TRUE;
            }else{
                return STATUS.FALSE;
            }
        }else{
            return STATUS.UNDEFINED;
        }
    }

    private User getUser(String login, Role role) {
        UserService service = new UserService();
        switch (role){
            case ADMIN:
                return service.getAdminByLogin(login);
            case NURSE:
                return service.getNurseByLogin(login);
            case DOCTOR:
                return service.getDoctorByLogin(login);
            default:
                return null;
        }
    }

    public User getCash(){
        return cash;
    }
}
