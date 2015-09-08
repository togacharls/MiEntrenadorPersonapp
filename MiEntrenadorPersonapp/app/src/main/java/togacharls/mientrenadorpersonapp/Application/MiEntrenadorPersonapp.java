package togacharls.mientrenadorpersonapp.Application;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

import togacharls.mientrenadorpersonapp.Database.Database;
import togacharls.mientrenadorpersonapp.Database.Ejercicio;
import togacharls.mientrenadorpersonapp.Database.EjercicioRutina;
import togacharls.mientrenadorpersonapp.Database.Serie;

/**
 * Created by Carlos on 02/09/2015.
 */
public class MiEntrenadorPersonapp extends Application {
    private static Context context;
    private static Database db;
    private static long tiempoSesion;

    @Override
    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();
        db = new Database(context);
        tiempoSesion = 0;
    }

    public static Context getContext() {
        return context;
    }

    public static long getTiempoSesion() {
        return tiempoSesion;
    }

    public static void setTiempoSesion(long tiempoSesion) {
        MiEntrenadorPersonapp.tiempoSesion = tiempoSesion;
    }

  /*
  * Devuelve una lista con todos los ejercicios definidos en la rutina para un día concreto.
  * */
    public static ArrayList<EjercicioRutina> listaEjerciciosDia(String dia){
        return db.listaEjerciciosDia(dia);
    }

    /*
    * Elimina los ejercicios de un día de la rutina
    * */
    public static void eliminarEjercicios(String dia){
        db.eliminarEjercicios(dia);
    }

    /*
    *  Añade una listView de ejercicios para un día concreto a la rutina
    * */
    public static void añadirEjercicios(String dia, ArrayList<EjercicioRutina> ejs){
        db.añadirEjercicios(dia, ejs);
    }

    /*
    * Devuelve el listado con los diferentes músculos registrados en la BD
    * */
    public static ArrayList<String> listaMusculos(){
       return db.listaMusculos();
    }

    public static ArrayList<Ejercicio> listaEjerciciosMusculo(String musculo){
        return db.listaEjerciciosMusculo(musculo);
    }

    /*
    * Devuelve la altura y el peso registrados en el perfil
    * */
    public static Pair<Integer, Integer> pesoAltura(){
        return db.pesoAltura();
    }

    /*
    * Devuelve la información registrada en el perfil por medio de un Hashmap
    * */
    public static HashMap<String, String> obtenerPerfil(){
        return db.obtenerPerfil();
    }

    /*
    * Registra datos en el perfil del usuario
    * */
    public static void registrarPerfil(HashMap<String, String> perfil){
        db.registrarPerfil(perfil);
    }

    /*
    * Actualiza los datos del perfil del usuario
    * */
    public static void actualizarPerfil(HashMap<String, String> perfil){
        db.actualizarPerfil(perfil);
    }

    /*
    * Devuelve True si el sonido está activo. False en caso contrario.
    */
    public static boolean sonidoActivo(){
       return db.sonidoActivo();
    }

    /*
    * Borra el registro definido por el usuario de la base de datos
    * */
    public static void borrarRegistro(){
        db.borrarRegistro();
    }

    /*
    * Devuelve True si ya se ha creado un perfil en la aplicación. False en caso contrario.
    * */
    public static boolean perfilCreado(){
        return db.perfilCreado();
    }

    /*
    * Determina si la alicación tendrá o no sonido
    * */
    public static void actualizarSonido(String s){
        db.actualizarSonido(s);
    }

    /*
    * Devuelve el tiempo de descanso que se ha definido en la rutina tras finalizar un ejercicio.
    * */
    public static int getDescansoEjercicio(String ejercicio, String diaSemana){
        return db.getDescansoEjercicio(ejercicio, diaSemana);
    }

    /*
    * Devuelve el tiempo de descanso que se ha definido en la rutina tras finalizar una serie.
    * */
    public static int getDescansoSerie(String ejercicio, String diaSemana){
        return db.getDescansoSerie(ejercicio, diaSemana);
    }

    /*
    * Devuelve la lista de ejercicios registrados en la rutina para un día concreto para ser utilizada durante la sesión (Utilizando la clase Ejercicio en lugar de EjercicioRutina)
    * */
    public static ArrayList<Ejercicio> listaEjerciciosSesionDia(String dia){
        return db.listaEjerciciosSesionDia(dia);
    }

    /*
    * Devuelve la lista de ejercicios en función de un músculo para ser utilizada durante la sesión (Utilizando la clase Ejercicio en lugar de EjercicioRutina)
    * */
    public static ArrayList<Ejercicio> listaEjerciciosSesionMusculo(String musculo){
        return db.listaEjerciciosSesionMusculo(musculo);
    }

    /*
    * Devuelve un ejercicio a partir de su nombre (Ejercicio, NO EjercicioRutina)
    * */
    public static Ejercicio obtenerEjercicioSesion(String nombre){
        return db.obtenerEjercicioSesion(nombre);
    }

    /*
    * Registra los datos de una serie en la BD
    * */
    public static long registrarSerie(String ejercicio, int repeticiones, float carga, String fecha){
        return db.registrarSerie(ejercicio, repeticiones, carga, fecha);
    }

    /*
    * Devuelve una lista con las series que se realizaron de un ejercicio determinado un día concreto
    * */
    public static ArrayList<Serie> obtenerSeries(String ejercicio, String fecha){
        return db.obtenerSeries(ejercicio, fecha);
    }

    /*
    * Devuelve el email registrado en el perfil
    * */
    public static String obtenerEmail(){
        return db.obtenerEmail();
    }

    /*
    * Devuelve los ejercicios de los que hay series registradas
    * */
    public static ArrayList<String> obtenerEjerciciosSeries(String fecha){
        return db.obtenerEjerciciosSeries(fecha);
    }
}
