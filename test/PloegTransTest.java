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
import exception.DBException;
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
    public static void tearDownClass() throws DBException {

    }

    @Before
    public void setUp() {
        transactie = new PloegTrans();
    }

    @After
    public void tearDown() {
        PloegDB ploeg = new PloegDB();
        try {
            ploeg.verwijderAllePloegen();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        PersoonDB persoon = new PersoonDB();

        try {
            persoon.verwijderAllePersonen();
        } catch (DBException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void ploegToevoegen() throws Exception {
        Ploeg p = new Ploeg();
        p.setNaam("maandag");
        p.setCategorie(Categorie.U6);
        transactie.ploegToevoegen(p);

    }

    @Test
    public void trainerVerwijderen() throws Exception {
        PersoonDB personen = new PersoonDB();
        Persoon a = new Persoon();
        a.setNaam("Claeys");
        a.setVoornaam("David");
        a.setGeboortedatum(1995, 4, 13);
        personen.toevoegenPersoon(a);
        Ploeg p = new Ploeg();
        p.setNaam("dinsdag");
        p.setCategorie(Categorie.U8);
        int persoonid = personen.zoekPersoon(a.getNaam(), a.getVoornaam()).getId();
        p.setTrainer(persoonid);
        int ploegid = transactie.ploegToevoegen(p);

        transactie.trainerVerwijderenVanPloeg(ploegid);
    }

    @Test
    public void trainerToevoegen() throws Exception {
        PersoonDB personen = new PersoonDB();
        Persoon a = new Persoon();
        a.setNaam("Claeys");
        a.setVoornaam("Cristina");
        a.setGeboortedatum(1999, 10, 1);
        personen.toevoegenPersoon(a);
        Ploeg p = new Ploeg();
        p.setNaam("woensdag");
        p.setCategorie(Categorie.U8);
        int persoonid = personen.zoekPersoon(a.getNaam(), a.getVoornaam()).getId();
        p.setTrainer(persoonid);
        int ploegid = transactie.ploegToevoegen(p);
        transactie.trainerKoppelenAanPloeg(persoonid, ploegid);
    }

    @Test
    public void verwijderPloeg() throws Exception {

        Ploeg a = new Ploeg();
        a.setNaam("U11a");
        a.setCategorie(Categorie.U11);

        Ploeg b = new Ploeg();
        b.setNaam("U11b");
        b.setCategorie(Categorie.U11);

        Ploeg c = new Ploeg();
        c.setNaam("U11c");
        c.setCategorie(Categorie.U11);

        transactie.ploegToevoegen(a);
        transactie.ploegToevoegen(b);
        transactie.ploegToevoegen(c);

        transactie.ploegVerwijderen(b);

    }

}
