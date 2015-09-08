package togacharls.mientrenadorpersonapp.Dialogs;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;
import java.util.ArrayList;
import android.view.View.OnClickListener;

import togacharls.mientrenadorpersonapp.Application.MiEntrenadorPersonapp;
import togacharls.mientrenadorpersonapp.Database.Ejercicio;
import togacharls.mientrenadorpersonapp.Listeners.ListenerEjercicio;
import togacharls.mientrenadorpersonapp.R;

public class DialogEjercicio extends Dialogo implements OnClickListener{
	
	private ImageView foto;
	private Button salir, guardar, siguiente, anterior;
	private int posicion;
	private ArrayList<Ejercicio> ejercicios;
	private Ejercicio ejercicio;
	private ListenerEjercicio listener;
	
	public DialogEjercicio(Context context, ListenerEjercicio l, ArrayList<Ejercicio> ejs){
		super(context, R.layout.dialog_ejercicios);
		ejercicios = new ArrayList<Ejercicio>(ejs);
		posicion = 0;
		ejercicio = ejercicios.get(posicion);
		listener = l;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		cargarItems();
		cargarEjercicio();
	}
	
	
	private void cargarEjercicio(){
		setTitle(ejercicio.getNombre());

		String path = "drawable/"+ejercicio.getFoto();
		int fuenteImagen = MiEntrenadorPersonapp.getContext().getResources().getIdentifier(path, null, MiEntrenadorPersonapp.getContext().getPackageName());
		Drawable imagen = MiEntrenadorPersonapp.getContext().getResources().getDrawable(fuenteImagen);
		foto.setImageDrawable(imagen);

		guardar.setText("Seleccionar");
	}	
	
	private void cargarItems(){
		anterior = (Button)findViewById(R.id.ejercicioanterior);
		siguiente = (Button)findViewById(R.id.ejerciciosiguiente);
		guardar = (Button)findViewById(R.id.ejercicioseleccionar);
		salir = (Button)findViewById(R.id.salirdialogo);
		foto = (ImageView)findViewById(R.id.fotoEj);
				
		salir.setOnClickListener(this);
		guardar.setOnClickListener(this);	
		siguiente.setOnClickListener(this);
		anterior.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) { 

		switch(v.getId()){
		case R.id.ejercicioanterior:
        	if(posicion > 0) posicion--;
        	else posicion = ejercicios.size()-1;
        	ejercicio = ejercicios.get(posicion);
        	cargarEjercicio();
			break;
		case R.id.ejerciciosiguiente:
        	if(posicion < ejercicios.size()-1) posicion++;
        	else posicion = 0;
        	ejercicio = ejercicios.get(posicion);
        	cargarEjercicio();
			break;	
		case R.id.ejercicioseleccionar:
			dismiss();	
			listener.cambiarEjercicio(ejercicio.getNombre());
			break;			
		case R.id.salirdialogo:
			dismiss();
			break;			
		}
	}
}
