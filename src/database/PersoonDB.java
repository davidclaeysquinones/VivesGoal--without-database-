/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import databag.Persoon;
import databag.Ploeg;
import exception.ApplicationException;
import exception.DBException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author david
 */
public class PersoonDB {
    public PersoonDB(){
        
    }
    
    public ArrayList<Persoon> zoekAllePersonen() throws DBException, ApplicationException
    {
         ArrayList<Persoon> kl = new ArrayList<>();
      // connectie tot stand brengen (en automatisch sluiten)
      try (Connection conn = ConnectionManager
              .getConnection();) {

         // preparedStatement opstellen (en automtisch sluiten)
         try (PreparedStatement stmt = conn.
            prepareStatement(
               "select id, naam, voornaam, geboortedatum,isTrainer,opmerking from persoon order by naam, voornaam");) {
               // execute voert elke sql-statement uit, executeQuery enkel de eenvoudige
               stmt.execute();
               // result opvragen (en automatisch sluiten)
               try (ResultSet r = stmt.getResultSet()) {
                  
                  while (r.next()) {

                     Persoon k = new Persoon();
                     k.setId(r.getInt("id"));
                     k.setNaam(r.getString("naam"));
                     k.setVoornaam(r.getString("voornaam"));
                     k.setGeboortedatum(r.getDate("geboortedatum"));
                     k.setTrainer(r.getBoolean("isTrainer"));
                     k.setOpmerking(r.getString("opmerking"));
                     kl.add(k);
                  }
                  
               }catch(NullPointerException e)
               {
                   throw new ApplicationException("Er werden geen personen gevonden");
               }
               
               catch (SQLException sqlEx) {
                  throw new DBException(
                     "SQL-exception in zoekAlleTrainers - resultset"+ sqlEx);
               }
            } catch (SQLException sqlEx) {
               throw new DBException(
                  "SQL-exception in zoekAlleTrainers - statement"+ sqlEx);
            }
      } catch (SQLException sqlEx) {
         throw new DBException(
            "SQL-exception in zoekAlleTrainers - connection"+ sqlEx);
      }
      return kl;
    }
   public Persoon zoekPersoon(int id) throws DBException, ApplicationException {
      Persoon returnPersoon = null;
      // connectie tot stand brengen (en automatisch sluiten)
      try (Connection conn = ConnectionManager.getConnection();) {
         // preparedStatement opstellen (en automtisch sluiten)
         try (PreparedStatement stmt = conn.prepareStatement(
            "select id, naam, voornaam, geboortedatum, isTrainer,opmerking,ploeg_id from persoon where id=?");) {
            stmt.setInt(1, id);
            // execute voert het SQL-statement uit
            stmt.execute();
            // result opvragen (en automatisch sluiten)
            try (ResultSet r = stmt.getResultSet()) {
               // van de persoon uit de database een Persoon-object maken
               Persoon k = new Persoon();

               // er werd een persoon gevonden
               if (r.next()) {
                  k.setId(r.getInt("id"));
                  k.setNaam(r.getString("naam"));
                  k.setVoornaam(r.getString("voornaam"));
                  k.setGeboortedatum(r.getDate("geboortedatum"));
                  k.setTrainer(r.getBoolean("isTrainer"));
                  k.setOpmerking(r.getString("opmerking"));
                  k.setPloegid(r.getInt("ploeg_id"));
                  returnPersoon = k;
               }

               return returnPersoon;
            }catch(NullPointerException e)
            {
                throw new ApplicationException("De opgegeven persoon werd niet gevonden");
            }
            
            catch (SQLException sqlEx) {
               throw new DBException("SQL-exception in zoekPersoon(int id) - resultset" + sqlEx);
            }
         } catch (SQLException sqlEx) {
            throw new DBException("SQL-exception in zoekPersoon(int id)- statement"+ sqlEx);
         }
      } catch (SQLException sqlEx) {
         throw new DBException(
            "SQL-exception in zoekPersoon(int id)- connection");
      }
   }
   public Persoon zoekPersoon(String naam,String voornaam) throws DBException, ApplicationException {
      Persoon returnPersoon = null;
      // connectie tot stand brengen (en automatisch sluiten)
      try (Connection conn = ConnectionManager.getConnection();) {
         // preparedStatement opstellen (en automtisch sluiten)
         try (PreparedStatement stmt = conn.prepareStatement(
            "select id, naam, voornaam, geboortedatum, isTrainer,opmerking,ploeg_id from persoon where voornaam =? and naam=?");) {
            stmt.setString(1,voornaam );
            stmt.setString(2, naam);
             
            // execute voert het SQL-statement uit
            stmt.execute();
            // result opvragen (en automatisch sluiten)
            try (ResultSet r = stmt.getResultSet()) {
               // van de persoon uit de database een Persoon-object maken
               Persoon k = new Persoon();

               // er werd een persoon gevonden
               if (r.next()) {
                  k.setId(r.getInt("id"));
                  k.setNaam(r.getString("naam"));
                  k.setVoornaam(r.getString("voornaam"));
                  k.setGeboortedatum(r.getDate("geboortedatum"));
                  k.setTrainer(r.getBoolean("isTrainer"));
                  k.setOpmerking(r.getString("opmerking"));
                  k.setPloegid(r.getInt("ploeg_id"));
                  returnPersoon = k;
               }

               return returnPersoon;
            }
            
            
            catch(NullPointerException e)
                {
                    throw new ApplicationException("Er staat geen persoon in de database met de opgegeven naam en voornaam");
                }   
            
            catch (SQLException sqlEx) {
               throw new DBException("SQL-exception in zoekPersoon(String naam,String voornaam) - resultset" + sqlEx);
            }
         } catch (SQLException sqlEx) {
            throw new DBException("SQL-exception in zoekPersoon(String naam,String voornaam) - statement"+ sqlEx);
         }
      } catch (SQLException sqlEx) {
         throw new DBException(
            "SQL-exception in zoekPersoon(String naam,String voornaam)t - connection");
      }
   }
   public Persoon zoekPersoon(Persoon p) throws DBException, ApplicationException {
      Persoon a=zoekPersoon(p.getId());
      return a;
   }
     // debugged method
   public ArrayList<Persoon> zoekAlleTrainers() throws DBException, ApplicationException {

      ArrayList<Persoon> kl = new ArrayList<>();
      // connectie tot stand brengen (en automatisch sluiten)
      try (Connection conn = ConnectionManager
              .getConnection();) {

         // preparedStatement opstellen (en automtisch sluiten)
         try (PreparedStatement stmt = conn.
            prepareStatement(
               "select id, naam, voornaam, geboortedatum,isTrainer,opmerking from persoon where isTrainer =true order by naam, voornaam");) {
               // execute voert elke sql-statement uit, executeQuery enkel de eenvoudige
               stmt.execute();
               // result opvragen (en automatisch sluiten)
               try (ResultSet r = stmt.getResultSet()) {
                  
                  while (r.next()) {

                     Persoon k = new Persoon();
                     k.setId(r.getInt("id"));
                     k.setNaam(r.getString("naam"));
                     k.setVoornaam(r.getString("voornaam"));
                     k.setGeboortedatum(r.getDate("geboortedatum"));
                     k.setTrainer(r.getBoolean("isTrainer"));
                     k.setOpmerking(r.getString("opmerking"));
                     kl.add(k);
                  }
                  
               }catch(NullPointerException e)
               {
                   throw new ApplicationException("Er werden geen trainers gevonden");
               }
               
               catch (SQLException sqlEx) {
                  throw new DBException(
                     "SQL-exception in zoekAlleTrainers - resultset"+ sqlEx);
               }
            } catch (SQLException sqlEx) {
               throw new DBException(
                  "SQL-exception in zoekAlleTrainers - statement"+ sqlEx);
            }
      } catch (SQLException sqlEx) {
         throw new DBException(
            "SQL-exception in zoekAlleTrainers - connection"+ sqlEx);
      }
      return kl;
   }
   
    
   // debugged method
   public ArrayList<Persoon> zoekAlleSpelers() throws DBException, ApplicationException {

      ArrayList<Persoon> kl = new ArrayList<>();
      // connectie tot stand brengen (en automatisch sluiten)
      try (Connection conn = ConnectionManager
              .getConnection();) {

         // preparedStatement opstellen (en automtisch sluiten)
         try (PreparedStatement stmt = conn.
            prepareStatement(
               "select id, naam, voornaam, geboortedatum,isTrainer,opmerking from persoon where isTrainer = false order by naam, voornaam");) {
               // execute voert elke sql-statement uit, executeQuery enkel de eenvoudige
               stmt.execute();
               // result opvragen (en automatisch sluiten)
               try (ResultSet r = stmt.getResultSet()) {
                  
                  while (r.next()) {

                     Persoon k = new Persoon();
                     k.setId(r.getInt("id"));
                     k.setNaam(r.getString("naam"));
                     k.setVoornaam(r.getString("voornaam"));
                     k.setGeboortedatum(r.getDate("geboortedatum"));
                     k.setTrainer(r.getBoolean("isTrainer"));
                     k.setOpmerking(r.getString("opmerking"));
                     kl.add(k);
                  }
                  return kl;
               } catch (SQLException sqlEx) {
                  throw new DBException(
                     "SQL-exception in zoekAlleSpelers - resultset"+ sqlEx);
               }
            } catch (SQLException sqlEx) {
               throw new DBException(
                  "SQL-exception in zoekAlleSpelers - statement"+ sqlEx);
            }
      } catch (SQLException sqlEx) {
         throw new DBException(
            "SQL-exception in zoekAlleSpelers - connection"+ sqlEx);
      }

   }
   
    public ArrayList<Persoon> zoekSpelersPloeg(String ploegnaam) throws DBException,ApplicationException {

      ArrayList<Persoon> kl = new ArrayList<>();
      // connectie tot stand brengen (en automatisch sluiten)
      try (Connection conn = ConnectionManager.getConnection();) {
         // preparedStatement opstellen (en automtisch sluiten)
         try (PreparedStatement stmt = conn.prepareStatement("select id, naam, voornaam, geboortedatum, isTrainer from persoon where trainer=\"false\" and id in (select speler from ploegpersoon where ploeg in(select id from ploeg where naam='?')) order by naam,voornaam");) {
            stmt.setString(1, ploegnaam);
            // execute voert elke sql-statement uit, executeQuery enkel de eenvoudige
            stmt.execute();
            // result opvragen (en automatisch sluiten)
            try (ResultSet r = stmt.getResultSet()) {
               // van alle spelers uit de database Persoon-objecten maken
             

               while (r.next()) {
                  Persoon k = new Persoon();
                  k.setId(r.getInt("id"));
                  k.setNaam(r.getString("naam"));
                  k.setVoornaam(r.getString("voornaam"));
                  k.setGeboortedatum(r.getDate("geboortedatum"));
                  k.setTrainer(r.getBoolean("isTrainer"));
                  kl.add(k);
               }
               return kl;
            } catch (SQLException sqlEx) {
               throw new DBException(
                  "SQL-exception in zoekSpelersPloeg(String ploegnaam) - resultset"+ sqlEx);
            }
         } catch (SQLException sqlEx) {
            throw new DBException("SQL-exception in zoekSpelersPloeg(String ploegnaam) - statement"+ sqlEx);
         }
      } catch (SQLException sqlEx) {
         throw new DBException(
            "SQL-exception in zoekSpelersPloeg(String ploegnaam) - connection"+ sqlEx);
      }
   }
   
   public ArrayList<Persoon> zoekSpelersPloeg(int id) throws DBException,ApplicationException {

      ArrayList<Persoon> kl = new ArrayList<>();
      // connectie tot stand brengen (en automatisch sluiten)
      try (Connection conn = ConnectionManager.getConnection();) {
         // preparedStatement opstellen (en automtisch sluiten)
         try (PreparedStatement stmt = conn.prepareStatement("select id, naam, voornaam, geboortedatum, isTrainer from persoon where trainer=\"false\" and ploeg_id=? order by naam,voornaam");) {
            stmt.setInt(1, id);
            // execute voert elke sql-statement uit, executeQuery enkel de eenvoudige
            stmt.execute();
            // result opvragen (en automatisch sluiten)
            try (ResultSet r = stmt.getResultSet()) {
               // van alle spelers uit de database Ploeg-objecten maken
          

               while (r.next()) {
                  Persoon k = new Persoon();
                  k.setId(r.getInt("id"));
                  k.setNaam(r.getString("naam"));
                  k.setVoornaam(r.getString("voornaam"));
                  k.setGeboortedatum(r.getDate("geboortedatum"));
                  k.setTrainer(r.getBoolean("isTrainer"));
                  kl.add(k);
               }
               return kl;
            } catch (SQLException sqlEx) {
               throw new DBException(
                  "SQL-exception in zoekSpelersPloeg(int id) - resultset"+ sqlEx);
            }
         } catch (SQLException sqlEx) {
            throw new DBException("SQL-exception in zoekSpelersPloeg(int id)n - statement"+ sqlEx);
         }
      } catch (SQLException sqlEx) {
         throw new DBException(
            "SQL-exception in zoekSpelersPloeg(int id) - connection"+ sqlEx);
      }
   }
    public ArrayList<Persoon> zoekSpelersPloeg(Ploeg p) throws DBException,ApplicationException {

      ArrayList<Persoon> kl = zoekSpelersPloeg(p.getId());
      return kl;
      
   }
    
    //   debugged method
   public void verwijderPersoon(int id) throws DBException, ApplicationException {

      // connectie tot stand brengen (en automatisch sluiten)
      try (Connection conn = ConnectionManager.getConnection();) {
         // preparedStatement opstellen (en automtisch sluiten)
        
          try (PreparedStatement stmt = conn.prepareStatement(
            "update persoon set ploeg_id=null where id=?" );) {
             stmt.setInt(1, id);
            stmt.execute();      
         } 
          try (PreparedStatement stmt = conn.prepareStatement(
            "update ploeg set trainer_id=NULL WHERE trainer_id=?");) {
            stmt.setInt(1, id);
            // execute voert elke sql-statement uit, executeQuery enkel de select
            stmt.execute();
            
         } 
          try (PreparedStatement stmt = conn.prepareStatement(
            "DELETE FROM persoon WHERE id=?");) {
            stmt.setInt(1, id);
            // execute voert elke sql-statement uit, executeQuery enkel de select
            stmt.execute();
            
         }catch (NullPointerException e )
         {
             throw new ApplicationException("De opgegeven persoon werd niet gevonden");
         }
          
          
          catch (SQLException sqlEx) {
            throw new DBException("SQL-exception in verwijderPersoon(int id) - statement"+ sqlEx);
         }
      } catch (SQLException sqlEx) {
         throw new DBException(
            "SQL-exception in verwijderPersoon(int id) - connection"+ sqlEx);
      }
   }
//   debugged method
   public void verwijderPersoon(String naam,String voornaam) throws DBException, ApplicationException {

      // connectie tot stand brengen (en automatisch sluiten)
      try (Connection conn = ConnectionManager.getConnection();) {
         // preparedStatement opstellen (en automtisch sluiten)
        
       
              Persoon a =zoekPersoon(naam,voornaam);
              verwijderPersoon(a);
          
          
          
      } catch (SQLException sqlEx) {
         throw new DBException(
            "SQL-exception in verwijderPersoon(String naam,String voornaam) - connection"+ sqlEx);
      }
   }
//   debugged method
   public void verwijderPersoon(Persoon p) throws DBException, ApplicationException {
       verwijderPersoon(p.getId());
   }
   
   //   debugged method
   public void toevoegenPersoon(Persoon p) throws DBException {

      // connectie tot stand brengen (en automatisch sluiten)
      try (Connection conn = ConnectionManager.getConnection();) {
         // preparedStatement opstellen (en automtisch sluiten)
         if(p.getPloegid()!=null)
         {
            try (PreparedStatement stmt = conn.prepareStatement(
            "INSERT INTO `persoon` (Naam, Voornaam, Geboortedatum, isTrainer, Opmerking,ploeg_id) VALUES (?,?,?,?,?,?);");) {
            stmt.setString(1, p.getNaam());
            stmt.setString(2, p.getVoornaam());
            stmt.setDate(3,new java.sql.Date(p.getGeboortedatum().getTime()) );
            stmt.setBoolean(4, p.getTrainer());
            stmt.setString(5,p.getOpmerking());
            stmt.setInt(6, p.getPloegid()); 
            
            
            stmt.execute();

            
         } catch (SQLException sqlEx) {
            throw new DBException("SQL-exception in toevoegenPersoon(PersoonBag p) - statement "+ sqlEx);
         } 
         }
         else
         {
             try (PreparedStatement stmt = conn.prepareStatement(
            "INSERT INTO `persoon` (Naam, Voornaam, Geboortedatum, isTrainer, Opmerking) VALUES (?,?,?,?,?);");) {
            stmt.setString(1, p.getNaam());
            stmt.setString(2, p.getVoornaam());
            stmt.setDate(3,new java.sql.Date(p.getGeboortedatum().getTime()) );
            stmt.setBoolean(4, p.getTrainer());
            stmt.setString(5,p.getOpmerking());
            
            
            
            stmt.execute();

            
         } catch (SQLException sqlEx) {
            throw new DBException("SQL-exception in toevoegenPersoon(PersoonBag p) - statement "+ sqlEx);
         } 
         }
          
      } catch (SQLException sqlEx) {
         throw new DBException(
            "SQL-exception in toevoegenPersoon(PersoonBag p) - connection "+ sqlEx);
      }

   }
   
   //    debugged
   public void wijzigenPersoon(Persoon p) throws DBException, ApplicationException {

      // connectie tot stand brengen (en automatisch sluiten)
      try (Connection conn = ConnectionManager.getConnection();) {
         // preparedStatement opstellen (en automtisch sluiten)
          
          System.out.println(p.getTrainer());
          if(p.getNaam()!=null && p.getVoornaam()!=null && p.getGeboortedatum()!=null && p.getOpmerking()!=null )
          {
              try (PreparedStatement stmt = conn.
            prepareStatement("update persoon set naam = ?, voornaam =?,geboortedatum = ?,trainer = ?,opmerking= ? where id = ?");) {

            stmt.setString(1, p.getNaam());
            stmt.setString(2, p.getVoornaam());
            stmt.setDate(3, new java.sql.Date(p.getGeboortedatum().getTime()));
            stmt.setBoolean(4, p.getTrainer());
            stmt.setString(5, p.getOpmerking());
            stmt.setInt(6, p.getId());

            // execute voert elke sql-statement uit, executeQuery enkel de select
            stmt.execute();
         }catch(NullPointerException e)
         {
             throw new ApplicationException("De opgegeven persoon kon niet gewijzigd worden "+e.getMessage());
         } 
         
         catch (SQLException sqlEx) {
            throw new DBException("SQL-exception in wijzigenPersoon(PersoonBag p) -sql statement"+ sqlEx);
         }
          }
          else
          {
              if(p.getNaam()!=null && p.getVoornaam()!=null && p.getGeboortedatum()!=null)
              {
                  try (PreparedStatement stmt = conn.
                     prepareStatement("update persoon set naam = ?, voornaam =?,geboortedatum = ?,isTrainer = ? where id = ?");) {

                     stmt.setString(1, p.getNaam());
                     stmt.setString(2, p.getVoornaam());
                     stmt.setDate(3, new java.sql.Date(p.getGeboortedatum().getTime()));
                     stmt.setBoolean(4, p.getTrainer());
                     stmt.setInt(5, p.getId());

                       // execute voert elke sql-statement uit, executeQuery enkel de select
                    stmt.execute();
                  }catch(NullPointerException e)
                 {
                   throw new ApplicationException("De opgegeven persoon kon niet gewijzigd worden "+e.getMessage());
                 } 
         
                    catch (SQLException sqlEx) {
                     throw new DBException("SQL-exception in wijzigenPersoon(PersoonBag p) -sql statement"+ sqlEx);
                }
              }
              else
              {
                  if(p.getNaam()!=null && p.getVoornaam()!=null)
                  {
                      try (PreparedStatement stmt = conn.
                         prepareStatement("update persoon set naam = ?, voornaam =?,isTrainer = ? where id = ?");) {

                             stmt.setString(1, p.getNaam());
                             stmt.setString(2, p.getVoornaam());                      
                             stmt.setBoolean(3,p.getTrainer());
                             stmt.setInt(4, p.getId());

                            // execute voert elke sql-statement uit, executeQuery enkel de select
                            stmt.execute();
                        }catch(NullPointerException e)
                          {
                                throw new ApplicationException("De opgegeven persoon kon niet gewijzigd worden "+e.getMessage());
                          } 
         
                          catch (SQLException sqlEx) {
                                throw new DBException("SQL-exception in wijzigenPersoon(PersoonBag p) -sql statement"+ sqlEx);
                            }
                  }
                  else
                  {
                      if(p.getNaam()!=null)
                      {
                          try (PreparedStatement stmt = conn.
                         prepareStatement("update persoon set naam = ?,isTrainer = ? where id = ?");) {

                             stmt.setString(1, p.getNaam());         
                             stmt.setBoolean(2, p.getTrainer());
                             stmt.setInt(3, p.getId());

                            // execute voert elke sql-statement uit, executeQuery enkel de select
                            stmt.execute();
                        }catch(NullPointerException e)
                          {
                                throw new ApplicationException("De opgegeven persoon kon niet gewijzigd worden "+e.getMessage());
                          } 
         
                          catch (SQLException sqlEx) {
                                throw new DBException("SQL-exception in wijzigenPersoon(PersoonBag p) -sql statement"+ sqlEx);
                            }
                      }
                      else
                      {
                          try (PreparedStatement stmt = conn.
                         prepareStatement("update persoon set voornaam =?, isTrainer = ? where id = ?");) {

                             stmt.setString(1, p.getVoornaam());         
                             stmt.setBoolean(2,p.getTrainer());
                             stmt.setInt(3, p.getId());

                            // execute voert elke sql-statement uit, executeQuery enkel de select
                            stmt.execute();
                        }catch(NullPointerException e)
                          {
                                throw new ApplicationException("De opgegeven persoon kon niet gewijzigd worden "+e.getMessage());
                          } 
         
                          catch (SQLException sqlEx) {
                                throw new DBException("SQL-exception in wijzigenPersoon(PersoonBag p) -sql statement"+ sqlEx);
                            }
                      }
                  }
              }
          }
          
      } catch (SQLException sqlEx) {
         throw new DBException(
            "SQL-exception in wijzigenPersoon(PersoonBag p) - connection"+ sqlEx);
      }
   }
   //   debugged
   public void wijzigenPersoon(String naam,String voornaam,Persoon p) throws DBException, ApplicationException {

          Persoon a =zoekPersoon(naam,voornaam);
          p.setId(a.getId());
          wijzigenPersoon(p);
         
   }
   
}
