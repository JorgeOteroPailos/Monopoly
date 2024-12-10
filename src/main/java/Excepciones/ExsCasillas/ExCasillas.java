package Excepciones.ExsCasillas;

import Juego.Casillas.Casilla;

public abstract class ExCasillas extends Exception{
    protected Casilla casilla;
    public ExCasillas(String mensaje, Casilla casilla){
        super(mensaje);
        this.casilla = casilla;
    }

    //GETTERS
    public Casilla getCasilla() {
        return casilla;
    }

    //SETTERS
    public void setCasilla(Casilla casilla) {
        this.casilla = casilla;
    }
}
