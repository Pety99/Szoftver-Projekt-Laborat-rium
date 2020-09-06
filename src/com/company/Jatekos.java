package com.company;

import java.util.ArrayList;

public abstract class Jatekos extends Mozgathato implements Cloneable {
    private Kontroller kontroller;
    //Ha ez nem volatile akkor nem breakel a while loop.
    private String ID;

    public void setMunkakSzama(int munkakSzama) {
        this.munkakSzama = munkakSzama;
    }

    private volatile int munkakSzama = 4;
    private volatile int testho;
    private volatile boolean vedett;
    private ArrayList<Alkatresz> alkatreszek = new ArrayList<>();
    private ArrayList<Targy> targyak = new ArrayList<>();
    private volatile FulladasiAllapot allapot = FulladasiAllapot.aktiv;
    Jatekos() {
        this.testho = 5;
    }

    Jatekos(Kontroller k, int testho, String ID) {
        this.kontroller = k;
        this.testho = testho;
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Jatekos jatekos = (Jatekos) super.clone();
        jatekos.alkatreszek = new ArrayList<>();
        jatekos.alkatreszek.addAll(this.alkatreszek);
        jatekos.targyak = new ArrayList<>();
        jatekos.targyak.addAll(this.targyak);
        return jatekos;
    }

    /**
     * kiírja a játékos állapotát
     */
    public void state() {
        String id = this.getTartozkodasiMezo().getID();
        System.out.println("Tartozkodasi mezo: " + id + " " + "Testho: " + testho + " " + "Allapot: " + allapot
                + " " + "Munkák száma: " + munkakSzama);
    }

    /**
     * Lekérdezi a tárgyak listát
     */
    public ArrayList<Targy> getTargyak() {
        return targyak;
    }

    /**
     * Lekérdezi a testhő attribútumot
     */
    public int getTestho() {
        return testho;
    }


    /**
     * Testhő beállítása
     *
     * @param testho A testhőérték, amire be szeretnénk állítani
     */
    public void setTestho(int testho) {
        this.testho = testho;
    }

    /**
     * Beállítja a védett attribútumot
     */
    public void setVedett(boolean b) {
        vedett = b;
    }

    /**
     * Átlépteti a játékost a szomszédos mezőre a megadott irányba.
     *
     * @param i Az az irány amerre lépni szeretnénk.
     */
    public void lep(Irany i) {
        // Lekéri a szomszéd mezőt
        Mezo aktualis = getTartozkodasiMezo();
        Mezo szomszed = aktualis.getSzomszed(i);
        if (szomszed != null) {
            munkakSzama--;
            //eltávolítja a játékost
            aktualis.eltavolit(this);
            //Átadja magát a szomszédos játékosnak
            szomszed.elfogad(this);

        }
        this.state();
    }

    /**
     * A játékos játszik, cselekvéseket végezhet amíg a 4 munka el nem fogy.
     */
    public void jatszik() {
       // if (getAllapot() == FulladasiAllapot.aktiv)
       //     munkakSzama = 4;
        //ha elfogytak a munkák a következő játékos jön

        while (true) {
            if (allapot.equals(FulladasiAllapot.fuldoklik) || this.munkakSzama <= 0) {
                break;
            }
        }

    }


    /**
     * A játékos meghal.
     */
    public void meghal() {
        kontroller.jatekVege(false);
    }

    /**
     * A saját mezőjén lévő tárgyat a játékos felveszi, a mezőn a felvett tárgy értékét nullra állítja, és a munkái számát egyel csökkenti.
     */
    public void kapar() {
        // Ezekből csak egy futhat le mert egy mezőn vagy alkatrész vagy tárgy van
        Mezo m = this.getTartozkodasiMezo();
        if (m.getTeherbiras()!=0) {
            int hotakaro = m.getHotakaro();
            if (hotakaro == 0) {
                Targy targy = m.getTargy();
                if (targy != null) {
                    targy.felvesz(this);
                    m.setFagyottTargy(null);
                    this.munkakSzama--;
                }
                Alkatresz alk = m.getFagyottAlkatresz();
                if (alk != null) {
                    alk.felvesz(this);
                    m.setFagyottAlk(null);
                    this.munkakSzama--;
                }
            }
        }
    }

    /**
     * Hozzáadja a tárgyak listájához a felvevendő lapátot.
     *
     * @param l A lapát amit fel szeretnénk venni.
     */
    public void lapatFelvesz(Lapat l) {
        targyak.add(l);
    }

    /**
     * Hozzáadja a tárgyak listájához a felvevendő kötelet.
     *
     * @param k A kötél amit fel szeretnénk venni.
     */
    public void kotelFelvesz(Kotel k) {
        targyak.add(k);
    }

    /**
     * Megnöveli a testhőt 1-el.
     *
     * @param e Az élelem, amit fel szeretnénk venni.
     */
    public void elelemFelvesz(Elelem e) {
        testho++;
    }

    /**
     * Védelmet nyújt a játékosnak vízbe esés ellen a védelem attribútum trueba állításával
     *
     * @param b A felvevendő búvárruha
     */
    public void buvarruhaFelvesz(Buvarruha b) {
        b.vedelem(this);
    }

    /**
     * Hozzáadja a tárgyak listájához a felvevendő sátrat.
     *
     * @param s A sátor amit fel szeretnénk venni.
     */
    public void satorFelvesz(Sator s) {
        targyak.add(s);
    }

    /**
     * Hozzáadja az alkatrészek listájához a felvevendő alkatrészt.
     *
     * @param a Az alkatrész, amit fel szeretnénk venni.
     */
    public void alkatreszFelvesz(Alkatresz a) {
        alkatreszek.add(a);
    }

    /**
     * Kihúz egy másik játékost a saját táblájára
     *
     * @param i Az irány, amerre lévő játékost szeretnénk kihúzni.
     */
    public void kihuz(Irany i) {
        KotelVisitor kv = new KotelVisitor();
        Mezo szomszed = this.getTartozkodasiMezo().getSzomszed(i);
        for (Targy t : targyak) {
            if (t.accept(kv)) {     //ha a tárgy kötél akkor true
                int size = szomszed.getAlloJatekos().size();
                boolean munkat = false;
                if (size != 0) {
                    for (int j = 0; j < size; j++) {  // a szomszéd mezőről minden játékost kihúz.
                        Jatekos mentett = szomszed.getAlloJatekos().get(0);
                        if (mentett.getAllapot() == FulladasiAllapot.fuldoklik|| mentett.getAllapot()==FulladasiAllapot.kimentheto) {
                            t.hasznal(mentett);
                            munkat=true;
                            szomszed.eltavolit(mentett);
                            this.getTartozkodasiMezo().elfogad(mentett);
                        }
                    }
                }
                if(munkat)
                    munkakSzama--;
            }
        }
    }

    /**
     * A játékos lapátol, ha van lapátja, a lapáttal, ha nincs, akkor lapát nélkül, de úgy csak 1 hóréteget tud eltávolítani.
     */
    public void lapatol() {
        if (getTartozkodasiMezo().getTeherbiras() != 0) {
            LapatVisitor lv = new LapatVisitor();
            boolean van_lapat = false;
            for (Targy t : targyak) {
                if (t.accept(lv)) {
                    t.hasznal(this);
                    van_lapat = true;
                    return;
                }
            }

            if (!van_lapat && this.getTartozkodasiMezo().getHotakaro() >= 1) {
                this.getTartozkodasiMezo().horetegCsokkent();
                munkakSzama--;
            }
        }
    }


    /**
     * A játékos sátrat épít, ha van neki sátra.
     */
    public void satratEpit() {
        if (!this.getTartozkodasiMezo().isIglu()) {
            SatorVisitor sv = new SatorVisitor();
            for (Targy t : targyak) {
                if (t.accept(sv)) {
                    t.hasznal(this);
                    return;
                }
            }
        }
    }

    /**
     * Törékeny lapátot kitöröl.
     *
     * @param t A törékeny lapát, amit törölni szeretnénk..
     */

    public void lapatTorol(TorekenyLapat t) {
        targyak.remove(t);
    }

    /**
     * Sátrat kitöröl.
     *
     * @param s A sátor, amit törölni szeretnénk..
     */
    public void satratTorol(Sator s) {
        targyak.remove(s);
    }


    /**
     * Beállítja a játékos allapot tagváltozójának értékét fuldoklikra,
     * valamint, ha a játékosnak nincs búvárruhája lecsökkenti a elvégezhető munkák számát
     * (munkakSzama tagváltozó) nullára, hogy a következő játékos jöjjön. Beállítja a beszakadt mezőn levő
     * befagyott tárgy/alkatrész és a rá letett alkatrész értékét nullra.
     */
    public void vizbeEsik() {
        Mezo m = this.getTartozkodasiMezo();
        m.setTeherbiras(0); //beállítjuk a mező teherbírását 0-ra, mert akkor már lyuknak minősül, aki odalép beleesik.
        m.setFagyottTargy(null);
        m.setFagyottAlk(null);
        m.setAlkatreszek(null, null, null);
        m.setIglu(false);
        m.setSatorMiotaVan(0);
        if (!vedett) {
            allapot = FulladasiAllapot.fuldoklik;
            munkakSzama = 0;
        }
        //ha védett nem történik semmi
    }

    /**
     * Ellenőrzi, hogy az adott mezőn van-e mindhárom alkatrész,
     * és ha igen a játékos összeszereli és elsüti a jelzőrakétát
     */
    public void osszeszerel() {
        ArrayList<Alkatresz> alkatreszek = this.getTartozkodasiMezo().getAlkatreszek();
        if (alkatreszek != null) {
            if (alkatreszek.size() == 3) {
                this.elsut();
            }
        }
    }

    /**
     * Lerak egy alkatrészt a mezőre hogy azt majd el lehessen sütni
     */
    public void lerak() {

            if (alkatreszek.size() > 0) {
                Alkatresz alk = this.alkatreszek.remove(0);
                Mezo m=this.getTartozkodasiMezo();
                m.alkatreszNovel(alk);
                kontroller.frissitLerak(this,m);
            }
            //this.getTartozkodasiMezo().alkatreszNovel(null);

    }

    /**
     * Csökkenti a hátramaradó munkát számát egyel.
     */
    public void munkaLevon() {
        munkakSzama--;
    }

    /**
     * Ez a függvény az összeszerelés után automatikusan hívódik,
     * elsüti a rakétát és  véget vet a játéknak
     */
    public void elsut() {
        kontroller.jatekVege(true);
    }

    /**
     * Visszadaj a játékosnál lévő alkatrészeket
     *
     * @return
     */
    public ArrayList<Alkatresz> getAlkatreszek() {
        return this.alkatreszek;
    }

    /**
     * Az eszkimó valósítja meg
     */
    public abstract void epit();

    /**
     * visszaadja a játékos fulladási állapotát
     *
     * @return FulladasiAllapot a játékos aktuális fulladási állapota
     */

    public FulladasiAllapot getAllapot() {
        return allapot;
    }

    /**
     * setter
     */
    public void setAllapot(FulladasiAllapot all) {
        allapot = all;
    }

    /**
     * A kutató valósíja meg
     *
     * @param i Az adott irányban vizsgálja a mezőt
     * @return
     */
    public int vizsgal(Irany i) {
        return -1;
    }

    /**
     * Visszaadja, hogy egy játékos védett-e.
     */
    public boolean isVedett() {
        return vedett;
    }

    /**
     * Visszaadja a játékos megmaradt munkáinak számát.
     */
    public int getMunka(){
        return munkakSzama;
    }
}
