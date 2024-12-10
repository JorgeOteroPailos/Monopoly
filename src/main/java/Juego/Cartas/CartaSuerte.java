package Juego.Cartas;

import Excepciones.AccionIlegal;
import Excepciones.ComandoDesconocido;
import Excepciones.ExsCasillas.ExsPropiedades.ExsSolares.VenderEdificada;
import Excepciones.ExsCasillas.ExsPropiedades.NoComprada;
import Excepciones.ExsCasillas.TipoCasillaInvalido;
import Excepciones.Inexistencias.InexCas;
import Juego.Casillas.Acciones.Parking;
import Juego.Casillas.Propiedades.Transporte;
import Juego.Edificios.*;
import Juego.Juego;
import Jugadores.Jugador;

public final class CartaSuerte extends Carta{
    public CartaSuerte(int indice) {
        super(indice, 1);
    }

    @Override
    public void accion(Juego juego, int turno) throws AccionIlegal, NoComprada, VenderEdificada, TipoCasillaInvalido, InexCas, ComandoDesconocido {
        Jugador jugadorturno=juego.getArrayJugadores().get(turno);
        double valorMedia= Juego.mediaglobal();
        switch(indice) {
            case 0:
                if (jugadorturno.getAvatar().getPosicion() > 15) {
                    juego.pasarSalida(jugadorturno);
                }
                juego.getCasillaConcreta(jugadorturno.getAvatar().getPosicion()).setArrayBoolean(turno, false);
                jugadorturno.getAvatar().setPosicion(15);
                juego.getCasillaConcreta(15).setArrayBoolean(turno, true);
                juego.getCasillaConcreta(15).ejecutar(juego);
                break;
            case 1:
                juego.getCasillaConcreta(jugadorturno.getAvatar().getPosicion()).setArrayBoolean(turno, false);
                jugadorturno.getAvatar().setPosicion(39);
                juego.getCasillaConcreta(39).setArrayBoolean(turno, true);
                juego.getCasillaConcreta(39).ejecutar(juego);
                break;
            case 2:
                jugadorturno.Aumento(valorMedia / 4);
                jugadorturno.setDineroBoteInversiones(jugadorturno.getDineroBoteInversiones()+valorMedia/4);
                break;
            case 3:
                if (jugadorturno.getAvatar().getPosicion() > 29) {
                    juego.pasarSalida(jugadorturno);
                }
                juego.getCasillaConcreta(jugadorturno.getAvatar().getPosicion()).setArrayBoolean(turno, false);
                jugadorturno.getAvatar().setPosicion(29);
                juego.getCasillaConcreta(jugadorturno.getAvatar().getPosicion()).setArrayBoolean(turno, true);
                juego.getCasillaConcreta(jugadorturno.getAvatar().getPosicion()).ejecutar(juego);
                break;
            case 4:
                jugadorturno.setCarcel(3);
                juego.getCasillaConcreta(jugadorturno.getAvatar().getPosicion()).setArrayBoolean(turno, false);
                jugadorturno.getAvatar().setPosicion(10);

                jugadorturno.setVecesCarcel(jugadorturno.getVecesCarcel() + 1);
                juego.getCasillaConcreta(jugadorturno.getAvatar().getPosicion()).setArrayBoolean(turno, true);
                break;
            case 5:
                jugadorturno.Aumento(valorMedia/2);
                jugadorturno.setDineroBoteInversiones(jugadorturno.getDineroBoteInversiones()+valorMedia/2);
                break;
            case 6:
                jugadorturno.Disminucion(valorMedia*3/4);
                ((Parking)juego.getCasillaConcreta(20)).setBote(((Parking) juego.getCasillaConcreta(20)).getBote() + valorMedia*3/4);
                jugadorturno.setDineroImpuestos(jugadorturno.getDineroImpuestos()+valorMedia*3/4);
                break;
            case 7:
                double suma=0;
                for(Edificio edificio:jugadorturno.getArrayEdificios()){
                    if(edificio instanceof Casa) {
                        jugadorturno.Disminucion(valorMedia / 5);
                        suma += valorMedia / 5;
                    }else if(edificio instanceof Hotel) {
                        jugadorturno.Disminucion(valorMedia * 0.575);
                        suma += valorMedia * 0.575;
                    }else if(edificio instanceof Piscina) {
                        jugadorturno.Disminucion(valorMedia / 10);
                        suma += valorMedia / 10;
                    }else if(edificio instanceof PistaDeporte) {
                        jugadorturno.Disminucion(valorMedia * 0.375);
                        suma += valorMedia * 0.375;
                    }
                }
                ((Parking)juego.getCasillaConcreta(20)).setBote(((Parking) juego.getCasillaConcreta(20)).getBote() + suma);
                jugadorturno.setDineroImpuestos(jugadorturno.getDineroImpuestos()+suma);

                Juego.consola.consolaimprimir("Se le ha restado al jugador " + jugadorturno.getNombre() + " " + String.format("%.2f",suma)+"€");///todo cambiar sigo
                break;
            case 8:
                if (jugadorturno.getAvatar().getPosicion() > 13) {
                    juego.pasarSalida(jugadorturno);
                }
                juego.getCasillaConcreta(jugadorturno.getAvatar().getPosicion()).setArrayBoolean(turno, false);
                jugadorturno.getAvatar().setPosicion(13);
                juego.getCasillaConcreta(jugadorturno.getAvatar().getPosicion()).setArrayBoolean(turno, true);
                juego.getCasillaConcreta(jugadorturno.getAvatar().getPosicion()).ejecutar(juego);
                break;
            case 9:
                for (Jugador jugador : juego.getArrayJugadores()) {
                    jugador.Aumento(valorMedia / 8);
                    jugadorturno.Disminucion(valorMedia / 8);
                }
                break;
            case 10:
                juego.getCasillaConcreta(jugadorturno.getAvatar().getPosicion()).setArrayBoolean(turno, false);
                jugadorturno.getAvatar().setPosicion(jugadorturno.getAvatar().getPosicion()-3);
                juego.getCasillaConcreta(jugadorturno.getAvatar().getPosicion()).setArrayBoolean(turno, true);
                juego.getCasillaConcreta(jugadorturno.getAvatar().getPosicion()).ejecutar(juego);
                break;
            case 11:
                jugadorturno.Disminucion(valorMedia*0.075);
                ((Parking)juego.getCasillaConcreta(20)).setBote(((Parking) juego.getCasillaConcreta(20)).getBote() + valorMedia*0.075);
                jugadorturno.setDineroImpuestos(jugadorturno.getDineroImpuestos()+valorMedia*0.075);
                break;
            case 12:
                jugadorturno.Aumento(valorMedia*0.075);
                jugadorturno.setDineroBoteInversiones(jugadorturno.getDineroBoteInversiones()+valorMedia*0.075);
                break;
            case 13:
                juego.getCasillaConcreta(jugadorturno.getAvatar().getPosicion()).setArrayBoolean(turno, false);
                Transporte trans;

                double alquiler;
                switch(jugadorturno.getAvatar().getPosicion()){
                    case 7:
                        jugadorturno.getAvatar().setPosicion(15);

                        break;
                    case 12:
                        jugadorturno.getAvatar().setPosicion(25);

                        break;
                    case 36:
                        jugadorturno.getAvatar().setPosicion(5);
                        juego.pasarSalida(jugadorturno);
                        break;
                }
                trans= (Transporte) juego.getCasillaConcreta(jugadorturno.getAvatar().getPosicion());
                trans.setArrayBoolean(turno, true);
                if(!(trans.getDuenho().getNombre().equals("Banca")) && !(trans.getDuenho().getNombre().equals(jugadorturno.getNombre())) &&!(trans.getHipotecada())){
                    alquiler = trans.alquiler(juego.getSumadados())*2;
                    jugadorturno.Disminucion(alquiler);
                    trans.getDuenho().Aumento(alquiler);
                    trans.getDuenho().setDineroAlquileresRecibido(trans.getDuenho().getDineroAlquileresRecibido() + alquiler);
                    trans.setAlquilerAcumulado(trans.getAlquilerAcumulado() + alquiler);
                    jugadorturno.setDineroAlquileresPagado(jugadorturno.getDineroAlquileresPagado() + alquiler);

                    Juego.consola.consolaimprimir(jugadorturno.getNombre() + " Ha pagado " + String.format("%.2f", alquiler) + "€ de alquiler a " + trans.getDuenho().getNombre() + "\n");

                    if (jugadorturno.getFortuna() <= 0) {
                        juego.menubancarrota(trans.getDuenho(), juego.getBanca());
                        break;
                    }
                }
                break;
        }
    }
}
