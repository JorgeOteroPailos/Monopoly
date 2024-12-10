package Juego.Casillas.Propiedades;

import Juego.Juego;
import Jugadores.Jugador;

public final class Servicio extends Propiedad{
    private final double factorservicio;
    public Servicio(String nombre, int posicion, int numJugadores, Jugador banca) {
        super(nombre,  posicion, numJugadores, banca);
        this.factorservicio = Juego.mediaglobal()*0.005;
    }

    @Override
    public double valor(){
        return Juego.mediaglobal()*0.75;
    }

    @Override
    public double alquiler(int suma) {
        int mult = 4;
        if (grupo.grupoComprado(this.getDuenho())) {
            mult = 10;
        }
        return mult*suma*factorservicio;
    }

    @Override
    public String toString(){
        return "Tipo: Servicio\nValor: " + String.format("%.2f", valor()) + "\nAlquiler: " + alquiler(1) + "\nPropietario: "+ duenho.getNombre();
    }

    @Override
    public String toStringEnventa() {
        return "Tipo: Servicio\nValor: " + String.format("%.2f", valor());
    }
}
