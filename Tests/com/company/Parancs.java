package com.company;

public class Parancs {
    private String Tipus;
    private String Nev;
    private String Fuggvenynev;
    private String[] Params;
    private String[] ParamTypes;

    public boolean isHasParam() {
        return hasParam;
    }

    public void setHasParam(boolean hasParam) {
        this.hasParam = hasParam;
    }
    private boolean hasParam;

    public String getTipus() {
        return Tipus;
    }

    public void setTipus(String tipus) {
        Tipus = tipus;
    }

    public String getNev() {
        return Nev;
    }

    public void setNev(String nev) {
        Nev = nev;
    }

    public String getFuggvenynev() {
        return Fuggvenynev;
    }

    public void setFuggvenynev(String fuggvenynev) {
        Fuggvenynev = fuggvenynev;
    }

    public String[] getParams() {
        return Params;
    }

    public void setParams(String[] params) {
        Params = params;
    }

    public String[] getParamTypes() {
        return ParamTypes;
    }

    public void setParamTypes(String[] paramTypes) {
        ParamTypes = paramTypes;
    }
}
