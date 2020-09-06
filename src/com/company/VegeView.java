package com.company;

import javax.swing.*;
import java.awt.*;

public class VegeView extends JPanel {

    JLabel label = new JLabel("Nyertél!");

    VegeView(Kontroller k) {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        JButton kilep = new JButton("Kilépés");
        kilep.setPreferredSize(new Dimension(220, 80));
        kilep.setUI(new CustomButtonUI(false));
        kilep.addActionListener(k);
        kilep.setActionCommand("vege");
        JButton ujJatek = new JButton("Új játék");
        ujJatek.setPreferredSize(new Dimension(220, 80));
        ujJatek.setUI(new CustomButtonUI(false));
        ujJatek.addActionListener(k);
        ujJatek.setActionCommand("ujjatek");

        setBackground(new Color(41, 54, 63));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 10);

        this.add(kilep, gbc);

        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 0, 0);
        this.add(ujJatek, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 100, 0);
        label.setPreferredSize(new Dimension(300, 100));
        label.setFont(new Font("Century Gothic", Font.BOLD, 60));
        label.setForeground(new Color(85, 192, 136));
        this.add(label, gbc);
    }

    void setText(String s) {
        this.label.setText(s);
    }
}
