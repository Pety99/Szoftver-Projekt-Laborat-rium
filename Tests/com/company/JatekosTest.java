package com.company;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.opentest4j.AssertionFailedError;

import static org.junit.jupiter.api.Assertions.*;

class JatekosTest {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";
    private boolean konzol = true;

    JatekosTest() {
        this.konzol = true;
    }

    /**
     * Az eszkimó iglut épít a mezőre, ellenőrzi megjelenik e az iglu.
     */
    @Test
    public void epit() {
        Kontroller k=new Kontroller();
        Mezo m = new Jegtabla(4, 4, null);
        Jatekos j = new Eszkimo(k, 0);
        j.setTartozkodasiMezo(m);

        j.epit();

        try {
            assertTrue(j.getTartozkodasiMezo().isIglu());
            System.out.println(ANSI_GREEN + "Siker" + ANSI_RESET);
            m.state();
        } catch (AssertionFailedError e) {
            System.out.println(ANSI_RED + "Fail: Nem épül iglu" + ANSI_RESET);
        }
    }

    /**
     *
     * @param irany
     */
    @ParameterizedTest
    @ValueSource(strings = {"Jobb", "Bal", "Fel", "Le", "JobbFel", "JobbLe", "BalFel", "BalLe" })
    public void lep(String irany) {
        Kontroller k=new Kontroller();
        Irany i = Irany.StringToIrany(irany);
        Jatekos j = new Eszkimo(k, 1);
        Mezo m = new Jegtabla("kezdomezo", 4, 4, null);
        Mezo m2 = new Jegtabla("szomszed " + irany, 4, 4, null);
        m.szomszedok.put(i, m2);
        j.setTartozkodasiMezo(m);
        j.lep(i);

        try {
            assertFalse(m2.getAlloJatekos().isEmpty());
            System.out.println(ANSI_GREEN + "Siker" + ANSI_RESET);
            assertTrue(m.getAlloJatekos().isEmpty());
            System.out.println(ANSI_GREEN + "Siker" + ANSI_RESET);
            j.state();
        } catch (AssertionFailedError e) {
            System.out.println(ANSI_RED + "Fail: Nem lépett át a játékos" + ANSI_RESET);
        }
    }

    @Test
    public void lyukraLep() {
        String iranyString = "Fel";
        Irany irany = Irany.StringToIrany(iranyString);
        Kontroller k=new Kontroller();
        Jatekos j = new Eszkimo(k, 0);
        j.setAllapot(FulladasiAllapot.aktiv);
        Mezo m = new Jegtabla("kezdomezo", 4, 4, null);
        Mezo m2 = new Lyuk("szomszed " + iranyString, 0);
        m.szomszedok.put(irany, m2);
        j.setTartozkodasiMezo(m);

        j.lep(irany);
        try {
            assertEquals(j.getAllapot(), FulladasiAllapot.fuldoklik);
            System.out.println(ANSI_GREEN + "Siker" + ANSI_RESET);
            j.state();
        } catch (AssertionFailedError e) {
            System.out.println(ANSI_RED + "Fail: Nem fuldoklik a játékos" + ANSI_RESET);
        }

        //Visszaállítjuk
        j.setMezo(m);
        j.setAllapot(FulladasiAllapot.aktiv);
        j.buvarruhaFelvesz(new Buvarruha());
        j.lep(irany);
        try {
            assertEquals(j.getAllapot(), FulladasiAllapot.aktiv);
            System.out.println(ANSI_GREEN + "Siker" + ANSI_RESET);
            j.state();
        } catch (AssertionFailedError e) {
            System.out.println(ANSI_RED + "Fail: Fuldoklik a játékos" + ANSI_RESET);
        }
    }


    @Test
    public void lapatolTest(){
        Kontroller k=new Kontroller();
        Eszkimo eszkimo=new Eszkimo(k,0 );
        Kutato kutato=new Kutato(k, 1);
        Jegtabla j1=new Jegtabla(5,4,new Lapat());
        Jegtabla j2=new Jegtabla(5,4,new Buvarruha());
        Jegtabla j3=new Jegtabla(5,1,new Buvarruha());
        eszkimo.setTartozkodasiMezo(j1);
        kutato.setTartozkodasiMezo(j2);
        Lapat l=new Lapat();
        eszkimo.lapatFelvesz(l);
        j1.elfogad(eszkimo);
        j2.elfogad(kutato);
        eszkimo.lapatol();
        kutato.lapatol();
        try {
            assertEquals(2,j1.getHotakaro());
            System.out.println(ANSI_GREEN + "Siker, amikor van lapát" + ANSI_RESET);
            assertEquals(3,j2.getHotakaro());
            System.out.println(ANSI_GREEN + "Siker, amikor nincs lapát" + ANSI_RESET);
        } catch (AssertionFailedError e) {
            System.out.println(ANSI_RED + "Fail: Nincs jól beállítva a lapátolás" + ANSI_RESET);
        }
        eszkimo.setTartozkodasiMezo(j3);
        j3.elfogad(eszkimo);
        eszkimo.lapatol();
        try {
            assertEquals(0,j3.getHotakaro());
            System.out.println(ANSI_GREEN + "Siker, amikor van lapát és 1 a hóréteg" + ANSI_RESET);
        } catch (AssertionFailedError e) {
            System.out.println(ANSI_RED + "Fail: Nincs jól beállítva amikor van lapát és 1 a hóréteg" + ANSI_RESET);
        }
    }
    @Test
    public void kihuzTest(){
        Jegtabla jegtabla=new Jegtabla(5,4,new Buvarruha());
        Lyuk lyuk=new Lyuk(0);
        Eszkimo megmento=new Eszkimo();
        Eszkimo fulldoklo=new Eszkimo();
        Kotel k=new Kotel();
        megmento.kotelFelvesz(k);
        megmento.setTartozkodasiMezo(jegtabla);
        jegtabla.elfogad(megmento);
        fulldoklo.setTartozkodasiMezo(lyuk);
        lyuk.elfogad(fulldoklo);
        jegtabla.szomszedok.put(Irany.Jobb,lyuk);
        lyuk.szomszedok.put(Irany.Bal,jegtabla);
        megmento.kihuz(Irany.Jobb);
        try {
            assertTrue(megmento.getTartozkodasiMezo()==jegtabla);
            assertTrue(jegtabla==fulldoklo.getTartozkodasiMezo());
            System.out.println(ANSI_GREEN + "Siker, ugyan azon a mezőn vannak" + ANSI_RESET);
        } catch (AssertionFailedError e) {
            System.out.println(ANSI_RED + "Fail: Nem működik a kihúzó rendszer" + ANSI_RESET);
        }
    }

    @Test
    public void kikaparTest(){
        Jegtabla jegtabla=new Jegtabla(5,0,new Kotel());
        Eszkimo eszkimo=new Eszkimo();
        eszkimo.setTartozkodasiMezo(jegtabla);
        jegtabla.elfogad(eszkimo);
        eszkimo.kapar();
        try{
            assertNotNull(eszkimo.getTargyak());
            assertNull(jegtabla.getTargy());
            System.out.println(ANSI_GREEN + "Siker: A tárgy a játékoshoz került." + ANSI_RESET);
        } catch(AssertionFailedError e){
            System.out.println(ANSI_RED + "Fail: Nem működik a tárgy felvétel" +ANSI_RESET);
        }
    }

    @Test
    public void osszeszerelTest(){
        Kontroller k = new Kontroller();
        Jegtabla jegtabla=new Jegtabla(5,0,null);
        Eszkimo eszkimo=new Eszkimo(k, 0);
        k.addJatekos(eszkimo);
        eszkimo.setTartozkodasiMezo(jegtabla);
        jegtabla.elfogad(eszkimo);
        Alkatresz a1 = new Alkatresz();
        Alkatresz a2 = new Alkatresz();
        Alkatresz a3 = new Alkatresz();
        jegtabla.setAlkatreszek(a1,a2,a3);
        eszkimo.osszeszerel();
        try{
            assertTrue(k.nyert && !k.aktiv.get());
            System.out.println(ANSI_GREEN + "Siker: A pisztoly elsült." + ANSI_RESET);
        } catch(AssertionFailedError e){
            System.out.println(ANSI_RED + "Fail: Nem működik az összeszerelés" +ANSI_RESET);
        }
    }
    @Test
    public void alkatreszFelveszTest(){
        Kontroller k=new Kontroller();
        Jegtabla jegtabla=new Jegtabla(5,0, new Alkatresz());
        Eszkimo eszkimo=new Eszkimo(k, 0);
        k.addJatekos(eszkimo);
        eszkimo.setTartozkodasiMezo(jegtabla);
        jegtabla.elfogad(eszkimo);
        eszkimo.kapar();
        try{
            assertEquals(1, eszkimo.getAlkatreszek().size());
            assertNull(jegtabla.getFagyottAlkatresz());
            System.out.println(ANSI_GREEN + "Siker: Az alkatrész a játékoshoz került." + ANSI_RESET);
        } catch(AssertionFailedError e){
            System.out.println(ANSI_RED + "Fail: Nem működik az alkatrész felvétel" +ANSI_RESET);
        }
    }


    @ParameterizedTest
    @ValueSource(strings = {"jobb", "bal", "fel", "le" })
    public void vizsgalTest(String irany) {
        Kontroller k=new Kontroller();
        Irany i = Irany.StringToIrany(irany);
        Jatekos j = new Kutato(k, 0);
        Mezo m = new Jegtabla(3, 4, null);
        Mezo m2 = new Jegtabla(2, 4, null);
        m.szomszedok.put(i, m2);
        j.setTartozkodasiMezo(m);
        j.vizsgal(i);

        try {
            assertEquals(2, m2.getTeherbiras());
            System.out.println(ANSI_GREEN + "A vizsgáló teherbírás vizsgálatának eredménye helyes" + ANSI_RESET);
        } catch (AssertionFailedError e) {
            System.out.println(ANSI_RED + "Fail: Nem egyezik a szomszéd tábla teherbírásával a kutató vizsgálatának eredménye" + ANSI_RESET);
        }
    }

}