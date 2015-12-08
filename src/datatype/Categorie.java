/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datatype;



/**
 *
 Beschikbare ploegcategoriën
 */
public enum Categorie {
    U6(6,"U6"),U7(7,"U7"),U8(8,"U8"),U9(9,"U9"),U10(10,"U10"),U11(11,"U11");
    

    private int leeftijd;
    private String tekst;
    
    Categorie(int leeftijd,String tekst)
    {
        this.leeftijd=leeftijd;
        this.tekst=tekst;
    }
    
    public int getLeeftijd()
    {
        return leeftijd;
    }
    
    public String getTekst()
    {
        return tekst;
    }
    
    
}
    

