package Juego.Casillas.Acciones;

import Excepciones.AccionIlegal;
import Excepciones.ComandoDesconocido;
import Excepciones.ExsCasillas.ExsPropiedades.ExsSolares.VenderEdificada;
import Excepciones.ExsCasillas.ExsPropiedades.NoComprada;
import Excepciones.ExsCasillas.TipoCasillaInvalido;
import Excepciones.Inexistencias.InexCas;
import Juego.Casillas.Casilla;
import Juego.Juego;

public abstract class Accion extends Casilla {

    //CONSTRUCTORES
    public Accion(String nombre, int posicion, int numJugadores) {
        super(nombre, posicion, numJugadores);
    }

    //GETTERS

    //SETTERS

    @Override
    public String toString(){
        return "Tipo: Accion";
    }

    @Override
    public abstract void ejecutar(Juego partida) throws AccionIlegal, NoComprada, VenderEdificada, TipoCasillaInvalido, InexCas, ComandoDesconocido;

}
