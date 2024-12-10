package Juego.Casillas.Especiales;

import Juego.Juego;
import Jugadores.Jugador;

import java.util.ArrayList;

public final class Carcel extends  Especial{
    public Carcel(String nombre, int posicion, int numJugadores) {
        super(nombre, posicion, numJugadores);
    }

    //METODOS
    @Override
    public String toString() {
        return "Tipo: Especial (Carcel)\nCoste salida: " + String.format("%.2f", Juego.mediaglobal() * 0.25);
    }

    public String Encarcelados(ArrayList<Jugador> arrayJugadores){
        StringBuilder res = new StringBuilder();
        for(Jugador jug : arrayJugadores){
            if(jug.getCarcel() != 0){
                res.append(jug.getNombre());
                res.append(" ");
            }
        }
        return "Encarcelados: [ "+ res +"]";
    }

    @Override
    public void ejecutar(Juego partida){
        Juego.consola.consolaimprimir("Has caido en la carcel, riete un poco de los que estan en ella");
    }
}
