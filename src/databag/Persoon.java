/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databag;

import exception.ApplicationException;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author david
 */
public class Persoon {

    private Integer id;
    private String naam;
    private String voornaam;
    private Date geboortedatum;
    private boolean trainer;
    private String opmerking;
    private Integer ploegid;

    // defaultconstructor
    // getters
    /**
     * Integer datatype omdat bij aanmaak het id standaard null is
     *
     * @return
     */
    public Integer getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public boolean getTrainer() {
        return trainer;
    }

    public String getOpmerking() {
        return opmerking;
    }

    //setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    /**
     * setGeboortedatum(2016,1,1) stelt de datum in op 1 januari 2016
     * 
     * @param jaar
     * @param maand
     * @param dag 

     * @throws ApplicationException
     */

    public void setGeboortedatum(int jaar, int maand, int dag) throws ApplicationException {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MILLISECOND, 86400000);
        Date current = new Date(c.getTimeInMillis());
        c.set(jaar, maand - 1, dag);
        Date d = new Date(c.getTimeInMillis());
        if (jaar >= 0 && maand <= 12 && maand > 0 && dag <= 31 && current.after(d)) {
            geboortedatum = d;
        } else {
            throw new ApplicationException("De opgegeven datum heeft een verkeerd formaat");
        }

    }

    public void setTrainer(boolean trainer) {
        this.trainer = trainer;
    }

    public void setOpmerking(String opmerking) {
        this.opmerking = opmerking;
    }

    public Integer getPloegid() {
        return ploegid;
    }

    public void setPloegid(Integer ploegid) {
        this.ploegid = ploegid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(voornaam).append(" ").
                append(naam).append(" ");
        return sb.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else {
            if (obj.getClass() != Persoon.class) {
                return false;
            } else {
                if (obj == this) {
                    return true;
                } else {
                    Persoon p = (Persoon) obj;
                    if (p.getNaam().equals(this.naam) && p.getVoornaam().equals(this.voornaam) && p.getGeboortedatum().equals(this.geboortedatum)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }

        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.naam);
        hash = 23 * hash + Objects.hashCode(this.voornaam);
        hash = 23 * hash + Objects.hashCode(this.geboortedatum);
        return hash;
    }

   
}
