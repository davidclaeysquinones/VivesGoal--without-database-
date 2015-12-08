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

public class DbTest {
    private PloegDB ploegDB;
    private PersoonDB persoonDB;
    public DbTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        ploegDB=new PloegDB();
        persoonDB=new PersoonDB();
    }
    
    @After
    public void tearDown() throws DBException, ApplicationException {
        
        ploegDB.verwijderAllePloegen();
        ArrayList spelers = persoonDB.zoekAlleSpelers();
        ArrayList trainers= persoonDB.zoekAlleTrainers();
        
        for(int i = 0;i<spelers.size();i++)
        {
            persoonDB.verwijderPersoon((Persoon) spelers.get(i));
        }
        
        for(int i = 0;i<trainers.size();i++)
        {
            persoonDB.verwijderPersoon((Persoon) trainers.get(i));
        }
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
        
        ArrayList speler=new ArrayList();
        ArrayList trainer=new ArrayList();
        
        speler=persoonDB.zoekAlleSpelers();
        trainer=persoonDB.zoekAlleTrainers();
       
      
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
    public void toevoegenPloegMetTrainer() throws Exception
    {
        
        Persoon drie=new Persoon();
        drie.setVoornaam("Marnisito");
        drie.setNaam("Chulo");
        
        drie.setGeboortedatum(1989,8,1);
       
        drie.setTrainer(true);
        
        persoonDB.toevoegenPersoon(drie);
        
        Ploeg ploeg=new Ploeg();
        ploeg.setNaam("los papis");
        ploeg.setCategorie(Categorie.U6);
        
        Persoon p = persoonDB.zoekPersoon(drie.getNaam(),drie.getVoornaam());
        ploeg.setTrainer(p.getId());
         
         ploegDB.toevoegenPloeg(ploeg); 
       
       
    }
    
    @Test
    public void toevoegenPloeg() throws Exception
    {
        Ploeg ploeg=new Ploeg();
        ploeg.setNaam("los papasitos");
        ploeg.setCategorie(Categorie.U6);
       
        ploegDB.toevoegenPloeg(ploeg);
        
        
    }
    
    @Test
    public void koppelenTrainerAanPloeg() throws Exception
    {
        Ploeg ploeg = new Ploeg();
        ploeg.setCategorie(Categorie.U7);
        ploeg.setNaam("los chungitos");
        ploegDB.toevoegenPloeg(ploeg);
        Persoon drie=new Persoon();
        drie.setVoornaam("Rubensito");
        drie.setNaam("Chulito");
        
        drie.setGeboortedatum(1998,8,1);
        
        drie.setTrainer(true);
        
        persoonDB.toevoegenPersoon(drie);
    
        
        ploeg = ploegDB.zoekPloeg("los chungitos");
        ploegDB.toevoegenTrainerPloeg(persoonDB.zoekPersoon("Chulito", "Rubensito"), ploeg);
      
       
    }
    
    @Test
    public void koppelenSpeler() throws Exception
    {
        Persoon een=new Persoon();
        een.setVoornaam("Davisito");
        een.setNaam("Claesito");
        een.setGeboortedatum(1960, 4, 12);
       
   
        Persoon twee=new Persoon();
        twee.setVoornaam("Cristinita");
        twee.setNaam("Claesita");
        twee.setGeboortedatum(1966, 5, 12);
        
        persoonDB.toevoegenPersoon(een);
        persoonDB.toevoegenPersoon(twee);
     
        
        Ploeg ploeg=new Ploeg();
        ploeg.setNaam("markisito");
        ploeg.setCategorie(Categorie.U11);
        
        ploegDB.toevoegenPloeg(ploeg);
       
        ploegDB.toevoegenSpelerPloeg(ploegDB.zoekPloeg(ploeg.getNaam()),persoonDB.zoekPersoon(een.getNaam(), een.getVoornaam()));
       
        ploegDB.toevoegenSpelerPloeg(ploegDB.zoekPloeg(ploeg.getNaam()),persoonDB.zoekPersoon(twee.getNaam(),twee.getVoornaam()) );
        
        
    }
    
    @Test 
    public void ontkoppelenSpeler() throws Exception
    {
        Persoon een=new Persoon();
        een.setVoornaam("stevisito");
        een.setNaam("rikisito");      
        een.setGeboortedatum(1995,4,13);
        een.setTrainer(false);
        
        persoonDB.toevoegenPersoon(een);
       
       
        
        Ploeg ploeg = new Ploeg();
        ploeg.setNaam("apllesito");
        ploeg.setCategorie(Categorie.U8);
        
        ploegDB.toevoegenPloeg(ploeg);
        
//        
        ploegDB.toevoegenSpelerPloeg(ploegDB.zoekPloeg(ploeg.getNaam()), persoonDB.zoekPersoon(een.getNaam(),een.getVoornaam()));
       
        ploegDB.verwijderSpelerPloeg(een.getNaam(),een.getVoornaam());
        
        
    }
    
    @Test
    public void verwijderAllePersonen() throws Exception
    {
        ArrayList spelers = persoonDB.zoekAlleSpelers();
        ArrayList trainers= persoonDB.zoekAlleTrainers();
        
        for(int i = 0;i<spelers.size();i++)
        {
            persoonDB.verwijderPersoon((Persoon) spelers.get(i));
        }
        
        for(int i = 0;i<trainers.size();i++)
        {
            persoonDB.verwijderPersoon((Persoon) trainers.get(i));
        }
    }
    
    @Test 
    public void verwijderAllePloegen() throws Exception
    {
        ploegDB.verwijderAllePloegen();
    }
    
}
