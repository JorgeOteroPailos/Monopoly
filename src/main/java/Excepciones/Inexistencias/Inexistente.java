package Excepciones.Inexistencias;

public abstract class Inexistente extends Exception{
    protected String noencontrado;

    public Inexistente(String mensaje, String noencontrado){
        super(mensaje);
        this.noencontrado = noencontrado;
    }

}
