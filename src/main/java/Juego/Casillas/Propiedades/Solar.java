package Juego.Casillas.Propiedades;

import Excepciones.ExsCasillas.ExsPropiedades.ExsSolares.ExsEdificios.TipoEdificioInvalido;
import Excepciones.ExsCasillas.ExsPropiedades.ExsSolares.SinPermisoSolar;
import Excepciones.Inexistencias.InexGrup;
import Juego.Edificios.*;
import Juego.Grupo;
import Juego.Juego;
import Jugadores.Jugador;

import java.util.ArrayList;

public final class Solar extends Propiedad{
    private final ArrayList<Edificio> arrayEdificios;
    public Solar(String nombre, int posicion, int numJugadores, Jugador banca) {
        super(nombre, posicion, numJugadores, banca);
        arrayEdificios = new ArrayList<>();
    }

    //GETTERS
    public ArrayList<Edificio> getArrayEdificios() {
        return arrayEdificios;
    }

    //METODOS
    public void Edificar(Edificio nuevoedificio, Grupo grupo){
        if(nuevoedificio instanceof Hotel) {
            duenho.getArrayEdificios().removeIf(e -> e instanceof Casa);
            arrayEdificios.removeIf(e -> e instanceof Casa);
            grupo.getArrayEdificios().removeIf(e -> e instanceof Casa);
        }

        arrayEdificios.add(nuevoedificio);
        duenho.Disminucion(nuevoedificio.getValor());
        duenho.setDineroInvertido(duenho.getDineroInvertido() + nuevoedificio.getValor());
        duenho.getArrayEdificios().add(nuevoedificio);
        grupo.getArrayEdificios().add(nuevoedificio);
    }
    public int NumeroTipoEdificios(int tipo){
        int res = 0;
        for (Edificio edificio : arrayEdificios){
            switch(tipo){
                case 0:
                    if(edificio instanceof Casa)
                        res++;
                    break;
                case 1:
                    if(edificio instanceof Hotel)
                        res++;
                    break;
                case 2:
                    if(edificio instanceof Piscina)
                        res++;
                    break;
                case 3:
                    if(edificio instanceof PistaDeporte)
                        res++;
                    break;
            }
        }
        return res;
    }

    public void PermisoCasilla(int tipoEdificio) throws TipoEdificioInvalido, SinPermisoSolar {
        int casas = 0, hoteles = 0;

        for(Edificio e: arrayEdificios){
            if(e instanceof Casa)
                casas++;
            else if(e instanceof Hotel)
                hoteles++;
        }

        switch(tipoEdificio){
            case 0:
                if (casas >= 4){
                    throw new SinPermisoSolar("La casilla correspondiente ya tiene 4 casas; prueba a crear un hotel", this);
                }
                break;
            case 1:
                if (casas != 4){
                    throw new SinPermisoSolar("La casilla correspondiente aun no tiene 4 casas para convertirlas en hotel", this);
                }
                break;
            case 2:
                if (hoteles <= 0 || casas <= 1){
                    throw new SinPermisoSolar("La casilla correspondiente necesita minimo 1 hotel y 2 casas parar construir una piscina", this);
                }
                break;
            case 3:
                if (hoteles <= 1){
                    throw new SinPermisoSolar("La casilla correspondiente aun no tiene 2 hoteles como para poder construir una pista de deporte", this);
                }
                break;
            default:
                throw new TipoEdificioInvalido("El tipo de edificio introducido no existe", this, tipoEdificio);
        }
    }
    public String toStringEdificios(){
        StringBuilder resultado = new StringBuilder();

        resultado.append("Edificios ").append(": [ ");

        for (Edificio edificio : arrayEdificios){
            if(edificio instanceof Casa)
                resultado.append("casa-").append(edificio.getID()).append(" ");
            else if(edificio instanceof Hotel)
                resultado.append("hotel-").append(edificio.getID()).append(" ");
            else if(edificio instanceof Piscina)
                resultado.append("piscina-").append(edificio.getID()).append(" ");
            else if(edificio instanceof PistaDeporte)
                resultado.append("pista-").append(edificio.getID()).append(" ");
        }

        return resultado + "]";
    }
    @Override
    public double alquiler(int suma){
        int casas = 0;
        int hoteles = 0;
        int piscinas = 0;
        int pistas = 0;

        for(Edificio e : arrayEdificios){
            if(e instanceof Casa)
                casas++;
            else if(e instanceof Hotel)
                hoteles++;
            else if(e instanceof Piscina)
                piscinas++;
            else if(e instanceof PistaDeporte)
                pistas++;
        }

        int Alqh= hoteles * 70;
        int Alqp= piscinas * 25;
        int Alqd= pistas * 25;

        int Alqc=0;

        switch(casas){
            case 0:
                break;
            case 1:
                Alqc = 5;
                break;
            case 2:
                Alqc = 15;
                break;
            case 3:
                Alqc = 35;
                break;
            case 4:
                Alqc = 50;
                break;
        }
        double val = 0.1*valor();
        double alq =  (val + val*Alqc + val*Alqh + val*Alqp + val*Alqd);

        if (grupo.grupoComprado(this.getDuenho())) {
            return alq * 2;
        }
        return alq;
    }

    @Override
    public double valor(){
        return (Juego.VALORPRIMERSOLAR*Math.pow(1.3,(grupo.getNumgrupo()-1)))*Math.pow(1.05, Juego.aumentosXvuelta);
    }

    @Override
    public String toStringEnventa() throws InexGrup {
        return "Tipo: Solar\nGrupo: " + Grupo.NumGruptoNombre(grupo.getNumgrupo()) + "\nValor: " + String.format("%.2f", valor());
    }

    @Override
    public String toString(){

        String resultado;
        try {
            resultado = "Tipo: Solar\n" +
                    "Grupo: " + Grupo.NumGruptoNombre(grupo.getNumgrupo()) + "\n" +
                    "Propietario: " + duenho.getNombre() + "\n" +
                    "Valor: " + String.format("%.2f", valor()) + "\n" +
                    "Alquiler: " + String.format("%.2f", alquiler(0)) + "\n" +
                    toStringEdificios();
        } catch (InexGrup e) {
            return null;
        }

        return "{ " + resultado + " }\n";
    }

    public String toStringCondiciones(Jugador jugador, int indexjug, Grupo grupo){
        return "La casilla ha sido visitada " + arrayVisitas.get(indexjug) + " veces por " + jugador.getNombre() + "\n" +
                "Los propietarios del grupo son:\n" + grupo.toStringDuenhos();
    }

    @Override
    public int saberColor() {
        return getGrupo().getNumgrupo();
    }
}
