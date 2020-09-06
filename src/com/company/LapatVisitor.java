package com.company;
/**
 * A LapatVisitor visit függvényei közül az az egy ad vissza true értéket, amelyik Lapátot kap paraméterül, az összes többi false-t.
 */
public class LapatVisitor implements TargyVisitor{
    public boolean visit(Kotel k){
        return false;
    };
    public boolean visit(Lapat l){
        return true;
    };
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
