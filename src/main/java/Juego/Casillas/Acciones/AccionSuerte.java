package Juego.Casillas.Acciones;

import Excepciones.AccionIlegal;
import Excepciones.ComandoDesconocido;
import Excepciones.ExsCasillas.ExsPropiedades.ExsSolares.VenderEdificada;
import Excepciones.ExsCasillas.ExsPropiedades.NoComprada;
import Excepciones.ExsCasillas.TipoCasillaInvalido;
import Excepciones.Inexistencias.InexCas;
import Juego.Juego;

public final class AccionSuerte extends Accion {
    public AccionSuerte(String nombre, int posicion, int numJugadores) {
        super(nombre, posicion, numJugadores);
    }

    @Override
    public String toString() {
        return super.toString()+ " (Suerte)";
    }

    @Override
    public void ejecutar(Juego partida) throws AccionIlegal, NoComprada, VenderEdificada, TipoCasillaInvalido, InexCas, ComandoDesconocido {
        partida.suerte_comunidad(1);
    }
}
