package Juego;
import java.util.Scanner;

public final class ConsolaNormal implements Consola {
    Scanner escaner;
    public ConsolaNormal(){
        escaner=new Scanner(System.in);
    }
    @Override
    public void consolaimprimir(String mensaje) {
        System.out.println(mensaje);
    }

    @Override
    public String consolaleer(String descripcion) {

        System.out.println(descripcion);
        return escaner.nextLine();
    }
}
