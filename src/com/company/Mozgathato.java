package com.company;

public abstract class Mozgathato {
    private Mezo tartozkodasiMezo;

    /**
     * Absztrakt függvények, a leszármozottak implementálják
     */
    public abstract void jatszik();
    public abstract void lep(Irany i);

    /**
     * Mező beállítása tartózkodási mezőként.
     *
     * @param m A mező, amire be szereténk állítani a tartózkodási mezőt.
     */
    public void setMezo(Mezo m) {
        this.tartozkodasiMezo = m;

    }

    /**
     * Tartózkodási mező lekérése.
     */
    public Mezo getTartozkodasiMezo() {
        return tartozkodasiMezo;
    }

    /**
     * Tartózkodási mező átállítása.
     * @param tartozkodasiMezo Az a tartózkodási mező, amire be szeretnénk állítani az aktuális tartózkodási mezőt.
     */
    public void setTartozkodasiMezo(Mezo tartozkodasiMezo) {
        this.tartozkodasiMezo = tartozkodasiMezo;
    }

}
