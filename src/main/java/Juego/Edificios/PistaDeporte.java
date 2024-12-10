package Juego.Edificios;

import Juego.Casillas.Propiedades.Solar;

public final class PistaDeporte extends Edificio {
    public PistaDeporte(int ID, Solar solar) {
        super(ID, solar);
        valor = solar.valor() * 1.25;
    }
}
