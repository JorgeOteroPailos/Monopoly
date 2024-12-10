package Juego.Edificios;

import Juego.Casillas.Propiedades.Solar;

public final class Piscina extends Edificio{

    public Piscina(int ID, Solar solar) {
        super(ID, solar);
        valor = solar.valor() * 0.70;
    }
}
