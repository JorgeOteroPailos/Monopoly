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

public abstract class Avatar {
    //ATRIBUTOS
    private final char letra;
    private Boolean tipomov;
    private int posicion;
    //private int turnosPorEsperar;

    //CONSTRUCTORES
    public Avatar(char letra) {
        this.letra = letra;
        tipomov = false;
        posicion = 0;
    }

    //GETTERS

    public char getLetra() {
        return letra;
    }
    public Boolean getTipomov() {
        return tipomov;
    }
    public int getPosicion() {
        return posicion;
    }

    //SETTERS
    public void setTipomov(Boolean valor) {

        tipomov = valor;

    }

    /**
     * @param movimiento Número de casillas que se mueve el jugador
     * @return Devuelve 1 si la posición se pasa de 40 y 0 si no se pasa
     */


    public int setSumPosicion(int movimiento) {

        if(posicion+movimiento<0){
            posicion = 40 + posicion + movimiento;
            return 1;
        }
        if(posicion+movimiento >= 40){
            posicion=(posicion+movimiento)%40;
            return 1;
        }
        else{
            posicion=posicion+movimiento;
            return 0;
        }
    }

    /**
     * @param posicion Posicion a la que se va a ir
     */
    public void setPosicion(int posicion) {
        if (posicion >= 0 && posicion < 40) {
            this.posicion = posicion;
        } else {
            Juego.consola.consolaimprimir("La Posicion indicada no se encuentra en el tablero");
        }
    }

    //METODOS
    public abstract void moverEnAvanzado(Juego M, String[] splitcom) throws AccionIlegal, NoComprada, VenderEdificada, TipoCasillaInvalido, InexCas, ComandoDesconocido;
    //TO STRING
    public void moverEnBasico(Juego M, String[] splitcom) throws AccionIlegal, NoComprada, VenderEdificada, TipoCasillaInvalido, InexCas, ComandoDesconocido {
        int tirada1, tirada2,suma = 0, vueltaCompletada;
        Jugador jugadorturno=M.getArrayJugadores().get(M.getTurno());
        Avatar avatarturno= jugadorturno.getAvatar();
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

                tirada1 = rnd.nextInt(5)+1;


                tirada2 = rnd.nextInt(5)+1;

                jugadorturno.setDadosLanzados(jugadorturno.getDadosLanzados()+1);
            }

            if (tirada1 == tirada2) {
                Juego.consola.consolaimprimir("Han salido dobles!");
                M.setDadosporlanzar(1);
                M.setContadordobles(M.getContadordobles()+1);
                if (jugadorturno.getCarcel() > 0) {
                    jugadorturno.setCarcel(0);
                    M.setContadordobles(0);

                    Juego.consola.consolaimprimir("Cuidado, cerrad vuestras casas con llave, " + jugadorturno.getNombre() + " salio de la carcel!!!");
                    return;

                } else if (M.getContadordobles() >= 3) {
                    M.entrarCarcel();
                    return;
                }
            }
            if ((jugadorturno.getCarcel()) == 0) {
                suma = tirada1 + tirada2;
            } else {
                jugadorturno.setCarcel(jugadorturno.getCarcel() - 1);
            }
            M.setSumadados(suma);
            M.getCasillaConcreta(avatarturno.getPosicion()).setArrayBoolean(M.getTurno(), false);

            if (jugadorturno.getEe() == 'f') {
                Juego.consola.consolaimprimir(jugadorturno.getNombre() + " TIRA LOS DADOS MUY FUERTE CONTRA EL TABLERO Y CASI LE DA A OTRO JUGADOR!!!");
                jugadorturno.setEe('n');
                Juego.consola.consolaimprimir("El colega " + avatarturno.getLetra() + " se da un voltio de " + suma +
                        " posiciones ( " + tirada1 + " " + tirada2 + " ) por el barrio desde " + M.getCasillaConcreta(avatarturno.getPosicion()).getNombre() +
                        " hasta " + M.getCasillaConcreta((avatarturno.getPosicion()+suma)%40).getNombre());
            } else {
                Juego.consola.consolaimprimir("El avatar " + avatarturno.getLetra() + " avanza " + suma +
                        " posiciones ( " + tirada1 + " " + tirada2 + " ), desde " + M.getArraycasillas()[avatarturno.getPosicion()].getNombre() +
                        " hasta " + M.getCasillaConcreta(((avatarturno.getPosicion()+suma)%40)).getNombre());
            }

            vueltaCompletada = avatarturno.setSumPosicion(suma);
            M.getCasillaConcreta(avatarturno.getPosicion()).setArrayBoolean(M.getTurno(), true);
            M.getCasillaConcreta(avatarturno.getPosicion()).frecuenciaVisita(M.getArrayJugadores().indexOf(jugadorturno));

            if ((vueltaCompletada == 1) && (jugadorturno.getCarcel() == 0)) { //este if se cumple cuando se pasa por la salida, aumenta las "vueltas" del jugador, cobra la salida y, si corresponde, aumenta el precio de los solares no comprados
                M.pasarSalida(jugadorturno);
            }
            M.getCasillaConcreta(avatarturno.getPosicion()).ejecutar(M);
            M.getCasillaConcreta(avatarturno.getPosicion()).setFrecuencia(M.getCasillaConcreta(avatarturno.getPosicion()).getFrecuencia() + 1);
            M.setDadosporlanzar(M.getDadosporlanzar()-1);
        }else{
           throw new AccionIlegal("Debes pasar de turno y esperar para poder volver a tirar");
        }
    }

    public String getTipoavatar(){
        if(this instanceof Sombrero)
            return  "Sombrero";
        else if(this instanceof Pelota)
            return  "Pelota";
        else if(this instanceof Esfinge)
            return  "Esfinge";
        else if(this instanceof Coche)
            return  "Coche";
        return "Avatar";
    }


}
