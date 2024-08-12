package proyecto_i_prograii;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import javax.swing.JOptionPane;

public class Gestion {

    public Usuario[] registro;
    public String[] userArray;
    public int eliminado;
    public int partidasJugadas = 0;
    public int victoriasHeroes = 0;
    public int victoriasVillanos = 0;

    public Gestion() {
        registro = new Usuario[10];
        userArray = new String[10];
        eliminado = 0;
    }

    public boolean checkName(String nombre) {
        for (Usuario user : registro) {
            if (user != null)
                if (nombre.equals(user.getNombre()))
                    return true;
        }
        return false;
    }

    public boolean checkPass(String nombre, String contra) {
        for (int i = 0; i < registro.length; i++) {
            if (registro[i] != null)
                if (nombre.equals(registro[i].nombre) 
                        && contra.equals(registro[i].contra))
                    return true;
        }
        return false;
    }

    public boolean addUser(String nombre, String contra) {
        if (!checkName(nombre))
            for (int i = 0; i < registro.length; i++) {
                if (registro[i] == null) {
                    registro[i] = new Usuario(nombre, contra);
                    return true;
                }
            }
        return false;
    }

    public long contarUser() {
        return Arrays.stream(registro).filter(Objects::nonNull).count();
    }

    public String[] userList(String nombre) {
        int c = 0;
        for (int i = 0; i < registro.length; i++) {
            if (registro[i] != null && !nombre.equals(registro[i].nombre)) {
                userArray[c] = registro[i].nombre;
                c++;
            }
        }
        return userArray;
    }

    public void ranking() {
        Usuario[] usuariosOrdenados = Arrays.stream(registro)
            .filter(Objects::nonNull)
            .sorted(Comparator.comparingInt(Usuario::getPuntos).reversed())
            .toArray(Usuario[]::new);

        // Construir el texto del ranking
        StringBuilder rankingTexto = new StringBuilder("Posicion - Usuario - Puntos\n");
        for (int i = 0; i < usuariosOrdenados.length; i++) {
            Usuario user = usuariosOrdenados[i];
            rankingTexto.append(String.format("%d - %s - %d\n", i + 1, user.getNombre(), user.getPuntos()));
        }

        JOptionPane.showMessageDialog(null, rankingTexto.toString(), "Ranking de Jugadores", JOptionPane.INFORMATION_MESSAGE);
    }

    public void batallas() {
        int u = 0;
        for (int i = 0; i < registro.length; i++) {
            if (registro[i] != null)
                u++;
        }

        JOptionPane.showMessageDialog(null, 
                "Usuarios activos: " + (u - eliminado) + 
                "\nUsuarios historicos: " + u +
                "\nPartidas jugadas: " + partidasJugadas +
                "\nVictorias de los Heroes: " + victoriasHeroes +
                "\nVictorias de los Villanos: " + victoriasVillanos,
                "Datos Estadisticos", JOptionPane.INFORMATION_MESSAGE);
    }

    public void puntos(String nombre) {
        for (Usuario user : registro) {
            if (user != null) {
                if (nombre.equals(user.getNombre())) {
                    user.setPuntos(user.getPuntos() + 3);
                }
            }
        }
    }

    public void registrarVictoria(String tipoGanador) {
        partidasJugadas++;
        if ("Heroe".equalsIgnoreCase(tipoGanador)) {
            victoriasHeroes++;
        } else if ("Villano".equalsIgnoreCase(tipoGanador)) {
            victoriasVillanos++;
        }
    }
}
