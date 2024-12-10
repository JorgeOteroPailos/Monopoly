package Juego;

import Excepciones.*;
import Excepciones.ExsCasillas.ExsPropiedades.ExsSolares.EdificarHipotecada;
import Excepciones.ExsCasillas.ExsPropiedades.ExsSolares.SinPermisoEdificar;
import Excepciones.ExsCasillas.ExsPropiedades.ExsSolares.SinPermisoSolar;
import Excepciones.ExsCasillas.ExsPropiedades.ExsSolares.VenderEdificada;
import Excepciones.ExsCasillas.ExsPropiedades.NoComprada;
import Excepciones.ExsCasillas.TipoCasillaInvalido;
import Excepciones.ExsCasillas.ExsPropiedades.ExsSolares.ExsEdificios.ExEdificios;
import Excepciones.ExsGrupos.SinPermisoGrupo;
import Excepciones.ExsJugadores.ExsAvatares.PosicionIncorrecta;
import Excepciones.ExsJugadores.FortunaInsuficiente;
import Excepciones.ExsTratos.NoPertenece;
import Excepciones.Inexistencias.*;
import Jugadores.Jugador;

public interface Comando {
    void help();
    void salirCarcelPagando() throws FortunaInsuficiente, PosicionIncorrecta;
    void cambiarMovimiento(String tipoMovimiento) throws AccionIlegal, ComandoDesconocido;
    void acabarTurno() throws AccionIlegal;
    void acabarPartida();
    void verJugadorturno();
    void verTablero();
    void listarAvatares();
    void listarJugadores();
    void listarEnventa() throws InexGrup;
    void listarEdificios() throws InexGrup;
    void listarEdificiosGrupo(String colorgrupo) throws InexGrup;
    void comprar(String nombreCasilla) throws FortunaInsuficiente, AccionIlegal, InexCas;
    void edificar(String tipoEdificio) throws FortunaInsuficiente, AccionIlegal, ExEdificios, NoComprada, TipoCasillaInvalido, EdificarHipotecada, SinPermisoEdificar, SinPermisoSolar, SinPermisoGrupo;
    void hipotecar(String nombreCasilla) throws AccionIlegal, InexCas, NoComprada, VenderEdificada, TipoCasillaInvalido;
    void deshipotecar(String nombreCasilla) throws AccionIlegal, FortunaInsuficiente, InexCas, NoComprada, TipoCasillaInvalido;
    void estadisticas();
    void estadisticas(String nombreJugador) throws InexJug;
    void vender(String tipoEdificio, String nombreCasilla, int numVenta) throws AccionIlegal, InexCas, TipoCasillaInvalido, NoComprada;
    void lanzarDados(String[] splitcom) throws AccionIlegal, NoComprada, VenderEdificada, TipoCasillaInvalido, InexCas, ComandoDesconocido;
    void describircasilla(String nombreCasilla) throws InexCas;
    void describirjugador(String nombreJugador) throws InexJug;
    void describiravatar(char letraAvatar) throws InexJug, InexAva;
    void bancarrota(Jugador muerto, Jugador beneficiado);
    void kill(String nombrenuevopropietario) throws InexJug;
    void proponerTrato(String comando) throws ComandoDesconocido, InexJug, AccionIlegal;
    void eliminarTrato(String[] splitcom) throws NoPertenece, InexTrat;
    void aceptarTrato(String[] splitcom) throws InexTrat, FortunaInsuficiente, NoPertenece;
    void listarTratos();

}
