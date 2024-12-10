package Juego.Edificios;

import Juego.Casillas.Propiedades.Solar;

public final class Casa extends Edificio{

    public Casa(int ID, Solar solar) {
        super(ID, solar);
        valor = solar.valor() * 0.60;

    }

}
