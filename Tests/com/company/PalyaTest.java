package com.company;


import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PalyaTest {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";
    private boolean konzol = true;

    PalyaTest() {
        this.konzol = true;
    }


    /**
     * A teszt során nem lehet megfulladni a 0 teherbírású mezőkön
     * A munkák számt nem vesszük figyelembe, egy látékos beármennyi dolgot megcsinálhat
     */
    @Test
    public void jatekTest() {
        Kontroller kontroller = new Kontroller();
        Parser parser = new Parser();

        try {
            parser.loadPalya(kontroller, "palya.json");
            Jatekos kutato1 = kontroller.getJatekosok().get(1);

            // Olyan irányba lép amerre nem lehet
            kutato1.lep(Irany.Le);
            try {
                assertEquals(kutato1.getTartozkodasiMezo(), kontroller.palya.get(11));
                System.out.println(ANSI_GREEN + "Siker" + ANSI_RESET);
                kutato1.state();
            } catch (AssertionFailedError e) {
                System.out.println(ANSI_RED + "Fail: Nem lépett át a játékos" + ANSI_RESET);
            }
            //Olyan irányba lép amerre lehet (8-as mező)
            kutato1.lep(Irany.BalFel);
            try {
                assertEquals(kutato1.getTartozkodasiMezo(), kontroller.palya.get(8));
                System.out.println(ANSI_GREEN + "Siker" + ANSI_RESET);
                kutato1.state();
            } catch (AssertionFailedError e) {
                System.out.println(ANSI_RED + "Fail: A játékos rossz helyre lépett" + ANSI_RESET);
            }

            // Mivel nincs hótakaró a pálya egyik mezőjén sem fel tudja venni rögtön
            // Felveszi a lapátot
            kutato1.kapar();
            try {
                assertNotEquals(kutato1.getTargyak().size(), 0);
                System.out.println(ANSI_GREEN + "Siker" + ANSI_RESET);
                kutato1.getTartozkodasiMezo().state();

            } catch (AssertionFailedError e) {
                System.out.println(ANSI_RED + "Fail: A Játékos nem tudta felvenni az ásót" + ANSI_RESET);
            }

            // kutato1 lapátol egyet és ellenőrzi, hogy jól lapátol e a kutato1
            int hotakaro = kutato1.getTartozkodasiMezo().getHotakaro();
            int newHotakaro = Math.max(hotakaro - 2, 0);
            kutato1.lapatol();
            try {

                assertEquals(kutato1.getTartozkodasiMezo().getHotakaro(), newHotakaro);
                System.out.println(ANSI_GREEN + "Siker" + ANSI_RESET);
                kutato1.getTartozkodasiMezo().state();
            } catch (AssertionFailedError e) {
                System.out.println(ANSI_RED + "Fail: A hótakaró rosszul vonódik le" + ANSI_RESET);
            }

            // Megvizsgálja a kutato1 a fölötte és a jobbra fölötte levő mező teherbírását
            int teherbiras = kutato1.vizsgal(Irany.Fel);
            try {
                assertEquals(teherbiras, -1);
                System.out.println(ANSI_GREEN + "Siker" + ANSI_RESET);
                System.out.print("Tartozkodasi mezo: ");
                kutato1.getTartozkodasiMezo().state();
                System.out.print("Vizsgalt mezo: ");
                try {
                    kutato1.getTartozkodasiMezo().getSzomszed(Irany.Fel).state();
                } catch (NullPointerException e) {
                    System.out.println("Nincs szomszéd ilyen irányban");
                }

            } catch (AssertionFailedError e) {
                System.out.println(ANSI_RED + "Fail: A kapott teherbírás érték nem megfelelő" + ANSI_RESET);
            }

            teherbiras = kutato1.vizsgal(Irany.JobbFel);
            try {
                assertNotEquals(teherbiras, -1);
                System.out.println(ANSI_GREEN + "Siker" + ANSI_RESET);
                System.out.print("Tartozkodasi mezo: ");
                kutato1.getTartozkodasiMezo().state();
                System.out.print("Vizsgalt mezo: ");
                try {
                    kutato1.getTartozkodasiMezo().getSzomszed(Irany.JobbFel).state();
                } catch (NullPointerException e) {
                    System.out.println("Nincs szomszéd ilyen irányban");
                }

            } catch (AssertionFailedError e) {
                System.out.println(ANSI_RED + "Fail: A kapott teherbírás érték nem megfelelő" + ANSI_RESET);
            }

            // A kutató belelép egy lyukba és, megnézi fuldoklik e
            // ( Eddig mivel nem foglalkoztunk a teherbírással már máshol is elkezfhetett fuldokolni, de ez nem probléma
            kutato1.lep(Irany.Jobb);
            try {
                assertEquals(kutato1.getAllapot(), FulladasiAllapot.fuldoklik);
                System.out.println(ANSI_GREEN + "Siker" + ANSI_RESET);
                kutato1.state();
            } catch (AssertionFailedError e) {
                System.out.println(ANSI_RED + "Fail: A játéékos nem esett a lyukba" + ANSI_RESET);
            }

            //
            // Kicsit módosult a pálya betöltése, az eszkimó mezőjén van kötél, hogy a kihúzást is ki tudja próbálni
            //
            // Ellenőrzése hogy jó helyre került -e létrehozáskor az eszkimo1
            Jatekos eszkimo1 = kontroller.getJatekosok().get(0);
            //Nulla a hóréteg felveheti
            eszkimo1.kapar();
            try {
                assertEquals(kontroller.getPalya(5), eszkimo1.getTartozkodasiMezo());
                System.out.println(ANSI_GREEN + "Siker, az eszkimó jó helyre került" + ANSI_RESET);
            } catch (AssertionFailedError e) {
                System.out.println(ANSI_RED + "Fail: Nem jó helyen áll" + ANSI_RESET);
            }

            // eszkimo1 karaktere kihúzza kutato1-et
            eszkimo1.kihuz(Irany.BalLe);
            try {
                assertTrue(eszkimo1.getTartozkodasiMezo().getAlloJatekos().size() > 1);
                System.out.println(ANSI_GREEN + "Siker, az eszkimó kihúzott valakit" + ANSI_RESET);
            } catch (AssertionFailedError e) {
                System.out.println(ANSI_RED + "Fail: Nem húzott ki senkit" + ANSI_RESET);
            }

            // Lapátol egyet és ellenőrzi, hogy jól lapátolt e az eszkimó
            int oldSnow = eszkimo1.getTartozkodasiMezo().getHotakaro();
            eszkimo1.lapatol();
            try {
                if (oldSnow == 0) {
                    assertTrue(eszkimo1.getTartozkodasiMezo().getHotakaro() == 0);
                    System.out.println(ANSI_GREEN + "Siker, a hótakaró 0 volt annyi is maradt" + ANSI_RESET);
                } else {
                    assertTrue(eszkimo1.getTartozkodasiMezo().getHotakaro() < oldSnow);
                    System.out.println(ANSI_GREEN + "Siker, a hótakaró nem 0 volt és kevesebb is lett" + ANSI_RESET);
                }
            } catch (AssertionFailedError e) {
                System.out.println(ANSI_RED + "Fail: Nem jó a lapátolás" + ANSI_RESET);
            }

            // Átlép a 12-es mezőre az eszkimó
            eszkimo1.lep(Irany.Le);
            try {
                assertEquals(kontroller.getPalya(12), eszkimo1.getTartozkodasiMezo());
                System.out.println(ANSI_GREEN + "Siker, az eszkimó lefele lépett" + ANSI_RESET);
            } catch (AssertionFailedError e) {
                System.out.println(ANSI_RED + "Fail: Nem lépett lefele az eszkimó" + ANSI_RESET);
            }

            // Iglut épít és Megnézi felépül e az iglu a 12-es mezőn
            eszkimo1.epit();

            try {
                assertTrue(kontroller.getPalya(12).isIglu());
                System.out.println(ANSI_GREEN + "Siker, az eszkimó megépítette az iglut" + ANSI_RESET);
            } catch (AssertionFailedError e) {
                System.out.println(ANSI_RED + "Fail: Nem lépett lefele az eszkimó" + ANSI_RESET);
            }
            //eszkimo1 olyan helyre próbál,lépni amilyen sziomszéd nincs
            eszkimo1.lep(Irany.Le);
            try {
                assertEquals(kontroller.getPalya(12), eszkimo1.getTartozkodasiMezo());
                System.out.println(ANSI_GREEN + "Siker, nem lépett lehetetlen mezőre" + ANSI_RESET);
            } catch (AssertionFailedError e) {
                System.out.println(ANSI_RED + "Fail: Nem ott van ahol volt" + ANSI_RESET);
            }

            //13as mezőről a kutató lép, aztán kapar de ott nincs tárgy, továbblép és megint kapar ott van egy alkatrész
            Jatekos kutato2 = kontroller.getJatekosok().get(2);
            kutato2.lep(Irany.Bal);
            try {
                assertEquals(kutato2.getTartozkodasiMezo(), kontroller.palya.get(11));
                System.out.println(ANSI_GREEN + "Siker" + ANSI_RESET);
                kutato2.state();
            } catch (AssertionFailedError e) {
                System.out.println(ANSI_RED + "Fail: Nem jó helyre lép" + ANSI_RESET);
            }
            kutato2.kapar();
            try {
                assertTrue(kutato2.getTargyak().size() == 0);
                System.out.println(ANSI_GREEN + "Siker" + ANSI_RESET);
                kutato2.state();
            } catch (AssertionFailedError e) {
                System.out.println(ANSI_RED + "Fail" + ANSI_RESET);
            }
            kutato2.lep(Irany.BalFel);
            try {
                assertEquals(kutato2.getTartozkodasiMezo(), kontroller.palya.get(8));
                System.out.println(ANSI_GREEN + "Siker" + ANSI_RESET);
                kutato2.state();
            } catch (AssertionFailedError e) {
                System.out.println(ANSI_RED + "Fail: Nem jó helyre lép" + ANSI_RESET);
            }
            kutato2.kapar();
            try {
                assertTrue(kutato2.getTargyak().size() == 0);
                System.out.println(ANSI_GREEN + "Siker" + ANSI_RESET);
                kutato2.state();
            } catch (AssertionFailedError e) {
                System.out.println(ANSI_RED + "Fail" + ANSI_RESET);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

