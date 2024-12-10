package Jugadores;

import Excepciones.NombreEnUso;
import Juego.Casillas.Casilla;
import Juego.Casillas.Propiedades.Propiedad;
import Juego.Edificios.*;
import Juego.Juego;
import Jugadores.Avatares.Avatar;
import Juego.ANSICodes;

import java.util.ArrayList;
import java.util.HashMap;

public final class Jugador{
    //ATRIBUTOS
    private String nombre;
    private final Avatar avatar;
    private double fortuna;
    private int vueltas;
    private int numVueltas;
    private int carcel;
    private int vecesCarcel;
    private int dadosLanzados;
    private double dineroInvertido;
    private double dineroImpuestos;
    private double dineroAlquileresPagado;
    private double dineroAlquileresRecibido;
    private double dineroSalida;
    private double dineroBoteInversiones;
    private char ee;
    private final HashMap<String,Propiedad> arrayPropiedades;
    private final ArrayList<Edificio> arrayEdificios;

    //GETTERS
    public String getNombre() {
        return nombre;
    }
    public int getCarcel(){return carcel;}
    public int getVecesCarcel(){return vecesCarcel;}
    public int getDadosLanzados(){return dadosLanzados;}
    public Avatar getAvatar() {
        return avatar;
    }
    public double getFortuna() {
        return fortuna;
    }
    public ArrayList<Edificio> getArrayEdificios() {
        return arrayEdificios;
    }
    public HashMap<String,Propiedad> getArrayPropiedades() {
        return arrayPropiedades;
    }
    public double getDineroInvertido() { return dineroInvertido; }
    public double getDineroImpuestos() { return dineroImpuestos; }
    public double getDineroAlquileresPagado() { return dineroAlquileresPagado; }
    public double getDineroAlquileresRecibido() { return dineroAlquileresRecibido; }
    public double getDineroSalida() { return dineroSalida; }
    public double getDineroBoteInversiones() { return dineroBoteInversiones; }
    public int getVueltas() {
        return vueltas;
    }
    public int getNumVueltas() {
        return numVueltas;
    }
    public char getEe() {
        return ee;
    }

    //SETTERS
    public void setFortuna(double fortuna) {
        if(fortuna<0){Juego.consola.consolaimprimir("BANCARROTA");}
        this.fortuna = fortuna;
    }
    public void setDineroInvertido(double dineroInvertido) {
        if(fortuna<0){Juego.consola.consolaimprimir("No existe dinero negativo");}
        this.dineroInvertido = dineroInvertido;
    }
    public void setDineroImpuestos(double dineroImpuestos) {
        if(fortuna<0){Juego.consola.consolaimprimir("No existe dinero negativo");}
        this.dineroImpuestos = dineroImpuestos;
    }
    public void setDineroAlquileresPagado(double dineroAlquileresPagado) {
        if(fortuna<0){Juego.consola.consolaimprimir("No existe dinero negativo");}
        this.dineroAlquileresPagado = dineroAlquileresPagado;
    }
    public void setDineroAlquileresRecibido(double dineroAlquileresRecibido) {
        if(fortuna<0){Juego.consola.consolaimprimir("No existe dinero negativo");}
        this.dineroAlquileresRecibido = dineroAlquileresRecibido;
    }
    public void setDineroSalida(double dineroSalida) {
        if(fortuna<0){Juego.consola.consolaimprimir("No existe dinero negativo");}
        this.dineroSalida = dineroSalida;
    }
    public void setDineroBoteInversiones(double dineroBoteInversiones) {
        if(fortuna<0){Juego.consola.consolaimprimir("No existe dinero negativo");}
        this.dineroBoteInversiones = dineroBoteInversiones;
    }
    public void setVueltas(int vueltas) {
        this.vueltas = vueltas;
    }
    public void setDadosLanzados(int dadosLanzados) {
        this.dadosLanzados = dadosLanzados;
    }
    public void setNumVueltas(int numVueltas) {
        this.numVueltas = numVueltas;
    }
    public void anhadirSolar(Propiedad propiedad){
        arrayPropiedades.put(propiedad.getNombre(), propiedad);
    }
    public void setCarcel(int carcel) {
        if(carcel>=0&&carcel<4) {
            this.carcel = carcel;
        }else{
            Juego.consola.consolaimprimir("Carcel tiene valores entre 0 y 3");
        }
    }
    public void setVecesCarcel(int vecesCarcel){
        if(vecesCarcel<0){
            Juego.consola.consolaimprimir("No puedes estar veces negativas en la carcel, tontito");
        }else {
            this.vecesCarcel = vecesCarcel;
        }
    }
    public void setEe (char ee){
        this.ee = ee;
    }

    //CONSTRUCTORES
    public Jugador(String nombre, Avatar avatar) throws NombreEnUso{
        if(nombre.equals("Banca") || nombre.equals("ERROR")){
            throw new NombreEnUso("El nombre introducido no se puede utilizar");
        }
        this.nombre = nombre;
        this.avatar = avatar;
        this.fortuna = 0;
        this.arrayPropiedades = new HashMap<>();
        this.arrayEdificios = new ArrayList<>();
        vueltas = 0;
        numVueltas=0;
        dadosLanzados=0;
        carcel = 0;
        dineroInvertido=0;
        dineroBoteInversiones=0;
        dineroAlquileresPagado=0;
        dineroSalida=0;
        dineroImpuestos=0;
        dineroAlquileresRecibido=0;
        vecesCarcel=0;
        ee = 'n';
    }
    public Jugador(String nombre){
        this.nombre = nombre;
        this.avatar = null;
        this.fortuna = 0;
        this.arrayPropiedades = new HashMap<>();
        this.arrayEdificios = new ArrayList<>();
        vueltas = 0;
        numVueltas=0;
        dadosLanzados=0;
        carcel = 0;
        dineroInvertido=0;
        dineroBoteInversiones=0;
        dineroAlquileresPagado=0;
        dineroSalida=0;
        dineroImpuestos=0;
        dineroAlquileresRecibido=0;
        vecesCarcel=0;
        ee = 'n';
    }

    //METODOS
    public void Aumento(double bonus){
        fortuna = fortuna + bonus;
    }
    public void Disminucion(double bonus){
        fortuna = fortuna - bonus;
        if (fortuna < 0){
            Juego.consola.consolaimprimir("BANCARROTA");
        }
    }
    public boolean pitufo(){
        if(ee=='n') {
            nombre = ANSICodes.ANSI_BLUE + nombre + ANSICodes.ANSI_RESET;
            ee = 'p';
            return true;
        }else {
            Juego.consola.consolaimprimir("Solo vale una mano de pintura");
            return false;
        }
    }
    public boolean simpson(){
        if(ee=='n') {
            nombre = ANSICodes.ANSI_YELLOW + nombre + ANSICodes.ANSI_RESET;
            ee = 's';
            return true;
        }else {
            Juego.consola.consolaimprimir("Solo vale una mano de pintura");
            return false;
        }
    }
    public boolean vendimia(){
        if(ee=='n') {
            nombre = ANSICodes.ANSI_PURPLE + nombre + ANSICodes.ANSI_RESET;
            ee = 'u';
            return true;
        }else {
            Juego.consola.consolaimprimir("Solo vale una mano de pintura");
            return false;
        }
    }
    public String toStringCom() {
        return  "Nombre: " + nombre + "\n" +
                "Avatar: " + avatar.getTipoavatar();
    }
    public String toStringJug(Casilla[] arrayCasillas) {
        StringBuilder resultado = new StringBuilder();
        resultado.append("ID: ").append(avatar.getLetra()).append("\n").append("Tipo: ").append(avatar.getTipoavatar()).append("\n").append("Casilla: ").append(arrayCasillas[avatar.getPosicion()].getNombre()).append("\n").append("Jugador: ").append(nombre);

        resultado.append("Edificios: [ ");

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

        resultado.append(" ]\n");

        return "{ " +resultado+ " }\n";
    }
    public String toStringJug(){
        StringBuilder resultado = new StringBuilder();
        resultado.append("Nombre: ").append(nombre).append("\n").append("Avatar: ").append(avatar.getLetra()).append("\n").append("Fortuna: ").append(String.format("%.2f", fortuna)).append("\n").append("Propiedades: [ ");

        for (Propiedad prop : arrayPropiedades.values()){

            resultado.append(prop.toStringHipotecada()).append(" ");

        }
        resultado.append("]\n");



        resultado.append("Edificios: [ ");

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

        resultado.append(" ]\n");

        return "{ " +resultado+ " }\n";
    }
    public String toStringStats(){
        String resultado="";
        //resultado+="\u001B[4mEstadisticas de: " + jugador.getNombre() +"\u001B[0m"+'\n';
        resultado+="\tDinero invertido:" + String.format("%.2f",getDineroInvertido()) + '\n';
        resultado+="\tPago tasas e impuestos: " + String.format("%.2f",getDineroImpuestos()) + '\n';
        resultado+="\tPago alquileres: " + String.format("%.2f",getDineroAlquileresPagado()) + '\n';
        resultado+="\tCobro alquileres: " + String.format("%.2f",getDineroAlquileresRecibido()) + '\n';
        resultado+="\tPasar por casilla de salida: " + String.format("%.2f",getDineroSalida()) + '\n';
        resultado+="\tPremios en inversiones y botes: " + String.format("%.2f",getDineroBoteInversiones()) + '\n';
        resultado+="\tVeces en la carcel: " + getVecesCarcel();
        return resultado;
    }
    public String propiedadesJugador(){
        StringBuilder res = new StringBuilder();

        for(Propiedad prop : arrayPropiedades.values()){
            if (!prop.getHipotecada()){
                res.append(prop.getNombre());
            }
        }
        return res.toString();
    }
    public boolean sepuedehipotecar(){
        for (Propiedad prop: arrayPropiedades.values()){
            if(prop.getHipotecada())
                return false;
        }
        return true;
    }
}

