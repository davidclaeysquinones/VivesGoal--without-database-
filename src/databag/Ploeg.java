/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databag;

import datatype.Categorie;
import exception.ApplicationException;

/**
 *
 * @author david
 */
public class Ploeg {
    private int id;
    private String naam;
    private Categorie categorie;
    private Integer trainer;
    
    
    public int getId()
    {
        return id;
    }
    
    public String getNaam()
    {
        return naam;
    }
    
    public Categorie getCategorie()
    {
        return categorie;
    }
    
    public Integer getTrainer()
    {
        return trainer;
    }
    
    public void setId(int id)
    {
        this.id=id;
    }
    
    public void setNaam(String naam)
    {
        this.naam=naam;
    }
    
    public void setCategorie(Categorie categorie)
    {
        this.categorie=categorie;
    }
     public void setCategorie(String categorie) throws ApplicationException {
        try {
            this.categorie = Categorie.valueOf(categorie);
        }
        catch (IllegalArgumentException e) 
        {
            throw new ApplicationException("Geen geldige categorie voor de ploeg");
        }
    }
    
    
    /**
     *
     * @param id
     */
    public void setTrainer(int id) 
    {
        trainer=id;
    }
    
    @Override
    public String toString()
    {
        String output ="id : "+id+" naam : "+naam;
        return output;
    }
    
 
    
}