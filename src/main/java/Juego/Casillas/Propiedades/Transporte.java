package Juego.Casillas.Propiedades;

import Juego.Juego;
import Jugadores.Jugador;

public final class Transporte extends Propiedad{
    private final double operaciontransporte;
    public Transporte(String nombre, int posicion, int numJugadores, Jugador banca) {
        super(nombre, posicion, numJugadores, banca);
        this.operaciontransporte = Juego.mediaglobal();
    }

    @Override
    public double valor(){
        return Juego.mediaglobal();
    }

    @Override
    public double alquiler(int suma) {
         int transcomprados = grupo.cuantosComprados(this.duenho);
         return operaciontransporte*0.25*transcomprados;
    }

    @Override
    public String toString(){
        return "Tipo: Transporte\nValor: " + String.format("%.2f", valor()) + "\nPropietario: "+ duenho.getNombre();
    }

    @Override
    public String toStringEnventa() {
        return "Tipo: Transporte\nValor: " + String.format("%.2f", valor());
    }
}
