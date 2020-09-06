package com.company;

/**
 * A játékos fulladási állapotai
 */
public enum FulladasiAllapot {
    aktiv,
    fuldoklik ,
    kimentheto,
    halott;


    @Override
    public String  toString(){
        if(this.name().equals("aktiv")){
            return "Aktív";
        }
        else if(this.name().equals("fuldoklik")){
            return "Fuldoklik";
        }
        else if(this.name().equals("kimentheto")){
            return "Kimenthető";
        }
        if(this.name().equals("halott")){
            return "Halott";
        }
        return null;
    }
}

