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
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author david
 */
public class PloegTrans implements PloegTransInterface {

    private PloegDB database = new PloegDB();

    @Override
    public Integer ploegToevoegen(Ploeg p) throws Exception {
//        controleren of het opgegeven ploegobject een categorie heeft
        if (p.getCategorie() == null) {
            throw new ApplicationException("Elke ploeg moet een categorie hebben");
        } else {
            String ploegnaam = genereerPloegNaam(p);

            
//            controleren of het opgegeven object een trainer heeft
            if (p.getTrainer() != null) {
                PersoonDB persoonDB = new PersoonDB();
//                controleren of de opgegeven trainer in de database staat
                if (persoonDB.bestaatPersoon(p.getTrainer()) == false) {
                    throw new ApplicationException("De trainer bestaat niet");

                } else {
                    p.setNaam(ploegnaam);
                    database.toevoegenPloeg(p);

//                  retourneren van het id van de toegevoegde ploeg in de database
                    return database.zoekPloeg(p).getId();
                }
            } else {

                p.setNaam(ploegnaam);
                database.toevoegenPloeg(p);

                //             retourneren van het id van de toegevoegde ploeg in de database
                return database.zoekPloeg(p).getId();

            }

        }

    }

    @Override
    public void trainerVerwijderenVanPloeg(int ploegId) throws Exception {
//      het ploegobject ophalen met het opgegeven id
        Ploeg a =database.zoekPloeg(ploegId);
       
//      controleren of het opgegeven id in de database staat
        if(a!=null)
        {
            database.verwijderTrainerPloeg(ploegId);
        }
        else
        {
            throw new ApplicationException("de opgegeven ploeg bestaat niet");
        }

    }

    @Override
    public void trainerKoppelenAanPloeg(int trainerId, int ploegId) throws Exception {
        PersoonDB a = new PersoonDB();
        
//      persoonobject met het opgegeven trainerid
        Persoon p = a.zoekPersoon(trainerId);
        
//      controleren of het opgegeven trainerid bestaat en of die persoon een trainer is
        if (p != null && p.getTrainer() == true) {
            database.toevoegenTrainerPloeg(trainerId, ploegId);
        } else {
            throw new ApplicationException("De opgegeven persoon bestaat niet of is geen trainer");
        }

    }

    /**
     * 
     * @param p de ploeg die verwijderd moet worden ( naam en categorie mogen niet null zijn)
     * @throws Exception 
     */
    public void ploegVerwijderen(Ploeg p) throws Exception {
//      ArrayList met alle ploegen binnen dezelfde categorie
        ArrayList<Ploeg> ploegen;
        ploegen = database.zoekPloegenCategorie(p.getCategorie());

//      HashMap die later de spelers per ploeg zal bijhouden
        HashMap<Ploeg, ArrayList<Persoon>> spelerslijst = new HashMap<>();

//      Toevoegen van spelers aan HashMap
        for (int i = 0; i < ploegen.size(); i++) {
            spelerslijst.put(ploegen.get(i), database.zoekSpelersPloeg(ploegen.get(i)));
        }

//      Alle ploegen binnen dezelfde categorie als ploeg p verwijderen van de database
        for (int i = 0; i < ploegen.size(); i++) {
            database.verwijderPloeg(ploegen.get(i));
        }
//      Ploeg p van de ArrayList met ploegen verwijderen
        ploegen.remove(p);
//      De entry met de spelers van ploeg p van de HashMap verwijderen
        spelerslijst.keySet().remove(p);

//        Overlopen van alle overgebleven ploegen
        for (Ploeg current : ploegen) {
//          Spelers van ploeg current toewijzen aan ArrayList
            ArrayList<Persoon> a = spelerslijst.get(current);
//          Ploeg current verwijderen van de HashMap 
//          (dit is nodig omdat later de naam opnieuw wordt gegenereerd)
            spelerslijst.keySet().remove(current);
//          de nieuwe id van de huidige ploeg bijhouden
            Integer i = ploegToevoegen(current);
//          ploegobject opvragen aan de database met nieuwe gegenereerde gegevens
            Ploeg nieuw = database.zoekPloeg(i);
//          De lijst van spelers terug koppelen aan de ploeg
            spelerslijst.put(nieuw, a);
        }

//        Alle entries in HashMap overlopen
        for (Ploeg current : spelerslijst.keySet()) {
//            ArrayList met alle spelers van de ploeg
            ArrayList<Persoon> spelers = spelerslijst.get(current);

//          indien de ploeg geen spelers heeft zal ArrayList spelers null zijn
            if (spelers != null) {
//                overlopen van alle spelers van de ploeg
                for (Persoon speler : spelers) {
//                    speler koppelen aan ploeg
                    database.toevoegenSpelerPloeg(current.getId(), speler.getId());
                }
            }

        }

    }

    private String genereerPloegNaam(Ploeg p) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(p.getCategorie().getTekst());
//        aantal ploegen binnen de categorie
        int aantal = database.zoekPloegenCategorie(p.getCategorie()).size();
        
//      als er meer dan 26 ploegen zijn binnen de categorie zal de naam niet meer juist gegenereerd worden
//      door de complexiteit van deze methode te laten toenemen kan je dit probleem oplossen
//      aangezien het onwerkelijk is dat dit probleem zich voordoet in de realiteit
//      hebben we gekozen om een foutmelding te genereren wanneer dit aantal overschreden wordt
        if(aantal>26)
        {
            throw new ApplicationException("Er kunnen geen ploegen meer toegevoegd worden met deze categorie");
        }
//        letter aan StringBuilder afhankelijk van het aantal ploegen binnen de categorie
        sb.append(Character.toString((char) ((char) aantal + 97)));
        return sb.toString();

    }

    

}
