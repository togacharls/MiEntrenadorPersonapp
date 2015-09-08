package togacharls.mientrenadorpersonapp.Activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;

import togacharls.mientrenadorpersonapp.Application.MiEntrenadorPersonapp;
import togacharls.mientrenadorpersonapp.Database.Ejercicio;
import togacharls.mientrenadorpersonapp.ListView.AdapterListView;
import togacharls.mientrenadorpersonapp.Dialogs.DialogEjercicioRutina;
import togacharls.mientrenadorpersonapp.Database.EjercicioRutina;
import togacharls.mientrenadorpersonapp.Listeners.ListenerEjercicioRutina;
import togacharls.mientrenadorpersonapp.R;

public class DefinirRutinaActivity extends AppCompatActivity implements ListenerEjercicioRutina {
	
	private Button buttonSiguienteDia, buttonAnteriorDia;
	private Button buttonEjerciciosSeleccionados, buttonGuardarDia;
	private TextView textViewDia;
	private ListView listaMusculos;
	private boolean listaConfirmada;
	protected static ArrayList<EjercicioRutina> ejerciciosSeleccionados;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.diassemana);

		buttonSiguienteDia = (Button)this.findViewById(R.id.nextday);
		buttonAnteriorDia = (Button)this.findViewById(R.id.prevday);
		textViewDia = (TextView)this.findViewById(R.id.diaSemana);
		buttonEjerciciosSeleccionados = (Button)this.findViewById(R.id.verDiaRutina);
		buttonGuardarDia = (Button)this.findViewById(R.id.guardarDiaRutina);

		//Se carga el listado de ejercicios para el día actual.
		ejerciciosSeleccionados = new ArrayList<EjercicioRutina>(MiEntrenadorPersonapp.listaEjerciciosDia(textViewDia.getText().toString()));
		listaConfirmada = false;
		final ListenerEjercicioRutina listenerEjerciciRutina = this;

		//Botón para cambiar al día siguiente
		buttonSiguienteDia.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				if (textViewDia.getText().toString().equals("Lunes")) {
					cambiarDia("Martes", "Lunes");
				} else if (textViewDia.getText().toString().equals("Martes")) {
					cambiarDia("Miércoles", "Martes");
				} else if (textViewDia.getText().toString().equals("Miércoles")) {
					cambiarDia("Jueves", "Miércoles");
				} else if (textViewDia.getText().toString().equals("Jueves")) {
					cambiarDia("Viernes", "Jueves");
				} else if (textViewDia.getText().toString().equals("Viernes")) {
					cambiarDia("Sábado", "Viernes");
				} else if (textViewDia.getText().toString().equals("Sábado")) {
					cambiarDia("Domingo", "Sábado");
				} else if (textViewDia.getText().toString().equals("Domingo")) {
					cambiarDia("Lunes", "Domingo");
				}
			}
		});

		//Botón para cambiar al día anterior
		buttonAnteriorDia.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (textViewDia.getText().toString().equals("Lunes")) {
					cambiarDia("Domingo", "Lunes");
				} else if (textViewDia.getText().toString().equals("Martes")) {
					cambiarDia("Lunes", "Martes");
				} else if (textViewDia.getText().toString().equals("Miércoles")) {
					cambiarDia("Martes", "Miércoles");
				} else if (textViewDia.getText().toString().equals("Jueves")) {
					cambiarDia("Miércoles", "Jueves");
				} else if (textViewDia.getText().toString().equals("Viernes")) {
					cambiarDia("Jueves", "Viernes");
				} else if (textViewDia.getText().toString().equals("Sábado")) {
					cambiarDia("Viernes", "Sábado");
				} else if (textViewDia.getText().toString().equals("Domingo")) {
					cambiarDia("Sábado", "Domingo");
				}
			}
		});


		buttonEjerciciosSeleccionados.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (ejerciciosSeleccionados.size() > 0) {
					DialogEjercicioRutina dialogEjercicioRutina = new DialogEjercicioRutina(MiEntrenadorPersonapp.getContext(), listenerEjerciciRutina, ejerciciosSeleccionados, ejerciciosSeleccionados);
					dialogEjercicioRutina.show();
				} else {
					Toast.makeText(MiEntrenadorPersonapp.getContext(), "No hay ningún ejercicio seleccionado", Toast.LENGTH_SHORT).show();
				}
			}
		});

		buttonGuardarDia.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//Se elimina la listaMusculos que hubiese anteriormente
				MiEntrenadorPersonapp.eliminarEjercicios(textViewDia.getText().toString());
				/*bd.getWritableDatabase().delete("EjercicioRutina",
						"textViewDia=" + "'" + textViewDia.getText().toString() + "'", null);
				bd.getWritableDatabase().rawQuery("" +
						"DELETE FROM EjercicioRutina " +
						"WHERE textViewDia = '"+ textViewDia.getText().toString() +"'" , null);*/

				//Se añade la listaMusculos de los ejercicios buttonEjerciciosSeleccionados
				if (ejerciciosSeleccionados.size() > 0) {
					MiEntrenadorPersonapp.añadirEjercicios(textViewDia.getText().toString(), ejerciciosSeleccionados);
				}
				//
				listaConfirmada = true;
			}
		});

		listaMusculos = (ListView) findViewById(R.id.listado);
		AdapterListView adapter = new AdapterListView(R.layout.elementolista, MiEntrenadorPersonapp.listaMusculos(), null);

		listaMusculos.setAdapter(adapter);

		listaMusculos.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				String musculoSeleccionado = ((TextView) view.findViewById(R.id.nombreElemento)).getText().toString();
				ArrayList<Ejercicio> listaEjercicios =  MiEntrenadorPersonapp.listaEjerciciosMusculo(musculoSeleccionado);

				if (listaEjercicios.isEmpty()) {
					Toast.makeText(MiEntrenadorPersonapp.getContext(), "No se han encontrado ejercicios para este grupo muscular", Toast.LENGTH_SHORT).show();
				} else {
					//Se genera un ArrayList<EjercicioRutina> a partir de listaEjercicios.
					ArrayList<EjercicioRutina> listaRutina = new ArrayList();
					for(Ejercicio ejercicio: listaEjercicios){
						EjercicioRutina ejercicioRutina = new EjercicioRutina(ejercicio.getNombre(), textViewDia.getText().toString(), ejercicio.getFoto(), 0, 0, 0, 0);
						listaRutina.add(ejercicioRutina);
					}
					//Se muestra el DialogEjercicioRutina con la listView de ejercicios de un grupo muscular resaltando los que ya están seleccionados.
					DialogEjercicioRutina de = new DialogEjercicioRutina(MiEntrenadorPersonapp.getContext(), listenerEjerciciRutina, listaRutina, ejerciciosSeleccionados);
					de.show();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		this.getMenuInflater().inflate(R.menu.main, menu);
		/*Inflater inflater = this.getMenuInflater();
		inflater.inflate(R.menu.main, menu);*/
		return true;
	}

	/*
	* Se cambia el día en el que se está editando la rutina. Si no se guarda la listView de un día concreto, ésta se pierde.
	* */
	private void cambiarDia(final String diaNuevo, final String diaActual){
		//Si se decide cambiar de día sin haber registrado los cambios, se muestra un diálogo que avisa al usuario. Sólo si ha habido cambios.
		if(!listaConfirmada){
			if(listaAlterada(diaActual)){
				DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which){
							case DialogInterface.BUTTON_POSITIVE:

								MiEntrenadorPersonapp.eliminarEjercicios(textViewDia.getText().toString());

								if (ejerciciosSeleccionados.size() > 0) {
									MiEntrenadorPersonapp.añadirEjercicios(diaActual, ejerciciosSeleccionados);
								}

								textViewDia.setText(diaNuevo);
								ejerciciosSeleccionados = MiEntrenadorPersonapp.listaEjerciciosDia(diaNuevo);
								break;

							case DialogInterface.BUTTON_NEGATIVE:
								textViewDia.setText(diaNuevo);
								ejerciciosSeleccionados.clear();
								break;
						}
					}
				};

				AlertDialog.Builder builder = new AlertDialog.Builder(MiEntrenadorPersonapp.getContext());
				builder.setMessage("No has guardado los ejercicios para este día, ¿deseas guardarlos?")
						.setPositiveButton("Sí", dialogClickListener)
						.setNegativeButton("No", dialogClickListener).show();
			}
			else{
				textViewDia.setText(diaNuevo);
				ejerciciosSeleccionados = MiEntrenadorPersonapp.listaEjerciciosDia(diaNuevo);
			}
		}else{
			textViewDia.setText(diaNuevo);
			ejerciciosSeleccionados = MiEntrenadorPersonapp.listaEjerciciosDia(diaNuevo);
		}
		listaConfirmada = false;
	}

	/*
	* Devuelve true si la listView ha sido alterada. False si coincide con la que ya hay registrada en la Base de datos.
	* */
	public boolean listaAlterada(String dia){
		boolean alterada = false;
		if(!MiEntrenadorPersonapp.listaEjerciciosDia(dia).isEmpty() || !ejerciciosSeleccionados.isEmpty()){
			if((MiEntrenadorPersonapp.listaEjerciciosDia(dia).size() == ejerciciosSeleccionados.size())){
				for (EjercicioRutina ejercicioRutinaSeleccionado : ejerciciosSeleccionados) {
					boolean esta = false;
					for (EjercicioRutina ejercicioRutinaBD : MiEntrenadorPersonapp.listaEjerciciosDia(dia)) {
						if (ejercicioRutinaSeleccionado.getNombre().equals(ejercicioRutinaBD.getNombre())) {
							esta = true;
						}
					}
					if (!esta) {
						alterada = true;
						break;
					}
				}
			}
			else{
				alterada = true;
			}
		}
		return alterada;
	}

	@Override
	public void actualizarLista(ArrayList<EjercicioRutina> lista) {
		if(ejerciciosSeleccionados == null){
			ejerciciosSeleccionados = new ArrayList<EjercicioRutina>();
		}
		ejerciciosSeleccionados.clear();
		for(EjercicioRutina ej: lista){
			ejerciciosSeleccionados.add(ej);
		}
	}

	@Override
	public boolean comprobarExistencia(String n) {
		boolean esta = false;
		for(EjercicioRutina ej: ejerciciosSeleccionados){
			if(n.equals(ej.getNombre())){
				esta = true;
				break;
			}
		}
		return esta;
	}
}