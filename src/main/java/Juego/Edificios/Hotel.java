package Juego.Edificios;

import Juego.Casillas.Propiedades.Solar;

public final class Hotel extends Edificio{

    public Hotel(int ID, Solar solar) {
        super(ID, solar);
        valor = solar.valor() * 0.60;
    }
}
