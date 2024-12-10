package Excepciones.ExsCasillas.ExsPropiedades.ExsSolares;

import Juego.Casillas.Propiedades.Solar;

public final class SinPermisoEdificar extends ExsSolar {
    public SinPermisoEdificar(String mensaje, Solar solar) {
        super(mensaje, solar);
    }

}
