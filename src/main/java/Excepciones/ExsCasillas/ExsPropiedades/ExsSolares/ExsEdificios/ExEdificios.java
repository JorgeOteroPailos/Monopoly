package Excepciones.ExsCasillas.ExsPropiedades.ExsSolares.ExsEdificios;

import Excepciones.ExsCasillas.ExsPropiedades.ExsSolares.ExsSolar;
import Juego.Casillas.Propiedades.Solar;

public abstract class ExEdificios extends ExsSolar {

    public ExEdificios(String mensaje, Solar solar) {
        super(mensaje, solar);
    }
}
