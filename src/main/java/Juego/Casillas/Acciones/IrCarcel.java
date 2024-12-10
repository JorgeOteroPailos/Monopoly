package Juego.Casillas.Acciones;

import Juego.Juego;

public final class IrCarcel extends Accion{
    public IrCarcel(String nombre, int posicion, int numJugadores) {
        super(nombre, posicion, numJugadores);
    }

    @Override
    public String toString() {
        return super.toString() + " (IrCarcel)\n(Te manda a la carcel)";
    }

    @Override
    public void ejecutar(Juego partida) {
        partida.entrarCarcel();
    }
}
