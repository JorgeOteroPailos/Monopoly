package Juego;

import Excepciones.ExsCasillas.ExsPropiedades.ExsSolares.ExsEdificios.TipoEdificioInvalido;
import Excepciones.ExsCasillas.TipoCasillaInvalido;
import Excepciones.ExsGrupos.SinPermisoGrupo;
import Excepciones.Inexistencias.InexGrup;
import Juego.Casillas.Casilla;
import Juego.Casillas.Propiedades.Propiedad;
import Juego.Casillas.Propiedades.Solar;
import Juego.Edificios.*;
import Jugadores.Jugador;

import java.util.ArrayList;

public final class Grupo {
    private double valor;
    private final int numgrupo;
    private final ArrayList<Propiedad> arrayGrupo;
    private final ArrayList<Edificio> arrayEdificios;

    //CONSTRUCTORES
    public Grupo(int numgrupo, Casilla[] tablero){
        this.numgrupo = numgrupo;

        arrayEdificios = new ArrayList<>();
        arrayGrupo = new ArrayList<>();

        switch(this.numgrupo){
            case 1:
                arrayGrupo.add((Propiedad)tablero[1]);
                ((Propiedad)tablero[1]).setGrupo(this);
                arrayGrupo.add((Propiedad)tablero[3]);
                ((Propiedad)tablero[3]).setGrupo(this);
                break;

            case 2:
                arrayGrupo.add((Propiedad)tablero[6]);
                ((Propiedad)tablero[6]).setGrupo(this);
                arrayGrupo.add((Propiedad)tablero[8]);
                ((Propiedad)tablero[8]).setGrupo(this);
                arrayGrupo.add((Propiedad)tablero[9]);
                ((Propiedad)tablero[9]).setGrupo(this);
                break;

            case 3:
                arrayGrupo.add((Propiedad)tablero[11]);
                ((Propiedad)tablero[11]).setGrupo(this);
                arrayGrupo.add((Propiedad)tablero[13]);
                ((Propiedad)tablero[13]).setGrupo(this);
                arrayGrupo.add((Propiedad)tablero[14]);
                ((Propiedad)tablero[14]).setGrupo(this);
                break;

            case 4:
                arrayGrupo.add((Propiedad)tablero[16]);
                ((Propiedad)tablero[16]).setGrupo(this);
                arrayGrupo.add((Propiedad)tablero[18]);
                ((Propiedad)tablero[18]).setGrupo(this);
                arrayGrupo.add((Propiedad)tablero[19]);
                ((Propiedad)tablero[19]).setGrupo(this);
                break;

            case 5:
                arrayGrupo.add((Propiedad)tablero[21]);
                ((Propiedad)tablero[21]).setGrupo(this);
                arrayGrupo.add((Propiedad)tablero[23]);
                ((Propiedad)tablero[23]).setGrupo(this);
                arrayGrupo.add((Propiedad)tablero[24]);
                ((Propiedad)tablero[24]).setGrupo(this);
                break;

            case 6:
                arrayGrupo.add((Propiedad)tablero[26]);
                ((Propiedad)tablero[26]).setGrupo(this);
                arrayGrupo.add((Propiedad)tablero[27]);
                ((Propiedad)tablero[27]).setGrupo(this);
                arrayGrupo.add((Propiedad)tablero[29]);
                ((Propiedad)tablero[29]).setGrupo(this);
                break;

            case 7:
                arrayGrupo.add((Propiedad)tablero[31]);
                ((Propiedad)tablero[31]).setGrupo(this);
                arrayGrupo.add((Propiedad)tablero[32]);
                ((Propiedad)tablero[32]).setGrupo(this);
                arrayGrupo.add((Propiedad)tablero[34]);
                ((Propiedad)tablero[34]).setGrupo(this);
                break;

            case 8:
                arrayGrupo.add((Propiedad)tablero[37]);
                ((Propiedad)tablero[37]).setGrupo(this);
                arrayGrupo.add((Propiedad)tablero[39]);
                ((Propiedad)tablero[39]).setGrupo(this);
                break;
            case 9:
                arrayGrupo.add((Propiedad)tablero[12]);
                ((Propiedad)tablero[12]).setGrupo(this);
                arrayGrupo.add((Propiedad)tablero[28]);
                ((Propiedad)tablero[28]).setGrupo(this);
                break;
            case 0:
                arrayGrupo.add((Propiedad)tablero[5]);
                ((Propiedad)tablero[5]).setGrupo(this);
                arrayGrupo.add((Propiedad)tablero[15]);
                ((Propiedad)tablero[15]).setGrupo(this);
                arrayGrupo.add((Propiedad)tablero[25]);
                ((Propiedad)tablero[25]).setGrupo(this);
                arrayGrupo.add((Propiedad)tablero[35]);
                ((Propiedad)tablero[35]).setGrupo(this);
                break;
        }

        this.valor = (((Propiedad)tablero[1]).valor()+((Propiedad)tablero[3]).valor())*(Math.pow(1.3,numgrupo));
    }


    //GETTERS
    public double getValor() {
        return valor;
    }
    public int getNumgrupo() {
        return numgrupo;
    }
    public ArrayList<Edificio> getArrayEdificios() {
        return arrayEdificios;
    }

    //SETTERS
    public void setValor(double valor) {
        this.valor = valor;
    }


    //METODOS
    public void permisoGrupo(int tipoEdificio) throws TipoEdificioInvalido, SinPermisoGrupo, TipoCasillaInvalido {
        int casas = 0;
        int hoteles = 0;
        int piscinas = 0;
        int pistas = 0;

        for (Propiedad prop: arrayGrupo){
            if(prop instanceof Solar s) {
                for (Edificio e : s.getArrayEdificios()) {
                    if (e instanceof Casa)
                        casas++;
                    else if (e instanceof Hotel)
                        hoteles++;
                    else if (e instanceof Piscina)
                        piscinas++;
                    else if (e instanceof PistaDeporte)
                        pistas++;
                }
            }else{
                throw new TipoCasillaInvalido("Solo se puede edificar en Solares", prop);
            }
        }

        switch(tipoEdificio){
            case 0:
                if (hoteles >= arrayGrupo.size() && casas >= arrayGrupo.size()){
                    throw new SinPermisoGrupo("El grupo correspondiente ya tiene edificados 2 hoteles y 2 casas; no se pueden anhadir mas", this);
                }
                break;
            case 1:
                if (hoteles >= arrayGrupo.size()){
                    throw new SinPermisoGrupo("El grupo correspondiente ya tiene edificados 2 hoteles y no se pueden anhadir mas", this);
                }
                break;
            case 2:
                if (piscinas >= arrayGrupo.size()){
                    throw new SinPermisoGrupo("El grupo correspondiente ya tiene edificados 2 piscinas y no se pueden anhadir mas", this);
                }
                break;
            case 3:
                if (pistas >= arrayGrupo.size()){
                    throw new SinPermisoGrupo("El grupo correspondiente ya tiene edificados 2 pistas y no se pueden anhadir mas", this);
                }
                break;
            default:
                throw new TipoEdificioInvalido("El tipo de edificio introducido no existe", null ,tipoEdificio);
        }
    }

    public Boolean grupoComprado(Propiedad prop){
        for ( Propiedad p: arrayGrupo){
            if (!p.getDuenho().getNombre().equals(prop.getDuenho().getNombre())) {
                return false;
            }
        }
        return true;
    }

    public Boolean grupoComprado(Jugador jugador){
        for ( Propiedad prop: arrayGrupo){
            if (!prop.getDuenho().getNombre().equals(jugador.getNombre())) {
                return false;
            }
        }
        return true;
    }
    public int cuantosComprados(Jugador jugador){
        int ncomprados = 0;
        for ( Propiedad prop: arrayGrupo){
            if (prop.getDuenho().getNombre().equals(jugador.getNombre()))
                ncomprados++;
        }
        return ncomprados;
    }

    public static String NumGruptoNombre (int ngrupo) throws InexGrup {
        String resultado = "";

        switch(ngrupo){
            case 1:
                resultado += "Rojo";
                break;

            case 2:
                resultado += "Verde Oscuro";
                break;

            case 3:
                resultado += "Amarillo";
                break;

            case 4:
                resultado += "Azul Oscuro";
                break;

            case 5:
                resultado += "Morado";
                break;

            case 6:
                resultado += "Azul Claro";
                break;

            case 7:
                resultado += "Verde Claro";
                break;

            case 8:
                resultado += "Azul Cian";
                break;

            default:
                throw new InexGrup("El grupo introducido no existe", ngrupo+"");
        }
        return resultado;
    }


    public String toStringEdificiosGrupo() throws InexGrup {
        StringBuilder resultado= new StringBuilder();
        for (Edificio e : arrayEdificios){
            resultado.append("{\n" + "ID: ");
            if(e instanceof Casa)
                resultado.append("casa-").append(e.getID()).append("\n");
            else if(e instanceof Hotel)
                    resultado.append("hotel-").append(e.getID()).append("\n");
            else if(e instanceof Piscina)
                    resultado.append("piscina-").append(e.getID()).append("\n");
            else if(e instanceof PistaDeporte)
                    resultado.append("pista-").append(e.getID()).append("\n");

            resultado.append("Propietario: ").append(e.getDuenho().getNombre()).append("\n").append("Casilla: ").append(e.getSolar().getNombre()).append("\n").append("Grupo: ").append(Grupo.NumGruptoNombre(this.getNumgrupo())).append("\n").append("Coste: ").append(String.format("%.2f", e.getValor())).append("\n").append("}\n");
        }
        return resultado.toString();
    }

    public String toStringEdificios(){
        StringBuilder resultado= new StringBuilder();

        int casas = 0;
        int hoteles = 0;
        int piscinas = 0;
        int pistas = 0;

        for (Propiedad prop: arrayGrupo) {
            if (prop instanceof Solar s) {
                resultado.append("{\n" + "Propiedad: ").append(s.getNombre()).append("\n").append("Casas: [ ");

                for (Edificio e : s.getArrayEdificios()) {
                    if (e instanceof Casa) {
                        resultado.append("casa-").append(e.getID()).append(" ");
                        casas++;
                    }
                }
                resultado.append("]\n" + "Hoteles: [ ");

                for (Edificio e : s.getArrayEdificios()) {
                    if (e instanceof Hotel) {
                        resultado.append("hotel-").append(e.getID()).append(" ");
                        hoteles++;
                    }
                }

                resultado.append("]\n" + "Piscinas: [ ");

                for (Edificio e : s.getArrayEdificios()) {
                    if (e instanceof Piscina) {
                        resultado.append("piscina-").append(e.getID()).append(" ");
                        piscinas++;
                    }
                }

                resultado.append("]\n" + "Pistas: [ ");

                for (Edificio e : s.getArrayEdificios()) {
                    if (e instanceof PistaDeporte) {
                        resultado.append("pista-").append(e.getID()).append(" ");
                        pistas++;
                    }
                }

                resultado.append("]\n" + "Alquiler: ").append(String.format("%.2f", s.alquiler(0))).append("\n").append("}\n");
            }
        }

        int casaspc = 10 - (casas + (hoteles*4));
        int hotelespc = arrayGrupo.size() - hoteles;
        int piscinaspc = arrayGrupo.size() - piscinas;
        int pistaspc = arrayGrupo.size() - pistas;

        resultado.append("Se pueden construir: ").append(casaspc).append(" casas, ").append(hotelespc).append(" hoteles, ").append(piscinaspc).append(" piscinas y ").append(pistaspc).append(" pistas mas\n");

        return resultado.toString();
    }

    public String toStringDuenhos(){
        StringBuilder resultado = new StringBuilder();
        for (Propiedad prop : arrayGrupo)
            resultado.append(prop.getNombre()).append(": ").append(prop.getDuenho().getNombre()).append("\n");

        return "{\n " + resultado + " }";
    }
}

