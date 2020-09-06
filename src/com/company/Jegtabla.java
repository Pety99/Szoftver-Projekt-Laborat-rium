package com.company;

import java.util.ArrayList;

public class Jegtabla extends Mezo{

    private ArrayList<Alkatresz> alkatreszek = new ArrayList<>();
    private Alkatresz fagyottAlkatresz;
    private Targy fagyotttargy;
    private int satorMiotaVan;
    private boolean iglu;

    /**
     * Konstruktor
     * @param teherbiras a jégtábla teherbírása
     * @param hotakaro a jégtáblán lévő hótakaró
     * @param targy a jégtáblába fagyott tárgy
     */
    public Jegtabla(int teherbiras, int hotakaro, Targy targy) {
        super(teherbiras, hotakaro);
        this.fagyotttargy = targy;
        this.satorMiotaVan = 0;
        this.iglu = false;
    }

    /**
     * KOnstruktor
     * @param id a jégtábla azonosítója
     * @param teherbiras a jégtábla teherbírása
     * @param hotakaro a jégtáblán lévő hótakaró
     * @param targy a jégtáblába fagyott tárgy
     */
    public Jegtabla(String id, int teherbiras, int hotakaro, Targy targy) {
        super(id, teherbiras, hotakaro);
        this.fagyotttargy = targy;
        this.satorMiotaVan = 0;
        this.iglu = false;
    }

    /**
     * Konstruktor
     * @param teherbiras a jégtábla teherbírása
     * @param hotakaro a jégtáblán lévő hótakaró
     * @param targy a jégtáblán lévő tárgy
     * @param alkatresz a jégtáblán lévő alkatrész
     */
    public Jegtabla(int teherbiras, int hotakaro, Targy targy,Alkatresz alkatresz) {
        super(teherbiras, hotakaro);
        this.fagyotttargy = targy;
        this.satorMiotaVan = 0;
        this.alkatreszek.add(alkatresz);
        this.iglu = false;
    }

    /**
     * kiírja a jégtábla állapotát
     */
    public void state(){
        System.out.println("Mezo ID: " + super.getID() + " "+ "Horeteg: "+super.getHotakaro() + " "
                + "Teherbiras: " + super.getTeherbiras());
        String m1 = super.getSzomszed(Irany.Fel) != null ? super.getSzomszed(Irany.Fel).getID() : "-";
        String m2 = super.getSzomszed(Irany.JobbFel)!= null ? super.getSzomszed(Irany.JobbFel).getID() : "-";
        String m3 = super.getSzomszed(Irany.Jobb)!= null ? super.getSzomszed(Irany.Jobb).getID() : "-";
        String m4 = super.getSzomszed(Irany.JobbLe)!= null ? super.getSzomszed(Irany.JobbLe).getID() : "-";
        String m5 = super.getSzomszed(Irany.Le)!= null ? super.getSzomszed(Irany.Le).getID() : "-";
        String m6 = super.getSzomszed(Irany.BalLe)!= null ? super.getSzomszed(Irany.BalLe).getID() : "-";
        String m7 = super.getSzomszed(Irany.Bal)!= null ? super.getSzomszed(Irany.Bal).getID() : "-";
        String m8 = super.getSzomszed(Irany.BalFel)!= null ? super.getSzomszed(Irany.BalFel).getID() : "-";

        System.out.println("Szomszédok (fentről kezdve jobbra haladva): " +
                m1 + " " + m2 + " " + m3 + " " + m4 + " " + m5 + " " + m6 + " " + m7 + " " + m8);
    }

    /**
     * Beállítja a jágtáblán lévő tárgyat.
     * @param t a tárgy.
     */
    @Override
    public void setFagyottTargy(Targy t) {
        fagyotttargy = t;
    }

    /**
     *  Beállítja a jágtáblán lévő alkatrészt.
     * @param t az alkatrész.
     */
    @Override
    public void setFagyottAlk(Alkatresz t) {
        fagyottAlkatresz = t;
    }

    /**
     * Megnöveli a sátor felállítása óta eltelt időt.
     */
    @Override
     public void satorIdoNovel() {
        satorMiotaVan += 1;
     }

    /**
     * Nullára állítja a sátor idejét.
     */
    @Override
    public void satratNullaz() {
        satorMiotaVan = 0;
    }

    /**
     * Visszaadja, hogy mióta áll a sátor.
     * @return az idő.
     */
    @Override
    public int getSatorMiotaVan() {
        return satorMiotaVan;
    }

    /**
     * Elfogadja a  játékost, úgy hogy beállítja a mezőjének saját magát, illetve beteszi az állójátékosok közé
     *
     * @param j A játékos amit el kell fogadni
     */
    @Override
    public void elfogad(Jatekos j) {
        j.setMezo(this);
        this.getAlloJatekos().add(j);

        if (getAlloJegesmedve() != null)
            utkozik(getAlloJegesmedve());
        if (getTeherbiras() < getAlloJatekos().size()) {
            for (Jatekos j2 : getAlloJatekos())
                j2.vizbeEsik();
        }
    }

    /**
     * Visszaadja a befagyott tárgyat amit tárol/ha nem tárol null-t
     *
     * @return visszaadja a befagyott targyat
     */
    @Override
    public Targy getTargy() {
        return this.fagyotttargy;
    }


    /**
     * Beállítja a tábla iglu tulajdonságát
     *
     * @param iglu annak az értéke hogy a tábla iglu típusú lesz vagy nem
     */
    @Override
    public void setIglu(boolean iglu) {
        this.iglu = iglu;
    }

    /**
     * Megmondja van e iglu a jégtáblán.
     * @return true ha van, false ha nincs.
     */
    @Override
    public boolean isIglu() {
        return iglu;
    }

    /**
     * Ez annak a modellezése amikor leteszik az  alkatrészeket a mezőre, hogy majd el lehessen sütni
     * és így megnyerjék a játékot. Hozzáadja a paraméterül kapott alkatrészt a kollekciójukhoz
     *
     * @param a amit hozzá kell adni a kollekcióhoz
     */
    @Override
    public void alkatreszNovel(Alkatresz a) {
        this.alkatreszek.add(a);
    }

    /**
     * Teszteléshez kell. Berakja az alkatrészeket a mező alkatrész tömbjébe.
     */
    @Override
    public void setAlkatreszek(Alkatresz a1, Alkatresz a2, Alkatresz a3) {
        alkatreszek.add(0, a1);
        alkatreszek.add(1, a2);
        alkatreszek.add(2, a3);
    }


    /**
     * Visszadja a jégtáblába fagyott alkatrészt.
     */
    @Override
    public Alkatresz getFagyottAlkatresz() {
        return this.fagyottAlkatresz;
    }

    /**
     * A jégtáblára letett alkatrészeket adja oda amikor a játékos meghívja az alkatreszFelvesz fv-t
     *
     * @return az alaktrészek tömbje.
     */
    @Override
    public ArrayList<Alkatresz> getAlkatreszek() {

        return this.alkatreszek;
    }

    /**
     * Beállítja, hogy a sátor mióta van felállítva
     * @param satorMiotaVan erre állítja be
     */
    @Override
    public void setSatorMiotaVan(int satorMiotaVan) {
        this.satorMiotaVan = satorMiotaVan;
    }
}
