package Juego.Cartas;

import Excepciones.AccionIlegal;
import Excepciones.ComandoDesconocido;
import Excepciones.ExsCasillas.ExsPropiedades.ExsSolares.VenderEdificada;
import Excepciones.ExsCasillas.ExsPropiedades.NoComprada;
import Excepciones.ExsCasillas.TipoCasillaInvalido;
import Excepciones.Inexistencias.InexCas;
import Juego.Juego;

public abstract class Carta {

    //ATRIBUTOS
    private String enunciado;
    //private String ee;
    protected final int indice;

    //CONSTRUCTORES
    public Carta(int indice, int tipo){
        double valorMedia= Juego.mediaglobal();
        this.indice=indice;
        if(tipo==0||tipo==1) {
            switch (tipo) {
                case 0:
                    if (indice >= 0 && indice < 10) {
                        switch (indice) {
                            case 0:
                                enunciado = "Paga "+String.format("%.2f",valorMedia/4)+"€ por un fin de semana en un balneario de 5 estrellas, aprovecha para pitufear un poco";
                                break;
                            case 1:
                                enunciado = "Te investigan por fraude de identidad. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar el dinero de la salida. Pueba a entrenar un poquito allí que te veo débil";
                                break;
                            case 2:
                                enunciado = "Colócate en la casilla de Salida y cobra los "+String.format("%.2f",valorMedia)+"€";
                                break;
                            case 3:
                                enunciado = "Tu compañía de Internet obtiene beneficios. Recibe los "+String.format("%.2f",valorMedia)+"€";
                                break;
                            case 4:
                                enunciado = "Paga "+String.format("%.2f",valorMedia/2)+"€ por invitar a todos tus amigos a un viaje a León.";
                                break;
                            case 5:
                                enunciado = "Devolución de Hacienda. Cobra "+String.format("%.2f",valorMedia/4)+"€.";
                                break;
                            case 6:
                                enunciado = "Retrocede hasta Solar1 para comprar antigüedades exóticas.";
                                break;
                            case 7:
                                enunciado = "Alquilas a tus compañeros una villa en Cannes durante una semana. Paga "+String.format("%.2f",valorMedia/10)+"€ a cada jugador. Puedes probar a recolectar uvas";
                                break;
                            case 8:
                                enunciado = "Recibe "+String.format("%.2f",valorMedia/2)+"€ de beneficios por alquilar los servicios de tu jet privado.";
                                break;
                            case 9:
                                enunciado = "Ve a Solar13 para disfrutar del San Fermín. Si pasas por la casilla de Salida, cobra los "+String.format("%.2f",valorMedia)+"€.";
                                break;
                        }
                    }else System.out.println("No existe esa carta");
                    break;
                case 1:
                    if (indice >= 0 && indice < 14) {
                        switch (indice) {
                            case 0:
                                enunciado = "Ve a TransporteII y coge un avión. Si pasas por la casilla de Salida, cobra "+String.format("%.2f",valorMedia)+"€.";
                                break;
                            case 1:
                                enunciado = "Decides hacer un viaje de placer. Avanza hasta Solar22, allí prueba a hablarte con Matt Groening";
                                break;
                            case 2:
                                enunciado = "Vendes tu billete de avión para Terrassa en una subasta por Internet, cobra "+String.format("%.2f",valorMedia/4)+"€.";
                                break;
                            case 3:
                                enunciado = "Ve a Solar17. Si pasas por la casilla de Salida, cobra los "+String.format("%.2f",valorMedia)+"€.";
                                break;
                            case 4:
                                enunciado = "Los acreedores te persiguen por impago. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar el dinero de la salida.";
                                break;
                            case 5:
                                enunciado = "¡Has ganado el bote de la lotería! Recibe "+String.format("%.2f",valorMedia/2)+"€.";
                                break;
                            case 6:
                                enunciado = "Paga "+String.format("%.2f",valorMedia*3/4)+"€ por la matrícula del colegio privado.";
                                break;
                            case 7:
                                enunciado = "El aumento del impuesto sobre bienes inmuebles afecta a todas tus propiedades. Paga "+String.format("%.2f",valorMedia/5)+"€ por casa, "+String.format("%.2f",valorMedia*0.575)+"€ por hotel, "+String.format("%.2f",valorMedia/10)+"€ por piscina y "+String.format("%.2f",valorMedia*0.375)+"€ por pista de deportes.";
                                break;
                            case 8:
                                enunciado = "Ve a Solar7. Si pasas por la casilla de Salida, cobra los "+String.format("%.2f",valorMedia)+"€.";
                                break;
                            case 9:
                                enunciado = "Has sido elegido presidente de la junta directiva. Paga a cada jugador "+String.format("%.2f",valorMedia/8)+"€";
                                break;
                            case 10:
                                enunciado="¡Hora punta de tráfico! Retrocede tres casillas.";
                                break;
                            case 11:
                                enunciado="Te multan por usar el móvil mientras conduces. Paga "+String.format("%.2f",valorMedia*0.075)+"€.";
                                break;
                            case 12:
                                enunciado="Beneficio por la venta de tus acciones. Recibe "+String.format("%.2f",valorMedia*0.075)+"€.";
                                break;
                            case 13:
                                enunciado="Avanza hasta la casilla de transporte más cercana. Si no tiene dueño, puedes comprársela a la banca. Si tiene dueño, paga al dueño el doble de la operación indicada.";
                                break;
                        }
                    }else System.out.println("No existe esa carta");
                    break;
            }
        }else System.out.println("Tipo no válido");
    }

    //GETTERS
    public String getEnunciado() {return enunciado;}

    //SETTERS

    //MÉTODOS
    public abstract void accion(Juego juego, int turno) throws AccionIlegal, NoComprada, VenderEdificada, TipoCasillaInvalido, InexCas, ComandoDesconocido;

}