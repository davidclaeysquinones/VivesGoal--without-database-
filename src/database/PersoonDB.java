/**
 *
 * @author David
 */
package database;

import databag.Persoon;
import exception.ApplicationException;
import exception.DBException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * database functionaliteiten voor spelers
 *
 */
public class PersoonDB {

    public PersoonDB() {

    }
/**
     *
     * @return Geeft een ArrayList weer van alle personen in de database met hun gegevens.
     * @throws DBException Als de database een error geeft, dit kan doordat er geen 
     *                     connectie gemaakt kan worden, een fout in de ontvangen 
     *                     data of een fout in de opgegeven query.
     * @throws ApplicationException Als de database leeg is.
     */
    public ArrayList<Persoon> zoekAllePersonen() throws DBException, ApplicationException {
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

                        } catch (SQLException sqlEx) {
                            throw new DBException(
                                    "SQL-exception in zoekAllePersonen() - resultset" + sqlEx);
                        }
                    } catch (SQLException sqlEx) {
                        throw new DBException(
                                "SQL-exception in zoekAllePersonen() - statement" + sqlEx);
                    }
        } catch (SQLException sqlEx) {
            throw new DBException(
                    "SQL-exception in zoekAllePersonen() - connection" + sqlEx);
        }
        return kl;
    }

    /**
     * zoekt een persoon
     * 
     * @param id het id van de persoon die je wilt zoeken
     * @return  geef een persoon terug met al zijn gegevens.
     * @throws DBException Als de database een error geeft, dit kan doordat er geen 
     *                     connectie gemaakt kan worden, een fout in de ontvangen 
     *                     data of een fout in de opgegeven query.
     * @throws ApplicationException Als de persoon niet in de database zit.
     */
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
                        if (r.getObject("ploeg_id") == null) {
                            k.setPloegid(null);
                        } else {
                            k.setPloegid(r.getInt("ploeg_id"));
                        }

                        returnPersoon = k;
                    }

                    return returnPersoon;
                } 
                catch (SQLException sqlEx) {
                    throw new DBException("SQL-exception in zoekPersoon(int id) - resultset" + sqlEx);
                }
            } catch (SQLException sqlEx) {
                throw new DBException("SQL-exception in zoekPersoon(int id)- statement" + sqlEx);
            }
        } catch (SQLException sqlEx) {
            throw new DBException(
                    "SQL-exception in zoekPersoon(int id)- connection");
        }
    }

    /**
     * zoekt een persoon.
     * 
     * @param naam de (achter)naam van de te zoeken persoon
     * @param voornaam de voornaam van de te zoeken persoon
     * @return  geef een persoon terug met al zijn gegevens.
     * @throws DBException Als de database een error geeft, dit kan doordat er geen 
     *                     connectie gemaakt kan worden, een fout in de ontvangen 
     *                     data of een fout in de opgegeven query.
     * @throws ApplicationException Als de persoon niet in de database zit.
     */
    public Persoon zoekPersoon(String naam, String voornaam) throws DBException, ApplicationException {
        Persoon returnPersoon = null;
        // connectie tot stand brengen (en automatisch sluiten)
        try (Connection conn = ConnectionManager.getConnection();) {
            // preparedStatement opstellen (en automtisch sluiten)
            try (PreparedStatement stmt = conn.prepareStatement(
                    "select id, naam, voornaam, geboortedatum, isTrainer,opmerking,ploeg_id from persoon where voornaam =? and naam=?");) {
                stmt.setString(1, voornaam);
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
                        if (r.getObject("ploeg_id") == null) {
                            k.setPloegid(null);
                        } else {
                            k.setPloegid(r.getInt("ploeg_id"));
                        }
                        returnPersoon = k;
                    }

                    return returnPersoon;
                } 
                catch (SQLException sqlEx) {
                    throw new DBException("SQL-exception in zoekPersoon(String naam,String voornaam) - resultset" + sqlEx);
                }
            } catch (SQLException sqlEx) {
                throw new DBException("SQL-exception in zoekPersoon(String naam,String voornaam) - statement" + sqlEx);
            }
        } catch (SQLException sqlEx) {
            throw new DBException(
                    "SQL-exception in zoekPersoon(String naam,String voornaam)t - connection");
        }
    }

    /**
     * zoekt een persoon
     * 
     * @param p een persoonobject van de te zoeken persoon.
     * @return  geef een persoon terug met al zijn gegevens.
     * @throws DBException Als de database een error geeft, dit kan doordat er geen 
     *                     connectie gemaakt kan worden, een fout in de ontvangen 
     *                     data of een fout in de opgegeven query.
     * @throws ApplicationException Als de persoon niet in de database zit.
     */
    public Persoon zoekPersoon(Persoon p) throws DBException, ApplicationException {
        Persoon a = zoekPersoon(p.getNaam(), p.getVoornaam());
        return a;
    }

    /**
     * zoekt alle trainers
     * 
     * @return  geeft alle trainers (persoon objecten) weer in een ArrayList.
     * @throws DBException Als de database een error geeft, dit kan doordat er geen 
     *                     connectie gemaakt kan worden, een fout in de ontvangen 
     *                     data of een fout in de opgegeven query.
     * @throws ApplicationException Als er geen trainers in de database aanwezig zijn.
     */
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

                        } catch (SQLException sqlEx) {
                            throw new DBException(
                                    "SQL-exception in zoekAlleTrainers - resultset" + sqlEx);
                        }
                    } catch (SQLException sqlEx) {
                        throw new DBException(
                                "SQL-exception in zoekAlleTrainers - statement" + sqlEx);
                    }
        } catch (SQLException sqlEx) {
            throw new DBException(
                    "SQL-exception in zoekAlleTrainers - connection" + sqlEx);
        }
        return kl;
    }

    /**
     * zoekt alle spelers
     * 
     * @return  geeft alle spelers (persoon objecten) weer in een ArrayList.
     * @throws DBException Als de database een error geeft, dit kan doordat er geen 
     *                     connectie gemaakt kan worden, een fout in de ontvangen 
     *                     data of een fout in de opgegeven query.
     * @throws ApplicationException Als ergeen spelers in de database aanwezig zijn.
     */
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
                                    "SQL-exception in zoekAlleSpelers - resultset" + sqlEx);
                        }
                    } catch (SQLException sqlEx) {
                        throw new DBException(
                                "SQL-exception in zoekAlleSpelers - statement" + sqlEx);
                    }
        } catch (SQLException sqlEx) {
            throw new DBException(
                    "SQL-exception in zoekAlleSpelers - connection" + sqlEx);
        }

    }

    /**
     * verwijdert alle personen in de database
     * 
     * @throws DBException Als de database een error geeft, dit kan doordat er geen 
     *                     connectie gemaakt kan worden, een fout in de ontvangen 
     *                     data of een fout in de opgegeven query.
     */
    public void verwijderAllePersonen() throws DBException {
        // connectie tot stand brengen (en automatisch sluiten)
        try (Connection conn = ConnectionManager.getConnection();) {
            // preparedStatement opstellen (en automtisch sluiten)
            try (PreparedStatement stmt = conn.prepareStatement(
                    "update persoon set ploeg_id=null");) {

                stmt.execute();
            }
            try (PreparedStatement stmt = conn.prepareStatement(
                    "update ploeg set trainer_id=NULL");) {

                // execute voert elke sql-statement uit, executeQuery enkel de select
                stmt.execute();

            }
            try (PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM persoon");) {

                // execute voert elke sql-statement uit, executeQuery enkel de select
                stmt.execute();

            } catch (SQLException sqlEx) {
                throw new DBException("SQL-exception in verwijderAllePersonen() - statement" + sqlEx);
            }
        } catch (SQLException sqlEx) {
            throw new DBException(
                    "SQL-exception in verwijderAllePersonen() - connection" + sqlEx);
        }
    }

    /**
     * verwijdert een persoon via zijn id.
     * 
     * @param id de id van de te verwijderen persoon.
     * @throws DBException Als de database een error geeft, dit kan doordat er geen 
     *                     connectie gemaakt kan worden, een fout in de ontvangen 
     *                     data of een fout in de opgegeven query.
     * @throws ApplicationException Als de opgegeven persoon niet aanwezig is in de database.
     */
    public void verwijderPersoon(int id) throws DBException, ApplicationException {

        // connectie tot stand brengen (en automatisch sluiten)
        try (Connection conn = ConnectionManager.getConnection();) {
            // preparedStatement opstellen (en automtisch sluiten)

            try (PreparedStatement stmt = conn.prepareStatement(
                    "update persoon set ploeg_id=null where id=?");) {
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

            } catch (SQLException sqlEx) {
                throw new DBException("SQL-exception in verwijderPersoon(int id) - statement" + sqlEx);
            }
        } catch (SQLException sqlEx) {
            throw new DBException(
                    "SQL-exception in verwijderPersoon(int id) - connection" + sqlEx);
        }
    }
//   debugged method

    /**
     * verwijdert een persoon via zijn naam en voornaam.
     * 
     * @param naam de naam van de te verwijderen persoon.
     * @param voornaam de voornaam van de te verwijderen persoon.
     * @throws DBException Als de database een error geeft, dit kan doordat er geen 
     *                     connectie gemaakt kan worden, een fout in de ontvangen 
     *                     data of een fout in de opgegeven query.
     * @throws ApplicationException Als de opgegeven persoon niet aanwezig is in de database.
     */
    public void verwijderPersoon(String naam, String voornaam) throws DBException, ApplicationException {

        Persoon a = zoekPersoon(naam, voornaam);
        verwijderPersoon(a.getId());

    }
    
/**
     * verwijdert een persoon via een persoon object.
     * 
     * @param p het persoon object van de te verwijderen persoon.
     * @throws DBException Als de database een error geeft, dit kan doordat er geen 
     *                     connectie gemaakt kan worden, een fout in de ontvangen 
     *                     data of een fout in de opgegeven query.
     * @throws ApplicationException Als de opgegeven persoon niet aanwezig is in de database.
     */
    public void verwijderPersoon(Persoon p) throws DBException, ApplicationException {
        verwijderPersoon(p.getId());
    }

    /**
     * voegt een persoon toe.
     * 
     * @param p het toe te voegen persoon object.
     * @throws DBException Als de database een error geeft, dit kan doordat er geen 
     *                     connectie gemaakt kan worden, een fout in de ontvangen 
     *                     data of een fout in de opgegeven query.
     */
    public void toevoegenPersoon(Persoon p) throws DBException {

        // connectie tot stand brengen (en automatisch sluiten)
        try (Connection conn = ConnectionManager.getConnection();) {
            // preparedStatement opstellen (en automtisch sluiten)
            if (p.getPloegid() != null) {
                try (PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO `persoon` (Naam, Voornaam, Geboortedatum, isTrainer, Opmerking,ploeg_id) VALUES (?,?,?,?,?,?);");) {
                    stmt.setString(1, p.getNaam());
                    stmt.setString(2, p.getVoornaam());
                    stmt.setDate(3, new java.sql.Date(p.getGeboortedatum().getTime()));
                    stmt.setBoolean(4, p.getTrainer());
                    stmt.setString(5, p.getOpmerking());
                    stmt.setInt(6, p.getPloegid());

                    stmt.execute();

                } catch (SQLException sqlEx) {
                    throw new DBException("SQL-exception in toevoegenPersoon(PersoonBag p) - statement " + sqlEx);
                }
            } else {
                try (PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO `persoon` (Naam, Voornaam, Geboortedatum, isTrainer, Opmerking) VALUES (?,?,?,?,?);");) {
                    stmt.setString(1, p.getNaam());
                    stmt.setString(2, p.getVoornaam());
                    stmt.setDate(3, new java.sql.Date(p.getGeboortedatum().getTime()));
                    stmt.setBoolean(4, p.getTrainer());
                    stmt.setString(5, p.getOpmerking());

                    stmt.execute();

                } catch (SQLException sqlEx) {
                    throw new DBException("SQL-exception in toevoegenPersoon(PersoonBag p) - statement " + sqlEx);
                }
            }

        } catch (SQLException sqlEx) {
            throw new DBException(
                    "SQL-exception in toevoegenPersoon(PersoonBag p) - connection " + sqlEx);
        }

    }
    /**
     * Deze methode werkt slechts als het Persoon-Object een correct id heeft (
     * een id dat reeds in de database zit ). Op basis daarvan worden alle
     * opgegeven gegevens gewijzigd
     *
     * De gegevens van een persoon wijzigen.
     * 
     * @param p het persoon object van de te wijzigen persoon.
     * @throws DBException Als de database een error geeft, dit kan doordat er geen 
     *                     connectie gemaakt kan worden, een fout in de ontvangen 
     *                     data of een fout in de opgegeven query.
     * @throws ApplicationException Als de persoon niet gewijzigd kon worden doordat geen enkel
     *                              gegeven in het Persoon object werd ingevuld
     */
    public void wijzigenPersoon(Persoon p) throws DBException, ApplicationException {

        // connectie tot stand brengen (en automatisch sluiten)
        try (Connection conn = ConnectionManager.getConnection();) {
            // preparedStatement opstellen (en automtisch sluiten)

            if (p.getNaam() != null && p.getVoornaam() != null && p.getGeboortedatum() != null && p.getOpmerking() != null) {
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
                } catch (SQLException sqlEx) {
                    throw new DBException("SQL-exception in wijzigenPersoon(PersoonBag p) -sql statement" + sqlEx);
                }
            } else {
                if (p.getNaam() != null || p.getVoornaam() != null || p.getGeboortedatum() != null || p.getOpmerking() != null) {
                    if (p.getNaam() != null) {
                        try (PreparedStatement stmt = conn.
                                prepareStatement("update persoon set naam = ? where id = ?");) {

                            stmt.setString(1, p.getNaam());
                            stmt.setInt(2, p.getId());

                            // execute voert elke sql-statement uit, executeQuery enkel de select
                            stmt.execute();
                        } catch (SQLException sqlEx) {
                            throw new DBException("SQL-exception in wijzigenPersoon(PersoonBag p) -sql statement" + sqlEx);
                        }
                    }
                    if (p.getVoornaam() != null) {
                        try (PreparedStatement stmt = conn.
                                prepareStatement("update persoon set voornaam = ? where id = ?");) {

                            stmt.setString(1, p.getVoornaam());
                            stmt.setInt(2, p.getId());

                            // execute voert elke sql-statement uit, executeQuery enkel de select
                            stmt.execute();
                        } catch (SQLException sqlEx) {
                            throw new DBException("SQL-exception in wijzigenPersoon(PersoonBag p) -sql statement" + sqlEx);
                        }
                    }

                    if (p.getGeboortedatum() != null) {
                        try (PreparedStatement stmt = conn.
                                prepareStatement("update persoon set geboortedatum = ? where id = ?");) {

                            stmt.setDate(1, new java.sql.Date(p.getGeboortedatum().getTime()));
                            stmt.setInt(2, p.getId());

                            // execute voert elke sql-statement uit, executeQuery enkel de select
                            stmt.execute();
                        } catch (SQLException sqlEx) {
                            throw new DBException("SQL-exception in wijzigenPersoon(PersoonBag p) -sql statement" + sqlEx);
                        }
                    }
                    if (p.getOpmerking() != null) {
                        try (PreparedStatement stmt = conn.
                                prepareStatement("update persoon set opmerking = ? where id = ?");) {

                            stmt.setString(1, p.getOpmerking());
                            stmt.setInt(2, p.getId());

                            // execute voert elke sql-statement uit, executeQuery enkel de select
                            stmt.execute();
                        } catch (SQLException sqlEx) {
                            throw new DBException("SQL-exception in wijzigenPersoon(PersoonBag p) -sql statement" + sqlEx);
                        }
                    }
                } else {
                    throw new ApplicationException("De opgegeven persoon kon niet gewijzigd worden");
                }
            }

        } catch (SQLException sqlEx) {
            throw new DBException(
                    "SQL-exception in wijzigenPersoon(PersoonBag p) - connection" + sqlEx);
        }
    }

    /**
     * Deze methode neemt de naam en voornaam van de persoon waarvan je de
     * gegevens wilt wijzigen. Het Persoon object heeft alle gegevens die je
     * wilt wijzigen.
     *
     * @param naam naam van de persoon
     * @param voornaam voornaam van de persoon
     * @param p Persoon object met alle gegevens die je wilt wijzigen
     * @throws DBException Als de database een error geeft, dit kan doordat er geen 
     *                     connectie gemaakt kan worden, een fout in de ontvangen 
     *                     data of een fout in de opgegeven query.
     *  @throws ApplicationException Als de persoon niet gewijzigd kon worden doordat geen enkel
     *                              gegeven in het Persoon object werd ingevuld
     */
     
    public void wijzigenPersoon(String naam, String voornaam, Persoon p) throws DBException, ApplicationException {

        Persoon a = zoekPersoon(naam, voornaam);
        p.setId(a.getId());
        wijzigenPersoon(p);

    }

    /**
     * kijkt of een persoon bestaat in de database.
     * 
     * @param id id van de persoon.
     * @return geeft true of false terug als de persoon al dan niet bestaat.
     * @throws DBException Als de database een error geeft, dit kan doordat er geen 
     *                     connectie gemaakt kan worden, een fout in de ontvangen 
     *                     data of een fout in de opgegeven query.
     * @throws ApplicationException Als de persoon niet bestaat.
     */
    public boolean bestaatPersoon(int id) throws DBException, ApplicationException {
        // connectie tot stand brengen (en automatisch sluiten)
        try (Connection conn = ConnectionManager.getConnection();) {
            // preparedStatement opstellen (en automtisch sluiten)

            try (PreparedStatement stmt = conn.prepareStatement(
                    "select * from persoon where id=?");) {
                stmt.setInt(1, id);
                stmt.execute();

                ResultSet r = stmt.getResultSet();
                if (r.next() != false) {
                    return true;
                } else {
                    return false;
                }
            } catch (SQLException sqlEx) {
                throw new DBException("SQL-exception in bestaatPersoon(Persoon p) - statement" + sqlEx);
            }

        } catch (SQLException sqlEx) {
            throw new DBException(
                    "SQL-exception in bestaatPersoon(Persoon p) - connection" + sqlEx);
        }
    }

}
