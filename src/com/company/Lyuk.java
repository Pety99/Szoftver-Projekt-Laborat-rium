package com.company;

import java.util.ArrayList;

public class Lyuk extends Mezo {

    /**
     * Konstruktor
     * @param hotakaro a lyukon lévő hóréteg száma
     */
    public Lyuk(int hotakaro) {   // a nulla teherbírású a Lyuk..
        super(0, hotakaro);
    }

    /**
     * Konstruktor
     * @param id azonosító
     * @param hotakaro a lyukon lévő hóréteg száma
     */
    public Lyuk(String id, int hotakaro) {   // a nulla teherbírású a Lyuk..
        super(id, 0, hotakaro);
    }

    /**
     * kiírja a lyuk állapotát
     */
    public void state() {
        System.out.println("Mezo ID: " + super.getID() + " " + "Horeteg: " + super.getHotakaro());
        String m1 = super.getSzomszed(Irany.Fel).getID();
        String m2 = super.getSzomszed(Irany.JobbFel).getID();
        String m3 = super.getSzomszed(Irany.Jobb).getID();
        String m4 = super.getSzomszed(Irany.JobbLe).getID();
        String m5 = super.getSzomszed(Irany.Le).getID();
        String m6 = super.getSzomszed(Irany.BalLe).getID();
        String m7 = super.getSzomszed(Irany.Bal).getID();
        String m8 = super.getSzomszed(Irany.BalFel).getID();

        System.out.println("Szomszédok (fentről kezdve jobbra haladva): " +
                m1 + " " + m2 + " " + m3 + " " + m4 + " " + m5 + " " + m6 + " " + m7 + " " + m8);
    }


    /**
     * Vízbe ejti a játékost
     *
     * @param j A játékos amit el kell fogadnia a lyuknak
     */
    @Override
    public void elfogad(Jatekos j) {

        j.setMezo(this);
        this.getAlloJatekos().add(j);
        utkozik(getAlloJegesmedve());
        j.vizbeEsik();

    }


    /**
     * Ez annak a modellezése amikor leteszik az  alkatrészeket a mezőre, hogy majd el lehessen sütni
     * és így megnyerjék a játékot. Hozzáadja a paraméterül kapott alkatrészt a kollekciójukhoz
     *
     * @param a amit hozzá kell adni a kollekcióhoz
     */
    @Override
    public void alkatreszNovel(Alkatresz a) {
    }

    /**
     * getter
     * @return Lyuk típusú mezőn nincs alkatrész elásva, ezért visszatérési értéke null
     */
    @Override
    public Alkatresz getFagyottAlkatresz() {
        return null;
    }

    /**
     * getter
     * @return Lyuk típusú mezőn nem lehet alkatrész letéve, ezért visszatérési értéke null
     */
    @Override
    public ArrayList<Alkatresz> getAlkatreszek() {
        return null;
    }
}
