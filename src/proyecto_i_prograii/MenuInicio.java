package proyecto_i_prograii;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MenuInicio extends JFrame implements ActionListener {

    public Gestion usuarios;
    public String logged, selected;
    public String loggedType, selectedType;
    public ArrayList<String> logs = new ArrayList<>();

    private JPanel menuPanel;
    private JButton logInButton;
    private JButton crearPlayerButton;
    private JButton salirButton;

    public MenuInicio() {

        usuarios = new Gestion();

        setTitle("Menu Inicio");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new CardLayout());

        menuPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                ImageIcon img = new ImageIcon(getClass().getResource("/Img/Img8.jpeg"));
                g2d.drawImage(img.getImage(), 0, 0, getWidth(), getHeight(), this);
                g2d.dispose();
            }
        };
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setOpaque(false);

        menuPanel.add(Box.createVerticalStrut(70));

        ConfiLabel titulo = new ConfiLabel("MENU INICIO");
        titulo.setFont(new Font("Arial Black", Font.BOLD, 30));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setForeground(Color.WHITE);
        menuPanel.add(titulo);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setOpaque(false);

        logInButton = new ConfiBotones("LOG IN");
        crearPlayerButton = new ConfiBotones("CREAR PLAYER");
        salirButton = new ConfiBotones("SALIR");

        Dimension buttonSize = new Dimension(220, 50);
        logInButton.setPreferredSize(buttonSize);
        crearPlayerButton.setPreferredSize(buttonSize);
        salirButton.setPreferredSize(buttonSize);

        logInButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        crearPlayerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        salirButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        logInButton.addActionListener(this);
        crearPlayerButton.addActionListener(this);
        salirButton.addActionListener(this);

        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonsPanel.add(logInButton);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonsPanel.add(crearPlayerButton);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonsPanel.add(salirButton);

        menuPanel.add(Box.createVerticalGlue());
        menuPanel.add(buttonsPanel);
        menuPanel.add(Box.createVerticalGlue());

        add(menuPanel, "Menu");

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logInButton) {
            LogIn loginForm = new LogIn(this);
            loginForm.setVisible(true);
            this.dispose();
        } else if (e.getSource() == crearPlayerButton) {
            CrearPlayer crearPlayerForm = new CrearPlayer(this);
            crearPlayerForm.setVisible(true);
            this.dispose();
        } else if (e.getSource() == salirButton) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new MenuInicio();
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

    private static class ConfiLabel extends JLabel {
        public ConfiLabel(String texto) {
            super(texto);
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(Color.BLACK);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            g2.dispose();
            super.paintComponent(g);
        }
    }
}
