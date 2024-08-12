package proyecto_i_prograii;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipal extends JFrame implements ActionListener {

    private MenuInicio menuInicio;
    private JButton nuevaPartidaButton;
    private JButton cuentaButton;
    private JButton logoutButton;
    private JButton universoMarvelButton;

    public MenuPrincipal(MenuInicio menu) {
        this.menuInicio = menu;
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Menu Principal");
        setSize(500, 400);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                ImageIcon img = new ImageIcon(getClass().getResource("/Img/Img7.jpeg"));
                g2d.drawImage(img.getImage(), 0, 0, getWidth(), getHeight(), this);
                g2d.dispose();
            }
        };
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);

        RedondearLabel titulolabel = new RedondearLabel("MENU PRINCIPAL");
        titulolabel.setFont(new Font("Arial Black", Font.BOLD, 30));
        titulolabel.setForeground(Color.WHITE);
        titulolabel.setBackground(Color.BLACK);
        titulolabel.setOpaque(true);
        titulolabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        titulolabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulolabel.setPreferredSize(new Dimension(400, 60));

        mainPanel.add(Box.createVerticalStrut(50));
        mainPanel.add(titulolabel);
        mainPanel.add(Box.createVerticalStrut(30));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        nuevaPartidaButton = new ConfiBotones("PARTIDA NUEVA");
        cuentaButton = new ConfiBotones("MI PERFIL");
        universoMarvelButton = new ConfiBotones("UNIVERSO MARVEL");
        logoutButton = new ConfiBotones("CERRAR SESION");

        Dimension buttonSize = new Dimension(300, 75);
        nuevaPartidaButton.setPreferredSize(buttonSize);
        cuentaButton.setPreferredSize(buttonSize);
        universoMarvelButton.setPreferredSize(buttonSize);
        logoutButton.setPreferredSize(buttonSize);

        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonsPanel.add(nuevaPartidaButton, gbc);

        gbc.gridy++;
        buttonsPanel.add(cuentaButton, gbc);

        gbc.gridy++;
        buttonsPanel.add(universoMarvelButton, gbc);

        gbc.gridy++;
        buttonsPanel.add(logoutButton, gbc);

        buttonsPanel.setOpaque(false);

        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(buttonsPanel);
        mainPanel.add(Box.createVerticalGlue());

        add(mainPanel);

        nuevaPartidaButton.addActionListener(this);
        cuentaButton.addActionListener(this);
        logoutButton.addActionListener(this);
        universoMarvelButton.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nuevaPartidaButton) {
            if (menuInicio.usuarios.contarUser() >= 2) {
                ElejirUser user = new ElejirUser(menuInicio);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Se necesitan 2 usuario como minimo para iniciar una partida", null, JOptionPane.INFORMATION_MESSAGE);
            }

        } else if (e.getSource() == cuentaButton) {
            InfodeCuenta infoCuenta = new InfodeCuenta(menuInicio);
            infoCuenta.setVisible(true);
            this.dispose();

        } else if (e.getSource() == logoutButton) {
            int input = JOptionPane.showConfirmDialog(null, "¿Estas seguro que deseas cerrar sesion?");
            if (input == 0) {
                menuInicio.setVisible(true);
                this.dispose();
            }

        } else if (e.getSource() == universoMarvelButton) {
            UniversoMarvel u = new UniversoMarvel(menuInicio);
            u.setVisible(true);
            this.dispose();
        }
    }

    private static class ConfiBotones extends JButton {
        public ConfiBotones(String texto) {
            super(texto);
            setForeground(Color.WHITE);
            setFont(new Font("Arial Black", Font.BOLD, 26));
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
