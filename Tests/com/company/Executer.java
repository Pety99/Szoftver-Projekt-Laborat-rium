package com.company;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Executer {

    public Map<String, Object> objects = new HashMap<>();

    public Object get(String key) {
        return this.objects.get(key);
    }

    public void put(String key, Object value) {
        this.objects.put(key, value);
    }

    /**
     * Bármilyen objektum konstruktora
     *
     * @param className Az osztály neve
     * @param argTypes  A konstruktor argumentumok osztályainak tömbje pl: {java.lang.Integer, java.Lang.String, Jatekos}
     * @param args      A konstruktor argumentumai String tömbben pl: {"5", "Egy", "Jatekos"}
     * @return Visszadja az újonan létrejöt objektumot
     */
    //A lehet a 2. paramétert össze kell vonni a 3. al hogy lehessen praméter nélkül is hívni
    public <T> T construct(String className, Class<?>[] argTypes, String... args)
            throws NoSuchMethodException, SecurityException,
            InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException,
            ClassNotFoundException {

        //Létrehozza a paraméterlista tömbjét
        Class<?>[] classes = new Class<?>[argTypes.length];
        for (int i = 0; i < argTypes.length; i++) {
            classes[i] = argTypes[i];
        }

        Object[] params = castTypes(argTypes, args);

        //Az első paramétter alapján osztályt csinál
        try{
            Class<?> cls = Class.forName(className);
            Constructor<?> ctor = cls.getDeclaredConstructor(classes);
            return (T) ctor.newInstance(params);
        }
        catch (ClassNotFoundException e){
            //Ilynekor megpróbáljuk hátha ebben a package-ben van ez is
            className = this.getClass().getPackage().toString().substring(8) + "." + className;
            Class<?> cls = Class.forName(className);
            //Visszadja az előbb létrehozott osztály megfelelő konstruktorát
            Constructor<?> ctor = cls.getDeclaredConstructor(classes);
            return (T) ctor.newInstance(params);
        }
    }


    /**
     * Egy megadott objektum bármilyen függvényét bármilyen paraméterezéssel futtatja
     *
     * @param obj        Amin híni szeretnénk a  függvényt
     * @param methodName A függvény neve amit meg akarunk hívni
     * @param argTypes   A függvény argumentumainak osztályainak tömbje
     * @param args
     * @return
     */
    public Object runTheMethod(Object obj, String methodName, Class<?>[] argTypes, String... args) {
        try {
            Object[] params = castTypes(argTypes, args);
            Method method = obj.getClass().getMethod(methodName, argTypes);
            Object o = method.invoke(obj, params);
            return o;
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Pár beépített osztály egyszerűbb elnevezését átváltja beépített osztály nevére
     *
     * @param name A parancsból érkező osztály neve
     * @return A beípített osztály neve
     */
    public String convertString(String name) {
        if (name.equals("String"))
            name = "java.lang.String";
        else if (name.equals("Int")) {
            name = "java.lang.Integer";
        } else if (name.equals("Bool")) {
            name = "java.lang.Boolean";
        }
        else{
            name = "com.company." + name;
        }
        return name;
    }

    /**
     * Név alapján megadja az osztályt egy tömb elemeinek, és visszadja ezeket az osztályokat egy tömbként
     * pl: {"Int", "Jatekos"} -> {Java.lang.Integer, Jatekos}
     *
     * @param args Az argumentumok osztályainak a szöveges nevei egy Strign[]-ben
     * @return Visszaadja az osztéylok tömbjét
     * @throws ClassNotFoundException
     */
    public Class<?>[] argClasses(String[] args) throws ClassNotFoundException {

        Class<?>[] classes = new Class<?>[args.length];

        for (int i = 0; i < args.length; i++) {

            String type = this.convertString(args[i]);
            Class<?> cls = Class.forName(type);
            classes[i] = cls;
        }

        return classes;
    }

    //LEHET ITT MAJD MÓDOSÍTANI KELL A SORRENDET ÉS ELŐBB KÉRNI A MAP-BŐL
    /** 2 tömböt vár paraméterben egy osztályok tömbjét és egy Stirng tömböt.
     * A String tömb i-edik elemét az osztályok  tömbjének i-edik elemével megegyező típusúra castolja
     * Ha nem található ilyen típus akkor megnézi, hogy volt e már ilyen nevű objektum eltárolva, ha igen akkor azt adja vissza
     * Ez a funkció azért fontos mert így lehet majd függvényeket hívni már létehozott és eltárolt objektumokon
     * @param types
     * @param args
     * @return
     */
    public Object[] castTypes(Class<?>[] types, String... args) {

        Object[] objs = new Object[args.length];

        for (int i = 0; i < types.length; i++) {

            PropertyEditor editor = PropertyEditorManager.findEditor(types[i]);
            try {
                editor.setAsText(args[i]);
                objs[i] = editor.getValue();
            } catch (NullPointerException e) {

                try {
                    objs[i] = this.objects.get(args[i]);
                } catch (NullPointerException ex) {
                    System.out.println("Nem található ilyen objektum");
                }
            }
        }
        return objs;
    }
}