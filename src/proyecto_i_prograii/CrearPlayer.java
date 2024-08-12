package proyecto_i_prograii;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CrearPlayer extends JFrame {
    private MenuInicio menuInicio;
    private JTextField newUsername;
    private JPasswordField newPassword;

    public CrearPlayer(MenuInicio menu) {
        this.menuInicio = menu;
        initUI();
    }

    private void initUI() {
        JPanel panelContenido = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon img = new ImageIcon(getClass().getResource("/Img/Img7.jpeg"));
                g.drawImage(img.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panelContenido.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 5, 10, 10);

        RedondearLabel titulolabel = new RedondearLabel("Crear Jugador");
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
        newUsername = new JTextField(15);
        newUsername.setFont(new Font("Arial Black", Font.BOLD, 11));
        panelContenido.add(newUsername, gbc);

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
        newPassword = new JPasswordField(15);
        newPassword.setFont(new Font("Arial Black", Font.BOLD, 11));
        panelContenido.add(newPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;

        JButton ingresarButton = new ConfiBotones("Crear Jugador");
        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jButton1ActionPerformed();
            }
        });
        panelContenido.add(ingresarButton, gbc);

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
        ingresarButton.setPreferredSize(buttonSize);
        salirButton.setPreferredSize(buttonSize);

        setContentPane(panelContenido);
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void jButton1ActionPerformed() {
        String nombre = newUsername.getText();
        String password = new String(newPassword.getPassword());

        if (password.length() != 5) {
            JOptionPane.showMessageDialog(null, "La contraseña tiene que tener exactamente 5 caracteres", null, JOptionPane.INFORMATION_MESSAGE);
        } else {
            if (menuInicio.usuarios.addUser(nombre, password)) {
                JOptionPane.showMessageDialog(null, "Usuario agregado correctamente", null, JOptionPane.INFORMATION_MESSAGE);
                menuInicio.setVisible(true);
                newUsername.setText("");
                newPassword.setText("");
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "El usuario ya existe", null, JOptionPane.INFORMATION_MESSAGE);
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
