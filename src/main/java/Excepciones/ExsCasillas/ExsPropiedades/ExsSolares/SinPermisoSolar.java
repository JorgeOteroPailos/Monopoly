package Excepciones.ExsCasillas.ExsPropiedades.ExsSolares;

import Juego.Casillas.Propiedades.Solar;

public final class SinPermisoSolar extends ExsSolar{
    public SinPermisoSolar(String mensaje, Solar solar) {
        super(mensaje, solar);
    }
}
