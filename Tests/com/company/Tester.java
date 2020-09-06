package com.company;



import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;



public class Tester {

    /**
     *  A Resources Mappában lévő <TestName>.txt fájlokban lévő parancsokat / teszteket tudja futtatni
     * @param filename A futtatandó fájl neve kiterjesztés nélkül
     */
    public void testfromfile(String filename) {
        Executer e = new Executer();
        Parser parser = new Parser();
        ArrayList<Parancs> parancsok = null;
        try {

            parancsok = parser.parse("Resources/" + filename + ".txt");

            for (int i = 0; i < parancsok.size(); i++) {
                Parancs p = parancsok.get(i);
                String tipus = p.getTipus();
                String nev = p.getNev();
                String fuggveny = p.getFuggvenynev();
                Class<?>[] paramTypes = e.argClasses(p.getParamTypes());
                String[] params = p.getParams();

                if (fuggveny.equals("create")) {
                    Object obj = e.construct(tipus, paramTypes, params);
                    e.put(nev, obj);
                } else {
                    Object o = e.get(nev);
                    Object ret = e.runTheMethod(o, fuggveny, paramTypes, params);
                    e.put("ret" + String.valueOf(i), ret);
                }
            }
        } catch (FileNotFoundException | NoSuchMethodException | InstantiationException |
                IllegalAccessException | InvocationTargetException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}