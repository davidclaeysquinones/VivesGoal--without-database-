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
                System.out.println(p.getTrainer() != null);
                System.out.println(ploegnaam);
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
        Persoon p =a.zoekPersoon(trainerId);
        if(p!=null && p.getTrainer()==true)
        {
            database.toevoegenTrainerPloeg(trainerId, ploegId);
        }
        else
        {
            throw new ApplicationException("De opgegeven persoon bestaat niet of is geen trainer");
        }
        
    }

    public void ploegVerwijderen(Ploeg p) throws Exception {

        ArrayList<Ploeg> ploegen = database.zoekPloegenCategorie(p.getCategorie());

        HashMap<Ploeg, ArrayList<Persoon>> spelerslijst = new HashMap<>();

        for (int i = 0; i < ploegen.size(); i++) {
            spelerslijst.put(ploegen.get(i), database.zoekSpelersPloeg(ploegen.get(i)));
        }


        for (int i = 0; i < ploegen.size(); i++) {
            database.verwijderPloeg(ploegen.get(i));
        }
        ploegen.remove(p);
        spelerslijst.keySet().remove(p);

        for (Ploeg current : ploegen) {
            ploegToevoegen(current);
        }

        for (Ploeg current : spelerslijst.keySet()) {
            ArrayList<Persoon> spelers = spelerslijst.get(current);
            if (spelers != null) {
                for (int j = 0; j < spelers.size(); j++) {

                    database.toevoegenSpelerPloeg(current, spelers.get(j));
                }
            }

        }



    }

    public String genereerPloegNaam(Ploeg p) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(p.getCategorie().getTekst());
        int aantal = database.zoekPloegenCategorie(p.getCategorie()).size();
        sb.append(Character.toString((char) ((char) aantal + 97)));
        return sb.toString();

    }
}
