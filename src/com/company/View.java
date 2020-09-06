package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

class View extends JFrame {
    /**
     * Az aktuális Játékos Inventory-jének nézete.
     */
    JatekosView jatekosView;

    /**
     * A pálya nézete
     */
    PalyaView palyaView;
    /**
     * A vége nézet
     */

    //Ezen helyezkendnek el a Játék nézetei
    JPanel panel = new JPanel(new BorderLayout());
    VegeView vegeView;
    CardLayout cl = new CardLayout();

    /**
     * Konstruktor
     * @param kontroller
     */
    View(Kontroller kontroller) {
        //A Frame beállítása
        super("Jatek");
        //TODO
        // Ezeket ki kéne olvasni a JSON-ből (Még bele sincs írva)
        // A palyaView létrehozása már paraméterben kaphatja
        int N = 4;
        int M = 4;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(cl);

        this.setVisible(true);
        //this.setResizable(false);
        this.setLocation(0, 0);
        this.setSize(222 * N + 256 + 30, 222 * M + 40);
        this.setMinimumSize(this.getSize());


        this.palyaView = new PalyaView(kontroller, this);
        this.jatekosView = new JatekosView(kontroller, this);
        this.vegeView=new VegeView(kontroller);

        panel.add(palyaView, BorderLayout.CENTER);
        panel.add(jatekosView, BorderLayout.EAST);

        this.getContentPane().add(panel, "panel");
        this.getContentPane().add(vegeView, "vegeView");
    }

    /**
     * Visszaadja a játék végét ábrázoló képet
     * @return
     */
    public VegeView getVegeView() {
        return vegeView;
    }

    /**
     * Textúrázva tölti ki a hátteret
     *
     * @param g
     */
    @Override
    public void paintComponents(Graphics g) {
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
            Rectangle2D r = new Rectangle(0, 0, this.getWidth(), this.getHeight());
            g2.fill(r);
        } catch (java.io.IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Visszaadja a CardLayout-ot
     *
     * @return A nézetek CardLayout-ja
     */
    @Override
    public LayoutManager getLayout() {
        return this.cl;
    }

    void setText(String s){
        this.vegeView.setText(s);
    }
}