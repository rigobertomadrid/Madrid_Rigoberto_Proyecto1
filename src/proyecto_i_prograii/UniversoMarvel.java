package proyecto_i_prograii;

import javax.swing.*;
import java.awt.*;

public class UniversoMarvel extends JFrame {
    private MenuInicio menuInicio;

    public UniversoMarvel(MenuInicio menu) {
        this.menuInicio = menu;
        initUI();
    }

    private void initUI() {
        setTitle("Universo Marvel");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon img = new ImageIcon(getClass().getResource("/Img/Img9.jpeg"));
                g.drawImage(img.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        RedondearLabel titulolabel = new RedondearLabel("UNIVERSO MARVEL");
        titulolabel.setFont(new Font("Arial Black", Font.BOLD, 30));
        titulolabel.setForeground(Color.WHITE);
        titulolabel.setBackground(Color.BLACK);
        titulolabel.setOpaque(true);
        titulolabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        mainPanel.add(titulolabel, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;

        JButton rankingButton = new ConfiBotones("RANKING");
        rankingButton.addActionListener(e -> menuInicio.usuarios.ranking());
        mainPanel.add(rankingButton, gbc);

        gbc.gridy++;
        JButton batallasButton = new ConfiBotones("BATALLAS");
        batallasButton.addActionListener(e -> menuInicio.usuarios.batallas());
        mainPanel.add(batallasButton, gbc);

        gbc.gridy++;
        JButton salirButton = new ConfiBotones("VOLVER");
        salirButton.addActionListener(e -> {
            MenuPrincipal mp = new MenuPrincipal(menuInicio);
            mp.setVisible(true);
            dispose();
        });
        mainPanel.add(salirButton, gbc);

        Dimension buttonSize = new Dimension(250, 60);
        rankingButton.setPreferredSize(buttonSize);
        batallasButton.setPreferredSize(buttonSize);
        salirButton.setPreferredSize(buttonSize);

        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private static class ConfiBotones extends JButton {
        public ConfiBotones(String text) {
            super(text);
            setForeground(Color.WHITE);
            setFont(new Font("Arial Black", Font.BOLD, 18));
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
        public RedondearLabel(String text) {
            super(text);
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
