package Excepciones.ExsJugadores.ExsAvatares;

import Jugadores.Jugador;

public final class PosicionIncorrecta extends ExAvatares{
    private final int posicioncorrecta;
    public PosicionIncorrecta(String mensaje, Jugador jugador, int posicioncorrecta) {
        super(mensaje, jugador);
        this.posicioncorrecta = posicioncorrecta;
    }

    //GETTER
    public int getPosicioncorrecta() {
        return posicioncorrecta;
    }

}
