package com.company;

/**
 * Absztrakt alaposztály, függvényeit a leszármazott tárgyak implementálják
 */
public abstract class Targy {
    public abstract boolean accept(TargyVisitor v);
    public abstract void felvesz(Jatekos j);
    public abstract void hasznal(Jatekos j);

}

