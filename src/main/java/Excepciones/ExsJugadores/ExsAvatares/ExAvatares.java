package Excepciones.ExsJugadores.ExsAvatares;

import Excepciones.ExsJugadores.ExJugadores;
import Jugadores.Avatares.Avatar;
import Jugadores.Jugador;

public abstract class ExAvatares extends ExJugadores {

    Avatar avatar;
    public ExAvatares(String mensaje, Jugador jugador) {
        super(mensaje, jugador);
        if(jugador != null)
            avatar= jugador.getAvatar();
    }
}
