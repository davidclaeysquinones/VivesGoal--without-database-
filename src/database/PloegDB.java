package database;

/**
 *
 * @author David
 */
import databag.*;
import datatype.Categorie;
import exception.ApplicationException;
import exception.DBException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * database functionaliteiten voor ploegen
 */
public class PloegDB {

    private PersoonDB persoonDB;

    public PloegDB() {
        persoonDB = new PersoonDB();
    }

    /**
     *
     * @param id het id van de ploeg die je wilt zoeken
     * @return De te zoeken ploeg wordt geretourneerd.
     * @throws DBException
     * @throws ApplicationException
     */
    public Ploeg zoekPloeg(int id) throws DBException, ApplicationException {
        Ploeg returnPloeg = null;
        // connectie tot stand brengen (en automatisch sluiten)
        try (Connection conn = ConnectionManager.getConnection();) {
            // preparedStatement opstellen (en automtisch sluiten)
            try (PreparedStatement stmt = conn.prepareStatement(
                    "select id, naam,niveau,trainer_id from ploeg where id = ?");) {
                stmt.setInt(1, id);
                // execute voert het SQL-statement uit
                stmt.execute();
                // result opvragen (en automatisch sluiten)
                try (ResultSet r = stmt.getResultSet()) {
                    // van de ploeg uit de database een Ploeg-object maken
                    Ploeg k = new Ploeg();

                    // er werd een ploeg gevonden
                    if (r.next()) {
                        k.setId(r.getInt("id"));
                        k.setNaam(r.getString("naam"));
                        k.setCategorie(r.getString("niveau"));
                        if (r.getObject("trainer_id") == null) {
                            k.setTrainer(null);
                        } else {
                            k.setTrainer(r.getInt("trainer_id"));
                        }

                        returnPloeg = k;
                    }

                    return returnPloeg;
                } catch (SQLException sqlEx) {
                    throw new DBException("SQL-exception in zoekPloeg(int id) - resultset" + sqlEx);
                }
            } catch (SQLException sqlEx) {
                throw new DBException("SQL-exception in zoekPloeg(int id) - statement" + sqlEx);
            }
        } catch (SQLException sqlEx) {
            throw new DBException(
                    "SQL-exception in zoekPloeg(int id) - connection");
        }
    }

    /**
     *
     * @param naam de naam van de ploeg die je zoekt
     * @return De te zoeken ploeg wordt geretourneerd.
     * @throws DBException
     * @throws ApplicationException
     */
    public Ploeg zoekPloeg(String naam) throws DBException, ApplicationException {
        Ploeg returnPloeg = null;
        // connectie tot stand brengen (en automatisch sluiten)
        try (Connection conn = ConnectionManager.getConnection();) {
            // preparedStatement opstellen (en automtisch sluiten)
            try (PreparedStatement stmt = conn.prepareStatement(
                    "select id, naam,niveau,trainer_id from ploeg where naam=?");) {
                stmt.setString(1, naam);
                // execute voert het SQL-statement uit
                stmt.execute();
                // result opvragen (en automatisch sluiten)
                try (ResultSet r = stmt.getResultSet()) {
                    // van de ploeg uit de database een Ploeg-object maken
                    Ploeg k = new Ploeg();

                    // er werd een ploeg gevonden
                    if (r.next()) {
                        k.setId(r.getInt("id"));
                        k.setNaam(r.getString("naam"));
                        k.setCategorie(r.getString("niveau"));
                        if (r.getObject("trainer_id") == null) {
                            k.setTrainer(null);
                        } else {
                            k.setTrainer(r.getInt("trainer_id"));
                        }
                        returnPloeg = k;
                    }

                    return returnPloeg;
                } catch (SQLException sqlEx) {
                    throw new DBException("SQL-exception in zoekPloeg(String naam) - resultset" + sqlEx);
                }
            } catch (SQLException sqlEx) {
                throw new DBException("SQL-exception in zoekPloeg(String naam) - statement" + sqlEx);
            }
        } catch (SQLException sqlEx) {
            throw new DBException(
                    "SQL-exception in zoekPloeg(String naam) - connection");
        }
    }

    public Ploeg zoekPloeg(Ploeg p) throws DBException, ApplicationException {
        Ploeg returnPloeg = zoekPloeg(p.getNaam());
        return returnPloeg;

    }

    /**
     *
     * @return ArrayList met alle ploegen
     * @throws DBException
     * @throws ApplicationException
     */
    public ArrayList<Ploeg> zoekAllePloegen() throws DBException, ApplicationException {
        ArrayList ploegen = new ArrayList();
        // connectie tot stand brengen (en automatisch sluiten)
        try (Connection conn = ConnectionManager.getConnection();) {
            // preparedStatement opstellen (en automtisch sluiten)
            try (PreparedStatement stmt = conn.prepareStatement(
                    "select id, naam,niveau,trainer_id from ploeg");) {

                // execute voert het SQL-statement uit
                stmt.execute();
                // result opvragen (en automatisch sluiten)
                try (ResultSet r = stmt.getResultSet()) {
                    for (int i = 0; i < ploegen.size(); i++) {
                        Ploeg p = new Ploeg();
                        if (r.next()) {
                            p.setId(r.getInt("id"));
                            p.setNaam(r.getString("naam"));
                            p.setCategorie(r.getString("niveau"));
                            p.setTrainer(r.getInt("trainer_id"));

                            ploegen.add(p);
                        }
                    }

                    return ploegen;

                } catch (SQLException sqlEx) {
                    throw new DBException("SQL-exception in zoekAllePloegen() - resultset" + sqlEx);
                }
            } catch (SQLException sqlEx) {
                throw new DBException("SQL-exception in zoekAllePloegen() - statement" + sqlEx);
            }
        } catch (SQLException sqlEx) {
            throw new DBException(
                    "SQL-exception in zoekPloeg(String naam) - connection");
        }

    }

    /**
     * deze methode zoekt de trainer op van de opgegeven ploeg
     *
     * @param ploegid het id van de ploeg waarvan je de trainer wilt zoeken
     * @return Geeft de trainer weer van een ploeg
     * @throws DBException
     */
    public Persoon getTrainer(int ploegid) throws DBException {
        try (Connection conn = ConnectionManager.getConnection();) {

            // preparedStatement opstellen (en automtisch sluiten)
            try (PreparedStatement stmt = conn.
                    prepareStatement(
                            "select id,naam,voornaam,geboortedatum,isTrainer,opmerking from persoon where id in (select trainer_id from ploeg where id='?')");) {
                        // execute voert elke sql-statement uit, executeQuery enkel de eenvoudige
                        stmt.execute();
                        // result opvragen (en automatisch sluiten)
                        try (ResultSet r = stmt.getResultSet()) {
                            Persoon k = new Persoon();
                            while (r.next()) {

                                k.setId(r.getInt("id"));
                                k.setNaam(r.getString("naam"));
                                k.setVoornaam(r.getString("voornaam"));
                                k.setGeboortedatum(r.getDate("geboortedatum"));
                                k.setTrainer(r.getBoolean("isTrainer"));
                                k.setOpmerking(r.getString("opmerking"));

                            }
                            return k;
                        }catch (SQLException sqlEx) {
                            throw new DBException(
                                    "SQL-exception in getTrainer (int ploegid) - resultset" + sqlEx);
                        }
                    } catch (SQLException sqlEx) {
                        throw new DBException(
                                "SQL-exception in getTrainer (int ploegid) - statement" + sqlEx);
                    }
        } catch (SQLException sqlEx) {
            throw new DBException(
                    "SQL-exception in getTrainer (int ploegid) - connection" + sqlEx);
        }
    }

    /**
     * deze methode zoekt de trainer op van de opgegeven ploeg
     *
     * @param p de ploeg waarvan je de trainer wilt zoeken
     * @return Geeft de trainer weer van een ploeg
     * @throws DBException
     * @throws ApplicationException
     */
    public Persoon getTrainer(Ploeg p) throws DBException, ApplicationException {
        return getTrainer(p.getNaam());
    }

    /**
     * deze methode zoekt de trainer op van de opgegeven ploeg
     *
     * @param ploegnaam de naam van de ploeg waarvan je de trainer wilt zoeken
     * @return
     * @throws DBException
     */
    public Persoon getTrainer(String ploegnaam) throws DBException {
        try (Connection conn = ConnectionManager.getConnection();) {

            // preparedStatement opstellen (en automtisch sluiten)
            try (PreparedStatement stmt = conn.
                    prepareStatement(
                            "select id,naam,voornaam,geboortedatum,isTrainer,opmerking from persoon where id in (select trainer_id from ploeg where id in (select id from ploeg where naam='?'))");) {
                        // execute voert elke sql-statement uit, executeQuery enkel de eenvoudige
                        stmt.execute();
                        // result opvragen (en automatisch sluiten)
                        try (ResultSet r = stmt.getResultSet()) {
                            Persoon k = new Persoon();
                            while (r.next()) {

                                k.setId(r.getInt("id"));
                                k.setNaam(r.getString("naam"));
                                k.setVoornaam(r.getString("voornaam"));
                                k.setGeboortedatum(r.getDate("geboortedatum"));
                                k.setTrainer(r.getBoolean("isTrainer"));
                                k.setOpmerking(r.getString("opmerking"));

                            }
                            return k;
                        }catch (SQLException sqlEx) {
                            throw new DBException(
                                    "SQL-exception in getTrainer (String ploegnaam) - resultset" + sqlEx);
                        }
                    } catch (SQLException sqlEx) {
                        throw new DBException(
                                "SQL-exception in getTrainer (String ploegnaam) - statement" + sqlEx);
                    }
        } catch (SQLException sqlEx) {
            throw new DBException(
                    "SQL-exception in getTrainer (String ploegnaam) - connection" + sqlEx);
        }
    }

    /**
     * ploeg toevoegen aan database
     *
     * @param p de ploeg die je wilt toevoegen
     * @throws DBException
     */
    public void toevoegenPloeg(Ploeg p) throws DBException {

        // connectie tot stand brengen (en automatisch sluiten)
        try (Connection conn = ConnectionManager.getConnection();) {
            // preparedStatement opstellen (en automtisch sluiten)
            if (p.getTrainer() != null) {

                try (PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO ploeg (`naam`, `niveau`, `trainer_id`) VALUES (?,?,?)");) {
                    stmt.setString(1, p.getNaam());
                    stmt.setString(2, p.getCategorie().getTekst());
                    stmt.setInt(3, p.getTrainer());

                    stmt.execute();
                } catch (SQLException sqlEx) {
                    throw new DBException("SQL-exception in toevoegenPloeg(PloegBag p) - statement" + sqlEx);
                }
            } else {
                try (PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO ploeg (`naam`, `niveau`) VALUES (?,?)");) {
                    stmt.setString(1, p.getNaam());
                    stmt.setString(2, p.getCategorie().getTekst());

                    stmt.execute();
                } catch (SQLException sqlEx) {
                    throw new DBException("SQL-exception in toevoegenPloeg(PloegBag p) - statement" + sqlEx);
                }
            }

        } catch (SQLException sqlEx) {
            throw new DBException(
                    "SQL-exception in toevoegenPloeg(PloegBag p) - connection" + sqlEx);
        }

    }

    /**
     * ploeg verwijderen van database
     *
     * @param p de ploeg die je wilt verwijderen
     * @throws DBException
     */
    public void verwijderPloeg(Ploeg p) throws DBException {

        verwijderPloeg(p.getNaam());

    }

    /**
     * ploeg verwijderen van database
     *
     * @param ploegid het id van de ploeg die je wilt verwijderen
     * @throws DBException
     */
    public void verwijderPloeg(int ploegid) throws DBException {

        // connectie tot stand brengen (en automatisch sluiten)
        try (Connection conn = ConnectionManager.getConnection();) {

            // preparedStatement opstellen (en automtisch sluiten)
            try (PreparedStatement stmt = conn.prepareStatement(
                    "update persoon set ploeg_id = NULL where ploeg_id = ?");) {
                stmt.setInt(1, ploegid);
                stmt.execute();
            }
            // preparedStatement opstellen (en automtisch sluiten)
            try (PreparedStatement stmt = conn.prepareStatement(
                    "delete from ploeg where id=?");) {
                stmt.setInt(1, ploegid);

                stmt.execute();
            } catch (SQLException sqlEx) {
                throw new DBException("SQL-exception in verwijderPloeg(int ploegid) - statement" + sqlEx);
            }
        } catch (SQLException sqlEx) {
            throw new DBException(
                    "SQL-exception in verwijderPloeg(int ploegid) - connection" + sqlEx);
        }

    }

    /**
     * ploeg verwijderen van database
     *
     * @param naam de naam van de ploeg die je wilt verwijderen
     * @throws DBException
     */
    public void verwijderPloeg(String naam) throws DBException {

        // connectie tot stand brengen (en automatisch sluiten)
        try (Connection conn = ConnectionManager.getConnection();) {
            // preparedStatement opstellen (en automtisch sluiten)

            try (PreparedStatement stmt = conn.prepareStatement(
                    "update persoon set ploeg_id = NULL where ploeg_id in (select id from ploeg where naam=?)");) {
                stmt.setString(1, naam);
                stmt.execute();
            }
            // preparedStatement opstellen (en automtisch sluiten)
            try (PreparedStatement stmt = conn.prepareStatement(
                    "delete from ploeg where naam=?");) {
                stmt.setString(1, naam);

                stmt.execute();
            } catch (SQLException sqlEx) {
                throw new DBException("SQL-exception in verwijderPloeg(String naam) - statement" + sqlEx);
            }
        } catch (SQLException sqlEx) {
            throw new DBException(
                    "SQL-exception in verwijderPloeg(String naam) - connection" + sqlEx);
        }

    }

    /**
     * deze methode verwijdert alle ploegen
     *
     * @throws DBException
     */
    public void verwijderAllePloegen() throws DBException {

        // connectie tot stand brengen (en automatisch sluiten)
        try (Connection conn = ConnectionManager.getConnection();) {
            // preparedStatement opstellen (en automtisch sluiten)
            try (PreparedStatement stmt = conn.prepareStatement(
                    "update persoon set ploeg_id = NULL");) {

                stmt.execute();
            }
            // preparedStatement opstellen (en automtisch sluiten)
            try (PreparedStatement stmt = conn.prepareStatement(
                    "delete from ploeg ");) {

                stmt.execute();
            } catch (SQLException sqlEx) {
                throw new DBException("SQL-exception in verwijderAllePloegen() - statement" + sqlEx);
            }
        } catch (SQLException sqlEx) {
            throw new DBException(
                    "SQL-exception in verwijderAllePloegen() - connection" + sqlEx);
        }

    }

    /**
     * deze methode koppelt de opgegeven speler aan de opgegeven ploeg
     *
     * @param ploegid De ploeg waaraan je een speler wilt koppelen.
     * @param persoonid De persoon die je aan een ploeg wilt koppelen.
     * @throws DBException
     */
    public void toevoegenSpelerPloeg(int ploegid, int persoonid) throws DBException {

        // connectie tot stand brengen (en automatisch sluiten)
        try (Connection conn = ConnectionManager.getConnection();) {
            // preparedStatement opstellen (en automtisch sluiten)
            try (PreparedStatement stmt = conn.prepareStatement(
                    "update persoon set ploeg_id=? where id=?;");) {
                stmt.setInt(1, ploegid);
                stmt.setInt(2, persoonid);

                stmt.execute();

            } catch (SQLException sqlEx) {
                throw new DBException("SQL-exception in toevoegenSpelerPloeg(int ploegid, int persoonid) - statement" + sqlEx);
            }
        } catch (SQLException sqlEx) {
            throw new DBException(
                    "SQL-exception in toevoegenSpelerPloeg(int ploegid, int persoonid) - connection" + sqlEx);
        }
    }

    /**
     * deze methode koppelt de opgegeven speler aan de opgegeven ploeg
     *
     * @param ploegnaam De ploeg waaraan je een speler wilt koppelen.
     * @param persoon De persoon die je aan een ploeg wilt koppelen.
     * @throws DBException
     */
    public void toevoegenSpelerPloeg(String ploegnaam, Persoon persoon) throws DBException, ApplicationException {
        Persoon p = persoonDB.zoekPersoon(persoon);
        // connectie tot stand brengen (en automatisch sluiten)
        try (Connection conn = ConnectionManager.getConnection();) {
            // preparedStatement opstellen (en automtisch sluiten)
            try (PreparedStatement stmt = conn.prepareStatement(
                    "update persoon set ploeg_id=(select id from ploeg where naam=?) where id=?;");) {
                stmt.setString(1, ploegnaam);
                stmt.setInt(2, p.getId());

                stmt.execute();

            } catch (SQLException sqlEx) {
                throw new DBException("SQL-exception in toevoegenSpelerPloeg(String naam,PersoonBag p) - statement" + sqlEx);
            }
        } catch (SQLException sqlEx) {
            throw new DBException(
                    "SQL-exception in toevoegenSpelerPloeg(String naam,PersoonBag p) - connection" + sqlEx);
        }
    }

    /**
     * deze methode koppelt de opgegeven speler van de ploegen waarin hij zit
     *
     * @param ploeg De ploeg waaraan je een speler wilt koppelen.
     * @param speler De persoon die je aan een ploeg wilt koppelen.
     * @throws DBException
     */
    public void toevoegenSpelerPloeg(Ploeg ploeg, Persoon speler) throws DBException, ApplicationException {

        toevoegenSpelerPloeg(ploeg.getNaam(), speler);
    }

    /**
     * deze methode ontkoppelt de opgegeven speler van de ploegen waarin hij zit
     *
     * @param id Het id van de speler die je wilt ontkoppelen
     * @throws DBException
     */
    public void verwijderSpelerPloeg(int id) throws DBException {

        // connectie tot stand brengen (en automatisch sluiten)
        try (Connection conn = ConnectionManager.getConnection();) {
            // preparedStatement opstellen (en automtisch sluiten)

            try (PreparedStatement stmt = conn.prepareStatement(
                    "update persoon set ploeg_id=null where id=?;");) {
                stmt.setInt(1, id);

                stmt.execute();

            } catch (SQLException sqlEx) {
                throw new DBException("SQL-exception in verwijderSpelerPloeg(int id) - statement" + sqlEx);
            }
        } catch (SQLException sqlEx) {
            throw new DBException(
                    "SQL-exception in verwijderSpelerPloeg(int id) - connection" + sqlEx);
        }
    }

    /**
     * deze methode ontkoppelt de opgegeven speler aan de opgegeven ploeg
     *
     * @param p De speler die je wilt ontkoppelen
     * @throws DBException
     * @throws ApplicationException
     */
    public void verwijderSpelerPloeg(Persoon p) throws DBException, ApplicationException {
        verwijderSpelerPloeg(p.getNaam(), p.getVoornaam());
    }

    /**
     * deze methode ontkoppelt de opgegeven speler van de ploegen waarin hij zit
     *
     * @param naam De naam van de speler die je wilt ontkoppelen
     * @param voornaam De voornaam van de speler die je wilt ontkoppelen
     * @throws DBException
     */
    public void verwijderSpelerPloeg(String naam, String voornaam) throws DBException, ApplicationException {

        Persoon p = persoonDB.zoekPersoon(naam, voornaam);
        verwijderSpelerPloeg(p.getId());
    }

    /**
     * deze methode koppelt de opgegeven trainer aan de opgegeven ploeg
     *
     * @param persoonid het id van de trainer
     * @param ploegid het id van de ploeg
     * @throws DBException
     */
    public void toevoegenTrainerPloeg(int persoonid, int ploegid) throws DBException {
        // connectie tot stand brengen (en automatisch sluiten)
        try (Connection conn = ConnectionManager.getConnection();) {
            // preparedStatement opstellen (en automtisch sluiten)
            try (PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE ploeg set trainer_id=? where id=?");) {
                stmt.setInt(1, persoonid);
                stmt.setInt(2, ploegid);

                stmt.execute();

            } catch (SQLException sqlEx) {
                throw new DBException("SQL-exception in toevoegenTrainerPloeg(int persoonid,int ploegid) - statement" + sqlEx);
            }
        } catch (SQLException sqlEx) {
            throw new DBException(
                    "SQL-exception in toevoegenTrainerPloeg(int persoonid,int ploegid) - connection" + sqlEx);
        }
    }

    /**
     * deze methode koppelt de opgegeven trainer aan de opgegeven ploeg
     *
     * @param persoon Het persoon object van de trainer die je wilt toevoegen.
     * @param ploeg Het ploeg object waraan je een trainer wilt toevoegen.
     * @throws DBException
     * @throws ApplicationException
     */
    public void toevoegenTrainerPloeg(Persoon persoon, Ploeg ploeg) throws DBException, ApplicationException {

        toevoegenTrainerPloeg(persoon.getNaam(), persoon.getVoornaam(), ploeg.getNaam());

    }

    /**
     * deze methode koppelt de opgegeven trainer aan de opgegeven ploeg
     *
     * @param naam de naam van de trainer
     * @param voornaam de voornaam van de trainer
     * @param ploegnaam de ploegnaam van de ploeg
     * @throws DBException
     * @throws ApplicationException
     */
    public void toevoegenTrainerPloeg(String naam, String voornaam, String ploegnaam) throws DBException, ApplicationException {

        Persoon persoon = persoonDB.zoekPersoon(naam, voornaam);
        Ploeg ploeg = zoekPloeg(ploegnaam);
        toevoegenTrainerPloeg(persoon.getId(), ploeg.getId());

    }

    /**
     * deze methode ontkoppelt de trainer van de opgegeven ploeg
     *
     * @param ploegid het id van de opgegeven ploeg
     * @throws DBException
     */
    public void verwijderTrainerPloeg(int ploegid) throws DBException {

        // connectie tot stand brengen (en automatisch sluiten)
        try (Connection conn = ConnectionManager.getConnection();) {
            // preparedStatement opstellen (en automtisch sluiten)
            try (PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE ploeg set trainer_id=null where id=?");) {
                stmt.setInt(1, ploegid);

                stmt.execute();

            } catch (SQLException sqlEx) {
                throw new DBException("SQL-exception in verwijderTrainerPloeg((int ploegid)) - statement" + sqlEx);
            }
        } catch (SQLException sqlEx) {
            throw new DBException(
                    "SQL-exception in verwijderTrainerPloeg(int ploegid) - connection" + sqlEx);
        }
    }

    /**
     * deze methode ontkoppelt de trainer van de opgegeven ploeg
     *
     * @param p de opgegeven ploeg
     * @throws DBException
     * @throws ApplicationException
     */
    public void verwijderTrainerPloeg(Ploeg p) throws DBException, ApplicationException {
        verwijderTrainerPloeg(p.getNaam());
    }

    /**
     * deze methode ontkoppelt de trainer van de opgegeven ploeg
     *
     * @param ploegnaam de naam van de opgegeven ploeg
     * @throws DBException
     */
    public void verwijderTrainerPloeg(String ploegnaam) throws DBException {

        // connectie tot stand brengen (en automatisch sluiten)
        try (Connection conn = ConnectionManager.getConnection();) {
            // preparedStatement opstellen (en automtisch sluiten)
            try (PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE ploeg set trainer_id=null where naam=?");) {
                stmt.setString(1, ploegnaam);

                stmt.execute();

            } catch (SQLException sqlEx) {
                throw new DBException("SQL-exception in verwijderTrainerPloeg((int ploegid)) - statement" + sqlEx);
            }
        } catch (SQLException sqlEx) {
            throw new DBException(
                    "SQL-exception in verwijderTrainerPloeg(int ploegid) - connection" + sqlEx);
        }
    }

    /**
     * deze methode geeft een ArrayList terug waarin alle spelers van de
     * opgegeven ploeg zitten
     *
     * @param ploegnaam de naam van de ploeg waarvan je alle spelers wilt
     * opvragen
     * @return
     * @throws DBException
     * @throws ApplicationException
     */
    public ArrayList<Persoon> zoekSpelersPloeg(String ploegnaam) throws DBException, ApplicationException {

        Ploeg p = zoekPloeg(ploegnaam);
        ArrayList<Persoon> a = zoekSpelersPloeg(p.getId());
        return a;
    }

    /**
     * deze methode geeft een ArrayList terug waarin alle spelers van de
     * opgegeven ploeg zitten
     *
     * @param id het id van de ploeg waarvan je alle spelers wilt opvragen
     * @return
     * @throws DBException
     */
    public ArrayList<Persoon> zoekSpelersPloeg(int id) throws DBException {

        ArrayList<Persoon> sp = new ArrayList<>();
        // connectie tot stand brengen (en automatisch sluiten)
        try (Connection conn = ConnectionManager.getConnection();) {
            // preparedStatement opstellen (en automtisch sluiten)
            try (PreparedStatement stmt = conn.prepareStatement("select id, naam, voornaam, geboortedatum, isTrainer,ploeg_id from persoon where isTrainer=\"false\" and ploeg_id in(select id from ploeg where id=?)");) {
                stmt.setInt(1, id);
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
                        if (r.getObject("ploeg_id") == null) {
                            k.setPloegid(null);
                        } else {
                            k.setPloegid(r.getInt("ploeg_id"));
                        }

                        sp.add(k);

                    }

                    return sp;
                } catch (SQLException sqlEx) {
                    throw new DBException(
                            "SQL-exception in zoekSpelersPloeg(int id) - resultset" + sqlEx);
                }
            } catch (SQLException sqlEx) {
                throw new DBException("SQL-exception in zoekSpelersPloeg(int id)n - statement" + sqlEx);
            }
        } catch (SQLException sqlEx) {
            throw new DBException(
                    "SQL-exception in zoekSpelersPloeg(int id) - connection" + sqlEx);
        }
    }

    /**
     * deze methode geeft een ArrayList terug waarin alle spelers van de
     * opgegeven ploeg zitten
     *
     * @param p de ploeg waarvan je alle spelers wilt opvragen
     * @return
     * @throws DBException
     * @throws ApplicationException
     */
    public ArrayList<Persoon> zoekSpelersPloeg(Ploeg p) throws DBException, ApplicationException {

        ArrayList<Persoon> kl = zoekSpelersPloeg(p.getNaam());
        return kl;

    }

    /**
     * deze methode geeft een ArrayList terug met alle ploegen die de opgegeven
     * persoon als trainer hebben
     *
     * @param trainer_id het id van de opgegeven persoon
     * @return
     * @throws DBException
     * @throws ApplicationException
     */
    public ArrayList<Ploeg> zoekPloegenTrainer(int trainer_id) throws DBException, ApplicationException {
        ArrayList<Ploeg> kl = new ArrayList<>();
        // connectie tot stand brengen (en automatisch sluiten)
        try (Connection conn = ConnectionManager.getConnection();) {
            // preparedStatement opstellen (en automtisch sluiten)
            try (PreparedStatement stmt = conn.prepareStatement("select id, naam, niveau, trainer_id from ploeg where trainer_id=?");) {
                stmt.setInt(1, trainer_id);
                // execute voert elke sql-statement uit, executeQuery enkel de eenvoudige
                stmt.execute();
                // result opvragen (en automatisch sluiten)
                try (ResultSet r = stmt.getResultSet()) {
                    // van alle spelers uit de database Ploeg-objecten maken

                    while (r.next()) {
                        Ploeg k = new Ploeg();
                        k.setId(r.getInt("id"));
                        k.setNaam(r.getString("naam"));
                        k.setCategorie(r.getString("niveau"));
                        k.setTrainer(r.getInt("trainer_id"));

                        kl.add(k);
                    }
                    return kl;
                } catch (SQLException sqlEx) {
                    throw new DBException(
                            "SQL-exception in zoekPloegenTrainer(int trainer_id) - resultset" + sqlEx);
                }
            } catch (SQLException sqlEx) {
                throw new DBException("SQL-exception in zoekPloegenTrainer(int trainer_id) - statement" + sqlEx);
            }
        } catch (SQLException sqlEx) {
            throw new DBException(
                    "SQL-exception in zoekPloegenTrainer(int trainer_id) - connection" + sqlEx);
        }
    }

    /**
     * deze methode geeft een ArrayList terug waarin alle ploegen zitten met de opgegeven categorie
     * 
     * @param categorie de opgegegeven categorie
     * @return
     * @throws ApplicationException
     * @throws DBException 
     */
    public ArrayList<Ploeg> zoekPloegenCategorie(Categorie categorie) throws ApplicationException, DBException {
        ArrayList<Ploeg> kl = new ArrayList<>();
        // connectie tot stand brengen (en automatisch sluiten)
        try (Connection conn = ConnectionManager.getConnection();) {
            // preparedStatement opstellen (en automtisch sluiten)
            try (PreparedStatement stmt = conn.prepareStatement("select id, naam, niveau, trainer_id from ploeg where niveau=?");) {
                stmt.setString(1, categorie.getTekst());
                // execute voert elke sql-statement uit, executeQuery enkel de eenvoudige
                stmt.execute();
                // result opvragen (en automatisch sluiten)
                try (ResultSet r = stmt.getResultSet()) {
                    // van alle spelers uit de database Ploeg-objecten maken

                    while (r.next()) {
                        Ploeg k = new Ploeg();
                        k.setId(r.getInt("id"));
                        k.setNaam(r.getString("naam"));
                        k.setCategorie(r.getString("niveau"));
                        if (r.getObject("trainer_id") == null) {
                            k.setTrainer(null);
                        } else {
                            k.setTrainer(r.getInt("trainer_id"));
                        }

                        kl.add(k);
                    }
                    return kl;
                } catch (SQLException sqlEx) {
                    throw new DBException(
                            "SQL-exception in zoekPloegenCategorie(Categorie categorie) - resultset" + sqlEx);
                }
            } catch (SQLException sqlEx) {
                throw new DBException("SQL-exception in zoekPloegenCategorie(Categorie categorie) - statement" + sqlEx);
            }
        } catch (SQLException sqlEx) {
            throw new DBException(
                    "SQL-exception in zoekPloegenCategorie(Categorie categorie) - connection" + sqlEx);
        }
    }

}
