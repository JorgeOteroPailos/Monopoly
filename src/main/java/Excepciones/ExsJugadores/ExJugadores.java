package Excepciones.ExsJugadores;

import Jugadores.Jugador;

public abstract class ExJugadores extends Exception{
    protected Jugador jugador;

    public ExJugadores(String mensaje, Jugador jugador){
        super(mensaje);
        this.jugador = jugador;
    }

    //GETTERS
    public Jugador getJugador() {
        return jugador;
    }

    //SETTERS
    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }
}
