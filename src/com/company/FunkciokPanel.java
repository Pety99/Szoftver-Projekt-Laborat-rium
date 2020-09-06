package com.company;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import java.awt.*;

public class FunkciokPanel extends JPanel {
    // Játékos cuccainak a panelja
    JButton kapar=new JButton();
    JButton osszeszerel=new JButton();
    JButton iglutepit=new JButton();
    JLabel vizsgal=new JLabel();


    private JButton balfel=new JButton();
    private JButton fel=new JButton();
    private JButton bal=new JButton();
    private JButton jobbfel=new JButton();

    private JButton jobb=new JButton();
    private JButton balle=new JButton();
    private JButton le=new JButton();
    private JButton jobble=new JButton();

    private ImageIcon osszeszerelim=new ImageIcon("Resources/Assets/osszeszerel.png");
    private ImageIcon nagyitoim=new ImageIcon("Resources/Assets/nagyito.png");
    private ImageIcon x=new ImageIcon("Resources/Assets/x.png");
    private ImageIcon csakanyim=new ImageIcon("Resources/Assets/csákány.png");
    private ImageIcon igluim=new ImageIcon("Resources/Assets/Iglu_I-01_kisebb.png");
    private ImageIcon igluimNULL=new ImageIcon("Resources/Assets/Iglu_I_NULL-01_kisebb.png");

    private ImageIcon felNyil=new ImageIcon("Resources/Assets/Fel-01.png");private ImageIcon felNyilNULL=new ImageIcon("Resources/Assets/Fel_NULL-01.png");
    private ImageIcon jobbfelNyil=new ImageIcon("Resources/Assets/JobbraFel-01.png");private ImageIcon jobbfelNyilNULL=new ImageIcon("Resources/Assets/JobbraFel_NULL-01.png");
    private ImageIcon jobbNyil=new ImageIcon("Resources/Assets/Jobbra-01.png");private ImageIcon jobbNyilNULL=new ImageIcon("Resources/Assets/Jobbra_NULL-01.png");
    private ImageIcon jobbleNyil=new ImageIcon("Resources/Assets/JobbraLe-01.png");private ImageIcon jobbleNyilNULL=new ImageIcon("Resources/Assets/JobbraLe_NULL-01.png");
    private ImageIcon leNyil=new ImageIcon("Resources/Assets/Le-01.png");private ImageIcon leNyilNULL=new ImageIcon("Resources/Assets/Le_NULL-01.png");
    private ImageIcon balleNyil=new ImageIcon("Resources/Assets/BalraLe-01.png");private ImageIcon balleNyilNULL=new ImageIcon("Resources/Assets/BalraLe_NULL-01.png");
    private ImageIcon balNyil=new ImageIcon("Resources/Assets/Balra-01.png");private ImageIcon balNyilNULL=new ImageIcon("Resources/Assets/Balra_NULL-01.png");
    private ImageIcon balfelNyil=new ImageIcon("Resources/Assets/BalraFel-01.png");private ImageIcon balfelNyilNULL=new ImageIcon("Resources/Assets/BalraFel_NULL-01.png");

    /**
     * Funkciók ikonjait hozzáadjuk a panelhez.
     *Actioncommandot és actionlistenert fűzünk hozzájuk, hogy le tudjuk kezelni az egérkattintásokat.
     *
     * @param kontroller A játékot irányító kontroller
     */

    public FunkciokPanel(Kontroller kontroller){

        //Egyedi UI
        this.setBackground(new Color(41, 54, 63));
        kapar.setUI(new CustomButtonUI());
        kapar.setText("Kapar");
        osszeszerel.setUI(new CustomButtonUI());
        osszeszerel.setText("Összeszerel");
        iglutepit.setUI(new CustomButtonUI());
        iglutepit.setText("Épít");
        fel.setUI(new CustomButtonUI());
        jobbfel.setUI(new CustomButtonUI());
        jobb.setUI(new CustomButtonUI());
        jobble.setUI(new CustomButtonUI());
        le.setUI(new CustomButtonUI());
        balle.setUI(new CustomButtonUI());
        bal.setUI(new CustomButtonUI());
        balfel.setUI(new CustomButtonUI());

        JPanel targyak=new JPanel();
        targyak.setBackground(new Color(41, 54, 63));
        targyak.setPreferredSize(new Dimension(220,220));
        GridLayout gl1=new GridLayout(2,2);
        gl1.setHgap(15);
        gl1.setVgap(10);
        targyak.setLayout(gl1);
        GridLayout forths=new GridLayout(3,1);
        forths.setVgap(10);
        this.setLayout(forths);

        this.add(kapar);
        kapar.setIcon(csakanyim);
        targyak.add(kapar);
        kapar.setActionCommand("kapar");
        kapar.addActionListener(kontroller);

        osszeszerel.setIcon(osszeszerelim);
        targyak.add(osszeszerel);
        osszeszerel.setActionCommand("osszeszerel");
        osszeszerel.addActionListener(kontroller);

        //Vizsgál panel irányonként egy gombbal
        JPanel targyak2=new JPanel();
        GridLayout gl5=new GridLayout(3,3);
        gl5.setHgap(2);
        gl5.setVgap(2);
        targyak2.setLayout(gl5);
        targyak2.setBackground(new Color(41, 54, 63));


        //BalFel
        //balfel.setPreferredSize(new Dimension(60,60));
        balfel.setIcon(balfelNyilNULL);
        balfel.setActionCommand("vizsgál BalFel");
        balfel.addActionListener(kontroller);
        targyak2.add(balfel);

        //Fel
        //  fel.setPreferredSize(new Dimension(60,60));
        fel.setIcon(felNyilNULL);
        fel.setActionCommand("vizsgál Fel");
        fel.addActionListener(kontroller);
        targyak2.add(fel);

        //JobbFel
        //  jobbfel.setPreferredSize(new Dimension(60,60));
        jobbfel.setIcon(jobbfelNyilNULL);
        jobbfel.setActionCommand("vizsgál JobbFel");
        jobbfel.addActionListener(kontroller);
        targyak2.add(jobbfel);


        //Bal
        // bal.setPreferredSize(new Dimension(60,60));
        bal.setIcon(balNyilNULL);
        bal.setActionCommand("vizsgál Bal");
        bal.addActionListener(kontroller);
        targyak2.add(bal);

        //kotel.setPreferredSize(new Dimension(60,60));
        vizsgal.setIcon(nagyitoim);
        targyak2.add(vizsgal);

        //Jobb
        //jobb.setPreferredSize(new Dimension(60,60));
        jobb.setIcon(jobbNyilNULL);
        jobb.setActionCommand("vizsgál Jobb");
        jobb.addActionListener(kontroller);
        targyak2.add(jobb);


        //BalLe
        //balle.setPreferredSize(new Dimension(60,60));
        balle.setIcon(balleNyilNULL);
        balle.setActionCommand("vizsgál BalLe");
        balle.addActionListener(kontroller);
        targyak2.add(balle);

        //Le
        // le.setPreferredSize(new Dimension(60,60));
        le.setIcon(leNyilNULL);
        le.setActionCommand("vizsgál Le");
        le.addActionListener(kontroller);
        targyak2.add(le);

        //jobble.setPreferredSize(new Dimension(60,60));
        jobble.setIcon(jobbleNyilNULL);
        jobble.setActionCommand("vizsgál JobbLe");
        jobble.addActionListener(kontroller);
        targyak2.add(jobble);
        targyak.add(targyak2);


        iglutepit.setPreferredSize(new Dimension(90,90));
        iglutepit.setIcon(igluim);
        iglutepit.setActionCommand("epit");
        iglutepit.addActionListener(kontroller);
        targyak.add(iglutepit);

        targyak.add(targyak2, BorderLayout.LINE_START);
        JPanel munkaGombPanel=new JPanel();
        munkaGombPanel.setPreferredSize(new Dimension(220,30));
        munkaGombPanel.setLayout(gl5);
        munkaGombPanel.setBackground(new Color(41, 54, 63));
        JButton gomb=new JButton("Munkát levon");
        gomb.setUI(new CustomButtonUI(false));
       // gomb.setPreferredSize(new Dimension(220,60));
        gomb.setActionCommand("munkaLevon");
        gomb.addActionListener(kontroller);
        munkaGombPanel.add(gomb);

        this.add(targyak);
        this.add(munkaGombPanel);
    }

    /**
     * Funkciók ikonjainak frissítése.
     *
     * @param aktivJatekos A játékos, akinek az adatait szeretnénk updatelni.
     */
    public void update(Jatekos aktivJatekos){
        //TODO aszerint h kutató vagy eszkimo, a vizsgál vagy az iglu szürke kell legyen.
        fel.setIcon(felNyilNULL);
        jobbfel.setIcon(jobbfelNyilNULL);
        jobb.setIcon(jobbNyilNULL);
        jobble.setIcon(jobbleNyilNULL);
        le.setIcon(leNyilNULL);
        balle.setIcon(balleNyilNULL);
        bal.setIcon(balNyilNULL);
        balfel.setIcon(balfelNyilNULL);
        String bs=aktivJatekos.getID();
        Mezo m = aktivJatekos.getTartozkodasiMezo();
        if (bs.charAt(0)=='K') {
            if (m.getSzomszed(Irany.Fel) != null) fel.setIcon(felNyil);
            if (m.getSzomszed(Irany.JobbFel) != null) jobbfel.setIcon(jobbfelNyil);
            if (m.getSzomszed(Irany.Jobb) != null) jobb.setIcon(jobbNyil);
            if (m.getSzomszed(Irany.JobbLe) != null) jobble.setIcon(jobbleNyil);
            if (m.getSzomszed(Irany.Le) != null) le.setIcon(leNyil);
            if (m.getSzomszed(Irany.BalLe) != null) balle.setIcon(balleNyil);
            if (m.getSzomszed(Irany.Bal) != null) bal.setIcon(balNyil);
            if (m.getSzomszed(Irany.BalFel) != null) balfel.setIcon(balfelNyil);
            iglutepit.setIcon(igluimNULL);
        }
        else iglutepit.setIcon(igluim);

        revalidate();
    }
}

