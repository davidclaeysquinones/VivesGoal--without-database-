/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactie;

import databag.Ploeg;
import database.PloegDB;
import exception.ApplicationException;


/**
 *
 * @author david
 */
public class PloegTrans implements PloegTransInterface{

   private PloegDB database = new PloegDB();
    @Override
    public Integer ploegToevoegen(Ploeg p) throws Exception {
       
           if(database.zoekPloeg(p.getNaam())!=null)
           {
               throw new ApplicationException("Deze ploeg bestaat al");
           }
            else
           {
               if(p.getCategorie()==null)
            {
                throw new ApplicationException("Elke ploeg moet een categorie hebben");
            }   
            else
            {
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
    
   
}
