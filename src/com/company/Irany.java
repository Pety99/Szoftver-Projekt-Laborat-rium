package com.company;

public enum Irany{
    Fel,
    Le,
    BalFel,
    Bal,
    BalLe,
    JobbFel,
    Jobb,
    JobbLe;


    /**
     * Visszaadja az irányok ellentétes irányát..
     *
     */
    public Irany ellentetes() {
        if(this==Irany.Fel)
        {
            return Le;
        }
        else if(this==Irany.Le)return Fel;
        else if(this==Irany.Jobb)return Bal;
        else return Jobb;
    }

    /**
     * Egy kapott stringből visszaadjuk az annak megfelelő Irany enumot.
     *
     * @param string Az irány stringje, amit vissza szeretnénk adni Irany típusban.
     */
    public static Irany StringToIrany(String string){
        if (string.equals("Fel")){
            return Fel;
        }
        else if(string.equals("Le")){
            return Le;
        }
        else if(string.equals("BalFel")){
            return BalFel;
        }
        else if(string.equals("Bal")){
            return Bal;
        }
        else if(string.equals("BalLe")){
            return BalLe;
        }
        else if(string.equals("JobbFel")){
            return JobbFel;
        }
        else if(string.equals("Jobb")){
            return Jobb;
        }
        else if(string.equals("JobbLe")){
            return JobbLe;
        }
        else return null;
    }

}
