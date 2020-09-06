package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TargyakPanel extends JPanel {
    // Játékos cuccainak a panelja
    private JButton lapat = new JButton();
    private JButton sator = new JButton();
    private JButton alkatresz = new JButton();
    private JLabel kotel = new JLabel();


    private JButton balfel = new JButton();
    private JButton fel = new JButton();
    private JButton bal = new JButton();
    private JButton jobbfel = new JButton();

    private JButton jobb = new JButton();
    private JButton balle = new JButton();
    private JButton le = new JButton();
    private JButton jobble = new JButton();

    private ArrayList<BufferedImage> retegek=new ArrayList<>();

    private ImageIcon kotelim=new ImageIcon("Resources/Assets/Kotel_I-02_kisebb.png");
    private ImageIcon kotelimNULL=new ImageIcon("Resources/Assets/Kotel_I_NULL-02_kisebb.png");
    private ImageIcon lapatim = new ImageIcon("Resources/Assets/Lapat_I-01.png");
    private ImageIcon lapatimNULL = new ImageIcon("Resources/Assets/Lapat_I_NULL-01.png");
    private ImageIcon satorim = new ImageIcon("Resources/Assets/Sator_I-01.png");
    private ImageIcon satorimNULL = new ImageIcon("Resources/Assets/Sator_I_NULL-01.png");
   // private ImageIcon x = new ImageIcon("Resources/Assets/x.png");

    private ImageIcon jobbraim = new ImageIcon("Resources/Assets/Jobbra-01.png");
    private ImageIcon jobbraimNULL = new ImageIcon("Resources/Assets/Jobbra_NULL-01.png");
    private ImageIcon balraim = new ImageIcon("Resources/Assets/Balra-01.png");
    private ImageIcon balraimNULL = new ImageIcon("Resources/Assets/Balra_NULL-01.png");
    private ImageIcon jobbfelim = new ImageIcon("Resources/Assets/JobbraFel-01.png");
    private ImageIcon jobbfelimNULL = new ImageIcon("Resources/Assets/JobbraFel_NULL-01.png");
    private ImageIcon felim = new ImageIcon("Resources/Assets/Fel-01.png");
    private ImageIcon felimNULL = new ImageIcon("Resources/Assets/Fel_NULL-01.png");
    private ImageIcon balfelim = new ImageIcon("Resources/Assets/BalraFel-01.png");
    private ImageIcon balfelimNULL = new ImageIcon("Resources/Assets/BalraFel_NULL-01.png");
    private ImageIcon balleim= new ImageIcon("Resources/Assets/BalraLe-01.png");
    private ImageIcon balleimNULL = new ImageIcon("Resources/Assets/BalraLe_NULL-01.png");
    private ImageIcon leim= new ImageIcon("Resources/Assets/Le-01.png");
    private ImageIcon leimNULL = new ImageIcon("Resources/Assets/Le_NULL-01.png");
    private ImageIcon jobbleim= new ImageIcon("Resources/Assets/JobbraLe-01.png");
    private ImageIcon jobbleimNULL = new ImageIcon("Resources/Assets/JobbraLe_NULL-01.png");

    private ImageIcon kotelim1 = new ImageIcon("Resources/Assets/Kotel_I-02.png");
    private ImageIcon kotelimNULL1 = new ImageIcon("Resources/Assets/Kotel_I_NULL-02.png");
    private BufferedImage pisztolyNULL;
    private BufferedImage pisztolyegy;
    private BufferedImage pisztolyket;
    private BufferedImage pisztolyhar;

    /**
     * Konstruktor
     * @param kontroller
     */
    public TargyakPanel(Kontroller kontroller) {

        //Beállítja az egyedi UI-t
        this.setBackground(new Color(41, 54, 63));
        lapat.setUI(new CustomButtonUI());
        lapat.setText("Lapátol");
        sator.setUI(new CustomButtonUI());
        sator.setText("Épít");
        alkatresz.setUI(new CustomButtonUI());
        alkatresz.setText("Letesz");
        fel.setUI(new CustomButtonUI());
        jobbfel.setUI(new CustomButtonUI());
        jobb.setUI(new CustomButtonUI());
        jobble.setUI(new CustomButtonUI());
        le.setUI(new CustomButtonUI());
        balle.setUI(new CustomButtonUI());
        bal.setUI(new CustomButtonUI());
        balfel.setUI(new CustomButtonUI());



        try {
            pisztolyNULL = ImageIO.read(new File("Resources/Assets/Pisztoly_I_NULL-01.png"));
            pisztolyegy = ImageIO.read(new File("Resources/Assets/1-Pisztoly_I-01.png"));
            pisztolyket = ImageIO.read(new File("Resources/Assets/2-Pisztoly_I-01.png"));
            pisztolyhar = ImageIO.read(new File("Resources/Assets/3-Pisztoly_I-01.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
        retegek.add(pisztolyNULL);

        JPanel targyak = new JPanel();
        targyak.setBackground(new Color(41, 54, 63));
        targyak.setPreferredSize(new Dimension(220, 220));
        GridLayout gl1 = new GridLayout(2, 2);
        gl1.setHgap(15);
        gl1.setVgap(10);
        targyak.setLayout(gl1);

      //  lapat.setPreferredSize(new Dimension(80, 80));
        lapat.setIcon((lapatim));
        targyak.add(lapat);
        lapat.setActionCommand("lapatol");
        lapat.addActionListener(kontroller);


      //  sator.setPreferredSize(new Dimension(80, 80));
        sator.setIcon(satorimNULL);
        targyak.add(sator);
        sator.setActionCommand("satratEpit");
        sator.addActionListener(kontroller);

        //Kihúz panel irányonként egy gombbal
        JPanel targyak2 = new JPanel();
        targyak2.setBackground(new Color(41, 54, 63));
        GridLayout gl5 = new GridLayout(3, 3);
        gl5.setHgap(2);
        gl5.setVgap(2);
        targyak2.setLayout(gl5);


        //BalFel
        //balfel.setPreferredSize(new Dimension(60,60));
        balfel.setIcon(balfelimNULL);
        balfel.setActionCommand("BalFel");
        balfel.addActionListener(kontroller);
        targyak2.add(balfel);

        //Fel
        //  fel.setPreferredSize(new Dimension(60,60));
        fel.setIcon(felimNULL);
        fel.setActionCommand("Fel");
        fel.addActionListener(kontroller);
        targyak2.add(fel);

        //JobbFel
        //  jobbfel.setPreferredSize(new Dimension(60,60));
        jobbfel.setIcon(jobbfelimNULL);
        jobbfel.setActionCommand("JobbFel");
        jobbfel.addActionListener(kontroller);
        targyak2.add(jobbfel);


        //Bal
        // bal.setPreferredSize(new Dimension(60,60));
        bal.setIcon(balraimNULL);
        bal.setActionCommand("Bal");
        bal.addActionListener(kontroller);
        targyak2.add(bal);

        //kotel.setPreferredSize(new Dimension(60,60));
        kotel.setIcon(kotelim);
        targyak2.add(kotel);

        //Jobb
        //jobb.setPreferredSize(new Dimension(60,60));
        jobb.setIcon(jobbraimNULL);
        jobb.setActionCommand("Jobb");
        jobb.addActionListener(kontroller);
        targyak2.add(jobb);


        //BalLe
        //balle.setPreferredSize(new Dimension(60,60));
        balle.setIcon(balleimNULL);
        balle.setActionCommand("BalLe");
        balle.addActionListener(kontroller);
        targyak2.add(balle);

        //Le
        // le.setPreferredSize(new Dimension(60,60));
        le.setIcon(leimNULL);
        le.setActionCommand("Le");
        le.addActionListener(kontroller);
        targyak2.add(le);

        //jobble.setPreferredSize(new Dimension(60,60));
        jobble.setIcon(jobbleimNULL);
        jobble.setActionCommand("JobbLe");
        jobble.addActionListener(kontroller);
        targyak2.add(jobble);


        targyak.add(targyak2);
        //Az alkatrész lerak úgy van megírva, hogy bármennyi alkatrészünk van, 1 lerak() hívással a 0. indexűt rakjuk le.  Itt 1 alkatrészt
       // alkatresz.setPreferredSize(new Dimension(80, 80));
        //alkatresz.setIcon(pisztolyNULL);
        alkatresz.setActionCommand("lerak");
        alkatresz.addActionListener(kontroller);
        targyak.add(alkatresz);

        targyak.add(targyak2, BorderLayout.LINE_START);
        this.add(targyak);
    }

    /**
     * Frissíti a játkos paneljét
     * @param aktivJatekos
     */
    public void update(Jatekos aktivJatekos) {
        LapatVisitor lv = new LapatVisitor();
        SatorVisitor sv = new SatorVisitor();
        KotelVisitor kv = new KotelVisitor();
        AlkatreszVisitor av = new AlkatreszVisitor();
        fel.setIcon(felimNULL);
        jobbfel.setIcon(jobbfelimNULL);
        jobb.setIcon(jobbraimNULL);
        jobble.setIcon(jobbleimNULL);
        le.setIcon(leimNULL);
        balle.setIcon(balleimNULL);
        bal.setIcon(balraimNULL);
        balfel.setIcon(balfelimNULL);
        lapat.setIcon(lapatimNULL);
        sator.setIcon(satorimNULL);
       // alkatresz.setIcon(pisztolyNULL);
        kotel.setIcon(kotelimNULL);
        //TODO hogy kell összemergelni a képeket helyesen, ha csak két alkatrészem van pl.?
        boolean vankotel = false;
        for (Targy t : aktivJatekos.getTargyak()) {
            if (t.accept(lv)) lapat.setIcon(lapatim);
            else if (t.accept(sv)) sator.setIcon(satorim);
            else if (t.accept(kv)) {
                kotel.setIcon(kotelim);
                vankotel = true;
            }
        }
        Mezo m = aktivJatekos.getTartozkodasiMezo();
        if (vankotel) {
            if (m.getSzomszed(Irany.Fel) != null) fel.setIcon(felim);
            if (m.getSzomszed(Irany.JobbFel) != null) jobbfel.setIcon(jobbfelim);
            if (m.getSzomszed(Irany.Jobb) != null) jobb.setIcon(jobbraim);
            if (m.getSzomszed(Irany.JobbLe) != null) jobble.setIcon(jobbleim);
            if (m.getSzomszed(Irany.Le) != null) le.setIcon(leim);
            if (m.getSzomszed(Irany.BalLe) != null) balle.setIcon(balleim);
            if (m.getSzomszed(Irany.Bal) != null) bal.setIcon(balraim);
            if (m.getSzomszed(Irany.BalFel) != null) balfel.setIcon(balfelim);
        }

        retegek.clear();
        retegek.add(pisztolyNULL);
        for (Alkatresz a:aktivJatekos.getAlkatreszek()) {
            if (a.ID==0)retegek.add(pisztolyegy);
            if (a.ID==1)retegek.add(pisztolyket);
            if (a.ID==2)retegek.add(pisztolyhar);
        }
        revalidate();

    }

    /**
     * Kirajzolja a képeket
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage combined = new BufferedImage(pisztolyegy.getWidth(), pisztolyegy.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics ger = combined.getGraphics();
        for (BufferedImage img:retegek) {
            ger.drawImage(img,0,0,null);
        }
        ger.dispose();
        alkatresz.setIcon(new ImageIcon(combined));
    }
}