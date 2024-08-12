package proyecto_i_prograii;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ElejirBando {

    MenuInicio menuInicio;

    public ElejirBando(MenuInicio menu) {
        this.menuInicio = menu;

        JFrame frame = new JFrame("Escoger el Bando");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 250);
        frame.setLocationRelativeTo(null);

        JButton heroe = new ConfiBotones("HEROES");
        JButton villano = new ConfiBotones("VILLANOS");

        heroe.addActionListener((ActionEvent e) -> {
            menuInicio.loggedType = "Heroe";
            menuInicio.selectedType = "Villano";
            new Tablero(menuInicio);
            frame.dispose();
        });

        villano.addActionListener((ActionEvent e) -> {
            menuInicio.loggedType = "Villano";
            menuInicio.selectedType = "Heroe";
            new Tablero(menuInicio);
            frame.dispose();
        });

        JPanel panelContenido = new JPanel();
        panelContenido.setBackground(new Color(0, 102, 204));
        panelContenido.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);

        RedondearLabel titulolabel = new RedondearLabel("ESCOGER EL BANDO");
        titulolabel.setFont(new Font("Arial Black", Font.BOLD, 22));
        titulolabel.setForeground(Color.WHITE);
        titulolabel.setBackground(Color.BLACK);
        titulolabel.setOpaque(true);
        titulolabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        titulolabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelContenido.add(titulolabel, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panelContenido.add(heroe, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panelContenido.add(villano, gbc);

        Dimension buttonSize = new Dimension(150, 40);
        heroe.setPreferredSize(buttonSize);
        villano.setPreferredSize(buttonSize);

        frame.getContentPane().add(panelContenido);
        frame.setVisible(true);
    }

    private static class ConfiBotones extends JButton {
        public ConfiBotones(String texto) {
            super(texto);
            setForeground(Color.WHITE);
            setFont(new Font("Arial Black", Font.BOLD, 16));
            setContentAreaFilled(false);
            setOpaque(false);
            setBorderPainted(false);
            setFocusPainted(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(new Color(255, 0, 0));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    private static class RedondearLabel extends JLabel {
        public RedondearLabel(String texto) {
            super(texto);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            g2.setColor(getForeground());
            g2.setFont(getFont());
            FontMetrics fm = g2.getFontMetrics();
            int textX = (getWidth() - fm.stringWidth(getText())) / 2;
            int textY = (getHeight() + fm.getAscent()) / 2;
            g2.drawString(getText(), textX, textY);
            g2.dispose();
        }
    }
}
