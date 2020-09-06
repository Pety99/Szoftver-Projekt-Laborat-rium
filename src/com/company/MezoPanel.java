package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MezoPanel extends JPanel {
    ArrayList<BufferedImage> images = new ArrayList<>();

    //Ez csak ,hogy megjelenjen valami a képen betölt egy képet
    public MezoPanel() {
        this.setOpaque(false);
    }

    /**
     * Betöltött kép kirajzolása
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(!images.isEmpty()) {
            for (BufferedImage image : images) {
                g.drawImage(image, 0, 0, this);
            }
        }
    }
    /**
     * Kicseréli a képet amit ki kell rajozolni
     * @param im Képek listája, amit be szeretnénk rakni.
     */
    public void update(ArrayList<BufferedImage> im){
        images.clear();
        for (BufferedImage image: im) {
            images.add(image);
        }
        revalidate();
    }
}