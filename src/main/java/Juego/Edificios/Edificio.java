package Juego.Edificios;

import Juego.Casillas.Propiedades.Solar;
import Jugadores.Jugador;

public abstract class Edificio {
    //ATRIBUTOS
    protected int ID;
    protected double valor;
    protected Solar solar;

    //GETTERS
    public double getValor() {
        return valor;
    }

    public int getID() {
        return ID;
    }

    public Jugador getDuenho() {
        return solar.getDuenho();
    }
    public Solar getSolar() {
        return solar;
    }
    //SETTERS

    //CONSTRUCTORES
    public Edificio(int ID, Solar solar) {
        this.valor = 0;
        this.ID = ID;
        this.solar = solar;
    }

    public static int StringtoIntEdificio(String TipoEdificio){
        return switch (TipoEdificio) {
            case "casas", "Casa", "Casas", "casa" -> 0;
            case "hoteles", "Hotel", "Hoteles", "hotel" -> 1;
            case "piscinas", "Piscina", "Piscinas", "piscina" -> 2;
            case "pistas", "Pista", "Pistas", "pista" -> 3;
            default -> -1;
        };
    }
    }





