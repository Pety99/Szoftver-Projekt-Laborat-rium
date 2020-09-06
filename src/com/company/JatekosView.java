package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class JatekosView extends JPanel {
    JPanel panel = new JPanel();
    private volatile Jatekos aktivJatekos;
    private View view;
    private AdatokPanel adatok;
    private TargyakPanel targyak;
    private FunkciokPanel funkciok;
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
         * Ha megváltozik az aktív játékos valamiért mert interakcióba lépett valamivel
         * Újra kell rajzolni az Inventoryt
         * @param event
         */
        @Override
        public void propertyChange(PropertyChangeEvent event) {
            propertyChangeHandler(event);
        }
    };

    /**
     * Ha az aktív játékosnak változott bármely tulajdonsága, akkor frissíti az adatokat.
     *
     * @param event Az esemény, amely értesít a változásról.
     */
    private void propertyChangeHandler(PropertyChangeEvent event) {
        if (event.getPropertyName().equals("aktivJatekos")) {
            // Az aktív játékos frissítése
            aktivJatekos = (Jatekos) event.getNewValue();
            //TODO Az inventory újrarajzolása
            // végig kell mennia az öszes mezőn mielőttaz aktív játékost kicsréli
            // Összehasonlítani és azt a részt újra rajzolni
            update();
        }
    }

    /**
     * Létrehozza az aktív játékos inventory-jének nézetét
     *
     * @param kontroller A kontroller amitől le kell kérni az aktív játékost
     * @param view       Az a Frame amin látni akarjuk ezt a nézetet
     */
    JatekosView(Kontroller kontroller, View view) {
        this.add(panel);
        this.view = view;
        adatok=new AdatokPanel();
        // A jelenleg aktív játékos inventory-jét is meg kell jeleníteni
        this.aktivJatekos = kontroller.getAktivJatekos();

        //A Kontrollerhez hozzá kell adni a Listenerünket
        kontroller.addPropertyChangeListener(listener);

        panel.setPreferredSize(new Dimension(256, 878));
        panel.setBackground(new Color(41, 54, 63));
        //loadImages();

        panel.add(adatok);
        targyak=new TargyakPanel(kontroller);
        JLabel cimke=new JLabel("Cuccaim:");
        panel.add(cimke);
        panel.add(targyak);
        //TODO:  végigmenni a játékos tömbjén hogy van-e az adott tárgyból. Képek, plusz sötét képek kellenek, ha egy tárgyból nincs..



        JLabel egyeblehetoseg=new JLabel("Egyéb lehetőségek: ");
        panel.add(egyeblehetoseg);
        funkciok=new FunkciokPanel(kontroller);
        panel.add(funkciok);
        adatok.update(aktivJatekos);
        targyak.update(aktivJatekos);
        funkciok.update(aktivJatekos);
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
            Rectangle2D rec = new java.awt.geom.Rectangle2D.Double(view.getWidth() - 266, 0 , image.getWidth(), image.getHeight());
            TexturePaint tp = new TexturePaint(image, rec);
            g2.setPaint(tp);
            Rectangle2D r = new Rectangle(0, 0, this.getWidth(), this.getHeight());
            g2.fill(r);
        } catch (java.io.IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Változás esetén érvénytelenítjük és újrarajzoljuk az adatokat.
     * Szinkronizálni kell a frissítést, mert ha éppen amikor vége lenne a játéknak frissül a view ott hibák keletkezhetnek
     */
    private void update() {
        synchronized (view) {
        //Változás történt a nézeten, újra kell rajzolni
        adatok.update(aktivJatekos);
        targyak.update(aktivJatekos);
        funkciok.update(aktivJatekos);
            revalidate();
            view.repaint();
        }
    }
}