/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactie;
import databag.Ploeg;


/**
 *
 * @author To
 */
public interface PloegTransInterface {

    /**
     * een Ploeg toevoegen. 
     *
     * @param p de ploeg die toegevoegd moet worden
     * @return het id van de zonet toegevoegde ploeg
     * @throws java.lang.Exception
     */
    public Integer ploegToevoegen(Ploeg p) throws Exception;

    /**
     * Een trainer ontkoppelen van een ploeg.
     * @param ploegId het id van de ploeg waarvoor de trainer ontkoppeld moet worden
     * @throws java.lang.Exception
     */
    public void trainerVerwijderenVanPloeg(int ploegId) throws Exception;
    
    /**
     * Een trainer aan een ploeg zonder trainer koppelen
     * @param trainerId het id van de trainer die gekoppeld moet worden
     * @param ploegId het id van de ploeg waaraan de opgegeven trainer gekoppeld moet worden
     * @throws java.lang.Exception
     */

    public void trainerKoppelenAanPloeg(int trainerId, int ploegId) throws Exception;
}