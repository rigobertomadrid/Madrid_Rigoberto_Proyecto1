package proyecto_i_prograii;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogIn extends JFrame {
    private MenuInicio menuInicio;
    private JTextField username;
    private JPasswordField pass;

    public LogIn(MenuInicio menu) {
        this.menuInicio = menu;
        initUI();
    }

    private void initUI() {
        JPanel panelContenido = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon img = new ImageIcon(getClass().getResource("/Img/Img6.jpeg"));
                g.drawImage(img.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panelContenido.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 5, 10, 10);

        RedondearLabel titulolabel = new RedondearLabel("Login");
        titulolabel.setFont(new Font("Arial Black", Font.BOLD, 30));
        titulolabel.setForeground(Color.WHITE);
        titulolabel.setBackground(Color.BLACK);
        titulolabel.setOpaque(true);
        titulolabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulolabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelContenido.add(titulolabel, gbc);

        gbc.gridy++;
        panelContenido.add(Box.createVerticalStrut(20), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel usernameLabel = new RedondearLabel("Nombre de Usuario:");
        estiloLabel(usernameLabel);
        panelContenido.add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        username = new JTextField(15);
        username.setFont(new Font("Arial Black", Font.BOLD, 11));
        panelContenido.add(username, gbc);

        gbc.gridy++;
        panelContenido.add(Box.createVerticalStrut(50), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel passwordLabel = new RedondearLabel("Contraseña:");
        estiloLabel(passwordLabel);
        panelContenido.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        pass = new JPasswordField(15);
        pass.setFont(new Font("Arial Black", Font.BOLD, 11));
        panelContenido.add(pass, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;

        JButton entrarButton = new ConfiBotones("Iniciar Sesion");
        entrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                entrarButtonActionPerformed();
            }
        });
        panelContenido.add(entrarButton, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;

        JButton salirButton = new ConfiBotones("Volver");
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salirButtonActionPerformed();
            }
        });
        panelContenido.add(salirButton, gbc);

        Dimension buttonSize = new Dimension(200, 50);
        entrarButton.setPreferredSize(buttonSize);
        salirButton.setPreferredSize(buttonSize);

        setContentPane(panelContenido);
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void entrarButtonActionPerformed() {
        String name = username.getText();
        String password = new String(pass.getPassword());

        if (!menuInicio.usuarios.checkName(name)) {
            JOptionPane.showMessageDialog(null, "El usuario no existe o esta ingresado incorrectamente", null, JOptionPane.INFORMATION_MESSAGE);
        } else {
            if (menuInicio.usuarios.checkPass(name, password)) {
                menuInicio.logged = name;
                MenuPrincipal menuPrincipal = new MenuPrincipal(menuInicio);
                menuPrincipal.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Contraseña incorrecta", null, JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void salirButtonActionPerformed() {
        menuInicio.setVisible(true);
        dispose();
    }

    private void estiloLabel(JLabel label) {
        label.setFont(new Font("Arial Black", Font.BOLD, 16));
        label.setForeground(Color.WHITE);
        label.setBackground(Color.BLACK);
        label.setOpaque(true);
        label.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        label.setHorizontalAlignment(SwingConstants.CENTER);
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
