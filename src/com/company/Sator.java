package com.company;

public class Sator extends Targy{

    public Sator() {
    }
    /**
     * A függvény a kapott TargyVisitor visit(this) függvényét meghívja.
     * @param v a TargyVisitor, akinek a visit függvényét meghívja.
     * @return Amennyiben a paraméterül kapott TargyVisitor SatorVisitor,
     * úgy a függvény visszatérési értéke true, ha bármi más TargyVisitor leszármazott, a visszatérési értéke false.
     */
    @Override
    public boolean accept(TargyVisitor v) {
        return v.visit(this);
    }

    /**
     * A tárgy felvétele során (amennyiben a kikapart tárgy sátor) a
     * this-t paraméterül adja a játékos SatorFelvesz(Sator s) függvényének.
     * @param j a sátrat kikaparó játékos.
     */
    @Override
    public void felvesz(Jatekos j) {
        j.satorFelvesz(this);
    }


    /**
     * A paraméterül kapott játékos tartózkodási mezőjére épít egy sátrat és ha sikerül felépíteni (nem lyuk a mező)
     * csökkenti a játékos munkáinak számát 1-gyel.
     * @param j ez a játékos állít sátrat.
     */
    @Override
    public void hasznal(Jatekos j){
        if (j.getTartozkodasiMezo().getTeherbiras()!=0) {
            j.getTartozkodasiMezo().satorIdoNovel();
            j.satratTorol(this);
            j.munkaLevon();
        }



    }


}
