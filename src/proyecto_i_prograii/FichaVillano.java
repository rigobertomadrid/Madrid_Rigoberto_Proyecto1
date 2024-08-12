package proyecto_i_prograii;

import javax.swing.ImageIcon;

public class FichaVillano extends Ficha {
    public FichaVillano(Personaje personaje, ImageIcon icono, ImageIcon ocultoIcono) {
        super(personaje, icono, ocultoIcono);
    }

    @Override
    public boolean esVillano() {
        return true;
    }
}
