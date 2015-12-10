/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactie;

import databag.Persoon;
import databag.Ploeg;
import database.PersoonDB;
import database.PloegDB;
import exception.ApplicationException;
import exception.DBException;
import java.util.ArrayList;

/**
 *
 * @author david
 */
public class PersoonTrans implements PersoonTransInterface {

    private PersoonDB database = new PersoonDB();
    private PloegDB databasePloeg = new PloegDB();
    
    @Override
    public Integer persoonToevoegen(Persoon p) throws DBException, ApplicationException {
        //kijken of de persoon al bestaat.
        if(database.zoekPersoon(p.getId())!=null)
            {
                throw new ApplicationException("Deze persoon bestaat al");
            }
        else
        {
            //controleren of alle velden zijn ingevuld.
            checkAlleVeldenIngevuld(p);
            //persoon toevoegen aan database.
            database.toevoegenPersoon(p);
            return database.zoekPersoon(p).getId();
        }
    }

    @Override
    public void persoonWijzigen(Persoon p) throws DBException, ApplicationException {
      //controleer of alle velden zijn ingevuld
      checkAlleVeldenIngevuld(p);

      // bestaat er al een persoon met deze nieuwe gegevens?
      if (database.zoekPersoon(p) != null) {
         throw new ApplicationException("Persoon bestaat al");
      }

      // bestaat de persoon die gewijzigd moet worden
      Persoon m = database.zoekPersoon(p.getId());
      if (m == null) {
         throw new ApplicationException("Persoon bestaat niet");
      }
      database.wijzigenPersoon(p);
    }

    @Override
    public void spelerVerwijderenVanPloeg(int spelerId) throws DBException, ApplicationException {
        //kijken of de persoon bestaat.
        if(database.zoekPersoon(spelerId)==null) {
            throw new ApplicationException("De opgegeven persoon bestaat niet.");
        }
        else {
            //kijken of speler gekoppeld is met een ploeg.
            if(database.zoekPersoon(spelerId).getPloegid()!=null) {
                throw new ApplicationException("De opgegeven persoon zit niet in een ploeg.");
            }
            else {
                //speler ontkoppelen van de ploeg.
                databasePloeg.verwijderSpelerPloeg(spelerId);
            }
        }  
    }

    @Override
    public void spelerToevoegenAanPloeg(int spelerId, int ploegId) throws DBException, ApplicationException {
        //de persoon moet bestaan.
        if(database.zoekPersoon(spelerId)==null) {
            throw new ApplicationException ("De opgegeven persoon bestaat niet.");
        }
        else {
            //de ploeg moet bestaan.
            if(databasePloeg.zoekPloeg(ploegId) == null) {
                throw new ApplicationException ("De opgegeven ploeg bestaat niet.");
            }
            else {
                //de persoon mag niet in een andere ploeg zitten.
                if(database.zoekPersoon(spelerId).getPloegid() == null) {
                    throw new ApplicationException ("De speler zit reeds in een andere ploeg.");
                }
                else {
                    //de persoon mag geen trainer zijn.
                    if(database.zoekPersoon(spelerId).getTrainer() == true) {
                        throw new ApplicationException ("De opgegeven persoon is reeds een trainer en kan dus niet toegevoegd worden.");
                    }
                    else {
                        //speler toevoegen.
                        databasePloeg.toevoegenSpelerPloeg(ploegId, ploegId);
                    }     
                }
            }
        }   
    }

    @Override
    public void persoonVerwijderen(int persoonId) throws DBException, ApplicationException {
        //de persoon moet reeds in de databse zitten om te kunnen verwijderen. (via ID)
        //(spelerVerwijderenVanPloeg uitvoeren?).
        
        if(database.zoekPersoon(persoonId)==null)
                {
                    throw new ApplicationException("Deze persoon bestaat niet");
                }
        else
        {
            
            database.verwijderPersoon(persoonId);
        }
    }

    @Override
    public void spelerWordtTrainer(int spelerId) throws DBException, ApplicationException {
        //speler moet bestaan
        if(database.zoekPersoon(spelerId)== null) {
            throw new ApplicationException("Deze persoon bestaat niet");
        }
        else {
            //speler ontkoppelen als hij in een ploeg zit. (niet noodzakkelijk)
            if(database.zoekPersoon(spelerId).getPloegid()!=null) {
                try {
                    spelerVerwijderenVanPloeg(spelerId);
                } catch (Exception ex) {
                    throw new ApplicationException("Er is iets misgelopen bij het ontkoppelen van de speler. error:" + ex.getMessage());
                }
            }
            //speler trainer maken
            database.zoekPersoon(spelerId).setTrainer(true);
        }   
    }

    @Override
    public void trainerWordtSpeler(int trainerId) throws DBException, ApplicationException {
        
        //kijken of de persoon wel een trainer is
        if(database.zoekPersoon(trainerId).getTrainer() == false) {
            throw new ApplicationException("Deze persoon is geen trainer");
        }
        else {
            //trainer ontkoppelen van alle ploegen.
            //alle ploegen in arraylist stoppen.
            ArrayList<Ploeg> kl = databasePloeg.zoekPloegenTrainer(trainerId);
            
            for (Ploeg kl1 : kl) {
                //voor elke ploeg de trainer op 'null' zetten in de database.
                databasePloeg.verwijderTrainerPloeg(kl1);
            }  
        }
    }

    @Override
    public void checkAlleVeldenIngevuld(Persoon p) throws DBException, ApplicationException {
        if (p.getId()!=null) {
         throw new ApplicationException("Naam niet ingevuld");
      }
  
    }

    
}
