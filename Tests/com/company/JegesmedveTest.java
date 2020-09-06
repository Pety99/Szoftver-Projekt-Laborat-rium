package com.company;

//import com.sun.org.apache.xpath.internal.operations.Bool;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import static org.junit.jupiter.api.Assertions.*;

class JegesmedveTest {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";
    JegesmedveTest(){

    }

    @Test
    public void lep() {
        System.out.println("Jegesmedve lép és nem öl(Nincs tag)");
        Jegesmedve jegesmedve=new Jegesmedve();
        Eszkimo e1=new Eszkimo(new Kontroller(), 0);
        Eszkimo e2=new Eszkimo(new Kontroller(), 0);
        Jegtabla j1=new Jegtabla(4,5,new Kotel());
        Jegtabla j2=new Jegtabla(4,5,new Kotel());
        Jegtabla j3=new Jegtabla(4,5,new Kotel());
        j1.elfogad(jegesmedve);
        jegesmedve.setTartozkodasiMezo(j1);
        j3.elfogad(e1);
        e1.setTartozkodasiMezo(j3);
        j2.elfogad(e2);
        e2.setTartozkodasiMezo(j2);
        j3.setIglu(true);
        j1.szomszedok.put(Irany.Jobb,j2);
        j2.szomszedok.put(Irany.Jobb,j3);

        j2.szomszedok.put(Irany.Bal,j1);
        j3.szomszedok.put(Irany.Bal,j2);
        jegesmedve.lep(Irany.Jobb);
        try {
            assertTrue(jegesmedve.getTartozkodasiMezo()==j2);
            assertEquals(0,j2.getAlloJatekos().size());
            System.out.println(ANSI_GREEN + "Siker, a medve megölte az iglutlan mezőn csapongó eszkimót" + ANSI_RESET);
        } catch (AssertionFailedError e) {
            System.out.println(ANSI_RED + "Fail: Nem működik az ölés" + ANSI_RESET);
        }
        jegesmedve.lep(Irany.Jobb);
        try {
            assertTrue(jegesmedve.getTartozkodasiMezo()==j3);
            assertEquals(1,j3.getAlloJatekos().size());
            System.out.println(ANSI_GREEN + "Siker, a medve nem ölte meg az iglus mezőn álló eszkimót" + ANSI_RESET);
        } catch (AssertionFailedError e) {
            System.out.println(ANSI_RED + "Fail: Nem működik az ölés iglunál" + ANSI_RESET);
        }
    }
}