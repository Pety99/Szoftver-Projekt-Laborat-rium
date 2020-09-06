package com.company;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

// konstruktorban kapja meg a játékosokat. Akkor tud a kontroller osztályra referenciát tartalmazni a játékos osztály
public class Kontroller implements ActionListener {
    /**
     * Egy segéd objektum ami Listener-ek listáját kezeli, és PropertyChangeEvent-eket küld nekik.
     * Ezek a PropertyChangeLister-ek regisztrálhatnak egy bizonyos nevű attribútum/property -re, vagy
     * akár az összesre is.
     */
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private final ArrayList<Jatekos> jatekosok = new ArrayList<>();
    protected ArrayList<Mezo> palya = new ArrayList<>();
    boolean nyert = false;
    AtomicBoolean aktiv = new AtomicBoolean(true);
    ArrayList<View> views = new ArrayList<>();
    MouseListener mouseListener;
    private volatile Jatekos aktivJatekos;
    private Jegesmedve jegesmedve = new Jegesmedve();
    private volatile Irany kihuzIrany = null;
    private volatile Irany vizsgalIrany = null;

    Kontroller() {
    }

    /**
     * Visszaadja a mouseListenert
     *
     * @return
     */
    public MouseListener getMouseListener() {
        return mouseListener;
    }

    /**
     * Hozzáad egy PropertyChangeListener-t a kontrollerhez, ami egy bizonyos property változása esetén
     * valamilyen műveletet hajt végre.
     * (A programunkban ezt a modell változásának megfigyelésére fogjuk használni)
     *
     * @param listener A Listener ami valamilyen property változásra reagál.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * Eltávolít egy PropertyChangeListener-t a kontroller Listener-jei közűl.
     *
     * @param listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    /**
     * Visszadja a jelenleg aktív játékost.
     *
     * @return Visszadja a jelenleg aktív játékost.
     */
    public Jatekos getAktivJatekos() {
        return aktivJatekos;
    }

    /**
     * Beállít aktív játékosnak egy új játékost,
     * értesíti a View-t, hogy megváltozott a játékos és frissíteni kell magát
     *
     * @param ujAktivJatekos Az új aktív játékos akit be akarunk állítani
     */
    public void setAktivJatekos(Jatekos ujAktivJatekos) {
        Jatekos regiJatekos = aktivJatekos;
        this.aktivJatekos = ujAktivJatekos;
        support.firePropertyChange("aktivJatekos", null, aktivJatekos);
    }

    /**
     * Hozzáad egy új nézetet a kontroller nézeteihez, és felveszi hozzá a mod kontrollerillenytű eseménykezelőjét.
     *
     * @param view Egy új nézet ami hozzáad a kontroller nézeteihez.
     */
    public void addView(View view) {
        KeyboardListener keyboardListener = new KeyboardListener();
        view.addKeyListener(keyboardListener);
        this.views.add(view);
        //Amikor hozzáadjuk a nézetet az jelenjen is meg rögtön
        support.firePropertyChange("palya", 0, palya);
        support.firePropertyChange("aktiv mezo", null, aktivJatekos.getTartozkodasiMezo());
    }

    /**
     * Visszaadja az i-dik mezőt
     *
     * @param i, hanyadik mező
     * @return a mező
     */
    public Mezo getPalya(int i) {
        return palya.get(i);
    }

    /**
     * A játék menete, minden játékos köre előtt detektálás van, utána pedig vihar
     */
    public void jatek() {
        try {

            while (aktiv.get()) {
                for (Jatekos j : jatekosok) {
                    this.setAktivJatekos(j);
                    detektal();
                    j.jatszik();
                    if (j.getAllapot() == FulladasiAllapot.aktiv)
                        j.setMunkakSzama(4);
                }

                ArrayList<Mezo> regiPalya = new ArrayList<>();
                for (Mezo m : palya) {
                    regiPalya.add((Mezo) m.clone());
                }
                vihar();
                //A vihar után az egész pályát újra kell rajzolni
                support.firePropertyChange("palya", regiPalya, palya);

                Jegesmedve regimedve = (Jegesmedve) jegesmedve.clone();

                jegesmedve.jatszik();

                support.firePropertyChange("mezo", null, jegesmedve.getTartozkodasiMezo());
                // Ahol előtte állt
                support.firePropertyChange("mezo", null, regimedve.getTartozkodasiMezo());
            }


        } catch (CloneNotSupportedException cloneNotSupportedException) {
            cloneNotSupportedException.printStackTrace();
        }
    }

    /**
     * Hozzáad egy játékost a játékhoz
     *
     * @param j a játékos
     */
    public void addJatekos(Jatekos j) {
        jatekosok.add(j);
    }

    /**
     * Hozzáad egy mezőt q játékhoz
     *
     * @param mezo a mező
     */
    public void addMezo(Mezo mezo) {
        this.palya.add(mezo);
    }

    /**
     * Végig megy az összes mezőn és megnöveli a hóréteget egy random számmal (negatív lineáris eloszlással)
     * 0 és a maxHoreteg kozott Között, tehát jóval kisebb a valószínűsége nagy hónak mint a semminek.
     * Lecsökkenti a mezőkön álló játékosok testhőjét, ha nincs iglu a mezőn vagy a sátor már
     */

    public void vihar() {
        int maxHoreteg = 5;

        for (Mezo mezo : palya) {
            //Megnöveljük a hóréteget egy random számmal (negatív lineáris eloszlással) 0 és a maxHoreteg kozott Között
            while (true) {
                double r1 = Math.random();
                double r2 = Math.random();
                if (r2 > r1) {
                    mezo.horetegNovel((int) (r1 * (maxHoreteg + 1)));
                    break;
                }
            }

            int i = mezo.getSatorMiotaVan();
            // ha nincs sátor ill iglu, csökken a testhő
            if (!mezo.isIglu()) {
                if (mezo.getSatorMiotaVan() == 0) {
                    for (Jatekos j : mezo.getAlloJatekos()) {
                        j.setTestho(j.getTestho() - 1);
                    }
                }
            }

        }
    }

    /**
     * Vizsgaljuk a játékosok állapotát, nem e hűlt ki, nem e fulladt meg
     */
    public void detektal() {
        int alkatreszSzam = 0;

        for (Jatekos j : jatekosok) {
            FulladasiAllapot allapot = j.getAllapot();
            if (allapot == FulladasiAllapot.fuldoklik) {
                j.setAllapot(FulladasiAllapot.kimentheto);
            } else {
                if (allapot == FulladasiAllapot.kimentheto) {
                    j.setAllapot(FulladasiAllapot.halott);
                    System.out.println("Megfulladtál.");
                    //j.meghal();
                    jatekVege(false);
                }
            }
        }

        for (Jatekos j : jatekosok) {
            int ho = j.getTestho();

            if (ho == 0) {
                j.setAllapot(FulladasiAllapot.halott);
                jatekVege(false);
            }
        }

        for (Jatekos j : jatekosok) {
            ArrayList<Alkatresz> alkatreszek = j.getAlkatreszek();
            alkatreszSzam += alkatreszek.size();
        }
        for (Mezo m : palya) {
            ArrayList<Alkatresz> alkatreszek = m.getAlkatreszek();
            String id = m.getID();
            if (id.charAt(0) == 'J') {


                if (alkatreszek != null) {
                    alkatreszSzam += alkatreszek.size();
                }
                if (m.getFagyottAlkatresz() != null) {
                    alkatreszSzam++;
                }
            }
        }

        if (alkatreszSzam != 3) {
            System.out.println("Nincs meg az összes alkatrész.");
            jatekVege(false);
        }


        for (Mezo m : palya) {
            int satorMiotaVan = m.getSatorMiotaVan();
            if (satorMiotaVan == ((jatekosok.size()))) { // ha lement egy kör eltűnteti a sátrat
                m.satratNullaz();
            } else {
                if (m.getSatorMiotaVan() > 0) // ha van a mezőn sátor
                    m.satorIdoNovel();  // 1-gyel nő a felállítástúl eltelt idő
            }
        }
    }

    public void frissitLerak(Jatekos aktivJatekos, Mezo mezo) {
        support.firePropertyChange("aktivJatekos", null, aktivJatekos);
        support.firePropertyChange("aktiv mezo", null, mezo);
    }

    /**
     * Véget vet a játéknak
     *
     * @param nyer true érték esetén nyeréssel, false esetén vesztéssel ér véget a játék
     */
    //TODO
    // Fire "vege" meg kell jeleníteni a vége képernyőt
    public void jatekVege(boolean nyer) {
        aktiv.set(false);

        View view = this.views.get(0);
        synchronized (view) {
            if (nyer) {
                view.setText("Nyertél!");
                nyert = true;
            } else
                view.setText("Vesztettél");
            CardLayout cl = (CardLayout) view.getContentPane().getLayout();
            cl.show(view.getContentPane(), "vegeView");
        }
    }

    /**
     * getter
     *
     * @return a játékban részt vevő játékosok listája
     */
    public ArrayList<Jatekos> getJatekosok() {
        return jatekosok;
    }

    /**
     * setter a jegesmedvéhez
     *
     * @param jegesmedve a játék jegesmedvéje
     */
    public void setJegesmedve(Jegesmedve jegesmedve) {
        this.jegesmedve = jegesmedve;
    }

    public ArrayList<Mezo> getPalya() {
        return this.palya;
    }

    /**
     * Visszadja az akív játékos paraméterként kapott irányában a tartózkodási mező szomszédjának másolatát
     *
     * @param i az irány amerrer a mezőt visszadja
     * @return Az aktív szomszéd másolatát
     */
    private Mezo copySzomszed(Irany i) {
        Mezo m = aktivJatekos.getTartozkodasiMezo().getSzomszed(i);
        return m;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        // Ez az állapota a cselekvés előtt a Játékosnak, mindegy, hogy mennyire Deep a másolat,
        // Csak az számít, hogy ne legyen azonos a két objektum, és akkor felül lesz írva
        if (actionEvent.getActionCommand() == "vege") System.exit(0);
        if (actionEvent.getActionCommand() == "ujjatek") {
            try {
                ujrakezd();
            } catch (IOException e) {
                e.printStackTrace();
            }


        } else {
            try {
                Jatekos regiJatekos = (Jatekos) aktivJatekos.clone();

                //Az a mező lesz ahová megérkezik majd az aktív játékos a lépés után
                Mezo ujTarozkodasiMezo;

                Mezo regiTartozkodasiMezo = (Mezo) aktivJatekos.getTartozkodasiMezo().clone();
                String actionCommand = actionEvent.getActionCommand();


                Mezo regiszomszed = null;

                //Az a mező amiről el kell tűnjenek a  kihúzott játékosok
                Mezo ujSzomszed = null;

                // Az irányok amerre majd vizsgálni vagy kihúzni kell ha nem null -ok.
                kihuzIrany = Irany.StringToIrany(actionCommand);

                // Ha nincs szóköz és nem tudja elválasztani akkor biztos nem vizsgálás volt tehát null lesz az irány.
                String[] parts = actionCommand.split(" ");
                try {
                    vizsgalIrany = Irany.StringToIrany(parts[1]);
                } catch (ArrayIndexOutOfBoundsException ex) {
                    vizsgalIrany = null;
                }

                /**
                 * Kihúz a megfelelő irányba
                 */
                if (kihuzIrany != null) {
                    ujSzomszed = copySzomszed(kihuzIrany);
                    //TODO itt exceptiont dob
                    if(aktivJatekos.getTartozkodasiMezo().getSzomszed(kihuzIrany) != null) {
                        regiszomszed = (Mezo) aktivJatekos.getTartozkodasiMezo().getSzomszed(kihuzIrany).clone();
                        aktivJatekos.kihuz(kihuzIrany);
                    }


                    if(regiszomszed != null) {
                        support.firePropertyChange("mezo", regiszomszed, ujSzomszed);
                    }
                    support.firePropertyChange("aktivJatekos", regiJatekos, aktivJatekos);
                    support.firePropertyChange("mezo", regiTartozkodasiMezo, regiJatekos.getTartozkodasiMezo());
                    support.firePropertyChange("aktiv mezo", regiTartozkodasiMezo, aktivJatekos.getTartozkodasiMezo());
                }
                /**
                 * Megvizsgálja a mezőt a megfelelő irányban
                 */
                else if (vizsgalIrany != null) {
                    Mezo vizsgaltMezo = copySzomszed(vizsgalIrany);
                    aktivJatekos.vizsgal(vizsgalIrany);
                    if(vizsgaltMezo != null) {
                        support.firePropertyChange("mezo", null, vizsgaltMezo);
                    }
                }

                /**
                 * Ha nem vizsgálás vagy kihúzás történéik akkor a többi, ezeket invokeolni lehet név alapján
                 */
                else {
                    Class c = aktivJatekos.getClass();
                    Method m = c.getMethod(actionCommand);
                    m.invoke(aktivJatekos);
                }

                if (kihuzIrany == null) {
                    support.firePropertyChange("aktivJatekos", regiJatekos, aktivJatekos);
                    support.firePropertyChange("mezo", regiTartozkodasiMezo, regiJatekos.getTartozkodasiMezo());
                    support.firePropertyChange("aktiv mezo", regiTartozkodasiMezo, aktivJatekos.getTartozkodasiMezo());
                }
                for (View v : views) {
                    v.requestFocusInWindow();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void ujrakezd() throws IOException {

        View view = this.views.get(0);
        synchronized (view) {
            CardLayout cl = (CardLayout) view.getContentPane().getLayout();
            cl.show(view.getContentPane(), "panel");
        }
        synchronized (this){
            aktiv.set(false);
            jatekosok.clear();
            palya.clear();
            Parser parser = new Parser();
            parser.loadPalya(this, "palya.json");
            view.dispose();
            this.views.clear();
            View v = new View(this);
            addView(v);

            nyert = false;
            //aktiv.set(false);
            kihuzIrany = null;
            vizsgalIrany = null;
        }

    }

    /**
     * A billenytűk eseménykezelőjének belső osztálya.
     */
    class KeyboardListener implements KeyListener {

        /**
         * Nem csinál semmit
         *
         * @param e Az esemény
         */
        @Override
        public void keyTyped(KeyEvent e) {
        }

        /**
         * A billenytű lenyomások kezelő függvénye.
         * Egy billenytű lenyomásra végrehajt egy műveletet az aktív játékoson, majd értesíti a nézeteket,
         * hogy azok tudják frissíteni magukat.
         *
         * @param e A lenyomott billenytű KeyEvent-je
         */
        @Override
        public void keyPressed(KeyEvent e) {

            try {

                // Ez az állapota a cselekvés előtt a Játékosnak, mindegy, hogy mennyire Deep a másolat,
                // Csak az számít, hogy ne legyen azonos a két objektum, és akkor felül lesz írva
                Jatekos regiJatekos = (Jatekos) aktivJatekos.clone();


                //Az a mező lesz ahová megérkezik majd az aktív játékos a lépés után
                Mezo ujTarozkodasiMezo;

                Mezo regiTartozkodasiMezo = (Mezo) aktivJatekos.getTartozkodasiMezo().clone();

                if (e.getKeyCode() == (KeyEvent.VK_NUMPAD8) || e.getKeyCode() == KeyEvent.VK_UP) {
                    ujTarozkodasiMezo = copySzomszed(Irany.Fel);
                    aktivJatekos.lep(Irany.Fel);
                } else if (e.getKeyCode() == (KeyEvent.VK_NUMPAD9) || e.getKeyCode() == KeyEvent.VK_E) {
                    ujTarozkodasiMezo = copySzomszed(Irany.JobbFel);
                    aktivJatekos.lep(Irany.JobbFel);
                } else if (e.getKeyCode() == (KeyEvent.VK_NUMPAD6) || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    ujTarozkodasiMezo = copySzomszed(Irany.Jobb);
                    aktivJatekos.lep(Irany.Jobb);
                } else if (e.getKeyCode() == (KeyEvent.VK_NUMPAD3) || e.getKeyCode() == KeyEvent.VK_X) {
                    ujTarozkodasiMezo = copySzomszed(Irany.JobbLe);
                    aktivJatekos.lep(Irany.JobbLe);
                } else if (e.getKeyCode() == (KeyEvent.VK_NUMPAD2) || e.getKeyCode() == KeyEvent.VK_DOWN) {
                    ujTarozkodasiMezo = copySzomszed(Irany.Le);
                    aktivJatekos.lep(Irany.Le);
                } else if (e.getKeyCode() == (KeyEvent.VK_NUMPAD1) || e.getKeyCode() == KeyEvent.VK_Y) {
                    ujTarozkodasiMezo = copySzomszed(Irany.BalLe);
                    aktivJatekos.lep(Irany.BalLe);
                } else if (e.getKeyCode() == (KeyEvent.VK_NUMPAD4) || e.getKeyCode() == KeyEvent.VK_LEFT) {
                    ujTarozkodasiMezo = copySzomszed(Irany.Bal);
                    aktivJatekos.lep(Irany.Bal);
                } else if (e.getKeyCode() == (KeyEvent.VK_NUMPAD7) || e.getKeyCode() == KeyEvent.VK_Q) {
                    ujTarozkodasiMezo = copySzomszed(Irany.BalFel);
                    aktivJatekos.lep(Irany.BalFel);
                } else {
                    return;
                }

                //Frissíteni kell a View Aktív Játékosát
                support.firePropertyChange("aktivJatekos", regiJatekos, aktivJatekos);

                // Frissíteni kell:
                // Ahová megérkezett
                if (ujTarozkodasiMezo != null)
                    support.firePropertyChange("mezo", null, ujTarozkodasiMezo);
                // Ahol előtte állt
                support.firePropertyChange("mezo", regiTartozkodasiMezo, regiJatekos.getTartozkodasiMezo());
                //Ahol az új játékos áll
                support.firePropertyChange("aktiv mezo", null, aktivJatekos.getTartozkodasiMezo());

            } catch (CloneNotSupportedException cloneNotSupportedException) {
                cloneNotSupportedException.printStackTrace();
            }
        }

        /**
         * Nem csinál semmit
         *
         * @param e
         */
        @Override
        public void keyReleased(KeyEvent e) {
        }
    }
}