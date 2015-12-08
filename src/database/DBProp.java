/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import exception.DBException;
import java.io.FileInputStream;
import java.util.Properties;

/**
sets the properties to get connection with the database
 */
public class DBProp {

   private static String dbUrl;
   private static String driver;
   private static String login;
   private static String paswoord;

   private DBProp() throws DBException {
      Properties appProperties = new Properties();
      try (FileInputStream in = new FileInputStream("DB.properties")) {
         appProperties.load(in);

         dbUrl = appProperties.getProperty("dbUrl");
         driver = appProperties.getProperty("driver");
         login = appProperties.getProperty("login");
         paswoord = appProperties.getProperty("paswoord");
         
         

      } catch (java.io.IOException ex) {
         throw new DBException(
            "Bestand (DB.properties) met gegevens over DB niet gevonden.");
      }
    
   }

   /**
    @return the dbUrl
    */
   public static String getDbUrl() throws DBException {
      if (dbUrl == null) {
         DBProp db = new DBProp();
      }
      return dbUrl;
   }

   /**
    @return the driver
    */
   public static String getDriver() throws DBException {
      if (driver == null) {
         DBProp db = new DBProp();
      }
      return driver;
   }

   /**
    @return the login
    */
   public static String getLogin() throws DBException {
      if (login == null) {
         DBProp db = new DBProp();
      }
      return login;
   }

   /**
    @return the paswoord
    */
   public static String getPaswoord() throws DBException {
      if (paswoord == null) {
         DBProp db = new DBProp();
      }
      return paswoord;
   }
}
