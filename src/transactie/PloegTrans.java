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

        if (p.getCategorie() == null) {
            throw new ApplicationException("Elke ploeg moet een categorie hebben");
        } else {
            String ploegnaam = genereerPloegNaam(p);

            if (p.getTrainer() != null) {
                PersoonDB persoonDB = new PersoonDB();
                if (persoonDB.bestaatPersoon(p.getTrainer()) == false) {
                    throw new ApplicationException("De trainer bestaat niet");

                } else {
                    p.setNaam(ploegnaam);
                    database.toevoegenPloeg(p);

                    return database.zoekPloeg(p).getId();
                }
            } else {

                p.setNaam(ploegnaam);
                database.toevoegenPloeg(p);

                return database.zoekPloeg(p).getId();

            }

        }

    }

    @Override
    public void trainerVerwijderenVanPloeg(int ploegId) throws Exception {
        database.verwijderTrainerPloeg(ploegId);

    }

    @Override
    public void trainerKoppelenAanPloeg(int trainerId, int ploegId) throws Exception {
        PersoonDB a = new PersoonDB();
        Persoon p = a.zoekPersoon(trainerId);
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
//        letter aan StringBuilder afhankelijk van het aantal ploegen binnen de categorie
        sb.append(Character.toString((char) ((char) aantal + 97)));
        return sb.toString();

    }

    private String genereerPloegNaamOneindig(Ploeg p) throws Exception {
        StringBuilder sb = new StringBuilder();

        int aantal = database.zoekPloegenCategorie(p.getCategorie()).size() + 1; //26 + 96 / 2+ 96
        int a = aantal;
        while (a > 26) {
            int r = a % 27; //28%27 = 1
            r += 97; // 1+97=b
            sb.insert(0, Character.toString((char) r)); //b
            a = a / 27;  //28/27=1         
        }
        int b = a;
        b += 96; //97 = a
        sb.insert(0, Character.toString((char) b));
        sb.insert(0, p.getCategorie().getTekst());

        return sb.toString();
    }

}
