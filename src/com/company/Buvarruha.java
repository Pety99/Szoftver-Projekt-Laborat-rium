package com.company;

public class Buvarruha extends Targy {
    /**
     * Konstruktor
     */
    public Buvarruha() {
    }

    /**
     * A TargyVisitor-t fogadja, meghivja a visit függvényét. True ha a tárgy búvárruha.
     *
     * @param v a paraméterül kapott visitor
     * @return
     */
    @Override
    public boolean accept(TargyVisitor v) {
        return v.visit(this);
    }

    /**
     * A tárgy felvétele során (amennyiben a kikapart tárgy búvárruha) a this-t paraméterül adja a játékos BuvarruhaFelvesz(Buvarruha b) függvényének.
     *
     * @param jatekos a búvárruhát kikaparó játékos
     */
    @Override
    public void felvesz(Jatekos jatekos) {
        jatekos.buvarruhaFelvesz(this);
    }

    /**
     * Üresen implementáljuk, mert a játékos már a búvárruha felvételekor védettséget szerez a fulladással szemben.
     *
     * @param j
     */
    @Override
    public void hasznal(Jatekos j) {
    }

    /**
     * Védettséget kap a játékos a búvárruhától
     *
     * @param j a játékos aki védett lesz
     */
    public void vedelem(Jatekos j) {
        j.setVedett(true);
    }

}
