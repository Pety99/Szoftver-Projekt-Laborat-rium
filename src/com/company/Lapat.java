package com.company;

public class Lapat extends Targy{
    public Lapat() {
    }

    /**
     * A függvény a kapott TargyVisitor visit(this) függvényét meghívja.
     * @param v a TargyVisitor, akinek a visit függvényét meghívja.
     * @return Amennyiben a paraméterül kapott TargyVisitor Lapát, úgy a függvény visszatérési értéke true, ha bármi más TargyVisitor leszármazott, a visszatérési értéke false.
     */
    @Override
    public boolean accept(TargyVisitor v) {
        return v.visit(this);
    }

    /**
     * A lapáttal való ásás két réteg havat takarít el, amennyiben 2 egység, vagy annál több hó van a mezőn, 1-et, ha csak 1, és semennyit, ha nincs.
     * @param j a havat lapátoló játékos
     */
    @Override
    public void hasznal(Jatekos j){
        int horeteg=j.getTartozkodasiMezo().getHotakaro();
        if (horeteg>=2) {
            j.getTartozkodasiMezo().horetegCsokkent();
            j.getTartozkodasiMezo().horetegCsokkent();
            j.munkaLevon();
        }
            else if (horeteg==1) {
            j.getTartozkodasiMezo().horetegCsokkent();
            j.munkaLevon();
        }

    }

    /**
     * A tárgy felvétele során (amennyiben a kikapart tárgy lapát) a this-t paraméterül adja a játékos LapatFelvesz(Lapat l) függvényének.
     * @param jatekos a lapátot kikaparó játékos
     */
    @Override
    public void felvesz(Jatekos jatekos){
        jatekos.lapatFelvesz(this);
    }
}
