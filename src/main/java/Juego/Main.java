package Juego;

import Excepciones.AccionIlegal;
import Excepciones.ComandoDesconocido;
import Excepciones.ExsJugadores.ExsAvatares.TipoAvatarInvalido;
import Excepciones.NombreEnUso;

public final class Main {
    public static void main(String[] args) throws NombreEnUso, TipoAvatarInvalido, AccionIlegal, ComandoDesconocido {
        Juego M1 = new Juego();
        System.out.println(M1);
        M1.partida();
    }
}

//    AUTORES
//Pablo Pazos Parada
//Jorge Otero Pailos
//Gabriel Novo Vila
//5100 lineas