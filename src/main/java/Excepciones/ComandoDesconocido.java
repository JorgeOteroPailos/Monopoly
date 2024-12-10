package Excepciones;

public final class ComandoDesconocido extends ExJuego{
    String comando;
    public ComandoDesconocido(String mensaje, String comando) {
        super(mensaje);
        this.comando = comando;
    }

    //GETTERS
    public String getComando() {
        return comando;
    }
}
