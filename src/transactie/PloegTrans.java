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

        ArrayList<Ploeg> ploegen;
        ploegen=database.zoekPloegenCategorie(p.getCategorie());
        

        HashMap<Ploeg, ArrayList<Persoon>> spelerslijst = new HashMap<>();
        
        HashMap<Ploeg,Integer> ploegids = new HashMap<>();
        
        

        for (int i = 0; i < ploegen.size(); i++) {
            System.out.println("naam"+ploegen.get(i).getNaam());
            spelerslijst.put(ploegen.get(i), database.zoekSpelersPloeg(ploegen.get(i)));
        }


        for (int i = 0; i < ploegen.size(); i++) {
            database.verwijderPloeg(ploegen.get(i));
        }
        ploegen.remove(p);
        spelerslijst.keySet().remove(p);
        

        for (Ploeg current : ploegen) {
            Integer i=ploegToevoegen(current);
            ploegids.put(current, i);
        }

        for (Ploeg current : spelerslijst.keySet()) {
            ArrayList<Persoon> spelers = spelerslijst.get(current);
            if (spelers != null) {
                for (int i = 0; i < spelers.size(); i++) {
                    System.out.println("takata"+database.zoekPloeg(ploegids.get(current)).getNaam());
                    Ploeg ploeg = database.zoekPloeg(ploegids.get(current));
                    database.toevoegenSpelerPloeg(ploeg.getId(), spelers.get(i).getId());
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

    
    private String genereerPloegNaamOneindig(Ploeg p) throws Exception {
        StringBuilder sb = new StringBuilder();
        
        int aantal = database.zoekPloegenCategorie(p.getCategorie()).size() + 1; //26 + 96 / 2+ 96
        int a = aantal;
        while (a > 26) {
            int r = a%27; //28%27 = 1
            r += 97; // 1+97=b
            sb.insert(0,Character.toString((char) r)); //b
            a = a / 27;  //28/27=1         
        }
        int b = a;
        b += 96; //97 = a
        sb.insert(0, Character.toString((char) b));
        sb.insert(0, p.getCategorie().getTekst());
        
        return sb.toString();
    }


}
