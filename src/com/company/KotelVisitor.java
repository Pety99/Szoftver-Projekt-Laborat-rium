package com.company;

/**
 * A KotelVisitor visit függvényei közül az az egy ad vissza true értéket, amelyik Kotelet kap paraméterül, az összes többi false-t.
 */
public class KotelVisitor implements TargyVisitor{
    public boolean visit(Kotel k){
        return true;
    };
    public boolean visit(Lapat l){ return false; };
    public boolean visit(Buvarruha b){
        return false;
    };
    public boolean visit(Elelem e){
        return false;
    };
    public boolean visit(Alkatresz a){
        return false;
    };
    public boolean visit(Sator s) { return false;}
}
