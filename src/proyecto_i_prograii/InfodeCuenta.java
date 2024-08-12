package proyecto_i_prograii;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InfodeCuenta extends JFrame implements ActionListener {

    MenuInicio menuInicio;

    private JButton cambiarPasswordButton;
    private JButton eliminarCuentaButton;
    private JButton verLogsButton;
    private JButton salirButton;

    public InfodeCuenta(MenuInicio menu) {
        this.menuInicio = menu;
        initComponents();
    }

    private void initComponents() {
        setTitle("Mi Perfil");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon img = new ImageIcon(getClass().getResource("/Img/Img1.jpg"));
                if (img.getImage() != null) {
                    g.drawImage(img.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        RedondearLabel tituloLabel = new RedondearLabel("MI PERFIL");
        tituloLabel.setFont(new Font("Arial Black", Font.BOLD, 30));
        tituloLabel.setForeground(Color.WHITE);
        tituloLabel.setBackground(Color.BLACK);
        tituloLabel.setOpaque(true);
        tituloLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(tituloLabel, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;

        cambiarPasswordButton = new ConfiBotones("CAMBIAR CONTRASEÑA");
        eliminarCuentaButton = new ConfiBotones("ELIMINAR CUENTA");
        verLogsButton = new ConfiBotones("VER MIS LOGS");
        salirButton = new ConfiBotones("VOLVER");

        Dimension buttonSize = new Dimension(250, 60);
        cambiarPasswordButton.setPreferredSize(buttonSize);
        eliminarCuentaButton.setPreferredSize(buttonSize);
        verLogsButton.setPreferredSize(buttonSize);
        salirButton.setPreferredSize(buttonSize);

        cambiarPasswordButton.addActionListener(this);
        eliminarCuentaButton.addActionListener(this);
        verLogsButton.addActionListener(this);
        salirButton.addActionListener(this);

        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(cambiarPasswordButton, gbc);
        gbc.gridx = 1;
        mainPanel.add(eliminarCuentaButton, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(verLogsButton, gbc);
        gbc.gridx = 1;
        mainPanel.add(salirButton, gbc);

        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cambiarPasswordButton) {
            cambiarPassword();
        } else if (e.getSource() == eliminarCuentaButton) {
            eliminarCuenta();
        } else if (e.getSource() == verLogsButton) {
            verLogs();
        } else if (e.getSource() == salirButton) {
            MenuPrincipal menuPrincipal = new MenuPrincipal(menuInicio);
            menuPrincipal.setVisible(true);
            this.dispose();
        }
    }

    private void cambiarPassword() {
        String name = JOptionPane.showInputDialog("Nombre de usuario");
        String oldPass = JOptionPane.showInputDialog("Contraseña actual");

        if (!menuInicio.usuarios.checkName(name)) {
            JOptionPane.showMessageDialog(null, "El usuario no existe o esta ingresado incorrectamente", null, JOptionPane.INFORMATION_MESSAGE);
        } else {
            if (menuInicio.usuarios.checkPass(name, oldPass)) {
                String newPass = JOptionPane.showInputDialog("Nueva contraseña");
                if (newPass != null && newPass.length() == 5) {
                    for (int i = 0; i < menuInicio.usuarios.registro.length; i++) {
                        if (menuInicio.usuarios.registro[i] != null) {
                            if (name.equals(menuInicio.usuarios.registro[i].nombre) 
                                    && oldPass.equals(menuInicio.usuarios.registro[i].contra)) {
                                menuInicio.usuarios.registro[i].contra = newPass;
                                JOptionPane.showMessageDialog(null, "Contraseña cambiada exitosamente", null, JOptionPane.INFORMATION_MESSAGE);
                                break;
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "La contraseña debe tener exactamente 5 caracteres", null, JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Contraseña Incorrecta", null, JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void eliminarCuenta() {
        int input = JOptionPane.showConfirmDialog(null, "¿Desea usted continuar?");

        if (input == 0) {
            String name = JOptionPane.showInputDialog("Ingresar nombre de usuario");
            String oldPass = JOptionPane.showInputDialog("Ingresar contraseña");

            if (!menuInicio.usuarios.checkName(name)) {
                JOptionPane.showMessageDialog(null, "El usuario no existe o esta ingresado incorrectamente", null, JOptionPane.INFORMATION_MESSAGE);
            } else {
                if (menuInicio.usuarios.checkPass(name, oldPass)) {
                    for (int i = 0; i < menuInicio.usuarios.registro.length; i++) {
                        if (menuInicio.usuarios.registro[i] != null) {
                            if (name.equals(menuInicio.usuarios.registro[i].nombre) 
                                    && oldPass.equals(menuInicio.usuarios.registro[i].contra)) {
                                menuInicio.usuarios.registro[i].nombre = null;
                                menuInicio.usuarios.registro[i].contra = null;
                                menuInicio.usuarios.eliminado += 1;
                                menuInicio.setVisible(true);
                                this.dispose();
                                JOptionPane.showMessageDialog(null, "Cuenta eliminada correctamente", null, JOptionPane.INFORMATION_MESSAGE);
                                break;
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Contraseña Incorrecta", null, JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    private void verLogs() {
        String logs = "";
        for (String str : menuInicio.logs) {
            if (str.contains(menuInicio.logged))
                logs += str + "\n";
        }
        JOptionPane.showMessageDialog(null, logs, "Ultimos Logs", JOptionPane.INFORMATION_MESSAGE);
    }

    private static class ConfiBotones extends JButton {
        public ConfiBotones(String text) {
            super(text);
            setForeground(Color.WHITE);
            setFont(new Font("Arial Black", Font.BOLD, 15));
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
