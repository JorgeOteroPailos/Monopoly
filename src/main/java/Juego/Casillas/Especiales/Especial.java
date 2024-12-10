package Juego.Casillas.Especiales;

import Juego.Casillas.Casilla;
import Juego.Juego;


public abstract class Especial extends Casilla {
    public Especial(String nombre, int posicion, int numJugadores) {
        super(nombre, posicion, numJugadores);
    }

    //GETTERS

    //SETTERS

    //METODOS
    @Override
    public abstract String toString();

    @Override
    public abstract void ejecutar(Juego partida);

}
