package Juego.Casillas.Especiales;

import Juego.Juego;

public final class Salida extends Especial{
    public Salida(String nombre, int posicion, int numJugadores) {
        super(nombre, posicion, numJugadores);
    }

    //METODOS
    @Override
    public String toString(){
        return "Tipo: Especial (Salida)\nBonus por paso: " + String.format("%.2f", Juego.mediaglobal());
    }

    @Override
    public void ejecutar(Juego partida){
        Juego.consola.consolaimprimir("Has dado una vuelta!");
    }

}
