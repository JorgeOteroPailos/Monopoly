package Excepciones.ExsTratos;

import Juego.Trato;

public class ExTratos extends Exception{
    protected Trato trato;

    public ExTratos(String mensaje, Trato trato){
        super(mensaje);
        this.trato = trato;

    }

    //GETTERS
    public Trato getTrato() {
        return trato;
    }
}
