package Jugadores.Avatares;

import Excepciones.AccionIlegal;
import Excepciones.ComandoDesconocido;
import Excepciones.ExsCasillas.ExsPropiedades.ExsSolares.VenderEdificada;
import Excepciones.ExsCasillas.ExsPropiedades.NoComprada;
import Excepciones.ExsCasillas.TipoCasillaInvalido;
import Excepciones.Inexistencias.InexCas;
import Juego.Juego;
import Jugadores.Jugador;
import Juego.ANSICodes;

import java.util.InputMismatchException;
import java.util.Random;

public final class Coche extends Avatar{

    private int turnosPorEsperar;

    public int getTurnosPorEsperar() {return turnosPorEsperar;}
    public void setTurnosPorEsperar(int turnos) {this.turnosPorEsperar = turnos;}
    public void setSumTurnosPorEsperar(int turnos) {
        if((this.turnosPorEsperar = turnosPorEsperar+turnos)<0){
            turnosPorEsperar=0;
        }
    }

    public Coche(char letra) {
        super( letra);
    }

    @Override
    public void moverEnAvanzado(Juego M, String[] splitcom) throws AccionIlegal, NoComprada, VenderEdificada, TipoCasillaInvalido, InexCas, ComandoDesconocido {
        Jugador jugadorturno=M.getArrayJugadores().get(M.getTurno());
        Coche avatarturno= (Coche)jugadorturno.getAvatar();

        int tirada1, tirada2, suma;

        if (avatarturno.getTurnosPorEsperar() <= 0) {
            if (M.getDadosporlanzar()>= 0) {
                if(M.getMovimientosRealizados()>4){Juego.consola.consolaimprimir("Ya te has movido mucho este turno, cálmate un poco");return;}
                if (splitcom.length == 4) {
                    try {
                        tirada1 = Integer.parseInt(splitcom[2]);
                        tirada2 = Integer.parseInt(splitcom[3]);
                    }catch (InputMismatchException error){
                        Juego.consola.consolaimprimir(ANSICodes.ANSI_RED + "ERROR -> Como no has introducido un numero se ha seleccionado un numero aleatorio" + ANSICodes.ANSI_RESET);
                        Random rnd = new Random();
                        tirada1 = rnd.nextInt(5)+1;
                        tirada2 = rnd.nextInt(5)+1;
                    }
                } else {
                    Random rnd = new Random();
                    tirada1 = rnd.nextInt(5) + 1;
                    tirada2 = rnd.nextInt(5) + 1;
                    jugadorturno.setDadosLanzados(jugadorturno.getDadosLanzados()+1);
                }
                if(M.getMovimientosRealizados()==4) {
                    M.setDadosporlanzar(M.getDadosporlanzar()-1);
                    if (tirada1 == tirada2) {
                        Juego.consola.consolaimprimir("Has sacado dobles");
                        M.setDadosporlanzar(M.getDadosporlanzar()+1);
                        M.setContadordobles(M.getContadordobles()+1);
                        M.setCambioModo(1);
                        avatarturno.setTipomov(false);
                    }
                    if (M.getContadordobles() >= 3) {
                        M.entrarCarcel();
                    }
                    this.moverEnBasico(M,splitcom);

                }


                M.setMovimientosRealizados(M.getMovimientosRealizados()+1);
                suma = tirada1 + tirada2;

                if (suma <= 4) {

                    Juego.consola.consolaimprimir("Has sacado un " + tirada1 + " y un " + tirada2 + ".Lo siento, el coche debe sacar más de 4 para avanzar.");
                    Juego.consola.consolaimprimir("El avatar " + avatarturno.getLetra() + " retrocederá " + suma +
                            " posiciones ( " + tirada1 + " " + tirada2 + " ), desde " + M.getCasillaConcreta(avatarturno.getPosicion()).getNombre() +
                            " hasta " + M.getCasillaConcreta((avatarturno.getPosicion() - suma < 0) ? avatarturno.getPosicion() - suma + 40 : avatarturno.getPosicion() - suma).getNombre());
                    M.setDadosporlanzar(-1);
                    M.getCasillaConcreta(avatarturno.getPosicion()).setArrayBoolean(M.getTurno(), false);
                    if ((avatarturno.setSumPosicion(-suma)) == 1) {
                        M.pasarSalidaHaciaAtras(jugadorturno);
                    }
                    M.getCasillaConcreta(avatarturno.getPosicion()).setArrayBoolean(M.getTurno(), true);
                    M.getCasillaConcreta(avatarturno.getPosicion()).ejecutar(M);
                    M.getCasillaConcreta(avatarturno.getPosicion()).setFrecuencia(M.getCasillaConcreta(avatarturno.getPosicion()).getFrecuencia() + 1);
                    avatarturno.setTurnosPorEsperar(2);
                } else if (M.getMovimientosRealizados() <= 3) {
                    M.setDadosporlanzar(M.getDadosporlanzar()+1);
                    M.setMovimientosRealizados(M.getMovimientosRealizados()+1);


                    if (jugadorturno.getEe() == 'f') {
                        Juego.consola.consolaimprimir("EL CARRO DE " + jugadorturno.getNombre() + " TIRA LOS DADOS MUY FUERTE CONTRA EL TABLERO Y CASI LE DA A OTRO JUGADOR!!!");
                        jugadorturno.setEe('n');
                        Juego.consola.consolaimprimir("El buga de " + avatarturno.getLetra() + "se echa una carrera de " + suma +
                                " posiciones ( " + tirada1 + " " + tirada2 + " ) por el barrio desde " + M.getCasillaConcreta(avatarturno.getPosicion()).getNombre() +
                                " hasta " + M.getCasillaConcreta(((avatarturno.getPosicion()) + suma) % 40).getNombre());
                    } else {
                        Juego.consola.consolaimprimir("El coche " + avatarturno.getLetra() + " avanza " + suma +
                                " posiciones ( " + tirada1 + " " + tirada2 + " ), desde " + M.getCasillaConcreta(avatarturno.getPosicion()).getNombre() +
                                " hasta " + M.getCasillaConcreta((((avatarturno.getPosicion())) + suma) % 40).getNombre());
                    }
                    M.getCasillaConcreta(avatarturno.getPosicion()).setArrayBoolean(M.getTurno(), false);
                    if ((avatarturno.setSumPosicion(suma)) == 1) {
                        M.pasarSalida(jugadorturno);
                    }
                    M.getCasillaConcreta(avatarturno.getPosicion()).setArrayBoolean(M.getTurno(), true);
                    M.getCasillaConcreta(avatarturno.getPosicion()).ejecutar(M);
                    M.getCasillaConcreta(avatarturno.getPosicion()).setFrecuencia(M.getCasillaConcreta(avatarturno.getPosicion()).getFrecuencia() + 1);

                }

            } else {
                throw new AccionIlegal("Ya has tirado los dados");
            }
        } else {
            throw new AccionIlegal("Lo siento, debes esperar " + avatarturno.getTurnosPorEsperar() + " turnos para poder moverte de nuevo");

        }
    }
}
