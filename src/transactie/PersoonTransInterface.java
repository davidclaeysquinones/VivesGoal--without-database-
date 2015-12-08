/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactie;
import databag.Persoon;

public interface PersoonTransInterface {

    /**
     * Voeg persoon toe
     *
     * @param p de persoon die toegevoegd moet worden
     * @return id van de net toegevoegde persoon
     * @throws java.lang.Exception
     */
    public Integer persoonToevoegen(Persoon p) throws Exception;

    /**
     * Wijzigen
     *
     * @param p de bestaande persoon die gewijzigd moet worden
     * @throws java.lang.Exception
     */
    public void persoonWijzigen(Persoon p) throws Exception;

    /**
     * Een speler ontkoppelen van de ploeg.
     *
     * @param spelerId het id van de speler die uit een ploeg verwijderd moet
     * worden
     * @throws java.lang.Exception
     */
    public void spelerVerwijderenVanPloeg(int spelerId) throws Exception;

    /**
     * Een speler zonder ploeg koppelen aan een ploeg.
     *
     * @param spelerId het id van de speler die toegevoegd moet worden aan een
     * ploeg
     * @param ploegId het id van de ploeg waar de opgegeven speler aan gekoppeld
     * moet worden
     * @throws java.lang.Exception
     */
    public void spelerToevoegenAanPloeg(int spelerId, int ploegId) throws Exception;

    /**
     * Een persoon verwijderen.
     *
     * @param persoonId het id van de persoon die verwijderd moet worden
     * @throws java.lang.Exception
     */
    public void persoonVerwijderen(int persoonId) throws Exception;

    /**
     * Een speler veranderen naar een trainer. 
     * @param spelerId het id van de speler die trainer wordt
     * @throws java.lang.Exception
     */
    public void spelerWordtTrainer(int spelerId) throws Exception;

    /**
     * Een trainer veranderen naar een speler. 
     *
     * @param trainerId is het id van de trainer die speler wordt
     * @throws java.lang.Exception
     */
    public void trainerWordtSpeler(int trainerId) throws Exception;

    /**
     * De gegevens van een persoon valideren
     * @param p het persoonsobject dat gevalideerd moet worden
     * @throws java.lang.Exception
     */
    public void checkAlleVeldenIngevuld(Persoon p) throws Exception;
}
