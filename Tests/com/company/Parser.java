package com.company;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Parser {

    private ArrayList<Parancs> lista;

    public Parser() {
        lista = new ArrayList<>();
    }

    public void addParancs(Parancs p) {
        lista.add(p);
    }

    public ArrayList<Parancs> parse(String path) throws FileNotFoundException {
        File myObj = new File(path);
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {

            String data = myReader.nextLine();
            String[] line = data.split(" ");

            Parancs parancs = new Parancs();
            parancs.setTipus(line[0]);
            parancs.setNev(line[1]);
            parancs.setFuggvenynev(line[2]);

            int length = line.length;
            int size = (length - 2) / 2;

            ArrayList<String> p = new ArrayList<>();
            ArrayList<String> pt = new ArrayList<>();


            String[] params = new String[size];
            String[] paramTypes = new String[size];

            if (size > 0) {

                parancs.setHasParam(true);
                int seq = 3;
                int cnt = 0;

                for (int i = 0; i < size; i++) {

                    pt.add(line[3 + (i * 2)]);
                    paramTypes[cnt] = line[3 + (i * 2)];
                    p.add(line[3 + (i * 2) + 1]);
                    params[cnt] = line[3 + (i * 2) + 1];
                    cnt++;
                }
            } else {
                parancs.setHasParam(false);
            }

            for (int i = 0; i < p.size(); i++) {
                params[i] = p.get(i);
                paramTypes[i] = pt.get(i);
            }

            parancs.setParams(params);
            parancs.setParamTypes(paramTypes);
            this.addParancs(parancs);
        }

        myReader.close();
        return this.lista;
    }

    public ArrayList<Parancs> getLista() {
        return lista;
    }

    public void loadPalya(Kontroller kontroller, String palyaPath) throws IOException {
        String jsonString = new String(Files.readAllBytes(Paths.get("Resources/" + palyaPath)));
        JSONObject obj = new JSONObject(jsonString);
        JSONObject palya = obj.getJSONObject("palya");
        JSONArray mezok = palya.getJSONArray("mezok");

        //Hányadik alkatrész, játékos.. az ID generáláshoz kell
        int alk = 0; int esk = 0; int kut = 0;

        // Végigmegy a JSON mezőin és betölti őket a kontrollerbe
        for (int i = 0; i < mezok.length(); i++) {
            String id = mezok.getJSONObject(i).getString("id");
            String pos = mezok.getJSONObject(i).getString("pozicio");
            Mezo mezo;

            if (id.charAt(0) == 'J') {
                String targyString = mezok.getJSONObject(i).getString("targy");
                Targy targy = CreateTargy(targyString);
                // 0-3 között random teherbírás
                mezo = new Jegtabla(id, (int) (Math.random() * (6 + 1)) , 0, targy);
            } else if (id.charAt(0) == 'Y') {
                mezo = new Lyuk(id, 0);
            }
            //Ez nem kéne lefusson
            else {
                mezo = null;
                System.out.println("Ez nem kellett volna lefusson\nA mező null ra lett beállítva\n" +
                        "Valószínűleg nem jó a" + i + ".mező id-je");
            }

            //Beállítja a mező helyét a pályán
            String[] idx = pos.split(",");
            mezo.setSor(Integer.parseInt(idx[0]));
            mezo.setOszlop(Integer.parseInt(idx[1]));

            // Ha van kutató a mezőn hozzáadjuk
            int kutatoNum = mezok.getJSONObject(i).getInt("kutato");
            for (int j = 0; j < kutatoNum; j++) {
                Jatekos jatekos = new Kutato(kontroller, kut++);
                jatekos.setMezo(mezo);
                mezo.addAlloJatekos(jatekos);
                kontroller.addJatekos(jatekos);
            }
            // Ha van eszkimó a mezőn hozzáadjuk
            int eszkimoNum = mezok.getJSONObject(i).getInt("eszkimo");
            for (int j = 0; j < eszkimoNum; j++) {
                Jatekos jatekos = new Eszkimo(kontroller, esk++);
                jatekos.setMezo(mezo);
                mezo.addAlloJatekos(jatekos);
                kontroller.addJatekos(jatekos);
            }

            //Ha van a mezőn medve hozzáadjuk
            boolean medve = mezok.getJSONObject(i).getBoolean("jegesmedve");
            if (medve) {
                Jegesmedve jm = new Jegesmedve();
                jm.setMezo(mezo);
                mezo.setAlloJegesmedve(jm);
                kontroller.setJegesmedve(jm);
            }
            //Ha van alkatrész hozzáadja az alkatrészhez
            boolean alkatresz = mezok.getJSONObject(i).getBoolean("alkatresz");
            if (alkatresz) {
                Alkatresz a = new Alkatresz(alk++);
                mezo.setFagyottAlk(a);
            }

            if (mezo.getTeherbiras() < mezo.getAlloJatekos().size()) {
                mezo.setTeherbiras(mezo.getTeherbiras() + mezo.getAlloJatekos().size());
            }

            //Hozzáadja a mezőt a kontrollerhez
            kontroller.addMezo(mezo);
        }

        // Beáállítja a szomszédosságot
        ArrayList<String> iranyok = new ArrayList<>
                (Arrays.asList("Fel", "JobbFel", "Jobb", "JobbLe", "Le", "BalLe", "Bal", "BalFel"));

        for (int i = 0; i < mezok.length(); i++) {
            for (String iranyString : iranyok) {
                String tmp = mezok.getJSONObject(i).getString(iranyString);
                if (tmp.length() > 1) {
                    int cnt = Integer.parseInt(tmp.substring(1));
                    kontroller.getPalya(i).addSzomszedok(Irany.StringToIrany(iranyString), kontroller.getPalya(cnt));
                }
            }
        }

        kontroller.setAktivJatekos(kontroller.getJatekosok().get(0));
    }

    /**
     * Létrehoz egy tárgyat a JSON-ből beolvasott TargyString-ből
     * A pálya beolvasásánál van hasznosítva
     *
     * @param targyString A táegy típusa pl: Lapat, Sator...
     * @return A Létrehoztott tárgyat adja vissza
     */
    private Targy CreateTargy(String targyString) {

        if (targyString.equals("-")) {
            return null;
        }

        Executer executer = new Executer();
        //com.company.valamiTargy
        String targyClass = executer.convertString(targyString);

        //Üres osztály tömb, mert nincs paramétere ezeknek a konstruktoroknak
        Class<?>[] classes = new Class<?>[]{};
        try {
            return executer.construct(targyString, classes);

        } catch (ClassNotFoundException | InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
