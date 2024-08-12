package proyecto_i_prograii;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public abstract class Ficha implements FichaInterface {
    protected Personaje personaje;
    protected ImageIcon icono;
    protected ImageIcon ocultoIcono;
    protected boolean oculto;

    public Ficha(Personaje personaje, ImageIcon icono, ImageIcon ocultoIcono) {
        this.personaje = personaje;
        this.icono = icono;
        this.ocultoIcono = ocultoIcono;
        this.oculto = true;
    }

    @Override
    public String getNombre() {
        return personaje.name();
    }

    @Override
    public int getRango() {
        return personaje.getRango();
    }

    @Override
    public void setVisibleIcono(JButton boton, boolean visible) {
        this.oculto = !visible;
        if (visible) {
            boton.setIcon(icono);
        } else {
            boton.setIcon(ocultoIcono);
        }
        boton.revalidate();
        boton.repaint();
    }

    @Override
    public boolean esOculto() {
        return oculto;
    }

    @Override
    public Personaje getPersonaje() {
        return personaje;
    }

    @Override
    public abstract boolean esVillano();

    public ImageIcon getIcono() {
        return icono;
    }
}
