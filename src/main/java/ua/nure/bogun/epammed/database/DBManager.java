package main.java.ua.nure.bogun.epammed.database;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Objects;
import java.util.Properties;


public class DBManager {

    private static final Logger logger = Logger.getLogger(DBManager.class);
    // =================== singleton
    protected static String CONNECTION_URL;
    // =================== singleton

    private static DBManager instance;


    private static String readProperty(String path, String property) {
        Properties prop = new Properties();
        String result = null;
        try {
            prop.load(
                    DBManager.class.getClassLoader().getResourceAsStream(path)
            );
            result = prop.getProperty(property);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private synchronized void readPropertyConnection(String property) {
        Properties prop = new Properties();

        try {
            prop.load(
                    getClass().getClassLoader().getResourceAsStream(property)
            );
            CONNECTION_URL =
                    prop.getProperty("connection.url") +
                            prop.getProperty("connection.database") +
                            "?user=" + prop.getProperty("connection.user") +
                            "&password=" + prop.getProperty("connection.password")+
                            "&characterEncoding=" + prop.getProperty("connection.encoding");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        public static Connection getConnection(String url) throws SQLException {
        return DriverManager.getConnection(url);
    }
    public static synchronized DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    DBManager() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        readPropertyConnection("db.properties");
    }

    public static void createDB(){
        Connection con = null;
        try {

            con = DBManager.getInstance().getConnection(CONNECTION_URL);
            System.out.println(CONNECTION_URL);
            ScriptRunner.run(con, new File(
                    readProperty("db.properties", "connection.init.path")
            ));
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close(con);
        }
    }
    public static void close(AutoCloseable ac) {
        if (ac != null) {
            try {
                ac.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void rollback(Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void commitAndClose(Connection con) {
        if (con != null) {
            try {
//                con.commit();
                con.close();
            } catch (SQLException ex) {
                logger.error("Cannot commit transaction and close connection", ex);
            }
        }
    }
}

