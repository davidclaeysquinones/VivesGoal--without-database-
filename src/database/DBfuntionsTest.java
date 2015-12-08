/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import exception.ApplicationException;
import exception.DBException;
import java.util.ArrayList;
import datatype.Categorie;
import databag.Persoon;
import databag.Ploeg;

/**
 Main class to test the functions of the database
 */
public class DBfuntionsTest {
   
    public static void main(String args[]) {
        
       
   
        Persoon een=new Persoon();
        een.setVoornaam("David");
        een.setNaam("Claeys");
        try {
            een.setGeboortedatum(1995,4,13);
        } catch (ApplicationException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(een.getGeboortedatum());
        een.setTrainer(false);
      
        
        Persoon twee=new Persoon();
        twee.setVoornaam("Cristina");
        twee.setNaam("Claeys");
        try {
            twee.setGeboortedatum(1999,10,1);
        }  catch (ApplicationException ex) {
            System.out.println(ex.getMessage());
        }
        twee.setTrainer(false);
        
        Persoon drie=new Persoon();
        drie.setVoornaam("Papi");
        drie.setNaam("Chulo");
        try {
            drie.setGeboortedatum(1989,8,1);
        } catch (ApplicationException ex) {
            System.out.println(ex.getMessage());
        }
        drie.setTrainer(true);
        
        Persoon vier=new Persoon();
        vier.setVoornaam("Mami");
        vier.setNaam("Chula");
        try {
            vier.setGeboortedatum(1979,6,2);
        } catch (ApplicationException ex) {
            System.out.println(ex.getMessage());
        }
        vier.setTrainer(true);
        
        ArrayList speler=new ArrayList();
        ArrayList trainer=new ArrayList();
        PloegDB ploegDB = new PloegDB();
        PersoonDB persoonDB = new PersoonDB();
        
        try{
            persoonDB.toevoegenPersoon(een);
            persoonDB.toevoegenPersoon(twee);
            persoonDB.toevoegenPersoon(drie);
            persoonDB.toevoegenPersoon(vier);
            
        }
        catch(DBException e)
        {
            System.out.println(e.getMessage());
        }
        
        try{
            speler=persoonDB.zoekAlleSpelers();
            trainer=persoonDB.zoekAlleTrainers();
            System.out.println("Spelers en trainers opslaan in apparte ArrayList"+"\n");
        }
        catch(DBException | ApplicationException  e1)
        {
            System.out.println("fout bij ophalen gegevens"+e1.getMessage());
        }
        
        System.out.println("Spelers");
        for (int i=0;i<speler.size();i++) {
            System.out.println(speler.get(i).toString());
        }
        System.out.println("\n"+"Trainers");
        for(int i=0;i<trainer.size();i++)
        {
            System.out.println(trainer.get(i).toString());
        }
        
        
        Ploeg ploeg=new Ploeg();
        ploeg.setNaam("los papis");
        ploeg.setCategorie(Categorie.U6);
        try{
            Persoon p = persoonDB.zoekPersoon(drie.getNaam(),drie.getVoornaam());
            ploeg.setTrainer(p.getId());
            System.out.println("\n"+"instellen trainer"+"\n");
                  
        }
        catch ( DBException|ApplicationException e)
        {
            System.out.println("fout bij opzoeken trainer "+e.getMessage());
        }
        
        
        
        
        try{
            ploegDB.toevoegenPloeg(ploeg);
            System.out.println("ploeg toegevoegd"+"\n");  
        }
        catch ( DBException e)
        {
            System.out.println("fout bij toevoegen ploeg "+e.getMessage());
        }
        
        try{
           ploegDB.toevoegenSpelerPloeg(ploegDB.zoekPloeg(ploeg.getNaam()),persoonDB.zoekPersoon(een.getNaam(), een.getVoornaam()));
            Ploeg p=ploegDB.zoekPloeg(ploeg.getNaam());
            ploegDB.toevoegenSpelerPloeg(p,persoonDB.zoekPersoon(twee.getNaam(),twee.getVoornaam()) );
            System.out.println("toevoegen spelers aan ploeg"+"\n");
        }
        catch ( DBException|ApplicationException e)
        {
            System.out.println("fout bij toevoegen speler "+e.getMessage());
        }
        
        try{
            ploegDB.toevoegenTrainerPloeg(drie.getNaam(),drie.getVoornaam(),ploeg.getNaam());
            System.out.println("toevoegen trainer aan ploeg"+"\n");
        }
        catch ( DBException|ApplicationException e)
        {
            System.out.println("fout bij toevoegen trainer "+e.getMessage());
        }
     
        try{
            ploegDB.verwijderSpelerPloeg(een.getNaam(),een.getVoornaam());
            System.out.println("ontkoppelen speler van ploeg"+"\n");
        }
        catch ( DBException|ApplicationException e)
        {
            System.out.println("fout bij ontkoppelen speler "+e.getMessage());
        }
        
        try{
            ploegDB.verwijderTrainerPloeg(ploeg.getNaam());
            System.out.println("ontkoppelen trainer van ploeg"+"\n");
        }
        catch ( DBException|ApplicationException e)
        {
            System.out.println("fout bij ontkoppelen trainer "+e.getMessage());
        }
        
          
       ArrayList ploegen = new ArrayList(); 
        try {
            
            ploegen = ploegDB.zoekAllePloegen();
            System.out.println("Alle ploegen zoeken"+"\n");
        }
        catch(DBException|ApplicationException e)
        {
            System.out.println("fout bij opzoeken van alle ploegen"+e.getMessage());
        } 
        
        try {
            Ploeg p=ploegDB.zoekPloeg(ploeg.getNaam());
            System.out.println("opzoeken ploeg : "+p+"\n");
        }
        catch(DBException|ApplicationException e)
        {
            System.out.println("fout bij opzoeken ploeg "+e.getMessage());
        }
        
        for(int i=0;i<ploegen.size();i++)
        {
            System.out.println(ploegen.get(i));
        }
        
        try {
            Persoon a = new Persoon();
            a.setNaam("Mano");
            a.setVoornaam("Lito");
            persoonDB.wijzigenPersoon(een.getNaam(), een.getVoornaam(), a);
            System.out.println("Wijzigen persoon"+"\n");
            
          
            
        }
        catch(DBException|ApplicationException e)
        {
            System.out.println("fout bij wijzigen persoon "+e.getMessage());
        }
        
        for(int i=0;i<speler.size();i++)
        {
            Persoon a = (Persoon) speler.get(i);
            try{
            persoonDB.verwijderPersoon(a.getId());
            if(i==0)
            {
                System.out.println("verwijderen spelers"+"\n");
            }
        }
        catch(DBException|ApplicationException e1   )
        {
            System.out.println("fout bij verwijderen speler"+e1.getMessage());
        }  
        }
        
        
        
        
        for(int i=0;i<trainer.size();i++)
        {
            Persoon a = (Persoon) trainer.get(i);
            try{
            persoonDB.verwijderPersoon(a.getNaam(),a.getVoornaam());
            if(i==0)
            {
                System.out.println("verwijderen trainers"+"\n");
            }
            
        }
        catch(DBException|ApplicationException   e1)
        {
            System.out.println("fout bij verwijderen trainer"+e1.getMessage());
        }
        }
       
      
        
        
        try{
            ploegDB.verwijderAllePloegen();
            System.out.println("Alle ploegen verwijderen");
        }
        catch ( DBException e)
        {
            System.out.println("fout bij verwijderen trainer"+e.getMessage());
        }
        
     
    }
    

}
