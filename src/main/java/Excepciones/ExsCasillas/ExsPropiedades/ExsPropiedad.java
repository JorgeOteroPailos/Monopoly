package Excepciones.ExsCasillas.ExsPropiedades;

import Excepciones.ExsCasillas.ExCasillas;
import Juego.Casillas.Propiedades.Propiedad;

public abstract class ExsPropiedad extends ExCasillas {
    public ExsPropiedad(String mensaje, Propiedad propiedad) {
        super(mensaje, propiedad);
        this.casilla = propiedad;
    }

    //GETTERS

    //SETTERS
}
