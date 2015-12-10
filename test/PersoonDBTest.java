/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import databag.Persoon;
import databag.Ploeg;
import database.PersoonDB;
import database.PloegDB;
import datatype.Categorie;
import exception.ApplicationException;
import exception.DBException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 *
 * @author david
 */

public class PersoonDBTest {
    
    private PersoonDB persoonDB;
    public PersoonDBTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
        persoonDB=new PersoonDB();
    }
    
    @After
    public void tearDown() throws DBException, ApplicationException {
        
        
        persoonDB.verwijderAllePersonen();
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void ToevoegenPersonen() throws Exception
    {
        Persoon een=new Persoon();
        een.setVoornaam("David");
        een.setNaam("Claeys"); 
        een.setGeboortedatum(1995,4,13);      
        een.setTrainer(false);
      
        
        Persoon twee=new Persoon();
        twee.setVoornaam("Cristina");
        twee.setNaam("Claeys");    
        twee.setGeboortedatum(1999,10,1);
        twee.setTrainer(false);
        
        Persoon drie=new Persoon();
        drie.setVoornaam("Papi");
        drie.setNaam("Chulo");
        drie.setGeboortedatum(1989,8,1);
        drie.setTrainer(true);
        
        Persoon vier=new Persoon();
        vier.setVoornaam("Mami");
        vier.setNaam("Chula");
        vier.setGeboortedatum(1979,6,2);
        vier.setTrainer(true);
                
        persoonDB.toevoegenPersoon(een);
        persoonDB.toevoegenPersoon(twee);
        persoonDB.toevoegenPersoon(drie);
        persoonDB.toevoegenPersoon(vier);
           
    }
    
    @Test
    public void AllePersonen() throws Exception
    {
        
        ArrayList spelers=new ArrayList();
      
        
        spelers=persoonDB.zoekAllePersonen();
        
       
      
    }
    
    @Test
    public void afdrukkenSpelers() throws Exception
    {
         ArrayList speler=new ArrayList();
         speler=persoonDB.zoekAlleSpelers();
             
        System.out.println("Spelers");
        for (int i=0;i<speler.size();i++) {
            System.out.println(speler.get(i).toString());
        }

    }
    
    @Test
    public void afdrukkenTrainers() throws Exception
    {
        ArrayList trainer;
        
        trainer=persoonDB.zoekAlleTrainers();     
      
        System.out.println("\n"+"Trainers");
        for(int i=0;i<trainer.size();i++)
        {
            System.out.println(trainer.get(i).toString());
        }
    }
    

    @Test
    public void verwijderAllePersonen() throws Exception
    {
        persoonDB.verwijderAllePersonen();
    }
    
   @Test
   public void wijzigPersoon() throws Exception
   {
       Persoon a = new Persoon();
       a.setGeboortedatum(1989, 5, 5);
       a.setNaam("Gonzalez");
       a.setVoornaam("Laura");
       
       persoonDB.toevoegenPersoon(a);
       
       Persoon b =persoonDB.zoekPersoon(a.getNaam(), a.getVoornaam());
       b.setNaam("Guiñones");
       b.setVoornaam("Melani");
       persoonDB.wijzigenPersoon(b);
   }
    
}
