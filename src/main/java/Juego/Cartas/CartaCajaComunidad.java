package Juego.Cartas;

import Excepciones.AccionIlegal;
import Excepciones.ComandoDesconocido;
import Excepciones.ExsCasillas.ExsPropiedades.ExsSolares.VenderEdificada;
import Excepciones.ExsCasillas.ExsPropiedades.NoComprada;
import Excepciones.ExsCasillas.TipoCasillaInvalido;
import Excepciones.Inexistencias.InexCas;
import Juego.Casillas.Acciones.Parking;
import Juego.Juego;
import Jugadores.Jugador;

public final class CartaCajaComunidad extends Carta {
    public CartaCajaComunidad(int indice) {
        super(indice,0);
    }

    @Override
    public void accion(Juego juego, int turno) throws AccionIlegal, NoComprada, VenderEdificada, TipoCasillaInvalido, InexCas, ComandoDesconocido {
        Jugador jugadorturno=juego.getArrayJugadores().get(turno);
        double valorMedia=Juego.mediaglobal();
        switch(indice) {
            case 0:
                jugadorturno.Disminucion(valorMedia / 4);
                ((Parking)juego.getCasillaConcreta(20)).setBote(((Parking) juego.getCasillaConcreta(20)).getBote() + valorMedia / 4);
                jugadorturno.setDineroImpuestos(jugadorturno.getDineroImpuestos()+valorMedia/4);
                break;
            case 1:
                juego.entrarCarcel();
                break;
            case 2:
                juego.getCasillaConcreta(jugadorturno.getAvatar().getPosicion()).setArrayBoolean(turno, false);
                jugadorturno.getAvatar().setPosicion(0);
                juego.getCasillaConcreta(jugadorturno.getAvatar().getPosicion()).setArrayBoolean(turno, true);
                juego.pasarSalida(jugadorturno);
                break;
            case 3:
                jugadorturno.Aumento(valorMedia);
                jugadorturno.setDineroBoteInversiones(jugadorturno.getDineroBoteInversiones()+valorMedia);
                break;
            case 4:
                jugadorturno.Disminucion(valorMedia / 2);
                ((Parking)juego.getCasillaConcreta(20)).setBote(((Parking) juego.getCasillaConcreta(20)).getBote() + valorMedia / 2);
                jugadorturno.setDineroImpuestos(jugadorturno.getDineroImpuestos()+valorMedia/2);
                break;
            case 5:
                jugadorturno.Aumento(valorMedia / 4);
                jugadorturno.setDineroBoteInversiones(jugadorturno.getDineroBoteInversiones()+valorMedia/4);
                break;
            case 6:
                juego.getCasillaConcreta(jugadorturno.getAvatar().getPosicion()).setArrayBoolean(turno, false);
                jugadorturno.getAvatar().setPosicion(1);
                juego.getCasillaConcreta(1).setArrayBoolean(turno, true);
                juego.getCasillaConcreta(1).ejecutar(juego);
                break;
            case 7:
                for (Jugador jugador : juego.getArrayJugadores()) {
                    if (jugador.getAvatar() != jugadorturno.getAvatar()) {
                        jugador.Aumento(valorMedia / 10);
                        jugadorturno.Disminucion(valorMedia / 10);
                    }
                }
                break;
            case 8:
                jugadorturno.Aumento(valorMedia / 2);
                jugadorturno.setDineroBoteInversiones(jugadorturno.getDineroBoteInversiones()+valorMedia/2);
                break;
            case 9:
                if (jugadorturno.getAvatar().getPosicion() > 23) {
                    juego.pasarSalida(jugadorturno);
                }
                juego.getCasillaConcreta(jugadorturno.getAvatar().getPosicion()).setArrayBoolean(turno, false);
                jugadorturno.getAvatar().setPosicion(23);
                juego.getCasillaConcreta(23).setArrayBoolean(turno, true);
                juego.getCasillaConcreta(23).ejecutar(juego);
                break;
        }
    }
}
