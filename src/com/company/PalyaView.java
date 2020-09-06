package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static javax.imageio.ImageIO.read;

public class PalyaView extends JPanel {
    /**
     * A megjelenítendő mezők paneljei
     */
    ArrayList<ArrayList<MezoPanel>> panels = new ArrayList<>();
    /**
     * A layouthoz kell
     */
    GridBagConstraints gbc = new GridBagConstraints();
    /**
     * Ebben lesznek tárolva a rajzoláshoz szükséges képek
     */
    Map<String, BufferedImage> images = new HashMap<>();
    /**
     * Egy Listener ami tagváltozók változását figyeli más osztályokban
     * Most csak egyet, de meg lehet írni máshogy is
     */
    PropertyChangeListener listener = new PropertyChangeListener() {

        /**
         * Ha valaki megnyomott egy gombot utánna ez mindig meghívódik.
         * Ha megváltozott a pálya újra kell rajzolni
         * @param event
         */
        @Override
        public void propertyChange(PropertyChangeEvent event) {
            propertyChangeHandler(event);
        }
    };

    private View view;

    /**
     * Ez az időzítő a képrenyőn mozgatja a tengeri élőlényeket
     */
    Timer t = new Timer(30, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            move(transformHal1, 2, 2 * Math.sin(time += 0.01), h1X, h1Y, "Hal 1");
            move(transformHal2, 4, -2 * Math.sin(time += 0.01), h2X, h2Y, "Hal 2");
            move(transformPolip, -3, 2.2 * Math.sin(time += 0.01), pX, pY, "Polip");
            move(transformTintahal, -2, 3 * Math.sin(time += 0.01), tX, tY, "Tintahal");
            move(transformRak, 1, 2.2 * Math.sin(0.25 * (time += 0.01)), rX, rY, "Rak");
            move(transformCapa, -1, 2.2 * Math.sin(0.01*(time += 0.01)), cX, cY, "Capa");
            repaint();
        }
    });

    AffineTransform transformPolip = new AffineTransform();
    AffineTransform transformHal1 = new AffineTransform();
    AffineTransform transformHal2 = new AffineTransform();
    AffineTransform transformTintahal = new AffineTransform();
    AffineTransform transformRak = new AffineTransform();
    AffineTransform transformCapa = new AffineTransform();

    double time = 0;
    AtomicInteger h1X = new AtomicInteger(1);
    AtomicInteger h1Y = new AtomicInteger(1);
    AtomicInteger h2X = new AtomicInteger(1);
    AtomicInteger h2Y = new AtomicInteger(1);
    AtomicInteger pX = new AtomicInteger(1);
    AtomicInteger pY = new AtomicInteger(1);
    AtomicInteger tX = new AtomicInteger(1);
    AtomicInteger tY = new AtomicInteger(1);
    AtomicInteger rX = new AtomicInteger(1);
    AtomicInteger rY = new AtomicInteger(1);
    AtomicInteger cX = new AtomicInteger(1);
    AtomicInteger cY = new AtomicInteger(1);

    /**
     * Létrehozza a pálya nézetet
     * Léterhoz N darab M hosszú MezoPanel ArrayList-et amin majd megjelennek a mezők
     *
     * @param kontroller A kontroller amitől le kell kérni a pályát amit meg akarunk jeleníteni
     * @param view       Az a Frame amin látni akarjuk ezt a nézetet
     */
    PalyaView(Kontroller kontroller, View view) {

        //Ahonnan indulnak az állatok
        transformHal1.translate(-1, 100);
        transformHal2.translate(700, 500);
        transformPolip.translate(200,200);
        transformTintahal.translate(300,800);
        transformRak.translate(400,400);
        transformCapa.translate(700,700);

        // A nézet amin minden elhelyezkedik
        this.view = view;

        //A Kontrollerhez hozzá kell adni a Listenerünket
        kontroller.addPropertyChangeListener(listener);

        //Betölti a szükséges képeket
        loadImages();

        //TODO Ezeket nem fiexen kéne ide írni
        int N = 4;
        int M = 2;


        this.setSize(N * 210, M * 222);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        this.setLayout(new GridBagLayout());

        //Páratlan sorok betöltése
        for (int i = 0; i < M; i++) {
            ArrayList<MezoPanel> row = new ArrayList<>();
            int top, left;
            //Hogy az első sort ne tolja vissza
            if (i == 0) top = 0;
            else top = -28;
            //Minden sorban 4 mező van
            for (int j = 0; j < N; j++) {
                //Hogy az első oszlopot ne tolja vissza
                if (j == 0) left = 0;
                else left = -28;

                MezoPanel p = new MezoPanel();

                gbc.insets = new Insets(top, left, 72, 72);
                gbc.gridx = j;
                gbc.gridy = i;

                p.setMinimumSize(new Dimension(128, 128));
                p.setMaximumSize(new Dimension(128, 128));
                p.setPreferredSize(new Dimension(128, 128));

                row.add(p);
                add(p, gbc);
            }
            panels.add(row);
        }

        //Páros sorok betöltése
        for (int i = 0; i < M; i++) {
            ArrayList<MezoPanel> row = new ArrayList<>();
            int top, left;
            //Hogy az második sort ne tolja vissza
            if (i == 0) top = 100;
            else top = 72;
            //Minden sorban 4 mező van
            for (int j = 0; j < N; j++) {
                //Hogy az második oszlopot ne tolja vissza
                if (j == 0) left = 100;
                else left = 72;

                MezoPanel p = new MezoPanel();

                gbc.insets = new Insets(top, left, 0, 0);
                gbc.gridx = j;
                gbc.gridy = i;

                p.setMinimumSize(new Dimension(128, 128));
                p.setMaximumSize(new Dimension(128, 128));
                p.setPreferredSize(new Dimension(128, 128));

                row.add(p);
                add(p, gbc);
            }
            panels.add(2 * i + 1, row);
        }
        t.start();
    }

    /**
     * Tulajdonság változását kezeli.
     *
     * @param event Az esemány, amely a változást jelzi.
     */
    private void propertyChangeHandler(PropertyChangeEvent event) {
        if (event.getPropertyName().equals("palya")) {

            // A pálya frissítése
            ArrayList<Mezo> palya = (ArrayList<Mezo>) event.getNewValue();

            //Újra rajzolás
            update(palya);
        } else if (event.getPropertyName().equals("mezo")) {
            Mezo mezo = (Mezo) event.getNewValue();
            update(mezo);
        } else if (event.getPropertyName().equals("aktiv mezo")) {
            Mezo mezo = (Mezo) event.getNewValue();
            update(mezo, "aktiv");
        } else if (event.getPropertyName().equals("vege")) {

            //TODO...
            //update();
        }
    }

    /**
     * Az egész pályát újra rajzolja (Minden menzőre meghívja az update()-t.)
     */
    private void update(ArrayList<Mezo> palya) {
        for (Mezo mezo : palya) {
            update(mezo);
        }
    }

    /**
     * Egy mezőt újra rajzol. A mezőhöz tartozó mezoPanel-t megkeresi és beállítja a rajta látható rétegeket,
     * majd ezt újra rajzolja.
     *
     * @param mezo A paraméterként kapott mező amit újra fog rajzolni.
     */
    public void update(Mezo mezo, String... aktiv) {
        int sor = mezo.getSor();
        int oszlop = mezo.getOszlop();
        MezoPanel mezoView = panels.get(sor).get(oszlop);
        ArrayList<BufferedImage> retegek = new ArrayList<>();
        //A legalsó réteg Jégtábla vagy lyuk
        if (mezo.getID().contains("J") && mezo.getTeherbiras() >= mezo.getAlloJatekos().size()) {
            retegek.add(images.get("Jegtabla"));
        } else if (mezo.getID().contains("Y") || mezo.getTeherbiras() < mezo.getAlloJatekos().size()) {
            retegek.add(images.get("Lyuk"));
        }

        //A rakéta alkatrészek
        if (mezo.getFagyottAlkatresz() != null) {
            retegek.add(images.get(mezo.getFagyottAlkatresz().getID() + "-Pisztoly"));
        }


        KotelVisitor kv = new KotelVisitor();
        if (mezo.getTargy() != null) {
            if (mezo.getTargy().accept(kv)) {
                retegek.add(images.get("Kotel"));
            }
        }

        LapatVisitor lv = new LapatVisitor();
        if (mezo.getTargy() != null) {
            if (mezo.getTargy().accept(lv)) {
                retegek.add(images.get("Lapat"));
            }
        }

        BuvarruhaVisitor bv = new BuvarruhaVisitor();
        if (mezo.getTargy() != null) {
            if (mezo.getTargy().accept(bv)) {
                retegek.add(images.get("Buvarruha"));
            }
        }

        ElelemVisitor ev = new ElelemVisitor();
        if (mezo.getTargy() != null) {
            if (mezo.getTargy().accept(ev)) {
                retegek.add(images.get("Etel"));
            }
        }
        if (mezo.getID().contains("J") && mezo.getTeherbiras() >= mezo.getAlloJatekos().size()) {
            retegek.add(images.get("Zuzmara"));
        }
        else if (mezo.getID().contains("Y") || mezo.getTeherbiras() < mezo.getAlloJatekos().size()) {
            retegek.add(images.get("Zuzmara_Lyuk"));
        }

        SatorVisitor sv = new SatorVisitor();
        if (mezo.getTargy() != null) {
            if (mezo.getTargy().accept(sv)) {
                retegek.add(images.get("Sator_T"));
            }
        }

        //A hó kirajzolása
        if (mezo.getHotakaro() != 0) {
            retegek.add(images.get("Ho"));
        }

        ArrayList<Jatekos> jatekosok = mezo.getAlloJatekos();
        if (!jatekosok.isEmpty()) {
            for (Jatekos jatekos : jatekosok) {
                retegek.add(images.get(jatekos.getID()));
            }
        }

        if (mezo.getAlkatreszek() != null) {
            if (!mezo.getAlkatreszek().isEmpty()) {
                for (Alkatresz alk : mezo.getAlkatreszek()) {
                    if(alk != null)
                        retegek.add(images.get((alk.getID()) + "-Pisztoly_Le"));
                }
            }
        }

        //A megfelelő rétegeket kell rárajzolni egy mezőPanelra ami megfelel a mezőnek
        if (mezo.getAlloJegesmedve() != null) {
            retegek.add(images.get("Medve"));
        }

        if (mezo.isIglu()) {
            retegek.add(images.get("Iglu"));
        }

        if (mezo.getSatorMiotaVan() != 0) {
            retegek.add(images.get("Sator"));
        }

        for (String s : aktiv) {
            if (s.equals("aktiv")) {
                retegek.add(images.get("Aktiv"));
            }
        }
        if (mezo.isVizsgalt()){
            retegek.add(images.get(String.valueOf(mezo.getTeherbiras())));
        }

        //Ki kell rajzolni
        mezoView.update(retegek);
    }

    /**
     * Textúrázva tölti ki a hátteret
     *
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (g2 == null) {
            System.out.println("error");
            return;
        }
        try {
            BufferedImage image = ImageIO.read(new File("Resources/Assets/Tenger.png"));
            Rectangle2D rec = new java.awt.geom.Rectangle2D.Double(0, 0, image.getWidth(), image.getHeight());
            TexturePaint tp = new TexturePaint(image, rec);
            g2.setPaint(tp);
            Rectangle2D r = this.getBounds();
            g2.fill(r);

            g2.drawImage(images.get("Hal 1"), transformHal1, this);
            g2.drawImage(images.get("Hal 2"), transformHal2, this);
            g2.drawImage(images.get("Polip"), transformPolip, this);
            g2.drawImage(images.get("Tintahal"), transformTintahal, this);
            g2.drawImage(images.get("Rak"), transformRak, this);
            g2.drawImage(images.get("Capa"), transformCapa, this);

        } catch (java.io.IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Ikonok betöltése
     */
    void loadImages() {
        try {
            images.put("Jegtabla", read(new File("Resources/Assets/Jegtabla-01.png")));
            images.put("Lyuk", read(new File("Resources/Assets/Lyuk-01.png")));
            images.put("Ho", read(new File("Resources/Assets/Ho-01.png")));
            images.put("Iglu", read(new File("Resources/Assets/Iglu-01.png")));
            images.put("Sator_T", read(new File("Resources/Assets/Sator-01.png")));
            images.put("Sator", read(new File("Resources/Assets/Sator_Le-01.png")));
            images.put("0-Pisztoly", read(new File("Resources/Assets/1-Pisztoly-01.png")));
            images.put("1-Pisztoly", read(new File("Resources/Assets/2-Pisztoly-01.png")));
            images.put("2-Pisztoly", read(new File("Resources/Assets/3-Pisztoly-01.png")));
            images.put("0-Pisztoly_Le", read(new File("Resources/Assets/1-Pisztoly_Le-01.png")));
            images.put("1-Pisztoly_Le", read(new File("Resources/Assets/2-Pisztoly_Le-01.png")));
            images.put("2-Pisztoly_Le", read(new File("Resources/Assets/3-Pisztoly_Le-01.png")));
            images.put("Buvarruha", read(new File("Resources/Assets/Buvarruha-01.png")));
            images.put("Kotel", read(new File("Resources/Assets/Kotel-01.png")));
            images.put("Lapat", read(new File("Resources/Assets/Lapat-01.png")));
            images.put("Etel", read(new File("Resources/Assets/Konzerv-01.png")));
            images.put("Medve", read(new File("Resources/Assets/Medve-01.png")));
            images.put("Hal 1", read(new File("Resources/Assets/Hal-01.png")));
            images.put("Hal 2", read(new File("Resources/Assets/Hal-01.png")));
            images.put("Polip", read(new File("Resources/Assets/Polip-01.png")));
            images.put("Tintahal", read(new File("Resources/Assets/Tintahal-01.png")));
            images.put("Capa", read(new File("Resources/Assets/Capa-01.png")));
            images.put("Rak", read(new File("Resources/Assets/Rak-01.png")));
            images.put("E0", read(new File("Resources/Assets/Eszkimo_Top-01.png")));
            images.put("E1", read(new File("Resources/Assets/Eszkimo_Mid_Left-01.png")));
            images.put("E2", read(new File("Resources/Assets/Eszkimo_Mid_Right-01.png")));
            images.put("E3", read(new File("Resources/Assets/Eszkimo_Bottom_Left-01.png")));
            images.put("E4", read(new File("Resources/Assets/Eszkimo_Bottom_Center-01.png")));
            images.put("E5", read(new File("Resources/Assets/Eszkimo_Bottom_Right-01.png")));
            images.put("K0", read(new File("Resources/Assets/Kutato_Bottom-01.png")));
            images.put("K1", read(new File("Resources/Assets/Kutato_Mid_Left-01.png")));
            images.put("K2", read(new File("Resources/Assets/Kutato_Mid_Right-01.png")));
            images.put("K3", read(new File("Resources/Assets/Kutato_Top_Left-01.png")));
            images.put("K4", read(new File("Resources/Assets/Kutato_Top_Center-01.png")));
            images.put("K5", read(new File("Resources/Assets/Kutato_Top_Right-01.png")));
            images.put("Aktiv", read(new File("Resources/Assets/Active-01.png")));
            images.put("Zuzmara", read(new File("Resources/Assets/Zuzmara-01.png")));
            images.put("Zuzmara_Lyuk", read(new File("Resources/Assets/Zuzmara_Lyuk-01.png")));
            images.put("0", read(new File("Resources/Assets/0-01.png")));
            images.put("1", read(new File("Resources/Assets/1-01.png")));
            images.put("2", read(new File("Resources/Assets/2-01.png")));
            images.put("3", read(new File("Resources/Assets/3-01.png")));
            images.put("4", read(new File("Resources/Assets/4-01.png")));
            images.put("5", read(new File("Resources/Assets/5-01.png")));
            images.put("6", read(new File("Resources/Assets/6-01.png")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Elmozgatja a képernyőn a vízben az élőlényeket
     * @param transform Az élőlény transzformációs mátrixa
     * @param X mennyivel szeretnénk, hogy arréb menjen X irányba
     * @param Y mennyivel szeretnénk, hogy arrébb menjen Y irányba
     * @param xElojel ha kimegy a képernyőről egy idő után vissza téríti ezért kell ez az előjel, azért AtomicInteger,
     *                mert így át lehet állítani az értékét a függvényből
     * @param yElojel ha kimegy a képernyőről egy idő után vissza téríti ezért kell ez az előjel, azért AtomicInteger,
     *      *                mert így át lehet állítani az értékét a függvényből
     * @param name A kép neve amit ki szeretnénk rajzolni.
     */
    private void move(AffineTransform transform, double X, double Y, AtomicInteger xElojel, AtomicInteger yElojel, String name){
        transform.translate(X * xElojel.get(), (Y + Math.random()) * yElojel.get());
        BufferedImage im = images.get(name);
        if(transform.getTranslateX() + im.getWidth() < 0 || transform.getTranslateX() - im.getWidth() > this.getWidth()){

            xElojel.set( xElojel.get() * -1);

            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-im.getWidth(null),0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            images.put(name,op.filter(im, null));
        }
        if(transform.getTranslateY() + im.getHeight() < 0 || transform.getTranslateY() - im.getHeight() > this.getHeight()){
            yElojel.set(yElojel.get() * -1);
        }
    }
}
