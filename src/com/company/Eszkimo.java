package com.company;

public class Eszkimo extends Jatekos implements Cloneable{

    /**
     * Default konstruktor
     */
    public Eszkimo(){
        super.setTestho(5);
        super.setVedett(false);
    }

    /**
     * Konstruktor
     * @param k a kontroller
     * @param ID az id
     */
    public Eszkimo(Kontroller k, int ID){
        super(k, 5, "E" + ID);
    } // amikor létrejön, 5 a testhője

    /**
     * Eszkimo iglut tud építeni
     */
    @Override
    public void epit(){
        if(this.getTartozkodasiMezo().isIglu()==false) {
            this.getTartozkodasiMezo().setIglu(true);
            if (this.getTartozkodasiMezo().getTeherbiras() != 0)
                this.munkaLevon();
        }
    }

    /**
     * Lemásolja az objektumot.
     * @return a másolat
     * @throws CloneNotSupportedException
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
