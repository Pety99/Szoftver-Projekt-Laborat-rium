package com.company;
/**
 * A SatorVisitor visit függvényei közül az az egy ad vissza true értéket, amelyik Sátrat kap paraméterül, az összes többi false-t.
 */
public class SatorVisitor implements TargyVisitor {
    @Override
    public boolean visit(Kotel k) { return false; }
    public boolean visit(Lapat l) { return false; }
    public boolean visit(Buvarruha b) { return false; }
    public boolean visit(Elelem e) { return false; }
    public boolean visit(Alkatresz a) { return false; }
    public boolean visit(Sator s) { return true; }

    
}
