package main.java.ua.nure.bogun.epammed.database;

public class DBException extends Exception {

	public DBException(String message, Exception ex) {
		super(message, ex);
	}

}
