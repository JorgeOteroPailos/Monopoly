package Excepciones.ExsGrupos;

import Juego.Grupo;

public abstract class ExGrupos extends Exception{
    protected Grupo grupo;
    public ExGrupos(String mensaje, Grupo grupo){
        super(mensaje);
        this.grupo = grupo;
    }

    //GETTERS
    public Grupo getGrupo() {
        return grupo;
    }

    //SETTERS
}
