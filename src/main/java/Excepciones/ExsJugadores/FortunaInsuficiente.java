package Excepciones.ExsJugadores;

import Jugadores.Jugador;

public final class FortunaInsuficiente extends ExJugadores {
    private final double pago;
    public FortunaInsuficiente(String mensaje, Jugador jugador, double pago) {
        super(mensaje, jugador);
        this.pago = pago;
    }

    //GETTERS
    public double getPago() {
        return pago;
    }
}
