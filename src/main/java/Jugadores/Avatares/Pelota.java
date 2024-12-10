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

public final class Pelota extends Avatar{

    private boolean heLlegado;


    public boolean getHeLlegado(){return heLlegado;}
    public void setHeLlegado(boolean n){heLlegado=n;}

    public Pelota(char letra) {
        super(letra);
    }


    public void moverEnAvanzado(Juego M, String[] splitcom) throws AccionIlegal, NoComprada, VenderEdificada, TipoCasillaInvalido, InexCas, ComandoDesconocido {
        Jugador jugadorturno=M.getArrayJugadores().get(M.getTurno());
        Avatar avatarturno= jugadorturno.getAvatar();
        this.setHeLlegado(false);
        int tirada1,tirada2,suma=0,ultposicion=avatarturno.getPosicion();
        boolean doblesflag = false;

        if (M.getDadosporlanzar() >= 0) {

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
            M.setDadosporlanzar(-1);
            M.setMovimientosRealizados(M.getMovimientosRealizados()+1);

            if (tirada1 == tirada2) {
                Juego.consola.consolaimprimir("Han salido dobles!");
                M.setDadosporlanzar(1);
                M.setContadordobles(M.getContadordobles()+1);
                M.setCambioModo(1);
                doblesflag=true;
                if (jugadorturno.getCarcel() > 0) {
                    jugadorturno.setCarcel(0);
                    M.setContadordobles(0);

                    Juego.consola.consolaimprimir("Cuidado, cerrad vuestras casas con llave, " + jugadorturno.getNombre() + " salió de la carcel!!!");
                    return;

                } else if (M.getContadordobles() >= 3) {
                    M.entrarCarcel();
                }
            }
            if ((jugadorturno.getCarcel()) == 0) {
                suma = tirada1 + tirada2;
            } else {
                jugadorturno.setCarcel(jugadorturno.getCarcel() - 1);
            }

            if (suma > 4) {
                //easter egg chorra
                if (jugadorturno.getEe() == 'f') {
                    Juego.consola.consolaimprimir("LA PELOTA MALOTA " + jugadorturno.getNombre() + " TIRA LOS DADOS MUY FUERTE CONTRA EL TABLERO Y CASI LE DA A OTRO JUGADOR!!!");
                    jugadorturno.setEe('n');
                    Juego.consola.consolaimprimir("El pelotón matón " + avatarturno.getLetra() + "se da un rebotón de " + suma +
                            " posiciones ( " + tirada1 + " " + tirada2 + " ) por el barrio desde " + M.getCasillaConcreta(ultposicion).getNombre() +
                            " hasta " + M.getCasillaConcreta(((avatarturno.getPosicion()) + suma) % 40).getNombre());
                } else {
                    Juego.consola.consolaimprimir("El avatar " + avatarturno.getLetra() + " avanzará " + suma +
                            " posiciones ( " + tirada1 + " " + tirada2 + " ), desde " + M.getCasillaConcreta(ultposicion).getNombre() +
                            " hasta " + M.getCasillaConcreta((((avatarturno.getPosicion())) + suma) % 40).getNombre());
                }
                M.getCasillaConcreta(avatarturno.getPosicion()).setArrayBoolean(M.getTurno(), false);
                if ((avatarturno.setSumPosicion(4)) == 1) {
                    M.pasarSalida(jugadorturno);
                }
                while (!heLlegado) {
                    M.getCasillaConcreta(avatarturno.getPosicion()).setArrayBoolean(M.getTurno(), false);
                    if (((avatarturno.setSumPosicion(1)) == 1) && (jugadorturno.getCarcel() == 0)) {
                        M.pasarSalida(jugadorturno);
                    }
                    if(avatarturno.getPosicion() == (ultposicion + suma)%40){
                        Juego.consola.consolaimprimir("Has llegado a tu destino");
                        this.setHeLlegado(true);
                    }
                    if (((jugadorturno.getAvatar().getPosicion() - ultposicion) % 2 == 1) || (avatarturno.getPosicion() == ultposicion + suma)) {
                        Juego.consola.consolaimprimir("Ahora la pelota está en la casilla " + jugadorturno.getAvatar().getPosicion() + ": " + M.getCasillaConcreta(avatarturno.getPosicion()).getNombre());

                        M.getCasillaConcreta(avatarturno.getPosicion()).setArrayBoolean(M.getTurno(), true);
                        M.getCasillaConcreta(avatarturno.getPosicion()).ejecutar(M);
                        if(!heLlegado){M.partida();}

                        M.getCasillaConcreta(avatarturno.getPosicion()).setFrecuencia(M.getCasillaConcreta(avatarturno.getPosicion()).getFrecuencia() + 1);

                    }

                    //M.consola.consolaimprimir("Has llegado a tu destino");
                }
            } else {
                //easter egg chorra

                if (jugadorturno.getEe() == 'f') {
                    Juego.consola.consolaimprimir("LA PELOTA MALOTA " + jugadorturno.getNombre() + " TIRA LOS DADOS MUY FUERTE CONTRA EL TABLERO Y CASI LE DA A OTRO JUGADOR!!!");
                    jugadorturno.setEe('n');
                    Juego.consola.consolaimprimir("El pelotón matón " + avatarturno.getLetra() + "se da un rebotón de " + suma +
                            " posiciones ( " + tirada1 + " " + tirada2 + " ) por el barrio desde " + M.getCasillaConcreta(ultposicion).getNombre() +
                            " hasta " + M.getCasillaConcreta((avatarturno.getPosicion() < 0) ? avatarturno.getPosicion() + 40 : avatarturno.getPosicion()).getNombre());
                } else {
                    Juego.consola.consolaimprimir("El avatar " + avatarturno.getLetra() + " retrocederá " + suma +
                            " posiciones ( " + tirada1 + " " + tirada2 + " ), desde " + M.getCasillaConcreta(ultposicion).getNombre() +
                            " hasta " + M.getCasillaConcreta((avatarturno.getPosicion() - suma < 0) ? avatarturno.getPosicion() - suma + 40 : avatarturno.getPosicion() - suma).getNombre());
                }
                M.getCasillaConcreta(avatarturno.getPosicion()).setArrayBoolean(M.getTurno(), false);
                while (!heLlegado) {
                    M.getCasillaConcreta(avatarturno.getPosicion()).setArrayBoolean(M.getTurno(), false);
                    if ((avatarturno.setSumPosicion(-1)) == 1) {
                        M.pasarSalidaHaciaAtras(jugadorturno);
                    }
                    if (avatarturno.getPosicion() == (((ultposicion - suma) < 0) ? (ultposicion - suma + 40) : (ultposicion - suma))){
                        Juego.consola.consolaimprimir("Has llegado a tu destino");
                        this.setHeLlegado(true);
                    }
                    if (((avatarturno.getPosicion() - ultposicion) % 2 != 0) || (avatarturno.getPosicion() == (((ultposicion - suma) < 0) ? (ultposicion - suma + 40) : (ultposicion - suma)))) {

                        Juego.consola.consolaimprimir("Ahora la pelota está en la casilla " + avatarturno.getPosicion() + ": " + M.getCasillaConcreta(avatarturno.getPosicion()).getNombre());
                        M.getCasillaConcreta(avatarturno.getPosicion()).setArrayBoolean(M.getTurno(), true);
                        M.getCasillaConcreta(avatarturno.getPosicion()).ejecutar(M);
                        if(!heLlegado){M.partida();}
                        M.getCasillaConcreta(avatarturno.getPosicion()).setFrecuencia(M.getCasillaConcreta(avatarturno.getPosicion()).getFrecuencia() + 1);

                    }


                }
                //Juego.consola.consolaimprimir("Has llegado a la última casilla.");
            }
            //M.setDadosporlanzar(M.getDadosporlanzar()-1);
        }
        if(doblesflag)
            avatarturno.setTipomov(false);
    }
}
