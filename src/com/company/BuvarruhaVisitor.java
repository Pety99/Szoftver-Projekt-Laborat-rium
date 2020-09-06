package com.company;

/**
 * A BuvarruhaVisitor visit függvényei közül az az egy ad vissza true értéket, amelyik Búvárruhát kap paraméterül, az összes többi false-t.
 */
public class BuvarruhaVisitor implements TargyVisitor{

    public BuvarruhaVisitor(){ }
    public boolean visit(Kotel k){
        return false;
    }
    public boolean visit(Lapat l){
        return false;
    };
    public boolean visit(Buvarruha b){
        return true;
    }
    public boolean visit(Elelem e){
        return false;
    }
    public boolean visit(Alkatresz a){
        return false;
    }

    @Override
    public boolean visit(Sator s) {
        return false;
    }


}
