package appTest;

/**
 *
 * @author David
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
import transactie.PersoonTrans;

/**
 *
 * @author Ruben
 */
public class PersoonTransTest {
    PersoonTrans transactie;
    PloegTrans transactiePloeg;

    public PersoonTransTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        transactie = new PersoonTrans();
        transactiePloeg = new PloegTrans();
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

        //Persoon toevoegen
    @Test
    public void persoonToevoegen() throws Exception{
        
        Persoon p1 = new Persoon();
        p1.setGeboortedatum(1111, 1, 1);
        p1.setVoornaam("Ruben");
        p1.setNaam("Descheemaecker");
        
        p1.setId(transactie.persoonToevoegen(p1));
    }
    
    //meerdere personen toevoegen
    @Test
    public void meerderePersonenToevoegen() throws Exception{
        
        for(int i = 1; i < 25; i ++) {
            Persoon p = new Persoon();
            p.setGeboortedatum(1995, 8, i);
            p.setVoornaam("Ruben"+i);
            p.setNaam("Descheemaecker"+i);
            transactie.persoonToevoegen(p);
        }
    }
    
    //persoon wijzigen
    @Test
    public void persoonWijzigen() throws Exception{
        //persoon toevoegen
        Persoon p1 = new Persoon();
        p1.setGeboortedatum(1995, 8, 23);
        p1.setVoornaam("David");
        p1.setNaam("Claeys");
        int id = transactie.persoonToevoegen(p1);
        
        //persoon aanpassen
        Persoon p2 = new Persoon();
        p2.setGeboortedatum(1995, 8, 23);
        p2.setVoornaam("Claeys");
        p2.setNaam("Cristina");
        //p2 hetzelfde id geven als p1 zodat p1 de nieuwe naa mzal krijgen
        p2.setId(id);
        transactie.persoonWijzigen(p2);
    }
    
    //speler verwijderen van ploeg
    @Test
    public void spelerVerwijderenVanPloeg() throws Exception{
        //Persoon aanmaken
        Persoon p = new Persoon();
        p.setGeboortedatum(95, 8, 23);
        p.setNaam("Tom");
        p.setVoornaam("Tjaf");
        //persoon toeveogen en ID opslaan voor verder gebruik.
        int id = transactie.persoonToevoegen(p);
        
        //Ploeg aanmaken
        Ploeg m = new Ploeg();
        m.setCategorie(Categorie.U6);
        m.setNaam("U6a");
        
        
        
        
        //Speler toevoegen aan ploeg en ploeg maken
        transactie.spelerToevoegenAanPloeg(id, transactiePloeg.ploegToevoegen(m));
        
        //Speler verwijderen van ploeg
        transactie.spelerVerwijderenVanPloeg(id);
    }
    
    //speler toevoegen aan ploeg
    @Test
    public void spelerToevoegenAanPloeg() throws Exception{
        //Persoon aanmaken
        Persoon p = new Persoon();
        p.setGeboortedatum(95, 8, 23);
        p.setNaam("De Rechter");
        p.setVoornaam("Ruben");
        
        //Ploeg aanmaken
        Ploeg m = new Ploeg();
        m.setCategorie(Categorie.U11);
        m.setNaam("U11a");
        
        //speler toevoegen aan de ploeg. terwijl de speler en ploeg maken.
        transactie.spelerToevoegenAanPloeg(transactie.persoonToevoegen(p), transactiePloeg.ploegToevoegen(m));
    }
    
    //persoon verwijderen
    @Test
    public void persoonVerwijderen() throws Exception {
        //Persoon aanmaken
        Persoon p = new Persoon();
        p.setGeboortedatum(95, 8, 23);
        p.setNaam("De Scheids");
        p.setVoornaam("Klaas");
        
        //Persoon toevoegen en verwijderen
        transactie.persoonVerwijderen(transactie.persoonToevoegen(p));
    }
    
    //speler wordt trainer
    @Test
    public void spelerWordtTrainer() throws Exception{
        //Persoon aanmaken
        Persoon p = new Persoon();
        p.setGeboortedatum(95, 8, 23);
        p.setNaam("De Trainer");
        p.setVoornaam("Mark");
        
        //persoon/speler trainer maken
        transactie.spelerWordtTrainer(transactie.persoonToevoegen(p));
    }
    
    //trainer wordt speler
    @Test
    public void trainerWordtSpeler() throws Exception{
        //Persoon aanmaken
        Persoon p = new Persoon();
        p.setGeboortedatum(95, 8, 23);
        p.setNaam("De Speler");
        p.setVoornaam("Ronnie");
        p.setTrainer(true);
        
        //trainer een speler/persoon maken.
        transactie.trainerWordtSpeler(transactie.persoonToevoegen(p));
    }

}
