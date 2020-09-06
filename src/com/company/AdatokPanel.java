package com.company;

import javax.swing.*;
import java.awt.*;

public class AdatokPanel extends JPanel{
    private ImageIcon eszkim=new ImageIcon("Resources/Assets/Eszkimo_I-01.png");
    private ImageIcon kuatato=new ImageIcon("Resources/Assets/Kutato_I-01.png");
    private JLabel kep=new JLabel();
    JTextField fulladasiAllapot=new JTextField();
    JTextField testho=new JTextField();
    JTextField buvarruha=new JTextField();
    JTextField munka=new JTextField();

    /**
     * Adatokat megjelenítő panel inicializálása, textfieldek és ikon hozzáadása.
     *
     */
            public AdatokPanel(){
                this.setPreferredSize(new Dimension(256,270));
                this.setBackground(new Color(41, 54, 63));
                BoxLayout boxlayout= new BoxLayout(this, BoxLayout.Y_AXIS); //felülről lefelé adja hozzá az elemeket.
                this.setLayout(boxlayout);
                kep.setIcon(eszkim);
                this.add(kep);
                testho.setEditable(false);
                testho.setBackground(new Color(41, 54, 63));
                testho.setFont(new Font("Century Gothic", 1, 16));
                testho.setForeground(new Color(85, 192, 136, 255));
                testho.setBorder(BorderFactory.createMatteBorder(2,0,1,0, Color.WHITE));
                this.add(testho);
                fulladasiAllapot.setEditable(false);
                fulladasiAllapot.setBackground(new Color(41, 54, 63));
                fulladasiAllapot.setFont(new Font("Century Gothic", 1, 16));
                fulladasiAllapot.setForeground(new Color(85, 192, 136, 255));
                fulladasiAllapot.setBorder(BorderFactory.createMatteBorder(1,0,1,0, Color.WHITE));
                this.add(fulladasiAllapot);
                buvarruha.setEditable(false);
                buvarruha.setBackground(new Color(41, 54, 63));
                buvarruha.setFont(new Font("Century Gothic", 1, 16));
                buvarruha.setForeground(new Color(85, 192, 136, 255));
                buvarruha.setBorder(BorderFactory.createMatteBorder(1,0,1,0, Color.WHITE));
                this.add(buvarruha);
                munka.setEditable(false);
                munka.setBackground(new Color(41, 54, 63));
                munka.setFont(new Font("Century Gothic", 1, 16));
                munka.setForeground(new Color(85, 192, 136, 255));
                munka.setBorder(BorderFactory.createMatteBorder(1,0,2,0, Color.WHITE));
                this.add(munka);
            }

    /**
     * Az aktív játékos adatainak lekérése és beállítása.
     *
     * @param aktivjatekos A játékos, akinek frissítjük az adatait
     */
            public void update(Jatekos aktivjatekos){
                //TODO típusellenőrzést kiszedni
                String strg=aktivjatekos.getID();
                if (strg.charAt(0)=='E')kep.setIcon(eszkim);
                else kep.setIcon(kuatato);
                fulladasiAllapot.setText("     Állapot  \t  "+aktivjatekos.getAllapot().toString());
                testho.setText("     Testhő  \t  "+aktivjatekos.getTestho());
                buvarruha.setText("     Búvárruha  \t  "+aktivjatekos.isVedett());
                munka.setText("     Munkák  \t  "+aktivjatekos.getMunka());
                revalidate();
            }
}
