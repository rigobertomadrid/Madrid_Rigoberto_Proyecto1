package proyecto_i_prograii;

import javax.swing.JButton;

public interface FichaInterface {
    String getNombre();
    int getRango();
    void setVisibleIcono(JButton boton, boolean visible);
    boolean esOculto();
    boolean esVillano();
    Personaje getPersonaje();
}
