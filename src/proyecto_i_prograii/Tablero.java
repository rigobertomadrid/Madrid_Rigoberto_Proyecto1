package proyecto_i_prograii;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Tablero extends JPanel {

    MenuInicio menuInicio;

    private JButton[][] botones;
    private Ficha[][] fichas;
    private Random random;
    private boolean turnoHeroe = true;
    private ImageIcon ocultoIcono;

    private Ficha fichaSeleccionada = null;
    private int filaSeleccionada = -1;
    private int columnaSeleccionada = -1;
    private boolean esTurnoHeroe = true;
    private boolean movimientoRealizado = false;

    private Set<JButton> botonesResaltados = new HashSet<>();
    private JLabel infoImagenLabel;
    private JLabel infoRangoLabel;
    private JLabel infoTipoLabel;
    private JLabel infoNombreLabel;

    public Tablero(MenuInicio menu) {
        this.menuInicio = menu;
        crearTablero();
    }

    private JPanel panelFichasVencidasHeroe;
    private JPanel panelFichasVencidasVillano;
    private JPanel infoPanel;

    private void crearTablero() {
        int tamanoTablero = 980;
        int tamanoBoton = 65;
        int tamanoPanelLateral = 200;

        JFrame frame = new JFrame("STRATEGO MARVEL");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(tamanoPanelLateral, tamanoTablero));

        panelFichasVencidasHeroe = new JPanel(new GridLayout(0, 2, 5, 5));  
        panelFichasVencidasHeroe.setPreferredSize(new Dimension(tamanoPanelLateral, (int) (tamanoTablero / 1.2)));
        panelFichasVencidasHeroe.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE), "Heroes Vencidos", 0, 0, new Font("Arial", Font.BOLD, 16), Color.WHITE));
        panelFichasVencidasHeroe.setBackground(Color.BLUE);

        JLabel heroeLabel = new JLabel("Heroe: " + (menuInicio.loggedType.equals("Heroe") ? menuInicio.logged : menuInicio.selected));
        heroeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        heroeLabel.setForeground(Color.WHITE);
        heroeLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        heroeLabel.setOpaque(true);
        heroeLabel.setBackground(Color.BLUE);
        leftPanel.add(heroeLabel, BorderLayout.NORTH);

        leftPanel.add(panelFichasVencidasHeroe, BorderLayout.CENTER);

        infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE), "Informacion de la Ficha", 0, 0, new Font("Arial", Font.BOLD, 16), Color.WHITE));
        infoPanel.setPreferredSize(new Dimension(tamanoPanelLateral, (int) (tamanoTablero / 3.5)));
        infoPanel.setBackground(Color.BLUE);

        infoImagenLabel = new JLabel();
        infoRangoLabel = new JLabel("Rango: ");
        infoRangoLabel.setForeground(Color.WHITE);
        infoTipoLabel = new JLabel("Tipo: ");
        infoTipoLabel.setForeground(Color.WHITE);
        infoNombreLabel = new JLabel("Nombre: ");
        infoNombreLabel.setForeground(Color.WHITE);

        infoPanel.add(infoImagenLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(infoRangoLabel);
        infoPanel.add(infoTipoLabel);
        infoPanel.add(infoNombreLabel);

        leftPanel.add(infoPanel, BorderLayout.SOUTH);

        JPanel villanoPanel = new JPanel(new BorderLayout());
        villanoPanel.setPreferredSize(new Dimension(tamanoPanelLateral, tamanoTablero));
        villanoPanel.setBackground(Color.BLUE);

        JLabel villanoLabel = new JLabel("Villano: " + (menuInicio.loggedType.equals("Villano") ? menuInicio.logged : menuInicio.selected));
        villanoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        villanoLabel.setForeground(Color.WHITE);
        villanoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        villanoPanel.add(villanoLabel, BorderLayout.NORTH);

        panelFichasVencidasVillano = new JPanel(new GridLayout(0, 2, 5, 5)); 
        panelFichasVencidasVillano.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE), "Villanos Vencidos", 0, 0, new Font("Arial", Font.BOLD, 16), Color.WHITE));
        panelFichasVencidasVillano.setBackground(Color.BLUE);
        villanoPanel.add(panelFichasVencidasVillano, BorderLayout.CENTER);

        JPanel tableroPanel = new JPanel(new GridLayout(10, 10));
        tableroPanel.setPreferredSize(new Dimension(tamanoTablero, tamanoTablero));
        setLayout(new GridLayout(10, 10));
        botones = new JButton[10][10];
        fichas = new Ficha[2][40];
        random = new Random();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                botones[i][j] = new JButton();
                botones[i][j].setPreferredSize(new Dimension(tamanoBoton, tamanoBoton));
                final int fila = i;
                final int columna = j;
                botones[i][j].addActionListener(e -> manejarMovimiento(fila, columna));
                tableroPanel.add(botones[i][j]);
            }
        }

        cargarFichas(tamanoBoton);
        colocarImagenes();
        actualizarVisibilidadFichas();

        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.setPreferredSize(new Dimension(tamanoTablero, 80));

        JButton rendirseButton = new JButton("Rendirse");
        rendirseButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
        rendirseButton.setForeground(Color.WHITE);
        rendirseButton.setBackground(Color.RED);
        rendirseButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(rendirseButton);
        controlPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(tableroPanel, BorderLayout.CENTER);
        frame.add(villanoPanel, BorderLayout.EAST);
        frame.add(controlPanel, BorderLayout.SOUTH);

        rendirseButton.addActionListener(e -> manejarRendicion());

        frame.setVisible(true);
    }

    private void manejarRendicion() {
        String ganador, perdedor, tipoGanador, tipoPerdedor;

        if (esTurnoHeroe && menuInicio.loggedType.equals("Heroe")) {
            perdedor = menuInicio.logged; 
            tipoPerdedor = menuInicio.loggedType;
            ganador = menuInicio.selected;
            tipoGanador = menuInicio.selectedType;
        } else if (esTurnoHeroe && menuInicio.selectedType.equals("Heroe")) {
            perdedor = menuInicio.selected;
            tipoPerdedor = menuInicio.selectedType;
            ganador = menuInicio.logged;
            tipoGanador = menuInicio.loggedType;
        } else if (!esTurnoHeroe && menuInicio.loggedType.equals("Villano")) {
            perdedor = menuInicio.logged;
            tipoPerdedor = menuInicio.loggedType;
            ganador = menuInicio.selected;
            tipoGanador = menuInicio.selectedType;
        } else {
            perdedor = menuInicio.selected;
            tipoPerdedor = menuInicio.selectedType;
            ganador = menuInicio.logged;
            tipoGanador = menuInicio.loggedType;
        }

        menuInicio.usuarios.registrarVictoria(tipoGanador);

        menuInicio.usuarios.puntos(ganador);

        String fecha = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());

        String mensaje = String.format("%s usando %s ha ganado ya que %s usando %s se ha retirado del juego – %s",
                ganador, tipoGanador, perdedor, tipoPerdedor, fecha);

        JOptionPane.showMessageDialog(this, mensaje, "Juego Terminado", JOptionPane.INFORMATION_MESSAGE);

        menuInicio.logs.add(mensaje);

        MenuPrincipal menuPrincipal = new MenuPrincipal(menuInicio);
        menuPrincipal.setVisible(true);

        for (Frame f : JFrame.getFrames()) {
            if (f.isVisible()) {
                f.dispose();
                break;
            }
        }
    }

    private Ficha obtenerFicha(int fila, int columna) {
        JButton boton = botones[fila][columna];
        return (Ficha) boton.getClientProperty("ficha");
    }

    private void actualizarVisibilidadFichas() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                JButton boton = botones[i][j];
                Ficha ficha = obtenerFicha(i, j);
                if (ficha != null) {
                    if (esTurnoHeroe && ficha.esVillano()) {
                        ficha.setVisibleIcono(boton, false);
                    } else if (!esTurnoHeroe && !ficha.esVillano()) {
                        ficha.setVisibleIcono(boton, false);
                    } else {
                        ficha.setVisibleIcono(boton, true);
                    }
                }
            }
        }
    }

    private void cargarFichas(int tamanoBoton) {
        int fichaTamano = tamanoBoton;

        ocultoIcono = new ImageIcon(getClass().getResource("/Img/Img12.png"));
        Image ocultoImg = ocultoIcono.getImage().getScaledInstance(fichaTamano, fichaTamano, java.awt.Image.SCALE_SMOOTH);
        ocultoIcono = new ImageIcon(ocultoImg);

        Personaje[] personajesVillanos = {
            Personaje.PLANET_EARTH_V, Personaje.DR_DOOM, Personaje.GALACTUS, Personaje.KING_PIN, 
            Personaje.MAGNETO, Personaje.APOCALYPSE, Personaje.GREEN_GOBLIN, Personaje.VENOM, 
            Personaje.BULLSEYE, Personaje.OMEGA_RED, Personaje.ONSLAUGHT, Personaje.RED_SKULL, 
            Personaje.MYSTIQUE, Personaje.MYSTERIO, Personaje.DR_OCTOPUS, Personaje.DEADPOOL, 
            Personaje.ABOMINATION, Personaje.THANOS, Personaje.BLACK_CAT, Personaje.SABRETOOTH, 
            Personaje.JUGGERNAUT, Personaje.RHINO, Personaje.CARNAGE, Personaje.MOLE_MAN, 
            Personaje.LIZARD, Personaje.MR_SINISTER, Personaje.SENTINEL1, Personaje.ULTRON, 
            Personaje.SANDMAN, Personaje.LEADER, Personaje.VIPER, Personaje.SENTINEL2, 
            Personaje.ELECTRO, Personaje.BLACK_WIDOW_V, Personaje.PUMPKIN_BOMB, Personaje.PUMPKIN_BOMB1, 
            Personaje.PUMPKIN_BOMB2, Personaje.PUMPKIN_BOMB3, Personaje.PUMPKIN_BOMB4, Personaje.PUMPKIN_BOMB5
        };

        Personaje[] personajesHeroes = {
            Personaje.PLANET_EARTH_H, Personaje.MR_FANTASTIC, Personaje.CAPTAIN_AMERICA, Personaje.PROFESSOR_X, 
            Personaje.NICK_FURY, Personaje.SPIDER_MAN, Personaje.WOLVERINE, Personaje.NAMOR, 
            Personaje.DAREDEVIL, Personaje.SILVER_SURFER, Personaje.HULK, Personaje.IRON_MAN, 
            Personaje.THOR, Personaje.HUMAN_TORCH, Personaje.CYCLOPS, Personaje.INVISIBLE_WOMAN, 
            Personaje.GHOST_RIDER, Personaje.PUNISHER, Personaje.BLADE, Personaje.THING, 
            Personaje.EMMA_FROST, Personaje.SHE_HULK, Personaje.GIANT_MAN, Personaje.BEAST, 
            Personaje.COLOSSUS, Personaje.GAMBIT, Personaje.SPIDER_GIRL, Personaje.ICE_MAN, 
            Personaje.STORM, Personaje.PHOENIX, Personaje.DR_STRANGE, Personaje.ELEKTRA, 
            Personaje.NIGHTCRAWLER, Personaje.BLACK_WIDOW_H, Personaje.NOVA_BLAST1, Personaje.NOVA_BLAST2, 
            Personaje.NOVA_BLAST3, Personaje.NOVA_BLAST4, Personaje.NOVA_BLAST5, Personaje.NOVA_BLAST6
        };

        for (int i = 0; i < 2; i++) {
            Personaje[] personajes = i == 0 ? personajesVillanos : personajesHeroes;
            for (int j = 0; j < personajes.length; j++) {
                String nombreImagen = (i == 0 ? "Vill" : "Her") + " - " + (j + 1) + ".png";
                ImageIcon icon = new ImageIcon(getClass().getResource("/Img/" + nombreImagen));
                Image image = icon.getImage();
                Image newimg = image.getScaledInstance(fichaTamano, fichaTamano, java.awt.Image.SCALE_SMOOTH);
                icon = new ImageIcon(newimg);

                Ficha ficha = i == 0
                        ? new FichaVillano(personajes[j], icon, ocultoIcono)
                        : new FichaHeroe(personajes[j], icon, ocultoIcono);

                fichas[i][j] = ficha;
            }
        }
    }

    private void colocarImagenes() {
        Set<Point> ocupadosVillanos = new HashSet<>();
        Set<Point> ocupadosHeroes = new HashSet<>();

        colocarTierraYBombas(0, ocupadosVillanos, true);
        colocarTierraYBombas(9, ocupadosHeroes, false);

        colocarImagenesRango(2, 3, 26, 33, ocupadosVillanos, true);
        colocarImagenesRango(6, 7, 26, 33, ocupadosHeroes, false);

        colocarImagenesResto(0, 3, 2, 25, ocupadosVillanos, true);
        colocarImagenesResto(6, 9, 2, 25, ocupadosHeroes, false);

        colocarProhibidos();
    }

    private void colocarTierraYBombas(int fila, Set<Point> ocupados, boolean villano) {
        int columnaTierra = random.nextInt(8) + 1;
        Ficha tierraFicha = fichas[villano ? 0 : 1][0];
        botones[fila][columnaTierra].setIcon(tierraFicha.getIcono());
        botones[fila][columnaTierra].putClientProperty("ficha", tierraFicha);
        ocupados.add(new Point(fila, columnaTierra));

        int[] bombas = {35, 36, 37, 38, 39, 40};
        int contadorBombas = 0;

        int[][] direcciones = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1},
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
        };

        for (int[] direccion : direcciones) {
            int filaBomba = fila + direccion[0];
            int columnaBomba = columnaTierra + direccion[1];
            if (filaBomba >= 0 && filaBomba < 10 && columnaBomba >= 0 && columnaBomba < 10) {
                Ficha bombaFicha = fichas[villano ? 0 : 1][bombas[contadorBombas] - 1];
                botones[filaBomba][columnaBomba].setIcon(bombaFicha.getIcono());
                botones[filaBomba][columnaBomba].putClientProperty("ficha", bombaFicha);
                ocupados.add(new Point(filaBomba, columnaBomba));
                contadorBombas++;
                if (contadorBombas == 5) break;
            }
        }

        colocarBombaRestante(fila == 0 ? 1 : 8, ocupados, villano);
    }

    private void colocarBombaRestante(int fila, Set<Point> ocupados, boolean villano) {
        int colBombaExtra;
        do {
            colBombaExtra = random.nextInt(10);
        } while (ocupados.contains(new Point(fila, colBombaExtra)));
        Ficha bombaFichaExtra = fichas[villano ? 0 : 1][39];
        botones[fila][colBombaExtra].setIcon(bombaFichaExtra.getIcono());
        botones[fila][colBombaExtra].putClientProperty("ficha", bombaFichaExtra);
        ocupados.add(new Point(fila, colBombaExtra));
    }

    private void colocarImagenesRango(int filaInicio, int filaFin, int inicioImg, int finImg, Set<Point> ocupados, boolean villano) {
        int bando = villano ? 0 : 1;
        for (int i = inicioImg - 1; i < finImg; i++) {
            int fila, col;
            do {
                fila = random.nextInt(filaFin - filaInicio + 1) + filaInicio;
                col = random.nextInt(10);
            } while (ocupados.contains(new Point(fila, col)));
            Ficha ficha = fichas[bando][i];
            botones[fila][col].setIcon(ficha.getIcono());
            botones[fila][col].putClientProperty("ficha", ficha);
            ocupados.add(new Point(fila, col));
        }
    }

    private void colocarImagenesResto(int filaInicio, int filaFin, int inicioImg, int finImg, Set<Point> ocupados, boolean villano) {
        int bando = villano ? 0 : 1;
        for (int i = inicioImg - 1; i < finImg; i++) {
            int fila, col;
            do {
                fila = random.nextInt(filaFin - filaInicio + 1) + filaInicio;
                col = random.nextInt(10);
            } while (ocupados.contains(new Point(fila, col)));
            Ficha ficha = fichas[bando][i];
            botones[fila][col].setIcon(ficha.getIcono());
            botones[fila][col].putClientProperty("ficha", ficha);
            ocupados.add(new Point(fila, col));
        }

        int fila34, col34;
        do {
            fila34 = random.nextInt(filaFin - filaInicio + 1) + filaInicio;
            col34 = random.nextInt(10);
        } while (ocupados.contains(new Point(fila34, col34)));
        Ficha ficha34 = fichas[bando][33];
        botones[fila34][col34].setIcon(ficha34.getIcono());
        botones[fila34][col34].putClientProperty("ficha", ficha34);
        ocupados.add(new Point(fila34, col34));
    }

    private void colocarProhibidos() {
        int[][] zonasProhibidas = {
            {4, 2}, {4, 3}, {5, 2}, {5, 3},
            {4, 6}, {4, 7}, {5, 6}, {5, 7}
        };

        for (int[] zona : zonasProhibidas) {
            int fila = zona[0];
            int col = zona[1];
            ImageIcon icon;
            if (col >= 6) {
                icon = new ImageIcon(getClass().getResource("/Img/Img11.png"));
            } else {
                icon = new ImageIcon(getClass().getResource("/Img/Img10.png"));
            }
            Image image = icon.getImage();
            Image newimg = image.getScaledInstance(65, 65, java.awt.Image.SCALE_SMOOTH);
            icon = new ImageIcon(newimg);
            botones[fila][col].setIcon(icon);
        }
    }

    private void manejarMovimiento(int fila, int columna) {
        Ficha ficha = obtenerFicha(fila, columna);

        if (ficha != null && !ficha.esOculto()) {
            mostrarInformacionFicha(ficha);
        } else {
            clearInformacionFicha();
        }

        if (fichaSeleccionada == null) {
            if (ficha != null && fichaPerteneceATurnoActual(ficha)) {
                if (fichaEsInmovil(ficha)) {
                    return;
                }
                fichaSeleccionada = ficha;
                filaSeleccionada = fila;
                columnaSeleccionada = columna;
                resaltarMovimientosValidos(fila, columna);
            }
        } else if (ficha != null && fichaPerteneceATurnoActual(ficha)) {
            restaurarColorOriginal();
            if (fichaEsInmovil(ficha)) {
                return;
            }
            fichaSeleccionada = ficha;
            filaSeleccionada = fila;
            columnaSeleccionada = columna;
            resaltarMovimientosValidos(fila, columna);
        } else {
            if (ficha != null && fichaPerteneceATurnoActual(fichaSeleccionada) && !fichaPerteneceATurnoActual(ficha)) {
                realizarAtaque(fila, columna);
            } else if (esMovimientoValido(fila, columna)) {
                moverFicha(fila, columna);
                movimientoRealizado = true;
                cambiarTurno();
                restaurarColorOriginal();
            }
        }
    }

    private void mostrarInformacionFicha(Ficha ficha) {
        infoImagenLabel.setIcon(ficha.getIcono());
        infoRangoLabel.setText("Rango: " + ficha.getRango());
        infoTipoLabel.setText("Tipo: " + (ficha.esVillano() ? "Villano" : "Heroe"));
        infoNombreLabel.setText("Nombre: " + ficha.getNombre());
    }

    private void clearInformacionFicha() {
        infoImagenLabel.setIcon(null);
        infoRangoLabel.setText("Rango: ");
        infoTipoLabel.setText("Tipo: ");
        infoNombreLabel.setText("Nombre: ");
    }

    private boolean fichaEsInmovil(Ficha ficha) {
        return ficha.getRango() == 0 || ficha.getRango() == -1;
    }

    private void realizarAtaque(int filaDestino, int columnaDestino) {
        Ficha fichaAtacante = fichaSeleccionada;
        Ficha fichaDefensora = obtenerFicha(filaDestino, columnaDestino);

        if (fichaAtacante == null || fichaDefensora == null) {
            return;
        }

        boolean fichaAtacanteVence = false;
        String mensajeResultado = "";
        ImageIcon iconoGanador = null;
        ImageIcon iconoPerdedor = null;

        if (fichaAtacante.getRango() == fichaDefensora.getRango()) {
            removerFicha(filaSeleccionada, columnaSeleccionada);
            removerFicha(filaDestino, columnaDestino);
            mensajeResultado = "Empate: Ambas fichas se eliminan mutuamente.";
            iconoGanador = fichaAtacante.getIcono();
            iconoPerdedor = fichaDefensora.getIcono();
        } else if (fichaAtacante.getRango() == 1 && fichaDefensora.getRango() == 10) {
            fichaAtacanteVence = true;
            mensajeResultado = "Victoria del que ataca";
            iconoGanador = fichaAtacante.getIcono();
            iconoPerdedor = fichaDefensora.getIcono();
        } else if (fichaDefensora.getRango() == -1) {
            if (fichaAtacante.getRango() == 3) {
                fichaAtacanteVence = true;
                mensajeResultado = "Victoria del que ataca";
                iconoGanador = fichaAtacante.getIcono();
                iconoPerdedor = fichaDefensora.getIcono();
            } else {
                removerFicha(filaSeleccionada, columnaSeleccionada);
                mensajeResultado = "Victoria de la bomba";
                iconoGanador = fichaDefensora.getIcono();
                iconoPerdedor = fichaAtacante.getIcono();
            }
        } else if (fichaAtacante.getRango() > fichaDefensora.getRango() || fichaDefensora.getRango() == 0) {
            fichaAtacanteVence = true;
            mensajeResultado = "Victoria del que ataca";
            iconoGanador = fichaAtacante.getIcono();
            iconoPerdedor = fichaDefensora.getIcono();

            if (fichaDefensora.getRango() == 0) {
                declararVictoriaPorCapturaDeTierra(filaDestino, columnaDestino);
                return;
            }
        } else {
            removerFicha(filaSeleccionada, columnaSeleccionada);
            mensajeResultado = "Victoria del que defiende";
            iconoGanador = fichaDefensora.getIcono();
            iconoPerdedor = fichaAtacante.getIcono();
        }

        if (fichaAtacanteVence) {
            removerFicha(filaDestino, columnaDestino);
            moverFicha(filaDestino, columnaDestino);
        }

        JPanel panelResultado = new JPanel(new GridLayout(2, 2, 10, 10));
        panelResultado.add(new JLabel(iconoGanador));
        panelResultado.add(new JLabel(iconoPerdedor));
        panelResultado.add(new JLabel("Ganador", SwingConstants.CENTER));
        panelResultado.add(new JLabel("Perdedor", SwingConstants.CENTER));

        JOptionPane.showMessageDialog(this, panelResultado, mensajeResultado, JOptionPane.INFORMATION_MESSAGE);

        cambiarTurno();
        restaurarColorOriginal();
    }

    private void removerFicha(int fila, int columna) {
        Ficha ficha = obtenerFicha(fila, columna);
        if (ficha != null) {
            if (ficha.esVillano()) {
                agregarFichaVencida(panelFichasVencidasVillano, ficha);
            } else {
                agregarFichaVencida(panelFichasVencidasHeroe, ficha);
            }
        }

        botones[fila][columna].setIcon(null);
        botones[fila][columna].putClientProperty("ficha", null);
    }

    private void agregarFichaVencida(JPanel panel, Ficha ficha) {
        int fichaVencidaTamano = 40;
        ImageIcon icon = ficha.getIcono();
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(fichaVencidaTamano, fichaVencidaTamano, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);

        JLabel fichaLabel = new JLabel();
        fichaLabel.setIcon(icon);
        panel.add(fichaLabel);
        panel.revalidate();
        panel.repaint();
    }

    private void resaltarMovimientosValidos(int fila, int columna) {
        restaurarColorOriginal();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (esMovimientoValido(i, j)) {
                    botones[i][j].setBackground(Color.YELLOW);
                    botonesResaltados.add(botones[i][j]);
                }
            }
        }
    }

    private void restaurarColorOriginal() {
        for (JButton boton : botonesResaltados) {
            boton.setBackground(null);
        }
        botonesResaltados.clear();
    }

    private boolean fichaPerteneceATurnoActual(Ficha ficha) {
        return (esTurnoHeroe && !ficha.esVillano()) || (!esTurnoHeroe && ficha.esVillano());
    }

    private boolean esMovimientoValido(int filaDestino, int columnaDestino) {
        if (filaDestino < 0 || filaDestino >= 10 || columnaDestino < 0 || columnaDestino >= 10) {
            return false;
        }
        if (obtenerFicha(filaDestino, columnaDestino) != null) {
            return false;
        }
        if (esCasillaProhibida(filaDestino, columnaDestino)) {
            return false;
        }
        int filaDiferencia = Math.abs(filaDestino - filaSeleccionada);
        int columnaDiferencia = Math.abs(columnaDestino - columnaSeleccionada);
        Ficha ficha = obtenerFicha(filaSeleccionada, columnaSeleccionada);
        if (ficha.getRango() == 2) {
            if (filaDiferencia == 0 || columnaDiferencia == 0) {
                return esCaminoLibre(filaDestino, columnaDestino);
            }
        } else {
            return (filaDiferencia == 1 && columnaDiferencia == 0) || (filaDiferencia == 0 && columnaDiferencia == 1);
        }
        return false;
    }

    private boolean esCaminoLibre(int filaDestino, int columnaDestino) {
        int filaInicio = Math.min(filaSeleccionada, filaDestino);
        int filaFin = Math.max(filaSeleccionada, filaDestino);
        int colInicio = Math.min(columnaSeleccionada, columnaDestino);
        int colFin = Math.max(columnaSeleccionada, columnaDestino);

        if (filaSeleccionada == filaDestino) {
            for (int i = colInicio + 1; i < colFin; i++) {
                if (obtenerFicha(filaSeleccionada, i) != null || esCasillaProhibida(filaSeleccionada, i)) {
                    return false;
                }
            }
        } else if (columnaSeleccionada == columnaDestino) {
            for (int i = filaInicio + 1; i < filaFin; i++) {
                if (obtenerFicha(i, columnaSeleccionada) != null || esCasillaProhibida(i, columnaSeleccionada)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean esCasillaProhibida(int fila, int columna) {
        int[][] zonasProhibidas = {
            {4, 2}, {4, 3}, {5, 2}, {5, 3},
            {4, 6}, {4, 7}, {5, 6}, {5, 7}
        };
        for (int[] zona : zonasProhibidas) {
            if (zona[0] == fila && zona[1] == columna) {
                return true;
            }
        }
        return false;
    }

    private void moverFicha(int filaDestino, int columnaDestino) {
        Ficha ficha = obtenerFicha(filaSeleccionada, columnaSeleccionada);

        if (ficha != null) {
            Ficha fichaDestino = obtenerFicha(filaDestino, columnaDestino);
            if (fichaDestino != null && fichaDestino.getRango() == 0) {
                declararVictoriaPorCapturaDeTierra(filaDestino, columnaDestino);
                return;
            }

            botones[filaSeleccionada][columnaSeleccionada].setIcon(null);
            botones[filaSeleccionada][columnaSeleccionada].putClientProperty("ficha", null);

            botones[filaDestino][columnaDestino].setIcon(ficha.getIcono());
            botones[filaDestino][columnaDestino].putClientProperty("ficha", ficha);

            fichaSeleccionada = null;
            filaSeleccionada = -1;
            columnaSeleccionada = -1;
        }
    }

    private void declararVictoriaPorCapturaDeTierra(int filaDestino, int columnaDestino) {
        String perdedor, tipoPerdedor, ganador, tipoGanador;

        if (esTurnoHeroe) {
            ganador = menuInicio.logged;
            tipoGanador = menuInicio.loggedType;
            perdedor = menuInicio.selected;
            tipoPerdedor = menuInicio.selectedType;
        } else {
            ganador = menuInicio.selected;
            tipoGanador = menuInicio.selectedType;
            perdedor = menuInicio.logged;
            tipoPerdedor = menuInicio.loggedType;
        }

        String fecha = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());

        String mensaje = String.format("%s usando %s ha %s la TIERRA! Venciendo a %s usando %s – %s",
                ganador, tipoGanador, esTurnoHeroe ? "SALVADO" : "CAPTURADO",
                perdedor, tipoPerdedor, fecha);

        JOptionPane.showMessageDialog(this, mensaje, "Juego Terminado", JOptionPane.INFORMATION_MESSAGE);

        menuInicio.usuarios.registrarVictoria(tipoGanador);

        menuInicio.logs.add(mensaje);

        menuInicio.usuarios.puntos(ganador);

        MenuPrincipal menuPrincipal = new MenuPrincipal(menuInicio);
        menuPrincipal.setVisible(true);

        for (Frame f : JFrame.getFrames()) {
            if (f.isVisible()) {
                f.dispose();
                break;
            }
        }
    }

    private void cambiarTurno() {
        esTurnoHeroe = !esTurnoHeroe;
        actualizarVisibilidadFichas();
        fichaSeleccionada = null;
        movimientoRealizado = false;

        String mensajeTurno = "Turno de: " + (esTurnoHeroe ? "Heroes (" + menuInicio.logged + ")" : "Villanos (" + menuInicio.selected + ")");
        JOptionPane.showMessageDialog(this, mensajeTurno, "Cambio de Turno", JOptionPane.INFORMATION_MESSAGE);
    }
}
