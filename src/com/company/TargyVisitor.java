package com.company;

public interface TargyVisitor {
    public boolean visit(Kotel k);
    public boolean visit(Lapat l);
    public boolean visit(Buvarruha b);
    public boolean visit(Elelem e);
    public boolean visit(Alkatresz a);
    public boolean visit(Sator s);
}
