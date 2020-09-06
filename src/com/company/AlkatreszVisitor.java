package com.company;
/**
 * Az AlkatreszVisitor visit függvényei közül az az egy ad vissza true értéket, amelyik Alkatrészt kap paraméterül, az összes többi false-t.
 */
public class AlkatreszVisitor implements TargyVisitor{
    public boolean visit(Kotel k){
        return false;
    };
    public boolean visit(Lapat l){
        return false;
    };
    public boolean visit(Buvarruha b){
        return false;
    };
    public boolean visit(Elelem e){
        return false;
    };
    public boolean visit(Alkatresz a){
        return true;
    }
    public boolean visit(Sator s) { return false; }


}
