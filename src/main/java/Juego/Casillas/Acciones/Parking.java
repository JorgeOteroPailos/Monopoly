package Juego.Casillas.Acciones;

import Juego.Juego;
import Jugadores.Jugador;

public final class Parking extends Accion{
    private double bote;
    public Parking(String nombre, int posicion, int numJugadores) {
        super(nombre, posicion, numJugadores);
        this.bote = 0;
    }

    //GETTERS
    public double getBote() {
        return bote;
    }

    //SETTERS
    public void setBote(double bote) {
        this.bote = bote;
    }


    //METODOS
    @Override
    public String toString() {
        return super.toString() + " (Parking)\nBote: "+ String.format("%.2f", Juego.mediaglobal() ) ;
    }

    @Override
    public void ejecutar(Juego partida){
        Jugador jugador = partida.getArrayJugadores().get(partida.getTurno());

        jugador.Aumento(this.getBote());//el bote del parking
        jugador.setDineroBoteInversiones(jugador.getDineroBoteInversiones() + this.getBote());
        Juego.consola.consolaimprimir(jugador.getNombre() + "¡Enhorabuena! Ha cobrado " + String.format("%.2f", bote) + "$ del bote del parking\n");
        bote = 0;
    }

}
