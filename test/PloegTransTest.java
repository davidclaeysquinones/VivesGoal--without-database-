/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import databag.Persoon;
import datatype.Categorie;
import databag.Ploeg;
import database.PersoonDB;
import database.PloegDB;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import transactie.PloegTrans;


/**
 *
 * @author david
 */
public class PloegTransTest {
    PloegTrans transactie;
    public PloegTransTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
      transactie=new PloegTrans();
    }
    
    @After
    public void tearDown() {
        PloegDB ploeg = new PloegDB();
        try{
        ploeg.verwijderAllePloegen();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        PersoonDB persoon = new PersoonDB();
        ArrayList<Persoon> personen=new ArrayList<>();
        try{
            personen=persoon.zoekAllePersonen();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        for (Persoon personen1 : personen) {
            try {
                persoon.verwijderPersoon(personen1);
            }catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void ploegToevoegen() throws Exception
    {
        Ploeg p =new Ploeg();
        p.setNaam("maandag");
        p.setCategorie(Categorie.U6);
        transactie.ploegToevoegen(p);
        
    }
    
    @Test
    public void trainerVerwijderen() throws Exception
    {
        PersoonDB personen = new PersoonDB();
        Persoon a = new Persoon();
        a.setNaam("Claeys");
        a.setVoornaam("David");
        a.setGeboortedatum(1995, 4, 13);
        personen.toevoegenPersoon(a);
        Ploeg p = new Ploeg();
        p.setNaam("dinsdag");
        p.setCategorie(Categorie.U8);
        int persoonid=personen.zoekPersoon(a.getNaam(), a.getVoornaam()).getId();
        p.setTrainer(persoonid);
        int ploegid =transactie.ploegToevoegen(p);
        
        transactie.trainerVerwijderenVanPloeg(ploegid);
    }
    
    @Test
    public void trainerToevoegen() throws Exception
    {
        PersoonDB personen = new PersoonDB();
        Persoon a = new Persoon();
        a.setNaam("Claeys");
        a.setVoornaam("Cristina");
        a.setGeboortedatum(1999, 10,1);
        personen.toevoegenPersoon(a);
        Ploeg p = new Ploeg();
        p.setNaam("woensdag");
        p.setCategorie(Categorie.U8);
        int persoonid=personen.zoekPersoon(a.getNaam(), a.getVoornaam()).getId();
        p.setTrainer(persoonid);
        int ploegid =transactie.ploegToevoegen(p); 
        transactie.trainerKoppelenAanPloeg(persoonid, ploegid);
    }
    
    /**
     * Voegt twee ploegen toe met dezelfde naam
     * Deze methode moet normaal gezien een exception opgooien
     * @throws Exception 
     */
    @Test
    public void DubbelePloeg() throws Exception
    {
        Ploeg a=new Ploeg();
        a.setNaam("woensdag");
        a.setCategorie(Categorie.U6);
        Ploeg b=new Ploeg();
        b.setNaam("woensdag");
        b.setCategorie(Categorie.U6);
        transactie.ploegToevoegen(a);
        transactie.ploegToevoegen(b);
    }
    
}
