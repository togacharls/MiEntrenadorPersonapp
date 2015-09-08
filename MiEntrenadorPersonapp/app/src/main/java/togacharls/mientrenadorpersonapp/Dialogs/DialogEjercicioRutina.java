package togacharls.mientrenadorpersonapp.Dialogs;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.EditText;

import togacharls.mientrenadorpersonapp.Application.MiEntrenadorPersonapp;
import togacharls.mientrenadorpersonapp.Database.EjercicioRutina;
import togacharls.mientrenadorpersonapp.Listeners.ListenerEjercicioRutina;
import togacharls.mientrenadorpersonapp.R;

public class DialogEjercicioRutina extends Dialogo implements OnClickListener{
	private ImageView imageViewFoto;
	private Button buttonSalir;
	private Button buttonGuardar;
	private Button buttonSiguiente;
	private Button buttonAnterior;

	private int posicion;
	private ArrayList<EjercicioRutina> ejercicios;
	private ArrayList<EjercicioRutina> seleccionados;
	private EjercicioRutina ejercicio;
	private boolean seleccionado;

	private ListenerEjercicioRutina listener;
	private EditText series, repeticiones, descansoSeries, descansoEjercicios;
	
	public DialogEjercicioRutina(Context context, ListenerEjercicioRutina l,
			ArrayList<EjercicioRutina> ejs, ArrayList<EjercicioRutina> selected ){

		super(context, R.layout.dialog_ejercicio_rutina);
		ejercicios = new ArrayList<EjercicioRutina>();		
		for(EjercicioRutina ej: ejs){
			ejercicios.add(ej);
			for(EjercicioRutina sej: selected){
				if(sej.getNombre().equals(ej.getNombre())){
					ejercicios.remove(ejercicios.size()-1);
					ejercicios.add(sej);
				}
			}
		}
		seleccionados = new ArrayList<EjercicioRutina>(selected);
		posicion = 0;
		ejercicio = ejercicios.get(posicion);
		listener = l;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		buttonAnterior = (Button)findViewById(R.id.derejercicioanterior);
		buttonSiguiente = (Button)findViewById(R.id.derejerciciosiguiente);
		buttonGuardar = (Button)findViewById(R.id.derejercicioseleccionar);
		buttonSalir = (Button)findViewById(R.id.dersalirdialogo);
		imageViewFoto = (ImageView)findViewById(R.id.derfotoEj);

		series = (EditText)findViewById(R.id.derseries);
		repeticiones = (EditText)findViewById(R.id.derrepeticiones);
		descansoSeries = (EditText)findViewById(R.id.derdescansoseries);
		descansoEjercicios = (EditText)findViewById(R.id.derdescansoejercicios);

		buttonSalir.setOnClickListener(this);
		buttonGuardar.setOnClickListener(this);
		buttonSiguiente.setOnClickListener(this);
		buttonAnterior.setOnClickListener(this);

		cargarEjercicio();
	}
	
	/*
	* Se editan los campos de los diferentes items en función del ejercicio que se muestre.
	* */
	void cargarEjercicio(){
		setTitle(ejercicio.getNombre());
		int resource = MiEntrenadorPersonapp.getContext().getResources().getIdentifier(ejercicio.getFoto(), "drawable", MiEntrenadorPersonapp.getContext().getPackageName());
		imageViewFoto.setImageResource(resource);
		//imageViewFoto.setImageDrawable(<Drawable>)
		seleccionado = enRutina();

		if(seleccionado){
			descansoSeries.setText(Integer.valueOf(ejercicio.getDescansoSeries()).toString());
			descansoEjercicios.setText(Integer.valueOf(ejercicio.getDescansoEjercicio()).toString());
			series.setText(Integer.valueOf(ejercicio.getSeries()).toString());
			repeticiones.setText(ejercicio.getRepeticiones());
			buttonGuardar.setText("Quitar");
		}

		else{
			String s = null;
			descansoSeries.setText(s);
			descansoEjercicios.setText(s);
			series.setText(s);
			repeticiones.setText(s);			
			buttonGuardar.setText("Añadir");
		}
	}
	
	@Override
	public void onClick(View v) { 

		switch(v.getId()){
		case R.id.derejercicioanterior:
        	if(posicion > 0) posicion--;
        	else posicion = ejercicios.size()-1;
        	ejercicio = ejercicios.get(posicion);
        	cargarEjercicio();
			break;

		case R.id.derejerciciosiguiente:
        	if(posicion < ejercicios.size()-1) posicion++;
        	else posicion = 0;
        	ejercicio = ejercicios.get(posicion);
        	cargarEjercicio();
			break;	
			
		case R.id.derejercicioseleccionar:
			if(seleccionado){
				Log.w("LOG","LINEA 96");
				for(int i = 0; i < seleccionados.size(); i++){
					if(ejercicio.getNombre().equals(seleccionados.get(i).getNombre())){
						seleccionados.remove(i);
					}
				}
				buttonGuardar.setText("Añadir");
			}
			
			else{
				Log.w("LOG","LINEA 106");
				if(! descansoEjercicios.getText().toString().equals("")){
					ejercicio.setDescansoEjercicio(Integer.valueOf(descansoEjercicios.getText().toString()));
				}
				
				if(! descansoSeries.getText().toString().equals("")){
					ejercicio.setDescansoSeries(Integer.valueOf(descansoSeries.getText().toString()));
				}			
				
				if(! series.getText().toString().equals("")){
					ejercicio.setSeries(Integer.valueOf(series.getText().toString()));
				}
				
				if(! repeticiones.getText().toString().equals("")){
					ejercicio.setRepeticiones(Integer.valueOf(repeticiones.getText().toString()));
				}
				seleccionados.add(ejercicio);
				buttonGuardar.setText("Quitar");
			}
			cargarEjercicio();
			break;		
			
		case R.id.dersalirdialogo:
			listener.actualizarLista(seleccionados);
			dismiss();
			break;			
		}
	}
	
	/*
	 * Comprueba si el ejercicio actual ha sido seleccionado para poner  en los editText 
	 * los campos tal y como los dejóel usuario
	 */
	private boolean enRutina(){
		boolean incluido = false;
		for(EjercicioRutina ej: seleccionados){
			if(ejercicio.getNombre().equals(ej.getNombre())){
				incluido = true;
			}
		}
		return incluido;
	}
	
}
