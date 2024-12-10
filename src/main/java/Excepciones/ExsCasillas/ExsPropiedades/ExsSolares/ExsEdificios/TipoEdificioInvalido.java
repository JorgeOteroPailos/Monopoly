package Excepciones.ExsCasillas.ExsPropiedades.ExsSolares.ExsEdificios;

import Juego.Casillas.Propiedades.Solar;

public final class TipoEdificioInvalido extends ExEdificios{
    private final int tipo;

    public TipoEdificioInvalido(String mensaje, Solar solar, int tipo) {
        super(mensaje, solar);
        this.tipo = tipo;
    }
    public int getTipo() {
        return tipo;
    }
}
