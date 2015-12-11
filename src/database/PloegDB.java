package database;

/**
 *
 * @author David
 */
import databag.*;
import datatype.Categorie;
import exception.*;
import java.sql.*;
import java.util.*;

/**
 * Bevat alle functionaliteiten voor de database
 */
public class PloegDB {

    private PersoonDB persoonDB;

    public PloegDB() {
        persoonDB = new PersoonDB();
    }

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
                        k.setTrainer(r.getInt("trainer_id"));

                        returnPloeg = k;
                    }

                    return returnPloeg;
                } catch (NullPointerException e) {
                    throw new ApplicationException("De opgegeven id staat niet in de database");
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

    // debugged method
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
                        k.setTrainer(r.getInt("trainer_id"));

                        returnPloeg = k;
                    }

                    return returnPloeg;
                } catch (NullPointerException e) {
                    throw new ApplicationException("De opgegeven ploegnaam staat niet in de database");
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

    public Persoon getTrainer(int ploegid) throws DBException, ApplicationException {
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
                        } catch (NullPointerException e) {
                            throw new ApplicationException("De opgegeven ploeg werd niet gevonden");
                        } catch (SQLException sqlEx) {
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

    public Persoon getTrainer(Ploeg p) throws DBException, ApplicationException {
        return getTrainer(p.getNaam());
    }

    public Persoon getTrainer(String ploegnaam) throws DBException, ApplicationException {
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
                        } catch (NullPointerException e) {
                            throw new ApplicationException("De opgegeven ploeg werd niet gevonden");
                        } catch (SQLException sqlEx) {
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

    //Ruben
    public void toevoegenPloeg(Ploeg p) throws DBException, ApplicationException {

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

    //Ruben
    public void verwijderPloeg(Ploeg p) throws DBException {

        verwijderPloeg(p.getNaam());

    }

    //Ruben
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

    // Ruben
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
// Ruben
//   debugged

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

    public void toevoegenSpelerPloeg(int ploegid, int persoonid) throws DBException, ApplicationException {

        // connectie tot stand brengen (en automatisch sluiten)
        try (Connection conn = ConnectionManager.getConnection();) {
            // preparedStatement opstellen (en automtisch sluiten)
            try (PreparedStatement stmt = conn.prepareStatement(
                    "update persoon set ploeg_id=? where id=?;");) {
                stmt.setInt(1, ploegid);
                stmt.setInt(2, persoonid);

                stmt.execute();

            } catch (NullPointerException e) {
                throw new ApplicationException("De opgegeven persoon of ploeg bestaat niet");
            } catch (SQLException sqlEx) {
                throw new DBException("SQL-exception in toevoegenSpelerPloeg(int ploegid,PersoonBag p) - statement" + sqlEx);
            }
        } catch (SQLException sqlEx) {
            throw new DBException(
                    "SQL-exception in toevoegenSpelerPloeg(int ploegid,PersoonBag p) - connection" + sqlEx);
        }
    }
//   debugged

    /**
     * Het Persoon object moet een bestaand naam en voornaam hebben
     *
     * @param ploegnaam
     * @param persoon
     * @throws DBException
     * @throws ApplicationException
     */
    public void toevoegenSpelerPloeg(String ploegnaam, Persoon persoon) throws DBException, ApplicationException {
        Persoon p = persoonDB.zoekPersoon(persoon.getNaam(), persoon.getVoornaam());
        // connectie tot stand brengen (en automatisch sluiten)
        try (Connection conn = ConnectionManager.getConnection();) {
            // preparedStatement opstellen (en automtisch sluiten)
            try (PreparedStatement stmt = conn.prepareStatement(
                    "update persoon set ploeg_id=(select id from ploeg where naam=?) where id=?;");) {
                stmt.setString(1, ploegnaam);
                stmt.setInt(2, p.getId());

                stmt.execute();

            } catch (NullPointerException e) {
                throw new ApplicationException("De opgegeven persoon of ploeg bestaat niet");
            } catch (SQLException sqlEx) {
                throw new DBException("SQL-exception in toevoegenSpelerPloeg(String naam,PersoonBag p) - statement" + sqlEx);
            }
        } catch (SQLException sqlEx) {
            throw new DBException(
                    "SQL-exception in toevoegenSpelerPloeg(String naam,PersoonBag p) - connection" + sqlEx);
        }
    }
//   debugged

    public void toevoegenSpelerPloeg(Ploeg ploeg, Persoon speler) throws DBException, ApplicationException {

        toevoegenSpelerPloeg(ploeg.getNaam(), speler);
    }

    public void verwijderSpelerPloeg(int id) throws DBException, ApplicationException {

        // connectie tot stand brengen (en automatisch sluiten)
        try (Connection conn = ConnectionManager.getConnection();) {
            // preparedStatement opstellen (en automtisch sluiten)

            try (PreparedStatement stmt = conn.prepareStatement(
                    "update persoon set ploeg_id=null where id=?;");) {
                stmt.setInt(1, id);

                stmt.execute();

            } catch (NullPointerException e) {
                throw new ApplicationException("De speler werd niet teruggevonden" + e.getMessage());
            } catch (SQLException sqlEx) {
                throw new DBException("SQL-exception in verwijderSpelerPloeg(int id) - statement" + sqlEx);
            }
        } catch (SQLException sqlEx) {
            throw new DBException(
                    "SQL-exception in verwijderSpelerPloeg(int id) - connection" + sqlEx);
        }
    }

    public void verwijderSpelerPloeg(Persoon p) throws DBException, ApplicationException {
        verwijderSpelerPloeg(p.getNaam(), p.getVoornaam());
    }

    public void verwijderSpelerPloeg(String naam, String voornaam) throws DBException, ApplicationException {

        Persoon p = persoonDB.zoekPersoon(naam, voornaam);
        verwijderSpelerPloeg(p.getId());
    }

    public void toevoegenTrainerPloeg(int persoonid, int ploegid) throws DBException, ApplicationException {
        // connectie tot stand brengen (en automatisch sluiten)
        try (Connection conn = ConnectionManager.getConnection();) {
            // preparedStatement opstellen (en automtisch sluiten)
            try (PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE ploeg set trainer_id=? where id=?");) {
                stmt.setInt(1, persoonid);
                stmt.setInt(2, ploegid);

                stmt.execute();

            } catch (NullPointerException e) {
                throw new ApplicationException("De opgeven persoon of ploeg kon niet worden gevonden");
            } catch (SQLException sqlEx) {
                throw new DBException("SQL-exception in toevoegenTrainerPloeg(int persoonid,int ploegid) - statement" + sqlEx);
            }
        } catch (SQLException sqlEx) {
            throw new DBException(
                    "SQL-exception in toevoegenTrainerPloeg(int persoonid,int ploegid) - connection" + sqlEx);
        }
    }

    public void toevoegenTrainerPloeg(Persoon persoon, Ploeg ploeg) throws DBException, ApplicationException {
        if (persoon != null && ploeg != null) {
            toevoegenTrainerPloeg(persoon.getNaam(), persoon.getVoornaam(), ploeg.getNaam());
        }
    }

    public void toevoegenTrainerPloeg(String naam, String voornaam, String ploegnaam) throws DBException, ApplicationException {
        if (naam != null && voornaam != null) {
            Persoon persoon = persoonDB.zoekPersoon(naam, voornaam);
            Ploeg ploeg = zoekPloeg(ploegnaam);
            toevoegenTrainerPloeg(persoon.getId(), ploeg.getId());
        }
        
    }

    public void verwijderTrainerPloeg(int ploegid) throws DBException, ApplicationException {

        // connectie tot stand brengen (en automatisch sluiten)
        try (Connection conn = ConnectionManager.getConnection();) {
            // preparedStatement opstellen (en automtisch sluiten)
            try (PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE ploeg set trainer_id=null where id=?");) {
                stmt.setInt(1, ploegid);

                stmt.execute();

            } catch (NullPointerException e) {
                throw new ApplicationException("De opgegeven ploeg werd niet gevonden " + e.getMessage());
            } catch (SQLException sqlEx) {
                throw new DBException("SQL-exception in verwijderTrainerPloeg((int ploegid)) - statement" + sqlEx);
            }
        } catch (SQLException sqlEx) {
            throw new DBException(
                    "SQL-exception in verwijderTrainerPloeg(int ploegid) - connection" + sqlEx);
        }
    }

    public void verwijderTrainerPloeg(Ploeg p) throws DBException, ApplicationException {
        verwijderTrainerPloeg(p.getNaam());
    }

    public void verwijderTrainerPloeg(String ploegnaam) throws DBException, ApplicationException {

        // connectie tot stand brengen (en automatisch sluiten)
        try (Connection conn = ConnectionManager.getConnection();) {
            // preparedStatement opstellen (en automtisch sluiten)
            try (PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE ploeg set trainer_id=null where naam=?");) {
                stmt.setString(1, ploegnaam);

                stmt.execute();

            } catch (NullPointerException e) {
                throw new ApplicationException("De opgegeven ploeg werd niet gevonden " + e.getMessage());
            } catch (SQLException sqlEx) {
                throw new DBException("SQL-exception in verwijderTrainerPloeg((int ploegid)) - statement" + sqlEx);
            }
        } catch (SQLException sqlEx) {
            throw new DBException(
                    "SQL-exception in verwijderTrainerPloeg(int ploegid) - connection" + sqlEx);
        }
    }

    public ArrayList<Persoon> zoekSpelersPloeg(String ploegnaam) throws DBException, ApplicationException {

        ArrayList<Persoon> kl = new ArrayList<>();
        // connectie tot stand brengen (en automatisch sluiten)
        try (Connection conn = ConnectionManager.getConnection();) {
            // preparedStatement opstellen (en automtisch sluiten)
            try (PreparedStatement stmt = conn.prepareStatement("select id, naam, voornaam, geboortedatum, isTrainer from persoon where isTrainer=\"false\" and ploeg_id in(select id from ploeg where naam=?) order by naam,voornaam");) {
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
                            "SQL-exception in zoekSpelersPloeg(String ploegnaam) - resultset" + sqlEx);
                }
            } catch (SQLException sqlEx) {
                throw new DBException("SQL-exception in zoekSpelersPloeg(String ploegnaam) - statement" + sqlEx);
            }
        } catch (SQLException sqlEx) {
            throw new DBException(
                    "SQL-exception in zoekSpelersPloeg(String ploegnaam) - connection" + sqlEx);
        }
    }

    public ArrayList<Persoon> zoekSpelersPloeg(int id) throws DBException, ApplicationException {

        ArrayList<Persoon> kl = new ArrayList<>();
        // connectie tot stand brengen (en automatisch sluiten)
        try (Connection conn = ConnectionManager.getConnection();) {
            // preparedStatement opstellen (en automtisch sluiten)
            try (PreparedStatement stmt = conn.prepareStatement("select id, naam, voornaam, geboortedatum, isTrainer from persoon where isTrainer=\"false\" and ploeg_id in(select id from ploeg where id=?) order by naam,voornaam");) {
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

    public ArrayList<Persoon> zoekSpelersPloeg(Ploeg p) throws DBException, ApplicationException {

        ArrayList<Persoon> kl = zoekSpelersPloeg(p.getNaam());
        return kl;

    }

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
                        k.setTrainer(r.getInt("trainer_id"));
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
