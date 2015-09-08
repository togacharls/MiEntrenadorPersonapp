package togacharls.mientrenadorpersonapp.Activities;

import java.util.ArrayList;
import java.util.Calendar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Button;	
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.ContentValues;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
//import android.content.Intent;
import android.widget.TextView;
import android.hardware.SensorManager;
import android.view.WindowManager;
//import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.Display;				//Para obtener la resolución
import android.widget.LinearLayout;

import togacharls.mientrenadorpersonapp.Application.MiEntrenadorPersonapp;
import togacharls.mientrenadorpersonapp.Database.EjercicioRutina;
import togacharls.mientrenadorpersonapp.Sensors.Acelerometro;
import togacharls.mientrenadorpersonapp.Dialogs.DialogCarga;
import togacharls.mientrenadorpersonapp.Dialogs.DialogDescanso;
import togacharls.mientrenadorpersonapp.Dialogs.DialogEjercicio;
import togacharls.mientrenadorpersonapp.Dialogs.DialogManual;
import togacharls.mientrenadorpersonapp.Dialogs.DialogMusculos;
import togacharls.mientrenadorpersonapp.Dialogs.DialogRepeticiones;
import togacharls.mientrenadorpersonapp.Dialogs.DialogSonarRepeticiones;
import togacharls.mientrenadorpersonapp.Database.Ejercicio;
import togacharls.mientrenadorpersonapp.Listeners.ListenerCarga;
import togacharls.mientrenadorpersonapp.Listeners.ListenerDescanso;
import togacharls.mientrenadorpersonapp.Listeners.ListenerEjercicio;
import togacharls.mientrenadorpersonapp.Listeners.ListenerIntroduccionRepeticionesManual;
import togacharls.mientrenadorpersonapp.Listeners.ListenerMusculos;
import togacharls.mientrenadorpersonapp.Listeners.ListenerRepeticiones;
import togacharls.mientrenadorpersonapp.Listeners.ListenerSonarRepeticiones;
import togacharls.mientrenadorpersonapp.R;

public class SesionActivity extends AppCompatActivity implements ListenerRepeticiones, ListenerSonarRepeticiones,
		ListenerCarga, ListenerIntroduccionRepeticionesManual, ListenerMusculos, ListenerDescanso, ListenerEjercicio, OnClickListener {
	private ImageView imageView;
	private TextView repeticionesTextView;
	private TextView repeticionesAcelerometroTextView;
	//private Button guardar;
	private Button comenzarButton;
	private Button seleccionarCargaButton;
	private Button todosEjerciciosButton;
	private Button ejerciciosRutinaButton;
	//private String nombreFichero;
	//private boolean registro;

	private boolean finEjercicio, primero;
	private int dia, mes, repeticiones, año;
	private String diaSemana;
	private float carga;
	private Ejercicio ejercicio;
	private SoundPool sndPool;
	private Acelerometro acelerometro;
	
	private ArrayList<Ejercicio> ejercicios;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.repeticiones);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //La pantalla no se bloqueará
		ejercicios = new ArrayList();

		if(MiEntrenadorPersonapp.getTiempoSesion() == 0){
			MiEntrenadorPersonapp.setTiempoSesion(System.currentTimeMillis());
		}

		sndPool = new SoundPool(16, AudioManager.STREAM_MUSIC, 100);

		primero = false;
		Calendar calendario = Calendar.getInstance();

		switch(calendario.get(Calendar.DAY_OF_WEEK)){
			case 2:
				diaSemana = "Lunes";
				break;
			case 3:
				diaSemana = "Martes";
				break;
			case 4:
				diaSemana = "Miércoles";
				break;
			case 5:
				diaSemana = "Jueves";
				break;
			case 6:
				diaSemana = "Viernes";
				break;
			case 7:
				diaSemana = "Sábado";
				break;
			case 1:
				diaSemana = "Domingo";
				break;
		}
		mes = calendario.get(Calendar.MONTH) + 1;
		año = calendario.get(Calendar.YEAR);
		dia = calendario.get(Calendar.DAY_OF_MONTH);

		finEjercicio = false;

		imageView = (ImageView)this.findViewById(R.id.exercise);
		repeticionesTextView = (TextView) findViewById(R.id.textorepes);
		comenzarButton = (Button) findViewById(R.id.comenzar);
		seleccionarCargaButton = (Button)findViewById(R.id.kilos);
		todosEjerciciosButton = (Button)findViewById(R.id.ejstodos);
		ejerciciosRutinaButton = (Button)findViewById(R.id.ejsrutina);

		comenzarButton.setOnClickListener(this);
		ejerciciosRutinaButton.setOnClickListener(this);
		todosEjerciciosButton.setOnClickListener(this);
		seleccionarCargaButton.setOnClickListener(this);

		//	nombreFichero = "valoresAcelerometro.txt";
		//	registro = false;
	 /* Este era el botón para almacenar en un .txt los valores registrados por el
	  * acelerómetro
	   	guardar = (Button)this.findViewById(R.id.registrar);
		guardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	if(registro){
            		registrarVector();
            		registro = false;
            	}
            	else if(!registro && escribirFichero()){
            		registro = true;
            	}
            }
           });
    */

		obtenerEjerciciosRutina();
		repeticionesTextView.setText("");
		if(ejercicios != null){
			if(ejercicios.size() > 0){
				ejercicio = ejercicios.get(0);
				if(ejercicio.getFoto() != null){

					int resource = MiEntrenadorPersonapp.getContext().getResources().getIdentifier(ejercicio.getFoto(), "drawable", MiEntrenadorPersonapp.getContext().getPackageName());
					imageView.setImageResource(resource);
				}
			}

		}
		else{
			Toast.makeText(MiEntrenadorPersonapp.getContext(), "ERROR: No se han registrado repeticiones.", Toast.LENGTH_SHORT);
			cargarManual();
		}

		Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		int size = (int)(display.getWidth() * 0.5);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(size, size);
		imageView.setLayoutParams(layoutParams);

		acelerometro = new Acelerometro((SensorManager) getSystemService(Context.SENSOR_SERVICE));
	}

	@Override
	protected void onResume(){
		super.onResume();
		if(acelerometro!=null)
			if(acelerometro.getManager()!=null){
				acelerometro.iniciar();
				
			}
	}
	
	@Override
	protected void onPause(){
		if(acelerometro!=null){
			if(acelerometro.getManager()!= null){
				acelerometro.apagar();
			}
		}	
		if(repeticionesTextView != null){
			repeticionesTextView.setText("");
		}
		super.onPause();
	}	
	
	@Override
	protected void onStop(){
		if(acelerometro != null){
			if(acelerometro.getManager()!= null){
				acelerometro.apagar();
			}
		}	
		if(repeticionesTextView != null){
			repeticionesTextView.setText("");
		}
		super.onStop();
	}
	
	
	/*
	 * Obtiene los ejercicios definidos en la rutina para el día actual
	 * 
	 * */
	private void obtenerEjerciciosRutina(){
		ejercicios = MiEntrenadorPersonapp.listaEjerciciosSesionDia(diaSemana);
		if(ejercicios.isEmpty()){
			primero = true;
			Toast.makeText(MiEntrenadorPersonapp.getContext(), "No se ha encontrado ningún ejercicio registrado en la rutina para hoy " + diaSemana, Toast.LENGTH_SHORT).show();
		}
	}
	
	/*
	 * Hace que suene una alarma cada vez que se cuenta una repetición
	 * 
	 */
	private void sonarRepeticiones(){
		int repes = 0;
		if(MiEntrenadorPersonapp.sonidoActivo()){
			while(!finEjercicio){
				if(acelerometro.getRepeticiones() > repes){
					play(R.raw.beep); //TODO ver el método en la clase ActivityConActionbar de mi TFG.
					repes++;
				}
			}
		}
	}
	
	/*
	 * Cambia el texto del botón comenzarButton
	 * 
	 * */
	public void actualizarComenzar(int caso) {
			if(caso == 0 ){
				comenzarButton.setText("Finalizar serie");
			}
			else if(caso == 1){
				apagarSensor();
				comenzarButton.setText("Comenzar");
			}
			else if(caso == 2){
				if(acelerometro.tieneAcelerometro()){
					comenzarButton.setText("Apagar acelerómetro");
				}
				else{
					comenzarButton.setText("Finalizar serie");
				}
			}
	}	
	
	/*
	 * Apaga el acelerómetro
	 * 
	 * */
	private void apagarSensor(){
		if(acelerometro != null){
			if(acelerometro.getActivo()){
				repeticiones = acelerometro.getRepeticiones();
				if( repeticiones <= 0){
					cargarManual();
				}
				acelerometro.apagar();
				repeticionesTextView.setText("");
				repeticionesAcelerometroTextView.setText("");
				finEjercicio = true;
			}
		}		
	}
	
	/*
	 * Almacena los datos registrados en la base de datos
	 * 
	 * */
	private void guardarSerie(){
		//Se construye la fecha en el formato en el cual se registra en la BD
		String fecha = "";
		if(dia < 10){
			fecha += "0" + dia + ".";
		}
		else{
			fecha += dia + ".";
		}

		if(mes < 10){
			fecha += "0" + mes + ".";
		}
		else{
			fecha += mes + ".";
		}
		fecha += año;

		long result = MiEntrenadorPersonapp.registrarSerie(ejercicio.getNombre(), repeticiones, carga, fecha);

		if(result != -1){
			Toast.makeText(MiEntrenadorPersonapp.getContext(), "Serie almacenada correctamente", Toast.LENGTH_SHORT).show();
		}
		else{
			Toast.makeText(MiEntrenadorPersonapp.getContext(), "ERROR: No se pudo almacenar la serie", Toast.LENGTH_SHORT).show();
		}
	}

//-------------------------------//Implementación de las interfaces
	@Override
	public void repeticionesManual(int r) {
		repeticiones = r;
		actualizarComenzar(0);
	}


	@Override
	public void carga(float c) {
		carga = c;
	}

	
	/*
	 * Carga el diálogo de Sonar Repeticiones
	 * */
	@Override
	public void cargarSonarRepeticiones() {
		if(MiEntrenadorPersonapp.sonidoActivo()){
			final DialogSonarRepeticiones dsr = new DialogSonarRepeticiones(MiEntrenadorPersonapp.getContext(), this);
			dsr.show();
		}
		else{
			Toast.makeText(MiEntrenadorPersonapp.getContext(), "Sonido desactivado. Si desea activarlo, acceda a la sección de Configuración", Toast.LENGTH_SHORT).show();
			comenzarButton.setText("Comenzar");
		}
	}

	
	/*
	 * Hace que suene una alarma cada "tiempo" tiempo durante "repe" veces
	 */
	@Override
	public void sonarRepeticiones(final float tiempo, final int repe){
		if(tiempo > 0 && repe > 0){
			actualizarComenzar(0);
		
			Thread th = new Thread() {
				public void run() {
					try {
						Thread.sleep(10000);		//Se espera 10 segundos desde que se pulsa el botón.
						for(int i=0; i< repe; i++){
							play(R.raw.beep);
							Thread.sleep((long)tiempo*1000);
						}				
					}catch(Exception e){
					}finally{
						SesionActivity.this.runOnUiThread(new Runnable() {
							public void run() {
								cargarManual();
							}
						}); 
					}
				}
			};
			Toast.makeText(MiEntrenadorPersonapp.getContext(), "En 10 segundos comenzará la cuenta", Toast.LENGTH_SHORT).show();
			th.start();
		}
		else{
			final DialogRepeticiones dr = new DialogRepeticiones(MiEntrenadorPersonapp.getContext(), this);
			dr.show();
		}
	}	
	
	/*
	 * Muestra un Toast de error
	 * */
	@Override
	public void errorSonar() {
		Toast.makeText(MiEntrenadorPersonapp.getContext(), "Introduce un intervalo y un número de veces correcto", Toast.LENGTH_SHORT).show();
	}	
	
	/*
	 * Este método activa el acelerómetro y cuenta las repeticiones
	 * */
	@Override
	public void contarRepeticiones() {
		
		if(acelerometro == null){
    		acelerometro = new Acelerometro((SensorManager) getSystemService(Context.SENSOR_SERVICE));
			repeticionesAcelerometroTextView = (TextView) findViewById(R.id.repeticiones);
    	}
    	
		if(!acelerometro.tieneAcelerometro()){        				
				Toast.makeText(MiEntrenadorPersonapp.getContext(), "Su Smartphone o Tablet no cuenta con un acelerómetro", Toast.LENGTH_SHORT).show();
		}
		else{
			actualizarComenzar(2);
			repeticionesTextView.setText("Repeticiones: ");
		}
		
		if(acelerometro.getManager()!=null){
			if(!acelerometro.getActivo()){
	            Thread th = new Thread(){
	            	public void run(){
	            		sonarRepeticiones();   			
	            	}
	            };
	            finEjercicio = false;
	            th.start();
	            acelerometro.iniciar();
			}
			else{
				repeticionesAcelerometroTextView.setText("");
			}
			actualizarComenzar(2);
		}			
	}

	/*
	 * Carga el diálogo de introducción manual de repeticiones
	 * */
	@Override
	public void cargarManual() {
		final DialogManual dm = new DialogManual(this, this);
		dm.show();
	}

	/*
	 *Muestra un diálogo con los ejercicios de la rutina
	 * */
	@Override
	public void mostrarRutina() {
		//Lanzar Dialog con la rutina que toque hoy
	}

	/*
	 * Muestra un diálogo con los ejercicios del grupo muscular (como en rutina)
	 * */
	@Override
	public void mostrarEjGrupoMuscular(String musculo) {
		ArrayList<Ejercicio> ejerciciosMusculo = MiEntrenadorPersonapp.listaEjerciciosSesionMusculo(musculo);
        DialogEjercicio de = new DialogEjercicio(MiEntrenadorPersonapp.getContext(), this, ejerciciosMusculo);
        if(ejerciciosMusculo.isEmpty()){
        	Toast.makeText(MiEntrenadorPersonapp.getContext(), "No se han encontrado ejercicios para este grupo muscular", Toast.LENGTH_SHORT).show();
        }
        else{
        	de.show();
        }
	}

	/*
	 * Carga el diálogo que muestra el descanso
	 * */
	public void cargarDescanso(int tipo) {
		DialogDescanso dd = new DialogDescanso(MiEntrenadorPersonapp.getContext(), this, tipo);
		dd.show();
	}	
	
	
	/*
	 *  Cronometra el descanso y actualiza el TextView por cada segundo que pasa
	 * */
	public int descanso(int tipo){
		//Descanso entre series (0) o entre ejercicios (1)
		int descanso = 0;
		Toast.makeText(MiEntrenadorPersonapp.getContext(), "Es hora de descansar un poco...", Toast.LENGTH_LONG).show();
		if(tipo == 0){
			if(ejercicio != null){
				descanso = MiEntrenadorPersonapp.getDescansoSerie(ejercicio.getNombre(), diaSemana);
			}
		}
		else{
			if(ejercicio != null){
				descanso = MiEntrenadorPersonapp.getDescansoEjercicio(ejercicio.getNombre(), diaSemana);
			}
		}
		return descanso;
	}

	/*
	*
	* */
	public void finDescanso() {
		actualizarComenzar(1);
	}

	public void cambiarEjercicio(String nombreEj) {
		if(primero){
			primero = false;
		}
		else{
			cargarDescanso(1);
		}

		ejercicio = MiEntrenadorPersonapp.obtenerEjercicioSesion(nombreEj);

		if (ejercicio != null) {
			String path = "drawable/"+ejercicio.getFoto();
			int fuenteImagen = getResources().getIdentifier(path, null, getPackageName());
			Drawable imagen = getResources().getDrawable(fuenteImagen);
			imageView.setImageDrawable(imagen);
		}
	}

	/*
	*  Reproduce un sonido.
	* */
	private void play(int sound_id){
		final int sonido = sndPool.load(MiEntrenadorPersonapp.getContext(), sound_id, 2);
		sndPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
			@Override
			public void onLoadComplete(SoundPool soundpool, int sampleID, int status) {
				soundpool.play(sonido, 20, 20, 1, 0, 1f);
			}
		});
	}
	
//-----------------------------------------------------------------------------
	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.comenzar:
				if(comenzarButton.getText().toString().equals("Comenzar")){
					carga = 0;
					repeticiones = 0;
					if(ejercicio != null){
						final DialogRepeticiones dr = new DialogRepeticiones(MiEntrenadorPersonapp.getContext(), this);
						dr.show();
					}
					else{
						Toast.makeText(MiEntrenadorPersonapp.getContext(), "Seleccione un ejercicio", Toast.LENGTH_SHORT).show();
					}
				}
				else if(comenzarButton.getText().toString().equals("Apagar el acelerómetro")){
					apagarSensor();
					actualizarComenzar(0);
				}
				else{
					if(carga == 0){
						Toast.makeText(MiEntrenadorPersonapp.getContext(), "Introduzca manualmente la carga", Toast.LENGTH_SHORT).show();
					}
					else{
						guardarSerie();
						cargarDescanso(0);
					}
				}
				break;
				
			case R.id.kilos:
				if(comenzarButton.getText().toString().equals("Comenzar")){
					Toast.makeText(MiEntrenadorPersonapp.getContext(), "Primero realice el ejercicio y después introduzca la carga"
							, Toast.LENGTH_SHORT).show();
				}
				else{
					final DialogCarga dc = new DialogCarga(MiEntrenadorPersonapp.getContext(), this);
					dc.show();
				}
				break;				
				
			case R.id.ejsrutina:
				if(ejercicios.size() == 0){
					Toast.makeText(MiEntrenadorPersonapp.getContext(), "No hay definida ninguna rutina", Toast.LENGTH_SHORT).show();
				}
				else{
					if(comenzarButton.getText().equals("Finalizar serie")){
						Toast.makeText(MiEntrenadorPersonapp.getContext(), "Para dar por finalizada su serie, pulse el botón 'Finalizar serie'", Toast.LENGTH_SHORT);
					}					
					else{
						final DialogEjercicio de = new DialogEjercicio(MiEntrenadorPersonapp.getContext(), this, ejercicios);
						de.show();
					}
				}
				break;	
				
			case R.id.ejstodos:

				if(comenzarButton.getText().equals("Finalizar serie")){
					Toast.makeText(MiEntrenadorPersonapp.getContext(), "Para dar por finalizada su serie, pulse el botón" +
							" 'Finalizar serie'", Toast.LENGTH_SHORT);
				}
				else{
					final DialogMusculos dm = new DialogMusculos(this, this);
					dm.show();
				}
				break;				
		}
	}	
//---------------------------------------------------------------------

// FUNCIONES UTILIZADAS PARA REGISTRAR LOS DATOS DEL VECTOR DEL ACELERÓMETRO
/*	private boolean escribirFichero(){
		try{
			File directorio = new File(Environment.getExternalStorageDirectory() + "/MiEntrenadorPersonal");
			if(!directorio.isDirectory()){
				directorio.mkdir();
			}
			File fichero = new File(directorio, nombreFichero);
			OutputStreamWriter out;
			if(fichero.exists()){
				out = new OutputStreamWriter(new FileOutputStream(fichero, true));
			}
			else{
				out = new OutputStreamWriter(new FileOutputStream(fichero));
				Toast.makeText(getApplicationContext(), "Archivo creado", Toast.LENGTH_SHORT).show();
			}
			out.flush();
			out.close();
			return true;
		}catch(Exception e){
			Toast.makeText(getApplicationContext(), "Ocurrió un error durante la apertura o creación el archivo", Toast.LENGTH_SHORT).show();
			return false;
		}
	}
	
	
	private boolean registrarVector(){
		try{			
			File directorio = new File(Environment.getExternalStorageDirectory() + "/MiEntrenadorPersonal");
			File fichero = new File(directorio, nombreFichero);
			OutputStreamWriter out;
			if(fichero.exists()){
				out = new OutputStreamWriter(new FileOutputStream(fichero, true));
				out.write("\nDatos:" + Integer.valueOf(acelerometro.vector.size()).toString() +"\n");
				for(int i =0; i< acelerometro.vector.size();i++ ){
					out.write(Integer.valueOf(acelerometro.vector.get(i)).toString() + "\n");
				}
				out.write("Quieto: "+Float.valueOf(acelerometro.quieto) + "\n");
				out.write("Ruido: "+Float.valueOf(acelerometro.ruido) + "\n");
				out.write("Constante: "+Integer.valueOf((int)(acelerometro.frecuencia*0.75)) + "\n");
				out.write("Quieto + Ruido: "+Float.valueOf(acelerometro.quieto + acelerometro.ruido) + "\n");
				
				out.write("Tamaño Vector Valores:" + Integer.valueOf(acelerometro.vectorValores.size()).toString() +"\n");
				for(int i =0; i< acelerometro.vectorValores.size();i++ ){
					out.write(Float.valueOf(acelerometro.vectorValores.get(i)).toString() + "\n");
				}				
				
				out.write("\n");
				out.flush();
				out.close();
				Toast.makeText(getApplicationContext(), "Datos del vector almacenados", Toast.LENGTH_SHORT).show();
				return true;
			}
			else{
				Toast.makeText(getApplicationContext(), "Ocurrió un error durante la escritura en el archivo", Toast.LENGTH_SHORT).show();
				return false;				
			}
		}catch(Exception e){
			Toast.makeText(getApplicationContext(), "Ocurrió un error durante la escritura en el archivo", Toast.LENGTH_SHORT).show();
			return false;
		}
	}*/
}