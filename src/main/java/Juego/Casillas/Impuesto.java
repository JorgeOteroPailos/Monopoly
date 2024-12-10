package Juego.Casillas;

import Excepciones.AccionIlegal;
import Excepciones.ComandoDesconocido;
import Excepciones.ExsCasillas.ExsPropiedades.ExsSolares.VenderEdificada;
import Excepciones.ExsCasillas.ExsPropiedades.NoComprada;
import Excepciones.ExsCasillas.TipoCasillaInvalido;
import Excepciones.Inexistencias.InexCas;
import Juego.Casillas.Acciones.Parking;
import Juego.Juego;
import Jugadores.Jugador;

public final class Impuesto extends Casilla {

    private final double deuda;
    //CONSTRUCTORES
    public Impuesto(String nombre, int posicion, int numJugadores) {
        super(nombre, posicion, numJugadores);
        switch(posicion){
            case 4-> this.deuda = Juego.mediaglobal()/2;
            case 38-> this.deuda = Juego.mediaglobal();
            default -> this.deuda = 0;
        }
    }

    //GETTERS
    public double getDeuda() {
        return deuda;
    }

    //SETTERS


    //METODOS
    @Override
    public String toString(){
        return  "Tipo: Impuesto\nDeuda: " + (String.format("%.2f", deuda));
    }

    @Override
    public void ejecutar(Juego partida) throws AccionIlegal, NoComprada, VenderEdificada, TipoCasillaInvalido, InexCas, ComandoDesconocido {
            Jugador jugador = partida.getArrayJugadores().get(partida.getTurno());

            jugador.Disminucion(this.getDeuda());
            if (jugador.getFortuna() <= 0)
                partida.menubancarrota(jugador, partida.getBanca());
            jugador.setDineroImpuestos(jugador.getDineroImpuestos() + this.getDeuda());
            ((Parking) partida.getArraycasillas()[20]).setBote(((Parking) partida.getArraycasillas()[20]).getBote() + this.getDeuda());
            Juego.consola.consolaimprimir(jugador.getNombre() + " Ha pagado " + String.format("%.2f", this.getDeuda()) + "$ de impuestos (._.)\n");
    }
}
