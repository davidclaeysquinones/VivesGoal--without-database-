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
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author david
 */
public class PloegDBTest {
    private PloegDB ploegDB;
    private PersoonDB persoonDB;
    public PloegDBTest() {
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
    
    /**
     *
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        ploegDB.verwijderAllePloegen();
        persoonDB.verwijderAllePersonen();
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
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
        ploeg.setCategorie(Categorie.U6);
        ploeg.setNaam("las mamasitas");
        
        Persoon p = persoonDB.zoekPersoon(drie.getNaam(),drie.getVoornaam());
        ploeg.setTrainer(p.getId());
         
         ploegDB.toevoegenPloeg(ploeg); 
       
       
    }
    
    @Test
    public void toevoegenPloeg() throws Exception
    {
        Ploeg ploeg=new Ploeg();
        ploeg.setNaam("Yaah");
        ploeg.setCategorie(Categorie.U7);
       
        ploegDB.toevoegenPloeg(ploeg);
        
        
    }
    
    @Test
    public void koppelenTrainerAanPloeg() throws Exception
    {
        Ploeg ploeg = new Ploeg();
        ploeg.setCategorie(Categorie.U8);
        ploeg.setNaam("Apllesiitos");
        ploegDB.toevoegenPloeg(ploeg);
        Persoon drie=new Persoon();
        drie.setVoornaam("Rubensito");
        drie.setNaam("Chulito");
        
        drie.setGeboortedatum(1998,8,1);
        
        drie.setTrainer(true);
        
        persoonDB.toevoegenPersoon(drie);
    
        
        ploeg = ploegDB.zoekPloeg(ploeg.getNaam());
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
        ploeg.setCategorie(Categorie.U11);
        ploeg.setNaam("aburido");
        
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
        ploeg.setCategorie(Categorie.U10);
        ploeg.setNaam("vivesito");
        
        ploegDB.toevoegenPloeg(ploeg);
        
//        
        ploegDB.toevoegenSpelerPloeg(ploegDB.zoekPloeg(ploeg.getNaam()), persoonDB.zoekPersoon(een.getNaam(),een.getVoornaam()));
       
        ploegDB.verwijderSpelerPloeg(een.getNaam(),een.getVoornaam());
        
        
    }
    
    @Test 
    public void verwijderAllePloegen() throws Exception
     {
        ploegDB.verwijderAllePloegen();
    }
    
    @Test
    public void SpelersZoekenVanPLoeg()throws Exception
    {
        Persoon a =  new Persoon();
        a.setGeboortedatum(1980,3,21);
        a.setNaam("Visiao");
        a.setVoornaam("Messi");
        
        Persoon b = new Persoon();
        b.setGeboortedatum(1979, 6, 13);
        b.setNaam("Plumita");
        b.setVoornaam("Ronaldo");
        
        persoonDB.toevoegenPersoon(a);
        persoonDB.toevoegenPersoon(b);
        
        Ploeg p = new Ploeg();
        p.setNaam("Los farsantes");
        p.setCategorie(Categorie.U9);
        ploegDB.toevoegenPloeg(p);
        ploegDB.toevoegenSpelerPloeg(p, a);
        ploegDB.toevoegenSpelerPloeg(p, b);
        
        ArrayList array=ploegDB.zoekSpelersPloeg(p);
        
        for(int i=0;i<array.size();i++)
        {
            System.out.println(array.get(i));
        }
    }
    
}
