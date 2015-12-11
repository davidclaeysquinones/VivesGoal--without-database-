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
                if (persoonDB.zoekPersoon(p.getTrainer()) != null) {

                    p.setNaam(ploegnaam);
                    database.toevoegenPloeg(p);
                   
                    return database.zoekPloeg(p.getNaam()).getId();
                } else {
                    throw new ApplicationException("De trainer bestaat niet");
                }
            } else {
                System.out.println(p.getTrainer() != null); 
                System.out.println(ploegnaam);
                p.setNaam(ploegnaam);
                database.toevoegenPloeg(p);
                
                return database.zoekPloeg(p.getNaam()).getId();
                
            }

        }
    }

    @Override
    public void trainerVerwijderenVanPloeg(int ploegId) throws Exception {
        database.verwijderTrainerPloeg(ploegId);

    }

    @Override
    public void trainerKoppelenAanPloeg(int trainerId, int ploegId) throws Exception {
        database.toevoegenTrainerPloeg(trainerId, ploegId);
    }

    public boolean ploegVerwijderen(Ploeg p) throws Exception {

        ArrayList<Ploeg> ploegen = database.zoekPloegenCategorie(p.getCategorie());

        HashMap<Ploeg, ArrayList<Persoon>> a = new HashMap<>();
        HashMap<Ploeg, Persoon> b = new HashMap<>();

        for (int i = 0; i < ploegen.size(); i++) {
            a.put(ploegen.get(i), database.zoekSpelersPloeg(ploegen.get(i)));
        }

        for (int i = 0; i < ploegen.size(); i++) {
            b.put(ploegen.get(i), database.getTrainer(ploegen.get(i)));
        }

        for (int i = 0; i < ploegen.size(); i++) {
            database.verwijderPloeg(ploegen.get(i));
        }
        ploegen.remove(p);
        a.keySet().remove(p);
        b.keySet().remove(p);
        System.out.println(ploegen.size());

       
        for (Ploeg current : ploegen) {
            Integer id = ploegToevoegen(current);
            System.out.println(id+"oki");
            System.out.println(database.zoekPloeg(id));
        }
        
        for (Ploeg current : a.keySet()) {
            ArrayList<Persoon> spelers = a.get(current);
            for (int j = 0; j < spelers.size(); j++) {
                database.toevoegenSpelerPloeg(current, spelers.get(j));
            }
        }

        for (Ploeg current : b.keySet()) {
            database.toevoegenTrainerPloeg(b.get(current), current);
        }

        return true;

    }

    private String genereerPloegNaam(Ploeg p) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(p.getCategorie().getTekst());
        int aantal = database.zoekPloegenCategorie(p.getCategorie()).size() + 97;
        sb.append(Character.toString((char) aantal));
        return sb.toString();

    }

}
