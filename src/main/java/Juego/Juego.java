package Juego;

import Excepciones.AccionIlegal;
import Excepciones.ComandoDesconocido;
import Excepciones.ExsCasillas.ExsPropiedades.ExsSolares.EdificarHipotecada;
import Excepciones.ExsCasillas.ExsPropiedades.ExsSolares.ExsEdificios.TipoEdificioInvalido;
import Excepciones.ExsCasillas.ExsPropiedades.ExsSolares.SinPermisoEdificar;
import Excepciones.ExsCasillas.ExsPropiedades.ExsSolares.SinPermisoSolar;
import Excepciones.ExsCasillas.ExsPropiedades.ExsSolares.VenderEdificada;
import Excepciones.ExsCasillas.ExsPropiedades.NoComprada;
import Excepciones.ExsCasillas.TipoCasillaInvalido;
import Excepciones.ExsGrupos.SinPermisoGrupo;
import Excepciones.ExsJugadores.ExsAvatares.PosicionIncorrecta;
import Excepciones.ExsJugadores.ExsAvatares.TipoAvatarInvalido;
import Excepciones.ExsJugadores.FortunaInsuficiente;
import Excepciones.ExsTratos.NoPertenece;
import Excepciones.Inexistencias.*;
import Excepciones.NombreEnUso;
import Juego.Cartas.Carta;
import Juego.Cartas.CartaCajaComunidad;
import Juego.Cartas.CartaSuerte;
import Juego.Casillas.Acciones.AccionCajaComunidad;
import Juego.Casillas.Acciones.AccionSuerte;
import Juego.Casillas.Acciones.IrCarcel;
import Juego.Casillas.Acciones.Parking;
import Juego.Casillas.Casilla;
import Juego.Casillas.Especiales.*;
import Juego.Casillas.Impuesto;
import Juego.Casillas.Propiedades.Propiedad;
import Juego.Casillas.Propiedades.Servicio;
import Juego.Casillas.Propiedades.Solar;
import Juego.Casillas.Propiedades.Transporte;
import Juego.Edificios.*;
import Jugadores.Avatares.*;
import Jugadores.Jugador;

import java.util.*;


public final class Juego implements Comando{
    public static final double VALORPRIMERSOLAR = 5000;
    public static final double MINIMO=-4000000;
    public static ConsolaNormal consola;

    //ATRIBUTOS
    private final ArrayList<Jugador> arrayJugadores;
    private int turno;
    private final Jugador banca;
    private final Casilla[] arraycasillas;
    private final Grupo[] arrayGrupos;
    private int idEdificio;
    private final HashMap<Integer,Trato> arrayTratos;
    private int sumadados;
    private int dadosporlanzar;
    private int contadordobles;
    private int comprasRealizadas;
    private int movimientosRealizados;
    private boolean hayganador;
    public int CambioModo;
    public static int aumentosXvuelta;

    //CONSTUCTORES
    public Juego() throws NombreEnUso, TipoAvatarInvalido, ComandoDesconocido, AccionIlegal { //constructor de Monopoly xd
        consola = new ConsolaNormal();
        consola.consolaimprimir("""
                PASOS PARA LA INICIALIZACION
                1. Crear Jugadores
                para crear jugadores (minimo 2, maximo 6) introduzca el comando "crear jugador nombre tipoavatar"
                2. Iniciar partida
                para iniciar partida introduzca el comando "iniciar\"""");

        arrayJugadores = new ArrayList<>();

        String comando = "";
        String[] splitcom;
        StringBuilder dn = new StringBuilder("defaultnameI");

        while((!comando.equals("iniciar") || arrayJugadores.size()<2) && arrayJugadores.size()< 6) { //este bucle dura mientras no se hayan creados los jugadores correctamente e iniciado la partida
            comando = consola.consolaleer("");
            splitcom = comando.split(" ");

            try {
                switch (splitcom.length) {
                    case 4:
                        if ((splitcom[0].equals("crear")) && (splitcom[1].equals("jugador"))) {
                            char avatar;
                            while (true) {
                                Random rnd = new Random();
                                avatar = (char) ('A' + rnd.nextInt(26));

                                int letrausada = 0;
                                for (Jugador jugador : arrayJugadores) {
                                    if (jugador.getAvatar().getLetra() == avatar) {
                                        letrausada++;
                                    }
                                }
                                if (letrausada == 0) { //comprobación de que no pueda haber dos avatares con la misma letra
                                    break;
                                }
                            }

                            Avatar a;
                            try {
                                switch (splitcom[3]) {
                                    case "Sombrero", "sombrero" -> a = new Sombrero(avatar);
                                    case "Coche", "coche" -> a = new Coche(avatar);
                                    case "Pelota", "pelota" -> a = new Pelota(avatar);
                                    case "Esfinge", "esfinge" -> a = new Esfinge(avatar);
                                    default ->
                                            throw new TipoAvatarInvalido("El tipo de avatar introducido no existe", null);
                                }
                            } catch (TipoAvatarInvalido error) {
                                try {
                                    consola.consolaimprimir(ANSICodes.ANSI_RED + "ERROR -> " + error.getMessage() + ANSICodes.ANSI_RESET);
                                    switch (consola.consolaleer("Indica un Avatar valido: ")) {
                                        case "Sombrero", "sombrero" -> a = new Sombrero(avatar);
                                        case "Coche", "coche" -> a = new Coche(avatar);
                                        case "Pelota", "pelota" -> a = new Pelota(avatar);
                                        case "Esfinge", "esfinge" -> a = new Esfinge(avatar);
                                        default ->
                                                throw new TipoAvatarInvalido("El tipo de avatar SIGUE SIN EXISTIR", null);
                                    }
                                } catch (TipoAvatarInvalido error2) {
                                    try {
                                        consola.consolaimprimir(ANSICodes.ANSI_RED + "ERROR -> " + error2.getMessage() + ANSICodes.ANSI_RESET);
                                        switch (consola.consolaleer("POR FAVOR Indica un Avatar valido: ")) {
                                            case "Sombrero", "sombrero" -> a = new Sombrero(avatar);
                                            case "Coche", "coche" -> a = new Coche(avatar);
                                            case "Pelota", "pelota" -> a = new Pelota(avatar);
                                            case "Esfinge", "esfinge" -> a = new Esfinge(avatar);
                                            default ->
                                                    throw new TipoAvatarInvalido("El tipo de avatar introducido no existe", null);
                                        }
                                    } catch (TipoAvatarInvalido error3) {
                                        a = new Sombrero(avatar);

                                    }
                                }
                            }

                            Jugador j;
                            String nombre = splitcom[2];
                            try {
                                for (Jugador jugador : arrayJugadores) {
                                    if (jugador.getNombre().equals(nombre)) { //comprobación de que no pueda haber dos jugadores con el mismo nombre
                                        throw new NombreEnUso("No puede haber dos jugadores con el mismo nombre!");
                                    }
                                }
                                j = new Jugador(nombre, a);
                            } catch (NombreEnUso error) {
                                try {
                                    consola.consolaimprimir(ANSICodes.ANSI_RED + "ERROR -> " + error.getMessage() + ANSICodes.ANSI_RESET);
                                    nombre = consola.consolaleer("Indica otro nombre: ");
                                    for (Jugador jugador : arrayJugadores) {
                                        if (jugador.getNombre().equals(nombre)) { //comprobación de que no pueda haber dos jugadores con el mismo nombre
                                            throw new NombreEnUso("QUE NO PUEDE HABER DOS JUGADORES CON EL MISMO NOMBRE!");
                                        }
                                    }
                                    j = new Jugador(nombre, a);
                                } catch (NombreEnUso error2) {
                                    try {
                                        consola.consolaimprimir(ANSICodes.ANSI_RED + "ERROR -> " + error2.getMessage() + ANSICodes.ANSI_RESET);
                                        nombre = consola.consolaleer("POR FAVOR Indica otro nombre: ");
                                        for (Jugador jugador : arrayJugadores) {
                                            if (jugador.getNombre().equals(nombre)) { //comprobación de que no pueda haber dos jugadores con el mismo nombre
                                                throw new NombreEnUso("Vale bro ya lo hago yo todo");
                                            }
                                        }
                                        j = new Jugador(nombre, a);
                                    } catch (NombreEnUso error3) {
                                        consola.consolaimprimir(ANSICodes.ANSI_RED + "ERROR -> " + error3.getMessage() + ANSICodes.ANSI_RESET);
                                        j = new Jugador(dn.toString(), a);
                                        dn.append("I");
                                    }
                                }
                            }
                            arrayJugadores.add(j);

                            consola.consolaimprimir(j.toStringCom());
                            consola.consolaimprimir("");
                        } else {
                            throw new ComandoDesconocido("Comando desconocido: ", comando);
                        }
                        break;
                    case 1:
                        if (splitcom[0].equals("iniciar")) {
                            if (arrayJugadores.size() > 1)
                                consola.consolaimprimir("Iniciando...");
                            else {
                                throw new AccionIlegal("Deben crearse minimo dos jugadores antes de iniciar la partida");
                            }
                        } else {
                            throw new ComandoDesconocido("Comando desconocido: ", comando);
                        }
                        break;
                    default:
                        throw new ComandoDesconocido("Comando desconocido: ", comando);
                }
            }catch (ComandoDesconocido error){
                consola.consolaimprimir(ANSICodes.ANSI_RED + "ERROR -> " + error.getMessage() + error.getComando()  + ANSICodes.ANSI_RESET);
            }catch (AccionIlegal error){
                consola.consolaimprimir(ANSICodes.ANSI_RED + "ERROR -> " + error.getMessage()  + ANSICodes.ANSI_RESET);
            }
        }

        banca = new Jugador("Banca"); //declaramos al jugador especia la banca

        arrayTratos=new HashMap<>();

        arraycasillas = new Casilla[40]; //declaramos el array de casillas (estático porque no va a cambiar en tiempo de ejecución) y creamos cada casilla pasando como argumentos sus distintos valores(nombre, precio, etc.)


        arraycasillas[1] = new Solar("Solar1",1,arrayJugadores.size(), banca);
        arraycasillas[2] = new AccionCajaComunidad("Caja",2,arrayJugadores.size());
        arraycasillas[3] = new Solar("Solar2",3,arrayJugadores.size(), banca);
        arraycasillas[6] = new Solar("Solar3",6, arrayJugadores.size(), banca);
        arraycasillas[7] = new AccionSuerte("Suerte",7,arrayJugadores.size());
        arraycasillas[8] = new Solar("Solar4",8,arrayJugadores.size(), banca);
        arraycasillas[9] = new Solar("Solar5",9,arrayJugadores.size(), banca);
        arraycasillas[10] = new Carcel("Carcel",10, arrayJugadores.size());
        arraycasillas[11] = new Solar("Solar6",11,arrayJugadores.size(), banca);
        arraycasillas[13] = new Solar("Solar7",13,arrayJugadores.size(), banca);
        arraycasillas[14] = new Solar("Solar8",14,arrayJugadores.size(), banca);
        arraycasillas[16] = new Solar("Solar9",16,arrayJugadores.size(), banca);
        arraycasillas[17] = new AccionCajaComunidad("Caja",17,arrayJugadores.size());
        arraycasillas[18] = new Solar("Solar10",18,arrayJugadores.size(), banca);
        arraycasillas[19] = new Solar("Solar11",19,arrayJugadores.size(), banca);
        arraycasillas[20] = new Parking("Parking",20,arrayJugadores.size());
        arraycasillas[21] = new Solar("Solar12",21,arrayJugadores.size(), banca);
        arraycasillas[22] = new AccionSuerte("Suerte",22,arrayJugadores.size());
        arraycasillas[23] = new Solar("Solar13",23,arrayJugadores.size(), banca);
        arraycasillas[24] = new Solar("Solar14",24,arrayJugadores.size(), banca);
        arraycasillas[26] = new Solar("Solar15",26,arrayJugadores.size(), banca);
        arraycasillas[27] = new Solar("Solar16",27,arrayJugadores.size(), banca);
        arraycasillas[29] = new Solar("Solar17",29,arrayJugadores.size(), banca);
        arraycasillas[30] = new IrCarcel("IrCarcel",30,arrayJugadores.size());
        arraycasillas[31] = new Solar("Solar18",31,arrayJugadores.size(), banca);
        arraycasillas[32] = new Solar("Solar19",32,arrayJugadores.size(), banca);
        arraycasillas[33] = new AccionCajaComunidad("Caja",33,arrayJugadores.size());
        arraycasillas[34] = new Solar("Solar20",34,arrayJugadores.size(), banca);
        arraycasillas[36] = new AccionSuerte("Suerte",36,arrayJugadores.size());
        arraycasillas[37] = new Solar("Solar21",37,arrayJugadores.size(), banca);
        arraycasillas[39] = new Solar("Solar22",39,arrayJugadores.size(), banca);


        arraycasillas[0] = new Salida("Salida",0,arrayJugadores.size());
        arraycasillas[0].setFrecuencia(arrayJugadores.size());
        arraycasillas[4] = new Impuesto("ImpuestoI",4,arrayJugadores.size());
        arraycasillas[38] = new Impuesto("Impuesto II",38,arrayJugadores.size());

        arraycasillas[5] = new Transporte("TransporteI",5,arrayJugadores.size(), banca);
        arraycasillas[15] = new Transporte("TransporteII",15,arrayJugadores.size(), banca);
        arraycasillas[25] = new Transporte("TransporteIII",25,arrayJugadores.size(), banca);
        arraycasillas[35] = new Transporte("TransporteIV",35,arrayJugadores.size(), banca);

        arraycasillas[12] = new Servicio("ServicioI",12,arrayJugadores.size(), banca);
        arraycasillas[28] = new Servicio("ServicioII",28,arrayJugadores.size(),banca);

        for(Jugador jugador : arrayJugadores){
            jugador.setFortuna((mediaglobal()*22)/3);
            jugador.setDineroAlquileresPagado(0);
            jugador.setDineroAlquileresRecibido(0);
            jugador.setDineroImpuestos(0);
            jugador.setDineroBoteInversiones(0);
            jugador.setDineroInvertido(0);
            jugador.setDineroSalida(0);
            jugador.setVecesCarcel(0);
            jugador.setNumVueltas(0);
        }

        arrayGrupos = new Grupo[10];

        arrayGrupos[1] = new Grupo(1,arraycasillas);
        arrayGrupos[0] = new Grupo(0,arraycasillas);
        for(int i = 2; i < 10; i++){
            arrayGrupos[i] = new Grupo(i, arraycasillas);
        }

        idEdificio = 0;
        dadosporlanzar = 0;
        contadordobles = 0;
        comprasRealizadas = 0;
        movimientosRealizados = 0;
        CambioModo = 0;
        hayganador = false;
        aumentosXvuelta = 0;
    }

    //GETTERS
    public int getTurno () {
        return turno;
    }
    public int getDadosporlanzar(){return dadosporlanzar;}
    public int getContadordobles(){return contadordobles;}
    public int getMovimientosRealizados(){return movimientosRealizados;}
    public ArrayList<Jugador> getArrayJugadores() {
        return arrayJugadores;
    }
    public Casilla[] getArraycasillas() {
        return arraycasillas;
    }
    public Casilla getCasillaConcreta(int pos){
        return arraycasillas[pos];
    }
    public int getSumadados() {
        return sumadados;
    }
    public Jugador getBanca() {
        return banca;
    }

    //SETTERS
    public void setTurno (int turno){
        if (turno > this.arrayJugadores.size() - 1)
            this.turno = 0;
        else
            this.turno =  turno;
    }
    public void setSumadados(int sumadados) {
        this.sumadados = sumadados;
    }
    public void setDadosporlanzar(int n){dadosporlanzar=n;}
    public void setContadordobles(int n){contadordobles=n;}
    public void setMovimientosRealizados(int n){movimientosRealizados=n;}
    public void setCambioModo(int cambioModo) {
        CambioModo = cambioModo;
    }


    //METODOS
    public static double mediaglobal(){
        return (((VALORPRIMERSOLAR*2)+(VALORPRIMERSOLAR*1.3*3)+(VALORPRIMERSOLAR*Math.pow(1.3,2)*3)+(VALORPRIMERSOLAR*Math.pow(1.3,3)*3)+
                (VALORPRIMERSOLAR*Math.pow(1.3,4)*3)+(VALORPRIMERSOLAR*Math.pow(1.3,5)*3)+(VALORPRIMERSOLAR*Math.pow(1.3,6)*3)+(VALORPRIMERSOLAR*Math.pow(1.3,7)*2))/26);
    }

    public void partida() {
        String comando = " ";
        String[] splitcom;
        while (!comando.equals("acabar partida") && !hayganador) {
            comando = consola.consolaleer("");
            splitcom = comando.split(" ");

            Jugador jugadorturno = arrayJugadores.get(turno);

            try {
                switch (splitcom[0]) {
                    case "help": //este comando imprime una lista de los comandos disponibles
                        help();
                        break;
                    case "jugador": //este comando muestra el jugador que tiene el turno
                        verJugadorturno();
                        break;
                    case "lanzar":
                        if(splitcom[1].equals("dados"))
                            lanzarDados(splitcom);
                        else
                            throw new ComandoDesconocido("Comando desconocido: ", comando);
                        break;
                    case "cambiar"://comando para cambiar el tipo de movimiento
                        if (splitcom.length == 3) {
                            if (splitcom[1].equals("movimiento")) {
                                cambiarMovimiento(splitcom[2]);
                            } else {
                                throw new ComandoDesconocido("Comando desconocido: ", comando);
                            }
                        } else {
                            throw new ComandoDesconocido("Comando desconocido: ", comando);
                        }
                        break;
                    case "salir":
                        if (splitcom.length == 2) {
                            if (splitcom[1].equals("carcel")) { //comando para pagar la multa y salir de la cárcel
                                salirCarcelPagando();
                            } else {
                                throw new ComandoDesconocido("Comando desconocido: ", comando);
                            }
                        } else {
                            throw new ComandoDesconocido("Comando desconocido: ", comando);
                        }
                        break;
                    case "acabar":
                        if (splitcom.length == 2) {
                            switch (splitcom[1]) {
                                case "turno":
                                    acabarTurno();
                                    break;
                                case "partida":
                                    acabarPartida();
                                    break;
                                default:
                                    throw new ComandoDesconocido("Comando desconocido: ", comando);
                            }
                        } else {
                            throw new ComandoDesconocido("Comando desconocido: ", comando);
                        }
                        break;
                    case "ver":
                        if (splitcom[1].equals("tablero")) { //comando para mostrar el tablero por pantalla
                            verTablero();
                        } else {
                            throw new ComandoDesconocido("Comando desconocido: ", comando);
                        }
                        break;
                    case "listar":
                        switch (splitcom.length) {
                            case 2:
                                switch (splitcom[1]) {
                                    case "jugadores" -> listarJugadores();
                                    case "avatares" -> listarAvatares();
                                    case "enventa" -> listarEnventa();
                                    case "edificios" -> listarEdificios();
                                    case "tratos" -> listarTratos();
                                    default -> throw new ComandoDesconocido("Comando desconocido: ", comando);
                                }
                                break;
                            case 3:
                                listarEdificiosGrupo(splitcom[2]);
                                break;
                            default:
                                throw new ComandoDesconocido("Comando desconocido: ", comando);
                        }
                        break;
                    case "describir":
                        switch (splitcom.length) {
                            case 2:
                                describircasilla(splitcom[1]);
                                break;
                            case 3:
                                switch (splitcom[1]) {
                                    case "avatar":
                                        describiravatar(splitcom[2].charAt(0));
                                        break;
                                    case "jugador":
                                        describirjugador(splitcom[2]);
                                        break;
                                    default:
                                        throw new ComandoDesconocido("Comando desconocido: ", comando);
                                }
                                break;
                            default:
                                throw new ComandoDesconocido("Comando desconocido: ", comando);
                        }
                        break;
                    case "comprar": //comando para comprar la casilla en la que se encuentra el jugador
                        if (splitcom.length == 2) {
                            comprar(splitcom[1]);
                        } else {
                            throw new ComandoDesconocido("Comando desconocido: ", comando);
                        }
                        break;
                    case "edificar":
                        if (splitcom.length == 2) {
                            edificar(splitcom[1]);
                        } else {
                            throw new ComandoDesconocido("Comando desconocido: ", comando);
                        }
                        break;
                    case "estadisticas":
                        switch (splitcom.length) {
                            case 1:
                                estadisticas();
                                break;
                            case 2:
                                estadisticas(splitcom[1]);
                                break;
                            default:
                                throw new ComandoDesconocido("Comando desconocido: ", comando);
                        }
                        break;
                    case "hipotecar":
                        if (splitcom.length == 2) {
                            hipotecar(splitcom[1]);
                        } else {
                            throw new ComandoDesconocido("Comando desconocido: ", comando);
                        }
                        break;
                    case "deshipotecar":
                        if (splitcom.length == 2) {
                            deshipotecar(splitcom[1]);
                        } else {
                            throw new ComandoDesconocido("Comando desconocido: ", comando);
                        }
                        break;
                    case "vender":
                        if (splitcom.length == 4) {
                            vender(splitcom[1], splitcom[2], Integer.parseInt(splitcom[3]));
                        } else {
                            throw new ComandoDesconocido("Comando desconocido: ", comando);
                        }
                        break;
                    case "bancarrota":
                        switch (splitcom.length) {
                            case 1:
                                bancarrota(arrayJugadores.get(turno), banca);
                                break;
                            case 2:
                                kill(splitcom[1]);
                                break;
                        }
                        break;
                    case "entrenar":
                        entrenar();
                        break;
                    case "pitufo":
                        pitufo();
                        break;
                    case "simpson":
                        simpson();
                        break;
                    case "vendimia":
                        vendimia();
                        break;
                    case "mis":
                        if(splitcom.length == 3) {
                            if (splitcom[1].equals("profes")) {
                                if (splitcom[2].equals("favoritos")) {
                                    showart();
                                } else {
                                    throw new ComandoDesconocido("Comando desconocido: ", comando);
                                }
                            } else {
                                throw new ComandoDesconocido("Comando desconocido: ", comando);
                            }
                        }else{
                            throw new ComandoDesconocido("Comando desconocido: ", comando);
                        }

                        break;
                    case "trato":
                        proponerTrato(comando);
                        break;
                    case "avanzar":
                        if (avanzar()) {
                            return;
                        }
                        break;
                    case "eliminar":
                        if(splitcom.length == 3) {
                            if (splitcom[1].equals("trato")) {
                                eliminarTrato(splitcom);
                            }
                        }else{
                            throw new ComandoDesconocido("Comando desconocido: ", comando);
                        }
                        break;
                    case "aceptar":
                        if(splitcom.length == 3) {
                            if (splitcom[1].equals("trato")) {
                                aceptarTrato(splitcom);
                            }
                        }else{
                            throw new ComandoDesconocido("Comando desconocido: ", comando);
                        }
                        break;
                    default:
                        throw new ComandoDesconocido("Comando desconocido: ", comando);
                }
            }catch (AccionIlegal | TipoCasillaInvalido | TipoEdificioInvalido error){
                consola.consolaimprimir(ANSICodes.ANSI_RED + "ERROR -> " + error.getMessage() + ANSICodes.ANSI_RESET);
            }catch (SinPermisoEdificar error){
                consola.consolaimprimir(ANSICodes.ANSI_RED + "ERROR -> " + error.getMessage() + ANSICodes.ANSI_RESET + "\n" +
                        ((Solar)error.getCasilla()).toStringCondiciones(jugadorturno, arrayJugadores.indexOf(jugadorturno), ((Solar)error.getCasilla()).getGrupo()));
            }catch (SinPermisoSolar | VenderEdificada error){
                consola.consolaimprimir(ANSICodes.ANSI_RED + "ERROR -> " + error.getMessage() + ANSICodes.ANSI_RESET + "\n" + ((Solar)error.getCasilla()).toStringEdificios());
            }catch (EdificarHipotecada error) {
                consola.consolaimprimir(ANSICodes.ANSI_RED + "ERROR -> " + error.getMessage() + "Usa el comando \"deshipotecar \""+ error.getCasilla().getNombre() + "si quieres edificar en esta Casilla" + ANSICodes.ANSI_RESET);
            }catch(NoComprada error) {
                consola.consolaimprimir(ANSICodes.ANSI_RED + "ERROR -> " + error.getMessage() + "La casilla pertenece a " +((Propiedad)error.getCasilla()).getDuenho().getNombre() + ANSICodes.ANSI_RESET);
            }catch(PosicionIncorrecta error){
                consola.consolaimprimir(ANSICodes.ANSI_RED + "ERROR -> " + error.getMessage() + "Deberias estar en " + arraycasillas[error.getPosicioncorrecta()].getNombre() + ANSICodes.ANSI_RESET);
            }catch (FortunaInsuficiente error){
                consola.consolaimprimir(ANSICodes.ANSI_RED + "ERROR -> " + error.getMessage() + "\nDinero actual: " + error.getJugador().getFortuna() + "€\nDinero necesario: " + error.getPago() +"€" + ANSICodes.ANSI_RESET);
            }catch (InexAva error){
                consola.consolaimprimir(ANSICodes.ANSI_RED + "ERROR -> " + error.getMessage() + ANSICodes.ANSI_RESET);
                listarAvatares();
            }catch (InexCas | InexGrup error) {
                consola.consolaimprimir(ANSICodes.ANSI_RED + "ERROR -> " + error.getMessage() + ANSICodes.ANSI_RESET);
                verTablero();
            }catch (InexJug error){
                consola.consolaimprimir(ANSICodes.ANSI_RED + "ERROR -> " + error.getMessage() + ANSICodes.ANSI_RESET);
                listarJugadores();
            }catch (SinPermisoGrupo error){
                consola.consolaimprimir(ANSICodes.ANSI_RED + "ERROR -> " + error.getMessage() + ANSICodes.ANSI_RESET + "\n" + error.getGrupo().toStringEdificios());
            } catch (ComandoDesconocido error) {
                consola.consolaimprimir(ANSICodes.ANSI_RED + "ERROR -> " + error.getMessage() + error.getComando()  + ANSICodes.ANSI_RESET);
                help();
            } catch (InexTrat error) {
                consola.consolaimprimir(ANSICodes.ANSI_RED + "ERROR -> " + error.getMessage() + ANSICodes.ANSI_RESET);
                listarTratos();
            } catch (NoPertenece error) {
                consola.consolaimprimir(ANSICodes.ANSI_RED + "ERROR -> " + error.getMessage() + ANSICodes.ANSI_RESET + "Solo " + error.getTrato().getAcepta().getNombre() + " puede");
            }
        }
    }


    //METODOS TABLERO
    public Boolean avanzar() throws AccionIlegal {
        if(arrayJugadores.get(turno).getAvatar() instanceof Pelota){
            if (arrayJugadores.get(turno).getAvatar().getTipomov()){
                if (!(((Pelota) arrayJugadores.get(turno).getAvatar()).getHeLlegado())){
                    return true;
                }else{throw new AccionIlegal("Ya has acabado de mover, ni avanzar ni hostias");}
            }
            else{throw new AccionIlegal("No tienes el movimiento en avanzado, ni avanzar ni hostias");}
        }
        else{throw new AccionIlegal("No eres una pelota, ni avanzar ni hostias");}
    }
    public void salvacion(Jugador muerto, Jugador beneficiado) throws AccionIlegal, NoComprada, InexCas, TipoCasillaInvalido, VenderEdificada, ComandoDesconocido {
        consola.consolaimprimir("Has decidico aferrarte a la vida\nEstas son las propiedades que puedes hipotecar:");
        consola.consolaimprimir(muerto.propiedadesJugador());

        String comando;
        String[] splitcom;

        boolean flag = false;
        while(muerto.sepuedehipotecar()) { //este bucle dura mientras no se hayan creados los jugadores correctamente e iniciado la partida
            comando = consola.consolaleer("");
            splitcom = comando.split("");


            switch (consola.consolaleer("")) {
                case "vender":
                    if (splitcom.length == 4) {
                        vender(splitcom[1], splitcom[2], Integer.parseInt(splitcom[3]));
                    } else {
                        throw new ComandoDesconocido("Comando desconocido: ", comando);
                    }
                    break;
                case "hipotecar":
                    if (splitcom.length == 2) {
                        hipotecar(splitcom[1]);
                    } else {
                        throw new ComandoDesconocido("Comando desconocido: ", comando);
                    }
                    break;
                default:
                    throw new ComandoDesconocido("Comando desconocido: ", comando);
            }


            if(muerto.getFortuna() > 0){
                consola.consolaimprimir("Enhorabuena! Has conseguido pagar la deuda");
                flag = true;
                break;
            }
        }

        if (!flag){
            consola.consolaimprimir("Lo siento, te has quedado sin honor, sin propiedades que puedas hipotecar" +
                    "y no has conseguido pagar la deuda");
            bancarrota(muerto, beneficiado);
        }
    }
    public void menubancarrota(Jugador muerto, Jugador beneficiado) throws AccionIlegal, NoComprada, VenderEdificada, TipoCasillaInvalido, InexCas, ComandoDesconocido {
        String opcion;
        do{
            opcion = consola.consolaleer("No tienes el dinero suficiente para el pago\n" +
                    "Decide si \"morir con honor\" (abandonar la partida) o \"nunca rendirte\" (hipotecar alguna propiedad)");

        }while(!opcion.equals("morir con honor") && !opcion.equals("nunca rendirte"));

        switch(opcion){
            case "morir con honor" -> bancarrota(muerto, beneficiado);
            case "nunca rendirte" -> salvacion(muerto, beneficiado);
        }
    }
    public void pasarSalida(Jugador jugadorturno){
        jugadorturno.Aumento(mediaglobal());
        jugadorturno.setDineroSalida(arrayJugadores.get(turno).getDineroSalida() + mediaglobal());
        jugadorturno.setVueltas(arrayJugadores.get(turno).getVueltas() + 1);
        jugadorturno.setNumVueltas(arrayJugadores.get(turno).getNumVueltas()+1);
        consola.consolaimprimir("Enhorabuena, has pasado por la salida y cobrado " + String.format("%.2f", mediaglobal()) + "$!!!");
        if (AumentoxVueltas() == 1) {
            aumentosXvuelta++;
            consola.consolaimprimir("Como todos los jugadores han pasado tres veces por  la salida, el precio de los solares en venta se incrementará. Inflación, baby.");
        }
    }
    public void pasarSalidaHaciaAtras(Jugador jugadorturno){
        jugadorturno.Aumento(mediaglobal());
        jugadorturno.setDineroSalida(jugadorturno.getDineroSalida() - mediaglobal());
        jugadorturno.setVueltas(jugadorturno.getVueltas() - 1);
        jugadorturno.setNumVueltas(jugadorturno.getNumVueltas() - 1);
        consola.consolaimprimir("Menudo pringado, has retrocedido por la salida y pagado " + String.format("%.2f", mediaglobal()) + "€!!!  :(");
        //aquí PODRÍAMOS implementar una disminución de los precios cuando sea necesario
    }
    public int AumentoxVueltas() {
        for (Jugador arrayJugadore : this.arrayJugadores) {
            if (arrayJugadore.getVueltas() < 3) {
                return 0;
            }
        }
        for(Grupo g : arrayGrupos)
            g.setValor(g.getValor()*1.05);

        for (Jugador jugador : arrayJugadores){
            jugador.setVueltas(0);
        }

        return 1;
    }
    public String NumGrupToNombre (int numGrupo){
        String resultado = "";

        switch(numGrupo){
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

        }
        return resultado;
    }
    public String toStringStats(int sumadados){
        String nombreCabeza1;
        StringBuilder nombreCabeza2= new StringBuilder();
        String nombreVueltas1;
        StringBuilder nombreVueltas2= new StringBuilder();
        String nombreDados1;
        StringBuilder nombreDados2= new StringBuilder();
        String casillaFrecuentada1;
        StringBuilder casillaFrecuentada2= new StringBuilder();
        StringBuilder casillaRentable1= new StringBuilder();
        String casillaRentable2;
        String jugadorEnCabeza="";
        String jugadorMasVueltas="";
        String jugadorMasDados="";
        String casillaMasFrecuentada="";
        String casillaMasRentable;
        String grupoMasRentable1;
        StringBuilder grupoMasRentable2= new StringBuilder();
        String resultado="";

        double valor1,valor2=0,rentable1=0,rentable2,masRentableGrupo=MINIMO;
        int vueltas1,vueltas2=0,dados1,dados2=0,frecuencia1,frecuencia2=0,
                n=0,m=0,nh=0,l=0,o=0,p=0;

        double[] valor;
        valor=new double[arrayJugadores.size()];

        double[] rentableGrupo;
        rentableGrupo=new double[9];

        int[] vueltas;
        vueltas=new int[arrayJugadores.size()];

        int[] dados;
        dados=new int[arrayJugadores.size()];

        int[] frecuencia;
        frecuencia=new int[arraycasillas.length];


        //Inicializamos el array a un valor imposible para poder comparar
        for(int i=0;i<9;i++){
            rentableGrupo[i]=MINIMO;
        }

        //Cálculo del valor, nº de vueltas, veces que se ha lanzado los dados
        //y casillas y grupos más rentables, se hace enteramente en el mismo bucle por
        //depender del número de jugadores
        for (Jugador jugador : arrayJugadores) {
            vueltas1=jugador.getNumVueltas();
            dados1=jugador.getDadosLanzados();
            valor1 = jugador.getFortuna();
            for (Propiedad prop : jugador.getArrayPropiedades().values()) {
                valor1 += prop.valor();
                rentable2= prop.getAlquilerAcumulado()- prop.valor();
                casillaRentable2 = prop.getNombre();
                if(rentable2>=rentable1||m==0){
                    rentable1=rentable2;
                    casillaRentable1 = new StringBuilder(casillaRentable2);
                    m++;
                }
                if(prop instanceof Solar sol) {
                    if (rentableGrupo[sol.getGrupo().getNumgrupo()] != MINIMO) {
                        rentableGrupo[sol.getGrupo().getNumgrupo()] += rentable2;
                    } else {
                        rentableGrupo[sol.getGrupo().getNumgrupo()] = rentable2;
                    }
                }
            }
            for (Edificio edificio : jugador.getArrayEdificios()) {
                valor1 += edificio.getValor();
            }

            valor[n] = valor1;
            vueltas[n] = vueltas1;
            dados[n]=dados1;
            nombreCabeza1 = jugador.getNombre();
            nombreVueltas1=jugador.getNombre();
            nombreDados1=jugador.getNombre();

            if (valor1 >= valor2) {
                valor2 = valor1;
                nombreCabeza2 = new StringBuilder(nombreCabeza1);
            }

            if (vueltas1 >= vueltas2) {
                vueltas2 = vueltas1;
                nombreVueltas2 = new StringBuilder(nombreVueltas1);
            }

            if (dados1 >= dados2) {
                dados2 = dados1;
                nombreDados2 = new StringBuilder(nombreDados1);
            }
            n++;
        }

        //Cálculo de la casilla en la que más gente
        //ha estado
        n=0;
        for(Casilla casilla : arraycasillas){
            frecuencia1 = casilla.getFrecuencia();
            frecuencia[n]=frecuencia1;
            casillaFrecuentada1=casilla.getNombre();

            if (frecuencia1 >= frecuencia2) {
                frecuencia2 = frecuencia1;
                casillaFrecuentada2 = new StringBuilder(casillaFrecuentada1);
            }
            n++;
        }

        n=0;
        m=0;

        nombreCabeza1= nombreCabeza2.toString();
        nombreVueltas1= nombreVueltas2.toString();
        nombreDados1= nombreDados2.toString();

        //Comprobación de si hay empate entre vueltas,
        //patrimonio o dados lanzados, si es así, se
        //dice y se imprimen todos los jugadores que
        //cumplan esa condición
        for(int i=0;i<arrayJugadores.size();i++){
            if(valor[i]==valor2){
                if(!Objects.equals(arrayJugadores.get(i).getNombre(), nombreCabeza1)){
                    n++;
                    nombreCabeza2.append(",").append(arrayJugadores.get(i).getNombre());
                }
            }
            if(vueltas[i]==vueltas2){
                if(!Objects.equals(arrayJugadores.get(i).getNombre(), nombreVueltas1)){
                    m++;
                    nombreVueltas2.append(",").append(arrayJugadores.get(i).getNombre());
                }
            }
            if(dados[i]==dados2){
                if(!Objects.equals(arrayJugadores.get(i).getNombre(), nombreDados1)){
                    nh++;
                    nombreDados2.append(",").append(arrayJugadores.get(i).getNombre());
                }
            }
        }

        //Comprobación de si hay empate entre casillas
        //frecuentadas, si es así, se
        //dice y se imprimen todas las casillas que
        //cumplan esa condición
        casillaFrecuentada1= casillaFrecuentada2.toString();
        for(int i=0;i<arraycasillas.length;i++) {
            if (frecuencia[i] == frecuencia2) {
                if (!Objects.equals(arraycasillas[i].getNombre(), casillaFrecuentada1)) {
                    l++;
                    casillaFrecuentada2.append(",").append(arraycasillas[i].getNombre());
                }
            }
        }

        //Comprobación de si hay empate entre casillas
        //mas rentables, si es así, se
        //dice y se imprimen todas las casillas que
        //cumplan esa condición
        casillaRentable2= casillaRentable1.toString();
        for(Jugador jugador:arrayJugadores){
            for(Propiedad prop :jugador.getArrayPropiedades().values()){
                if(prop.getAlquilerAcumulado()-prop.alquiler(sumadados)==rentable1){
                    if(!Objects.equals(prop.getNombre(), casillaRentable2)){
                        o++;
                        casillaRentable1.append(",").append(prop.getNombre());
                    }
                }
            }
        }

        //Cálculo del grupo más rentable
        for(int i=0;i<9;i++){
            if(rentableGrupo[i]>masRentableGrupo) {
                masRentableGrupo = rentableGrupo[i];
                grupoMasRentable2 = new StringBuilder(NumGrupToNombre(i));
            }
        }

        //Comprobación de si existen más grupos con esa rentabilidad,
        //si es así, se imprimirán todos ellos
        grupoMasRentable1 = grupoMasRentable2.toString();
        for(int i=0;i<9;i++){
            if(rentableGrupo[i]==masRentableGrupo) {
                if(!Objects.equals(grupoMasRentable1, NumGrupToNombre(i))) {
                    p++;
                    grupoMasRentable2.append(", ").append(NumGrupToNombre(i));
                }
            }
        }

        //Cada estadística mencionada tiene una variable que cuenta
        //cuantos elementos hay con el máximo, por lo que se imprime
        //o directamente el elemento o se menciona que hay empate y
        //se procede a imprimir todos con ese máximo
        if(n==0){
            jugadorEnCabeza+="\t "+nombreCabeza2 + " con " + String.format("%.2f",valor2) + " €";
        }else{
            jugadorEnCabeza+="\tHay empate, gente en cabeza:\n" + "\t "+nombreCabeza2 + ". Con " + String.format("%.2f",valor2) + " €";
        }
        if(m==0){
            jugadorMasVueltas+="\t "+nombreVueltas2 + " con " + vueltas2 + " vueltas";
        }else{
            jugadorMasVueltas+="\tHay empate, gente con mas vueltas:\n" + "\t "+nombreVueltas2 + ". Con " + vueltas2 + " vueltas";
        }
        if(nh==0){
            jugadorMasDados+="\t "+nombreDados2 + " con " + dados2 + " lanzamientos";
        }else{
            jugadorMasDados+="\tHay empate, gente con mas dados lanzados:\n" + "\t "+nombreDados2 + ". Con " + dados2 + " lanzamientos";
        }
        if(l==0){
            casillaMasFrecuentada+="\t "+casillaFrecuentada2 + " con " + frecuencia2 + " estancias";
        }else{
            casillaMasFrecuentada+="\tHay empate, casillas mas frecuentadas:\n" + "\t "+casillaFrecuentada2 + ". Con " + frecuencia2 + " estancias";
        }
        //Si no hay casillas compradas es cuando casillaRentable2 es igual a "", lo que significa
        //que esté vacía, y si no hay casillas compradas no tiene sentido hablar de rentabilidad
        if(casillaRentable2.isEmpty()){
            casillaMasRentable="\t No hay casillas compradas";
        }else if(o==0){
            casillaMasRentable="\t "+casillaRentable2 + " con " + String.format("%.2f",rentable1) + " € de rentabilidad";
        }else {
            casillaMasRentable = "\tHay empate, casillas mas rentables:\n" + "\t " + casillaRentable2 + ". Con " + String.format("%.2f",rentable1) + " € de rentabilidad";
        }
        //Si no hay casillas compradas es cuando grupoMasRentable2 es igual a "", lo que significa
        //que esté vacía, y si no hay casillas compradas no tiene sentido hablar de rentabilidad
        if(grupoMasRentable2.isEmpty()){
            grupoMasRentable2 = new StringBuilder("\t No hay grupos con alguna casilla comprada");
        }else if(p==0){
            grupoMasRentable2 = new StringBuilder("\t " + grupoMasRentable2 + " con " + String.format("%.2f", masRentableGrupo) + " € de rentabilidad");
        }else {
            grupoMasRentable2 = new StringBuilder("\tHay empate, grupos mas rentables:\n" + "\t " + grupoMasRentable2 + ". Con " + String.format("%.2f", masRentableGrupo) + " € de rentabilidad");
        }


        resultado+="Casilla mas rentable:" + '\n'+ casillaMasRentable +'\n';
        resultado+="Grupo mas rentable: " + '\n' + grupoMasRentable2 + '\n';
        resultado+="Casilla mas frecuentada: "+ '\n' + casillaMasFrecuentada +'\n';
        resultado+="Jugador con mas vueltas: "+ '\n' + jugadorMasVueltas + '\n';
        resultado+="Jugador que lanzo mas veces los dados: " + '\n' + jugadorMasDados + '\n';
        resultado+="Jugador en cabeza : " + '\n' +  jugadorEnCabeza + '\n';

        return resultado;
    }
    public void suerte_comunidad(int tipo) throws AccionIlegal, NoComprada, VenderEdificada, TipoCasillaInvalido, InexCas, ComandoDesconocido {
        Jugador jugadorturno = arrayJugadores.get(turno);
        Carta[] arrayCartas;
        int indice;

        switch (tipo) {
            case 0:
                arrayCartas = new Carta[10];

                for (int i = 0; i < 10; i++) {
                    arrayCartas[i] = new CartaCajaComunidad(i);
                }

                barajar_cartas(arrayCartas);

                consola.consolaimprimir("Se han barajado las cartas, escoge una poniendo un número del 1 al 10:");
                Scanner leer = new Scanner(System.in);
                try {
                    indice = leer.nextInt() - 1;
                }catch (InputMismatchException error){
                    consola.consolaimprimir(ANSICodes.ANSI_RED + "ERROR -> No has introducido un numero payaso, se ha seleccionado un numero aleatorio" + ANSICodes.ANSI_RESET);
                    Random rnd = new Random();
                    indice = rnd.nextInt(9)+1;
                }
                while (indice < 0 || indice > 9) {
                    consola.consolaimprimir("Escoge una carta poniendo un numero entre 1 y 10");
                    try {
                        indice = leer.nextInt() - 1;
                    }catch (InputMismatchException error){
                        consola.consolaimprimir(ANSICodes.ANSI_RED + "ERROR -> No has introducido un numero payaso, se ha seleccionado un numero aleatorio" + ANSICodes.ANSI_RESET);
                        Random rnd = new Random();
                        indice = rnd.nextInt(9)+1;
                    }
                }

                consola.consolaimprimir(arrayCartas[indice].getEnunciado());
                arrayCartas[indice].accion(this, turno);
                if (jugadorturno.getFortuna() < 0) {
                    menubancarrota(jugadorturno, banca);
                }
                break;
            case 1:
                arrayCartas = new Carta[14];

                for (int i = 0; i < 14; i++) {
                    arrayCartas[i] = new CartaSuerte(i);
                }

                barajar_cartas(arrayCartas);

                consola.consolaimprimir("Se han barajado las cartas, escoge una poniendo un número del 1 al 14:");
                Scanner leer1 = new Scanner(System.in);
                try {
                    indice = leer1.nextInt() - 1;
                }catch (InputMismatchException error){
                    consola.consolaimprimir(ANSICodes.ANSI_RED + "ERROR -> No has introducido un numero payaso, se ha seleccionado un numero aleatorio" + ANSICodes.ANSI_RESET);
                    Random rnd = new Random();
                    indice = rnd.nextInt(9)+1;
                }
                while (indice < 0 || indice > 9) {
                    consola.consolaimprimir("Escoge una carta poniendo un numero entre 1 y 10");
                    try {
                        indice = leer1.nextInt() - 1;
                    }catch (InputMismatchException error){
                        consola.consolaimprimir(ANSICodes.ANSI_RED + "ERROR -> No has introducido un numero payaso, se ha seleccionado un numero aleatorio" + ANSICodes.ANSI_RESET);
                        Random rnd = new Random();
                        indice = rnd.nextInt(9)+1;
                    }
                }
                consola.consolaimprimir(arrayCartas[indice].getEnunciado());
                arrayCartas[indice].accion(this, turno);
                if (jugadorturno.getFortuna() < 0) {
                    bancarrota(jugadorturno, banca);
                }
                break;
        }
    }
    public void barajar_cartas(Carta[] arrayCartas){
        List<Carta> arrayCartas1= Arrays.asList(arrayCartas);
        Collections.shuffle(arrayCartas1);
        arrayCartas1.toArray(arrayCartas);
    }
    public boolean esNumericoDouble(String s){
        if (s == null || s.isEmpty()) {
            return false;
        }

        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }

    }
    public boolean esNumericoInt(String s){

        boolean resultado;

        try {
            Integer.parseInt(s);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }

        return resultado;
    }
    public void entrarCarcel(){
        Jugador jugadorturno = arrayJugadores.get(turno);
        Avatar avatarturno = jugadorturno.getAvatar();
        Casilla casillactual = arraycasillas[jugadorturno.getAvatar().getPosicion()];

        jugadorturno.setCarcel(3);
        casillactual.setArrayBoolean(turno, false);
        avatarturno.setPosicion(10);
        arraycasillas[10].setFrecuencia(arraycasillas[10].getFrecuencia() + 1);

        jugadorturno.setVecesCarcel(arrayJugadores.get(turno).getVecesCarcel() + 1);
        arraycasillas[10].setArrayBoolean(turno, true);
        dadosporlanzar = -1;
        contadordobles = 0;
        consola.consolaimprimir(arrayJugadores.get(turno).getNombre() + " ha entrado en la carcel\n");
        if(avatarturno instanceof  Pelota){
            ((Pelota) avatarturno).setHeLlegado(true);
        }

    }


    //COMANDOS
    @Override
    public void help(){

        consola.consolaimprimir("""
                /////////////////////GUIA COMANDOS//////////////////////////////////////////////////////////////////////////////////////////////////////////////
                "jugador": Muestra el jugador que tiene el turno
                "lanzar dados": Lanza los dados
                "ver tablero": muestra el tablero por pantalla
                "salir carcel": Paga la multa y sale de la cárcel
                "listar jugadores": Muestra los jugadores en partida
                "listar avatares": Muestra los avatares de los jugadores en partida
                "listar enventa": Muestra las casillas en venta
                "describir [nombrecasilla]": Describe la casilla nombrada
                "describir jugador [nombrejugador]": Describe el jugador nombrado
                "describir avatar [nombreavatar": Describe el avatar nombrado
                "comprar [nombrepropiedad]": Compra (si es posible) la propiedad que se indique
                "vender [tipoedificio] [solar] [numerodeedificios]": vende el numero de edificios del tipo indicado que se encuentren en la casilla indicada
                "hipotecar [nombresolar]": hipoteca (si es posible) el solar indicado
                "deshipotecar [nombresolar]": deshipoteca (si es posible) el solar indicado
                "acabar turno": Termina su turno
                "acabar partida": Termina la partida
                "cambiar movimiento [avanzado/basico]": cambia tu tipo de movimiento a avanzado o a básico
                "trato [nombrejugador]: [x] y [z] por [a] y [b]": le propone al jugador indicado un intercambio de dinero, propiedades o turnos sin alquiler
                "bancarrota": te declaras en bancarrota y abandonas la partida
                /////////////////////GUIA COMANDOS////////////////////////////////////////////////////////////////////////////////////////////////////////////////""");
    }
    @Override
    public void listarTratos(){
        if(arrayTratos.isEmpty()){
            consola.consolaimprimir("No hay ningún trato propuesto en este momento");
            return;
        }
        consola.consolaimprimir("Tratos propuestos:");

        for(Trato trato: arrayTratos.values()){
            consola.consolaimprimir(trato.toString());
        }
    }
    @Override
    public void eliminarTrato(String[] splitcom) throws NoPertenece, InexTrat {
        int ID;
        if (esNumericoInt(splitcom[2])){
            ID=Integer.parseInt(splitcom[2]);
        }
        else{
            throw new InexTrat("ID de trato inválido", splitcom[2]);
        }
        if(!arrayTratos.containsKey(ID)){
            throw new InexTrat("El trato "+ID+" no existe, no puedes eliminarlo", splitcom[2]);
        }
        Trato trato= arrayTratos.get(ID);
        trato.eliminarTrato(arrayTratos,ID,arrayJugadores.get(turno));
    }
    @Override
    public void aceptarTrato(String[] splitcom) throws InexTrat, FortunaInsuficiente, NoPertenece {
        int ID;
        if (esNumericoInt(splitcom[2])){
            ID=Integer.parseInt(splitcom[2]);
        }
        else{
            throw new InexTrat("ID de trato inválido", splitcom[2]);
        }
        if(!arrayTratos.containsKey(ID)){
            throw new InexTrat("El trato "+ID+" no existe, no puedes aceptarlo", splitcom[2]);
        }
        Trato trato= arrayTratos.get(ID);
        trato.aceptarTrato(arrayTratos,ID,arrayJugadores,turno);
    }
    @Override
    public void proponerTrato(String comando) throws ComandoDesconocido, InexJug, AccionIlegal {
        try {
            String[] aux1 = comando.split(":");
            String[] aux2 = aux1[0].split(" ");
            String[] aux3;
            Jugador jugadorAcepta = null, jugadorPropone = arrayJugadores.get(turno);
            double extra = 0;
            ArrayList<Propiedad> propiedadesOfrece = new ArrayList<>();
            ArrayList<Propiedad> propiedadesPide = new ArrayList<>();
            ArrayList<Integer> turnosSinAlquilerOfrece = new ArrayList<>();
            ArrayList<Integer> turnosSinAlquilerPide = new ArrayList<>();
            ArrayList<Propiedad> propiedadesSinAlquilerOfrece = new ArrayList<>();
            ArrayList<Propiedad> propiedadesSinAlquilerPide = new ArrayList<>();
            int ID;

            Random rnd = new Random();
            do {
                ID = rnd.nextInt(256);
            } while (arrayTratos.containsKey(ID));

            aux2[1] = (String) aux2[1].subSequence(0, aux2[1].length());
            for (Jugador jugador : arrayJugadores) {
                if (jugador.getEe() == 'p') {
                    if (jugador.getNombre().equals(ANSICodes.ANSI_BLUE + aux2[1] + ANSICodes.ANSI_RESET)) {
                        jugadorAcepta = jugador;
                        break;
                    }
                } else if (jugador.getEe() == 's') {
                    if (jugador.getNombre().equals(ANSICodes.ANSI_YELLOW + aux2[1] + ANSICodes.ANSI_RESET)) {
                        jugadorAcepta = jugador;
                        break;
                    }
                } else if (jugador.getEe() == 'u') {
                    if (jugador.getNombre().equals(ANSICodes.ANSI_PURPLE + aux2[1] + ANSICodes.ANSI_RESET)) {
                        jugadorAcepta = jugador;
                        break;
                    }
                } else {
                    if (jugador.getNombre().equals(aux2[1])) {
                        jugadorAcepta = jugador;
                        break;
                    }
                }
            }

            if (jugadorAcepta == null) {
                throw new InexJug("Pero se puede saber quién cojones es \"" + aux2[1] + "\"? Payaso.", aux2[1]);
            } else if (jugadorAcepta == jugadorPropone) {
                throw new AccionIlegal("No puedes proponerte un trato a ti mismo, pailán");
            }
            aux2 = aux1[1].split(" por ");
            aux1 = aux2[0].split(" y ");
            for (String S : aux1) {
                S = S.trim();
                if (esNumericoDouble(S)) {
                    if (extra == 0) {
                        extra = -Double.parseDouble(S);
                    } else {
                        throw new AccionIlegal("Ya has ofrecido dinero, pailán");
                    }
                } else if (S.charAt(0) == '(') {
                    S = S.substring(1, S.length() - 1);
                    aux3 = S.split(",");
                    if (jugadorPropone.getArrayPropiedades().containsKey(aux3[0])) {
                        if (!esNumericoInt(aux3[1])) {
                            throw new AccionIlegal("Valor inválido para el número de turnos sin pagar alquiler");
                        }
                        propiedadesSinAlquilerOfrece.add(jugadorPropone.getArrayPropiedades().get(aux3[0]));
                        turnosSinAlquilerOfrece.add(Integer.parseInt(aux3[1]));
                    } else {
                        throw new AccionIlegal("No puedes ofrecer no pagar en una propiedad que no tienes");
                    }
                } else {
                    if (jugadorPropone.getArrayPropiedades().containsKey(S)) {
                        propiedadesOfrece.add(jugadorPropone.getArrayPropiedades().get(S));
                    } else {
                        throw new AccionIlegal("No puedes ofrecer una propiedad que no tienes, payaso");
                    }
                }
                arrayTratos.put(ID, new Trato(jugadorPropone, jugadorAcepta, extra, propiedadesOfrece, propiedadesSinAlquilerOfrece, turnosSinAlquilerOfrece,
                        propiedadesPide, propiedadesSinAlquilerPide, turnosSinAlquilerPide, ID));
            }
            aux1 = aux2[1].split(" y ");
            for (String S : aux1) {
                S = S.trim();
                if (esNumericoDouble(S)) {
                    if (extra == 0) {
                        extra = Double.parseDouble(S);
                    } else {
                        throw new AccionIlegal("Ya has ofrecido dinero, pailán");
                    }
                } else if (S.charAt(0) == '(') {
                    S = S.substring(1, S.length() - 1);
                    aux3 = S.split(",");
                    if (jugadorAcepta.getArrayPropiedades().containsKey(aux3[0])) {
                        if (!esNumericoInt(aux3[1])) {
                            throw new AccionIlegal("Valor inválido para el número de turnos sin pagar alquiler");
                        }
                        propiedadesSinAlquilerPide.add(jugadorAcepta.getArrayPropiedades().get(aux3[0]));
                        turnosSinAlquilerPide.add(Integer.parseInt(aux3[1]));
                    } else {
                        throw new AccionIlegal("No puedes pedir no pagar en una propiedad que no tienen");
                    }


                } else {
                    if (jugadorAcepta.getArrayPropiedades().containsKey(S)) {
                        propiedadesPide.add(jugadorAcepta.getArrayPropiedades().get(S));
                    } else {
                        throw new AccionIlegal("No puedes pedir una propiedad que no tienen, payaso");
                    }

                }
            }
            arrayTratos.put(ID, new Trato(jugadorPropone, jugadorAcepta, extra, propiedadesOfrece, propiedadesSinAlquilerOfrece, turnosSinAlquilerOfrece,
                    propiedadesPide, propiedadesSinAlquilerPide, turnosSinAlquilerPide, ID));
            consola.consolaimprimir("Trato propuesto correctamente");

        }catch (IndexOutOfBoundsException error){
            throw new ComandoDesconocido("Comando desconocido", comando);
        }

    }
    @Override
    public void lanzarDados(String[] splitcom) throws AccionIlegal, NoComprada, VenderEdificada, TipoCasillaInvalido, InexCas, ComandoDesconocido {
        Avatar av = arrayJugadores.get(turno).getAvatar();
        if(av.getTipomov()){
            av.moverEnAvanzado(this, splitcom);
        }
        else{
            av.moverEnBasico(this, splitcom);
        }
    }
    @Override
    public void describircasilla(String nombreCasilla) throws InexCas {
        for (Casilla casilla : arraycasillas) {
            if (nombreCasilla.equals(casilla.getNombre())) {
                consola.consolaimprimir(casilla.toString());
                if (casilla instanceof Carcel car)
                    consola.consolaimprimir(car.Encarcelados(arrayJugadores));
                return;
            }
        }
        throw new InexCas("La casilla " + nombreCasilla + " no existe", nombreCasilla);
    }
    @Override
    public void describirjugador(String nombreJugador) throws InexJug{
        for (Jugador jugador : arrayJugadores) {
            if (jugador.getEe() == 'p') {
                if (jugador.getNombre().equals(ANSICodes.ANSI_BLUE + nombreJugador + ANSICodes.ANSI_RESET)) {
                    consola.consolaimprimir(jugador.toStringJug());
                    return;
                }
            }else if(jugador.getEe() == 's') {
                if (jugador.getNombre().equals(ANSICodes.ANSI_YELLOW + nombreJugador + ANSICodes.ANSI_RESET)) {
                    consola.consolaimprimir(jugador.toStringJug());
                    return;
                }
            }else if(jugador.getEe() == 'u') {
                if (jugador.getNombre().equals(ANSICodes.ANSI_PURPLE+ nombreJugador + ANSICodes.ANSI_RESET)) {
                    consola.consolaimprimir(jugador.toStringJug());
                    return;
                }
            }else {
                if (jugador.getNombre().equals(nombreJugador)) {
                    consola.consolaimprimir(jugador.toStringJug());
                    return;
                }
            }
        }
        throw new InexJug("El jugador " + nombreJugador + " no existe", nombreJugador);
    }
    @Override
    public void describiravatar(char letraAvatar) throws InexAva { //perdon
        for (Jugador jugador : arrayJugadores) {
            if (jugador.getAvatar().getLetra() == letraAvatar) {
                jugador.toStringJug(arraycasillas);
                return;
            }
        }
        String str = "" + letraAvatar;
        throw new InexAva("El jugador con avatar: " + letraAvatar+ " no existe", str);
    }
    @Override
    public void salirCarcelPagando() throws FortunaInsuficiente, PosicionIncorrecta {
        Jugador jugadorturno = arrayJugadores.get(turno);

        if (jugadorturno.getCarcel() != 0) {
            if (jugadorturno.getFortuna() > mediaglobal()/2) {
                jugadorturno.setCarcel(0);
                jugadorturno.Disminucion(mediaglobal()/2);
                jugadorturno.setDineroImpuestos(jugadorturno.getDineroImpuestos() + mediaglobal()/2);
                consola.consolaimprimir(jugadorturno.getNombre() + " paga 500000€ y sale de la cárcel. Puede lanzar los dados");
            } else {
                throw new FortunaInsuficiente("Fortuna insuficiente", jugadorturno, mediaglobal()/2);
            }
        } else {
            throw new PosicionIncorrecta("No estas en la carcel", jugadorturno,10);
        }
    }
    @Override
    public void cambiarMovimiento(String tipoMovimiento) throws AccionIlegal, ComandoDesconocido {
        Avatar avatarturno = arrayJugadores.get(turno).getAvatar();
            if(movimientosRealizados>0){//la variable "compras realizadas" solo cambia su valor para el coche, por tanto, esta comprobación solo impide que el coche cambie su movimiento a mitad de turno
                throw new AccionIlegal("Ya te has movido este turno bro, no intentes trampear");

            }
            if (tipoMovimiento.equals("avanzado")) {
                if (avatarturno.getTipomov()) {
                    throw new AccionIlegal("Ya estás en modo avanzado, espabila");
                }
                else if((avatarturno.getTipoavatar().equals("Sombrero"))||(avatarturno.getTipoavatar().equals("Esfinge"))){
                    throw new AccionIlegal("Lo siento, este tipo de avatar no dispone de ningún movimiento especial");
                }else {
                    avatarturno.setTipomov(true);
                    consola.consolaimprimir("Movimiento cambiado correctamente");
                }
            } else if (tipoMovimiento.equals("basico")) {
                if (!avatarturno.getTipomov()) {
                    throw new AccionIlegal("Ya estás en modo básico, espabila");
                }
                else {
                    avatarturno.setTipomov(false);
                    consola.consolaimprimir("Movimiento cambiado correctamente");
                }
            }
            else {
                throw new ComandoDesconocido("Comando desconocido: ", tipoMovimiento);
            }
    }
    @Override
    public void acabarTurno() throws AccionIlegal{
        Avatar avatarturno = arrayJugadores.get(turno).getAvatar();

        if ((dadosporlanzar < 0) || ((avatarturno instanceof Coche c) && ((c.getTurnosPorEsperar() > 0)||(movimientosRealizados>4)))){
            if (avatarturno instanceof Coche c){
                c.setSumTurnosPorEsperar(-1);}
            setTurno(getTurno() + 1);
            dadosporlanzar = 0;
            contadordobles = 0;
            comprasRealizadas = 0;
            movimientosRealizados = 0;
            if(avatarturno instanceof Pelota){((Pelota) avatarturno).setHeLlegado(false);}
            consola.consolaimprimir("El turno ahora lo tiene " + arrayJugadores.get(turno).getNombre());
        }else{
            throw new AccionIlegal("Se deben lanzar los dados antes de cambiar de turno");
        }
    }
    @Override
    public void acabarPartida(){
        hayganador = true;
        consola.consolaimprimir("Acabando partida...");
    }
    @Override
    public void verJugadorturno(){
        Jugador jugadorturno = arrayJugadores.get(turno);
        consola.consolaimprimir(jugadorturno.toStringCom());
    }
    @Override
    public void verTablero(){
        consola.consolaimprimir(this.toString());
    }
    @Override
    public void listarAvatares() {
        for (Jugador jugador : arrayJugadores) {
            consola.consolaimprimir(jugador.toStringJug(arraycasillas) + "\n");
        }
    }
    @Override
    public void listarJugadores(){
        for (Jugador jugador : arrayJugadores) {
            consola.consolaimprimir(jugador.toStringJug() + "\n");
        }
    }
    @Override
    public void listarEnventa() throws InexGrup {
        for (Casilla casilla : arraycasillas){
            if (casilla instanceof Propiedad prop){
                if (prop.getDuenho().getNombre().equals("Banca")) {
                    consola.consolaimprimir(prop.toStringEnventa());
                }
            }
        }
    }
    @Override
    public void listarEdificios() throws InexGrup {
        for (Grupo g : arrayGrupos){
            consola.consolaimprimir(g.toStringEdificiosGrupo());
        }
    }
    @Override
    public void listarEdificiosGrupo(String colorgrupo) throws InexGrup{
        boolean flag = false;
        for(Grupo g : arrayGrupos){
            if(Grupo.NumGruptoNombre(g.getNumgrupo()).equals(colorgrupo)){
                consola.consolaimprimir(g.toStringEdificios());
                flag = true;
            }
        }
        if (!flag){
            throw new InexGrup("El nombre del grupo introducido no existe", colorgrupo);
        }
    }
    @Override
    public void comprar(String nombreCasilla) throws FortunaInsuficiente, AccionIlegal, InexCas {

        Jugador jugadorturno = arrayJugadores.get(turno);
        Casilla casillactual = arraycasillas[jugadorturno.getAvatar().getPosicion()];

        if ((jugadorturno.getAvatar() instanceof Coche) && (comprasRealizadas>0) &&(jugadorturno.getAvatar().getTipomov())){
            consola.consolaimprimir("Ya has comprado este turno, pillabán");
            return;
        }

        boolean flag = false;

        for(Casilla cas : arraycasillas){
            if(cas.getNombre().equals(nombreCasilla)){
                if (nombreCasilla.equals(casillactual.getNombre())) {
                    if (casillactual instanceof Propiedad prop) {
                        if (prop.perteneceajugador(banca)) {
                            if (jugadorturno.getFortuna() >= prop.valor()) {
                                jugadorturno.Disminucion(prop.valor());
                                jugadorturno.setDineroInvertido(jugadorturno.getDineroInvertido()+prop.valor());
                                prop.comprar(jugadorturno);
                                jugadorturno.anhadirSolar(prop);
                                ///////////////////////////////////////////////////////////////////OJO comprasRealizadas+=1;
                                comprasRealizadas+=1;
                                consola.consolaimprimir(jugadorturno.getNombre() + " ha comprado " + prop.getNombre() + ". Esta costó " +
                                        String.format("%.2f",prop.valor()) + "€ y a " + jugadorturno.getNombre() + " le quedan " +
                                        String.format("%.2f",jugadorturno.getFortuna())+"€");
                            } else {
                                throw new FortunaInsuficiente("No hay suficiente dinero para comprar esta casilla", jugadorturno, prop.valor());
                            }
                        } else {
                            throw new AccionIlegal("No se puede robar una casilla");
                        }
                    } else {
                        throw new AccionIlegal("No se puede comprar este tipo de casilla");
                    }
                } else {
                    throw new AccionIlegal("No se puede comprar una casilla por Bluetooth");
                }

                flag = true;
                break;
            }
        }

        if(!flag){
            throw new InexCas("No existe la Casilla " + nombreCasilla, nombreCasilla);
        }

    }
    @Override
    public void edificar(String tipoEdificio) throws TipoEdificioInvalido, FortunaInsuficiente, SinPermisoEdificar, EdificarHipotecada, NoComprada, TipoCasillaInvalido, SinPermisoGrupo, SinPermisoSolar {
        Jugador jugadorturno = arrayJugadores.get(turno);
        int posicionactual = jugadorturno.getAvatar().getPosicion();
        Casilla casillactual = arraycasillas[posicionactual];

        if (casillactual instanceof Solar sol) {
            Grupo grupoactual = sol.getGrupo();
            int tipo = Edificio.StringtoIntEdificio(tipoEdificio);
            Edificio nuevoedificio;
            if (sol.perteneceajugador(jugadorturno)){
                if (!sol.getHipotecada()) {
                    if ((grupoactual.grupoComprado(sol) || (casillactual.frecuenciaVisita(arrayJugadores.indexOf(jugadorturno)) > 2))) {

                        sol.PermisoCasilla(tipo);

                        grupoactual.permisoGrupo(tipo);


                        nuevoedificio = switch (tipo) {
                            case 0 -> new Casa(idEdificio, sol);
                            case 1 -> new Hotel(idEdificio, sol);
                            case 2 -> new Piscina(idEdificio, sol);
                            case 3 -> new PistaDeporte(idEdificio, sol);
                            default -> throw new TipoEdificioInvalido("El tipo de edificio introducido no existe", sol, tipo);
                        };
                        if (jugadorturno.getFortuna() > nuevoedificio.getValor()) {

                            sol.Edificar(nuevoedificio, grupoactual);



                            consola.consolaimprimir("Felicidades, eres propietario de la " + tipoEdificio + "-" + nuevoedificio.getID() + ", valorada en " + nuevoedificio.getValor() + "€");
                            idEdificio++;

                        } else {
                            throw new FortunaInsuficiente("Fondos insuficientes", jugadorturno, nuevoedificio.getValor());
                        }
                    } else {
                        throw new SinPermisoEdificar("No puedes edificar donde te de la gana, necesitas obetener el grupo de esta casilla o haber pasado mas de dos veces por ella para poder edificar", sol);
                    }
                } else {
                    throw new EdificarHipotecada("No puedes edificar en una casilla hipotecada", sol);
                }
            } else {
                throw new NoComprada("No posees esta Casilla", sol);
            }
        }else{
            throw new TipoCasillaInvalido("No puedes edificar en una casilla que no sea un solar", casillactual);
        }
    }
    @Override
    public void hipotecar(String nombreCasilla) throws AccionIlegal, InexCas, NoComprada, VenderEdificada, TipoCasillaInvalido {

        Jugador jugadorturno = arrayJugadores.get(turno);

        boolean flag = false;
        for(Casilla c : arraycasillas){
            if (c.getNombre().equals(nombreCasilla)){
                flag = true;
                if(c instanceof Solar sol) {
                    if (sol.perteneceajugador(jugadorturno)) {
                        if (sol.getArrayEdificios().isEmpty()) {
                            if (!sol.getHipotecada()) {
                                sol.setHipotecada(true);
                                jugadorturno.Aumento(sol.valor() / 2);
                                consola.consolaimprimir("La casilla " + nombreCasilla + " ha sido hipotecada. El jugador " +
                                        jugadorturno.getNombre() + " ha recibido " + String.format("%.2f", (sol.valor() / 2)) + "€");
                            } else {
                                throw new AccionIlegal("La casilla " + nombreCasilla + " ya esta hipotecada");
                            }
                            break;
                        } else {
                            throw new VenderEdificada("Debes vender los edificos construidos en el solar antes de poder venderlo\n" +
                                    " Actualmente hay " + sol.NumeroTipoEdificios(0) + " casas, " + sol.NumeroTipoEdificios(1) +
                                    " hoteles, " + sol.NumeroTipoEdificios(2) + " piscinas y " + sol.NumeroTipoEdificios(3) +
                                    " pistas en " + sol.getNombre(), sol);
                        }
                    } else {
                        throw new NoComprada("No puedes hipotecar una casilla que no te pertenece", sol);
                    }
                }else {
                    throw new TipoCasillaInvalido("No puedes hipotecar una casilla que no sea un solar", c);
                }
            }
        }
        if(!flag){
            throw new InexCas("La casilla " + nombreCasilla + " no existe", nombreCasilla);
        }

    }
    @Override
    public void deshipotecar(String nombreCasilla) throws AccionIlegal, FortunaInsuficiente, InexCas, NoComprada, TipoCasillaInvalido {

        Jugador jugadorturno = arrayJugadores.get(turno);

        boolean flag = false;
        for(Casilla c : arraycasillas){
            if (c.getNombre().equals(nombreCasilla)){
                flag = true;
                if(c instanceof Solar sol) {
                    if (sol.perteneceajugador(jugadorturno)) {
                        if (sol.getHipotecada()) {
                            double deuda = (((sol.valor() * -1) / 2)) - (0.1 * (sol.valor() / 2));
                            if (jugadorturno.getFortuna() > deuda) {
                                jugadorturno.Aumento(deuda);
                                sol.setHipotecada(false);
                                consola.consolaimprimir("La casilla " + nombreCasilla + " ha sido deshipotecada. El jugador " +
                                        jugadorturno.getNombre() + " ha pagado " + String.format("%.2f", -deuda) + "€");
                            } else {
                                throw new FortunaInsuficiente("No tienes el dinero suficiente para deshipotecar la propiedad", jugadorturno, deuda);
                            }
                        } else {
                            throw new AccionIlegal("La casilla " + nombreCasilla + " no esta hipotecada, no se puede deshipotecar");
                        }
                    } else {
                        throw new NoComprada("No puedes deshipotecar una casilla que no te pertenece", sol);
                    }
                    break;
                }else{
                    throw new TipoCasillaInvalido("No puedes deshipotecar una casilla que no sea un solar", c);
                }
            }
        }
        if(!flag){
            throw new InexCas("La casilla " + nombreCasilla + " no existe", nombreCasilla);
        }
    }
    @Override
    public void estadisticas(){
        consola.consolaimprimir(toStringStats(sumadados));
    }
    @Override
    public void estadisticas(String nombreJugador) throws InexJug {
        for (Jugador jugador : arrayJugadores) {
            if (jugador.getEe() == 'p') {
                if (jugador.getNombre().equals(ANSICodes.ANSI_BLUE + nombreJugador + ANSICodes.ANSI_RESET)) {
                    consola.consolaimprimir(jugador.toStringStats());
                    return;
                }
            } else if (jugador.getEe() == 's') {
                if (jugador.getNombre().equals(ANSICodes.ANSI_YELLOW + nombreJugador + ANSICodes.ANSI_RESET)) {
                    consola.consolaimprimir(jugador.toStringStats());
                    return;
                }
            } else if (jugador.getEe() == 'u') {
                if (jugador.getNombre().equals(ANSICodes.ANSI_PURPLE + nombreJugador + ANSICodes.ANSI_RESET)) {
                    consola.consolaimprimir(jugador.toStringStats());
                    return;
                }
            } else {
                if (jugador.getNombre().equals(nombreJugador)) {
                    consola.consolaimprimir(jugador.toStringStats());
                    return;
                }
            }
        }
        throw new InexJug("El jugador " + nombreJugador + " no existe", nombreJugador);
    }
    @Override
    public void vender(String tipoEdificio, String nombreCasilla, int numVenta) throws AccionIlegal, InexCas, TipoCasillaInvalido, NoComprada {
        Jugador jugadorturno = arrayJugadores.get(turno);
        
        boolean flag = false;
        for (Casilla c : arraycasillas) {
            if (c.getNombre().equals(nombreCasilla)) {
                if(c instanceof Solar sol) {
                    Grupo grupoactual = sol.getGrupo();
                    flag = true;
                    if (((Solar) c).perteneceajugador(jugadorturno)) {
                        int tipo;
                        if ((tipo = Edificio.StringtoIntEdificio(tipoEdificio)) < 0) {
                            //ERROR
                            consola.consolaimprimir(" ");
                            return;
                        }
                        int porvender = numVenta;
                        boolean flag2, flag3;
                        double aumento = 0;

                        while (porvender > 0) {
                            flag2 = false;
                            flag3 = false;
                            for (Edificio e : sol.getArrayEdificios()) {
                                switch(tipo){
                                    case 0:
                                        flag3 = (e instanceof Casa);
                                        break;
                                    case 1:
                                        flag3 = (e instanceof Hotel);
                                        break;
                                    case 2:
                                        flag3 = (e instanceof Piscina);
                                        break;
                                    case 3:
                                        flag3 = (e instanceof PistaDeporte);
                                        break;
                                }

                                if(flag3){
                                    sol.getArrayEdificios().remove(e);
                                    jugadorturno.getArrayEdificios().remove(e);
                                    grupoactual.getArrayEdificios().remove(e);
                                    jugadorturno.Aumento(e.getValor() / 2);
                                    aumento += e.getValor() / 2;
                                    flag2 = true;
                                    break;
                                }
                            }
                            if (!flag2) {
                                int vendidos = numVenta - porvender;
                                consola.consolaimprimir("Solo se han podido vender " + vendidos + " " + tipoEdificio + " ya que la casilla seleccionada no tiene mas");
                                break;
                            }
                            porvender--;
                        }
                        int vendidos = numVenta - porvender;
                        consola.consolaimprimir("El jugador " + jugadorturno.getNombre() + " ha vendido un total de " + vendidos + " " + tipoEdificio + ", recibiendo " +
                                String.format("%.2f", aumento) + "€. En la propiedad " + nombreCasilla + " quedan " + sol.NumeroTipoEdificios(tipo) + " " + tipoEdificio);
                        break;
                    } else {
                        throw new NoComprada("La casilla" + nombreCasilla + " no pertenece al jugador " + jugadorturno.getNombre(), sol);
                    }
                }else{
                    throw new TipoCasillaInvalido("No se puede vender este tipo de casilla", c);
                }
            }
        }
        if (!flag) {
            throw new InexCas("La casilla " + nombreCasilla + " no existe", nombreCasilla);
        }
    }
    @Override
    public void bancarrota(Jugador muerto, Jugador beneficiado){
        int i;
        Propiedad p;

        while(!muerto.getArrayPropiedades().isEmpty()) {
            i = muerto.getArrayPropiedades().size()-1;
            p = (Propiedad) muerto.getArrayPropiedades().values().toArray()[i];
            p.setDuenho(beneficiado);
            beneficiado.getArrayPropiedades().put(p.getNombre(),p);
            muerto.getArrayPropiedades().remove(p.getNombre(),p);
        }

        Edificio e;
        while(!muerto.getArrayEdificios().isEmpty()){
            i = muerto.getArrayEdificios().size()-1;
            e = muerto.getArrayEdificios().get(i);

            beneficiado.getArrayEdificios().add(e);
            muerto.getArrayEdificios().remove(e);
        }

        for(Casilla cas : arraycasillas){
            cas.getArrayBoolean().remove(arrayJugadores.indexOf(muerto));
        }

        consola.consolaimprimir("El jugador " + muerto.getNombre() + " ha caido en bancarrota y por lo tanto queda fuera de la partida\n" +
                "Sus propiedades vuelven a estar en venta");

        int turnoanterior = getTurno();
        setTurno(getTurno()+1);
        Jugador siguientejugador = arrayJugadores.get(turno);
        setTurno(turnoanterior);
        arrayJugadores.remove(muerto);
        setTurno(arrayJugadores.indexOf(siguientejugador));

        dadosporlanzar = 0;
        contadordobles = 0;
        comprasRealizadas = 0;
        movimientosRealizados = 0;

        consola.consolaimprimir("Quedan " + arrayJugadores.size() + " jugadores!");

        for (Trato t:arrayTratos.values()){
            if (t.getPropone()==muerto || t.getAcepta()==beneficiado){
                consola.consolaimprimir("El trato "+t.getID()+" ha sido eliminado");
                t.eliminarTrato(arrayTratos,t.getID());
            }
        }

        if (arrayJugadores.size()==1){
            consola.consolaimprimir("\u001B[38;5;10m WINNER WINNER CHICKEN DINNER\u001B[0m\nFELICIDADES HERMANO ERES EL MEJOR");
            consola.consolaimprimir("HA GANADO "+arrayJugadores.get(turno).getNombre()+"!!!!!!");
            this.acabarPartida();
            return;
        }
        consola.consolaimprimir("El turno pasa a tenerlo " + arrayJugadores.get(turno).getNombre());
    }
    @Override
    public void kill(String nombrenuevopropietario) throws InexJug{
        Jugador jugadorturno = arrayJugadores.get(turno);


        for (Jugador j : arrayJugadores){
            if (nombrenuevopropietario.equals(j.getNombre())) {
                bancarrota(j, jugadorturno);
                return;
            }
        }
        throw new InexJug("El jugador " + nombrenuevopropietario + " no existe", nombrenuevopropietario);
    }
    @Override
    public String toString() {
        StringBuilder resultado = new StringBuilder();
        String espacio = "                     ";//21 espacios

        int i;
        resultado.append("\u001B[4m").append(espacio).append(espacio).append(espacio).append(espacio).append(espacio).append(espacio).append(espacio).append(espacio).append(espacio).append(espacio).append(espacio).append(" \n").append("\u001B[0m");
        for (i = 20; i <= 30; i++) {
            resultado.append("|\u001B[38;5;").append(arraycasillas[i].saberColor()).append("m").append(String.format("%-20s", arraycasillas[i].getNombre())).append("\u001B[0m");
        }
        resultado.append("|\n");
        for (i = 20; i <= 30; i++) {
            resultado.append("\u001B[4m" + "|").append(arraycasillas[i].toStringJugadoresPresentes(arrayJugadores)).append("\u001B[0m");
        }
        resultado.append("\u001B[4m" + "|\n" + "\u001B[0m");

        for (i = 1; i <= 8; i++) {
            resultado.append("|\u001B[38;5;").append(arraycasillas[20 - i].saberColor()).append("m").append(String.format("%-20s", arraycasillas[20 - i].getNombre())).append("\u001B[0m|").append(espacio).append(espacio).append(espacio).append(espacio).append(espacio).append(espacio).append(espacio).append(espacio).append("                    ").append("|\u001B[38;5;").append(arraycasillas[30 + i].saberColor() * 10).append("m").append(String.format("%-20s", arraycasillas[30 + i].getNombre())).append("\u001B[0m|\n");
            resultado.append("\u001B[4m" + "|").append(arraycasillas[20 - i].toStringJugadoresPresentes(arrayJugadores)).append("|").append("\u001B[0m").append(espacio).append(espacio).append(espacio).append(espacio).append(espacio).append(espacio).append(espacio).append(espacio).append("                    ").append("\u001B[4m").append("|").append(arraycasillas[30 + i].toStringJugadoresPresentes(arrayJugadores)).append("|\n").append("\u001B[0m");
        }
        resultado.append("|\u001B[38;5;").append(arraycasillas[11].saberColor()).append("m").append(String.format("%-20s", arraycasillas[11].getNombre())).append("\u001B[0m|").append(espacio).append(espacio).append(espacio).append(espacio).append(espacio).append(espacio).append(espacio).append(espacio).append("                    ").append("|\u001B[38;5;").append(arraycasillas[39].saberColor() * 10).append("m").append(String.format("%-20s", arraycasillas[39].getNombre())).append("\u001B[0m|\n");
        resultado.append("\u001B[4m" + "|").append(arraycasillas[11].toStringJugadoresPresentes(arrayJugadores)).append("|").append("\u001B[0m").append("\u001B[4m").append(espacio).append(espacio).append(espacio).append(espacio).append(espacio).append(espacio).append(espacio).append(espacio).append("                    ").append("|").append(arraycasillas[39].toStringJugadoresPresentes(arrayJugadores)).append("|\n").append("\u001B[0m");


        for (i = 10; i >= 0; i--) {
            resultado.append("|\u001B[38;5;").append(arraycasillas[i].saberColor()).append("m").append(String.format("%-20s", arraycasillas[i].getNombre())).append("\u001B[0m");
        }
        resultado.append("|\n");
        for (i = 10; i >= 0; i--) {
            resultado.append("\u001B[4m" + "|").append(arraycasillas[i].toStringJugadoresPresentes(arrayJugadores));
        }
        resultado.append("|\u001B[0m \n");
        return resultado.toString();
    }


    //EASTER EGGS
    public void showart(){
        consola.consolaimprimir("""


                .............................................................................................................................................
                .............................................................................................................................................
                ........................................................................-----------------------------------------:----:-::::::::::::::::::...
                ........................................................................-------------------------------:--------:----:-:::--::::::::::::::...
                .............................-*########**+-:--+=+-......................--------------------------:---------+++------:----:-:-::::::::::::...
                ........................-@%%%%%%%%%%%%%@@%@%@%%@%%#+=-..................---------------------+#*=@+@@@@@@@@@@@@@@%@-.-----:-::::-:::::::::...
                .....................#@@@@@@@%%%%%%%%%@@@@@@@@@@@@@@@%*-................------------------%@@@@@@%@@@@@@@%#@@@@@@@%*@@@@#-::---:-:::::::::...
                ..................:@@@@@@@@%@%%%%%%%@@@@@@@@@@@@@@@@@@@%*+..............---------------=%@@@@@@@@#@@%@%%%@@@%@@@%%%#%@#@@@@-+:::-::-::::::...
                ................=@@@@@@@@@@@%%%%%%#%%%@@@@@@@@@@@@@@@@@@@%#=............-----------+=@@@@@#@@@@@+@@@#@@@%#%@%@++%%#@%@@%#@@@#.--::::::::::...
                ..............=@@@@@@@@@@@%%%%%%%%%%%@@@@@@@@@@@@@@%@@@@@@@#=..:........--------=-%%@%@@@@@@@@@@@@@@@@%#@@@%@#%%@%@@#@@@@%%@@@@@%:-:::::::...
                .............@@@@@@@@@@@%@%%%%%@%%@@@%@@@%@@@@@@@@@%%@@@@@@@#=..........--------=-%@@@@@@@@@@@@@@@@@@@@@%=*@%@%###%@@@%%@@@@%@@##-+--:::::...
                ...........=@@@@@@@@@@@@@%%%%%%%%%%%@@@%%@@@@@@@@@@@@@@@@@@@%%=...:.....--------:@@@@@@@@@@@@@@#@@@@@@@@%%@@@@@@@@%@@#%@@@@@@@@@@=+:::::::...
                ..........*@@@@@@@@@@@@%@%%@@@%%%%%@@@%@@@@@@@@@@@@@@@@@@@@@@%%-........----=-=+@@@@@@@@@@@@@@%@@@@@@@@@%@%%@%@@%@%@%%%%@@%%@@@@@+-:::::::...
                .........#@@@@@@@@@@@@@%%%@@%%%%@%%@%%%%@@@%@@@@@@@@%%%%@@@@@%%#=.......----=*#@@@@@@@@@@@@@@@##@@@@@@@@@%@%%%#@@@@@@@@%%%@%%@@@@@@@==-:::...
                ........%@@@@@@@@@@@@@@@@@@@%@%@%@@%@%%%%%%%%@%%%%%@%####%@@@@%%#.......---#-+#@@@@@@@@@@@@@@@@@@@@@@@@@@@%@#@@@@@%@%@@@%@%@@@@@@@@@+%+-::...
                .......#@@@@@@@@@@@@@@%@@@@%%%@%@@%%%%%%#%%%%%%%%@@##**++*%@@@@%#+-.....--%++%#@@@@@@@@@@@@@@@@@@@@@@@@%@@%@@@@@@%@@%%@%@%@@%%@@@@@@@@*--:...
                ......*@@@@@@@@@@@@@@@@@@@%%%@@@@%%%%%%*%%%%%%##%%%###++==+%@@@%%#--....-@=:@#@@@@@@@@@@@@@@@@@@@@@@@@@@@##@#@@@@%@@@@@%@@@%@@@@@@@@@@%-::...
                .....=@@@@@@@@@@@@@@@@@@@@%@@@@@%@%%%##%%%####+#%%#+++=====+%@@%%#+.:...-#*-@@@@@@@@@@@@@@@@@@@@@@@@@@@%@@%%@%@@@@%@@%@@@@@@@@@@@@@@@%%-::...
                .....@@@@@@@@@@@@@@@@@@@@@@@@@@@@@%%%%@%%####+*#%*+=++======+@@@%%*=....-#+@@@@@@@@@@@@@@@@@@@@@@@@@@@@%@@@@@%@@%@@@#%%%%%%%%%@@@@@@@@@+-:...
                ....%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@%%%%%%####++#%#+=+==-======#@@@@%+....-+@@@@@@@@@@@@@@@@@@@@@@@@@@@@@#@@@@@@@@#+-:---++++=+*#*#@@@@%@@@=...
                ...%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@%%%%###+*#%#+++==--======+%@@@@%=...-+@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@##=-==--==--==-=+===+@@@@%@+:...
                ...@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@%####+*#%#++++------=====*@%@@@%...-@@@@@@@@@@@@@@@@@@@@@@@@@@@@%%@%##+++----=---------===-==%@%%@@#=...
                ...@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@%%#*+#*%#+=+*--------======%@%@@@...=@@@@@@@@@@@@@@@@@@@@@@@@@@@**+=++=+==----------------====%@@@@#%-...
                ...@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@%%#%%%#*++*=----===========+@%@@@...-*@@@@@@@@@@@@@@@@@@%%@##@#+=========--------------------=#@%@@%+:...
                ...@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@%%####=--===+#####*+==++=@@%@@...-#@@@@@@@@@@@@@@@@@#*+**++++=======------:---------------=*@@@%-+:...
                ...@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@%%%%%#+==--===+++++========#@%@@...-#@@@@@@@@@@@@@@@@#**+****++=+====--=-------:------------=+@@@#-::...
                ...@@@@@@@@@@@@@@@@@@@@@@@%%@@@@@@@%#+#+#%#++=--===+#%%%###+====+@@@@...-@@@@@@@@@@@@@@@@%####***%#######++====------------====+===@@@@-::...
                ...@@@@@@@@@@@@@@@@@@@%%%%%%@@%@@@@--%#=#%#+==--==+##@@+==%*+====%@@@...-@@@@@@@@@@@@@@@@####%@%@@@@@@@@@@@@%#++====-==**%*@%%#@@#%@@%@-::...
                ...@@@@@@@@@@@@@@@@@@%%######%####+++++=###++==-===++*++==++=====*@@@...-+@@@@@@@@@@@@@@#####*+**##*%%#%@@%%%%@*+===+*#%###*++==+##@#@--::...
                ...@@@@@@@@@@@@@@@@@%##****++*++++++==++*###+====================+@@@...-=%#@@@@@@@@@@@####**#**%%%@@@=-%%#@%##*+===+*#%#@@@@+#@++-@@@-:::...
                ...@@@@@@@@@@@@@@@@@##***+++==========+++*##+==-===--===---=======@@@...-%@@##@@@@@@@@@###*+++##%%%##@+=###@*%+*+=--==%*#%%@++%#*+=@@+::::...
                ...@@@@@%%%@@@@@@@@%##***++++====-====+**##*+=--===----------=====@@@...-%@@#+@@@@@@@@%###*++++++**#**+++++#*+**+=--===*#*#*#**===-@#:::::...
                ...@@%%%%@@@@@@@@@%%%##*++++++====--==+*###+========---=-----=====%@@...--#@@###@#%@@@%%###*++++++++=+===+++++++++=--=-=======---==@=:::::...
                ...@%%%%@@@@@@@@@@%%%##***+++========+*%###++==--===---=------====@@@...---#%@@@@##@@@@####***+++==========+++++++=--------------==@-:::::...
                ...%%%%%@@@@@@@@@@%%%%###**+++===-===+#%###*+==---======-----=====@@@...----=@@@@*#%@@@%#####**+===-=--====++++++==--------:-----==%--::::...
                ...%%%%%@@@@@@@@@%%%%%%###**+++=====+#@###%#*+======+++==---=====%@@@...-----#@@%@#*@@@%#####*+++======-==*++++++==-----=--------==-::::::...
                ...%%%%@@@@@@@@@@%%%%%%%%##*++++=====+%@##%@%#*+*%*-=+++=========@@@@...------##%@%##@@######***+++====+#%@#+=====--:---++==--=----:::::::...
                ...%%%%%@@@@@@@@%%%%%%%%%%##*++++===++***##*++====----=++=======#@@@@...------:%%%%%*#%#######++++++++++#%%%#%@@#+++++%===**======::::::::...
                ...@@@@%@@@@@@@@@%%%%%%%%%###+++++++******+==-==--=-=====+======@@@@@...--------%##%##%######**#**++++##*###%@@@%*##+*+==+%+#+====.:::::::...
                ...@@@@@@@@@@@@@@%%%%%%%%###*++*#####**++++=====----====+++====#@@@@@...---------@@@##%%%##**#**+++=#####*##+**###%+======++*++=+=::::::::...
                ...@@@@@@@@@@@@@@@%%%%%##%##*+=+#%%%%%%%######+++++++=++*+=====@@@@@@...---------:@@%#%#####**+#*++####*++++++++=--===--=+===+==+=::::::::...
                ...@@@@@@@@@@@@@@@@@%%%%#####+==+##%%#%###%%%#+++======++=====@@@@@@@...----------:=%##%########*+=++%@@@@##++%=*#=%+%%#*++++=+===::::::::...
                ...@@@@@@@@@@@@@@@@@@%%%%%###*+=+*##%%%##*+===-========+=====@@@@@@%@...------------@###%#####***+==++#@#%-==.--.-:-:-=%%+++===++:::::::::...
                ...@@@@@@@@@@@@@@@@@@@@%%#%%#*+++**###%%##**+++++===========@@@@@%%%@...------------=####%####**#++=++++###***@%%#%#=%**--====++-:::::::::...
                ...@@@@@@@%@@@@@@@@@@@@@%%#%%##+**##**###***+*++===========@@@@@%@%@@...-------------##%%%%####*#*+++++++++*#*+++===++=---===++*::::::::::...
                ...@@@@@@@@@@@@@@@@@@@@@@%%%%%##**#**+++++===============+@@@@@@@@%@@...-------------%###%%######*++*++**+#***++++++========++*.::::::::::...
                ...@@@@@@@%@@@@@@@@@@@@@@@@@%%%%###**++==+====--=======+@@@@@@@@@@@@@...-------------%####%%%%###**++#++****#*#*++*+=======++*::::::::::::...
                ...@@@@@@@@@@@@@@@@@@@@@@@@@@@%%%%%##**+++===========+%@@@@@@@@@@@@@@...------------:@####%%%%%####++++++++++++**========-==#:::::::::::::...
                ...@@@@@@@@@@@@@@@@@@@@@@@@@@%@@@%%%####**+++======+%@@@@@@@@@@@@@@@@...------------:%#####%%%%%%%%#+*+++=++++-=--===---==+:::::::::::::::...
                ...@@@@@@@@@@@@@@@@@@@@@@%%%@%%%%%%%%%%####++++++==@@@@@@@@@@@@@@@@@@...-------------%#####%#%%%%#%%###***+===+=+-=-=-==+#::::::::::::::::...
                ...%@@@@@@@@%@%@@@@@@@@@@@%%##*####**#####**+=====#@@@@@@@@@@@@@@@@@@...-----------:+%####%#%%%%%%@%%%%%###*++===+===++#-:::::::::::::::::...
                ...===++@@@@@@@@@@@@@@@@@@@#%*#*++===============+@@@@@@@@@@%@@@@@@@@...------------%%####%##%%%%%%%@@%%%%%%%###**####+:::::::::::::::::::...
                ...=-=====%@@@@@@@@@@@@@@@@@*#%*#++==============@@@@@@@@@@@@@@@@@@@@...-----------%%########%%%@%%%%%@%@@@%@@@@@@@%#*::::::::::::::::::::...
                ...=-======-%@@@@@@@@@@@@@@@%##*+#*==============@@@@@@@@@@@%@@@@@@@@...----------%%%##########%%@%%%%%%%%%%%%%%%*#+**::::::::::::::::::::...
                ...-=--======%@@@@@@@@@@@@@@%@@+##+++===========*@@@@@@@@@@@@@@%@@@@@...--------.%######***######%%%%%%%#%%%%#*#+++*+=::::::::::::::::::::...
                ...--=========%@@@@@@@@@@@@@@##@##%#+++=========@@@@@@@@@@@@@%@%@@@@@...-----:.%########*****##*####*#####*++#*+*+++=:::::::::::::::::::::...
                ...-=-+=--==+**@@%##%@@@@@@@@@@*#%==*=*+#-======%@@@@@@@@@@@@@%%@@@@%...@%@%%%###*#####**+++++******+#+*++++*+#+=+++=:::::::::::::::::::::...
                ...===-=-====++#%%%%=*@@@@@@@@@%++#%=+++--=-===+#%@@@%@@@@@@@%%@@@@%%...%%%%%%%++***####*+++++++++++++++==+=+=+++===+#::::::::::::::::::::...
                ...===-=-====*+%*=*%@%@@@@@@@@@@%#+==-=+%-=+===+##%@@%@@@@@@@%%%@@@%%...%@%%#%#%%+***##*+++==+=+=+===+==+====+=======%%##.::::::::::::::::...
                ...=====---==++#*-+*#@@@@@@@@@@@@%%*+#=+++*==#-=*##%@%%@@%@@%%@@@@%%@...%%@@@%%%%@%%+*+++++==================+=======.%#####*.::::::::::::...
                ...==+===--==-=#+==++==@@@@@@@@*@@%%%=+*+++#+=*+*=#%@%@%@@@%%%@@@@%%@...@@#@%@#%%@@@@%%+++=======++===================.#########%*=:::::::...
                ...--+=----=-:=#+-#=-=#@@%@@@@@##%%*+%@@+#*#+#===--+%@@@@@@@%%@@@@@%@...@%%@%%%@@@%@%#%@@##=======+==================-.%#########%#%###%#+...
                ...-=+=--==--==%+=%=-=@@@#@@@@@@#*=%+=#@@@%#%%*###=#=+@%@@@%%@@@@@@@@...@@@@%@%%@@%#%#%@@%%@%%--=================-=--=###%#%##%#######%%##...
                ...-+=-==---===%+=%*+*%%%*@@@@@#%=*=####%*@@##*%*=#=++=#@@@@%@@@@@@@@...%%@@%@%@@%%@@%%%@@@#%@%@%-=====-========--==+%%%###%######%#####%%...
                ...-+===-=---=+@#=##=**@=#@@%@@%%#-+=#+=%%%@@@+===+#%%%=+#@@@@@@@@@@@...%%%@#%%%@#@#@%%@%*%@%%@@@%@#%=-========-=-#%##%###%%##%%%%##%%#%##...
                ...=====-==--=+@#=*@==%#*###%%%@%@++==#%@+%%=@@@##%=++*#*#%%@@@@@@@@@...%%#%@%@%#@%@@%%@@@%@##%@%@%@@%@@@%%###%%@%@##%%@%#%%########%%%#%%...
                ...-=-====:=-=#@+=+@#=+#+=+##%%%@%@===+%=#%+%*++@@*+===*+=+%%@@@@@@@@...%%*%%##%@%%@@@%%@%@@%@#%%%@%%@#@@%#%%%%%%%%#%%%%#%%%%%%%#%#%%###%#...
                ...-======-=--#@*==@#==++=+*=*%%%%##+==+%###=+=#=*%@+======##@@@@@@@@...#%#%%#@%%%@%@#@%%@%%@#%%@%#@#%#%@%#%%%%#%@%%###%##%%#%%%##%%%%###%...
                ...:-+=-+--=-=#@*==#@-=+#====++##*#+++=-#%%%#%%%#*%@@@%+=%=**@#%@@@%@...%%#%@%@####%%@%@@@%@%#%%@@@@@%%#@##%#@%####%%#%%#%%%####%###%#%###...
                ...=--=+=---+-#@+==+@*===*---===+*#+=+=-=#+-%##%%@@%*###%%#%%@@@@@@@@...#@%%%@@%#%##%#%@@@@%@#%@@%%#%%##@#@@%@@@@%@%%#%#%%@@@%%%#%%#%%@%#%...
                ...=--=+===-==%@+-=+%%=---+-=-==+==*=+=-=+=+%#*%%+#+=#+#%####%+===@@%...#%%#%#@@%@@%##%#%@%@%%%#@%%%*%@@@@@@@@%%%%#%%#%%%%@%%%#%%%%%@#%@##...
                ...-=:=====-+=%%=-=++@+===-#====-==--=-*--*=*%-=%==--=+*=*%%@*#@+=+=+...@#%####@@@%%%%#*%*%@@#@@##@##@%##%###+**#%%*%%*%#%%#%%#%#%#%#@#%@#...
                ...----==*+==+%#=-=+=%#===--=---=--=====---=+%%==%+=*=#+=+#@@%@%@%#=#...@@%@*%#%%%@@@##@###%%@@#%#%%##%#@@@@%%@%@%@%@@@#####%##%%####%@%%@...
                ...=--:-*+=-=#@++=-==%@=-----=--=====+-=:=-%%+=-++=+#=*=##=@@@@@%@@@=...@@@@#%%#%%@#%%@%*%###%@%@@@##*##%###***%%*###%@@##%%##%@%@@%%#%@%#...
                ...+---+##+==*@+====-+@%=-====--==-------=-+%%==-+=+=#-=+#%-@%%@@++@@...%%@@@@%%#%*#@@@@%%@#@@#%@@#@@%%%##%%@%*%*%%@@@##@%%%#%%#%%%%%@*%@%...
                .............................................................................................................................................
                .............................................................................................................................................

                """);
    }
    public void pitufo(){
        Jugador jugadorturno = arrayJugadores.get(turno);

        if (!jugadorturno.pitufo())
            return;

        consola.consolaimprimir("Has pitufeado...");
    }
    public void simpson(){
        Jugador jugadorturno = arrayJugadores.get(turno);

        if (!jugadorturno.simpson())
            return;

        consola.consolaimprimir("Los Simpsooon...\uD83C\uDFB6");
    }
    public void vendimia(){
        Jugador jugadorturno = arrayJugadores.get(turno);

        if(!jugadorturno.vendimia())
            return;

        consola.consolaimprimir("Te has manchado recogiendo uvas...");
    }
    public void entrenar(){
        Jugador jugadorturno = arrayJugadores.get(turno);
        if (jugadorturno.getCarcel() >0){
            consola.consolaimprimir(jugadorturno.getNombre() + " se esta poniendo fuerte...");
            jugadorturno.setEe('f');
        }
    }
}