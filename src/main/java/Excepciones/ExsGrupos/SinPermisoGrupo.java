package Excepciones.ExsGrupos;

import Juego.Grupo;

public final class SinPermisoGrupo extends ExGrupos {
    public SinPermisoGrupo(String mensaje, Grupo grupo) {
        super(mensaje, grupo);
    }
}
