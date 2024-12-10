package Juego.Casillas;
/*
tipo 0-> salida
tipo 1-> solar
tipo 2-> impuesto
tipo 3-> suerte
tipo 4-> caja de la comunidad
tipo 5-> transportes
tipo 6-> carcel
tipo 7-> servicios
tipo 8-> parking
tipo 9-> ve a la cárcel
...*/

import Excepciones.AccionIlegal;
import Excepciones.ComandoDesconocido;
import Excepciones.ExsCasillas.ExsPropiedades.ExsSolares.VenderEdificada;
import Excepciones.ExsCasillas.ExsPropiedades.NoComprada;
import Excepciones.ExsCasillas.TipoCasillaInvalido;
import Excepciones.Inexistencias.InexCas;
import Juego.Juego;
import Jugadores.Jugador;

import java.util.ArrayList;

public abstract class Casilla {

    protected final String nombre;
    protected int frecuencia;
    protected final ArrayList<Boolean> arrayBoolean;
    protected final ArrayList<Integer> arrayVisitas;

    //CONSTRUCTORES
    public Casilla(String nombre, int posicion,int numJugadores) {
        this.nombre = nombre;

        arrayVisitas = new ArrayList<>();
        arrayBoolean = new ArrayList<>();
        for(int i = 0; i < numJugadores; i++){
            arrayBoolean.add(posicion==0);
            arrayVisitas.add(0);
        }

        frecuencia = 0;
    }

    //GETTERS
    public String getNombre() {
        return nombre;
    }
    public ArrayList<Boolean> getArrayBoolean() {
        return arrayBoolean;
    }
    public int getFrecuencia() {
        return frecuencia;
    }


    //SETTERS
    public void setArrayBoolean(int indice, Boolean estado ) {
        arrayBoolean.set(indice, estado);
    }
    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }

    //METODOS
    public Boolean estaAvatar(int index){
        return arrayBoolean.get(index);
    }
    public int frecuenciaVisita(int index){
        return arrayVisitas.get(index);
    }
    public String toStringJugadoresPresentes(ArrayList<Jugador> arrayJugadores){
        StringBuilder resultado= new StringBuilder();
        for(int i=0;i<arrayBoolean.size();i++){
            if(estaAvatar(i)){
                resultado.append("&").append(arrayJugadores.get(i).getAvatar().getLetra()).append(" ");
            }
        }
        return String.format("%-20s", resultado);
    }
    @Override
    public abstract String toString();

    public abstract void ejecutar(Juego partida) throws AccionIlegal, NoComprada, VenderEdificada, TipoCasillaInvalido, InexCas, ComandoDesconocido;

    public int saberColor() {
        return 250;
    }
}
