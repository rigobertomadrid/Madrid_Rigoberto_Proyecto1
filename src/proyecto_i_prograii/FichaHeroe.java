package proyecto_i_prograii;

import javax.swing.ImageIcon;

public class FichaHeroe extends Ficha {
    public FichaHeroe(Personaje personaje, ImageIcon icono, ImageIcon ocultoIcono) {
        super(personaje, icono, ocultoIcono);
    }

    @Override
    public boolean esVillano() {
        return false;
    }
}
