package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Mezo implements Cloneable {

    protected Map<Irany, Mezo> szomszedok = new HashMap<>();
    private String id;
    private int sor;
    private int oszlop;
    private int teherbiras;
    private int hotakaro;
    private ArrayList<Jatekos> alloJatekos = new ArrayList<>();
    private Jegesmedve alloJegesmedve;
    private boolean vizsgalt;

    public Mezo(int teherbiras, int hotakaro) {
        this.teherbiras = teherbiras;
        this.hotakaro = hotakaro;
        vizsgalt = false;
    }

    public Mezo(String id, int teherbiras, int hotakaro) {
        this.id = id;
        this.teherbiras = teherbiras;
        this.hotakaro = hotakaro;
        vizsgalt = false;
    }

    public Mezo(int teherbiras, int hotakaro, Jegesmedve medve) {
        this.teherbiras = teherbiras;
        this.hotakaro = hotakaro;
        this.alloJegesmedve = medve;
        vizsgalt = false;
    }

    public Mezo(String id, int teherbiras, int hotakaro, Jegesmedve medve) {
        this.id = id;
        this.teherbiras = teherbiras;
        this.hotakaro = hotakaro;
        this.alloJegesmedve = medve;
        vizsgalt = false;
    }

    public boolean isVizsgalt() {
        return vizsgalt;
    }

    public void setVizsgalt(boolean vizsgalt) {
        this.vizsgalt = vizsgalt;
    }

    /**
     * Leklónoz egy mezőt, nem túl deep copy, de egy pálya lemásolásánál és összehasonlításánál,
     * és ellenőrzésénél hogy megegyeznek e a pályák ha ezt a függvényt használjuk,
     * már különböző lesz az eredmény és ennyi elég is.
     *
     * @return Egy Shallow Copyt a mezőről
     * @throws CloneNotSupportedException
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public int getSor() {
        return sor;
    }

    public void setSor(int sor) {
        this.sor = sor;
    }

    public int getOszlop() {
        return oszlop;
    }

    public void setOszlop(int oszlop) {
        this.oszlop = oszlop;
    }

    /**
     * visszaadja a mező ID-ját.
     *
     * @return
     */
    public String getID() {
        return id;
    }

    /**
     * setter, beállítja a mező szomszédját
     *
     * @param i az irány amibe a szomszédot teszi
     * @param m a szomszéd mező
     */
    public void addSzomszedok(Irany i, Mezo m) {
        this.szomszedok.put(i, m);
    }

    /**
     * visszaadja a mezőn befagyott tárgyat. A Jegtabla osztály felüldefiniálja
     *
     * @return alapesetben nullt ad vissza.
     */
    Targy getTargy() {
        return null;
    }

    /**
     * setter, beállítja a mezőre letett alkatrészek értékeit.
     *
     * @param a1 alkatrész
     * @param a2 alkatrész
     * @param a3 alkatrész
     */
    public void setAlkatreszek(Alkatresz a1, Alkatresz a2, Alkatresz a3) {
    }

    /**
     * setter, beálllítja a táblába befagyott tárgyat. A leszármazottak felüldefiniálják.
     *
     * @param t a kapott tárgy
     */
    public void setFagyottTargy(Targy t) {
    }

    /**
     * setter, beálllítja a táblába befagyott alkatrészt. A leszármazottak felüldefiniálják.
     *
     * @param t a kapott alkatrész
     */
    public void setFagyottAlk(Alkatresz t) {
    }

    /**
     * Növeli a mező hórétegének értékét a paraméterben kapott értékkel.
     * Ha már 5 hó van egy mezőn az nem növekszik tovább.
     *
     * @param num ahány egységgel növelni kell a hóréteget.
     */
    public void horetegNovel(int num) {
        if (hotakaro >= 5) {
            return;
        }
        this.hotakaro += num;
    }

    /**
     * Csökkenti a mezőn lévő hóréteget egyel.
     */
    public void horetegCsokkent() {
        if (hotakaro > 0)
            this.hotakaro--;
    }

    /**
     * Absztrakt függvény, implementációja befogadja a rálépő játékost (attól függően, hogy jégtábla, vagy lyuk az adott mező)
     *
     * @param j a mezőre lépő játékos
     */

    public abstract void elfogad(Jatekos j);

    /**
     * A paraméterül kapott jegesmedvét a jégtáblára teszi
     *
     * @param j a jegesmedve akit rá kell tenni a jégtáblára
     */
    public void elfogad(Jegesmedve j) { // a Jegesmedve nem esik vízbe csak odalép vhova, mindegy hogy jégtábla vagy lyuk..
        j.setMezo(this);
        this.alloJegesmedve = j;
    }

    /**
     * Eltávolítja a Játékost erről a mezőről
     *
     * @param j A játékos akit le kell venni
     */
    public void eltavolit(Jatekos j) {

        alloJatekos.remove(j);
    }

    /**
     * Eltávolítja a rajta álló jegesmedvét
     *
     * @param j a jegesmedve akit el kell távolítani
     */
    public void eltavolit(Jegesmedve j) {
        alloJegesmedve = null;
    }

    /**
     * A játékos és a jegesmedve találkozik.
     *
     * @param j a jegesmedve
     */
    public void utkozik(Jegesmedve j) {
        if (j != null) {
            if (!isIglu() && alloJatekos.size() >= 1) {
                alloJatekos.get(0).meghal();
                alloJatekos.remove(0);
            }
        }
    }

    /**
     * Visszaadja a mezőn álló játokosokat.
     *
     * @return A játékosok listája.
     */
    public ArrayList<Jatekos> getAlloJatekos() {
        return alloJatekos;
    }

    /**
     * Hozzáad egy játékost a mezőhöz.
     *
     * @param alloJatekos a játékos akit hozzáad.
     */
    public void addAlloJatekos(Jatekos alloJatekos) {
        this.alloJatekos.add(alloJatekos);
    }

    /**
     * getter
     *
     * @return visszaadja, hogy az adott mezőn van-e iglu. Ha a mező jégtábla, a függvény felüldefiniálódik, egyébként visszatérési értéke false.
     */
    public boolean isIglu() {
        return false;
    }

    /**
     * AAmennyiben a mező jégtábla, úgy beállítja a jégtábla iglu attribútumának értékét truera, egyébként nem csinál semmit.
     *
     * @param iglu
     */
    public void setIglu(boolean iglu) {
    }

    /**
     * A Jegtabla típusú mező felülírja ezt a függvényt (növeli 1-gyel a sátor felépítése óta eltelt időt 1-gyel),
     * egyébként nem csinál semmit.
     */
    public void satorIdoNovel() {
    }

    /**
     * getter
     *
     * @return Ha a mező egy jégtábla, őgy a Jegtabla osztály felülírja ezt a függvényt(visszaadja, hogy mióta
     * áll a sátor az adott jégtáblán), amennyiben a mező Lyuk, úgy 0 a visszatérési érték.
     */
    public int getSatorMiotaVan() {
        return 0;
    }
    public void setSatorMiotaVan(int i){};

    /**
     * Amennyiben a mező jégtábla, úgy nullára állítja az adott jégtáblán lévő sátor satorIdo attribútumát (eltűnik a sátor a jégtábláról)
     */
    public void satratNullaz() {
    }

    /**
     * Visszadja a szomszédos mezőt a paraméterként kapott irányba
     *
     * @param i Az irány
     * @return A szomszédos mező
     */
    public Mezo getSzomszed(Irany i) {
        return this.szomszedok.get(i);
    }

    /**
     * Ez annak a modellezése amikor leteszik az  alkatrészeket a mezőre, hogy majd el lehessen sütni
     * és így megnyerjék a játékot. Hozzáadja a paraméterül kapott alkatrészt a kollekciójukhoz
     *
     * @param a amit hozzá kell adni a kollekcióhoz
     */
    public abstract void alkatreszNovel(Alkatresz a);

    /**
     * Ellenőrzi, hogy van-e a mezőn iglu, és ha nincs, a mezőn álló játékosok testhőjét csökkenti 1-gyel.
     */
    public void testhoCsokkent() {
        if (!this.isIglu()) {
            for (Jatekos jatekos : alloJatekos) {
                int ho = jatekos.getTestho();
                jatekos.setTestho(ho - 1);
            }
        }
    }

    /**
     * Megmondja mennyi a mező teherbírása.
     *
     * @return a teherbírás értéke.
     */
    public int getTeherbiras() {
        return teherbiras;
    }

    /**
     * setter, beállítja a tábla teherbírásának értékét
     *
     * @param teherbiras
     */

    public void setTeherbiras(int teherbiras) {
        this.teherbiras = teherbiras;
    }

    /**
     * Visszadaj a mezőn lévő fagyott alkatrészt
     *
     * @return
     */
    public abstract Alkatresz getFagyottAlkatresz();

    /**
     * Visszadja a mezőre letett alkatrészeket
     *
     * @return
     */
    public abstract ArrayList<Alkatresz> getAlkatreszek();

    /**
     * Visszadja a mezőn lévő jegesmedvét.
     *
     * @return a jegesmedve
     */
    public Jegesmedve getAlloJegesmedve() {
        return alloJegesmedve;
    }

    /**
     * Elhelyez egy jegesmdvét a mezőn.
     *
     * @param alloJegesmedve akit elhelyez.
     */
    public void setAlloJegesmedve(Jegesmedve alloJegesmedve) {
        this.alloJegesmedve = alloJegesmedve;
    }

    /**
     * Visszadja mennyi hóréteg van a mezőn.
     *
     * @return a hóréteg értéke.
     */
    public int getHotakaro() {
        return hotakaro;
    }

    /**
     * Beállítja a hóréteget a mezőn.
     *
     * @param hotakaro a hótakaró rétegeinek száma.
     */
    public void setHotakaro(int hotakaro) {
        this.hotakaro = hotakaro;
    }

    public abstract void state();
}
