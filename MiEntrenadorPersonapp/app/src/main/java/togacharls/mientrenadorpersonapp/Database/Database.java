package togacharls.mientrenadorpersonapp.Database;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Database extends SQLiteOpenHelper {

	private ContentValues registro;
	private Cursor fila;

	public Database(Context c) {
		super(c, "Database.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase bd) {

		bd.execSQL("CREATE TABLE IF NOT EXISTS Usuario (" +
				"nombre VARCHAR(20), altura INT, peso INT, fechaNacimiento VARCHAR(10), sexo VARCHAR(1), mail VARCHAR(30), " +
				"sonido VARCHAR, PRIMARY KEY (nombre))");

		bd.execSQL("CREATE TABLE IF NOT EXISTS Ejercicio(" +
				"nombre VARCHAR(50), musculoPrincipal VARCHAR(15), " +
				"path VARCHAR(50), PRIMARY KEY (nombre))");

		bd.execSQL("CREATE TABLE IF NOT EXISTS Serie(" +
				"nombre VARCHAR(50), repeticiones INT, peso FLOAT, dia VARCHAR(10), nDiaria INT, PRIMARY KEY(nombre, dia, nDiaria))");

		bd.execSQL("CREATE TABLE IF NOT EXISTS EjercicioRutina(" +
				"nombre VARCHAR(50), series INT, repeticiones VARCHAR(50), descansoEntreSeries INT, descansoEntreEjercicios INT," +
				"dia VARCHAR(10), PRIMARY KEY(nombre, dia))");

		//Bíceps
		for (int i = 1; i < 42; i++) {
			registro = new ContentValues();
			registro.put("nombre", "Bíceps " + Integer.valueOf(i).toString());
			registro.put("musculoPrincipal", "Bíceps");
			registro.put("path", "bic" + Integer.valueOf(i).toString());
			bd.insert("Ejercicio", null, registro);
		}

		//Tríceps
		for (int i = 1; i < 42; i++) {
			registro = new ContentValues();
			registro.put("nombre", "Tríceps " + Integer.valueOf(i).toString());
			registro.put("musculoPrincipal", "Tríceps");
			registro.put("path", "tric" + Integer.valueOf(i).toString());
			bd.insert("Ejercicio", null, registro);
		}

		//Pectoral
		for (int i = 1; i < 50; i++) {
			registro = new ContentValues();
			registro.put("nombre", "Pectotal " + Integer.valueOf(i).toString());
			registro.put("musculoPrincipal", "Pectoral");
			registro.put("path", "pec" + Integer.valueOf(i).toString());
			bd.insert("Ejercicio", null, registro);
		}
		//Antebrazo
		for (int i = 1; i < 10; i++) {
			registro = new ContentValues();
			registro.put("nombre", "Antebrazo " + Integer.valueOf(i).toString());
			registro.put("musculoPrincipal", "Antebrazo");
			registro.put("path", "ant" + Integer.valueOf(i).toString());
			bd.insert("Ejercicio", null, registro);
		}
		//Hombros
		for (int i = 1; i < 61; i++) {
			registro = new ContentValues();
			registro.put("nombre", "Hombros" + Integer.valueOf(i).toString());
			registro.put("musculoPrincipal", "Hombros");
			registro.put("path", "hom" + Integer.valueOf(i).toString());
			bd.insert("Ejercicio", null, registro);
		}

		//Gemelos
		for (int i = 1; i < 16; i++) {
			registro = new ContentValues();
			registro.put("nombre", "Gemelos" + Integer.valueOf(i).toString());
			registro.put("musculoPrincipal", "Gemelos");
			registro.put("path", "gem" + Integer.valueOf(i).toString());
			bd.insert("Ejercicio", null, registro);
		}

		//Manguito Rotador
		for (int i = 1; i < 25; i++) {
			registro = new ContentValues();
			registro.put("nombre", "Manguito" + Integer.valueOf(i).toString());
			registro.put("musculoPrincipal", "Manguito Rotador");
			registro.put("path", "man" + Integer.valueOf(i).toString());
			bd.insert("Ejercicio", null, registro);
		}

		//Abdomen
		for (int i = 1; i < 84; i++) {
			registro = new ContentValues();
			registro.put("nombre", "Abdomen" + Integer.valueOf(i).toString());
			registro.put("musculoPrincipal", "Abdomen");
			registro.put("path", "abs" + Integer.valueOf(i).toString());
			bd.insert("Ejercicio", null, registro);
		}

		//Glúteos
		for (int i = 1; i < 20; i++) {
			registro = new ContentValues();
			registro.put("nombre", "Glúteos" + Integer.valueOf(i).toString());
			registro.put("musculoPrincipal", "Glúteos");
			registro.put("path", "glu" + Integer.valueOf(i).toString());
			bd.insert("Ejercicio", null, registro);
		}

		//Dorsales
		for (int i = 1; i < 48; i++) {
			registro = new ContentValues();
			registro.put("nombre", "Dorsales" + Integer.valueOf(i).toString());
			registro.put("musculoPrincipal", "Dorsales");
			registro.put("path", "dor" + Integer.valueOf(i).toString());
			bd.insert("Ejercicio", null, registro);
		}

		//Lumbares
		for (int i = 1; i < 10; i++) {
			registro = new ContentValues();
			registro.put("nombre", "Lumbares" + Integer.valueOf(i).toString());
			registro.put("musculoPrincipal", "Lumbares");
			registro.put("path", "lum" + Integer.valueOf(i).toString());
			bd.insert("Ejercicio", null, registro);
		}

		//Trapecio
		for (int i = 1; i < 8; i++) {
			registro = new ContentValues();
			registro.put("nombre", "Trapecio " + Integer.valueOf(i).toString());
			registro.put("musculoPrincipal", "Trapecio");
			registro.put("path", "trap" + Integer.valueOf(i).toString());
			bd.insert("Ejercicio", null, registro);
		}

		//Piernas
		for (int i = 1; i < 36; i++) {
			registro = new ContentValues();
			registro.put("nombre", "Piernas " + Integer.valueOf(i).toString());
			registro.put("musculoPrincipal", "Piernas");
			registro.put("path", "leg" + Integer.valueOf(i).toString());
			bd.insert("Ejercicio", null, registro);
		}
	}


	@Override
	public void onUpgrade(SQLiteDatabase bd, int oldVersion, int newVersion) {
		if (newVersion > oldVersion) {
		}
	}

	/*
	*  Devuelve los diferentes músculos
	* */
	public ArrayList<String> listaMusculos() {
		ArrayList<String> musculos = new ArrayList<>();
		musculos.add("Abdmen");
		musculos.add("Antebrazo");
		musculos.add("Bíceps");
		musculos.add("Dorsales");
		musculos.add("Glúteos");
		musculos.add("Hombros");
		musculos.add("Lumbares");
		musculos.add("Manguito Rotador");
		musculos.add("Pectoral");
		musculos.add("Piernas");
		musculos.add("Tríceps");
		return musculos;
	}


	/*
	*  Devuelve los ejercicios de la rutina registrados en la BD para un día concreto.
	*/
	public ArrayList<EjercicioRutina> listaEjerciciosDia(String dia) {

		ArrayList<EjercicioRutina> ejs = new ArrayList();

		fila = getReadableDatabase().rawQuery("" +
				"SELECT Ejercicio.nombre, path, series, repeticiones, descansoEntreSeries, " +
				"descansoEntreEjercicios " +
				"FROM EjercicioRutina " +
				"JOIN Ejercicio " +
				"ON Ejercicio.nombre = EjercicioRutina.nombre " +
				"WHERE dia = '" + dia + "'", null);

		if (fila.moveToFirst()) {
			do {
				EjercicioRutina ej = new EjercicioRutina(fila.getString(0), dia, fila.getString(1), fila.getInt(2), fila.getInt(3), fila.getInt(4), fila.getInt(5));
				ejs.add(ej);
			} while (fila.moveToNext());
		}
		return ejs;
	}

	/*
	* Devuelve los ejercicios en función del músculo
	*/
	public ArrayList<Ejercicio> listaEjerciciosMusculo(String musculo) {
		ArrayList<Ejercicio> ejs = new ArrayList();
		//TODO poner aquí la consulta
		fila = getReadableDatabase().rawQuery(
				"SELECT nombre, path" +
						" FROM Ejercicio" +
						" WHERE musculoPrincipal='" + musculo + "'", null);
		if (fila.moveToFirst()) {
			do {
				ejs.add(new Ejercicio(fila.getString(0), musculo, fila.getString(1)));
			} while (fila.moveToNext());
		}
		return ejs;
	}

	/*
	* Devuelve la lista de ejercicios registrados en la rutina para un día concreto para ser utilizada durante la sesión (Utilizando la clase Ejercicio en lugar de EjercicioRutina)
	* */
	public ArrayList<Ejercicio> listaEjerciciosSesionDia(String dia){
		ArrayList<Ejercicio> ejs = new ArrayList();
		fila = getReadableDatabase().rawQuery(
				"SELECT Ejercicio.nombre, Ejercicio.path, Ejercicio.musculoPrincipal " +
						"FROM Ejercicio " +
						"JOIN EjercicioRutina " +
						"ON EjercicioRutina.nombre = Ejercicio.nombre " +
						"WHERE EjercicioRutina.dia ='"+ dia +"'", null);
		if(fila.moveToFirst()){
			do{
				ejs.add(new Ejercicio(fila.getString(0), fila.getString(2), fila.getString(1)));
			} while (fila.moveToNext());
		}
		return ejs;
	}


	/*
	* Devuelve la lista de ejercicios en función de un músculo para ser utilizada durante la sesión (Utilizando la clase Ejercicio en lugar de EjercicioRutina)
	* */
	public ArrayList<Ejercicio> listaEjerciciosSesionMusculo(String musculo){
		ArrayList<Ejercicio> ejs = new ArrayList();
		fila = getReadableDatabase().rawQuery(
				"SELECT nombre, path, musculoPrincipal" +
						" FROM Ejercicio" +
						" WHERE musculoPrincipal='"+ musculo +"'" , null);
		if(fila.moveToFirst()){
			do{
				ejs.add(new Ejercicio(fila.getString(0), fila.getString(2), fila.getString(1)));
			} while (fila.moveToNext());
		}
		return ejs;
	}

	/*
	* Devuelve un ejercicio a partir de su nombre (Ejercicio, NO EjercicioRutina)
	* */
	public Ejercicio obtenerEjercicioSesion(String nombre){
		Ejercicio ejercicio = null;
		fila = getReadableDatabase().rawQuery("SELECT nombre, path, musculoPrincipal " +
				"FROM Ejercicio " +
				"WHERE nombre='"+ nombre +"'",null);
		if (fila.moveToFirst()) {
			ejercicio = new Ejercicio(fila.getString(0), fila.getString(2), fila.getString(1));
		}
		return ejercicio;
	}

	/*
	*  Se eliminan los ejercicios definidos en la rutina para un día concreto
	* */
	public void eliminarEjercicios(String dia) {
		getWritableDatabase().delete("EjercicioRutina", "dia=" + "'" + dia + "'", null);
	}

	/*
	* Se añade una listView de ejercicios para un día concreto
	* */
	public void añadirEjercicios(String dia, ArrayList<EjercicioRutina> ejs) {
		for (EjercicioRutina ej : ejs) {
			registro = new ContentValues();
			registro.put("nombre", ej.getNombre());
			registro.put("series", ej.getSeries());
			registro.put("repeticiones", ej.getRepeticiones());
			registro.put("descansoEntreSeries", ej.getDescansoSeries());
			registro.put("descansoEntreEjercicios", ej.getDescansoEjercicio());
			registro.put("dia", dia);
			getWritableDatabase().insert("EjercicioRutina", null, registro);
		}
	}

	/*
	* Devuelve la altura y el peso registrados en el perfil
	* */
	public Pair<Integer, Integer> pesoAltura() {
		Pair<Integer, Integer> pesoAltura;
		fila = getReadableDatabase().rawQuery(
				"SELECT peso, altura FROM Usuario", null);
		if (fila.moveToFirst()) {
			pesoAltura = new Pair(6, 1);
		} else pesoAltura = new Pair(-1, -1);
		return pesoAltura;
	}

	/*
	*  Devuelve la información registrada en el perfil por medio de un Hashmap
 	*/
	public HashMap<String, String> obtenerPerfil() {

		fila = getReadableDatabase().rawQuery("SELECT nombre, fechaNacimiento, peso, altura, sexo, mail, sonido FROM Usuario", null);
		HashMap<String, String> datos = new HashMap();

		if (fila.moveToFirst()) {
			datos.put("Nombre", fila.getString(0));
			datos.put("Fecha de Nacimiento", fila.getString(1));
			datos.put("Peso", fila.getString(2));
			datos.put("Altura", fila.getString(3));
			datos.put("Sexo", fila.getString(4));
			datos.put("Correo", fila.getString(5));
			datos.put("Sonido", fila.getString(6));
		}
		return datos;
	}

	/*
	*  Devuelve una lista con las series que se realizaron de un ejercicio determinado un día concreto
	* */
	public ArrayList<Serie> obtenerSeries(String ejercicio, String fecha){
		ArrayList<Serie> series = new ArrayList();

		fila = getReadableDatabase().rawQuery(
				"SELECT nDiaria, repeticiones, peso " +
						"FROM Serie " +
						"WHERE nombre='" + ejercicio + "' AND dia='" + fecha + "' "+
						"ORDER BY nDiaria", null);

		if(fila.moveToFirst()){
			do{
				series.add(new Serie(fila.getInt(0), fila.getFloat(2), fila.getInt(1)));
			}while (fila.moveToNext());
		}

		return series;
	}

	/*
	* Devuelve el email registrado en el perfil
	* */
	public String obtenerEmail(){
		String email = "";
		fila = getReadableDatabase().rawQuery( "SELECT mail FROM Usuario", null);
		if(fila.moveToFirst()){
			email = fila.getString(0);
		}
		return email;
	}


	/*
	* Devuelve los ejercicios de los que hay series registradas
	* */
	public ArrayList<String> obtenerEjerciciosSeries(String fecha){
		ArrayList<String> ejercicios = new ArrayList();

		fila = getReadableDatabase().rawQuery(
				"SELECT nombre FROM Serie " +
						"WHERE dia ='" + fecha + "' GROUP BY nombre", null);
		if(fila.moveToFirst()){
			do {
				ejercicios.add(fila.getString(0));
			} while(fila.moveToNext());
		}

		return ejercicios;
	}

	/*
    * Registra datos en el perfil del usuario
    * */
	public void registrarPerfil(HashMap<String, String> perfil) {
		registro = new ContentValues();
		registro.put("nombre", perfil.get("Nombre"));
		registro.put("altura", perfil.get("Altura"));
		registro.put("peso", perfil.get("Peso"));
		registro.put("fechaNacimiento", perfil.get("Fecha de Nacimiento"));
		registro.put("sexo", perfil.get("Sexo"));
		registro.put("mail", perfil.get("Correo"));
		registro.put("sonido", perfil.get("Sonido"));
		getWritableDatabase().insert("Usuario", null, registro);
	}

	/*
	* Registra los datos de una serie en la BD
	* */
	public long registrarSerie(String ejercicio, int repeticiones, float carga, String fecha){

		int nDiaria = 0;

		//Se comprueba si se han realizado repeticiones de este mismo ejercicio ese día y se coge únicamente nDiaria mayor de los registrados.
		fila = getReadableDatabase().rawQuery(
				"SELECT nDiaria FROM Serie " +
						"WHERE dia='" + fecha + "' " +
						"AND nombre='" + ejercicio + "' " +
						"ORDER BY nDiaria DESC LIMIT 1", null);

		if(fila.moveToFirst()){
			nDiaria = fila.getInt(0) + 1;
		}

		ContentValues registro = new ContentValues();
		registro.put("nombre", ejercicio);
		registro.put("dia", fecha);
		registro.put("repeticiones", repeticiones);
		registro.put("peso", carga);
		registro.put("nDiaria", nDiaria);

		return getWritableDatabase().insert("Serie", null, registro);
	}

	/*
    * Actualiza los datos del perfil del usuario
    * */
	public void actualizarPerfil(HashMap<String, String> perfil) {
		registro = new ContentValues();
		registro.put("nombre", perfil.get("Nombre"));
		registro.put("altura", perfil.get("Altura"));
		registro.put("peso", perfil.get("Peso"));
		registro.put("fechaNacimiento", perfil.get("Fecha de Nacimiento"));
		registro.put("sexo", perfil.get("Sexo"));
		registro.put("mail", perfil.get("Correo"));
		registro.put("sonido", perfil.get("Sonido"));
		getWritableDatabase().update("Usuario", registro, "", null);
	}

	/*
	* Devuelve True si el sonido está activo. False en caso contrario.
	* */
	public boolean sonidoActivo() {
		fila = getReadableDatabase().rawQuery("SELECT sonido FROM Usuario", null);
		if (fila.moveToFirst()) {
			if (fila.getString(0).equals("S")) {
				return true;
			}
		}
		return false;
	}

	/*
	* Devuelve el tiempo de descanso que se ha definido en la rutina tras finalizar un ejercicio.
	* */
	public int getDescansoEjercicio(String ejercicio, String diaSemana){
		int descanso = 0;
		fila = getReadableDatabase().rawQuery(
				"SELECT descansoEntreEjercicios FROM EjercicioRutina " +
						"WHERE dia = '" + diaSemana +"' AND nombre ='"+
						ejercicio + "'", null);
		if (fila.moveToFirst()) {
			descanso = fila.getInt(0);
		}
		return descanso;
	}

	/*
	* Devuelve el tiempo de descanso que se ha definido en la rutina tras finalizar una serie
	* */
	public int getDescansoSerie(String ejercicio, String diaSemana){
		int descanso = 0;
		fila = getReadableDatabase().rawQuery(
				"SELECT descansoEntreSeries FROM EjercicioRutina " +
						"WHERE dia = '" + diaSemana +"' AND nombre ='"+
						ejercicio + "'", null);
		if (fila.moveToFirst()) {
			descanso = fila.getInt(0);
		}
		return descanso;
	}


	/*
	* Borra el registro definido por el usuario de la base de datos
	* */
	public void borrarRegistro(){
		getWritableDatabase().delete("EjercicioRutina", "", null);
		getWritableDatabase().delete("Serie", "", null);
		getWritableDatabase().delete("Usuario", "", null);
	}

	/*
	* Devuelve True si ya se ha creado un perfil en la aplicación. False en caso contrario.
	* */
	public boolean perfilCreado(){
		fila = getReadableDatabase().rawQuery("SELECT * FROM Usuario",null);
		if(fila.moveToFirst()){
			return true;
		}
		return false;
	}

	/*
	* Determina si la alicación tendrá o no sonido.
	* */
	public void actualizarSonido(String s){
		registro = new ContentValues();
		registro.put("sonido", s);
		getWritableDatabase().update("Usuario", registro, "" , null);
	}
}