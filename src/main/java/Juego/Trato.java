package Juego;
import Excepciones.ExsJugadores.FortunaInsuficiente;
import Excepciones.ExsTratos.NoPertenece;
import Excepciones.Inexistencias.InexTrat;
import Juego.Casillas.Propiedades.Propiedad;
import Jugadores.Jugador;

import java.util.ArrayList;
import java.util.HashMap;

public final class Trato {
    //ATRIBUTOS

    private final int ID;
    private final Jugador propone;
    private final Jugador acepta;
    private final double extra;//positivo si el que ofrece pide dinero, negativo si ofrece dinero
    private final ArrayList<Integer> turnosSinAlquilerOfrece;
    private final ArrayList<Propiedad> propiedadesSinAlquilerOfrece;

    private final ArrayList<Propiedad> propiedadesOfrece;
    private final ArrayList<Propiedad> propiedadesPide;

    private final ArrayList<Integer> turnosSinAlquilerPide;
    private final ArrayList<Propiedad> propiedadesSinAlquilerPide;
    





    //CONSTRUCTORES
    public Trato(Jugador propone, Jugador acepta, double extra, ArrayList<Propiedad> propiedadesOfrece, ArrayList<Propiedad> propiedadesSinAlquilerOfrece, ArrayList<Integer> turnosSinAlquilerOfrece,
                 ArrayList<Propiedad> propiedadesPide, ArrayList<Propiedad> propiedadesSinAlquilerPide, ArrayList<Integer> turnosSinAlquilerPide, int ID){
        this.propone=propone;
        this.acepta=acepta;
        this.extra=extra;

        this.propiedadesOfrece=propiedadesOfrece;
        this.propiedadesSinAlquilerOfrece=propiedadesSinAlquilerOfrece;
        this.turnosSinAlquilerOfrece=turnosSinAlquilerOfrece;

        this.propiedadesPide=propiedadesPide;
        this.propiedadesSinAlquilerPide=propiedadesSinAlquilerPide;
        this.turnosSinAlquilerPide=turnosSinAlquilerPide;

        this.ID=ID;



    }

    //getters
    public Jugador getPropone(){return propone;}
    public Jugador getAcepta(){return acepta;}

    public int getID(){return ID;}


    public void aceptarTrato(HashMap<Integer,Trato> arrayTratos, int ID, ArrayList<Jugador> arrayJugadores, int turno) throws FortunaInsuficiente, InexTrat, NoPertenece {
        Jugador jugadorturno=arrayJugadores.get(turno);
        if(!arrayTratos.containsKey(ID)){
            throw new InexTrat("El trato "+ID+" no existe, no puedes aceptarlo", ID+"");
        }
        Trato trato= arrayTratos.get(ID);
        if(jugadorturno!=trato.acepta){
            throw new NoPertenece("No puedes aceptar este trato", trato);
        }
        if(extra>0){
            if(jugadorturno.getFortuna()<extra){
                throw new FortunaInsuficiente("No tienes dinero suficiente para aceptar este trato en este momento", jugadorturno, extra);
            }

        }
        else if(extra<0){
            if(trato.propone.getFortuna()<-extra){
                throw new FortunaInsuficiente("Ahora mismo, "+trato.propone.getNombre()+" no tiene suficiente dinero para aceptar el trato", trato.propone, extra);
            }
        }

        jugadorturno.Aumento(-extra);
        trato.propone.Aumento(extra);
        for (Propiedad p: trato.propiedadesOfrece){
            trato.propone.getArrayPropiedades().remove(p.getNombre());
            jugadorturno.getArrayPropiedades().put(p.getNombre(),p);
            p.setDuenho(acepta);
        }
        for (Propiedad p: trato.propiedadesPide){
            jugadorturno.getArrayPropiedades().remove(p.getNombre());
            trato.propone.getArrayPropiedades().put(p.getNombre(),p);
            p.setDuenho(propone);
        }
        for (Propiedad p: trato.propiedadesSinAlquilerPide){
            //p.getTurnosProteccion().set(arrayJugadores.indexOf(trato.propone),trato.turnosSinAlquilerPide.get(arrayJugadores.indexOf(trato.propone)));
            p.getTurnosProteccion().set(arrayJugadores.indexOf(propone),this.turnosSinAlquilerPide.get(propiedadesSinAlquilerPide.indexOf(p)));
        }
        for (Propiedad p: trato.propiedadesSinAlquilerOfrece){
            //p.getTurnosProteccion().set(arrayJugadores.indexOf(trato.acepta),trato.turnosSinAlquilerOfrece.get(arrayJugadores.indexOf(trato.acepta)));
            p.getTurnosProteccion().set(arrayJugadores.indexOf(acepta),this.turnosSinAlquilerOfrece.get(propiedadesSinAlquilerOfrece.indexOf(p)));
        }

        Juego.consola.consolaimprimir("Trato aceptado correctamente");
        arrayTratos.remove(ID);

        chequearTratos(arrayTratos);

    }




    public void chequearTratos(HashMap<Integer,Trato> arrayTratos){
            for (Trato trato : arrayTratos.values()) {
                chequearTrato(arrayTratos, trato);
            }
            //ANHADIR EXCEPTIONS A LOS FOR EACH
    }

    public void chequearTrato(HashMap<Integer,Trato> arrayTratos, Trato trato){
        for (Propiedad p:propiedadesOfrece){
            if(!trato.propone.getArrayPropiedades().containsKey(p.getNombre())){
                arrayTratos.remove(trato.ID);
                return;
            }
        }
        for (Propiedad p:propiedadesSinAlquilerOfrece){
            if(!trato.propone.getArrayPropiedades().containsKey(p.getNombre())){
                arrayTratos.remove(trato.ID);
                return;
            }
        }
        for(Propiedad p:propiedadesPide){
            if(!trato.acepta.getArrayPropiedades().containsKey(p.getNombre())){
                arrayTratos.remove(trato.ID);
                return;
            }
        }
        for (Propiedad p:propiedadesSinAlquilerPide){
            if(!trato.acepta.getArrayPropiedades().containsKey(p.getNombre())){
                arrayTratos.remove(trato.ID);
                return;
            }
        }
    }

    public void eliminarTrato(HashMap<Integer,Trato> arrayTratos, int ID, Jugador jugadorTurno) throws NoPertenece {

        Trato trato= arrayTratos.get(ID);
        if(jugadorTurno!=trato.propone){
            throw new NoPertenece("No puedes eliminar este trato", trato);
        }
        arrayTratos.remove(ID);
        Juego.consola.consolaimprimir("Trato eliminado con éxito");
    }

    public void eliminarTrato(HashMap<Integer,Trato> arrayTratos, int ID){
        arrayTratos.remove(ID);
    }

    @Override
    public String toString(){
        int i;
        StringBuilder resultado= new StringBuilder();

        resultado.append("Trato ").append(ID).append(": ").append(propone.getNombre()).append(" pide a ").append(acepta.getNombre()).append(" ");
        if(!propiedadesPide.isEmpty()){
            resultado.append(propiedadesPide.get(0).getNombre());}
        for(i=1;i<propiedadesPide.size();i++){
            resultado.append(" y ").append(propiedadesPide.get(i).getNombre());
        }
        if(!propiedadesPide.isEmpty() && extra>0){
            resultado.append(" y ");}
        if(extra>0){
            resultado.append(extra).append("€");
        }
        for(i=0;i<turnosSinAlquilerPide.size();i++){
            resultado.append(" y ").append(turnosSinAlquilerPide.get(i)).append(" turnos sin pagar en ").append(propiedadesSinAlquilerPide.get(i).getNombre());
        }

        resultado.append(" por ");

        if(!propiedadesOfrece.isEmpty()){
            resultado.append(propiedadesOfrece.get(0).getNombre());}
        for(i=1;i<propiedadesOfrece.size();i++){
            resultado.append(" y ").append(propiedadesOfrece.get(i).getNombre());
        }
        if(!propiedadesOfrece.isEmpty() && extra<0){
            resultado.append(" y ");}
        if(extra<0){
            resultado.append(-extra).append("€");
        }
        for(i=0;i<turnosSinAlquilerOfrece.size();i++){
            resultado.append(" y ").append(turnosSinAlquilerOfrece.get(i)).append(" turnos sin pagar en ").append(propiedadesSinAlquilerOfrece.get(i).getNombre());
        }

        return resultado.toString();
    }

}
