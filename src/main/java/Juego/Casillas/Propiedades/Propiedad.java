package Juego.Casillas.Propiedades;

import Excepciones.AccionIlegal;
import Excepciones.ComandoDesconocido;
import Excepciones.ExsCasillas.ExsPropiedades.ExsSolares.VenderEdificada;
import Excepciones.ExsCasillas.ExsPropiedades.NoComprada;
import Excepciones.ExsCasillas.TipoCasillaInvalido;
import Excepciones.Inexistencias.InexCas;
import Excepciones.Inexistencias.InexGrup;
import Juego.Casillas.Casilla;
import Juego.Grupo;
import Juego.Juego;
import Jugadores.Jugador;

import java.util.ArrayList;

public abstract class Propiedad extends Casilla {
    protected Jugador duenho;
    protected double alquilerAcumulado;
    protected boolean hipotecada;
    protected Grupo grupo;

    protected ArrayList<Integer> turnosProteccion; //si este array vale n en la posición i, el jugador i estará n turnos sin pagar alquiler en esta casilla

    //CONSTRUCTORES
    public Propiedad(String nombre, int posicion, int numJugadores, Jugador banca) {
        super(nombre, posicion, numJugadores);
        this.duenho = banca;
        alquilerAcumulado=0;
        hipotecada = false;
        turnosProteccion=new ArrayList<>();
        for(int i = 0; i < numJugadores; i++){
            turnosProteccion.add(0);
        }
    }

    //GETTERS
    public double getAlquilerAcumulado() {
        return alquilerAcumulado;
    }
    public ArrayList<Integer> getTurnosProteccion() {return turnosProteccion;}
    public Jugador getDuenho(){return duenho;}
    public boolean getHipotecada() {
        return hipotecada;
    }
    public Grupo getGrupo() {
        return grupo;
    }
    //SETTERS
    public void setAlquilerAcumulado(double alquilerAcumulado) {
        this.alquilerAcumulado = alquilerAcumulado;
    }
    public void setDuenho(Jugador duenho) {this.duenho = duenho;
    }
    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public void setHipotecada(Boolean hipotecada) {
        this.hipotecada = hipotecada;
    }
    //METODOS
    public boolean perteneceajugador(Jugador jugador){
        return jugador.getNombre().equals(duenho.getNombre());
    }
    public abstract double alquiler(int suma);
    public abstract double valor();
    public void comprar(Jugador comprador){
        duenho = comprador;
    }
    //TOSTRING
    public String toStringHipotecada() { //esta función devuelve el nombre de la casilla. Si la casilla está hipotecada, devuelve el nombre en rojo. Si no, en blanco.
        if (this.hipotecada) {
            return "\033[0;31m" + this.getNombre() + "\u001B[0m";
        }
        return this.getNombre();
    }

    public abstract String toStringEnventa() throws InexGrup;
    @Override
    public abstract String toString();

    @Override
    public void ejecutar(Juego partida) throws AccionIlegal, NoComprada, ComandoDesconocido, VenderEdificada, TipoCasillaInvalido, InexCas {
        Jugador jugador = partida.getArrayJugadores().get(partida.getTurno());
        int indexjug = partida.getArrayJugadores().indexOf(jugador);

        if (!(this.perteneceajugador(partida.getBanca())) && !(this.perteneceajugador(jugador)) && !(this.getHipotecada())){
            if(turnosProteccion.get(indexjug) == 0) {
                double alquiler = this.alquiler(partida.getSumadados());
                jugador.Disminucion(alquiler);
                if (jugador.getFortuna() <= 0) {
                    partida.menubancarrota(jugador, duenho);
                    return;
                }
                this.setAlquilerAcumulado(this.getAlquilerAcumulado() + this.alquiler(partida.getSumadados()));
                jugador.setDineroAlquileresPagado(jugador.getDineroAlquileresPagado() + this.alquiler(partida.getSumadados()));
                this.getDuenho().Aumento(this.alquiler(partida.getSumadados()));
                this.getDuenho().setDineroAlquileresRecibido(this.getDuenho().getDineroAlquileresRecibido() + this.alquiler(partida.getSumadados()));
                Juego.consola.consolaimprimir(jugador.getNombre() + " Ha pagado " + String.format("%.2f", alquiler) + "$ de alquiler a " + this.getDuenho().getNombre() + "\n");
            }else{
                turnosProteccion.set(indexjug, turnosProteccion.get(indexjug) -1);
            }
        }
    }

}
