package com.company;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class CustomButtonUI extends BasicButtonUI implements java.io.Serializable,
        MouseListener, KeyListener {

    private final static CustomButtonUI m_buttonUI = new CustomButtonUI();
    protected Border m_borderRaised = BorderFactory.createLineBorder(Color.WHITE, 2);
    protected Border m_borderLowered = BorderFactory.createLineBorder(new Color(85, 192, 136), 2);
    protected Color m_backgroundNormal = new Color(41, 54, 63);
    protected Color m_backgroundPressed = new Color(16, 29, 38);
    protected Color m_backgroundActive = new Color(28, 41, 50);
    protected Color m_foregroundNormal = new Color(255, 255, 255);
    protected Color m_foregroundActive = new Color(85, 192, 136, 255);
    protected Color m_focusBorder = Color.WHITE;
    protected Font m_font = new Font("Century Gothic", Font.BOLD, 20);
    private volatile boolean entered;
    private boolean hidden;

    public CustomButtonUI(boolean hidden) {
        this.hidden = hidden;
    }

    public CustomButtonUI() {
        hidden = true;
    }

    public static ComponentUI createUI(JComponent c) {
        return m_buttonUI;
    }

    public void installUI(JComponent c) {
        super.installUI(c);

        c.addMouseListener(this);
        c.addKeyListener(this);
        c.setBackground(m_backgroundNormal);
        c.setBorder(m_borderRaised);
        c.setForeground(m_foregroundNormal);
    }

    public void uninstallUI(JComponent c) {
        super.uninstallUI(c);
        c.removeMouseListener(this);
        c.removeKeyListener(this);
    }

    public void paint(Graphics g, JComponent c) {
        //super.paint(g, c);
        AbstractButton b = (AbstractButton) c;
        Dimension d = b.getSize();
        g.setFont(m_font);
        FontMetrics fm = g.getFontMetrics();

        g.setColor(b.getForeground());

        //Ha van rajta ikon azt is kirajzolja
        Icon icon = b.getIcon();
        if (icon != null) {
            int w = icon.getIconHeight();
            int h = icon.getIconWidth();
            int cw = c.getWidth();
            int ch = c.getHeight();

            //Hogy jó heéyre rajzolja az ikonokat
            icon.paintIcon(c, g, (cw - w) / 2, (ch - h) / 2);
        }

        //Ki kell rajzolni a szöveget, ha nem hidden mindig, ha hidden csak akkor ha rajta van az egér
        String caption;
        if (entered) {
            caption = b.getText();
            g.setColor(m_foregroundActive);
        } else {
            if (hidden) caption = "";
            else caption = b.getText();
            g.setColor(m_foregroundNormal);
        }

        //Le kell csökkenteni a betűméretet ha túl nagy
        while (true)
            if (fm.stringWidth(caption) > d.getWidth() - 10) {
                m_font = new Font(m_font.getName(), m_font.getStyle(), m_font.getSize() - 2);
                g.setFont(m_font);
                fm = g.getFontMetrics(g.getFont());
            }
            else {
                break;
            }

        int x = (d.width - fm.stringWidth(caption)) / 2;
        int y = (d.height + fm.getAscent()) / 2;
        g.drawString(caption, x, y);

        m_font = new Font(m_font.getName(), m_font.getStyle(), 20);
    }

    public Dimension getPreferredSize(JComponent c) {
        Dimension d = super.getPreferredSize(c);
        if (m_borderRaised != null) {
            Insets ins = m_borderRaised.getBorderInsets(c);
            d.setSize(d.width + ins.left + ins.right, d.height + ins.top
                    + ins.bottom);
        }
        return d;
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        JComponent c = (JComponent) e.getComponent();
        c.setBorder(m_borderLowered);
        c.setBackground(m_backgroundPressed);
    }

    public void mouseReleased(MouseEvent e) {
        JComponent c = (JComponent) e.getComponent();
        c.setBorder(m_borderLowered);
        c.setBackground(m_backgroundActive);
    }

    public void mouseEntered(MouseEvent e) {
        JComponent c = (JComponent) e.getComponent();
        c.setForeground(m_foregroundActive);
        c.setBackground(m_backgroundActive);
        c.setBorder(m_borderLowered);
        entered = true;
        c.repaint();
    }

    public void mouseExited(MouseEvent e) {
        JComponent c = (JComponent) e.getComponent();
        c.setForeground(m_foregroundNormal);
        c.setBackground(m_backgroundNormal);
        c.setBorder(m_borderRaised);
        entered = false;
        c.repaint();
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
            JComponent c = (JComponent) e.getComponent();
            c.setBorder(m_borderLowered);
            c.setBackground(m_backgroundPressed);
        }
    }

    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
            JComponent c = (JComponent) e.getComponent();
            c.setBorder(m_borderRaised);
            c.setBackground(m_backgroundNormal);
        }
    }
}